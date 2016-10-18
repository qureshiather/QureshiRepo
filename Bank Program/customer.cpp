#include "bank.h"

class Customer: public Person {

private:
    float checkingAccount;
    float savingsAccount;
    bool belowThresholdCheck;

public:

    Customer(){
        checkingAccount = -1;
        savingsAccount = -1;
        belowThresholdCheck = false;
    }

    void setCheck(float newCheck){
        checkingAccount = newCheck;
    }

    float getCheck(){
        return checkingAccount;
    }

    void setSave(float newSave){
        savingsAccount = newSave;
    }

    float getSave(){
        return savingsAccount;
    }

    void deposit(float amount, string accountType){

        //CHECKING ACCOUNT
        if(!accountType.compare("check")){
            if(checkingAccount == -1){
                cout << "Checking account is closed!" << endl;
                return;
            }
            else if(amount + checkingAccount >= 1000 && belowThresholdCheck == true){
                checkingAccount = checkingAccount + amount;
                belowThresholdCheck = false;
                return;
            }
            else{
                checkingAccount += amount;
                return;
            }
        }

        //SAVINGS ACCOUNT
        else if (!accountType.compare("saving")){
            if(savingsAccount == -1){
                cout << "Savings account is closed!" << endl;
                return;
            }
            else{
                savingsAccount += amount;
                return;
            }
        }
        else{
            cout << "You entered an invalid account type" << endl;
            return;
        }
    }


    bool withdraw(float amount, string accountType){

        //CHECKING ACCOUNT
        if(!accountType.compare("check")){
            if(amount > checkingAccount){
                cout << "\nyou have insufficient funds\n" << endl;
                return false;
            }
            else if(checkingAccount - amount <= 1000 && belowThresholdCheck == false){
                cout << "\n WARNING: This transaction will result in a checking balance under $1000" << endl;
                cout << "\n a 2$ fee will be charged if you wish to continue (y/n)\n" << endl;
                char ch2;
                cin >> ch2;
                if (ch2 == 'n'){
                    return false;
                }
                else if (ch2 == 'y'){
                    checkingAccount -= amount + 2;
                    belowThresholdCheck = true;
                    return true;
                }
                else{
                    cout <<"\nYou have entered an invalid key\n!" << endl;
                    return false;
                }
            }
            else{
                checkingAccount -= amount;
                return true;
            }
        }

        //SAVINGS ACCOUNT
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
        else{
            cout << "Invalid Account Type Entered" << endl;
            return false;
        }
    }

    bool transferToSavings(float amount){
        if(savingsAccount == -1){
            cout << "Savings Account is Closed" << endl;
            return false;
        }
        else if(checkingAccount == -1){
            cout << "Checking Account is Closed" << endl;
            return false;
        }
        //accounts are both open
        else{
            if(amount > checkingAccount){
                cout << "You have insufficient funds" << endl;
                printBalance("check");
                return false;
            }
            if(withdraw(amount, "check")){
                deposit(amount, "saving");
                return true;
            }
        }
        return false;
    }

    bool transferToCheck(float amount){
        if(savingsAccount == -1){
            cout << "Savings Account is Closed" << endl;
            return false;
        }
        else if(checkingAccount == -1){
            cout << "Checking Account is Closed" << endl;
            return false;
        }
        //accounts are both open
        else{
            if(amount > savingsAccount){
                cout << "You have insufficient funds" << endl;
                printBalance("saving");
                return false;
            }
            if(withdraw(amount, "saving")){
                deposit(amount, "check");
                return true;
            }
        }
        return false;
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

