# CTCI 2.8 Given a singly (corrupt) linkedList, return the node
# the is the start of the loop
# Specfically, a node's next pointer, points to a earlier node

from Data_structures import Node
import time

def loopChecker(head):
    visited = set()
    if head is None or head.next is None:
        raise ValueError("Head or Head.next is None")
    p = head
    visited.add(p)
    p = p.next
    while(p != None):
        if p in visited:
            return p
        else:
            visited.add(p)
            p = p.next
    return None

if __name__ in '__main__':
    head = Node(10)
    head.next = Node(20)
    cycleNode = Node(40)
    head.next.next = cycleNode
    head.next.next.next = Node(50)
    head.next.next.next.next = Node(53)
    head.next.next.next.next.next = cycleNode
    # Can not print linkedList, as there's a cycle 
    # head.printLinkedList()
    print(loopChecker(head).val)