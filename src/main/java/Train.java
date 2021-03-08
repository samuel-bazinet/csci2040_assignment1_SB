import java.io.*;
import java.util.*;

public class Train {

    private static Map<String, Double> probabilitySpamGivenHam;

    public static void calculateProbabilitySpamGivenHam(Map<String, Double> spam, Map<String, Double> ham) {

        probabilitySpamGivenHam = new TreeMap<>();

        Set<String> keys = spam.keySet();
        Iterator<String> keyIterator = keys.iterator();

        while(keyIterator.hasNext()){
			String key = keyIterator.next();
            //System.out.println("Calculating probability of " + key + " being spam given ham");
			double countSpam = spam.get(key);

            if (countSpam <= 0) {
                probabilitySpamGivenHam.put(key, 0.0d);
                continue;
            }

            

            try {
                double countHam = ham.get(key);
                probabilitySpamGivenHam.put(key, (countSpam/(countSpam + countHam)));
            } catch (NullPointerException e) {
                probabilitySpamGivenHam.put(key, 1.0d);
            } 
			
		}

    }

    public static void train() {
        
        WordCounter spamWC = new WordCounter();
        WordCounter hamWC = new WordCounter();
        String localDir = System.getProperty("user.dir");
        spamWC.countWords(localDir + "/data/train/spam/", "outSpam");
        hamWC.countWords(localDir + "/data/train/ham/", "outHam");
        hamWC.countWords(localDir + "/data/train/ham2/", "outHam");

        calculateProbabilitySpamGivenHam(spamWC.getProbability(), hamWC.getProbability());

        /*
        Set<String> keys = probabilitySpamGivenHam.keySet();
        Iterator<String> keyIterator = keys.iterator();

        
        while (keyIterator.hasNext()) {
            String key = keyIterator.next();
            System.out.println(key + " : " + probabilitySpamGivenHam.get(key).toString());
        }
        */

    }

    public static Map<String, Double> getProbabilitySpamGivenHam() {
        train();
        return probabilitySpamGivenHam;
    }
}