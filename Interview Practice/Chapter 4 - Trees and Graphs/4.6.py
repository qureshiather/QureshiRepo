# !/usr/bin/python3
# CTCI 4.6
# Write a function that takes a node from a Complete
# binary search tree, and returns in-order succesor


class TreeNode:
    """
        Binary Tree Node, not Full/Complete/Balanced
    """
    def __init__(self, value=None):
        if value:
            self.val = value
        self.left = None
        self.right = None
        self.parent = None


def inorder_succesor(u):
    if u.left is None and u.right is None:
        if u.parent.left is u:
            return u.parent
        else:
            return u.parent.parent
    else:
        if u.right:
            return u.right
        else:
            return None


if __name__ in '__main__':
    # Visual Representation
    # 		 50
    # 		/  \
    #     30   70
    #    /  \
    #  10   40
    root = TreeNode(50)
    root.left = TreeNode(30)
    root.left.parent = root
    root.right = TreeNode(70)
    root.right.parent = root
    root.left.left = TreeNode(10)
    root.left.left.parent = root.left
    root.left.right = TreeNode(40)
    root.left.right.parent = root.left
    # Test Cases
    print('Test Case 1:  ' + str(inorder_succesor(root.left.left).val == 30))
    print('Test Case 2:  ' + str(inorder_succesor(root.left).val == 40))
    print('Test Case 3:  ' + str(inorder_succesor(root).val == 70))
    print('Test Case 4:  ' + str(inorder_succesor(root.right) is None))
