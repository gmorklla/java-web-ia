package mx.com.qtx.api.controller;

import mx.com.qtx.api.dto.BeanInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/monitoreo/beans")
public class SpringBeanController {

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping
    public String showBeans(Model model) {
        List<BeanInfoDTO> propios = new ArrayList<>();
        List<BeanInfoDTO> infraestructura = new ArrayList<>();

        String[] beanNames = applicationContext.getBeanDefinitionNames();
        
        ConfigurableListableBeanFactory factory = null;
        if (applicationContext instanceof ConfigurableApplicationContext) {
            factory = ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
        }

        for (String beanName : beanNames) {
            Object bean = null;
            try {
                bean = applicationContext.getBean(beanName);
            } catch (Exception e) {
                // Hay beans que fallan al obtenerse en runtime o si estamo solicitando proxies raros. Ignorar.
                continue;
            }

            if (bean == null) continue;

            String className = bean.getClass().getName();
            
            // Si es un proxy de CGLIB o JDK Dynamic Proxy, intentamos obtener su clase original 
            // (Aun asi los proxies de spring suelen conservar el paquete original en el nombre).
            if(className.contains("$$EnhancerBySpringCGLIB$$")) {
               className = className.substring(0, className.indexOf("$$"));
            }

            // Identificar si es propio o de infraestructura
            String tipo = className.startsWith("mx.com.qtx") ? "Propio" : "Infraestructura";
            
            // Obtener el scope desde la definicion del Bean si es posible
            String scope = "singleton"; // Por defecto Spring usa singleton
            if (factory != null && factory.containsBeanDefinition(beanName)) {
                try {
                    BeanDefinition beanDef = factory.getBeanDefinition(beanName);
                    if (beanDef.getScope() != null && !beanDef.getScope().isEmpty()) {
                        scope = beanDef.getScope();
                    }
                } catch (Exception e) {
                    // Si no se puede obtener la resolucion, asumimos singleton
                }
            }

            BeanInfoDTO info = new BeanInfoDTO(beanName, className, tipo, scope);

            if ("Propio".equals(tipo)) {
                propios.add(info);
            } else {
                infraestructura.add(info);
            }
        }
        
        // Ordenamos alfabeticamente por nombre para que sea mas facil de leer
        propios.sort((b1, b2) -> b1.getName().compareToIgnoreCase(b2.getName()));
        infraestructura.sort((b1, b2) -> b1.getName().compareToIgnoreCase(b2.getName()));

        model.addAttribute("beansPropios", propios);
        model.addAttribute("beansInfraestructura", infraestructura);
        model.addAttribute("totalBeans", beanNames.length);

        return "monitoring/beans";
    }
}
