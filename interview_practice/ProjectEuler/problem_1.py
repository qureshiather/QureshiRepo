def sum_multp_3_5(n):
    """
    sums factors of 3, 5, and both, from 1 to n-1
    """
    count = 0
    for i in range(1, n):
        if i % 3 == 0 or i % 5 == 0:
            count += i
    return count

print(sum_multp_3_5(1000))
