import java.util.*;

public class SpellingBee {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String base = in.nextLine();

		int NUM_DICT_WORDS = in.nextInt();
		List<String> words = new ArrayList<>();
		in.nextLine();
		for (int i = 0; i < NUM_DICT_WORDS; i++) {
			String word = in.nextLine();	
			words.add(word);
		}

		Set<Character> characters = new HashSet<>();
		for (int i = 0; i < base.length(); i++) {
			characters.add(base.charAt(i));
		}
		char baseChar = base.charAt(0);

		for (String word : words) {
			if (isValid(word, baseChar, characters))
				System.out.println(word);	
		}
	}

	public static boolean isValid(String word, char baseChar, Set<Character> chars) {
		Set<Character> charsForWord = new HashSet<>();
		boolean flag = false;
		for (int i = 0; i < word.length(); i++) {
			char ch = word.charAt(i);
			charsForWord.add(ch);
			if (!flag) {
				flag = ch == baseChar;	
			}
		}

		for (Character ch : charsForWord) {
			if (!chars.contains(ch)) {
				return false;
			}
		}

		return flag && word.length() >= 4;
	}
}
