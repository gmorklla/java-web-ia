package mx.com.qtx.api.dto;

public class BeanInfoDTO {
    private String name;
    private String className;
    private String type; // "Propio" o "Infraestructura"
    private String scope;

    public BeanInfoDTO(String name, String className, String type, String scope) {
        this.name = name;
        this.className = className;
        this.type = type;
        this.scope = scope;
    }

    public String getName() { return name; }
    public String getClassName() { return className; }
    public String getType() { return type; }
    public String getScope() { return scope; }
}
