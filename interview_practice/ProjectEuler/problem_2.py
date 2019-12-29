def fib_iterative_sum_even(n):
    """
    n is the value size
    """
    count = 0
    a = 1
    b = 1
    while (a + b) < n:
        if (a + b) % 2 == 0:
            count += (a + b)
        b, a = (a+b), b
    return count

print(fib_iterative_sum_even(4000000))