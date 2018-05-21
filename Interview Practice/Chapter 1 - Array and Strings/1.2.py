# Check Permutation: Given two strings, 
# write a method to decide if one is a permutation of the
# other

def checkPermutation(input1, input2):
	input1 = input1.lower()
	input2 = input2.lower()
	if len(input1) != len(input2):
		return False
	found = False
	i = 0
	k = 0
	while(True):
		if i == len(input1):
			return True
		if input1[i] == input2[k]:
			i = i + 1
			k = 0
			continue
		else:
			k = k + 1
			if k == len(input2):
				return False
	return True

def checkPermutationSort(A,B):
	A = A.lower()
	B = B.lower()
	if len(A) != len(B):
		return False
	A = sorted(A)
	B = sorted(B)
	# recall that == is checking quality, whereas A is B would check identity 
	return A == B

string1 = 'ather'
string2 = 'rehza'
print checkPermutationSort(string1, string2)
	