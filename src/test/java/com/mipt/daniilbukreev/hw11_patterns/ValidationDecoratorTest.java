package com.mipt.daniilbukreev.hw11_patterns;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ValidationDecoratorTest {

    @Mock
    private DataService mockOriginalService;

    private ValidationDecorator validationDecorator;

    @BeforeEach
    void setUp() {
        validationDecorator = new ValidationDecorator(mockOriginalService);
    }

    @Test
    void findDataByKey_WithValidKey_CallsOriginal() {
        String key = "validKey";
        String data = "testData";
        when(mockOriginalService.findDataByKey(key)).thenReturn(Optional.of(data));
        Optional<String> result = validationDecorator.findDataByKey(key);

        assertTrue(result.isPresent());
        assertEquals(data, result.get());

        verify(mockOriginalService, times(1)).findDataByKey(key);
    }

    @Test
    void findDataByKey_WithNullKey_ThrowsException() {
        boolean exceptionThrown = false;
        try {
            validationDecorator.findDataByKey(null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown, "IllegalArgumentException");
        verify(mockOriginalService, never()).findDataByKey(anyString());
    }

    @Test
    void findDataByKey_WithEmptyKey_ThrowsException() {
        boolean exceptionThrown = false;
        try {
            validationDecorator.findDataByKey("");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown, "IllegalArgumentException");

        verify(mockOriginalService, never()).findDataByKey(anyString());
    }

    @Test
    void findDataByKey_WithWhitespaceKey_ThrowsException() {
        boolean exceptionThrown = false;
        try {
            validationDecorator.findDataByKey("   ");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown, "IllegalArgumentException");

        verify(mockOriginalService, never()).findDataByKey(anyString());
    }

    @Test
    void saveData_WithValidKey_CallsOriginal() {
        String key = "validKey";
        String data = "testData";

        validationDecorator.saveData(key, data);

        verify(mockOriginalService, times(1)).saveData(key, data);
    }

    @Test
    void saveData_WithNullKey_ThrowsException() {
        boolean exceptionThrown = false;
        try {
            validationDecorator.saveData(null, "data");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown, "IllegalArgumentException");

        verify(mockOriginalService, never()).saveData(anyString(), anyString());
    }

    @Test
    void saveData_WithEmptyKey_ThrowsException() {
        boolean exceptionThrown = false;
        try {
            validationDecorator.saveData("", "data");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown, "IllegalArgumentException");

        verify(mockOriginalService, never()).saveData(anyString(), anyString());
    }

    @Test
    void saveData_WithWhitespaceKey_ThrowsException() {
        boolean exceptionThrown = false;
        try {
            validationDecorator.saveData("   ", "data");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown, "IllegalArgumentException");

        verify(mockOriginalService, never()).saveData(anyString(), anyString());
    }

    @Test
    void deleteData_WithValidKey_CallsOriginal() {
        String key = "validKey";
        when(mockOriginalService.deleteData(key)).thenReturn(true);

        boolean result = validationDecorator.deleteData(key);

        assertTrue(result);

        verify(mockOriginalService, times(1)).deleteData(key);
    }

    @Test
    void deleteData_WithNullKey_ThrowsException() {
        boolean exceptionThrown = false;
        try {
            validationDecorator.deleteData(null);
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown, "IllegalArgumentException");

        verify(mockOriginalService, never()).deleteData(anyString());
    }

    @Test
    void deleteData_WithEmptyKey_ThrowsException() {
        boolean exceptionThrown = false;
        try {
            validationDecorator.deleteData("");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown, "IllegalArgumentException");

        verify(mockOriginalService, never()).deleteData(anyString());
    }

    @Test
    void deleteData_WithWhitespaceKey_ThrowsException() {
        boolean exceptionThrown = false;
        try {
            validationDecorator.deleteData("   ");
        } catch (IllegalArgumentException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown, "IllegalArgumentException");

        verify(mockOriginalService, never()).deleteData(anyString());
    }
}