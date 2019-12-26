password, string = input().split()

count = len(password)
for char in string:
	if char in password:
		if char == password[0]:
			count -= 1
			password = password[1:]
		else:
			break
			

# error the password has been manipulated so obviously it will pass if == len
if count == 0:
	print('PASS')
else:
	print('FAIL')
