# !/usr/bin/python3
# CTCI 4.3
# Given a binary search tree, make a linkedlist at each depth
from collections import deque


class TreeNode:
    """
        Binary Tree Node, not Full/Complete/Balanced
    """
    def __init__(self, value=None):
        if value:
            self.val = value
        self.left = None
        self.right = None


class Node:
    """
    Generic Node Class | Can be doubly or singly
    """
    def __init__(self, data=None):
        """
            Type argument changes behavior to singly, or doubly
        """
        if data:
            self.val = data
        self.next = None
        self.prev = None

    def appendToTail(self, data, typeChar='d'):
        """
        O(n), will need to traverse and place at end
        typeChar default is d, explictly use s for singly
        """
        newNode = Node(data)
        pointer = self
        while(pointer.next):
            pointer = pointer.next
        pointer.next = newNode
        if typeChar == 'd':
            newNode.prev = pointer

    def printLinkedList(self):
        """
        Helper Method to print out the linkedList
        """
        pointer = self
        # Cause I want to not put the - after last element
        while(pointer.next):
            print(str(pointer.val) + ' - ', end='')
            pointer = pointer.next
        print(str(pointer.val))


def tree_to_LL_depth(root):
    """
    :param root: root of binary search tree
    :return: list of linkedLists at each depth (starting depth = 0)
    Explanation: Using a dict to keep track of which level
    each Node is in. And appending that node to result list,
    at that result[level[node]]
    """
    result = []
    level = dict()
    q = deque()
    q.append(root)
    level[root] = 0
    while(q):
        p = q.popleft()
        if p.left:
            if p.left not in q:
                q.append(p.left)
            if p.left not in level:
                level[p.left] = level[p]+1
        if p.right:
            if p.right not in q:
                q.append(p.right)
            if p.right not in level:
                level[p.right] = level[p]+1
        if level[p] >= len(result):
            result.append(Node(p.val))
        else:
            result[level[p]].appendToTail(p.val, 's')
    return result


if __name__ in '__main__':
    # Visual Representation
    # 		 50
    # 		/  \
    #     30   70
    #    /  \
    #  10   40
    root = TreeNode(50)
    root.left = TreeNode(30)
    root.right = TreeNode(70)
    root.left.left = TreeNode(10)
    root.left.right = TreeNode(40)
    list_of_ll = tree_to_LL_depth(root)
    for list in list_of_ll:
        list.printLinkedList()
    print(0)
