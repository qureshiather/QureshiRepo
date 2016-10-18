#include "bank.h"
class Person
{

private:
    int id;

public:

    Person(){
        id = -1;
    }

    int getID(){
        return id;
    }

    void setID(int newID){
        id = newID;
    }

    void printID(){
        cout << id << endl;
    }

};
