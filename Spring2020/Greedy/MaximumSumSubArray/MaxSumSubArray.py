import sys

input()
arr = list(map(int, input().split()))

global_max = -sys.maxsize - 1
local_max = 0

for num in arr:
	local_max += num
	if local_max < 0:
		local_max = 0
	global_max = max(local_max, global_max)

print(global_max)
