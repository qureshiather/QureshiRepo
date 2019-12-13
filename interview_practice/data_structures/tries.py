class Node:

    def __init__(self, val=None):
        self.val = val
        self.children = []

class tries:

    def __init__(self, word_list):
        self.root = Node()
        for word in word_list:
            self.add_word(word)
        return root

    def find_prefix(self, prefix):
        

    def add_word(self, word):
        pass