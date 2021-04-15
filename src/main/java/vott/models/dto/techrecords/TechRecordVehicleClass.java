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

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Objects;

/**
 * Used for all vehicle types - PSV, HGV, TRL, car, lgv, motorcycle. Optional for CAR and LGV
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-04-13T13:30:43.231Z[GMT]")
public class TechRecordVehicleClass {
  /**
   * Gets or Sets code
   */
  @JsonAdapter(CodeEnum.Adapter.class)
  public enum CodeEnum {
    _2("2"),
    N("n"),
    S("s"),
    _1("1"),
    T("t"),
    L("l"),
    _3("3"),
    V("v"),
    _4("4"),
    _7("7"),
    _5("5");

    private String value;

    CodeEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static CodeEnum fromValue(String text) {
      for (CodeEnum b : CodeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<CodeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final CodeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public CodeEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return CodeEnum.fromValue(String.valueOf(value));
      }
    }
  }  @SerializedName("code")
  private CodeEnum code = null;

  /**
   * Gets or Sets description
   */
  @JsonAdapter(DescriptionEnum.Adapter.class)
  public enum DescriptionEnum {
    MOTORBIKES_OVER_200CC_OR_WITH_A_SIDECAR("motorbikes over 200cc or with a sidecar"),
    NOT_APPLICABLE("not applicable"),
    SMALL_PSV_IE_LESS_THAN_OR_EQUAL_TO_22_SEATS_("small psv (ie: less than or equal to 22 seats)"),
    MOTORBIKES_UP_TO_200CC("motorbikes up to 200cc"),
    TRAILER("trailer"),
    LARGE_PSV_IE_GREATER_THAN_23_SEATS_("large psv(ie: greater than 23 seats)"),
    _3_WHEELERS("3 wheelers"),
    HEAVY_GOODS_VEHICLE("heavy goods vehicle"),
    MOT_CLASS_4("MOT class 4"),
    MOT_CLASS_7("MOT class 7"),
    MOT_CLASS_5("MOT class 5"),
    PSV_OF_UNKNOWN_OR_UNSPECIFIED_SIZE("PSV of unknown or unspecified size"),
    NOT_KNOWN("Not Known");

    private String value;

    DescriptionEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static DescriptionEnum fromValue(String text) {
      for (DescriptionEnum b : DescriptionEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<DescriptionEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final DescriptionEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public DescriptionEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return DescriptionEnum.fromValue(String.valueOf(value));
      }
    }
  }  @SerializedName("description")
  private DescriptionEnum description = null;

  public TechRecordVehicleClass code(CodeEnum code) {
    this.code = code;
    return this;
  }

   /**
   * Get code
   * @return code
  **/
    public CodeEnum getCode() {
    return code;
  }

  public void setCode(CodeEnum code) {
    this.code = code;
  }

  public TechRecordVehicleClass description(DescriptionEnum description) {
    this.description = description;
    return this;
  }

   /**
   * Get description
   * @return description
  **/
    public DescriptionEnum getDescription() {
    return description;
  }

  public void setDescription(DescriptionEnum description) {
    this.description = description;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TechRecordVehicleClass techRecordVehicleClass = (TechRecordVehicleClass) o;
    return Objects.equals(this.code, techRecordVehicleClass.code) &&
        Objects.equals(this.description, techRecordVehicleClass.description);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, description);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TechRecordVehicleClass {\n");
    
    sb.append("    code: ").append(toIndentedString(code)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
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
