package vott.models.dto.techrecordsv3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;

/**
 * AxleSpacing
 * <p>
 */
@Generated("jsonschema2pojo")
public class TechRecordDimensionsAxleSpacing {

    @SerializedName("axles")
    @Expose
    private String axles;
    @SerializedName("value")
    @Expose
    private Integer value;

    public String getAxles() {
        return axles;
    }

    public void setAxles(String axles) {
        this.axles = axles;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}
