package org.example.lab3;

import java.lang.Thread;
import java.io.*;
import java.util.*;

public class Kernel extends Thread {
    // The number of virtual pages must be fixed at 63 due to
    // dependencies in the GUI
    private static int virtualPageNumber = 63;

    private String output = null;
    private static final String lineSeparator =
            System.getProperty("line.separator");
    private String command_file;
    private String config_file;
    private ControlPanel controlPanel;
    private final List<Page> memVector = new ArrayList<>();
    private final List<Instruction> instructVector = new ArrayList<>();
    private boolean doStdoutLog = false;
    private boolean doFileLog = false;
    public int runs;
    public int runCycles;
    public long block = (int) Math.pow(2, 12);
    public static byte addressRadix = 10;

    private final List<Integer> clock = new ArrayList<>();
    Integer handIndex = 0;

    public void init(String commands, String config) {
        File f;
        command_file = commands;
        config_file = config;
        String line;
        String tmp = null;
        String command = "";
        byte R = 0;
        byte M = 0;
        int i;
        int j;
        int id;
        int physical;
        int physicalCount = 0;
        int inMemTime;
        int lastTouchTime;
        int mapCount = 0;
        double power;
        long high;
        long low;
        long address;
        long addressLimit = (block * virtualPageNumber + 1) - 1;


        if (config != null) {
            f = new File(config);
            try {
                DataInputStream d = new DataInputStream(new FileInputStream(f));
                BufferedReader in = new BufferedReader(new InputStreamReader(d));
                while ((line = in.readLine()) != null) {
                    if (line.startsWith("numberOfPages")) {
                        StringTokenizer st = new StringTokenizer(line);
                        while (st.hasMoreTokens()) {
                            tmp = st.nextToken();
                            virtualPageNumber = Common.s2i(st.nextToken()) - 1;
                            if (virtualPageNumber < 2 || virtualPageNumber > 63) {
                                System.out.println("MemoryManagement: numberOfPages out of bounds.");
                                System.exit(-1);
                            }
                            addressLimit = (block * virtualPageNumber + 1) - 1;
                        }
                    }
                }
                in.close();
            } catch (IOException e) { /* Handle exceptions */ }
            for (i = 0; i <= virtualPageNumber; i++) {
                high = (block * (i + 1)) - 1;
                low = block * i;
                memVector.add(new Page(i, -1, R, M, 0, 0, high, low));
            }
            try {
                DataInputStream d = new DataInputStream(new FileInputStream(f));
                BufferedReader in = new BufferedReader(new InputStreamReader(d));
                while ((line = in.readLine()) != null) {
                    if (line.startsWith("memorySet")) {
                        StringTokenizer st = new StringTokenizer(line);
                        st.nextToken();
                        while (st.hasMoreTokens()) {
                            id = Common.s2i(st.nextToken());
                            tmp = st.nextToken();
                            if (tmp.startsWith("x")) {
                                physical = -1;
                            } else {
                                physical = Common.s2i(tmp);
                            }
                            if ((0 > id || id > virtualPageNumber) || (-1 > physical || physical > ((virtualPageNumber - 1) / 2))) {
                                System.out.println("MemoryManagement: Invalid page value in " + config);
                                System.exit(-1);
                            }
                            R = Common.s2b(st.nextToken());
                            if (R < 0 || R > 1) {
                                System.out.println("MemoryManagement: Invalid R value in " + config);
                                System.exit(-1);
                            }
                            M = Common.s2b(st.nextToken());
                            if (M < 0 || M > 1) {
                                System.out.println("MemoryManagement: Invalid M value in " + config);
                                System.exit(-1);
                            }
                            inMemTime = Common.s2i(st.nextToken());
                            if (inMemTime < 0) {
                                System.out.println("MemoryManagement: Invalid inMemTime in " + config);
                                System.exit(-1);
                            }
                            lastTouchTime = Common.s2i(st.nextToken());
                            if (lastTouchTime < 0) {
                                System.out.println("MemoryManagement: Invalid lastTouchTime in " + config);
                                System.exit(-1);
                            }
                            Page page = memVector.get(id);
                            page.physical = physical;
                            page.R = R;
                            page.M = M;
                            page.inMemTime = inMemTime;
                            page.lastTouchTime = lastTouchTime;
                            clock.add(page.id);
                        }
                    }
                    if (line.startsWith("enable_logging")) {
                        StringTokenizer st = new StringTokenizer(line);
                        while (st.hasMoreTokens()) {
                            if (st.nextToken().startsWith("true")) {
                                doStdoutLog = true;
                            }
                        }
                    }
                    if (line.startsWith("log_file")) {
                        StringTokenizer st = new StringTokenizer(line);
                        while (st.hasMoreTokens()) {
                            tmp = st.nextToken();
                        }
                        if (tmp.startsWith("log_file")) {
                            doFileLog = false;
                            output = "traceFile";
                        } else {
                            doFileLog = true;
                            doStdoutLog = false;
                            output = tmp;
                        }
                    }
                    if (line.startsWith("pageSize")) {
                        StringTokenizer st = new StringTokenizer(line);
                        while (st.hasMoreTokens()) {
                            st.nextToken();
                            tmp = st.nextToken();
                            if (tmp.startsWith("power")) {
                                power = Integer.parseInt(st.nextToken());
                                block = (int) Math.pow(2, power);
                            } else {
                                block = Long.parseLong(tmp, 10);
                            }
                            addressLimit = (block * virtualPageNumber + 1) - 1;
                        }
                        if (block < 64 || block > Math.pow(2, 26)) {
                            System.out.println("MemoryManagement: pageSize is out of bounds");
                            System.exit(-1);
                        }
                        for (i = 0; i <= virtualPageNumber; i++) {
                            Page page = memVector.get(i);
                            page.high = (block * (i + 1)) - 1;
                            page.low = block * i;
                        }
                    }
                    if (line.startsWith("addressRadix")) {
                        StringTokenizer st = new StringTokenizer(line);
                        while (st.hasMoreTokens()) {
                            st.nextToken();
                            tmp = st.nextToken();
                            addressRadix = Byte.parseByte(tmp);
                            if (addressRadix < 0 || addressRadix > 20) {
                                System.out.println("MemoryManagement: addressRadix out of bounds.");
                                System.exit(-1);
                            }
                        }
                    }
                }
                in.close();
            } catch (IOException e) { /* Handle exceptions */ }
        }
        f = new File(commands);
        try {
            DataInputStream d = new DataInputStream(new FileInputStream(f));
            BufferedReader in = new BufferedReader(new InputStreamReader(d));
            while ((line = in.readLine()) != null) {
                if (line.startsWith("READ") || line.startsWith("WRITE")) {
                    if (line.startsWith("READ")) {
                        command = "READ";
                    }
                    if (line.startsWith("WRITE")) {
                        command = "WRITE";
                    }
                    StringTokenizer st = new StringTokenizer(line);
                    st.nextToken();
                    tmp = st.nextToken();
                    if (tmp.startsWith("random")) {
                        instructVector.add(new Instruction(command, Common.randomLong(addressLimit)));
                    } else {
                        if (tmp.startsWith("bin")) {
                            address = Long.parseLong(st.nextToken(), 2);
                        } else if (tmp.startsWith("oct")) {
                            address = Long.parseLong(st.nextToken(), 8);
                        } else if (tmp.startsWith("hex")) {
                            address = Long.parseLong(st.nextToken(), 16);
                        } else {
                            address = Long.parseLong(tmp);
                        }
                        if (0 > address || address > addressLimit) {
                            System.out.println("MemoryManagement: " + address + ", Address out of range in " + commands);
                            System.exit(-1);
                        }
                        instructVector.add(new Instruction(command, address));
                    }
                }
            }
            in.close();
        } catch (IOException e) { /* Handle exceptions */ }
        runCycles = instructVector.size();
        if (runCycles < 1) {
            System.out.println("MemoryManagement: no instructions present for execution.");
            System.exit(-1);
        }
        if (doFileLog) {
            File trace = new File(output);
            trace.delete();
        }
        runs = 0;
        for (i = 0; i < virtualPageNumber; i++) {
            Page page = memVector.get(i);
            if (page.physical != -1) {
                mapCount++;
            }
            for (j = 0; j < virtualPageNumber; j++) {
                Page tmp_page = memVector.get(j);
                if (tmp_page.physical == page.physical && page.physical >= 0) {
                    physicalCount++;
                }
            }
            if (physicalCount > 1) {
                System.out.println("MemoryManagement: Duplicate physical page's in " + config);
                System.exit(-1);
            }
            physicalCount = 0;
        }
        if (mapCount < (virtualPageNumber + 1) / 2) {
            for (i = 0; i < virtualPageNumber; i++) {
                Page page = memVector.get(i);
                if (page.physical == -1 && mapCount < (virtualPageNumber + 1) / 2) {
                    page.physical = i;
                    mapCount++;
                }
            }
        }
        for (i = 0; i < virtualPageNumber; i++) {
            Page page = memVector.get(i);
            if (page.physical == -1) {
                controlPanel.removePhysicalPage(i);
            } else {
                controlPanel.addPhysicalPage(i, page.physical);
            }
        }
        for (i = 0; i < instructVector.size(); i++) {
            high = block * virtualPageNumber;
            Instruction instruct = instructVector.get(i);
            if (instruct.address < 0 || instruct.address > high) {
                System.out.println("MemoryManagement: Instruction (" + instruct.inst + " " + instruct.address + ") out of bounds.");
                System.exit(-1);
            }
        }
    }

    public void setControlPanel(ControlPanel newControlPanel) {
        controlPanel = newControlPanel;
    }

    public void getPage(int pageNum) {
        Page page = memVector.get(pageNum);
        controlPanel.paintPage(page);
    }

    private void printLogFile(String message) {
        String line;
        StringBuilder temp = new StringBuilder();

        File trace = new File(output);
        if (trace.exists()) {
            try {
                DataInputStream d = new DataInputStream(new FileInputStream(output));
                BufferedReader in = new BufferedReader(new InputStreamReader(d));
                while ((line = in.readLine()) != null) {
                    temp.append(line).append(lineSeparator);
                }
                in.close();
            } catch (IOException e) {
                /* Do nothing */
            }
        }
        try {
            PrintStream out = new PrintStream(new FileOutputStream(output));
            out.print(temp);
            out.print(message);
            out.close();
        } catch (IOException e) {
            /* Do nothing */
        }
    }

    private void changeHand(int id) {
        for (int i = 0; i < clock.size(); i++) {
            if (clock.get(i) == id) {
                handIndex = i;
                return;
            }
        }
    }

    public void run() {
        step();
        while (runs != runCycles) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                /* Do nothing */
            }
            step();
        }
    }

    public void step() {
        int i;

        Instruction instruct = instructVector.get(runs);
        controlPanel.instructionValueLabel.setText(instruct.inst);
        controlPanel.addressValueLabel.setText(Long.toString(instruct.address, addressRadix));
        getPage(Virtual2Physical.pageNum(instruct.address, virtualPageNumber, block));
        if (Objects.equals(controlPanel.pageFaultValueLabel.getText(), "YES")) {
            controlPanel.pageFaultValueLabel.setText("NO");
        }
        if (instruct.inst.startsWith("READ")) {
            Page page = memVector.get(Virtual2Physical.pageNum(instruct.address, virtualPageNumber, block));
            if (page.physical == -1) {
                if (doFileLog) {
                    printLogFile("READ " + Long.toString(instruct.address, addressRadix) + " ... page fault");
                }
                if (doStdoutLog) {
                    System.out.println("READ " + Long.toString(instruct.address, addressRadix) + " ... page fault");
                }
                handIndex = ClockReplacementAlgorithm.replacePage(memVector, clock,
                        Virtual2Physical.pageNum(instruct.address, virtualPageNumber, block),
                        handIndex,
                        controlPanel);
                //PageFault.replacePage( memVector , virtualPageNumber, Virtual2Physical.pageNum( instruct.address, virtualPageNumber, block ) , controlPanel );
                controlPanel.pageFaultValueLabel.setText("YES");
            } else {
                page.R = 1;
                changeHand(page.physical);
                page.lastTouchTime = 0;
                if (doFileLog) {
                    printLogFile("READ " + Long.toString(instruct.address, addressRadix) + " ... okay");
                }
                if (doStdoutLog) {
                    System.out.println("READ " + Long.toString(instruct.address, addressRadix) + " ... okay");
                }
            }
        }
        if (instruct.inst.startsWith("WRITE")) {
            Page page = memVector.get(Virtual2Physical.pageNum(instruct.address, virtualPageNumber, block));
            if (page.physical == -1) {
                if (doFileLog) {
                    printLogFile("WRITE " + Long.toString(instruct.address, addressRadix) + " ... page fault");
                }
                if (doStdoutLog) {
                    System.out.println("WRITE " + Long.toString(instruct.address, addressRadix) + " ... page fault");
                }
                handIndex = ClockReplacementAlgorithm.replacePage(memVector, clock,
                        Virtual2Physical.pageNum(instruct.address, virtualPageNumber, block),
                        handIndex,
                        controlPanel);
                //PageFault.replacePage( memVector , virtualPageNumber, Virtual2Physical.pageNum( instruct.address, virtualPageNumber, block ) , controlPanel );          controlPanel.pageFaultValueLabel.setText( "YES" );
            } else {
                page.M = 1;
                page.R = 1;
                changeHand(page.id);
                page.lastTouchTime = 0;
                if (doFileLog) {
                    printLogFile("WRITE " + Long.toString(instruct.address, addressRadix) + " ... okay");
                }
                if (doStdoutLog) {
                    System.out.println("WRITE " + Long.toString(instruct.address, addressRadix) + " ... okay");
                }
            }
        }
        for (i = 0; i < virtualPageNumber; i++) {
            Page page = memVector.get(i);/*
            if ( page.R == 1 && page.lastTouchTime == 10 )
            {
                page.R = 0;
            }/*/
            if (page.physical != -1) {
                page.inMemTime = page.inMemTime + 10;
                page.lastTouchTime = page.lastTouchTime + 10;
            }
        }
        runs++;
        controlPanel.timeValueLabel.setText(runs * 10 + " (ns)");
    }

    public void reset() {
        handIndex = 0;
        clock.clear();
        memVector.clear();
        instructVector.clear();
        controlPanel.statusValueLabel.setText("STOP");
        controlPanel.timeValueLabel.setText("0");
        controlPanel.instructionValueLabel.setText("NONE");
        controlPanel.addressValueLabel.setText("NULL");
        controlPanel.pageFaultValueLabel.setText("NO");
        controlPanel.virtualPageValueLabel.setText("x");
        controlPanel.physicalPageValueLabel.setText("0");
        controlPanel.RValueLabel.setText("0");
        controlPanel.MValueLabel.setText("0");
        controlPanel.inMemTimeValueLabel.setText("0");
        controlPanel.lastTouchTimeValueLabel.setText("0");
        controlPanel.lowValueLabel.setText("0");
        controlPanel.highValueLabel.setText("0");
        init(command_file, config_file);
    }
}
