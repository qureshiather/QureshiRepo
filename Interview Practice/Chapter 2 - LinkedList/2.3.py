# 2.2 CTCI Function to delete node in middle of LinkedList

from Data_structures import Node

def findNode(head, value):
    """
        Return Node reference of Node given a payload value
        else, it will return None
    """
    pointer = head
    while(pointer != None):
        if pointer.val == value:
            return pointer
        pointer = pointer.next
    return None

def delMiddleNode(mid):
    """
        Will Delete Middle Node in singly linkedList,
        will return error if last node referenced,
        will DELETE the head node, if given head  
    """
    if mid is None:
        raise ValueError("None Value given")
    if mid.next is None:
        raise ValueError("last node given")
    mid.val = mid.next.val
    # If 2nd last node
    if mid.next.next is None:
        mid.next = None
    else:
        mid.next = mid.next.next

def main():
    head = Node(10)
    head.appendToTail(20,'s')
    head.appendToTail(46,'s')
    head.appendToTail(7654,'s')
    head.printLinkedList()
    midNode = findNode(head,10)
    delMiddleNode(midNode)
    head.printLinkedList()

if __name__ in '__main__': main()
