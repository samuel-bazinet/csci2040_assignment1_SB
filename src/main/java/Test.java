import java.io.*;
import java.util.*;

public class Test {

    /**
     * calculateProbabilitySpam sets up the file to be parsed and calculate the odds of being spam
     * @param filePath : The path of the file being tested
     * @param mapProbabilitySpamGivenHam : The map containing the odds of every word being in a spam file
     * @return The odds of being spam
     */
    public static double calculateProbabilitySpam(String filePath, Map<String, Double> mapProbabilitySpamGivenHam) {

        try {
            // We parse the file 
            String[] parsedFile = parseFile(new File(filePath));
            // Then we calculate the odds of it being spam
            return getProbabilitySpam(parsedFile, mapProbabilitySpamGivenHam);
        } catch(FileNotFoundException e){
			System.err.println("Invalid input dir.");
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}

        // Returns 0 as a default value
        return 0.0d;
        
    }

    /**
     * getProbabilitySpam goes through every word in a file and calculates the odds of the file being spam
     * @param parsedFile : The list of words contained in the file 
     * @param mapProbabilitySpamGivenHam : The map containing the odds of every word found in spam
     * @return The odds of being spam
     */
    private static double getProbabilitySpam(String[] parsedFile, Map<String, Double> mapProbabilitySpamGivenHam) {

        double n = 0.0d;

        // We go through every word in the file
        for (String word: parsedFile) { 
            
            try {
                // We calculate the odds of the file being spam
                double probabilitySpamGivenHam = mapProbabilitySpamGivenHam.get(word);
                n += Math.log(1 - probabilitySpamGivenHam) - Math.log(probabilitySpamGivenHam);
            } catch (NullPointerException e) {
                // We use the try/catch statement to skip words where we do not have any info
                continue;
            }
        }

        // We calculate the probability of spam
        double out = (double)(1.0)/(double)(1.0+Math.pow(Math.E,n));

        return out;
    }

    /**
     * parseFile goes through a file and makes an array containing every word in it
     * @param file : The file being decomposed into an array of string
     * @return An array of string containing the words in the file
     * @throws IOException
     */
    private static String[] parseFile(File file) throws IOException{

        // We store the words in an array to speed up the reading
        String[] outArr = new String[100];

        int increment = 0;

        Scanner scanner = new Scanner(file);
        
        // scanning token by token
        while (scanner.hasNext()){

            String  token = scanner.next();

            if (isValidWord(token)){
                
                outArr[increment] = token;
                increment++;

                // If the array is too small, we enlarge it
                if (outArr.length == increment) {
                    outArr = enlargeArray(outArr);
                }
            }
        }

        scanner.close();

        return outArr;

    }

    /**
     * enlargeArray enlarges the array if they are more words than the size of the array
     * @param inArr : The array to be enlarged
     * @return  The enlarged array
     */
    private static String[] enlargeArray(String[] inArr) {

        // The array is just 100 elements larger
        String[] outArr = new String[inArr.length + 100];
        for (int i = 0; i < inArr.length; i++) {
            outArr[i] = inArr[i];
        } 
        return outArr;

    }   

    /**
     * isValidWord verifies if a word is real or just a random string of characters
     * @param word : The word to be verified
     * @return A boolean representing whether or not the word is valid
     */
    private static boolean isValidWord(String word){
		String allLetters = "^[a-zA-Z]+$";
		// returns true if the word is composed by only letters otherwise returns false;
		return (word.matches(allLetters) && word.length() > 3);
			
	}

}