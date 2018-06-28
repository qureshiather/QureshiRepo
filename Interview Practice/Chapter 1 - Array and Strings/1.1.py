#!/usr/bin/python3
# 1.1 Is Unique: Implement an algorithm to determine 
# if a string has all unique characters
#What if you cannot use additional data structures?

def checkUnique(input):
	seen = set()
	for i in range(0,len(input)):
		if input[i] in seen:
			return False
		else:
			seen.add(input[i])
	return True
		
if __name__ in '__main__':
	string1 = 'Atrher'
	print(checkUnique(string1))

