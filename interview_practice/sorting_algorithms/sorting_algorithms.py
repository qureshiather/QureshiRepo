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


def merge_sort_recur(A):
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
    return merge(merge_sort_recur(A[l:m]), merge_sort_recur(A[m:r]))


def merge_sort_iter(A):

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

    # intially each element is size one
    subarray_size = 1
    n = len(A)

    # go until the subbarray size equals n
    while subarray_size < n:
        
        level_merges = n//subarray_size
        
        # sort in 1s, then 2s, then 3s.
        for i in range(0, level_merges):
            
            left = A[i*subarray_size:i*subarray_size+subarray_size]
            right = A[i*subarray_size+subarray_size:i*subarray_size+subarray_size+subarray_size]
            A[i*subarray_size:i*subarray_size+subarray_size+subarray_size] = merge(left, right)
            
        subarray_size *= 2
    
    return A


if __name__ in '__main__':
    
    # A = [random.randint(0, 100) for _ in range(0, 10)]
    A = [1, 5, 234, 7, 3, 2, 1, 19, 11]

    start = time.time()
    print(merge_sort_iter(A))
    print(f'merge_sort_iter: {time.time()-start}')
