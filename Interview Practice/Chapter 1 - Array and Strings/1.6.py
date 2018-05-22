# String Compression: Implement a method to perform basic string compression using the counts
# of repeated characters. For example, the string aabcccccaaa would become a2blc5a3. If the
# compressed" string would not become smaller than the original string, your method should return
# the original string. You can assume the string has only uppercase and lowercase letters (a - z).

def Compress(A):
	A = A.lower()
	# make new compressed string
	comStr = ''
	i = 0
	while(i <= len(A)-1):
		skip = CountLetter(A, i)
		comStr += A[i]
		comStr += str(skip)
		i += skip
	if len(comStr) >= len(A):
		return A
	return comStr

def CountLetter(A, i):
	letter = A[i]
	end = False
	i += 1
	count = 1
	while (end != True):
		if i > len(A)-1:
			break
		elif A[i] == letter:
			count += 1
			i += 1
		else:
			end = True
	return count

string = 'aaaaaaaaaaaaabcd'

print Compress(string)