NOTES = ['C', 'C#', 'D', 'D#', 'E', 'F', 'F#', 'G', 'G#', 'A', 'A#', 'B']

def main():
	NUM_NOTES = input()
	melodyA = list(input().split())
	melodyB = list(input().split())

	if isTransposition(melodyA, melodyB):
		print("Transposition")
	elif isRetrograde(melodyA, melodyB):
		print('Retrograde')
	elif isInversion(melodyA, melodyB):
		print('Inversion')
	else:
		print('Nonsense')

def isTransposition(melodyA, melodyB):	
	idx1 = NOTES.index(melodyA[0])
	idx2 = NOTES.index(melodyB[0])
	c = idx1 - idx2
	for i in range(1, len(melodyA)):
		idx1 = NOTES.index(melodyA[i])
		idx2 = NOTES.index(melodyB[i])
		diff = idx1 - idx2
		if diff != c:
			return False
	return True

def isRetrograde(melodyA, melodyB):
	return melodyA == [n for n in reversed(melodyB)]

def isInversion(melodyA, melodyB):
	if melodyA[0] != melodyB[0]:
		return False

	first_semitone = NOTES.index(melodyA[0])
	for i in range(1, len(melodyA)):
		x = first_semitone - NOTES.index(melodyA[i])
		
		if x < 0:	# make all shift down situations a shift up situation
			x = len(NOTES) + x

		idx = (first_semitone + x) % len(NOTES)
		pred = NOTES[idx]
		
		if pred != melodyB[i]:
			return False

	return True


	

main()
