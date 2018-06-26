# 2.4 CTCI Partioning LinkedList Given Value

from Data_structures import Node

def partitionLinkedList(head, pValue):
    """
    Will Partiion a new linkedList but not in place,
    needs to return new head, old head pointer 
    doesn't seem to change
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
    # Make new list with before then Partition, then after
    new_list = []
    new_list.extend(before)
    new_list.append(pValue)
    new_list.extend(after)
    head = Node(0)
    head.val = new_list[0]
    p2 = head
    for i in range(1, len(new_list)):
        p2.next = Node(0)
        p2.next.val = new_list[i]
        p2 = p2.next
    return head

def main():
    head = Node(10)
    head.appendToTail(46,'s')
    head.appendToTail(20,'s')
    head.appendToTail(7654,'s')
    head.printLinkedList()
    newHead = partitionLinkedList(head, 46)
    newHead.printLinkedList()

if __name__ in '__main__': main()