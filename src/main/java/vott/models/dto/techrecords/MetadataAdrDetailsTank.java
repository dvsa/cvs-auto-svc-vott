/*
 * Vehicles Microservice
 * This is the API spec for the vehicle microservice. Endpoints and parameters only exist for the operations getVehicle and getTechRecords. Other operations within the microservice are out of scope.
 *
 * OpenAPI spec version: 1.0.0
 * Contact: bpecete@deloittece.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package vott.models.dto.techrecords;

import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;
import java.util.Objects;

/**
 * MetadataAdrDetailsTank
 */

@Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-04-13T13:30:43.231Z[GMT]")
public class MetadataAdrDetailsTank {
  @SerializedName("tankStatement")
  private MetadataAdrDetailsTankTankStatement tankStatement = null;

  public MetadataAdrDetailsTank tankStatement(MetadataAdrDetailsTankTankStatement tankStatement) {
    this.tankStatement = tankStatement;
    return this;
  }

   /**
   * Get tankStatement
   * @return tankStatement
  **/
    public MetadataAdrDetailsTankTankStatement getTankStatement() {
    return tankStatement;
  }

  public void setTankStatement(MetadataAdrDetailsTankTankStatement tankStatement) {
    this.tankStatement = tankStatement;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MetadataAdrDetailsTank metadataAdrDetailsTank = (MetadataAdrDetailsTank) o;
    return Objects.equals(this.tankStatement, metadataAdrDetailsTank.tankStatement);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tankStatement);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MetadataAdrDetailsTank {\n");
    
    sb.append("    tankStatement: ").append(toIndentedString(tankStatement)).append("\n");
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
