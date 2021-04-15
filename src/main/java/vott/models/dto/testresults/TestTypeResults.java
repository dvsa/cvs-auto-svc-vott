/*
 * Test Results Microservice
 * This is the API spec for capturing test results. These test result will be stored in the AWS DynamoDB database. Authorization details will be updated once we have confirmed the security scheme we are using.
 *
 * OpenAPI spec version: 1.0.0
 * Contact: test@test.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package vott.models.dto.testresults;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import vott.models.adapter.ExplicitNull;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * TestTypeResults
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-04-13T13:44:54.508Z[GMT]")
public class TestTypeResults {
  @SerializedName("createdAt")
  private OffsetDateTime createdAt = null;

  @SerializedName("lastUpdatedAt")
  private OffsetDateTime lastUpdatedAt = null;

  @SerializedName("deletionFlag")
  private Boolean deletionFlag = null;

  @SerializedName("testCode")
  private String testCode = null;

  @SerializedName("testTypeName")
  private String testTypeName = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("testTypeId")
  private String testTypeId = null;

  @SerializedName("testNumber")
  private String testNumber = null;

  @SerializedName("certificateNumber")
  private String certificateNumber = null;

  @SerializedName("secondaryCertificateNumber")
  private String secondaryCertificateNumber = null;

  @SerializedName("certificateLink")
  private String certificateLink = null;

  @SerializedName("testExpiryDate")
  private LocalDate testExpiryDate = null;

  @SerializedName("testAnniversaryDate")
  private LocalDate testAnniversaryDate = null;

  @SerializedName("testTypeStartTimestamp")
  private OffsetDateTime testTypeStartTimestamp = null;

  @SerializedName("testTypeEndTimestamp")
  private OffsetDateTime testTypeEndTimestamp = null;

  @SerializedName("statusUpdatedFlag")
  private Boolean statusUpdatedFlag = null;

  @SerializedName("numberOfSeatbeltsFitted")
  private BigDecimal numberOfSeatbeltsFitted = null;

  @SerializedName("lastSeatbeltInstallationCheckDate")
  private LocalDate lastSeatbeltInstallationCheckDate = null;

  @SerializedName("seatbeltInstallationCheckDate")
  private Boolean seatbeltInstallationCheckDate = null;

  /**
   * Nullable only for Cancelled tests.
   */
  @JsonAdapter(TestResultEnum.Adapter.class)
  public enum TestResultEnum {
    FAIL("fail"),
    PASS("pass"),
    PRS("prs"),
    ABANDONED("abandoned");

    private String value;

    TestResultEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static TestResultEnum fromValue(String text) {
      for (TestResultEnum b : TestResultEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<TestResultEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final TestResultEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public TestResultEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return TestResultEnum.fromValue(String.valueOf(value));
      }
    }
  }  @SerializedName("testResult")
  private TestResultEnum testResult = null;

  @SerializedName("prohibitionIssued")
  private Boolean prohibitionIssued = null;

  @ExplicitNull
  @SerializedName("reasonForAbandoning")
  private String reasonForAbandoning = null;

  @SerializedName("additionalNotesRecorded")
  private String additionalNotesRecorded = null;

  @SerializedName("additionalCommentsForAbandon")
  private String additionalCommentsForAbandon = null;

  @SerializedName("modType")
  private TestTypeResultsModType modType = null;

  /**
   * Used only for LEC tests.
   */
  @JsonAdapter(EmissionStandardEnum.Adapter.class)
  public enum EmissionStandardEnum {
    _0_10_G_KWH_EURO_3_PM("0.10 g/kWh Euro 3 PM"),
    _0_03_G_KWH_EURO_IV_PM("0.03 g/kWh Euro IV PM"),
    EURO_3("Euro 3"),
    EURO_4("Euro 4"),
    EURO_6("Euro 6"),
    EURO_VI("Euro VI"),
    FULL_ELECTRIC("Full Electric");

    private String value;

    EmissionStandardEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static EmissionStandardEnum fromValue(String text) {
      for (EmissionStandardEnum b : EmissionStandardEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<EmissionStandardEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final EmissionStandardEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public EmissionStandardEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return EmissionStandardEnum.fromValue(String.valueOf(value));
      }
    }
  }  @SerializedName("emissionStandard")
  private EmissionStandardEnum emissionStandard = null;

  /**
   * Used only for LEC tests.
   */
  @JsonAdapter(FuelTypeEnum.Adapter.class)
  public enum FuelTypeEnum {
    DIESEL("diesel"),
    GAS_CNG("gas-cng"),
    GAS_LNG("gas-lng"),
    GAS_LPG("gas-lpg"),
    FUEL_CELL("fuel cell"),
    PETROL("petrol"),
    FULL_ELECTRIC("full electric");

    private String value;

    FuelTypeEnum(String value) {
      this.value = value;
    }
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }
    public static FuelTypeEnum fromValue(String text) {
      for (FuelTypeEnum b : FuelTypeEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
    public static class Adapter extends TypeAdapter<FuelTypeEnum> {
      @Override
      public void write(final JsonWriter jsonWriter, final FuelTypeEnum enumeration) throws IOException {
        jsonWriter.value(enumeration.getValue());
      }

      @Override
      public FuelTypeEnum read(final JsonReader jsonReader) throws IOException {
        Object value = jsonReader.nextString();
        return FuelTypeEnum.fromValue(String.valueOf(value));
      }
    }
  }  @SerializedName("fuelType")
  private FuelTypeEnum fuelType = null;

  @SerializedName("particulateTrapFitted")
  private String particulateTrapFitted = null;

  @SerializedName("particulateTrapSerialNumber")
  private String particulateTrapSerialNumber = null;

  @SerializedName("modificationTypeUsed")
  private String modificationTypeUsed = null;

  @SerializedName("smokeTestKLimitApplied")
  private String smokeTestKLimitApplied = null;

  @SerializedName("defects")
  private Defects defects = null;

  @SerializedName("customDefects")
  private CustomDefects customDefects = null;

  public TestTypeResults createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Not sent from FE, calculated in the BE.
   * @return createdAt
  **/
  
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public TestTypeResults lastUpdatedAt(OffsetDateTime lastUpdatedAt) {
    this.lastUpdatedAt = lastUpdatedAt;
    return this;
  }

   /**
   * Not sent from FE, calculated in the BE.
   * @return lastUpdatedAt
  **/
  
  public OffsetDateTime getLastUpdatedAt() {
    return lastUpdatedAt;
  }

  public void setLastUpdatedAt(OffsetDateTime lastUpdatedAt) {
    this.lastUpdatedAt = lastUpdatedAt;
  }

  public TestTypeResults deletionFlag(Boolean deletionFlag) {
    this.deletionFlag = deletionFlag;
    return this;
  }

   /**
   * Not sent from FE, calculated in the BE.
   * @return deletionFlag
  **/
  
  public Boolean isDeletionFlag() {
    return deletionFlag;
  }

  public void setDeletionFlag(Boolean deletionFlag) {
    this.deletionFlag = deletionFlag;
  }

  public TestTypeResults testCode(String testCode) {
    this.testCode = testCode;
    return this;
  }

   /**
   * Not sent from FE, calculated in the BE.
   * @return testCode
  **/
  
  public String getTestCode() {
    return testCode;
  }

  public void setTestCode(String testCode) {
    this.testCode = testCode;
  }

  public TestTypeResults testTypeName(String testTypeName) {
    this.testTypeName = testTypeName;
    return this;
  }

   /**
   * Get testTypeName
   * @return testTypeName
  **/
  
  public String getTestTypeName() {
    return testTypeName;
  }

  public void setTestTypeName(String testTypeName) {
    this.testTypeName = testTypeName;
  }

  public TestTypeResults name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TestTypeResults testTypeId(String testTypeId) {
    this.testTypeId = testTypeId;
    return this;
  }

   /**
   * Get testTypeId
   * @return testTypeId
  **/
  
  public String getTestTypeId() {
    return testTypeId;
  }

  public void setTestTypeId(String testTypeId) {
    this.testTypeId = testTypeId;
  }

  public TestTypeResults testNumber(String testNumber) {
    this.testNumber = testNumber;
    return this;
  }

   /**
   * Not sent from FE, calculated in the BE.
   * @return testNumber
  **/
  
  public String getTestNumber() {
    return testNumber;
  }

  public void setTestNumber(String testNumber) {
    this.testNumber = testNumber;
  }

  public TestTypeResults certificateNumber(String certificateNumber) {
    this.certificateNumber = certificateNumber;
    return this;
  }

   /**
   * Get certificateNumber
   * @return certificateNumber
  **/
  
  public String getCertificateNumber() {
    return certificateNumber;
  }

  public void setCertificateNumber(String certificateNumber) {
    this.certificateNumber = certificateNumber;
  }

  public TestTypeResults secondaryCertificateNumber(String secondaryCertificateNumber) {
    this.secondaryCertificateNumber = secondaryCertificateNumber;
    return this;
  }

   /**
   * Get secondaryCertificateNumber
   * @return secondaryCertificateNumber
  **/
  
  public String getSecondaryCertificateNumber() {
    return secondaryCertificateNumber;
  }

  public void setSecondaryCertificateNumber(String secondaryCertificateNumber) {
    this.secondaryCertificateNumber = secondaryCertificateNumber;
  }

  public TestTypeResults certificateLink(String certificateLink) {
    this.certificateLink = certificateLink;
    return this;
  }

   /**
   * Not sent from FE, calculated in the BE.
   * @return certificateLink
  **/
  
  public String getCertificateLink() {
    return certificateLink;
  }

  public void setCertificateLink(String certificateLink) {
    this.certificateLink = certificateLink;
  }

  public TestTypeResults testExpiryDate(LocalDate testExpiryDate) {
    this.testExpiryDate = testExpiryDate;
    return this;
  }

   /**
   * Sent form FE only for LEC tests. For the rest of the test types it is not sent from FE, and calculated in the BE.
   * @return testExpiryDate
  **/
  
  public LocalDate getTestExpiryDate() {
    return testExpiryDate;
  }

  public void setTestExpiryDate(LocalDate testExpiryDate) {
    this.testExpiryDate = testExpiryDate;
  }

  public TestTypeResults testAnniversaryDate(LocalDate testAnniversaryDate) {
    this.testAnniversaryDate = testAnniversaryDate;
    return this;
  }

   /**
   * Not sent from FE, calculated in the BE.
   * @return testAnniversaryDate
  **/
  
  public LocalDate getTestAnniversaryDate() {
    return testAnniversaryDate;
  }

  public void setTestAnniversaryDate(LocalDate testAnniversaryDate) {
    this.testAnniversaryDate = testAnniversaryDate;
  }

  public TestTypeResults testTypeStartTimestamp(OffsetDateTime testTypeStartTimestamp) {
    this.testTypeStartTimestamp = testTypeStartTimestamp;
    return this;
  }

   /**
   * Get testTypeStartTimestamp
   * @return testTypeStartTimestamp
  **/
  
  public OffsetDateTime getTestTypeStartTimestamp() {
    return testTypeStartTimestamp;
  }

  public void setTestTypeStartTimestamp(OffsetDateTime testTypeStartTimestamp) {
    this.testTypeStartTimestamp = testTypeStartTimestamp;
  }

  public TestTypeResults testTypeEndTimestamp(OffsetDateTime testTypeEndTimestamp) {
    this.testTypeEndTimestamp = testTypeEndTimestamp;
    return this;
  }

   /**
   * Nullable only for Cancelled tests.
   * @return testTypeEndTimestamp
  **/
  
  public OffsetDateTime getTestTypeEndTimestamp() {
    return testTypeEndTimestamp;
  }

  public void setTestTypeEndTimestamp(OffsetDateTime testTypeEndTimestamp) {
    this.testTypeEndTimestamp = testTypeEndTimestamp;
  }

  public TestTypeResults statusUpdatedFlag(Boolean statusUpdatedFlag) {
    this.statusUpdatedFlag = statusUpdatedFlag;
    return this;
  }

   /**
   * Sent from FE. Used to determine which test-type was updated/archived
   * @return statusUpdatedFlag
  **/
  
  public Boolean isStatusUpdatedFlag() {
    return statusUpdatedFlag;
  }

  public void setStatusUpdatedFlag(Boolean statusUpdatedFlag) {
    this.statusUpdatedFlag = statusUpdatedFlag;
  }

  public TestTypeResults numberOfSeatbeltsFitted(BigDecimal numberOfSeatbeltsFitted) {
    this.numberOfSeatbeltsFitted = numberOfSeatbeltsFitted;
    return this;
  }

   /**
   * mandatory for PSV only, not applicable for HGV and TRL
   * @return numberOfSeatbeltsFitted
  **/
  
  public BigDecimal getNumberOfSeatbeltsFitted() {
    return numberOfSeatbeltsFitted;
  }

  public void setNumberOfSeatbeltsFitted(BigDecimal numberOfSeatbeltsFitted) {
    this.numberOfSeatbeltsFitted = numberOfSeatbeltsFitted;
  }

  public TestTypeResults lastSeatbeltInstallationCheckDate(LocalDate lastSeatbeltInstallationCheckDate) {
    this.lastSeatbeltInstallationCheckDate = lastSeatbeltInstallationCheckDate;
    return this;
  }

   /**
   * mandatory for PSV only, not applicable for HGV and TRL
   * @return lastSeatbeltInstallationCheckDate
  **/
  
  public LocalDate getLastSeatbeltInstallationCheckDate() {
    return lastSeatbeltInstallationCheckDate;
  }

  public void setLastSeatbeltInstallationCheckDate(LocalDate lastSeatbeltInstallationCheckDate) {
    this.lastSeatbeltInstallationCheckDate = lastSeatbeltInstallationCheckDate;
  }

  public TestTypeResults seatbeltInstallationCheckDate(Boolean seatbeltInstallationCheckDate) {
    this.seatbeltInstallationCheckDate = seatbeltInstallationCheckDate;
    return this;
  }

   /**
   * mandatory for PSV only, not applicable for HGV and TRL
   * @return seatbeltInstallationCheckDate
  **/
  
  public Boolean isSeatbeltInstallationCheckDate() {
    return seatbeltInstallationCheckDate;
  }

  public void setSeatbeltInstallationCheckDate(Boolean seatbeltInstallationCheckDate) {
    this.seatbeltInstallationCheckDate = seatbeltInstallationCheckDate;
  }

  public TestTypeResults testResult(TestResultEnum testResult) {
    this.testResult = testResult;
    return this;
  }

   /**
   * Nullable only for Cancelled tests.
   * @return testResult
  **/
  
  public TestResultEnum getTestResult() {
    return testResult;
  }

  public void setTestResult(TestResultEnum testResult) {
    this.testResult = testResult;
  }

  public TestTypeResults prohibitionIssued(Boolean prohibitionIssued) {
    this.prohibitionIssued = prohibitionIssued;
    return this;
  }

   /**
   * Get prohibitionIssued
   * @return prohibitionIssued
  **/
  
  public Boolean isProhibitionIssued() {
    return prohibitionIssued;
  }

  public void setProhibitionIssued(Boolean prohibitionIssued) {
    this.prohibitionIssued = prohibitionIssued;
  }

  public TestTypeResults reasonForAbandoning(String reasonForAbandoning) {
    this.reasonForAbandoning = reasonForAbandoning;
    return this;
  }

   /**
   * Required for Abandoned tests.
   * @return reasonForAbandoning
  **/
  
  public String getReasonForAbandoning() {
    return reasonForAbandoning;
  }

  public void setReasonForAbandoning(String reasonForAbandoning) {
    this.reasonForAbandoning = reasonForAbandoning;
  }

  public TestTypeResults additionalNotesRecorded(String additionalNotesRecorded) {
    this.additionalNotesRecorded = additionalNotesRecorded;
    return this;
  }

   /**
   * Get additionalNotesRecorded
   * @return additionalNotesRecorded
  **/
  
  public String getAdditionalNotesRecorded() {
    return additionalNotesRecorded;
  }

  public void setAdditionalNotesRecorded(String additionalNotesRecorded) {
    this.additionalNotesRecorded = additionalNotesRecorded;
  }

  public TestTypeResults additionalCommentsForAbandon(String additionalCommentsForAbandon) {
    this.additionalCommentsForAbandon = additionalCommentsForAbandon;
    return this;
  }

   /**
   * Get additionalCommentsForAbandon
   * @return additionalCommentsForAbandon
  **/
  
  public String getAdditionalCommentsForAbandon() {
    return additionalCommentsForAbandon;
  }

  public void setAdditionalCommentsForAbandon(String additionalCommentsForAbandon) {
    this.additionalCommentsForAbandon = additionalCommentsForAbandon;
  }

  public TestTypeResults modType(TestTypeResultsModType modType) {
    this.modType = modType;
    return this;
  }

   /**
   * Get modType
   * @return modType
  **/
  
  public TestTypeResultsModType getModType() {
    return modType;
  }

  public void setModType(TestTypeResultsModType modType) {
    this.modType = modType;
  }

  public TestTypeResults emissionStandard(EmissionStandardEnum emissionStandard) {
    this.emissionStandard = emissionStandard;
    return this;
  }

   /**
   * Used only for LEC tests.
   * @return emissionStandard
  **/
  
  public EmissionStandardEnum getEmissionStandard() {
    return emissionStandard;
  }

  public void setEmissionStandard(EmissionStandardEnum emissionStandard) {
    this.emissionStandard = emissionStandard;
  }

  public TestTypeResults fuelType(FuelTypeEnum fuelType) {
    this.fuelType = fuelType;
    return this;
  }

   /**
   * Used only for LEC tests.
   * @return fuelType
  **/
  
  public FuelTypeEnum getFuelType() {
    return fuelType;
  }

  public void setFuelType(FuelTypeEnum fuelType) {
    this.fuelType = fuelType;
  }

  public TestTypeResults particulateTrapFitted(String particulateTrapFitted) {
    this.particulateTrapFitted = particulateTrapFitted;
    return this;
  }

   /**
   * Used only for LEC tests.
   * @return particulateTrapFitted
  **/
  
  public String getParticulateTrapFitted() {
    return particulateTrapFitted;
  }

  public void setParticulateTrapFitted(String particulateTrapFitted) {
    this.particulateTrapFitted = particulateTrapFitted;
  }

  public TestTypeResults particulateTrapSerialNumber(String particulateTrapSerialNumber) {
    this.particulateTrapSerialNumber = particulateTrapSerialNumber;
    return this;
  }

   /**
   * Used only for LEC tests.
   * @return particulateTrapSerialNumber
  **/
  
  public String getParticulateTrapSerialNumber() {
    return particulateTrapSerialNumber;
  }

  public void setParticulateTrapSerialNumber(String particulateTrapSerialNumber) {
    this.particulateTrapSerialNumber = particulateTrapSerialNumber;
  }

  public TestTypeResults modificationTypeUsed(String modificationTypeUsed) {
    this.modificationTypeUsed = modificationTypeUsed;
    return this;
  }

   /**
   * Used only for LEC tests.
   * @return modificationTypeUsed
  **/
  
  public String getModificationTypeUsed() {
    return modificationTypeUsed;
  }

  public void setModificationTypeUsed(String modificationTypeUsed) {
    this.modificationTypeUsed = modificationTypeUsed;
  }

  public TestTypeResults smokeTestKLimitApplied(String smokeTestKLimitApplied) {
    this.smokeTestKLimitApplied = smokeTestKLimitApplied;
    return this;
  }

   /**
   * Used only for LEC tests.
   * @return smokeTestKLimitApplied
  **/
  
  public String getSmokeTestKLimitApplied() {
    return smokeTestKLimitApplied;
  }

  public void setSmokeTestKLimitApplied(String smokeTestKLimitApplied) {
    this.smokeTestKLimitApplied = smokeTestKLimitApplied;
  }

  public TestTypeResults defects(Defects defects) {
    this.defects = defects;
    return this;
  }

   /**
   * Get defects
   * @return defects
  **/
  
  public Defects getDefects() {
    return defects;
  }

  public void setDefects(Defects defects) {
    this.defects = defects;
  }

  public TestTypeResults customDefects(CustomDefects customDefects) {
    this.customDefects = customDefects;
    return this;
  }

   /**
   * Get customDefects
   * @return customDefects
  **/
  
  public CustomDefects getCustomDefects() {
    return customDefects;
  }

  public void setCustomDefects(CustomDefects customDefects) {
    this.customDefects = customDefects;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TestTypeResults testTypeResults = (TestTypeResults) o;
    return Objects.equals(this.createdAt, testTypeResults.createdAt) &&
        Objects.equals(this.lastUpdatedAt, testTypeResults.lastUpdatedAt) &&
        Objects.equals(this.deletionFlag, testTypeResults.deletionFlag) &&
        Objects.equals(this.testCode, testTypeResults.testCode) &&
        Objects.equals(this.testTypeName, testTypeResults.testTypeName) &&
        Objects.equals(this.name, testTypeResults.name) &&
        Objects.equals(this.testTypeId, testTypeResults.testTypeId) &&
        Objects.equals(this.testNumber, testTypeResults.testNumber) &&
        Objects.equals(this.certificateNumber, testTypeResults.certificateNumber) &&
        Objects.equals(this.secondaryCertificateNumber, testTypeResults.secondaryCertificateNumber) &&
        Objects.equals(this.certificateLink, testTypeResults.certificateLink) &&
        Objects.equals(this.testExpiryDate, testTypeResults.testExpiryDate) &&
        Objects.equals(this.testAnniversaryDate, testTypeResults.testAnniversaryDate) &&
        Objects.equals(this.testTypeStartTimestamp, testTypeResults.testTypeStartTimestamp) &&
        Objects.equals(this.testTypeEndTimestamp, testTypeResults.testTypeEndTimestamp) &&
        Objects.equals(this.statusUpdatedFlag, testTypeResults.statusUpdatedFlag) &&
        Objects.equals(this.numberOfSeatbeltsFitted, testTypeResults.numberOfSeatbeltsFitted) &&
        Objects.equals(this.lastSeatbeltInstallationCheckDate, testTypeResults.lastSeatbeltInstallationCheckDate) &&
        Objects.equals(this.seatbeltInstallationCheckDate, testTypeResults.seatbeltInstallationCheckDate) &&
        Objects.equals(this.testResult, testTypeResults.testResult) &&
        Objects.equals(this.prohibitionIssued, testTypeResults.prohibitionIssued) &&
        Objects.equals(this.reasonForAbandoning, testTypeResults.reasonForAbandoning) &&
        Objects.equals(this.additionalNotesRecorded, testTypeResults.additionalNotesRecorded) &&
        Objects.equals(this.additionalCommentsForAbandon, testTypeResults.additionalCommentsForAbandon) &&
        Objects.equals(this.modType, testTypeResults.modType) &&
        Objects.equals(this.emissionStandard, testTypeResults.emissionStandard) &&
        Objects.equals(this.fuelType, testTypeResults.fuelType) &&
        Objects.equals(this.particulateTrapFitted, testTypeResults.particulateTrapFitted) &&
        Objects.equals(this.particulateTrapSerialNumber, testTypeResults.particulateTrapSerialNumber) &&
        Objects.equals(this.modificationTypeUsed, testTypeResults.modificationTypeUsed) &&
        Objects.equals(this.smokeTestKLimitApplied, testTypeResults.smokeTestKLimitApplied) &&
        Objects.equals(this.defects, testTypeResults.defects) &&
        Objects.equals(this.customDefects, testTypeResults.customDefects);
  }

  @Override
  public int hashCode() {
    return Objects.hash(createdAt, lastUpdatedAt, deletionFlag, testCode, testTypeName, name, testTypeId, testNumber, certificateNumber, secondaryCertificateNumber, certificateLink, testExpiryDate, testAnniversaryDate, testTypeStartTimestamp, testTypeEndTimestamp, statusUpdatedFlag, numberOfSeatbeltsFitted, lastSeatbeltInstallationCheckDate, seatbeltInstallationCheckDate, testResult, prohibitionIssued, reasonForAbandoning, additionalNotesRecorded, additionalCommentsForAbandon, modType, emissionStandard, fuelType, particulateTrapFitted, particulateTrapSerialNumber, modificationTypeUsed, smokeTestKLimitApplied, defects, customDefects);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TestTypeResults {\n");
    
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    lastUpdatedAt: ").append(toIndentedString(lastUpdatedAt)).append("\n");
    sb.append("    deletionFlag: ").append(toIndentedString(deletionFlag)).append("\n");
    sb.append("    testCode: ").append(toIndentedString(testCode)).append("\n");
    sb.append("    testTypeName: ").append(toIndentedString(testTypeName)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    testTypeId: ").append(toIndentedString(testTypeId)).append("\n");
    sb.append("    testNumber: ").append(toIndentedString(testNumber)).append("\n");
    sb.append("    certificateNumber: ").append(toIndentedString(certificateNumber)).append("\n");
    sb.append("    secondaryCertificateNumber: ").append(toIndentedString(secondaryCertificateNumber)).append("\n");
    sb.append("    certificateLink: ").append(toIndentedString(certificateLink)).append("\n");
    sb.append("    testExpiryDate: ").append(toIndentedString(testExpiryDate)).append("\n");
    sb.append("    testAnniversaryDate: ").append(toIndentedString(testAnniversaryDate)).append("\n");
    sb.append("    testTypeStartTimestamp: ").append(toIndentedString(testTypeStartTimestamp)).append("\n");
    sb.append("    testTypeEndTimestamp: ").append(toIndentedString(testTypeEndTimestamp)).append("\n");
    sb.append("    statusUpdatedFlag: ").append(toIndentedString(statusUpdatedFlag)).append("\n");
    sb.append("    numberOfSeatbeltsFitted: ").append(toIndentedString(numberOfSeatbeltsFitted)).append("\n");
    sb.append("    lastSeatbeltInstallationCheckDate: ").append(toIndentedString(lastSeatbeltInstallationCheckDate)).append("\n");
    sb.append("    seatbeltInstallationCheckDate: ").append(toIndentedString(seatbeltInstallationCheckDate)).append("\n");
    sb.append("    testResult: ").append(toIndentedString(testResult)).append("\n");
    sb.append("    prohibitionIssued: ").append(toIndentedString(prohibitionIssued)).append("\n");
    sb.append("    reasonForAbandoning: ").append(toIndentedString(reasonForAbandoning)).append("\n");
    sb.append("    additionalNotesRecorded: ").append(toIndentedString(additionalNotesRecorded)).append("\n");
    sb.append("    additionalCommentsForAbandon: ").append(toIndentedString(additionalCommentsForAbandon)).append("\n");
    sb.append("    modType: ").append(toIndentedString(modType)).append("\n");
    sb.append("    emissionStandard: ").append(toIndentedString(emissionStandard)).append("\n");
    sb.append("    fuelType: ").append(toIndentedString(fuelType)).append("\n");
    sb.append("    particulateTrapFitted: ").append(toIndentedString(particulateTrapFitted)).append("\n");
    sb.append("    particulateTrapSerialNumber: ").append(toIndentedString(particulateTrapSerialNumber)).append("\n");
    sb.append("    modificationTypeUsed: ").append(toIndentedString(modificationTypeUsed)).append("\n");
    sb.append("    smokeTestKLimitApplied: ").append(toIndentedString(smokeTestKLimitApplied)).append("\n");
    sb.append("    defects: ").append(toIndentedString(defects)).append("\n");
    sb.append("    customDefects: ").append(toIndentedString(customDefects)).append("\n");
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
