'''
Generic DFS with visited
1.) using iterative DFS with Stack to avoid stack overflow
	- this is because Python's declares a simple integer as an object which
		will requires a lot of memory for each function call
	- also we can Python provides versatile tuples, so we can put our current
		row & column index along with our path, which allows us to keep track
		of our path so far
2.) using a visited set to avoid an infinite loop
	- if we visit the same car again, then we'll never reach an empty spot;
		essentially, we have a cycle without ever reaching a goal (empty spot)
		when instead we want a path from start -> goal.
'''

# Parse
m, n = list(map(int, input().split()))
graph = [list(map(int, input().split())) for i in range(m)]
start_x, start_y = list(map(int, input().split()))

# Get row and column index of the empty spot
goal_x, goal_y = [(index, row.index(-1)) for index, row in enumerate(graph) if -1 in row][0]

# Initialize data structures
ans = []
path = []
visited = set() 	
stack = []

stack.append((start_x-1, start_y-1, path))

while (len(stack) > 0): 						# DFS
	i, j, path = stack.pop()

	if i == goal_x and j == goal_y: 			# reached our goal
		ans.extend(reversed(path))
		break

	currCar = graph[i][j]
	if currCar in visited or currCar == -2: 	# hit a cycle or dead end
		break

	visited.add(currCar)
	path.append(currCar)

	if j > 1 and currCar == graph[i][j-1]:		# left
		stack.append((i, j-2, path))
	if j < n-2 and currCar == graph[i][j+1]:	# right
		stack.append((i, j+2, path))
	if i > 1 and currCar == graph[i-1][j]: 		# up
		stack.append((i-2, j, path))
	if i < m-2 and currCar == graph[i+1][j]: 	# down
		stack.append((i+2, j, path))


print("impossible" if len(ans) == 0  else ''.join(str(car) + ' ' for car in ans))
