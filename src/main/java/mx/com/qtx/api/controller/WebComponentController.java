package mx.com.qtx.api.controller;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterRegistration;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import mx.com.qtx.api.dto.ComponentInfoDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/monitoreo/componentes")
public class WebComponentController {

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping
    public String showComponents(Model model) {
        // Obtenemos los Servlets
        Map<String, ? extends ServletRegistration> servlets = servletContext.getServletRegistrations();
        List<ComponentInfoDTO> servletList = new ArrayList<>();
        for (ServletRegistration reg : servlets.values()) {
            servletList.add(new ComponentInfoDTO(reg.getName(), reg.getClassName(), String.join(", ", reg.getMappings()), "N/A"));
        }

        // Obtenemos los Filtros registrados en ServletContext
        Map<String, ? extends FilterRegistration> filters = servletContext.getFilterRegistrations();
        List<ComponentInfoDTO> filterList = new ArrayList<>();
        
        // Extraemos los ordenamientos declarados por Spring para mayor precision
        Map<String, Integer> filterOrders = getSpringFilterOrders();

        for (FilterRegistration reg : filters.values()) {
            String filterName = reg.getName();
            String className = reg.getClassName();
            
            String orderStr = "Interno de Tomcat"; // Por defecto si no detectamos un orden provisto por Spring
            Integer order = filterOrders.get(className);
            
            if (order != null) {
                orderStr = String.valueOf(order);
            } else {
                // Buscamos si la clase tiene anotacion @Order pero sin Beans
                Integer classOrder = getOrderFromClass(className);
                if (classOrder != null) {
                    orderStr = String.valueOf(classOrder);
                }
            }

            filterList.add(new ComponentInfoDTO(filterName, className, String.join(", ", reg.getUrlPatternMappings()), orderStr));
        }
        
        // Ordenamos la lista de filtros por la columna "Orden"
        filterList.sort((c1, c2) -> {
            Integer o1 = parseOrderInfo(c1.getOrder());
            Integer o2 = parseOrderInfo(c2.getOrder());
            return o1.compareTo(o2);
        });

        model.addAttribute("servlets", servletList);
        model.addAttribute("filtros", filterList);

        return "monitoring/componentes";
    }

    @SuppressWarnings("rawtypes")
    private Map<String, Integer> getSpringFilterOrders() {
        Map<String, Integer> orders = new HashMap<>();
        
        // Revisamos beans creados via FilterRegistrationBean (tipicamente configuraciones manuales de Spring Boot)
        Map<String, FilterRegistrationBean> beans = applicationContext.getBeansOfType(FilterRegistrationBean.class);
        for (FilterRegistrationBean bean : beans.values()) {
            if(bean.getFilter() != null) {
                orders.put(bean.getFilter().getClass().getName(), bean.getOrder());
            }
        }
        
        // Revisamos beans que son directamente sub-clases de Filter y pueden tener la anotacion @Order
        Map<String, Filter> filterBeans = applicationContext.getBeansOfType(Filter.class);
        for (Filter filter : filterBeans.values()) {
            Integer order = getOrderFromClass(filter.getClass().getName());
            if (order != null) {
                orders.putIfAbsent(filter.getClass().getName(), order);
            }
        }
        return orders;
    }

    private Integer getOrderFromClass(String className) {
        try {
            if (className == null) return null;
            Class<?> clazz = Class.forName(className);
            Order orderAnnotation = AnnotationUtils.findAnnotation(clazz, Order.class);
            if (orderAnnotation != null) {
                return orderAnnotation.value();
            }
        } catch (Throwable e) {
            // Ignoramos si la clase no puede cargarse estaticamente u otros fallos
        }
        return null;
    }
    
    private Integer parseOrderInfo(String orderStr) {
        try {
            return Integer.parseInt(orderStr);
        } catch (NumberFormatException e) {
            // Mandamos los filtros sin ordenamiento numerico al final de la lista
            return Integer.MAX_VALUE; 
        }
    }


}
