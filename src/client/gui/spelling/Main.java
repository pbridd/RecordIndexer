package client.gui.spelling;

import java.io.IOException;

import client.gui.spelling.SpellCorrector.NoSimilarWordFoundException;

/**
 * A simple main class for running the spelling corrector
 */
public class Main {
	
	/**
	 * Give the dictionary file name as the first argument and the word to correct
	 * as the second argument.
	 */
	public static void main(String[] args) throws NoSimilarWordFoundException, IOException {
		
		String dictionaryFileName = args[0];
		if(args.length < 2)
			throw new NoSimilarWordFoundException();
		String inputWord = args[1];
		
		/**
		 * Create an instance of your corrector here
		 */
		SpellCorrector corrector = new Checker();

		
		corrector.useDictionary(dictionaryFileName);
		String suggestion = corrector.suggestSimilarWord(inputWord);
		
		System.out.println("Suggestion is: " + suggestion);
	}

}
