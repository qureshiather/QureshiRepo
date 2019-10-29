# !/usr/bin/python3
# CTCI 4.5
# Write a function that checks if given tree
# is a binary search tree

# Definition of BST is
# Left child < Parent < Right Child
# Only 2 children Per Node


class TreeNode:
    """
        Binary Tree Node, not Full/Complete/Balanced
    """
    def __init__(self, value=None):
        if value:
            self.val = value
        self.left = None
        self.right = None


def in_order(u, list):
    if not u:
        return
    in_order(u.left, list)
    list.append(u.val)
    in_order(u.right, list)
    return


def check_BST(root):
    list = []
    in_order(root, list)
    if sorted(list) == list:
        return True
    else:
        return False


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
    print(check_BST(root))
    # Visual Representation (not BST)
    # 		 50
    # 		/  \
    #     30    5
    #    /  \
    #  10   40
    root.right.val = 5
    print(check_BST(root))
