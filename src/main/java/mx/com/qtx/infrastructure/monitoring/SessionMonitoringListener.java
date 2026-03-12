package mx.com.qtx.infrastructure.monitoring;

import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

@Configuration
public class SessionMonitoringListener implements HttpSessionListener {

    private static final Logger log = LoggerFactory.getLogger(SessionMonitoringListener.class);
    
    // Contador Atomico (Thread-safe) para concurrencia extrema
    private final AtomicInteger activeSessions = new AtomicInteger(0);

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        int current = activeSessions.incrementAndGet();
        log.info("+++ NUEVA SESION CREADA: [{}] - Total usuarios activos: {}", se.getSession().getId(), current);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        int current = activeSessions.decrementAndGet();
        log.info("--- SESION DESTRUIDA: [{}] - Total usuarios activos restantes: {}", se.getSession().getId(), current);
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener() {
        ServletListenerRegistrationBean<HttpSessionListener> listenerRegBean = new ServletListenerRegistrationBean<>();
        listenerRegBean.setListener(this);
        return listenerRegBean;
    }
}
