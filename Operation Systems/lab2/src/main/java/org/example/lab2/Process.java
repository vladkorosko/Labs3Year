package org.example.lab2;

public class Process {
    public int cpu_time;
    public int io_blocking;
    public int cpu_done;
    public int io_next;
    public int num_blocked;

    public Process(int cpu_time, int io_blocking, int cpu_done, int io_next, int num_blocked) {
        this.cpu_time = cpu_time;
        this.io_blocking = io_blocking;
        this.cpu_done = cpu_done;
        this.io_next = io_next;
        this.num_blocked = num_blocked;
    }
}
