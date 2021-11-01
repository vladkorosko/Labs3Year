package org.example.lab2;

// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.List;
import java.util.Vector;
import java.io.*;

public class SchedulingAlgorithm {

    public static Results Run(int runtime, List<Process> processVector, Results result) {
        int i;
        int comptime = 0;
        int currentProcess = 0;
        int previousProcess = 0;
        int size = processVector.size();
        int completed = 0;
        String resultsFile = "Summary-Processes";

        result.schedulingType = "Batch (Nonpreemptive)";
        result.schedulingName = "First-Come First-Served";
        try {
            //BufferedWriter out = new BufferedWriter(new FileWriter(resultsFile));
            //OutputStream out = new FileOutputStream(resultsFile);
            PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
            Process process = (Process) processVector.get(currentProcess);
            out.println("Process: " + currentProcess + " registered... (" + process.cpu_time + " " +
                        process.io_blocking + " " + process.cpu_done + " " + process.cpu_done+ ")");
            while (comptime < runtime) {
                if (process.cpu_done == process.cpu_time) {
                    completed++;
                    out.println("Process: " + currentProcess + " completed... (" + process.cpu_time + " " + process.io_blocking + " " + process.cpu_done + " " + process.cpu_done + ")");
                    if (completed == size) {
                        result.compuTime = comptime;
                        out.close();
                        return result;
                    }
                    for (i = size - 1; i >= 0; i--) {
                        process = (Process) processVector.get(i);
                        if (process.cpu_done < process.cpu_time) {
                            currentProcess = i;
                        }
                    }
                    process = (Process) processVector.get(currentProcess);
                    out.println("Process: " + currentProcess + " registered... (" + process.cpu_time + " " + process.io_blocking + " " + process.cpu_done + " " + process.cpu_done + ")");
                }
                if (process.io_blocking == process.io_next) {
                    out.println("Process: " + currentProcess + " I/O blocked... (" + process.cpu_time + " " + process.io_blocking + " " + process.cpu_done + " " + process.cpu_done + ")");
                    process.num_blocked++;
                    process.io_next = 0;
                    previousProcess = currentProcess;
                    for (i = size - 1; i >= 0; i--) {
                        process = (Process) processVector.get(i);
                        if (process.cpu_done < process.cpu_time && previousProcess != i) {
                            currentProcess = i;
                        }
                    }
                    process = (Process) processVector.get(currentProcess);
                    out.println("Process: " + currentProcess + " registered... (" + process.cpu_time + " " + process.io_blocking + " " + process.cpu_done + " " + process.cpu_done + ")");
                }
                process.cpu_done++;
                if (process.io_blocking > 0) {
                    process.io_next++;
                }
                comptime++;
            }
            out.close();
        } catch (IOException e) { /* Handle exceptions */ }
        result.compuTime = comptime;
        return result;
    }
}
