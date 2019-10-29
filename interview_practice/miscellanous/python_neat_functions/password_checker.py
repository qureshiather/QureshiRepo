# !/usr/bin/python3
# Console UI, checks if Password is Valid based on
# assignment business rules


if __name__ in '__main__':
    pswd_valid = False

    while(not pswd_valid):

        # assume password is valid, and change conditoins if not true
        pswd_upper = False
        pswd_decimal = False
        pswd_special = False
        pswd_length = False
        pswd_space = True

        pswd = input("Enter password: ")

        # Length can be checked once at start
        if (len(pswd) > 10):
            pswd_length = True

        # check for space in string
        if ' ' in pswd:
            pswd_space = False

        # check if conditions are met
        for x in range(0, len(pswd)):
            if (pswd[x].isupper()):
                pswd_upper = True
            elif (pswd[x].isdecimal()):
                pswd_decimal = True
            elif (pswd[x] == '$' or pswd[x] == '!' or pswd[x] == '*'):
                pswd_special = True

        # breaks loop and gives correct password iff all conditions are met
        if (pswd_upper and pswd_decimal and pswd_special and pswd_length
                and pswd_space):
            pswd_valid = True

        # print useful commands to user to fix their password
        else:
            if (not pswd_upper):
                print("password does not contain a capital letter")
            if (not pswd_decimal):
                print("password needs to have a number")
            if (not pswd_special):
                print("needs special character")
            if (not pswd_length):
                print("password is not long enough")
            if (not pswd_space):
                print("password contains a space character")

    print("your password is valid")
