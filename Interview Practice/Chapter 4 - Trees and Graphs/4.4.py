# !/usr/bin/python3
# CTCI 4.3
# returns True if tree is balanced, False otherwise


class TreeNode:
    """
        Binary Tree Node, not Full/Complete/Balanced
    """
    def __init__(self, value=None):
        if value:
            self.val = value
        self.left = None
        self.right = None


def get_height(u):
    if not u:
        return 0
    return 1 + max(get_height(u.left), get_height(u.right))


def is_balanced(root):
    if abs(get_height(root.left)-get_height(root.right)) > 1:
        return False
    if root.left:
        return is_balanced(root.left)
    if root.right:
        return is_balanced(root.right)
    return True


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
    print(is_balanced(root))
    # Visual Representation (Unbalanced BST)
    # 		  50
    # 		 /  \
    #      30   70
    #     /  \
    #    10   40
    #   /
    #  5
    root.left.left.left = TreeNode(5)
    print(is_balanced(root))
