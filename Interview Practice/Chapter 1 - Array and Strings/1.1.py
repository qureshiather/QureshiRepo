# 1.1 Is Unique: Implement an algorithm to determine 
# if a string has all unique characters
#What if you cannot use additional data structures?

def checkUnique(input):
	for i, char1 in enumerate(input):
		for o, char2 in enumerate(input):
			o = o + 1
			if o == len(input)-1: 
				# string is unique
				return True 
			if input[i] == input[o]:
				 #string in not unique
				return False
	return False

string1 = 'Ather'
print checkUnique(string1)

