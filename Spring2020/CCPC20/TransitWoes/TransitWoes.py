START, END, N = map(int, input().split())
walking_times = list(map(int, input().split()))
riding_times = list(map(int, input().split()))
bus_arrival_times = list(map(int, input().split()))

time = START
for i in range(N):
	time += walking_times[i]					# walk
	if time % bus_arrival_times[i] != 0:
		time += bus_arrival_times[i] - time % bus_arrival_times[i]		# wait
	time += riding_times[i]						# ride

time += walking_times[-1]						# walk to class

print("yes" if time <= END else "no")
