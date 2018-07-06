#!/usr/bin/python3


def add_one(A):
    """
    A in input list of integers,
    that make up a number left to right
    makes changes in place
    that uses no addtional space complexity
    except in certain cases
    """
    if not A:
        return A
    if A[len(A)-1] != 9:
        A[len(A)-1] = A[len(A)-1]+1
        return A
    A[len(A)-1] = 0
    if len(A)-1 == 1:
        return [1, 0]
    for i in range(len(A)-2, -1, -1):
        if A[i] != 9:
            A[i] += 1
            return A
        else:
            A[i] = 0
    A.insert(0, 1)
    return A


def add_one_noob(A):
    B = list(A)
    B = map(str, B)
    B = int(''.join(B))
    B += 1
    return B


if __name__ in '__main__':
    input = [9]
    print(add_one(input))
