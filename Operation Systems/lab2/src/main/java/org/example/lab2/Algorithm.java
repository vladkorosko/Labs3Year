package org.example.lab2;

// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.List;
import java.io.*;

public class Algorithm {

    private static int indexEarliestDeadline(List<sProcess> processVector){
        int index = 0;
        for (int i = 0; i < processVector.size(); i++)
            if(!processVector.get(i).finish)
                index = i;
        for (int i = 0; i < processVector.size(); i++) {
            if (!processVector.get(i).finish) {
                if (processVector.get(index).deadline > processVector.get(i).deadline)
                    index = i;
            }
        }
        return index;
    }

    public static Results Run(int runtime, List<sProcess> processVector, Results result) {
        int comp_time = 0;
        int currentProcess = indexEarliestDeadline(processVector);
        int size = processVector.size();
        int completed = 0;
        String resultsFile = "Summary-Processes";

        result.schedulingType = "Batch (Nonpreemptive)";
        result.schedulingName = "Earliest Deadline First";
        try {
            //BufferedWriter out = new BufferedWriter(new FileWriter(resultsFile));
            //OutputStream out = new FileOutputStream(resultsFile);
            PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
            sProcess process = processVector.get(currentProcess);
            out.println("Process: " + currentProcess + " registered... (" + process.cpu_time + " " +
                        process.io_blocking + " " + process.cpu_done + " " + process.deadline+ ")");
            while (comp_time < runtime) {
                if (process.cpu_done == process.cpu_time) {
                    completed++;
                    process.finish = true;
                    process.cpu_done = comp_time;
                    out.println("Process: " + currentProcess + " completed... (" + process.cpu_time + " " +
                                process.io_blocking + " " + process.cpu_done + " " + process.deadline + ")");
                    if (completed == size) {
                        result.compuTime = comp_time;
                        out.close();
                        return result;
                    }
                    currentProcess = indexEarliestDeadline(processVector);
                    process = processVector.get(currentProcess);
                    out.println("Process: " + currentProcess + " registered... (" + process.cpu_time + " " +
                                process.io_blocking + " " + process.cpu_done + " " + process.deadline + ")");
                }
                if (process.io_blocking == process.io_next) {
                    out.println("Process: " + currentProcess + " I/O blocked... (" + process.cpu_time + " " +
                                process.io_blocking + " " + process.cpu_done + " " + process.deadline + ")");
                    process.num_blocked++;
                    process.io_next = 0;
                    currentProcess = indexEarliestDeadline(processVector);
                    process = processVector.get(currentProcess);
                    out.println("Process: " + currentProcess + " registered... (" + process.cpu_time + " " +
                                process.io_blocking + " " + process.cpu_done + " " + process.deadline + ")");
                }
                process.cpu_done++;
                if (process.io_blocking > 0) {
                    process.io_next++;
                }
                comp_time++;
            }
            out.close();
        } catch (IOException e) { /* Handle exceptions */ }
        result.compuTime = comp_time;
        return result;
    }
}
