package com.mipt.daniilbukreev.hw11_patterns;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// Базовая реализация - работа с памятью
class SimpleDataService implements DataService {
    private final Map<String, String> storage = new HashMap<>();

    @Override
    public Optional<String> findDataByKey(String key) {
        if (key == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(storage.get(key));
    }

    @Override
    public void saveData(String key, String data) {
        if (key != null) {
            storage.put(key, data);
        }
    }

    @Override
    public boolean deleteData(String key) {
        return storage.remove(key) != null;
    }
}