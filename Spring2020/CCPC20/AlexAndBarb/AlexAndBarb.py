ALEXWINS = 0
BARBWINS = 1
NCARDS, M, N = map(int, input().split())

# -1 for undecided, 0 for Alex wins, 1 for Barb wins
TABLE = [-1 for _ in range(NCARDS+1)]

def rec(curr, alexTurn):
	if TABLE[curr] != -1:
		return TABLE[curr]
	
	# base case
	if curr < M:
		TABLE[curr] = ALEXWINS if alexTurn else BARBWINS
		return TABLE[curr]

	alexCanWin = False
	for d in range(M, N+1):
		if curr-d < 0:
			break
		res = rec(curr-d, not alexTurn)
		if alexTurn and res == ALEXWINS: 
			alexCanWin = True
		if not alexTurn and res == BARBWINS:
			alexCanWin = False
	
	if alexTurn and alexCanWin:
		TABLE[curr] = ALEXWINS
	else:
		TABLE[curr] = BARBWINS
	return TABLE[curr]

if rec(NCARDS, True) == ALEXWINS:
	print('Alex')
else:
	print('Barb')
