#!/usr/bin/python3

def checkBinarySearch(A,u):
    """
        Iterative Implementation of BS
        Returns True if u is in A
        else, returns False
        manual implementation of is u in A
    """
    A.sort()
    L = 0
    R = len(A)
    while(L<R):
        mid = int(L + (R-L)/2)
        if A[mid] == u:
            return True
        elif u > A[mid]:
            L = mid+1
        else: # u < A[mid]
            R = mid-1
    return False

if __name__ in '__main__':
    newList = [4,6,74,1,3,5]
    print(checkBinarySearch(newList,8))
    print(checkBinarySearch(newList,3))



