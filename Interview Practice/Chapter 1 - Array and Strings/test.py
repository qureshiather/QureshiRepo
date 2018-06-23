
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

moves = 'RRLL'
print(judgeCircle(moves))