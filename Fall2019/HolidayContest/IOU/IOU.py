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
		flag, lst = find_cycle(i, i, money, visited, True, lst, -1)
		if flag:
			remove_dfs(i, lst, money, cash)

	ans = []
	for i in range(n):
		for j in range(n):
			curr_cash = cash[i]
			debt = money[i][j] - money[j][i]
			
			if debt > 0: 
				if curr_cash >= debt:
					cash[i] -= debt
					cash[j] += debt
				else:
					ans.append((i, j, debt))

	print(len(ans))
	for (u, v, c) in ans:
		print("%d %d %d" % (u,v,c))	
	
def remove_dfs(u, lst, money, cash):
	if len(lst) == 0:
		return
	v = lst.pop(0)
	cash[u] += money[u][v] 
	cash[v] -= money[u][v] 
	money[u][v] = 0
	remove_dfs(v, lst, money, cash)

def find_cycle(start, idx, money, visited, first, lst, edge_weight):
	if start == idx and not first:
		return True, lst
	for i in range(n):	
		if money[idx][i] == 0 or visited[i] or (money[idx][i] != edge_weight and not first):
			continue
		visited[i] = True
		lst.append(i)
		if first:
			edge_weight = money[idx][i]
		flag, _list = find_cycle(start, i, money, visited, False, lst, edge_weight)
		if flag:
			return flag, _list
	return False, None
		
main()
