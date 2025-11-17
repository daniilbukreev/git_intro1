package com.mipt.daniilbukreev.hw11_patterns;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoggingDecoratorTest {

    @Mock
    private DataService mockOriginalService;

    private LoggingDecorator loggingDecorator;
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        loggingDecorator = new LoggingDecorator(mockOriginalService);
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void findDataByKey_WithExistingKey_LogsCorrectly() {
        String key = "testKey";
        String data = "testData";
        when(mockOriginalService.findDataByKey(key)).thenReturn(Optional.of(data));

        Optional<String> result = loggingDecorator.findDataByKey(key);

        assertTrue(result.isPresent());
        assertEquals(data, result.get());

        String logOutput = outContent.toString();
        assertTrue(logOutput.contains("Вызвали findDataByKey с ключом: '" + key + "'"));
        assertTrue(logOutput.contains("findDataByKey вернул: найдено"));

        verify(mockOriginalService, times(1)).findDataByKey(key);
    }

    @Test
    void findDataByKey_WithNonExistentKey_LogsCorrectly() {
        String key = "nonExistentKey";
        when(mockOriginalService.findDataByKey(key)).thenReturn(Optional.empty());

        Optional<String> result = loggingDecorator.findDataByKey(key);

        assertTrue(result.isEmpty());

        String logOutput = outContent.toString();
        assertTrue(logOutput.contains("Вызвали findDataByKey с ключом: '" + key + "'"));
        assertTrue(logOutput.contains("findDataByKey вернул: не найдено"));

        verify(mockOriginalService, times(1)).findDataByKey(key);
    }

    @Test
    void saveData_LogsCorrectly() {
        String key = "testKey";
        String data = "testData";

        loggingDecorator.saveData(key, data);

        String logOutput = outContent.toString();
        assertTrue(logOutput.contains("Вызвали saveData с ключом: '" + key + "' и данными: '" + data + "'"));
        assertTrue(logOutput.contains("saveData выполнился"));

        verify(mockOriginalService, times(1)).saveData(key, data);
    }

    @Test
    void deleteData_WithExistingKey_LogsCorrectly() {
        String key = "testKey";
        when(mockOriginalService.deleteData(key)).thenReturn(true);

        boolean result = loggingDecorator.deleteData(key);
        assertTrue(result);

        String logOutput = outContent.toString();
        assertTrue(logOutput.contains("Вызвали deleteData с ключом: '" + key + "'"));
        assertTrue(logOutput.contains("deleteData выполнился. Удаление успешно"));

        verify(mockOriginalService, times(1)).deleteData(key);
    }

    @Test
    void deleteData_WithNonExistentKey_LogsCorrectly() {
        String key = "nonExistentKey";
        when(mockOriginalService.deleteData(key)).thenReturn(false);
        boolean result = loggingDecorator.deleteData(key);
        assertFalse(result);

        String logOutput = outContent.toString();
        assertTrue(logOutput.contains("Вызвали deleteData с ключом: '" + key + "'"));
        assertTrue(logOutput.contains("deleteData выполнился. Удаление не удалось"));

        verify(mockOriginalService, times(1)).deleteData(key);
    }
}