import java.io.*;
import java.util.*;


public class WordCounter{
	
	private Map<String, Integer> wordCounts;
	private Map<String, Double> probability;

	private int totalFiles;
	
	public WordCounter(){
		wordCounts = new TreeMap<>();
		probability = new TreeMap<>();
		totalFiles = 0;
	}
	
	public void parseFile(File folder) throws IOException{
		System.out.println(folder.getAbsolutePath());
		File[] listOfFiles = folder.listFiles();
		for (File file: listOfFiles) {
			System.out.println("Starting parsing the file# " + totalFiles + ":" + file.getAbsolutePath());
			totalFiles++;
			
			Scanner scanner = new Scanner(file);
			// scanning token by token
			while (scanner.hasNext()){
				String  token = scanner.next();
				if (isValidWord(token)){
					countWord(token);
				}
			}
		}
	}
	
	private boolean isValidWord(String word){
		String allLetters = "^[a-zA-Z]+$";
		// returns true if the word is composed by only letters otherwise returns false;
		return word.matches(allLetters);
			
	}
	
	private void countWord(String word){
		if(wordCounts.containsKey(word)){
			int previous = wordCounts.get(word);
			wordCounts.put(word, previous+1);
		}else{
			wordCounts.put(word, 1);
		}
	}

	public void calculateProbability() {

		Set<String> keys = wordCounts.keySet();
		Iterator<String> keyIterator = keys.iterator();
		
		while(keyIterator.hasNext()){
			String key = keyIterator.next();
			int count = wordCounts.get(key);
			probability.put(key, (double)(count)/(totalFiles));
		}

	}

	public Map<String, Integer> getWordCounts() { return wordCounts; }
	public int getTotalFiles() { return totalFiles; }
	public Map<String, Double> getProbability() { return probability; }

	
	
	//main method
	public void countWords(String inFolder, String outFile) {
		System.out.println("Hello");
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