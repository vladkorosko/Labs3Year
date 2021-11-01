package org.example.lab2;

import java.util.Random;

public class sProcess extends Process {
    public int deadline;
    public boolean finish = false;

    public sProcess (int cpu_time, int io_blocking, int cpu_done, int io_next, int num_blocked, int deadline) {
        super(cpu_time, io_blocking, cpu_done, io_next, num_blocked);
        this.deadline = deadline;
    }
}
