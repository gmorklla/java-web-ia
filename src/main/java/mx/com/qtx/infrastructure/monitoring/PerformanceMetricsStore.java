package mx.com.qtx.infrastructure.monitoring;

import mx.com.qtx.api.dto.MethodMetricDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PerformanceMetricsStore {

    // Clase interna para llevar la cuenta atómica del tiempo y las invocaciones de un método
    private static class MetricData {
        private final AtomicLong executionCount = new AtomicLong(0);
        private final AtomicLong totalTimeInMs = new AtomicLong(0);

        public void addExecution(long timeInMs) {
            executionCount.incrementAndGet();
            totalTimeInMs.addAndGet(timeInMs);
        }

        public long getCount() { return executionCount.get(); }
        public long getTotalTime() { return totalTimeInMs.get(); }
    }

    // Mapa thread-safe: la llave es "NombreClase.nombreMetodo"
    private final ConcurrentHashMap<String, MetricData> metricsMap = new ConcurrentHashMap<>();

    public void recordExecution(String className, String methodName, long timeInMs) {
        String key = className + "." + methodName;
        
        // ComputeIfAbsent inserta de forma segura el objeto si no existe
        MetricData data = metricsMap.computeIfAbsent(key, k -> new MetricData());
        data.addExecution(timeInMs);
    }

    public List<MethodMetricDTO> getAllMetrics() {
        List<MethodMetricDTO> result = new ArrayList<>();
        metricsMap.forEach((key, data) -> {
            int dotIndex = key.lastIndexOf('.');
            String className = key.substring(0, dotIndex);
            String methodName = key.substring(dotIndex + 1);
            
            result.add(new MethodMetricDTO(className, methodName, data.getCount(), data.getTotalTime()));
        });
        
        // Ordenamos por mayor tiempo total
        result.sort((m1, m2) -> Long.compare(m2.getTotalTimeInMs(), m1.getTotalTimeInMs()));
        
        return result;
    }
    
    public void clearMetrics() {
        metricsMap.clear();
    }
}
