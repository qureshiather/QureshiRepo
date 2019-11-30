class TreeNode:

    def __init__(self, val):
        self.val = val
        self.left = None
        self.right = None


def preorder_recursive(root, result):
    # root, left right
    result.append(root.val)
    if root.left:
        preorder_recursive(root.left, result)
    if root.right:
        preorder_recursive(root.right, result)
    return result


def inorder_recursive(root, result):
    # left, root, right
    if root.left:
        inorder_recursive(root.left, result)
    result.append(root.val)
    if root.right:
        inorder_recursive(root.right, result)
    return result


def postorder_recursive(root, result):
    # left, right, root
    if root.left:
        postorder_recursive(root.left, result)
    if root.right:
        postorder_recursive(root.right, result)
    result.append(root.val)
    return result


def preorder_iterative(root, result):
    pass


def inorder_iterative(root, result):
    pass


def postorder_iterative(root, result):
    pass

# test cases
if __name__ in '__main__':
    #
    #       50
    #     /    \
    #    30     60
    #   /  \
    #  10   15

    tree_root = TreeNode(50)
    tree_root.left = TreeNode(30)
    tree_root.left.left = TreeNode(10)
    tree_root.left.right = TreeNode(15)
    tree_root.right = TreeNode(60)

    print(preorder_recursive(tree_root, []))
    print(inorder_recursive(tree_root, []))
    print(postorder_recursive(tree_root, []))



