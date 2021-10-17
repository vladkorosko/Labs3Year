#include <iostream>
#include <sstream>
#include <Windows.h>

#include "./../../testing/trialfuncs.hpp"

using namespace std;

long long getResult(const string& answer)
{
	long long result = 0;
	for (size_t i = 4; i < answer.size() - 1; i++)
		result = result * 10 + (answer[i] - '0');
	return result;
}

int main()
{
	//cout << "Calculation g(x) started" << endl << endl;
	MSG a;
	while (!PeekMessage(&a, NULL, 0, 0, PM_REMOVE)) {};
	int x = a.lParam;
	DWORD ui_process_thread_id = a.wParam;
	//cout << "Get message: " << x << endl << endl;
	//cout << "The sender is: " << ui_process_thread_id << endl << endl;
	stringstream s;
	int n = 1;
	while (n < 100)
	{
		s << os::lab1::compfuncs::trial_g<os::lab1::compfuncs::AND>(x);
		if (s.str() == "hard fail")
		{
			PostThreadMessage(ui_process_thread_id, NULL, n, 3);
			return 0;
		}
		else if (s.str() != "soft fail")
		{
			if (!PostThreadMessage(ui_process_thread_id, getResult(s.str()) + 2, n, 1))
			{
				cout << "PostMessage failed " << GetLastError() << endl;
			}
			return 0;
		}
		PostThreadMessage(ui_process_thread_id, NULL, n, 5);
		n++;
		s.clear();
	}
	cout << "Calculation g finished" << endl << endl;
	PostThreadMessage(ui_process_thread_id, NULL, n, 2);

	return 0;
}