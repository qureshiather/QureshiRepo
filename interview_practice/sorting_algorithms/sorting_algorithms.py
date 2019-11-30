import time
import random

def bubble_sort(A):
    """
        Keep swaping adjacent elements, until you don't need
        to swap anymore
    """
    sorted = False
    while sorted != True:
        for i in range(len(A)-1):
            swap = False
            if A[i+1] < A[i]:
                A[i+1], A[i] = A[i], A[i+1]
                swap = True
        if swap == False:
            sorted = True
    return A


def merge_sort(A):
    """
        Divide the Aay into twos recursively, and sort the smallest pieces
    """
    def merge(left, right):
        result = []        
        i = 0
        k = 0
        while(i < len(left) and k < len(right)):
            if left[i] <= right[k]:
                result.append(left[i])
                i += 1
            else:
                result.append(right[k])
                k += 1
        if i > k:
            result.extend(right[k:])
        else:
            result.extend(left[i:])
        return result

    
    if len(A) == 1:
        return A
    l = 0
    r = len(A)
    m = int((l+r)/2)
    L = A[l:m]
    R = A[m:r]
    return merge(merge_sort(L), merge_sort(R))


def insertion_sort(A):
    if len(A) == 1:
        return A
    for i in range(1, len(A)):
        x = A[i]
        j = i-1
        while j >= 0 and x < A[j]:
            # keep moving elements up up from A, until x > A[j]
            A[j+1] = A[j]
            j -= 1
        A[j+1] = x
    return A


if __name__ in '__main__':
    
    A = [random.randint(0, 16453) for _ in range(0, 10)]

    start = time.time()
    print(insertion_sort(A))
    print(f'insertion_sort: {time.time()-start}')

    start = time.time()
    print(bubble_sort(A))
    print(f'bubble_sort: {time.time()-start}')

    start = time.time()
    print(merge_sort(A))
    print(f'merge_sort: {time.time()-start}')

    start = time.time()
    print(sorted(A))
    print(f'python_sort: {time.time()-start}')
