# returns true if input is in fibonacci sequence, false otherwise. 
def isInFib(n):
	if n == 0: return False
	elif n == 1: return True
	else:
		A,B = 1,1
		FLIP = True
		while(True):
			new = A + B
			if new > n: return False
			elif new == n: return True
			else:
				if(FLIP):
					A = new 
					FLIP = not FLIP
				else:
					B = new
					FLIP = not FLIP

# UI main loop 
if __name__ == '__main__':
	while(True):
		n = input( "Enter Number Check if in Fib, else write x to exit: " )
		if n == 'x': break
		n = int(n)
		print(isInFib(n))
	

