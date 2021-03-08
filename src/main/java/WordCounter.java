import java.io.*;
import java.util.*;


public class WordCounter{
	
	private Map<String, Integer> wordCounts;

	private int totalFiles;
	
	public WordCounter(){
		wordCounts = new TreeMap<>();
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
	
	public void outputWordCount(File output) throws IOException{
		System.out.println("Saving word counts to file:" + output.getAbsolutePath());
		
		if (!output.exists()){
			output.createNewFile();
			if (output.canWrite()){
				PrintWriter fileOutput = new PrintWriter(output);
				
				Set<String> keys = wordCounts.keySet();
				Iterator<String> keyIterator = keys.iterator();
				
				while(keyIterator.hasNext()){
					String key = keyIterator.next();
					int count = wordCounts.get(key);
					
					fileOutput.println(key + ": " + count);
				}
				
				fileOutput.close();
			}
		}else{
			System.out.println("Error: the output file already exists: " + output.getAbsolutePath());
			if (output.canWrite()){
				PrintWriter fileOutput = new PrintWriter(output);
				
				Set<String> keys = wordCounts.keySet();
				Iterator<String> keyIterator = keys.iterator();
				
				while(keyIterator.hasNext()){
					String key = keyIterator.next();
					int count = wordCounts.get(key);
					
					fileOutput.println(key + ": " + count);
				}
				System.out.println("Over");
				fileOutput.close();
			}
		}
		
	}

	public Map<String, Integer> getWordCounts() { return wordCounts;}
	public int getTotalFiles() { return totalFiles; }
	
	//main method
	public void countWords(String inFolder, String outFile) {
		System.out.println("Hello");
		try{
			this.parseFile(new File(inFolder));
			this.outputWordCount(new File(outFile));
		}catch(FileNotFoundException e){
			System.err.println("Invalid input dir.");
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		
	}
	
}