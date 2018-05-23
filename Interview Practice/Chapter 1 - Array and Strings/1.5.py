# Test Matrix
squareMat = [ [1,  2,  3,  4 	],
        [5,  6,  7,  8 	],
        [9,  10, 11, 12 ],
        [13, 14, 15, 16 ] 
]

rectMat = [ [1,  2,  3,  4 	],
        [5,  6,  7,  8 	],
        [9,  10, 11, 12 ],
        [13, 14, 15, 16 ],
        [24, 1, 35, 12 ]
]

def rotateSquareMatrix90(m1):
	n = len(m1)
	m2 = [[0 for x in range(n)] for y in range(n)]
	for i in range(0, n):
		for j in range(0, n):
			m2[i][j] = m1[(n-1)-j][i]
	return m2