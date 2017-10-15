/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 1;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private int wordLength = DEFAULT_WORD_LENGTH;
    private ArrayList<String> wordlist = new ArrayList<>();
    private HashSet<String> wordSet = new HashSet<String>();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<String, ArrayList<String>>();
    private HashMap<Integer, ArrayList<String>> sizeToWord = new HashMap<Integer, ArrayList<String>>();


    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            wordlist.add(line.trim());
            wordSet.add(line.trim());

            if(lettersToWord.containsKey(sortLetters(line.trim())))
            {
                lettersToWord.get(sortLetters(line.trim())).add(line.trim());
            }
            else
            {
                ArrayList<String> listOfWords =  new ArrayList<String>();
                listOfWords.add(line.trim());
                lettersToWord.put(sortLetters(line.trim()),listOfWords);
            }

            if(sizeToWord.containsKey(line.trim().length()))
            {
                sizeToWord.get(line.trim().length()).add(line.trim());
            }
            else
            {
                ArrayList<String> sameSizedWords =  new ArrayList<String>();
                sameSizedWords.add(line.trim());
                sizeToWord.put(line.trim().length(),sameSizedWords);
            }


        }
    }

    public boolean isGoodWord(String word, String base) {

            if(word.equals(base))
                return false;

        if(wordSet.contains(word) && !word.contains(base))
            return true;
        else
            return false;
    }

    public String sortLetters(String unSortedWord)
    {
        char [] tempArray = unSortedWord.toCharArray();
        Arrays.sort(tempArray);
        return new String (tempArray);
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();

        //Loop over all the words in our dictionary and return any anagrams
        for(String dictWord: wordlist)
        {
            if(targetWord.length() == dictWord.length()) {
                if (sortLetters(targetWord).equals(sortLetters(dictWord))) {
                    result.add(dictWord);
                }
            }
        }

        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        String [] myAlphabet = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        for (String aWord: lettersToWord.get(sortLetters(word)))
        {
            for(int i=0;i<myAlphabet.length;i++)
            {
                if(wordSet.contains(myAlphabet[i] + aWord) && !result.contains(myAlphabet[i] + aWord) && !(myAlphabet[i] + aWord).contains(word))
                result.add(myAlphabet[i] + aWord); // append current letter to the beginning of the word

                if(wordSet.contains(aWord + myAlphabet[i]) &&  !result.contains(aWord + myAlphabet[i]) && !(aWord + myAlphabet[i]).contains(word))
                result.add(aWord + myAlphabet[i]); // append current letter to the beginning of the word
            }
        }

        Log.i("ANSWERS", result.toString());

        if(result.size()==0)
            result.add("none");
        return result;
    }

    public String pickGoodStarterWord() {

        Random random = new Random();

        ArrayList<String> sameLengthWords = sizeToWord.get(wordLength);

        int index = random.nextInt(sameLengthWords.size());

        while(true)
        {
            if(lettersToWord.get(sortLetters(sameLengthWords.get(index))).size()>=MIN_NUM_ANAGRAMS) {
                Log.i("KK", "Word is " + sameLengthWords.get(index) +"-------- \n" + lettersToWord.get(sortLetters(sameLengthWords.get(index))).toString());
                wordLength++;
                return sameLengthWords.get(index);
            }

            index = random.nextInt(sameLengthWords.size());
        }


    }
}
