/*
 * Vehicle Enquiry Service Swagger
 * This is the CVS API for showing details of heavy goods vehicles and their associated test result.
 *
 * OpenAPI spec version: 1.1-oas3
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package vott.models.dto.enquiry;

import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;
import java.util.Objects;

/**
 * AxleSpacing
 */

@Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-04-13T17:16:44.237Z[GMT]")
public class AxleSpacing {
  @SerializedName("axles")
  private String axles = null;

  @SerializedName("value")
  private Integer value = null;

  public AxleSpacing axles(String axles) {
    this.axles = axles;
    return this;
  }

   /**
   * Get axles
   * @return axles
  **/
  public String getAxles() {
    return axles;
  }

  public void setAxles(String axles) {
    this.axles = axles;
  }

  public AxleSpacing value(Integer value) {
    this.value = value;
    return this;
  }

   /**
   * Get value
   * @return value
  **/
  public Integer getValue() {
    return value;
  }

  public void setValue(Integer value) {
    this.value = value;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AxleSpacing axleSpacing = (AxleSpacing) o;
    return Objects.equals(this.axles, axleSpacing.axles) &&
        Objects.equals(this.value, axleSpacing.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(axles, value);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AxleSpacing {\n");
    
    sb.append("    axles: ").append(toIndentedString(axles)).append("\n");
    sb.append("    value: ").append(toIndentedString(value)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}