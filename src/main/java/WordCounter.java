import java.io.*;
import java.util.*;


public class WordCounter{
	
	private Map<String, Integer> wordCounts;
	private Map<String, Double> probability;

	private int totalFiles;
	
	/**
	 * Constructor setting up the WordCounter
	 */
	public WordCounter(){
		wordCounts = new TreeMap<>();
		probability = new TreeMap<>();
		totalFiles = 0;
	}
	
	/**
	 * parseFile goes through every word, confirms them, then sends them to the counter
	 * @param folder : The folder containing the files being read
	 * @throws IOException
	 */
	public void parseFile(File folder) throws IOException{
		// We put all the files in an array to go through them		
		File[] listOfFiles = folder.listFiles();
		// We go through every files in the folder
		for (File file: listOfFiles) {
			// We print the status
			System.out.println("Starting parsing the file# " + totalFiles + ":" + file.getAbsolutePath());
			// We increment the number of file being read
			totalFiles++;
			// We use a scanner to go through every word in the file
			Scanner scanner = new Scanner(file);
			// scanning token by token
			while (scanner.hasNext()){
				// We store the word in a string
				String  token = scanner.next();
				// We confirm the word is a word
				if (isValidWord(token)){
					// We send it to countWord for it to be added to the map
					countWord(token);
				}
			}
		}
	}

	/**
	 * isValidWord confirms that the word is a word
	 * @param word : A supposed word
	 * @return A boolean saying if the word is a word or not
	 */
	private boolean isValidWord(String word){
		String allLetters = "^[a-zA-Z]+$";
		// returns true if the word is composed by only letters otherwise returns false;
		return (word.matches(allLetters) && word.length() > 3);
			
	}
	
	/**
	 * countWord puts the words in a map, and increment the frequency if the words are already in it
	 * @param word : The word to be added
	 */
	private void countWord(String word){
		if(wordCounts.containsKey(word)){
			int previous = wordCounts.get(word);
			wordCounts.put(word, previous+1);
		}else{
			wordCounts.put(word, 1);
		}
	}

	/**
	 * calculateProbability calculates the odds of each word being present in the set of files given
	 */
	public void calculateProbability() {

		Set<String> keys = wordCounts.keySet();
		Iterator<String> keyIterator = keys.iterator();
		
		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			int count = wordCounts.get(key);
			// This calculate the probability of a word being found in the set of files
			probability.put(key, (double)(count)/(totalFiles));
		}

	}

	public Map<String, Integer> getWordCounts() { return wordCounts; }
	public int getTotalFiles() { return totalFiles; }
	public Map<String, Double> getProbability() { return probability; }

	
	/**
	 * countWords is the controller of the WordCounter class
	 * @param inFolder : The folder containing the files to be parsed
	 */
	public void countWords(String inFolder) {
		try{
			this.parseFile(new File(inFolder));
			this.calculateProbability();
		}catch(FileNotFoundException e){
			System.err.println("Invalid input dir.");
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
	}
	
}