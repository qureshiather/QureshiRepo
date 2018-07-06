# !/usr/bin/python3
# Test Matrix


def rotate_square_matrix_90(m1):
    n = len(m1)
    m2 = [[0 for x in range(n)] for y in range(n)]
    for i in range(0, n):
        for j in range(0, n):
            m2[i][j] = m1[(n-1)-j][i]
    return m2


if __name__ == '__main__':
    square_mat = [
        [1,  2,  3,  4],
        [5,  6,  7,  8],
        [9,  10, 11, 12],
        [13, 14, 15, 16]
    ]
    new = rotate_square_matrix_90(square_mat)
    print(new)
