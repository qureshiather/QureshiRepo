# Remove Dupes with LinkedList
# How to do if Temporary Buffer not allowed? 

from Data_structures import Node

# Using Doubly LinkedList functionality (using Prev), all in place
def removeDupesDoubly(head):
	pointer = head
	my_set = set()
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
	return 0

if __name__ == '__main__': main()