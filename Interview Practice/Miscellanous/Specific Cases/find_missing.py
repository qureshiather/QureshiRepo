#bin/python

def find_missing(A, B):
    seen = set()
    for elem in B:
        seen.add(elem)
    for elem in A:
        if elem not in seen:
            return elem

def find_missing_2(A, B):
    return sum(A) - sum(B)

if __name__ in '__main__':
    A = [4, 8, 3, 9]
    B = [4, 3, 9]
    print(find_missing_2(A,B))