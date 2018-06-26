# 2.4 CTCI Partioning LinkedList Given Value

from Data_structures import Node

def partitionLinkedList(head, value):
    partitionValue = head.findNode(value)
    if partitionValue is None:
        raise ValueError("Value is not in list")
        
    return None

def main():
    head = Node(10)
    head.appendToTail(20,'s')
    head.appendToTail(46,'s')
    head.appendToTail(7654,'s')
    head.printLinkedList()
    midNode = head.findNode(10)
    partitionLinkedList(head, 46)
    head.printLinkedList()

if __name__ in '__main__': main()