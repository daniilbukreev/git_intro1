package com.mipt.daniilbukreev.hw10_io;

import java.io.*;
import java.util.*;

public class TextFileAnalyzer {

    public static class AnalysisResult {
        private final long lineCount;
        private final long wordCount;
        private final long charCount;
        private final List<CharFrequency> charFrequency;

        public AnalysisResult(long lineCount, long wordCount, long charCount, List<CharFrequency> charFrequency) {
            this.lineCount = lineCount;
            this.wordCount = wordCount;
            this.charCount = charCount;
            this.charFrequency = charFrequency;
        }

        //////////////
        public long getLineCount() {
            return lineCount;
        }
        public long getWordCount() {
            return wordCount;
        }
        public long getCharCount() {
            return charCount;
        }
        public List<CharFrequency> getCharFrequency() {
            return new ArrayList<>(charFrequency);
        }
        ///////////////


        @Override
        public String toString() {
            return "Lines: " + lineCount + ", Words: " + wordCount + ", Chars: " + charCount + ", Frequency: " + charFrequency.size();
        }
    }


    public static class CharFrequency {
        private final char character;
        private final long frequency;

        public CharFrequency(char character, long frequency) {
            this.character = character;
            this.frequency = frequency;
        }

        public char getCharacter() {
            return character;
        }
        public long getFrequency() {
            return frequency;
        }

        @Override
        public String toString() {
            return "'" + character + "': " + frequency;
        }
    }

    public AnalysisResult analyzeFile(String filePath) throws IOException {
        long lineCount = 0;
        long wordCount = 0;
        long charCount = 0;

        long[] frequencyArray = new long[256];

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                lineCount++;
                charCount += line.length();

                if (!line.trim().isEmpty()) {
                    String[] words = line.trim().split("\\s+");
                    wordCount += words.length;
                }
                for (char c : line.toCharArray()) {
                    if (c < frequencyArray.length) {
                        frequencyArray[c]++;
                    }
                }
            }
        }

        List<CharFrequency> charFrequencyList = convertArrayToList(frequencyArray);
        return new AnalysisResult(lineCount, wordCount, charCount, charFrequencyList);
    }

    private List<CharFrequency> convertArrayToList(long[] frequencyArray) {
        List<CharFrequency> result = new ArrayList<>();

        for (int i = 0; i < frequencyArray.length; i++) {
            if (frequencyArray[i] > 0) {
                result.add(new CharFrequency((char) i, frequencyArray[i]));
            }
        }

        result.sort(Comparator.comparingLong(CharFrequency::getFrequency).reversed());
        return result;
    }

    public void saveAnalysisResult(AnalysisResult result, String outputPath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath))) {
            writer.write("Lines: " + result.getLineCount());
            writer.write("Words: " + result.getWordCount());
            writer.write("Chars:" + result.getCharCount());
            writer.write("Frequency: ");
            for (CharFrequency cf : result.getCharFrequency()) {
                String charRepresentation = String.valueOf(cf.getCharacter());
                writer.write("'" + charRepresentation + "': " + cf.getFrequency() + " times");
                writer.newLine();
            }
        }
    }
}