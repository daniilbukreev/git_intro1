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
class CachingDecoratorTest {

    @Mock
    private DataService mockOriginalService;

    private CachingDecorator cachingDecorator;

    @BeforeEach
    void setUp() {
        cachingDecorator = new CachingDecorator(mockOriginalService);
    }

    @Test
    void findDataByKey_WhenCalledTwice_CallsOriginalServiceOnce() {
        String key = "testKey";
        String expectedData = "testData";

        when(mockOriginalService.findDataByKey(key)).thenReturn(Optional.of(expectedData));

        Optional<String> result1 = cachingDecorator.findDataByKey(key);
        Optional<String> result2 = cachingDecorator.findDataByKey(key);

        assertTrue(result1.isPresent());
        assertEquals(expectedData, result1.get());
        assertTrue(result2.isPresent());
        assertEquals(expectedData, result2.get());

        verify(mockOriginalService, times(1)).findDataByKey(key);
    }

    @Test
    void saveData_UpdatesCacheAndCallsOriginal() {
        String key = "testKey";
        String data = "testData";

        cachingDecorator.saveData(key, data);

        verify(mockOriginalService, times(1)).saveData(key, data);

        Optional<String> cachedResult = cachingDecorator.findDataByKey(key);
        assertTrue(cachedResult.isPresent());
        assertEquals(data, cachedResult.get());

        Optional<String> resultFromCache = cachingDecorator.findDataByKey(key);
        assertTrue(resultFromCache.isPresent());
        assertEquals(data, resultFromCache.get());
        verify(mockOriginalService, times(0)).findDataByKey(key);
    }

    @Test
    void findDataByKey_WithNullKey_ThrowsNPE() {
        String key = null;

        assertThrows(NullPointerException.class, () -> {
            cachingDecorator.findDataByKey(null);
        });

        verify(mockOriginalService, never()).findDataByKey(anyString());
    }

    @Test
    void saveData_WithNullKey_ThrowsNPE() {
        String key = null;
        String data = "someData";

        assertThrows(NullPointerException.class, () -> {
            cachingDecorator.saveData(key, data);
        });

        verify(mockOriginalService, never()).saveData(anyString(), anyString());
    }

    @Test
    void deleteData_WithNullKey_ThrowsNPE() {
        String key = null;

        assertThrows(NullPointerException.class, () -> {
            cachingDecorator.deleteData(null);
        });

        verify(mockOriginalService, never()).deleteData(anyString());
    }

    @Test
    void findDataByKey_WithExistingKeyInCache_ReturnsCachedValue() {
        String key = "cachedKey";
        String cachedData = "cachedData";

        cachingDecorator.saveData(key, cachedData);

        Optional<String> result = cachingDecorator.findDataByKey(key);

        assertTrue(result.isPresent());
        assertEquals(cachedData, result.get());

        verify(mockOriginalService, never()).findDataByKey(key);
    }
}