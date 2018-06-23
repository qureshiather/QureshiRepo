# Remove Dupes with LinkedList
# How to do if Temporary Buffer not allowed? 

from Data_structures import Node

def removeDupesDoubly(head):
	"""
	Using Doubly LinkedList functionality (using Prev), all in place
	"""
	pointer = head
	my_set = set() # Hash Set
	while(pointer != None):
		if pointer.val in my_set:
			if pointer.next is None:
				pointer.prev.next = None
				break
			else:
				pointer.prev.next = pointer.next
				pointer.next.prev = pointer.prev
		else:
			my_set.add(pointer.val)
		pointer = pointer.next
	return head

def removeDupesSingly(head):
	"""
	Using two pointers to keep track of previous node
	"""
	# If LinkedList with only 1 element
	if head.next is None:
		return head
	pointer = head
	my_set = set()
	my_set.add(pointer.val)
	pointer_prev = pointer
	pointer = pointer.next
	while(pointer != None):
		# Seen this value before
		if pointer.val in my_set:
			# If End of LinkedList
			if pointer.next is None:
				pointer_prev.next = None
				break
			else:
				pointer_prev.next = pointer.next
		# Not seen before
		else:
			my_set.add(pointer.val)
			pointer_prev = pointer
		pointer = pointer.next
	return head

def printLinkedList(head):
	"""
	Helper Method to print downstream nodes, given a head Node
	"""
	pointer = head
	# Cause I want to not put the - after last element
	while(pointer.next != None):
		print(str(pointer.val) + ' - ',end='')
		pointer = pointer.next
	print(str(pointer.val))

def main():
	head = Node(10)
	head.appendToTail(20)
	head.appendToTail(50) # Dupe
	head.appendToTail(30)
	head.appendToTail(120)
	head.appendToTail(50) # Dupe
	head.appendToTail(10)
	head.appendToTail(15)
	printLinkedList(head)
	removeDupesDoubly(head) # Should remove Dupes from LinkedList
	printLinkedList(head)
	return

if __name__ == '__main__': main()