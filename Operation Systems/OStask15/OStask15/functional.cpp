#include "functional.h"

using namespace std;

int enterNumber(const string& number)
{
	istringstream date_stream(number);
	bool ok = true;
	int new_number;
	ok = ok && (date_stream >> new_number);
	ok = ok && date_stream.eof();

	if (!ok)
	{
		throw logic_error("Wrong number format: " + number);
	}
	return new_number;
}

int getParametrs()
{
	while (true)
	{
		cout << "Enter number" << endl;
		string line;
		getline(cin, line);
		try
		{
			return enterNumber(line);
		}
		catch (exception e)
		{
			cout << e.what() << endl;
			cout << "Try again." << endl << endl;
		}
	}
}

void calculate(int x, int& result, mutex* m, bool* ready) 
{
	result += x;
	MSG message;
	while (true)
	{
		//Sleep(2000 + result*1000);
		cout << "Thread " << result << " are ready" << endl;
		if (PeekMessage(&message, NULL, 0, 0,0 ))
			break;
	}
	{
		lock_guard<mutex> lock(*m);
		*ready = true;
	}
}

void communication(mutex* m, bool* ready1, bool* ready2, DWORD f_thread, DWORD g_thread)
{
	time_t start = clock();
	Sleep(1);
	while (!*ready1 || !*ready2)
	{
		time_t interval = clock();
		if ((interval - start) % 5000 == 0)
		{
			cout << "Press esc to abort calculating." << endl;
			cout << "Press any other key to continue." << endl;
			if (_getch() == 27)
			{
				PostThreadMessage(f_thread, NULL, NULL, NULL);
				PostThreadMessage(g_thread, NULL, NULL, NULL);
				break;
			}
		}
	}
	time_t finish = clock();
	cout << "Time of calculating: " << finish - start << endl;
}

void userInterface()
{
	mutex* m = new mutex;
	bool* ready_f = new bool(false);
	bool* ready_g = new bool(false);
	int x = getParametrs();
	int res1 = 1;
	int res2 = 2;
	
	thread* calc1 = new thread(calculate, x, ref(res1), m, ready_f);
	thread* calc2 = new thread(calculate, x, ref(res2), m, ready_g);
	DWORD function_f = GetThreadId(calc1->native_handle());
	DWORD function_g = GetThreadId(calc2->native_handle());

	thread* waiting = new thread(communication, m, ready_f, ready_g, function_f, function_g);
	
	calc1->join();
	calc2->join();
	waiting->join();
	
	delete calc1;
	delete calc2;
	delete waiting;
	delete m;
	delete ready_f;
	delete ready_g;
}