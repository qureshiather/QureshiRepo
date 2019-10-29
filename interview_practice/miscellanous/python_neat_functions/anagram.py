def is_anagram(s1, s2):
    if len(s1) != len(s2):
        return False
    char_s2 = dict()
    for char in s2:
        if char in char_s2:
            char_s2[char] += 1
        else:
            char_s2[char] = 1
    for char in s1:
        if char not in char_s2:
            return False
        else:
            char_s2[char] -= 1
            if char_s2[char] == 0:
                del char_s2[char]
    return True if len(list(char_s2.keys())) == 0 else False

if __name__ in '__main__':
    print(is_anagram('tac','cat'))
