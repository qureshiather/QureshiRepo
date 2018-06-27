# 2.4 CTCI Partioning LinkedList Given Value

from Data_structures import Node

def partitionLinkedList(head, pValue):
    """
    Will Partiion a new linkedList, and 
    replace values in place
    """
    # Checking if value is in list
    partitionValue = head.findNode(pValue)
    if partitionValue is None:
        raise ValueError("Value is not in list")
    before = []
    after = []
    p = head
    while(p != None):
        if p.val < pValue:
            before.append(p.val)
        elif p.val == pValue:
            p = p.next
            continue
        else:
            after.append(p.val)
        p = p.next
    # Make new list with before - pValue - after
    new_list = []
    new_list.extend(before)
    new_list.append(pValue)
    new_list.extend(after)
    p = head
    # iterate through linkedlist and change values 
    for i in range(len(new_list)):
        p.val = new_list[i]
        p = p.next
    return

def main():
    head = Node(10)
    head.appendToTail(46,'s')
    head.appendToTail(20,'s')
    head.appendToTail(7654,'s')
    head.printLinkedList()
    partitionLinkedList(head, 46)
    head.printLinkedList()

if __name__ in '__main__': main()