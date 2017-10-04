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

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordlist = new ArrayList<String>();
    private HashSet<String> wordSet = new HashSet<String>();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<String, ArrayList<String>>();



    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            wordlist.add(line.trim());
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
        }
    }

    public boolean isGoodWord(String word, String base) {
        return true;
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
        return result;
    }

    public String pickGoodStarterWord() {
        return "stop";
    }
}
