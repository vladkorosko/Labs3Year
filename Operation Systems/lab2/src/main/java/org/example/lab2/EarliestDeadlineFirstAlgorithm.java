package org.example.lab2;

// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class EarliestDeadlineFirstAlgorithm {

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

    private static int indexEarliestDeadline(List<sProcess> processVector, int index_previous){
        int index = 0;
        for (int i = 0; i < processVector.size(); i++)
            if (i != index_previous) {
                if (!processVector.get(i).finish)
                    index = i;
            }
        for (int i = 0; i < processVector.size(); i++) {
            if (i != index_previous) {
                if (!processVector.get(i).finish) {
                    if (processVector.get(index).deadline > processVector.get(i).deadline)
                        index = i;
                }
            }
        }
        return index;
    }

    private static List<Integer> getDeadlines(List<sProcess> process_vector){
        List<Integer> result = new ArrayList<>();
        for (sProcess i : process_vector) {
            result.add(i.deadline);
        }
        return result;
    }

    private static boolean hasNotFinishedProcesses(List<sProcess> process_vector){
        for (sProcess i : process_vector){
            if (i.finish == false)
                return true;
        }
        return false;
    }

    public static Results RunFullProcess(int runtime, List<sProcess> process_vector, Results result) {
        int comp_time = 0;
        int current_process = indexEarliestDeadline(process_vector);
        List<Integer> arrival_time = getDeadlines(process_vector);
        String resultsFile = "Summary-Processes";

        result.scheduling_type = "Full Process If It Possible";
        result.scheduling_name = "Earliest Deadline First";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
            sProcess process = process_vector.get(current_process);
            process.start_time = 1;
            out.println("Process: " + current_process + " registered... (" + process.cpu_time + " " +
                    process.io_blocking + " " + process.cpu_done + " " + comp_time + " " +
                    process.deadline + ")");
            while (comp_time < runtime) {
                if (hasNotFinishedProcesses(process_vector)) {
                    if (process.cpu_done == process.cpu_time) {
                        process.finish = true;
                        process.finish_time = comp_time;
                        out.println("Process: " + current_process + " completed... (" + process.cpu_time + " " +
                                process.io_blocking + " " + process.cpu_done + " " + comp_time + " " +
                                process.deadline + ")");
                        current_process = indexEarliestDeadline(process_vector);
                        process = process_vector.get(current_process);
                        if (process.start_time == 0)
                            process.start_time = comp_time;
                        out.println("Process: " + current_process + " registered... (" + process.cpu_time + " " +
                                process.io_blocking + " " + process.cpu_done + " " + comp_time + " " +
                                process.deadline + ")");
                    }
                    if (process.io_blocking == process.io_next) {
                        out.println("Process: " + current_process + " I/O blocked... (" + process.cpu_time + " " +
                                process.io_blocking + " " + process.cpu_done + " " + comp_time + " " +
                                process.deadline + ")");
                        process.num_blocked++;
                        process.io_next = 0;
                        current_process = indexEarliestDeadline(process_vector);
                        process = process_vector.get(current_process);
                        if (process.start_time == 0)
                            process.start_time = comp_time;
                        out.println("Process: " + current_process + " registered... (" + process.cpu_time + " " +
                                process.io_blocking + " " + process.cpu_done + " " + comp_time + " " +
                                process.deadline + ")");
                    }
                    process.cpu_done++;
                    if (process.io_blocking > 0) {
                        process.io_next++;
                    }
                }
                comp_time++;
                for (int i = 0; i < arrival_time.size(); i++) {
                    if (comp_time % arrival_time.get(i) == 0) {
                        int deadline = ((comp_time / arrival_time.get(i)) + 1) * arrival_time.get(i);
                        if (deadline < runtime) {
                            sProcess p = new sProcess(process_vector.get(i).cpu_time, process_vector.get(i).io_blocking,
                                    0, 0, 0, deadline);
                            process_vector.add(p);
                            {
                                sProcess new_process = process_vector.get(process_vector.size() - 1);
                                if (process.finish) {
                                    process = new_process;
                                    process.start_time = comp_time;
                                } else if (process.deadline > new_process.deadline) {
                                    process.io_next = 0;
                                    process.num_blocked++;
                                    process = new_process;
                                    process.start_time = comp_time;
                                }
                            }
                        }
                    }
                }
            }
            out.close();
        } catch (IOException e) { /* Handle exceptions */ }
        result.compu_time = comp_time;
        return result;
    }

    public static Results RunBatch(int runtime, List<sProcess> process_vector, Results result) {
        int comp_time = 0;
        int current_process = indexEarliestDeadline(process_vector);
        int previous_process = 0;
        int completed = 0;
        List<Integer> arrival_time = getDeadlines(process_vector);
        String resultsFile = "Summary-Processes";

        result.scheduling_type = "Batch (Nonpreemptive)";
        result.scheduling_name = "Earliest Deadline First";
        try {
            PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
            sProcess process = process_vector.get(current_process);
            process.start_time = 1;
            out.println("Process: " + current_process + " registered... (" + process.cpu_time + " " +
                    process.io_blocking + " " + process.cpu_done + " " + comp_time + " " +
                    process.deadline + ")");
            while (comp_time < runtime) {
                if (hasNotFinishedProcesses(process_vector)) {
                    if (process.cpu_done == process.cpu_time) {
                        completed++;
                        process.finish = true;
                        process.finish_time = comp_time;
                        out.println("Process: " + current_process + " completed... (" + process.cpu_time + " " +
                                process.io_blocking + " " + process.cpu_done + " " + comp_time + " " +
                                process.deadline + ")");
                    /*if (completed == process_vector.size()) {
                        result.compuTime = comp_time;
                        out.close();
                        return result;
                    }*/
                        current_process = indexEarliestDeadline(process_vector);
                        process = process_vector.get(current_process);
                        if (process.start_time == 0)
                            process.start_time = comp_time;
                        out.println("Process: " + current_process + " registered... (" + process.cpu_time + " " +
                                process.io_blocking + " " + process.cpu_done + " " + comp_time + " " +
                                process.deadline + ")");
                    }
                    if (process.io_blocking == process.io_next) {
                        out.println("Process: " + current_process + " I/O blocked... (" + process.cpu_time + " " +
                                process.io_blocking + " " + process.cpu_done + " " + comp_time + " " +
                                process.deadline + ")");
                        process.num_blocked++;
                        process.io_next = 0;
                        if (completed < process_vector.size()-1) {
                            previous_process = current_process;
                            current_process = indexEarliestDeadline(process_vector, previous_process);
                        }
                        else{
                            current_process = indexEarliestDeadline(process_vector);
                        }
                        process = process_vector.get(current_process);
                        if (process.start_time == 0)
                            process.start_time = comp_time;
                        out.println("Process: " + current_process + " registered... (" + process.cpu_time + " " +
                                process.io_blocking + " " + process.cpu_done + " " + comp_time + " " +
                                process.deadline + ")");
                    }
                    process.cpu_done++;
                    if (process.io_blocking > 0) {
                        process.io_next++;
                    }
                }
                comp_time++;
                for(int i = 0; i < arrival_time.size(); i++){
                    if (comp_time % arrival_time.get(i) == 0) {
                        int deadline = ((comp_time / arrival_time.get(i)) + 1) * arrival_time.get(i);
                        if (deadline < runtime) {
                            sProcess p = new sProcess(process_vector.get(i).cpu_time, process_vector.get(i).io_blocking,
                                    0, 0, 0, deadline);
                            process_vector.add(p);
                            {
                                sProcess new_process = process_vector.get(process_vector.size()-1);
                                if (process.finish) {
                                    process = new_process;
                                    process.start_time = comp_time;
                                } else if (process.deadline > new_process.deadline) {
                                    process.io_next = 0;
                                    process.num_blocked++;
                                    process = new_process;
                                    process.start_time = comp_time;
                                }
                            }
                        }
                    }
                }
            }
            out.close();
        } catch (IOException e) { /* Handle exceptions */ }
        result.compu_time = comp_time;
        return result;
    }
}
