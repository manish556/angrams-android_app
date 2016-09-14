package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.StringTokenizer;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private int wordLength;

    private ArrayList<String> wordList;
    private HashSet<String> wordSet;
    private HashMap<String,ArrayList<String>> lettersToWord;
    private HashMap<int,ArrayList<String>> sizeToWords;

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        wordList = new ArrayList<>();
        wordSet = new HashSet<>();
        lettersToWord = new HashMap<>();
        wordLength = DEFAULT_WORD_LENGTH;

        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String sortedWord = sortLetters(word);
            if(!lettersToWord.containsKey(sortedWord))
                lettersToWord.put(sortedWord,new ArrayList<String>());
            lettersToWord.get(sortedWord).add(word);

            if(!sizeToWords.containsKey(word.length()))
                sizeToWords.put(word.length(),new ArrayList<String>());
            sizeToWords.get(word.length()).add(word);


        }


    }

    public String sortLetters(String word){
        char[] charArray = word.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }

    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word) && !word.contains(base))
        return true;
        else
            return false;
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String sortedTarget = sortLetters(targetWord);

        int n = wordList.size();
        for(int i=0;i<n;i++){
            String word = wordList.get(i);
            if( sortLetters(word).equals(sortedTarget) )
                result.add(wordList.get(i));
        }

        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        char ch='a';
        for (int i=0;i<26;i++)
        {
            String newWord = word+ch;
            ch++;
            newWord=sortLetters(newWord);
            if(lettersToWord.containsKey(newWord))
                result.addAll(lettersToWord.get(newWord));
        }

        return result;
    }

    public String pickGoodStarterWord() {
      int index = random.nextInt(wordList.size());
              while(getAnagramsWithOneMoreLetter(wordList.get(index)).size()<MIN_NUM_ANAGRAMS)
              {
                  index=(index+1)%wordList.size();
              }
        return wordList.get(index);
    }
}
