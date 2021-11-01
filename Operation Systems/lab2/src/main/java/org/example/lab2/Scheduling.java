package org.example.lab2;

// This file contains the main() function for the Scheduling
// simulation.  Init() initializes most of the variables by
// reading from a provided file.  SchedulingAlgorithm.Run() is
// called from main() to run the simulation.  Summary-Results
// is where the summary results are written, and Summary-Processes
// is where the process scheduling summary is written.

// Created by Alexander Reeder, 2001 January 06

import java.io.*;
import java.util.*;

public class Scheduling {

    private static int process_num = 5;
    private static int meanDev = 1000;
    private static int standardDev = 100;
    private static int runtime = 1000;
    private static final List<Process> processVector = new ArrayList<>();
    private static Results result = new Results("null","null",0);
    private static final String resultsFile = "Summary-Results";

    private static void Init(String file) {
        File f = new File(file);
        String line;
        int cpu_time;
        int io_blocking;
        double X;

        try {
            //BufferedReader in = new BufferedReader(new FileReader(f));
            DataInputStream in = new DataInputStream(new FileInputStream(f));
            while ((line = in.readLine()) != null) {
                if (line.startsWith("num_process")) {
                    StringTokenizer st = new StringTokenizer(line);
                    st.nextToken();
                    process_num = Common.s2i(st.nextToken());
                }
                if (line.startsWith("mean_dev")) {
                    StringTokenizer st = new StringTokenizer(line);
                    st.nextToken();
                    meanDev = Common.s2i(st.nextToken());
                }
                if (line.startsWith("stand_dev")) {
                    StringTokenizer st = new StringTokenizer(line);
                    st.nextToken();
                    standardDev = Common.s2i(st.nextToken());
                }
                if (line.startsWith("process")) {
                    StringTokenizer st = new StringTokenizer(line);
                    st.nextToken();
                    io_blocking = Common.s2i(st.nextToken());
                    X = Common.R1();
                    while (X == -1.0) {
                        X = Common.R1();
                    }
                    X = X * standardDev;
                    cpu_time = (int) X + meanDev;
                    processVector.add(new Process(cpu_time, io_blocking, 0, 0, 0));
                }
                if (line.startsWith("runtime")) {
                    StringTokenizer st = new StringTokenizer(line);
                    st.nextToken();
                    runtime = Common.s2i(st.nextToken());
                }
            }
            in.close();
        } catch (IOException e) { /* Handle exceptions */ }
    }

    private static void debug() {
        System.out.println("process_num " + process_num);
        System.out.println("mean_dev " + meanDev);
        System.out.println("stand_dev " + standardDev);
        int size = processVector.size();
        for (int i = 0; i < size; i++) {
            Process process = (Process) processVector.get(i);
            System.out.println("process " + i + " " + process.cpu_time + " " + process.io_blocking + " " + process.cpu_done + " " + process.num_blocked);
        }
        System.out.println("runtime " + runtime);
    }

    public static void main(String[] args) {
        /*if (args.length != 1) {
            System.out.println("Usage: 'java Scheduling <INIT FILE>'");
            System.exit(-1);
        }*/
        File f = new File("scheduling.conf");
        if (!(f.exists())) {
            System.out.println("Scheduling: error, file '" + f.getName() + "' does not exist.");
            System.exit(-1);
        }
        if (!(f.canRead())) {
            System.out.println("Scheduling: error, read of " + f.getName() + " failed.");
            System.exit(-1);
        }
        System.out.println("Working...");
        Init("scheduling.conf");

        int i;
        if (processVector.size() < process_num) {
            i = 0;
            while (processVector.size() < process_num) {
                double X = Common.R1();
                while (X == -1.0) {
                    X = Common.R1();
                }
                X = X * standardDev;
                int cpu_time = (int) X + meanDev;
                processVector.add(new Process(cpu_time,i*100,0,0,0));
                i++;
            }
        }
        result = SchedulingAlgorithm.Run(runtime, processVector, result);
        try {
            //BufferedWriter out = new BufferedWriter(new FileWriter(resultsFile));
            PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
            out.println("Scheduling Type: " + result.schedulingType);
            out.println("Scheduling Name: " + result.schedulingName);
            out.println("Simulation Run Time: " + result.compuTime);
            out.println("Mean: " + meanDev);
            out.println("Standard Deviation: " + standardDev);
            out.println("Process #\tCPU Time\tIO Blocking\tCPU Completed\tCPU Blocked");
            for (i = 0; i < processVector.size(); i++) {
                Process process = (Process) processVector.get(i);
                out.print(i);
                if (i < 100) { out.print("\t\t"); } else { out.print("\t"); }
                out.print(process.cpu_time);
                if (process.cpu_time < 100) { out.print(" (ms)\t\t"); } else { out.print(" (ms)\t"); }
                out.print(process.io_blocking);
                if (process.io_blocking < 100) { out.print(" (ms)\t\t"); } else { out.print(" (ms)\t"); }
                out.print(process.cpu_done);
                if (process.cpu_done < 100) { out.print(" (ms)\t\t"); } else { out.print(" (ms)\t"); }
                out.println(process.num_blocked + " times");
            }
            out.close();
        } catch (IOException e) { /* Handle exceptions */ }
        System.out.println("Completed.");
    }
}


