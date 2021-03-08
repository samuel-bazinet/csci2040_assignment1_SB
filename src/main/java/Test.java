import java.lang.*;
import java.io.*;
import java.util.*;

public class Test {

    public static double calculateProbabilitySpam(String filePath) {

        try {
            String[] parsedFile = parseFile(new File(filePath));
            return getProbabilitySpam(parsedFile);
        } catch(FileNotFoundException e){
			System.err.println("Invalid input dir.");
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}

        return 0.0d;
        
    }

    private static double getProbabilitySpam(String[] parsedFile) {

        double n = 0.0d;

        Map<String, Double> mapProbabilitySpamGivenHam = Train.getProbabilitySpamGivenHam();

        for (String word: parsedFile) {
            
            try {
                double probabilitySpamGivenHam = mapProbabilitySpamGivenHam.get(word);
                n += Math.log(1 - probabilitySpamGivenHam) - Math.log(probabilitySpamGivenHam);
            } catch (NullPointerException e) {
                continue;
            }
        }

        double out = (double)(1)/(double)(1.0+Math.pow(Math.E,n));

        return out;
    }

    private static String[] parseFile(File file) throws IOException{

        String[] outArr = new String[100];

        int increment = 0;

        Scanner scanner = new Scanner(file);
        
        // scanning token by token
        while (scanner.hasNext()){

            String  token = scanner.next();

            if (isValidWord(token)){
                
                outArr[increment] = token;
                increment++;

                if (outArr.length == increment) {
                    outArr = enlargeArray(outArr);
                }
            }
        }

        scanner.close();

        return outArr;

    }

    private static String[] enlargeArray(String[] inArr) {

        String[] outArr = new String[inArr.length + 100];
        for (int i = 0; i < inArr.length; i++) {
            outArr[i] = inArr[i];
        } 
        return outArr;

    }

    private static boolean isValidWord(String word){
		String allLetters = "^[a-zA-Z]+$";
		// returns true if the word is composed by only letters otherwise returns false;
		return word.matches(allLetters);
			
	}

}