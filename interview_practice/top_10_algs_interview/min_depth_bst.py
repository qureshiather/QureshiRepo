class TreeNode:
    """
        Binary Tree Node, not Full/Complete/Balanced
    """
    def __init__(self, value=None):
        if value:
            self.val = value
        self.left = None
        self.right = None


def min_depth(root):
    """
    min depth from root, to get to a leaf
    """
    if not root:
        return 0
    else:
        return min(min_depth(root.left)+1, min_depth(root.right)+1)

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
    print(min_depth(root))