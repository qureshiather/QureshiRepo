class TreeNode:
    """
        Binary Tree Node, not Full/Complete/Balanced
    """
    def __init__(self, value=None):
        if value:
            self.val = value
        self.left = None
        self.right = None

def array_match(A, root):
    """
    
    """
    i = 0
    stack = []
    stack.append(root)
    while len(stack) != 0:
        u = stack.pop()
        if u.val != A[i]:
            return False
        i += 1
        if u.right:
            stack.append(u.right)
        if u.left:
            stack.append(u.left)
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
    arr = [50, 30, 10, 40, 70]
    print(array_match(arr, root) == True)