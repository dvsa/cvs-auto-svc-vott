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
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import java.util.Objects;

/**
 * MetadataAdrDetailsAdditionalNotes
 */

@Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-04-13T13:30:43.231Z[GMT]")
public class MetadataAdrDetailsAdditionalNotes {
  /**
   * Gets or Sets guidanceNotesFe
   */
  @JsonAdapter(GuidanceNotesFeEnum.Adapter.class)
  public enum GuidanceNotesFeEnum {
    REQUESTED("New certificate requested");

    private String value;

    GuidanceNotesFeEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static GuidanceNotesFeEnum fromValue(String text) {
      for (GuidanceNotesFeEnum b : GuidanceNotesFeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<GuidanceNotesFeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final GuidanceNotesFeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public GuidanceNotesFeEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return GuidanceNotesFeEnum.fromValue(String.valueOf(value));
      }
    }
  }  @SerializedName("guidanceNotesFe")
  private List<GuidanceNotesFeEnum> guidanceNotesFe = null;

  /**
   * Gets or Sets numberFe
   */
  @JsonAdapter(NumberFeEnum.Adapter.class)
  public enum NumberFeEnum {
    _1("1"),
    _1A("1A"),
    _2("2"),
    _3("3"),
    V1B("V1B"),
    T1B("T1B");

    private String value;

    NumberFeEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static NumberFeEnum fromValue(String text) {
      for (NumberFeEnum b : NumberFeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<NumberFeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final NumberFeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public NumberFeEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return NumberFeEnum.fromValue(String.valueOf(value));
      }
    }
  }  @SerializedName("numberFe")
  private List<NumberFeEnum> numberFe = null;

  public MetadataAdrDetailsAdditionalNotes guidanceNotesFe(List<GuidanceNotesFeEnum> guidanceNotesFe) {
    this.guidanceNotesFe = guidanceNotesFe;
    return this;
  }

  public MetadataAdrDetailsAdditionalNotes addGuidanceNotesFeItem(GuidanceNotesFeEnum guidanceNotesFeItem) {
    if (this.guidanceNotesFe == null) {
      this.guidanceNotesFe = new ArrayList<GuidanceNotesFeEnum>();
    }
    this.guidanceNotesFe.add(guidanceNotesFeItem);
    return this;
  }

   /**
   * Get guidanceNotesFe
   * @return guidanceNotesFe
  **/
    public List<GuidanceNotesFeEnum> getGuidanceNotesFe() {
    return guidanceNotesFe;
  }

  public void setGuidanceNotesFe(List<GuidanceNotesFeEnum> guidanceNotesFe) {
    this.guidanceNotesFe = guidanceNotesFe;
  }

  public MetadataAdrDetailsAdditionalNotes numberFe(List<NumberFeEnum> numberFe) {
    this.numberFe = numberFe;
    return this;
  }

  public MetadataAdrDetailsAdditionalNotes addNumberFeItem(NumberFeEnum numberFeItem) {
    if (this.numberFe == null) {
      this.numberFe = new ArrayList<NumberFeEnum>();
    }
    this.numberFe.add(numberFeItem);
    return this;
  }

   /**
   * Get numberFe
   * @return numberFe
  **/
    public List<NumberFeEnum> getNumberFe() {
    return numberFe;
  }

  public void setNumberFe(List<NumberFeEnum> numberFe) {
    this.numberFe = numberFe;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MetadataAdrDetailsAdditionalNotes metadataAdrDetailsAdditionalNotes = (MetadataAdrDetailsAdditionalNotes) o;
    return Objects.equals(this.guidanceNotesFe, metadataAdrDetailsAdditionalNotes.guidanceNotesFe) &&
        Objects.equals(this.numberFe, metadataAdrDetailsAdditionalNotes.numberFe);
  }

  @Override
  public int hashCode() {
    return Objects.hash(guidanceNotesFe, numberFe);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MetadataAdrDetailsAdditionalNotes {\n");
    
    sb.append("    guidanceNotesFe: ").append(toIndentedString(guidanceNotesFe)).append("\n");
    sb.append("    numberFe: ").append(toIndentedString(numberFe)).append("\n");
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
