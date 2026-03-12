package mx.com.qtx.infrastructure.monitoring;

import org.springframework.stereotype.Service;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class LogStorageService {

    private static final int MAX_LOGS = 100; // Mantener solo los últimos 100
    private final Queue<RequestLog> logs = new ConcurrentLinkedQueue<>();

    public void addLog(RequestLog log) {
        logs.offer(log);
        if (logs.size() > MAX_LOGS) {
            logs.poll(); // Remover el más antiguo
        }
    }

    public List<RequestLog> getRecentLogs() {
        return new ArrayList<>(logs).stream()
                .sorted(Comparator.comparing(RequestLog::getTimestamp).reversed())
                .collect(Collectors.toList());
    }
    
    public void clearLogs() {
        logs.clear();
    }
}
