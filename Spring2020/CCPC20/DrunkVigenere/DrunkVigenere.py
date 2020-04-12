encrypted = input()
key = input()
decrypted = ''

def convert(char, shift_val):
	x = shift_val + ord(char)
	if x > ord('Z'):
		# + 1 because we're taking the extra step to move to A
		shift_val -= ord('Z') - ord(char) + 1
		char = 'A'
	elif x < ord('A'):
		# + 1 because we're taking the extra step to move to Z
		shift_val += ord(char) - ord('A') + 1
		char = 'Z'
	
	new_char = chr(ord(char) + shift_val)
	return new_char

for i in range(len(key)):
	encrypted_char = encrypted[i]	
	key_char = key[i]

	key_val = ord(key[i]) - ord('A')

	if i % 2 == 0:		# even-indexed
		key_val *= -1	
	new_char = convert(encrypted_char, key_val)
	decrypted += new_char

print(decrypted)
