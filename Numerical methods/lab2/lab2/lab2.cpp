#include <iostream>
#include <vector>
#include <iomanip>

using namespace std;

void printM(const vector<vector<double>>& A_matrix)
{
    for (auto i : A_matrix)
    {
        for (auto j : i)
            cout << setw(10) << setprecision(3) << j << ' ';
        cout << endl;
    }
}

vector<double> GaussMethod(vector<vector<double>>& A_matrix)
{
    for (size_t i = 0; i < A_matrix.size(); i++)
    {
        for (size_t j = i + 1; j < A_matrix.size() + 1; j++)
        {
            A_matrix[i][j] = A_matrix[i][j] / A_matrix[i][i];
            for (size_t k = i + 1; k < A_matrix.size(); k++)
            {
                A_matrix[k][j] = A_matrix[k][j] - A_matrix[k][i] * A_matrix[i][j];
            }
        }
        A_matrix[i][i] = 1;
        for (size_t k = i + 1; k < A_matrix.size(); k++)
        {
            A_matrix[k][i] = 0;
        }
        cout << "Step = " << i+1 << endl;
        printM(A_matrix);
        cout << endl;
    }
    vector<double> result(A_matrix.size());
    
    for (int i = static_cast<int>(A_matrix.size()) - 1; i >= 0; i--)
    {
        result[i] = A_matrix[i][A_matrix.size()];
        for (size_t j = i + 1; j < A_matrix.size(); j++)
        {
            result[i] -= A_matrix[i][j] * result[j];
        }
    }
    return result;
}

vector<double> progMethod(const vector<double>& a, const vector<double>& b,
                          const vector<double>& c, const vector<double>& f) {
    vector<double> alpha;
    vector<double> beta;
    vector<double> z;
    alpha.push_back(b[0] / c[0] * (-1));
    beta.push_back(f[0] / c[0]);
    for (size_t i = 1; i < b.size(); i++) {
        z.push_back(c[i] + alpha[alpha.size() - 1] * a[i - 1]);
        alpha.push_back((-1) * b[i] / z[z.size() - 1]);
        double temp = f[i] - a[i - 1] * beta[beta.size() - 1];
        beta.push_back(temp / z[z.size() - 1]);
    }
    vector<double> result(f.size());
    double temp1 = (f[f.size() - 1] - a[a.size() - 1] * beta[beta.size() - 1]);
    double temp2 = c[c.size() - 1] + alpha[alpha.size() - 1] * a[a.size() - 1];
    result[result.size() - 1] = temp1 / temp2;
    for (int i = result.size() - 2; i >= 0; i--) {
        result[i] = alpha[i] * result[i + 1] + beta[i];
    }
    return result;
}


vector<double> zedellMethod(const vector<vector<double>>& a_matrix, const vector<double>& b)
{
    vector<double> result(b.size());
    int n = 1;
    while (n <= 20)
    {
        for (size_t i = 0; i < b.size(); i++)
        {
            result[i] = b[i];
            for (size_t j = 0; j < b.size(); j++)
                if (j != i)
                    result[i] -= a_matrix[i][j] * result[j];
            result[i] /= a_matrix[i][i];
        }
        cout << "Step " << n << ":\n";
        for (size_t i = 0; i < result.size(); i++)
            cout << "X" << i + 1 << " = " << setw(6) << setprecision(5) << result[i] << ";\n";
        n++;
    }
    return result;
}

vector<double> simpleIterationMethod(const vector<vector<double>>& a_matrix, const vector<double>& b)
{
    vector<double> result(b.size(), 0.0);
    int n = 1;
    while (n <= 20)
    {
        vector<double> result1 = result;
        for (size_t i = 0; i < b.size(); i++)
        {
            result1[i] = b[i];
            for (size_t j = 0; j < b.size(); j++)
                if (j != i)
                    result1[i] -= a_matrix[i][j] * result[j];
            result1[i] /= a_matrix[i][i];
        }
        result = result1;
        cout << "Step " << n << ":\n";
        for (size_t i = 0; i < result.size(); i++)
            cout << "X" << i + 1 << " = " << setw(6) << setprecision(5) << result[i] << ";\n";
        n++;
    }
    return result;
}

int main()
{
    {
        cout << "Task1: Gauss method" << endl;
        vector<vector<double>> task1 = { {5.0, 2.0, 1.0, 0.0, 15.0},
                                        {1.0, 3.0, 2.0, 8.0, 58.0},
                                        {4.0, -6.0, 1.0, 0.0, -10.0},
                                        {5.0, 0.0, 3.0, 2.0, 27.0} };
        vector<double> rs = GaussMethod(task1);
        cout << endl;
        for (size_t i = 0; i < rs.size(); i++)
            cout << "X" << i + 1 << " = " << rs[i] << ";\n";
        cout << endl << endl;
    }
    {
        cout << "Method for 3 diagonal matrix" << endl;
        vector<double> a = { 4,5 };
        vector<double> b = { 4,5 };
        vector<double> c = { 2,1,2 };
        vector<double> f = { 18,33,30 };
        vector<double> rs = progMethod(a, b, c, f);
        for (size_t i = 0; i < rs.size(); i++)
            cout << "X" << i + 1 << " = " << rs[i] << ";\n";
        cout << endl << endl;
    }
    {
        cout << "Zedell method" << endl;
        vector<vector<double>> a_matrix = { {6,3,1,0},
                                            {3,5,0,2},
                                            {1,0,3,1},
                                            {0,2,1,5} };
        vector<double> b = { 25,31,19,35 };
        vector<double> rs = zedellMethod(a_matrix, b);
        cout << endl;
    }
    {
        cout << "Simple iteration method" << endl;
        vector<vector<double>> a_matrix = { {4,0,1,0},
                                            {0,3,0,2},
                                            {1,0,5,1},
                                            {0,2,1,4} };
        vector<double> b = { 12,19,27,30 };
        vector<double> rs = simpleIterationMethod(a_matrix, b);
    }
    
    return 0;

}

// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started: 
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file
