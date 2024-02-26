package vott.models.dto.techrecordsv3;

public class TechRecordSearchResponse {

    private String techRecord_createdByName;
    private int techRecord_manufactureYear;
    private String techRecord_reasonForCreation;
    private String techRecord_make;
    private String primaryVrm;
    private String trailerId;
    private String vin;
    private String techRecord_statusCode;
    private String systemNumber;
    private String techRecord_vehicleType;
    private String createdTimestamp;
    private String techRecord_model;

    public String getTechRecord_createdByName() {
        return techRecord_createdByName;
    }

    public int getTechRecord_manufactureYear() {
        return techRecord_manufactureYear;
    }

    public String getTechRecord_reasonForCreation() {
        return techRecord_reasonForCreation;
    }

    public String getTechRecord_make() {
        return techRecord_make;
    }

    public String getPrimaryVrm() {
        return primaryVrm;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public String getVin() {
        return vin;
    }

    public String getTechRecord_statusCode() {
        return techRecord_statusCode;
    }

    public String getSystemNumber() {
        return systemNumber;
    }

    public String getTechRecord_vehicleType() {
        return techRecord_vehicleType;
    }

    public String getCreatedTimestamp() {
        return createdTimestamp;
    }

    public String getTechRecord_model() {
        return techRecord_model;
    }

}
