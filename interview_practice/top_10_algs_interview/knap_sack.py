
def knapsack(w, v, c):
    n = len(w)
    mem = {}

    def helper(i, cap):
        if (i, cap) in mem:
            return mem[(i, cap)]
        if i >= n:
            return 0
        elif w[i] > cap:
            return helper(i+1, cap)
        else:
            yes = helper(i+1, cap - w[i]) + v[i]
            no = helper(i+1, cap)
            mem[(i, cap)] = max(yes, no)
            return mem[(i, cap)]
    
    return helper(0, c)

if __name__ in '__main__':
    w = [1, 3, 5, 3, 2, 5, 3, 1]
    v = [3, 4, 7, 2, 6, 11, 5, 2]
    cap = 8
    print(knapsack(w, v, cap))