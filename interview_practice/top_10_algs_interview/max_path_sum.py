class TreeNode:
    """
        Binary Tree Node, not Full/Complete/Balanced
    """
    def __init__(self, value=None):
        if value:
            self.val = value
        self.left = None
        self.right = None


def max_path_sum(root):
    if not root:
        return 0
    else:
        return max(max_path_sum(root.left) + root.val, max_path_sum(root.right) + root.val)

if __name__ in '__main__':
    # Visual Representation
    # 		 50
    # 		/  \
    #     30   70
    #    /  \
    #  10   50
    root = TreeNode(50)
    root.left = TreeNode(30)
    root.right = TreeNode(70)
    root.left.left = TreeNode(10)
    root.left.right = TreeNode(50)
    # Since 50 + 30 + 50 is lagest path, and it's 130
    print(max_path_sum(root) == 130)