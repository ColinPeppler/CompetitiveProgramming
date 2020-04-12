n = int(input())

projects = []
for _ in range(n):
	name, deadline, duration = input().split()
	deadline = int(deadline)
	duration = int(duration)

	projects.append((name, deadline, duration))

projects = sorted(projects, key=lambda x: (x[1], x[2]))

res = []

timer = 0
max_lateness = 0
for project in projects:
	name, deadline, duration = project
	timer += duration
	if deadline <= timer:
		max_lateness = max(timer-deadline, max_lateness)
		res.append(name)

print(max_lateness)
for name in sorted(res, key=lambda x: x):
	print(name)
