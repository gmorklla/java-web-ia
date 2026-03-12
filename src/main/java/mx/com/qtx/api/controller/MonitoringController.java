package mx.com.qtx.api.controller;

import mx.com.qtx.infrastructure.monitoring.LogStorageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/monitoreo/logs")
public class MonitoringController {

    private final LogStorageService logStorageService;

    public MonitoringController(LogStorageService logStorageService) {
        this.logStorageService = logStorageService;
    }

    @GetMapping
    public String showLogs(Model model) {
        model.addAttribute("registros", logStorageService.getRecentLogs());
        return "monitoring/logs"; // Retorna la vista templates/monitoring/logs.html
    }
    
    @PostMapping("/limpiar")
    public String clearLogs() {
        logStorageService.clearLogs();
        return "redirect:/monitoreo/logs";
    }
}
