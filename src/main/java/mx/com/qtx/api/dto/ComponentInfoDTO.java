package mx.com.qtx.api.dto;

public class ComponentInfoDTO {
    private String name;
    private String className;
    private String mappings;
    private String order;

    public ComponentInfoDTO(String name, String className, String mappings, String order) {
        this.name = name;
        this.className = className;
        this.mappings = mappings;
        this.order = order;
    }

    public String getName() { return name; }
    public String getClassName() { return className; }
    public String getMappings() { return mappings; }
    public String getOrder() { return order; }
}
