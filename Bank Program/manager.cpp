#include "bank.h"

/*
has managerial powers to open and close an account and see the critical
details of a particular, or all (at once), customers in a formatted display.

Manager IS A Person
 */

class Manager: public Person
{
public:
    //will close accounts of customerAccount highlighted in Parameter
    //will only close account if account has balance 0
    bool accountClose(Customer* cust, string account){

        //CHECKING
        if(!account.compare("check")){
            if(cust->getCheck() == -1){
                cout << "account is already closed!";
                return false;
            }
            else if(cust->getCheck() != 0){
                cout << "Account does not have 0 balance" << endl;
                return false;
            }
            else{
                cust->setCheck(-1);
                cout << "Checking Account closed" << endl;
                return true;
            }
        }

        //SAVING
        if(!account.compare("saving")){
            if(cust->getSave() == -1){
                cout << "account is already closed!";
                return false;
            }
            else if(cust->getSave() != 0){
                cout << "Account does not have 0 balance" << endl;
                return false;
            }
            else{
                cust->setSave(-1);
                cout << "Savings Account closed" << endl;
                return true;
            }
        }

        return false;
    }

    bool accountOpen(Customer* cust, string account){
        if(!account.compare("check")){
            if(cust->getCheck() == -1){
                cust->setCheck(0);
                cout << "Checking Account Opened!" << endl;
                return true;
            }
            else{
                cout << "Account is already open!" << endl;
                return false;
            }
        }

        else if(!account.compare("saving")){
            if(cust->getSave() == -1){
                cust->setSave(0);
                cout << "Savings Account Opened!" << endl;
                return true;
            }
            else{
                cout << "Account is already open!" << endl;
                return false;
            }
        }

        else{
            cout << "Entered Wrong Account Type" << endl;
            return false;
        }
    }

    //will printout balances of customer
    void printbalance(Customer* cust){
        cout << "\n Checking: " << cust->getCheck();
        cout << "\n Savings: " << cust->getSave() << endl;
    }


};
