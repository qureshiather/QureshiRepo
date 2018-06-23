class Node:
	"""
	Generic Node Class
	"""
	def __init__(self, data):
		self.val = data
		self.next = None
		self.prev = None

	def appendToTail(self, data):
		"""
		O(n), will need to traverse and place at end
		"""
		newNode = Node(data)
		pointer = self
		while(pointer.next != None):
			pointer = pointer.next
		pointer.next = newNode
		newNode.prev = pointer

class Stack:
	"""
		LIFO || push, peek, pop, and isEmpty. 
		Value is any payload. 
		Needs node class
	"""
	def __init__(self):
		self.head = Node(None)
		self.size = 0

	def push(self, data):
		if self.isEmpty():
			self.head.val = data
		else:
			self.head.next = Node(data)
			self.head.next.prev = self.head
			self.head = self.head.next
		self.size += 1

	def peek(self):
		if self.isEmpty():
			raise ValueError('Stack has no elements')
		else:
			return self.head.val

	def pop(self):
		if self.isEmpty():
			raise ValueError('Stack has no elements')
		elif self.size == 1:
			result = self.head.val
			self.head.val = None
			self.size -= 1
			return result
		else:
			result = self.head.val
			self.head = self.head.prev
			self.head.next = None
			self.size -= 1
			return result

	def isEmpty(self):
		return self.size == 0

class Queue:
	"""
		FIFO || implements add, remove, examine, and isEmpty
	"""
	def __init__(self):
		self.head = None
		self.tail = None
		self.size = 0

	def isEmpty(self):
		return self.size == 0

	def add(self, value):
		newNode = Node(value)
		if self.isEmpty():
			self.head = newNode
			self.tail = newNode
		else:
			self.head.next = newNode
			self.head.next.prev = self.head
			self.head = newNode
		self.size += 1

	def remove(self):
		if self.isEmpty():
			raise ValueError('Queue has no elements')
		elif self.size == 1:
			result = self.tail.val
			self.head = None
			self.tail = None
			self.size -= 1
			return result
		else:
			result = self.tail.val
			self.tail = self.tail.next
			self.tail.prev = None
			self.size -= 1
			return result

	def examine(self):
		if self.isEmpty():
			raise ValueError('Queue has no elements')
		else:
			return self.tail.val
