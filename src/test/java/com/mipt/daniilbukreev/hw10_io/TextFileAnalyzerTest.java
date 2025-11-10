package com.mipt.daniilbukreev.hw10_io;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

class TextFileAnalyzerTest {

    @Test
    void testAnalyzeFile() throws IOException {
        TextFileAnalyzer analyzer = new TextFileAnalyzer();

        Path testFile = Files.createTempFile("test", ".txt");
        Files.write(testFile, Arrays.asList("Hello world", "Test"));

        TextFileAnalyzer.AnalysisResult result = analyzer.analyzeFile(testFile.toString());

        assertEquals(2, result.getLineCount());
        assertEquals(3, result.getWordCount());
        assertEquals(16, result.getCharCount());

        assertFalse(result.getCharFrequency().isEmpty());

        boolean hasLetterL = false;
        for (TextFileAnalyzer.CharFrequency cf : result.getCharFrequency()) {
            if (cf.getCharacter() == 'l' && cf.getFrequency() >= 3) {
                hasLetterL = true;
                break;
            }
        }
        assertTrue(hasLetterL, "'l' w freq >= 3");

        boolean hasSpace = false;
        for (TextFileAnalyzer.CharFrequency cf : result.getCharFrequency()) {
            if (cf.getCharacter() == ' ') {
                hasSpace = true;
                break;
            }
        }
        assertTrue(hasSpace, "space contain");

        Files.delete(testFile);
    }

    @Test
    void testSaveAnalysisResult() throws IOException {
        TextFileAnalyzer analyzer = new TextFileAnalyzer();

        List<TextFileAnalyzer.CharFrequency> charFrequency = new ArrayList<>();
        charFrequency.add(new TextFileAnalyzer.CharFrequency('a', 5));
        charFrequency.add(new TextFileAnalyzer.CharFrequency('b', 3));
        charFrequency.add(new TextFileAnalyzer.CharFrequency(' ', 2));

        TextFileAnalyzer.AnalysisResult result = new TextFileAnalyzer.AnalysisResult(2, 3, 15, charFrequency);

        Path outputFile = Files.createTempFile("analysis", ".txt");
        analyzer.saveAnalysisResult(result, outputFile.toString());

        List<String> lines = Files.readAllLines(outputFile);

        boolean hasLines = false;
        boolean hasWords = false;
        boolean hasChars = false;
        for (String line : lines) {
            if (line.contains("Lines: 2")) hasLines = true;
            if (line.contains("Words: 3")) hasWords = true;
            if (line.contains("Chars:15")) hasChars = true;
        }
        assertTrue(hasLines, "hasLines");
        assertTrue(hasWords, "hasWords");
        assertTrue(hasChars, "hasChars");

        boolean hasFrequencyA = false;
        boolean hasFrequencyB = false;
        boolean hasFrequencySpace = false;
        for (String line : lines) {
            if (line.contains("'a': 5 times")) hasFrequencyA = true;
            if (line.contains("'b': 3 times")) hasFrequencyB = true;
            if (line.contains("' ': 2 times")) hasFrequencySpace = true;
        }
        assertTrue(hasFrequencyA, "freq 'a'");
        assertTrue(hasFrequencyB, "freq 'b'");
        assertTrue(hasFrequencySpace, "freq space");
    }
}