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
class MetricableDecoratorTest {

    @Mock
    private DataService mockOriginalService;

    private MetricableDecorator metricableDecorator;
    private ByteArrayOutputStream outContent;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        metricableDecorator = new MetricableDecorator(mockOriginalService);
        outContent = new ByteArrayOutputStream();
        originalOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void findDataByKey_CallsOriginalServiceAndMetricService() {
        String key = "testKey";
        String data = "testData";
        when(mockOriginalService.findDataByKey(key)).thenReturn(Optional.of(data));

        Optional<String> result = metricableDecorator.findDataByKey(key);

        assertTrue(result.isPresent());
        assertEquals(data, result.get());

        verify(mockOriginalService, times(1)).findDataByKey(key);

        String logOutput = outContent.toString();
        assertTrue(logOutput.contains("Метод выполнялся: "));
    }

    @Test
    void findDataByKey_WithNonExistentKey_CallsOriginalServiceAndMetricService() {
        String key = "nonExistentKey";
        when(mockOriginalService.findDataByKey(key)).thenReturn(Optional.empty());

        Optional<String> result = metricableDecorator.findDataByKey(key);
        assertTrue(result.isEmpty());

        verify(mockOriginalService, times(1)).findDataByKey(key);
        String logOutput = outContent.toString();
        assertTrue(logOutput.contains("Метод выполнялся: "));
    }

    @Test
    void saveData_CallsOriginalServiceAndMetricService() {
        String key = "testKey";
        String data = "testData";

        metricableDecorator.saveData(key, data);

        verify(mockOriginalService, times(1)).saveData(key, data);
        String logOutput = outContent.toString();
        assertTrue(logOutput.contains("Метод выполнялся: "));
    }

    @Test
    void deleteData_WithExistingKey_CallsOriginalServiceAndMetricService() {
        String key = "testKey";
        when(mockOriginalService.deleteData(key)).thenReturn(true);
        boolean result = metricableDecorator.deleteData(key);
        assertTrue(result);
        verify(mockOriginalService, times(1)).deleteData(key);
        String logOutput = outContent.toString();
        assertTrue(logOutput.contains("Метод выполнялся: "));
    }

    @Test
    void deleteData_WithNonExistentKey_CallsOriginalServiceAndMetricService() {
        String key = "nonExistentKey";
        when(mockOriginalService.deleteData(key)).thenReturn(false);
        boolean result = metricableDecorator.deleteData(key);

        assertFalse(result);

        verify(mockOriginalService, times(1)).deleteData(key);
        String logOutput = outContent.toString();
        assertTrue(logOutput.contains("Метод выполнялся: "));
    }
}