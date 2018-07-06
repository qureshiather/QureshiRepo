# !/usr/bin/python3
# String Compression: Implement a method to perform basic string compression
# using the counts
# of repeated characters. For example, the string aabcccccaaa would become
# a2blc5a3. If the
# compressed" string would not become smaller than the original string,
#  your method should return
# the original string.
# You can assume the string has only uppercase and lowercase letters (a - z).


def compress(A):
    A = A.lower()
    # make new compressed string
    com_string = []
    i = 0
    while(i <= len(A)-1):
        skip = counter_letter(A, i)
        com_string.append(A[i])
        com_string.append(str(skip))
        i += skip
    if len(com_string) >= len(A):
        return A
    return ''.join(com_string)


def counter_letter(A, i):
    letter = A[i]
    end = False
    i += 1
    count = 1
    while (not end):
        if i > len(A)-1:
            break
        elif A[i] == letter:
            count += 1
            i += 1
        else:
            end = True
    return count


if __name__ == '__main__':
    string = 'aaaaaaaaaaaaabcd'
    print(compress(string))
