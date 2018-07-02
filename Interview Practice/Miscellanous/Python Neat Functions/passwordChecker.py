#!/usr/bin/python3

# Console UI, checks if Password is Valid based on 
# assignment business rules
if __name__ in '__main__':
    passValid = False

    while(passValid == False):

        # assume password is valid, and change conditoins if not true
        pswdUpper = False
        pswdDecimal = False
        pswdSpecial = False
        pswdLength = False
        pswdSpace = True


        pswd = input( "Enter password: " )

        # Length can be checked once at start
        if (len(pswd) > 10):
            pswdLength = True

        # check for space in string
        if ' ' in pswd:
            pswdSpace = False;

        # check if conditions are met 
        for x in range (0, len(pswd) ):
            if (pswd[x].isupper()):
                pswdUpper = True
            elif (pswd[x].isdecimal()):
                pswdDecimal = True
            elif (pswd[x] =='$' or pswd[x] == '!' or pswd[x] == '*'):
                pswdSpecial = True

        # breaks loop and gives correct password iff all conditions are met 
        if pswdUpper and pswdDecimal and pswdSpecial and pswdLength and pswdSpace:
            passValid = True

        # print useful commands to user to fix their password
        else:
            if (pswdUpper == False):
                print ("password does not contain a capital letter")
            if (pswdDecimal  == False):
                print ("Passwrod needs to have a number" )
            if (pswdSpecial == False):
                print ("needs special character")
            if (pswdLength == False):
                print ("password is not long enough")
            if (pswdSpace == False):
                print ("password contains a space character")


    print ("your password is valid")

        
