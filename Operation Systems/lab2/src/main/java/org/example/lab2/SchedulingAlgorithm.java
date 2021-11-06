package org.example.lab2;

// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.List;
import java.io.*;

public class SchedulingAlgorithm {

    public static Results Run(int runtime, List<Process> process_vector, Results result) {
        int i;
        int comp_time = 0;
        int current_process = 0;
        int previous_process;
        int size = process_vector.size();
        int completed = 0;
        String results_file = "Summary-Processes";

        result.scheduling_type = "Batch (Nonpreemptive)";
        result.scheduling_name = "First-Come First-Served";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(results_file));
            Process process = process_vector.get(current_process);
            out.println("Process: " + current_process + " registered... (" + process.cpu_time + " " +
                        process.io_blocking + " " + process.cpu_done + " " + process.cpu_done+ ")");
            while (comp_time < runtime) {
                if (process.cpu_done == process.cpu_time) {
                    completed++;
                    out.println("Process: " + current_process + " completed... (" + process.cpu_time + " " +
                                process.io_blocking + " " + process.cpu_done + " " + process.cpu_done + ")");
                    if (completed == size) {
                        result.compu_time = comp_time;
                        out.close();
                        return result;
                    }
                    for (i = size - 1; i >= 0; i--) {
                        process = process_vector.get(i);
                        if (process.cpu_done < process.cpu_time) {
                            current_process = i;
                        }
                    }
                    process = process_vector.get(current_process);
                    out.println("Process: " + current_process + " registered... (" + process.cpu_time + " " +
                                process.io_blocking + " " + process.cpu_done + " " + process.cpu_done + ")");
                }
                if (process.io_blocking == process.io_next) {
                    out.println("Process: " + current_process + " I/O blocked... (" + process.cpu_time + " " +
                                process.io_blocking + " " + process.cpu_done + " " + process.cpu_done + ")");
                    process.num_blocked++;
                    process.io_next = 0;
                    previous_process = current_process;
                    for (i = size - 1; i >= 0; i--) {
                        process = process_vector.get(i);
                        if (process.cpu_done < process.cpu_time && previous_process != i) {
                            current_process = i;
                        }
                    }
                    process = process_vector.get(current_process);
                    out.println("Process: " + current_process + " registered... (" + process.cpu_time + " " +
                                process.io_blocking + " " + process.cpu_done + " " + process.cpu_done + ")");
                }
                process.cpu_done++;
                if (process.io_blocking > 0) {
                    process.io_next++;
                }
                comp_time++;
            }
            out.close();
        } catch (IOException e) { /* Handle exceptions */ }
        result.compu_time = comp_time;
        return result;
    }
}
