import math
import functools

n = int(input())
times = list(map(int, input().split()))

times.sort()

for i in range(1, n):
	times[i] = times[i-1] + times[i]


print(sum(times)/n)
