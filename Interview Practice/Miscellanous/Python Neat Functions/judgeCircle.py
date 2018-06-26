
# Checks to see if given directions of movement of 1 unit each, 
# , if movements had a circle in there (ended up on a prior path)
def judgeCircle(moves):
	upDown = 0
	rightLeft = 0
	for char in moves:
		if char == 'R':
			rightLeft += 1
		if char =='L':
			rightLeft -= 1
		if char == 'U':
			upDown += 1
		if char == 'D':
			upDown -= 1
	if upDown == 0 and rightLeft == 0:
		return True
	else:
		return False

if __name__ in '__main__':
	moves = 'RRLL'
	print(judgeCircle(moves))