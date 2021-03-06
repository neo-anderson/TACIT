package edu.usc.cssl.tacit.topicmodel.turbotopics.services;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by msamak on 3/14/16.
 */

/** This is the main class that generates TurboTopics for each topic that was generated by LDA.
 * STEP 1: Before using this tool to generate TurboTopics, LDA should be run on a corpus to generate a set of topics and significant words in those topics.
 * STEP 2: In this step we have to generate required files that will be used to generate TurboTopics
 * --corpus: This is a single file which has a list of lines. Each line is a line of text from the same corpus that was used to generate topics through LDA.
 *
 * --vocab: This a vocabulary file that lists all unique words that are used in the corpus
 *
 * --assignment: This is a word-to-topic assignment file. This file should be generated using the words from a given topic that was generated by LDA
 * The format of the file is as follows
 * For every line in the corpus file that was generated in the STEP 1, we generate line which has word-to-topic mapping. ie,
 * <line-number> <vocab-index-of-the-word>:<topic-to-which-this-word-is-assigned>. ex:
 * Let us assume the vocabulary list to be:
 * slow
 * and
 * study
 * wins
 * the
 * race
 * Let us assume the corpus is
 * [slow wins]
 * [study wins]
 * [lose race and wins race]
 * Let the word-topic assignment obtained from LDA be
 * slow - 1
 * and - 3
 * study - 2
 * wins - 1
 * the - 2
 * race - 4
 * Note: topics are represented as integers (ie, topic0 - 0, topic1 -1) and each word is assigned to a single topic which is the most probable topic for the word
 * With all of the above parameters, the assignment file generated will be:
 * 0 0:1 3:1
 * 1 2:2 3:1
 * 2 5:4 1:3 3:1 5:4
 *
 * --out: The output path in which the files for each topic will be generated
 *
 * --pre: The prefix for the output files
 *
 * --ntopics: Number of topics to generate. Should be same as the number of topics generated by LDA
 *
 * --pvalue: a value of type Double
 *
 * --use_perm: "true" if likelihood ratio score should be generated using permutations
 *
 * --min_count: minimum count of word occurences to be used
 *
 *
 * command to run the file,
 * enter the top level directory and type
 * javac -cp src/main/java/turbotopics/ src/main/java/turbotopics/LDAtopics.java
 * java -cp src/main/java/turbotopics/ src/main/java/turbotopics/LDAtopics <corpus_file> <word_topic_assignemnt_file> <vocabulary_file> <output_path> <number_of_topics> <min_count> <pvalue> <use_permutaion> <prefix>
 */
import edu.usc.cssl.tacit.common.TacitUtility;
public class LDAtopics {
    String corpus;
    String assignments;
    String vocab;
    boolean use_perm = false;
    Double pvalue = 0.001;
    Integer min_count = 25;
    Integer ntopics;
    String out;
    String prefix="topics.txt";

    //Reads the vocabulary and stores it in a list
    private ArrayList<String> read_vocab() throws Exception {
        String vocab_fname = vocab;
        ArrayList<String> terms = new ArrayList<String>();
        System.out.println("Reading vocabulary from "+vocab_fname);
        BufferedReader br = new BufferedReader(new FileReader(vocab_fname));
        String line = br.readLine();
        while(line != null){
            terms.add(line);
            line = br.readLine();
        }
        return terms;
    }

    /**
     given a word assignments file and a list of words,
     returns a list of dictionaries mapping words to topics
     * @param vocab : The list of words
     * @return
     * @throws Exception
     */
    private ArrayList<Map<Object,Object>> parse_word_assignments(ArrayList<String> vocab) throws Exception {
        String assigns_fname = this.assignments;
        ArrayList<Map<Object,Object>> results = new ArrayList<Map<Object,Object>>();
        BufferedReader br = new BufferedReader(new FileReader(assigns_fname));
        String line = br.readLine();
        while(line != null){
            Map<Object,Object> wordmap= new HashMap<Object,Object>();
            String[] mappings = line.split(" ");
            for(int i=1; i<mappings.length; i++){
                String[] termtopic = mappings[i].split(":");
                wordmap.put(vocab.get(Integer.parseInt(termtopic[0])),Integer.parseInt(termtopic[1]));
            }
            results.add(wordmap);
            line = br.readLine();
        }
        return results;
    }

    /**
     updates the counts of a counts object from the following
     * @param doc: line of text
     * @param topicmap : mapping of words to topics
     * @param topic : integer of the topic to focus on
     * @param counts_obj : counts object to update
     */
    private void update_counts_from_topic(String doc, final Map<Object,Object>topicmap, final Integer topic, Counts counts_obj){
        boolean topicFound = false;
        for(Object item: topicmap.values()){
            if((Integer)item == topic){
                topicFound = true;
                break;
            }
        }
        if(!topicFound) return;
        TestingFilter root_filter = new TestingFilter() {
            @Override
            public boolean apply(String s) {
                int comp = -1;
                if(topicmap.containsKey(s.split(" ")[0])){
                    comp = (Integer)topicmap.get(s.split(" ")[0]);
                }
                return (comp == topic);
            }
        };
        counts_obj.update_counts(doc,root_filter);
    }

    /**
     * Return a counts object that holds the counts - marginal counts, bigram counts, next_word counts etc
     * @param corpus: List of lines that form the corpus
     * @param assigns: List of maps. Each map is a word-to-topic map
     * @param topic: topic integer to focus on
     * @param use_perm: use permutaions to get the likelihood score
     * @param pvalue: precision value
     * @param min: Threshold minimum count
     * @return: A counts object
     */
    private Counts turbo_topic(ArrayList<String> corpus, ArrayList<Map<Object,Object>> assigns, final int topic, boolean use_perm, Double pvalue, int min){
        ArrayList<Object[]> iter_gen = new ArrayList<Object[]>();
        for(int i=0; i<corpus.size() && i<assigns.size(); i++){
            Object[] item = new Object[2];
            item[0] = corpus.get(i);
            item[1] = assigns.get(i);
            iter_gen.add(item);
        }
        TestingBiconsumer<Counts,Object[]> update_fun = new TestingBiconsumer<Counts, Object[]>() {
            @Override
            public void accept(Counts counts, Object[] objects) {
                update_counts_from_topic((String)objects[0],(Map<Object,Object>)objects[1],topic,counts);
            }
        };
        LikelihoodRatio test = new LikelihoodRatio(pvalue,use_perm);
        Counts cnts = Turbotopics.nested_sig_bigrams(iter_gen,update_fun,test,min);
        return cnts;
    }

    //constructor
    public LDAtopics(String corpus, String assignments, String vocab, String out, Integer ntopics, Integer min_count, Double pvalue, Boolean use_perm){
        this.corpus = corpus;
        this.assignments = assignments;
        this.vocab = vocab;
        this.ntopics = ntopics;
        this.out = out;
        if(min_count != null){
            this.min_count = min_count;
        }
        if(pvalue != null){
            this.pvalue = pvalue;
        }
        if(use_perm != null){
            this.use_perm = use_perm;
        }


    }

    //function to generate the turbo topics
    public void generateTurboTopics() throws Exception{
        ArrayList<String> vocab = read_vocab();
        ArrayList<Map<Object,Object>> assigns = parse_word_assignments(vocab);
        ArrayList<String> corpus = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(this.corpus));
        String line = br.readLine();
        while(line != null){
            corpus.add(line);
            line = br.readLine();
        }
        br.close();
        
        for(int topic=0; topic<this.ntopics; topic++){
            System.out.println("'writing topic "+topic);
            Counts sig_bigrams = turbo_topic(corpus,assigns,topic,this.use_perm,this.pvalue,this.min_count);
            if(this.out.charAt(this.out.length()-1) == '/'){
                this.out = this.out.substring(0,this.out.length()-1);
            }
            Turbotopics.write_vocab(sig_bigrams.marg,this.out+"/"+this.prefix, topic);
        }
        createRunReport(Calendar.getInstance().getTime()) ;
    }

    protected void createRunReport(Date dateObj) {
		TacitUtility.createRunReport(out, "Turbotopics Analysis", dateObj,null);
	}
}