class Node:
	def __init__(self, data):
		"""
			Constructor to init node class
		"""
		self.data = data
		self.next = None
		return

class SinglyLinkedList:
	def __init__(self, headData):
		self.head = None
		self.tail = None
		return

	def append(self, item):
		"""
		Args:
        	item: Node to be added to LinkedList
   		Returns:
        	Node: Head of SinglyLinkedList
		"""
		
		n = self  
		while(n.next is not None):
			n = n.next
		n.next = Node(data)
		return

LL = SinglyLinkedList()
print LL.getHead().data


