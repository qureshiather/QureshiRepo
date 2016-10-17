#include <iostream>
#include <string>
#include <time.h>

using namespace std;

//Parent Class
class Person
{

    public:
        string firstName;
        string lastName;
        string password;
        int id;

    Person(){
        id++;
    }

    void printName(){
        cout << firstName + " " + lastName << endl;
    }

    void printID(){
        cout << id << endl;
    }

    void setPass(string newPass){
        password = newPass;
    }

    bool authenticate(int currID, string currPass){
        if(currID == id && (!currPass.compare(password)))
            return true;
        else
            return false;
    }
};

//Derived class
//Customer IS A person
class Customer: public Person
{
    public:
        float checkingAccount;
        float savingsAccount;

        //constructor initialize
        Customer(){
            checkingAccount = 0;
            savingsAccount = 0;
            id++;
        }

        void deposit(float amount, string accountType){
            if(!accountType.compare("check"))
                checkingAccount = checkingAccount + amount;
            else if (!accountType.compare("saving"))
                savingsAccount = savingsAccount + amount;
            else
                cout << "You entered an invalid account type" << endl;
        }

        bool withdraw(float amount, string accountType){
            if(!accountType.compare("check")){
                if(amount > checkingAccount){
                    cout << "you have insufficient funds" << endl;
                    return false;
                }
                else{
                    checkingAccount -= amount;
                    return true;
                }
            }
            else if(!accountType.compare("saving")){
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
            if (!accountType.compare("check")){
                cout << "Checking: " << checkingAccount << endl;
            }
            else if (!accountType.compare("saving")){
                cout << "Savings: " <<savingsAccount << endl;
            }
            else{
                cout << "You entered an invalid account type " << endl;
            }
        }

};

//derived class
//maintenancePerson is a Person
class MaintenancePerson: public Person
{
    public:
        string password;

   MaintenancePerson(string newPass){
       password = newPass;
   }
};

/*
has managerial powers to open and close an account and see the critical
details of a particular, or all (at once), customers in a formatted display.

Manager IS A Person
 */
class Manager: public Person
{
    public:
        //will close accounts of customerAccount highlighted in Parameter
        bool accountClose(int IDofCustomerToDelete){
        }

        //will printout balances of customers stored in the array
        void printbalances(string customers[]){
        }


};



//BANK INFO
Person totalCust[20];

int main(){

    char ch;

    //CHECK WHAT TYPE OF PERSON

    //Manager/Customer/Maintenenance

    do{
        cout<<"\n\n\n\tMAIN MENU: SELECT YOUR ACCOUNT TYPE";
        cout<<"\n\n\t01. Main Menu";
        cout<<"\n\n\t01. Customer";
        cout<<"\n\n\t02. Manager";
        cout<<"\n\n\t03. Maintenance";
        cout<<"\n\n\t04. Exit \n";
        cout << "Enter your Input: " << endl;

        cin >> ch;
        switch(ch){
            case '1':
                cout << "Return to Main Menu...";
                break;
            case '4':
                cout << "exiting...";
                break;

            //Now we will go into specific account type
            default:
                cout << "EnterID" << endl;
                int ID;
                cin >> ID;
                cout << "EnterPass" << endl;
                string password;
                cin >> password;
                break;
        }



    }
    while(ch != '4');
        return 0;
}
