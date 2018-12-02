# !/usr/bin/python3
# Palindrome Permutation: Given a string, write a function to check if it i
# s a permutation of a palindrome.
# A palindrome is a word or phrase that is the same forwards and backwards.
# A permutation
# is a rearrangement of letters.
# The palindrome does not need to be limited to just dictionary words.


def palindrome_checker(A):
    left = 0
    right = len(A)-1
    while(left != right):
        if A[left] == A[right]:
            left = left + 1
            right = right - 1
        else:
            return False
    return True


if __name__ in '__main__':
    string = 'racecar'
    print(palindrome_checker(string))
    print(addition(5, 3))