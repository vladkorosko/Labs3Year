#include <windows.h>
#include <stdio.h>
#include <iostream>
#include <tchar.h>
#include <sstream>
#include <conio.h>

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

void communication(PROCESS_INFORMATION pi_f, PROCESS_INFORMATION pi_g)
{
	time_t start = clock();
	Sleep(1);
	time_t time_calculation = 0;
	
	int f_number = 0, g_number = 0;
	bool get_f_number = false;
	bool get_g_number = false;

	string f_result = "No answer from f";
	string g_result = "No answer from g";

	bool ready_f = false;
	bool ready_g = false;

	MSG message;

	while (!ready_f || !ready_g)
	{
		time_t finish = clock();
		if (PeekMessage(&message, NULL, 0, 0, PM_REMOVE))
		{
			SuspendThread(pi_f.hThread);
			SuspendThread(pi_g.hThread);
			finish = clock();
			if (message.lParam % 2 == 1 && !ready_g)
			{
				if (message.lParam == 1)
				{
					g_result = "Get the result after " + to_string(message.wParam)
						+ " attempt(s).\nThe result is " + to_string(message.message-2)
						+ ".\nTime of calculating : " + to_string(finish - start + time_calculation);
					g_number = message.message - 2;
					get_g_number = true;
					ready_g = true;
				}
				else if (message.lParam == 3)
				{
					g_result = "Hard fail after " + to_string(message.wParam)
						+ " attempt(s).\nTime of calculating : " + to_string(finish - start + time_calculation);
					ready_g = true;
				}
				else
				{
					g_result = "Soft fail after " + to_string(message.wParam)
						+ " attempt(s).\nTime of calculating : " + to_string(finish - start + time_calculation);
				}
			}
			else if (message.lParam % 2 == 0 && !ready_f)
			{
				if (message.lParam == 0)
				{
					f_result = "Get the result after " + to_string(message.wParam)
						+ " attempt(s).\nThe result is " + to_string(message.message-2)
						+ ".\nTime of calculating : " + to_string(finish - start + time_calculation);
					f_number = message.message - 2;
					get_f_number = true;
					ready_f = true;
				}
				else if (message.lParam == 2)
				{
					f_result = "Hard fail after " + to_string(message.wParam)
						+ " attempt(s).\nTime of calculating : " + to_string(finish - start + time_calculation);
					ready_f = true;
				}
				else
				{
					f_result = "Soft fail after " + to_string(message.wParam)
						+ " attempt(s).\nTime of calculating : " + to_string(finish - start + time_calculation);
				}
			}

			ResumeThread(pi_f.hThread);
			ResumeThread(pi_g.hThread);
		}
		else if ((finish - start) % 5000 == 0)
		{
			SuspendThread(pi_f.hThread);
			SuspendThread(pi_g.hThread);
			cout << "Press esc to abort calculating." << endl;
			cout << "Press any other key to continue." << endl << endl;
			if (_getch() == 27)
			{
				cout << "The programm has ended work. The result is FAILED." << endl;
				break;
			}
			else cout << "The program continues to work" << endl << endl;
			ResumeThread(pi_f.hThread);
			ResumeThread(pi_g.hThread);
			time_calculation += finish - start;
			start = clock();
			finish = clock();
			Sleep(1);
		}
	}

	if (get_f_number && get_g_number)
	{
		cout << "The result of f(x) && g(x) = " << (bool)(f_number && g_number) << endl << endl;
	}
	else
	{
		cout << "f: " << f_result << endl << endl;
		cout << "g: " << g_result << endl << endl;
	}
}

int main()
{
	while (true)
	{
		cout << "Press any key to start program" << endl;
		cout << "Press esc to finish program" << endl << endl;
		if (_getch() == 27)
		{
			return 0;
		}
		else {
			int x = getParametrs();
			cout << endl << endl;

			STARTUPINFO si_f;
			STARTUPINFO si_g;
			PROCESS_INFORMATION pi_f;
			PROCESS_INFORMATION pi_g;

			ZeroMemory(&si_f, sizeof(si_f));
			si_f.cb = sizeof(si_f);
			ZeroMemory(&pi_f, sizeof(pi_f));

			ZeroMemory(&si_g, sizeof(si_g));
			si_g.cb = sizeof(si_g);
			ZeroMemory(&pi_g, sizeof(pi_g));

			// Start the child process. 
			if (!CreateProcess("../../calculation_f/Debug/calculation_f.exe",
				NULL, NULL, NULL, FALSE, 0, NULL, NULL, &si_f, &pi_f))
			{
				cout << "CreateProcess failed (" << GetLastError() << ")" << endl;
				continue;
			}

			if (!CreateProcess("../../calculation_g/Debug/calculation_g.exe",
				NULL, NULL, NULL, FALSE, 0, NULL, NULL, &si_g, &pi_g))
			{
				cout << "CreateProcess failed (" << GetLastError() << ")" << endl;
				continue;
			}

			Sleep(10);

			if (!PostThreadMessage(GetThreadId(pi_f.hThread), NULL, GetCurrentThreadId(), x))
			{
				cout << "PostThreadMessage failed (" << GetLastError() << ")" << endl;
				continue;
			}

			if (!PostThreadMessage(GetThreadId(pi_g.hThread), NULL, GetCurrentThreadId(), x))
			{
				cout << "PostThreadMessage failed (" << GetLastError() << ")" << endl;
				continue;
			}

			communication(pi_f, pi_g);

			// Close process and thread handles. 
			TerminateProcess(pi_f.hProcess, NULL);
			TerminateProcess(pi_g.hProcess, NULL);
			CloseHandle(pi_f.hProcess);
			CloseHandle(pi_g.hProcess);
			CloseHandle(pi_f.hThread);
			CloseHandle(pi_g.hThread);
		}
	}
}