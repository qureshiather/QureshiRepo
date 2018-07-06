# !/usr/bin/python3
# Check Permutation: Given two strings,
# write a method to decide if one is a permutation of the
# other


def check_permutation_sort(A, B):
    A = A.lower()
    B = B.lower()
    if len(A) != len(B):
        return False
    A = sorted(A)
    B = sorted(B)
    # recall that == is checking quality, whereas A is B would check identity
    return A == B


if __name__ in '__main__':
    string1 = 'ather'
    string2 = 'thera'
    print(check_permutation_sort(string1, string2))
