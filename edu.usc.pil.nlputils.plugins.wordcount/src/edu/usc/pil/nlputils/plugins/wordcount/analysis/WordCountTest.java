package edu.usc.pil.nlputils.plugins.wordcount.analysis;

import edu.usc.pil.nlputils.plugins.wordcount.utilities.Trie;

import java.io.IOException;
import java.util.ArrayList;

import snowballstemmer.PorterStemmer;



public class WordCountTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/**/
		//mainTest();
		//trieTest();
		multiTest();
		/*
		PorterStemmer stemmer = new PorterStemmer();
		stemmer.setCurrent("legalization");
		if (stemmer.stem()){
			System.out.println(stemmer.getCurrent());
		}
		*/
	}
	
	public static void multiTest(){
		System.out.println("Starting word count");
		WordCount wc = new WordCount();
		String files[];
		//files = new String[]{"c:/LIWC/LTest/L1.txt", "c:/LIWC/LTest/L2.txt", "c:/LIWC/LTest/L3.txt", "c:/LIWC/LTest/L4.txt", "c:/LIWC/LTest/L5.txt", "c:/LIWC/LTest/L6.txt", "c:/LIWC/LTest/L7.txt", "c:/LIWC/LTest/L8.txt"};
		//files = new String[]{"c:/LIWC/FTest/1.txt", "c:/LIWC/FTest/2.txt", "c:/LIWC/FTest/3.txt", "c:/LIWC/FTest/4.txt", "c:/LIWC/FTest/5.txt", "c:/LIWC/FTest/6.txt", "c:/LIWC/FTest/7.txt", "c:/LIWC/FTest/8.txt", "c:/LIWC/FTest/9.txt", "c:/LIWC/FTest/10.txt", "c:/LIWC/FTest/11.txt", "c:/LIWC/FTest/F1.txt", "c:/LIWC/FTest/F2.txt", "c:/LIWC/FTest/F3.txt", "c:/LIWC/FTest/F4.txt"};
		files = new String[]{"c:/LIWC/LTest/T42.txt"};
		//files = new String[]{"c:/LIWC/LTest/T1.txt", "c:/LIWC/LTest/T2.txt", "c:/LIWC/LTest/T3.txt","c:/LIWC/LTest/T4.txt"};
		//files = new String[]{"c:/LIWC/C/One1.txt", "c:/LIWC/C/One2.txt", "c:/LIWC/C/One3.txt", "c:/LIWC/C/One4.txt", "c:/LIWC/C/One5.txt"};
		//files = new String[]{"c:/test/indian summer.txt"};
		try {
		wc.wordCount(files,"c:/LIWC/TabbedDictionary.dic","","c:/LIWC/Delete","",true, true, false, false, false, true);
		//wc.wordCount(files,"c:/LIWC/TabbedDictionary.dic","","c:/LIWC/smallouton","",true, true, false, false, false);
		//wc.wordCount(files,"c:/LIWC/TabbedDictionary.dic","","c:/LIWC/numberOutWin"," .,;\"!-()[]{}:?",true, true, false, false, false);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static void mainTest(){
		WordCount wc = new WordCount();
		try {
			/*
			Test Data
			inputFile = "c:/test/indian summer.txt";
			dictionaryFile = "c:/test/dictionary.dic";
			stopWordsFile = "c:/test/stop words.txt";
			outputFile = "c:/test/output.csv";
			delimiters = " .,;'\"!-()[]{}:?";
			*/
		wc.oldWordCount("c:/test/indian summer.txt","c:/test/dictionary.dic","c:/test/stop words.txt","c:/test/output.csv"," .,;'\"!-()[]{}:?",true);	
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static void trieTest(){
		ArrayList i = new ArrayList();
		i.add(5);
		ArrayList j = new ArrayList();
		j.add(6);
		j.add(8);
		Trie test = new Trie();
		test.insert("a", i);
		test.insert("abs", i);
		test.insert("Healo", i);
		test.insert("Heal", i);
		test.insert("Helper", i);
		test.insert("Hellogoodbye", i);
		test.insert("Abominat", i);
		test.printTrie();
		test.insert("assist", i);
		test.insert("assignm*", j);
		test.printTrie();
		System.out.println(test.query("assist"));
		System.out.println(test.query("assign"));
		System.out.println(test.query("assignm"));
		System.out.println(test.query("assignment"));
		System.out.println(test.query("Healo"));
		System.out.println(test.query("Heal"));
		System.out.println(test.query("Helper"));
		System.out.println(test.query("Hellogoodbye"));
		
	}

}