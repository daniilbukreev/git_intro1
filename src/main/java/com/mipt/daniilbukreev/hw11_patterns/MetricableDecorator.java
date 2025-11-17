package com.mipt.daniilbukreev.hw11_patterns;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

class MetricableDecorator implements DataService {
    private final DataService originalDataService;
    private final MetricService metricService = new MetricService();

    public MetricableDecorator(DataService originalDataService) {
        this.originalDataService = originalDataService;
    }

    @Override
    public Optional<String> findDataByKey(String key) {
        Instant start = Instant.now();
        Optional<String> result = originalDataService.findDataByKey(key);
        Instant end = Instant.now();
        metricService.sendMetric(Duration.between(start, end));
        return result;
    }

    @Override
    public void saveData(String key, String data) {
        Instant start = Instant.now();
        originalDataService.saveData(key, data);
        Instant end = Instant.now();
        metricService.sendMetric(Duration.between(start, end));
    }

    @Override
    public boolean deleteData(String key) {
        Instant start = Instant.now();
        boolean result = originalDataService.deleteData(key);
        Instant end = Instant.now();
        metricService.sendMetric(Duration.between(start, end));
        return result;
    }

    public static class MetricService {
        public void sendMetric(Duration duration) {
            System.out.println("Метод выполнялся: " + duration.toString());
        }
    }
}
