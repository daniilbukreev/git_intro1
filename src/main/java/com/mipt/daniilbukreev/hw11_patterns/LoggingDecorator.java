package com.mipt.daniilbukreev.hw11_patterns;

import java.util.Optional;

class LoggingDecorator implements DataService {
    private final DataService originalDataService;

    public LoggingDecorator(DataService originalDataService) {
        this.originalDataService = originalDataService;
    }

    @Override
    public Optional<String> findDataByKey(String key) {
        System.out.println("Вызвали findDataByKey с ключом: '" + key + "'");
        Optional<String> result = originalDataService.findDataByKey(key);
        String status;
        if (result.isPresent()) {
            status = "найдено";
        } else {
            status = "не найдено";
        }
        System.out.println("findDataByKey вернул: " + status);
        return result;
    }

    @Override
    public void saveData(String key, String data) {
        System.out.println("Вызвали saveData с ключом: '" + key + "' и данными: '" + data + "'");
        originalDataService.saveData(key, data);
        System.out.println("saveData выполнился");
    }

    @Override
    public boolean deleteData(String key) {
        System.out.println("Вызвали deleteData с ключом: '" + key + "'");
        boolean result = originalDataService.deleteData(key);
        String status;
        if (result) {
            status = "успешно";
        } else {
            status = "не удалось";
        }
        System.out.println("deleteData выполнился. Удаление " + status);
        return result;
    }
}
