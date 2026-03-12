package mx.com.qtx.api.controller;

import mx.com.qtx.infrastructure.monitoring.PerformanceMetricsStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/monitoreo/rendimiento")
public class MetricsController {

    @Autowired
    private PerformanceMetricsStore metricsStore;

    @GetMapping
    public String showMetrics(Model model) {
        model.addAttribute("metricas", metricsStore.getAllMetrics());
        return "monitoring/rendimiento";
    }

    @PostMapping("/limpiar")
    public String clearMetrics() {
        metricsStore.clearMetrics();
        return "redirect:/monitoreo/rendimiento";
    }
}
