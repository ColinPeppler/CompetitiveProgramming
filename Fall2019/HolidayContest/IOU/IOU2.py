#Questions
# does order matter? -> yes
n, m = map(int, input().split())
def main():

	money = [[0 for _ in range(n)] for _ in range(n)]
	cash = [0 for _ in range(n)]

	for _ in range(m):
		u, v, c = map(int, input().split())
		money[u][v] += c
		cash[u] -= c
		cash[v] += c


	flag = True
	for i in range(n):
		if cash[i] != 0:
			flag = False
	
	if flag:
		print('0')
		return

	for i in range(n):
		visited = [False for i in range(n)]
		lst = []
		flag, lst, minCost = find_cycle(i, i, money, visited, True, lst, 100000000)
		if flag:
			remove_dfs(i, lst, money, cash, minCost)
			i -= 1

	ans = []
	for i in range(n):
		for j in range(n):
			debt = money[i][j] - money[j][i]
			if debt > 0: 
				ans.append((i, j, debt))

	print(len(ans))
	for (u, v, c) in ans:
		print("%d %d %d" % (u,v,c))	
	
def remove_dfs(u, lst, money, cash, minCost):
	if len(lst) == 0:
		return
	v = lst.pop(0)
	cash[u] += minCost
	cash[v] -= minCost
	money[u][v] -= minCost
	remove_dfs(v, lst, money, cash, minCost)

def find_cycle(start, idx, money, visited, first, lst, minCost):
	if start == idx and not first:
		return True, lst, minCost
	for i in range(n):	
		if money[idx][i] == 0 or visited[i]:
			continue
		visited[i] = True
		lst.append(i)
		minCost = min(minCost, money[idx][i])
		flag, _list, _min = find_cycle(start, i, money, visited, False, lst, minCost)
		if flag:
			return flag, _list, _min
	return False, None, None

		
main()
