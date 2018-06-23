from Data_structures import Stack, Queue

def main():
	myQueue = Queue()
	myQueue.add(50)
	myQueue.add('Test')
	myQueue.add(50)
	print(myQueue.remove())
	print(myQueue.examine())
	print(myQueue.remove())
	print(myQueue.examine())
	print(myQueue.remove())

if __name__ in '__main__': main()