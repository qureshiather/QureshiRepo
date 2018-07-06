# !/usr/bin/python3
# Checks to see if given directions of movement of 1 unit each,
# , if movements had a circle in there (ended up on a prior path)


def judge_circle(moves):
    up_down = 0
    right_left = 0
    for char in moves:
        if char == 'R':
            right_left += 1
        if char == 'L':
            right_left -= 1
        if char == 'U':
            up_down += 1
        if char == 'D':
            up_down -= 1
    if up_down == 0 and right_left == 0:
        return True
    else:
        return False


if __name__ in '__main__':
    moves = 'RRLL'
    print(judge_circle(moves))
