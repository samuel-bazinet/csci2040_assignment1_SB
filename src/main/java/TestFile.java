import java.text.DecimalFormat;

public class TestFile {

    private String filename;
    private double spamProbability;
    private String actualClass;
    private String spamProbabilityRounded;
    private boolean flaggedSpam;
    
    /**
     * TestFile stores the info needed to test the effectiveness of the program 
     * @param filename : the name of the text file
     * @param spamProbability : the probability of being a spam
     * @param actualClass : the actual class (spam or ham)
     */
    public TestFile(String filename, double spamProbability, String actualClass) {
        this.filename = filename; 
        this.spamProbability = spamProbability; 
        this.actualClass = actualClass; 
        this.spamProbabilityRounded = this.getSpamProbRounded();
        this.flaggedSpam = (spamProbability > 0.5);
    }

    public String getFilename(){ 
        return this.filename;
    }

    public double getSpamProbability(){ 
        return this.spamProbability; 
    }

    public String getSpamProbRounded(){ 
        DecimalFormat df = new DecimalFormat("0.00000"); 
        return df.format(this.spamProbability);
    }

    public String getSpamProbabilityRounded() {
        return this.spamProbabilityRounded;
    }

    public String getActualClass(){
        return this.actualClass;
    }

    public boolean getFlaggedSpam() {
        return this.flaggedSpam;
    }

    public String getFlaggedSpamString() {
        return String.valueOf(this.flaggedSpam);
    }

    public void setFilename(String value) { 
        this.filename = value; 
    }

    public void setSpamProbability(double val){
        this.spamProbability = val; 
    }

    public void setActualClass(String value){
        this.actualClass=value; 
    }

    public void setFlaggedSpam(boolean flagged) {
        this.flaggedSpam = flagged;
    }
}