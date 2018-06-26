# Palindrome Permutation: Given a string, write a function to check if it is a permutation of a palindrome.
# A palindrome is a word or phrase that is the same forwards and backwards. A permutation
# is a rearrangement of letters. The palindrome does not need to be limited to just dictionary words.

def palindromeChecker(A):
	left = 0
	right = len(A)-1
	while(left != right):
		if A[left] == A[right]:
			left = left + 1
			right = right - 1
		else:
			return False
	return True

if __name__ in '__main__':
	string = 'racecar'
	print(palindromeChecker(string))

