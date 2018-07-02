#!/usr/bin/python3
# CTCI 4.1
# Given a directed graph, design an algorithm to find out whether there is a 
# route between two nodes

class GraphNode:
	"""
		Graph Node, can have many children (pointers to other nodes)
	"""
	def __init__(self, value=None):
		if value:
			self.val = value
		self.children = []

def is_route(a,b):
	"""
		Function returns true, if there is a path
		between two nodes in a directed graph
		Doing modified DFS to create visited sets
		for each a and b, and then checking if 
		a and b is in any of the others visited sets, 
		since it is a directed graph! 
	"""
	if a is b:
		return True
	visited1 = set()
	visited2 = set()
	visited1 = modified_DFS(a,visited1)
	visited2 = modified_DFS(b,visited2)
	if a in visited2 or b in visited1:
		return True
	return False

def modified_DFS(root, visited):
	visited.add(root)
	for u in root.children:
		if u not in visited:
			modified_DFS(u,visited)
	return visited

if __name__ in '__main__':
	# Visual Representation
	#  A-->B-->C
	#   \       \
	#   >D      >E--->F
	#    \
	#     >G
	ANode = GraphNode('A')
	BNode = GraphNode('B')
	CNode = GraphNode('C')
	DNode = GraphNode('D')
	ENode = GraphNode('E')
	FNode = GraphNode('F')
	GNode = GraphNode('G')
	ZNode = GraphNode('Z')
	ANode.children.append(BNode)
	ANode.children.append(DNode)
	BNode.children.append(CNode)
	CNode.children.append(ENode)
	ENode.children.append(FNode)
	DNode.children.append(GNode)
	# Should return True
	print(is_route(ANode,CNode))
	# Should return False
	print(is_route(ANode,ZNode))

