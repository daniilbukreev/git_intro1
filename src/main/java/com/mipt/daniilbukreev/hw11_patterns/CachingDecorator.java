package com.mipt.daniilbukreev.hw11_patterns;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class CachingDecorator implements DataService {
    private final DataService originalDataService;
    private final Map<String, String> cache = new ConcurrentHashMap<>();

    public CachingDecorator(DataService originalDataService) {
        this.originalDataService = originalDataService;
    }

    @Override
    public Optional<String> findDataByKey(String key) {
        String cachedValue = cache.get(key);
        if (cachedValue != null) {
            return Optional.ofNullable(cachedValue);
        }

        // Когда нет в кэше
        Optional<String> result = originalDataService.findDataByKey(key);
        cache.put(key, result.orElse(null));

        return result;
    }

    @Override
    public void saveData(String key, String data) {
        cache.put(key, data);
        originalDataService.saveData(key, data);
    }

    @Override
    public boolean deleteData(String key) {
        cache.remove(key);
        return originalDataService.deleteData(key);
    }
}
