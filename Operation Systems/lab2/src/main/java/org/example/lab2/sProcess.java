package org.example.lab2;

/*
Клас Process у скачаній версії програми не використовувався, замість нього був ідентичний sProcess
Зараз в основну алгоритмі (Scheduling) використовується Process;
sProcess я переробив під свій алгоритм (MyScheduling).
 */

public class sProcess extends Process {
    public int deadline;
    public int start_time;
    public int finish_time;
    public boolean finish = false;

    public sProcess (int cpu_time, int io_blocking, int cpu_done, int io_next, int num_blocked, int deadline) {
        super(cpu_time, io_blocking, cpu_done, io_next, num_blocked);
        this.deadline = deadline;
        this.start_time = 0;
        this.finish_time = 0;
    }
}
