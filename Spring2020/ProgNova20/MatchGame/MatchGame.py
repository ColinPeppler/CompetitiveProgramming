MOVE = 2
ADD = 1
REMOVE = 1

x, y = input().split()

moves = [[0 for _ in range(10)] for _ in range(10)]

moves[0][6] = MOVE
moves[0][8] = ADD
moves[0][9] = MOVE
moves[1][7] = ADD
moves[2][3] = MOVE
moves[3][5] = MOVE
moves[3][9] = ADD
moves[5][6] = ADD
moves[5][9] = ADD
moves[6][8] = ADD
moves[6][9] = MOVE
moves[8][9] = REMOVE

moves[6][0] = MOVE
moves[8][0] = REMOVE
moves[9][0] = MOVE
moves[7][1] = REMOVE
moves[3][2] = MOVE
moves[5][3] = MOVE
moves[9][3] = REMOVE
moves[6][5] = REMOVE
moves[9][5] = REMOVE
moves[8][6] = REMOVE
moves[9][6] = MOVE
moves[9][8] = ADD

n = len(x)
count = 0
for i in range(n):
	x_dig = int(x[i])
	y_dig = int(y[i])

	count += moves[x_dig][y_dig]

if count == 2:
	print('yes')
else:
	print('no')
