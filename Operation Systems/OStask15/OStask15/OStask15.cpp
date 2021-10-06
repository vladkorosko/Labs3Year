#include "functional.h"

using namespace std;

void f(bool* res)
{
    while (res)
    {
        cout << 1;
    }
}

void g(bool* res)
{
    Sleep(1000);
    *res = false;
}

int main()
{/*
    bool* res = new bool(true);
    thread a = thread(f, res);
    thread b = thread(g, res);
    a.join();
    b.join();*/
    userInterface();
    return 0;
}
