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

long long getResult(const string& answer)
{
	long long result = 0;
	for (size_t i = 4; i < answer.size() - 1; i++)
		result = result * 10 + (answer[i] - '0');
	return result;
}

void calculateF(int x, DWORD ui_thread)
{
	stringstream s;
	int n = 1;
	while (n < 100)
	{
		s << os::lab1::compfuncs::trial_f<os::lab1::compfuncs::AND>(x);
		if (s.str() == "hard fail")
		{
			PostThreadMessage(ui_thread, NULL, n, 2);
			break;
		}
		else if (s.str() != "soft fail")
		{
			PostThreadMessage(ui_thread, getResult(s.str()), n, 0);
			break;
		}
		PostThreadMessage(ui_thread, NULL, n, 4);
		n++;
		s.clear();
	}
	PostThreadMessage(ui_thread, NULL, n, 2);
}

void calculateG(int x, DWORD ui_thread) 
{
	stringstream s;
	int n = 1;
	while (n < 100)
	{
		s << os::lab1::compfuncs::trial_g<os::lab1::compfuncs::AND>(x);
		if (s.str() == "hard fail")
		{
			PostThreadMessage(ui_thread, NULL, n, 3);
			break;
		}
		else if (s.str() != "soft fail")
		{
			PostThreadMessage(ui_thread, getResult(s.str()), n, 1);
			break;
		}
		PostThreadMessage(ui_thread, NULL, n, 5);
		n++;
		s.clear();
	}
	PostThreadMessage(ui_thread, NULL, n, 3);
}

void communication()
{
	time_t start = clock();
	time_t finish = clock();
	Sleep(1);

	string f_result = "No answer from f";
	string g_result = "No answer from g";

	bool ready_f = false;
	bool ready_g = false;

	MSG* message = new MSG();
	
	while (!ready_f || !ready_g)
	{
		time_t interval = clock();
		if (PeekMessage(message, NULL, 0, 0, PM_REMOVE))
		{
			
			finish = clock();
			if (message->lParam % 2 == 1 && !ready_g)
			{
				if (message->lParam == 1)
				{
					g_result = "Get the result after " + to_string(message->wParam)
						+ " attempt(s).\nThe result is " + to_string(message->message)
						+ ".\nTime of calculating : " + to_string(finish - start);
					ready_g = true;
				}
				else if (message->lParam == 3)
				{
					g_result = "Hard fail after " + to_string(message->wParam)
						+ " attempt(s).\nTime of calculating : " + to_string(finish - start);
					ready_g = true;
				}
				else
				{
					g_result = "Soft fail after " + to_string(message->wParam)
						+ " attempt(s).\nTime of calculating : " + to_string(finish - start);
				}
			}
			else if(message->lParam%2 == 0 && !ready_f)
			{
				if (message->lParam == 0)
				{
					f_result = "Get the result after " + to_string(message->wParam)
						+ " attempt(s).\nThe result is " + to_string(message->message)
						+ ".\nTime of calculating : " + to_string(finish - start);
					ready_f = true;
				}
				else if (message->lParam == 2)
				{
					f_result = "Hard fail after " + to_string(message->wParam) 
						+ " attempt(s).\nTime of calculating : " + to_string(finish - start);
					ready_f = true;
				}
				else
				{
					f_result = "Soft fail after " + to_string(message->wParam)
						+ " attempt(s).\nTime of calculating : " + to_string(finish - start);
				}
			}
		}
		else if ((interval - start) % 5000 == 0)
		{
			cout << "Press esc to abort calculating." << endl;
			cout << "Press any other key to continue." << endl << endl;
			if (_getch() == 27)
			{
				finish = clock();
				cout << "The programm has ended work" << endl << endl;
				break;
			}
			else cout << "The program continues to work" << endl << endl;
		}
	}
	cout << "f: " << f_result << endl << endl;
	cout << "g: " << g_result << endl << endl;
}

void userInterface()
{
	int x = getParametrs();
	cout << endl << endl;
	
	thread* waiting = new thread(communication);

	DWORD ui_thread = GetThreadId(waiting->native_handle());

	thread* calculation_f = new thread(calculateF, x, ui_thread);
	thread* calculation_g = new thread(calculateG, x, ui_thread);

	calculation_f->join();
	calculation_g->join();
	waiting->join();
	
	delete calculation_f;
	delete calculation_g;
	delete waiting;
}
