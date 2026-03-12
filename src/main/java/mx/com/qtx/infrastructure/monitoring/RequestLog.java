package mx.com.qtx.infrastructure.monitoring;

import java.time.LocalDateTime;

public class RequestLog {
    private String requestId;
    private String threadName;
    private String method;
    private String uri;
    private int status;
    private long durationMs;
    private LocalDateTime timestamp;

    public RequestLog(String requestId, String threadName, String method, String uri, int status, long durationMs) {
        this.requestId = requestId;
        this.threadName = threadName;
        this.method = method;
        this.uri = uri;
        this.status = status;
        this.durationMs = durationMs;
        this.timestamp = LocalDateTime.now();
    }

    public String getRequestId() {
        return requestId;
    }

    public String getThreadName() {
        return threadName;
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public int getStatus() {
        return status;
    }

    public long getDurationMs() {
        return durationMs;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
