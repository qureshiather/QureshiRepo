#! /usr/bin/python3
# Rotate Matrix: Given an image represented by an NxN matrix,
# where each pixel in the image is 4
# bytes, write a method to rotate the image by 90 degrees.
# Can you do this in place?


# ez mode - returns Matrix object that I am building from scratch
def rotate_square_matrix_90(m1):
    n = len(m1)
    m2 = [[0 for x in range(n)] for y in range(n)]
    for i in range(0, n):
        for j in range(0, n):
            m2[i][j] = m1[(n-1)-j][i]
    return m2


if __name__ == '__main__':
    w, h = 3, 3
    Matrix = [[0 for x in range(w)] for y in range(h)]

    Matrix[0][0] = 3
    Matrix[0][1] = 5
    Matrix[0][2] = 4
    Matrix[1][0] = 5
    Matrix[1][1] = 9
    Matrix[1][2] = 5
    Matrix[2][0] = 9
    Matrix[2][1] = 8
    Matrix[2][2] = 6

    # Visual Representation
    # 3 5 4
    # 5 9 5
    # 9 8 6
    # should become
    # 9 5 3
    # 8 9 5
    # 6 5 4
    print(rotate_square_matrix_90(Matrix))
