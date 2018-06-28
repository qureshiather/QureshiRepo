#!/usr/bin/python3
# Check if str1 or str1 are rotated versinos of eachother. Only call is substring once. 

def isRotated(str1,str2):
	if len(str1) != len(str2):
		return False
	return str2 in str1+str1

A = 'waterbottle'
B = 'bottlewater'
print(isRotated(A,B))