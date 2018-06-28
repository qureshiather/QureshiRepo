#!/usr/bin/python3
# CTCI 2.7 Given 2 singly linkedLists, determine if the two lists 
# intersect, return intersecting node, else return null

from Data_structures import Node

def intersection(head_a, head_b):
    if head_a is head_b: 
        return head_a
    if head_a is None or head_b is None:
        return None
    pA = head_a
    pB = head_b
    visited = set()
    visited.add(pA)
    visited.add(pB)
    pA = pA.next
    pB = pB.next
    # Break out of while loop if both are null
    while(pA is not None or pB is not None):
        if pA in visited:
            return pA
        if pB in visited:
            return pB
        if pA is pB: return pA
        visited.add(pA)
        visited.add(pB)
        if pA is not None: pA = pA.next
        if pB is not None: pB = pB.next
    return None

if __name__ in '__main__':
    # Making A LinkedLIst
    headA = Node(10)
    headA.next = Node(20)
    # Making B LinkedList
    headB = Node(765)
    headB.next = Node(543)
    headB.next.next = Node(432)
    # Creating Intersecting Node and appendingn to lists
    intersectNode = Node(40)
    headA.next.next = intersectNode
    headB.next.next.next = intersectNode
    # adding some more fluff nodes
    headA.next.next.next = Node(50)
    headA.next.next.next.next = Node(53)
    headA.printLinkedList()
    headB.printLinkedList()
    print(intersection(headA,headB).val)