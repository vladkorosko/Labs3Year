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

void calculate(int x, int& result, mutex* m, DWORD ui_thread) 
{
	result += x;
	while (true)
	{
		Sleep(10000);
		cout << "Thread " << result << " are ready" << endl;
		PostThreadMessage(ui_thread, result, NULL, NULL);
		break;
	}
	{
		lock_guard<mutex> lock(*m);
		result = 100;
	}
}

void communication(mutex* m, int& result)
{
	time_t start = clock();
	time_t finish = clock();
	Sleep(1);
	MSG* message = new MSG();
	while (true)
	{
		time_t interval = clock();
		if (PeekMessage(message, NULL, 0, 0, NULL))
		{
			finish = clock();
			cout << message->message << endl;
			break;
		}
		else if ((interval - start) % 5000 == 0)
		{
			cout << "Press esc to abort calculating." << endl;
			cout << "Press any other key to continue." << endl;
			if (_getch() == 27)
			{
				finish = clock();
				cout << "The programm has ended work" << endl;
				break;
			}
			else cout << "Åhe program continues to work" << endl;
		}
	}
	cout << "Time of calculating: " << finish - start << endl;
}

void userInterface()
{
	mutex* m = new mutex;
	bool* ready_f = new bool(false);
	bool* ready_g = new bool(false);
	int x = getParametrs();
	int res = 1;
	
	thread* waiting = new thread(communication, m, ref(res));

	DWORD ui_thread = GetThreadId(waiting->native_handle());

	thread* calculation = new thread(calculate, x, ref(res), m, ui_thread);

	calculation->join();
	waiting->join();
	
	delete calculation;
	delete waiting;
	delete m;
	delete ready_f;
	delete ready_g;
}