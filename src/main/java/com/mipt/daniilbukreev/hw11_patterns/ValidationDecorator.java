package com.mipt.daniilbukreev.hw11_patterns;

import java.util.Optional;

class ValidationDecorator implements DataService {
    private final DataService originalDataService;

    public ValidationDecorator(DataService originalDataService) {
        this.originalDataService = originalDataService;
    }

    @Override
    public Optional<String> findDataByKey(String key) {
        validateKey(key);
        return originalDataService.findDataByKey(key);
    }

    @Override
    public void saveData(String key, String data) {
        validateKey(key);
        originalDataService.saveData(key, data);
    }

    @Override
    public boolean deleteData(String key) {
        validateKey(key);
        return originalDataService.deleteData(key);
    }

    private void validateKey(String key) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException("Ключ невалидный");
        }
    }
}