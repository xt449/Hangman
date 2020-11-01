package com.github.xt449.hangman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author Jonathan Talcott (xt449 / BinaryBanana)
 */
public class Main {

	private static final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static final BufferedReader bufferedInputReader = new BufferedReader(new InputStreamReader(System.in));
	private static final Random random = new Random();
	private static final String[] hangerStates = {
			'\n' +
					"          _____\n" +
					"          |   |\n" +
					"          |\n" +
					"          |\n" +
					"          |\n" +
					"          |\n" +
					"          |\n" +
					"        __|______\n",

			'\n' +
					"          _____\n" +
					"          |   |\n" +
					"          |   O\n" +
					"          |\n" +
					"          |\n" +
					"          |\n" +
					"          |\n" +
					"        __|______\n",

			'\n' +
					"          _____\n" +
					"          |   |\n" +
					"          |   O\n" +
					"          |   |\n" +
					"          |   |\n" +
					"          |\n" +
					"          |\n" +
					"        __|______\n",

			'\n' +
					"          _____\n" +
					"          |   |\n" +
					"          |   O\n" +
					"          |   |\n" +
					"          |   |\n" +
					"          |  /\n" +
					"          |\n" +
					"        __|______\n",

			'\n' +
					"          _____\n" +
					"          |   |\n" +
					"          |   O\n" +
					"          |   |\n" +
					"          |   |\n" +
					"          |  / \\\n" +
					"          |\n" +
					"        __|______\n",

			'\n' +
					"          _____\n" +
					"          |   |\n" +
					"          |   O\n" +
					"          |  \\|\n" +
					"          |   |\n" +
					"          |  / \\\n" +
					"          |\n" +
					"        __|______\n",

			'\n' +
					"          _____\n" +
					"          |   |\n" +
					"          |   O\n" +
					"          |  \\|/\n" +
					"          |   |\n" +
					"          |  / \\\n" +
					"          |\n" +
					"        __|______\n",
	};

	private static byte wrongGuesses = 0;
	private static char[] letters;

	public static void main(String[] args) throws IOException {
		System.out.print("Enter desired world length: ");

		int number;
		do {
			try {
				number = Integer.parseInt(bufferedInputReader.readLine());
				break;
			} catch(NumberFormatException exc) {
				System.out.println("Invalid number!");
			}
		} while(true);
		final int wordLength = number;

		final List<String> words = new BufferedReader(new InputStreamReader(Main.class.getClassLoader().getResourceAsStream("words_alpha.txt")))
				.lines()
				.filter(word -> word.length() == wordLength)
				.collect(Collectors.toList());

		final char[] word = words.get(random.nextInt(words.size())).toUpperCase().toCharArray();
		int remainingLetters = word.length;

		letters = new char[word.length];
		Arrays.fill(letters, '_');

		System.out.println(word);
		System.out.println("The word is " + letters.length + " letters long:");

		do {
			writeOut();
			final char guess = getNextGuess();

			boolean letterFound = false;

			for(int i = 0; i < letters.length; i++) {
				if(word[i] == guess) {
					letters[i] = guess;
					remainingLetters--;
					letterFound = true;
				}
			}

			if(!letterFound) {
				System.out.println("Word does not contain that letter!");

				if(++wrongGuesses >= 6) {
					writeOut();
					System.out.println("Defeat!");
				}
			}
		} while(remainingLetters > 0);

		writeOut();
		System.out.println("Victory!");
	}

	private static char getNextGuess() throws IOException {
		do {
			final String line = bufferedInputReader.readLine();
			if(line.length() == 1) {
				char character = line.charAt(0);
				if(character > 90) {
					character -= 32;
				}
				for(int i = 0; i < alphabet.length; i++) {
					if(alphabet[i] == character) {
						alphabet[i] = '_';
						return character;
					}
				}
				System.out.println("Invalid letter! (Already guessed or not an actual letter)");
			} else {
				System.out.println("Input must be one letter in length!");
			}
		} while(true);
	}

	private static void writeOut() {
		System.out.println(hangerStates[wrongGuesses]);

		final char[] wordIndent = new char[12 - letters.length];
		Arrays.fill(wordIndent, ' ');
		System.out.print(wordIndent);

		for(int i = 0; i < letters.length; i++) {
			System.out.print(' ');
			System.out.print(letters[i]);
		}
		System.out.println();
	}
}
