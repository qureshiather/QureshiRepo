#include <iostream>
#include <string>

using namespace std;

class Person
{
	public:
		string firstName;
		string lastName;
};

//Derived class
class Customer: public Person
{
	public:
		float CheckingAccount;
		float SavingsAccount;
};

int main(){
	Person test;
	cout << "Enter Your Name: " << endl;
	getline(cin, test.firstName);
	cout << test.firstName << endl;
	return 0;
}

