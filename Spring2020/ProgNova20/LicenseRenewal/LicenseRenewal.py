num_customers, counter_time1, counter_time2 = map(int, input().split())
customer_times = list(map(int, input().split()))

stack = []
max_num_customers_served = -1
stack.append((counter_time1, counter_time2, 0))

while len(stack) != 0:
	counter_time1, counter_time2, index = stack.pop()


	if counter_time1 < 0 or counter_time2 < 0 or index == num_customers:
		continue

	curr_customer_time = customer_times[index]
	if counter_time1 < curr_customer_time and counter_time2 < curr_customer_time:
		continue
	

	if counter_time1 - curr_customer_time >= 0 and counter_time2 >= 0:
		max_num_customers_served = max(max_num_customers_served, index)
		stack.append((counter_time1 - curr_customer_time, counter_time2, index+1))
	if counter_time2 - curr_customer_time >= 0 and counter_time1 >= 0:
		max_num_customers_served = max(max_num_customers_served, index)
		stack.append((counter_time1, counter_time2 - curr_customer_time, index+1))
	
print((max_num_customers_served+1))	
