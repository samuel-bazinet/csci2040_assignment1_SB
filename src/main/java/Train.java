import java.util.*;

public class Train {

    private static Map<String, Double> probabilitySpamGivenHam;

    /**
     * calculateProbabilitySpamGivenHam will find the probability of every word found in the training spam files to be found in a 
     * random spam file and store it in the static map probabilitySpamGivenHam
     * @param spam : The map containing the words found in spam files and their frequency
     * @param ham : The map containing the words found in ham files and their frequency
     */
    private static void calculateProbabilitySpamGivenHam(Map<String, Double> spam, Map<String, Double> ham) {

        // We instancialize probabilitySpamGivenHam
        probabilitySpamGivenHam = new TreeMap<>();

        Set<String> keys = spam.keySet();
        Iterator<String> keyIterator = keys.iterator();

        // We go through every word in the spam map
        while(keyIterator.hasNext()){
			String key = keyIterator.next();
			double countSpam = spam.get(key);

            try {
                // We calculate the probability of the word being in a spam file
                double countHam = ham.get(key);
                probabilitySpamGivenHam.put(key, (countSpam/(countSpam + countHam)));
            } catch (NullPointerException e) {
                probabilitySpamGivenHam.put(key, 1.0d);
            } 
			
		}

    }

    /**
     * train sets up the word counters and call the probability calculator
     * @param path : The path of the folder containing the "test" and "train" files
     */
    private static void train(String path) {
        
        WordCounter spamWC = new WordCounter();
        WordCounter hamWC = new WordCounter();
        // We collect the spam words and their frequency
        spamWC.countWords(path + "/train/spam/");
        // We collect the ham words and their frequency
        hamWC.countWords(path + "/train/ham/");
        hamWC.countWords(path + "/train/ham2/");

        // We calculate the probability of each word being in a spam file
        calculateProbabilitySpamGivenHam(spamWC.getProbability(), hamWC.getProbability());

    }

    /**
     * getProbabilitySpamGivenHam is the controller of the train file
     * @param path : The path to the folder containing the "test" and "train" folders
     * @return The map containing the probability of a word being in a spam file
     */
    public static Map<String, Double> getProbabilitySpamGivenHam(String path) {
        train(path);
        return probabilitySpamGivenHam;
    }
}