#include "bank.h"

#include <iostream>
#include <fstream>\


bool checkID(int ID){
    for(int i = 0; i < arraySize; i++){
        if(cust[i].getID() == ID)
            return true;
    }
    return false;
}

int main(){

    //initialize customers


    for(int i = 0; i < arraySize ; i++){
        manage.accountOpen(&cust[i],"check");
        cust[i].setID(i);
        cust[i].deposit(1000+i*20,"check");
        cout << "ID: " << cust[i].getID() << endl;
        cout << "CH Balance: " << cust[i].getCheck() << endl;
    }

    char ch;

    //CHECK WHAT TYPE OF PERSON

    //Manager/Customer/Maintenenance

    do{
        cout<<"\n\n\tMAIN MENU: SELECT YOUR ACCOUNT TYPE";
        cout<<"\n\n\t01. Customer";
        cout<<"\n\n\t02. Manager";
        cout<<"\n\n\t03. Maintenance";
        cout<<"\n\n\tq. Exit \n\n\n";
        cout << "Enter your Input: " << endl;
        cin >> ch;
        switch(ch){

        //CUSTOMER
        case '1':
        {
            cout << "Welcome Customer! Enter Your ID" << endl;
            int theID;
            cin >> theID;
            if(!checkID(theID)){
                cout << "We have no record of you! Try Again!" << endl;
                cout << "Enter any key to Continue" << endl;
                cin >> ch;
                break;
            }
            else{
                //code to work with customer
                do{
                    cout<<"\n\n\tCUSTOMER MENU FOR ID: " << theID << endl;
                    cout<<"\n\n\t01. Deposit";
                    cout<<"\n\n\t02. Withdraw";
                    cout<<"\n\n\t03. Transfer";
                    cout<<"\n\n\t04. View Balance";
                    cout<<"\n\n\t09. Main Menu";
                    cout<<"\n\n\tq. Exit \n\n\n";
                    cout << "Enter your Input: " << endl;
                    cin >> ch;
                    switch(ch){
                    case 'q':
                        return 0;

                        //deposit
                    case '1':
                        cout << "Select Account" << endl;
                        cout<<"\n\n\t01. Checking";
                        cout<<"\n\n\t02. Saving\n\n";
                        cout << "Enter your Input: " << endl;
                        cin >> ch;
                        if (ch == '1'){
                            cout << "Enter Amount" << endl;
                            float amount;
                            cin >> amount;
                            cust[theID].deposit(amount,"check");
                        }
                        else if (ch == '2'){
                            cout << "Enter Amount" << endl;
                            float amount;
                            cin >> amount;
                            cust[theID].deposit(amount,"saving");
                        }
                        else{
                            cout << "You have pressed an invalid key!" << endl;
                        }
                        cout << "Enter any key to Continue" << endl;
                        cin >> ch;
                        break;

                        //withdraw
                    case '2':
                        cout << "Select Account" << endl;
                        cout<<"\n\n\t01. Checking";
                        cout<<"\n\n\t02. Saving\n\n";
                        cout << "Enter your Input: " << endl;
                        cin >> ch;
                        if (ch == '1'){
                            cout << "Enter Amount" << endl;
                            float amount;
                            cin >> amount;
                            cust[theID].withdraw(amount,"check");
                        }
                        else if (ch == '2'){
                            cout << "Enter Amount" << endl;
                            float amount;
                            cin >> amount;
                            cust[theID].withdraw(amount,"saving");
                        }
                        else{
                            cout << "You have pressed an invalid key!" << endl;
                        }
                        cout << "Enter any key to Continue" << endl;
                        cin >> ch;
                        break;

                        //Transfer
                    case '3':
                        cout << "Select Option" << endl;
                        cout<<"\n\n\t01. Checking to Saving";
                        cout<<"\n\n\t02. Saving to Checking";
                        cout<<"\n\n\t03. Transfer To Another Customer's Chequing\n\n";
                        cout << "Enter your Input: " << endl;
                        cin >> ch;
                        if (ch == '1'){
                            cout << "Enter Amount" << endl;
                            float amount;
                            cin >> amount;
                            cust[theID].transferToSavings(amount);
                        }
                        else if (ch == '2'){
                            cout << "Enter Amount" << endl;
                            float amount;
                            cin >> amount;
                            cust[theID].transferToCheck(amount);
                        }
                        else if (ch == '3'){
                            cout << "Enter Customer ID" << endl;
                            int id;
                            cin >> id;
                            bool check = checkID(id);
                            if (check == false){
                                cout << "You Entered an Invalid Customer!" << endl;
                                cout << "Enter any key to Continue" << endl;
                                cin >> ch;
                                break;
                            }
                            cout << "Enter Amount to Transfer from Your Chequeing Account" << endl;
                            float amount;
                            cin >> amount;
                            cust[theID].withdraw(amount, "check");
                            cust[id].deposit(amount, "check");
                        }
                        else{
                            cout << "You have pressed an invalid key!" << endl;
                        }
                        cout << "Enter any key to Continue" << endl;
                        cin >> ch;
                        break;


                        //view balance
                    case '4':
                        cout << "Checking: " << cust[theID].getCheck() << endl;
                        cout << "Savings: " << cust[theID].getSave() << endl;
                        cout << "Enter any key to Continue" << endl;
                        cin >> ch;
                        break;
                    }
                }
                while(ch != '9');
                break;
            }
            break;
        }

            //MANAGER
        case '2':
        {
            cout << "Welcome Manager! Enter Your Password" << endl;
            string enteredPass;
            cin >> enteredPass;
            if(managerPassword != enteredPass){
                cout << "You have entered an invalid pass" << endl;
                cout << "Enter any key to Continue" << endl;
                cin >> ch;
                break;
            }
            else{
                do{
                    cout<<"\n\n\tMANAGER MENU" << endl;
                    cout<<"\n\t01. Open/Close Accounts";
                    cout<<"\n\n\t02. Print Balances";
                    cout<<"\n\n\t09. Main Menu";
                    cout<<"\n\n\t0q. Exit\n\n\n";
                    cout << "Enter your Input: " << endl;
                    cin >> ch;
                    switch (ch) {

                    case 'q':
                        return 0;

                    //open/close accounts Account
                    case '1':
                    {
                        cout << "Write Customer ID" << endl;
                        int manageID;
                        cin >> manageID;
                        if(!checkID(manageID)){
                            cout << "You have entered an invalid ID!" << endl;
                            break;
                        }
                        else{
                            do{
                                cout<<"\n\n\tSELECT ACCOUNT YOU WISH TO CLOSE/OPEN FOR CUST ID: " << manageID << endl;
                                cout<<"\n\t01. Checking: Open";
                                cout<<"\n\n\t02. Checking: Close";
                                cout<<"\n\n\t03. Saving: Open";
                                cout<<"\n\n\t04. Saving: Close";
                                cout<<"\n\n\t0q. Exit\n\n\n";
                                cout << "Enter your Input: " << endl;
                                cin >> ch;
                                switch (ch) {
                                case 'q':
                                    return 0;
                                case '1':
                                    manage.accountOpen(&cust[manageID],"check");
                                    cout << "Enter any key to Continue" << endl;
                                    cin >> ch;
                                    break;
                                case '2':
                                    manage.accountClose(&cust[manageID],"check");
                                    cout << "Enter any key to Continue" << endl;
                                    cin >> ch;
                                    break;
                                case '3':
                                    manage.accountOpen(&cust[manageID],"saving");
                                    cout << "Enter any key to Continue" << endl;
                                    cin >> ch;
                                    break;
                                case '4':
                                    manage.accountClose(&cust[manageID],"saving");
                                    cout << "Enter any key to Continue" << endl;
                                    cin >> ch;
                                    break;
                                }
                            }
                            while(0);
                        }
                    }

                        //print balances
                    case '2':
                    {
                        do{
                            cout<<"\n\n\t SELECT WHAT YOU WANT TO PRINT" << endl;
                            cout<<"\n\t01. Single Customer Account";
                            cout<<"\n\n\t02. All Customer Accounts";
                            cout<<"\n\n\t03. Total Bank Funds";
                            cout<<"\n\n\t0q. Exit\n\n\n";
                            cout << "Enter your Input: " << endl;
                            cin >> ch;
                            switch(ch){
                            case 'q':
                                return 0;
                            case '1':
                                cout << "Enter Customer ID to print balance for:" << endl;
                                int newID;
                                cin >> newID;
                                if(checkID(newID)){
                                    cust[newID].printBalance("check");
                                    cust[newID].printBalance("saving");
                                    }
                                else{
                                    cout << "No matching ID for that Customer" << endl;
                                }
                                cout << "Enter any key to Continue" << endl;
                                cin >> ch;
                                break;
                            case '2':
                                for(int i = 0; i < arraySize; i++){
                                    cout << "ID: " << i << "\tChecking: " << cust[i].getCheck() << "\tSaving: " << cust[i].getSave() << endl;
                                }
                                cout << "Enter any key to Continue" << endl;
                                cin >> ch;
                                break;
                            case '3':
                                float totalBankfunds;
                                for(int i = 0; i < arraySize; i++){
                                    totalBankfunds += cust[i].getCheck() + cust[i].getSave();
                                }
                                cout << "Total Bank Funds: " << totalBankfunds << endl;
                                cout << "Enter any key to Continue" << endl;
                                cin >> ch;
                                break;
                            }
                        }
                        while(0);
                    }

                        //more cases
                    }

                }
                while(ch != '9');
                break;
            }
            break;
        }

            //MAINTENANCE
        case '3':
        {
            cout << "Welcome Maintenance! Enter Your ID" << endl;
            string enteredID;
            cin >> enteredID;
            if(maintenancePassword != enteredID){
                cout << "You have entered an invalid ID" << endl;
                break;
            }
            else{
                //code to start execuation trace
            }
            break;
        }
        }
    }
    while(ch != 'q');
    return 0;
}


