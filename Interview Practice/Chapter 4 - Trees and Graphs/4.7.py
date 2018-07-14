# !/usr/bin/python3
# CTCI 4.7
# Given a list of projects and dependencies, determine valid build order
# if not valid build order, return an error (raise ValueError("not valid"))


from collections import deque


class GraphNode:

    def __init__(self, value=None):
        self.val = value
        self.children = []


def build_order(projects, dependencies):
    root = build_graph(dependencies)
    build_order = BST(root, projects)
    # add in indepdendent projects
    return build_order


def build_graph(dependencies):
    """
    creates graph using edges in dependencies, returns root 
    of graph, else returns error
    ONLY WORKS IF THERE'S A SINGLE STARTING PROJECT
    """
    nodes = dict()
    for edge in dependencies:
        if edge[0] not in nodes:
            nodes[edge[0]] = GraphNode(edge[0])
        if edge[1] not in nodes:
            nodes[edge[1]] = GraphNode(edge[1])
        nodes[edge[0]].children.append(nodes[edge[1]])
    for pair in dependencies:
        if pair[1] in nodes:
            nodes.pop(pair[1])
    if len(list(nodes.keys())) != 1:
        raise ValueError('No Valid Build Order Found')
    else:
        return nodes[list(nodes.keys())[0]]


def BST(root, projects):
    q = deque()
    q.append(root)
    result = []
    while(q):
        u = q.popleft()
        result.append(u.val)
        projects.remove(u.val)
        for child in u.children:
            if child not in q:
                q.append(child)
    result.extend(projects)
    return result


if __name__ in '__main__':
    dependencies = [('a', 'd'), ('f', 'b'), ('b', 'd'), ('f', 'a'), ('d', 'c')]
    projects = ['a', 'b', 'c', 'd', 'e', 'f']
    print(build_order(projects, dependencies))
