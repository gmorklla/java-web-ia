package mx.com.qtx.infrastructure.monitoring;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ConcurrencyTrackingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ConcurrencyTrackingFilter.class);
    private static final String REQUEST_ID = "requestId";
    private static final String THREAD_NAME = "threadName";

    private final LogStorageService logStorageService;

    public ConcurrencyTrackingFilter(LogStorageService logStorageService) {
        this.logStorageService = logStorageService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // 1. Generar variables de entorno de diagnostico MDC
        String requestId = UUID.randomUUID().toString().substring(0, 8); // UUID Corto para legibilidad
        String threadName = Thread.currentThread().getName();

        // 2. Inyectar en MDC
        MDC.put(REQUEST_ID, requestId);
        MDC.put(THREAD_NAME, threadName);

        long startTime = System.currentTimeMillis();

        try {
            log.info(">> Iniciando peticion [{} {}]", request.getMethod(), request.getRequestURI());
            
            // 3. Continuar la cadena de filtros hacia el controlador
            filterChain.doFilter(request, response);
            
            long duration = System.currentTimeMillis() - startTime;
            int status = response.getStatus();
            log.info("<< Finalizando peticion [{} {}] - Status: {} - Duracion: {} ms", 
                    request.getMethod(), request.getRequestURI(), status, duration);
            
            // 4. Guardar log en memoria para la vista web
            if (!request.getRequestURI().startsWith("/css/") && !request.getRequestURI().startsWith("/js/") && !request.getRequestURI().startsWith("/images/")) {
               logStorageService.addLog(new RequestLog(requestId, threadName, request.getMethod(), request.getRequestURI(), status, duration));
            }
                    
        } finally {
            // 4. Limpieza OBLIGATORIA para evitar fugas en el ThreadPool
            MDC.remove(REQUEST_ID);
            MDC.remove(THREAD_NAME);
        }
    }
}
