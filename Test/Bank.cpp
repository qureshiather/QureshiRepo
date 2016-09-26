#include <iostream>
#include <string>
#include <time.h>

using namespace std;

class Person
{
	public:
		string firstName;
		string lastName;
		int id;

	void printName(){
		cout << firstName + " " + lastName << endl;
	}
		
	void printID(){
		cout << id << end1;
	}
};

//Derived class
class Customer: public Person
{
	public:
		float checkingAccount;
		float savingsAccount;

		void deposit(float amount, string accountType){
			if(accountType.compare("check")){
				checkingAccount =+ amount;
			}
			else if (accountType.compare("saving")){
				savingsAccount =+ amount;
			}
			else{
				cout << "You entered an invalid account type. Type check for chequing, and saving for savings account" << endl;
			}
		}

		bool withdraw(float amount, string accountType){
			if(accountType.compare("check")){
				if(amount > checkingAccount){
					cout << "you have insufficient funds" << endl;
					return false;
				}
				else{
					checkingAccount -= amount;
					return true;
				}
			}
			else if(accountType.compare("saving")){
				if(amount > savingsAccount){
					cout << "you have insufficient funds" << endl;
					return false;
				}
				else{
					savingsAccount -= amount;
					return true;
				}
			}
		}

		//prints out current balances to screen
		void printBalance(string accountType){
			if (accountType.compare("check")){
				cout << "Checking: " << checkingAccount << endl;
			}
			else if (accountType.compare("saving")){
				cout << "Savings: " <<savingsAccount << endl;
			}
			else{
				cout << "You entered an invalid account type. Type check for chequing, and saving for savings account" << endl;
			}
		}
};

//derived class
class maintenancePerson: public Person
{
	public:
		string ID;
		string password;
};

/*
has managerial powers to open and close an account and see the critical 
details of a particular, or all (at once), customers in a formatted display.
 */
class Manager: public Person
{
	public:
		//will close accounts of customerAccount highlighted in Parameter 
		bool accountClose(Customer toDelete){
		}

		//will printout balances of customers stored in the array
		void printbalances(string customers[]){
		}


};

int main(){
	Customer test;
	cout << "Enter Your firstName: " << endl;
	getline(cin, test.firstName);
	cout << "Enter Your lastName: " << endl;
	getline(cin, test.lastName);

	cout << "Enter Checking Balance: " << endl;
	cin >> test.checkingAccount;
	cout << "Enter Savings Account " << endl;
	cin >> test.savingsAccount;
	test.printBalance("check");

	return 0;
}
