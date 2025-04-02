import lombok.Data;

@Data
public class Vehicle {
    private String name;
    private String vehicleTypeName;
    private String homeHubCode;
    private String registrationNumber;
    private String description;
    private double length;
    private double width;
    private double height;
    private double capacity;
    private double volume;
    private int maxPerson;
    private String note;
    private String externalId;
    private String vehicleCode;
    private String vehicleRoutingProfileName;
    private String sourceRef;
    private String metadata;
}
