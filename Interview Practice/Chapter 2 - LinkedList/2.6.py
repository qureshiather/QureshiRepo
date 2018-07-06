# !/usr/bin/python3
# CTCI 2.6 | Implement function to check if Singly LinkedList is Palindrome

from Data_structures import Node, Stack


def is_palindrome_node(head):
    string = []
    p = head
    while(p is not None):
        string.append(p.val)
        p = p.next
    return is_palindrome_str(''.join(string))


def is_palindrome_str(A):
    """
    Determines if String is a palindrome,
    returns True is yes, otherwise False
    """
    left = 0
    right = len(A)-1
    while(left < right):
        if A[left] == A[right]:
            left += 1
            right -= 1
        else:
            return False
    return True


def is_palindrome_stack(head):
    myStack = Stack()
    p = head
    # First Pass, put all values in Stack
    while(p is not None):
        myStack.push(p.val)
        p = p.next
    # second pass
    p = head
    while(p is not None):
        if p.val != myStack.pop():
            return False
        else:
            p = p.next
    return True


if __name__ in '__main__':
    head = Node('R')
    head.appendToTail('A')
    head.appendToTail('D')
    head.appendToTail('A')
    head.appendToTail('R')
    head.printLinkedList()
    print(is_palindrome_node(head))
    print(is_palindrome_stack(head))
