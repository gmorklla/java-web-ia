package mx.com.qtx.api.dto;

public class MethodMetricDTO {
    private String className;
    private String methodName;
    private long executionCount;
    private long totalTimeInMs;

    public MethodMetricDTO(String className, String methodName, long executionCount, long totalTimeInMs) {
        this.className = className;
        this.methodName = methodName;
        this.executionCount = executionCount;
        this.totalTimeInMs = totalTimeInMs;
    }

    public String getClassName() { return className; }
    public String getMethodName() { return methodName; }
    public long getExecutionCount() { return executionCount; }
    public long getTotalTimeInMs() { return totalTimeInMs; }
    
    public double getAverageTime() {
        if (executionCount == 0) return 0.0;
        return (double) totalTimeInMs / executionCount;
    }
}
