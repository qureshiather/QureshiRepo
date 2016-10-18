#ifndef BANK_H
#define BANK_H

#include <iostream>
#include <string>
#include <time.h>

using namespace std;

#include "person.cpp"
#include "customer.cpp"
#include "manager.cpp"

//global variables
Customer cust[20];
int arraySize = sizeof(cust)/sizeof(cust[0]);
Manager manage;
string maintenancePassword = "letmein";
string managerPassword = "michaelscott";

#endif // BANK_H
