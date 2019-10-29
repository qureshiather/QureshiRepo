# !/usr/bin/python3
# URLify: Write a method to replace all spaces in a string with '%20'.
# You may assume that the string
# has sufficient space at the end to hold the additional characters,
# and that you are given the "true"
# length of the string. (Note: If implementing in Java,
# please use a character array so that you can
# perform this operation in place.)


# replace whitespace with %20
def replace_space(A):
    i = 0
    new_string = []
    while i < len(A):
        if A[i] != ' ':
            new_string.append(A[i])
        else:
            new_string.append('%20')
        i = i + 1
    return ''.join(new_string)


if __name__ in '__main__':
    print(replace_space("Ather is cool"))
