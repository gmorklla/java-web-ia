package mx.com.qtx.infrastructure.monitoring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceAspect {

    @Autowired
    private PerformanceMetricsStore metricsStore;

    // Escucha la ejecucion de CUALQUIER metodo dentro del paquete mx.com.qtx y subpaquetes
    // EXCLUYE: 
    // - Las clases de DTO para no agregar ruido 
    // - Las propias clases de monitoring para evitar bucles infinitos midiendo el medidor de rendimiento
    @Around("execution(* mx.com.qtx..*(..)) " +
            "&& !execution(* mx.com.qtx.api.dto..*(..)) " +
            "&& !execution(* mx.com.qtx.infrastructure.monitoring..*(..))")
    public Object profileMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        
        try {
            // Continua la ejecucion normal del metodo interceptado
            return joinPoint.proceed();
        } finally {
            long elapsedTime = System.currentTimeMillis() - start;
            
            // Extrae el nombre de la clase y el metodo
            String className = joinPoint.getSignature().getDeclaringTypeName();
            String methodName = joinPoint.getSignature().getName();
            
            // Almacena la metrica en memoria
            metricsStore.recordExecution(className, methodName, elapsedTime);
        }
    }
}
