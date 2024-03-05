package vott.models.dto.seeddata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vott.models.dto.techrecordsv3.AdrRemediationClass;
import vott.models.dto.techrecordsv3.TechRecordAdrDetailsAdditionalExaminerNote;
import vott.models.dto.techrecordsv3.TechRecordAdrDetailsTankTankDetailsTc3Detail;

public class AdrSeedData {

    private AdrRemediationClass adrData;

    public AdrSeedData(String systemNumber, String createdTimestamp) {
        adrData = new AdrRemediationClass();
        adrData.setSystem_Number(systemNumber);
        adrData.setCreated_Timestamp(createdTimestamp);
    }

    public AdrRemediationClass vehicleTypeTankStatement() {
        setApplicantOperatorDetails();
        setAdrDetailsSection();
        setGenericTankDetails();
        setTankSelectToStatement();
        setDeclarationsSeen();
        setAdditionalExaminerNotes();
        return adrData;
    }

    public AdrRemediationClass vehicleTypeTankProductListWithRefNo() {
        adrData.setTechRecordAdrDetailsVehicleDetailsType("Rigid tank");
        setApplicantOperatorDetails();
        setAdrDetailsSection();
        setGenericTankDetails();
        setTankSelectToProductListWithRefNo();
        setDeclarationsSeen();
        setAdditionalExaminerNotes();
        return adrData;
    }

    public AdrRemediationClass vehicleTypeTankProductListWithUnNoList() {
        adrData.setTechRecordAdrDetailsVehicleDetailsType("Rigid tank");
        setApplicantOperatorDetails();
        setAdrDetailsSection();
        setGenericTankDetails();
        setTankSelectToProductListWithUnNo();
        setDeclarationsSeen();
        setAdditionalExaminerNotes();
        return adrData;
    }

    public AdrRemediationClass vehicleTypeWithBatteryAdrDetails() {
        adrData.setTechRecordAdrDetailsVehicleDetailsType("Rigid battery");
        setApplicantOperatorDetails();
        setAdrDetailsSection();
        setGenericTankDetails();
        setTankSelectToStatement();
        setDeclarationsSeen();
        setAdditionalExaminerNotes();
        setBatteryListApplicable();
        return adrData;
    }

    public AdrRemediationClass vehicleTypeNotTankOrBattery() {
        setApplicantOperatorDetails();
        setAdrDetailsSection();
        setDeclarationsSeen();
        setAdditionalExaminerNotes();
        return adrData;
    }

    public AdrRemediationClass minimumAdrDataNotTank() {
        mandatoryAdrFieldsOnlyNotTankOrBattery();
        return adrData;
    }

    public AdrRemediationClass minimumAdrDataForTankOrBattery() {
        mandatoryAdrFieldsOnlyNotTankOrBattery();
        mandatoryAdrFieldsTankOrBatteryStatement();
        return adrData;
    }

    // mandatory fields for any ADR data
    private void mandatoryAdrFieldsOnlyNotTankOrBattery() {
        adrData.setTechRecordAdrDetailsDangerousGoods(true);
        adrData.setTechRecordAdrDetailsVehicleDetailsType("Artic tractor");
        adrData.setTechRecordAdrDetailsVehicleDetailsApprovalDate("2024-01-13");
        String[] permittedGoods = new String[] { "MEMU" };
        adrData.setTechRecordAdrDetailsPermittedDangerousGoods(Arrays.asList(permittedGoods));
        String[] additionalNotedNumber = new String[] { "1A" };
        adrData.setTechRecordAdrDetailsAdditionalNotesNumber(Arrays.asList(additionalNotedNumber));

    }

    // additional mandatory fields if vehicle type is tank or battery
    private void mandatoryAdrFieldsTankOrBatteryStatement() {
        adrData.setTechRecordAdrDetailsVehicleDetailsType("Rigid tank");
        adrData.setTechRecordAdrDetailsTankTankDetailsTankManufacturer("tankManufacturer");
        adrData.setTechRecordAdrDetailsTankTankDetailsYearOfManufacture(2018);
        adrData.setTechRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo("serialNo9");
        adrData.setTechRecordAdrDetailsTankTankDetailsTankTypeAppNo("appNumber1");
        adrData.setTechRecordAdrDetailsTankTankDetailsTankCode("tankCode");
        adrData.setTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted(
                "Substances permitted under the tank code and any special provisions specified in 9 may be carried");
        adrData.setTechRecordAdrDetailsTankTankDetailsTankStatementSelect("Statement");
    }

    // completes mandatory and optional fields with data
    private void setApplicantOperatorDetails() {
        adrData.setTechRecordAdrDetailsApplicantDetailsName("applicantName");
        adrData.setTechRecordAdrDetailsApplicantDetailsStreet("street");
        adrData.setTechRecordAdrDetailsApplicantDetailsTown("town");
        adrData.setTechRecordAdrDetailsApplicantDetailsCity("city");
        adrData.setTechRecordAdrDetailsApplicantDetailsPostcode("AB12CD");
        adrData.setTechRecordAdrDetailsDangerousGoods(true);
        adrData.setTechRecordAdrDetailsVehicleDetailsApprovalDate("2024-01-13");
    }

    private void setAdrDetailsSection() {
        String[] permittedGoods = new String[] { "Explosives (type 2)" };
        adrData.setTechRecordAdrDetailsPermittedDangerousGoods(Arrays.asList(permittedGoods));
        adrData.setTechRecordAdrDetailsCompatibilityGroupJ("I");
        String[] additionalNotedNumber = new String[] { "1A" };
        adrData.setTechRecordAdrDetailsAdditionalNotesNumber(Arrays.asList(additionalNotedNumber));
        adrData.setTechRecordAdrDetailsAdrTypeApprovalNo("717");
    }

    private void setGenericTankDetails() {
        adrData.setTechRecordAdrDetailsTankTankDetailsTankManufacturer("tankManufacturer");
        adrData.setTechRecordAdrDetailsTankTankDetailsYearOfManufacture(2018);
        adrData.setTechRecordAdrDetailsTankTankDetailsTankManufacturerSerialNo("serialNo9");
        adrData.setTechRecordAdrDetailsTankTankDetailsTankTypeAppNo("appNumber1");
        adrData.setTechRecordAdrDetailsTankTankDetailsTankCode("tankCode");
        adrData.setTechRecordAdrDetailsTankTankDetailsTankStatementSubstancesPermitted(
                "Substances permitted under the tank code and any special provisions specified in 9 may be carried");
        adrData.setTechRecordAdrDetailsTankTankDetailsSpecialProvisions("specialProvisions");
        adrData.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2Type("initial");
        adrData.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateApprovalNo("tc2Approval88");
        adrData.setTechRecordAdrDetailsTankTankDetailsTc2DetailsTc2IntermediateExpiryDate("2024-02-14");
        TechRecordAdrDetailsTankTankDetailsTc3Detail tc3Details = new TechRecordAdrDetailsTankTankDetailsTc3Detail(
                "exceptional", "tc3pNumber", "2024-03-15");
        List<TechRecordAdrDetailsTankTankDetailsTc3Detail> tc3DetailsList = new ArrayList<>();
        tc3DetailsList.add(tc3Details);
        adrData.setTechRecordAdrDetailsTankTankDetailsTc3Details(tc3DetailsList);
        String[] memosApply = new String[] { "07/09 3mth leak ext" };
        adrData.setTechRecordAdrDetailsMemosApply(Arrays.asList(memosApply));
        adrData.setTechRecordAdrDetailsM145Statement(true);
        String[] tankDocuments = new String[] { "tankDoc45" };
        adrData.setTechRecordAdrDetailsDocuments(Arrays.asList(tankDocuments));
    }

    // In FE for tank details user select either statement or product list
    private void setTankSelectToStatement() {
        adrData.setTechRecordAdrDetailsTankTankDetailsTankStatementSelect("Statement");
        adrData.setTechRecordAdrDetailsTankTankDetailsTankStatementStatement("tankStatementRef4");
    }

    // In FE either product list ref no or product list UnNo must be completed on
    // selecting product list
    private void setTankSelectToProductListWithRefNo() {
        adrData.setTechRecordAdrDetailsTankTankDetailsTankStatementSelect("Product list");
        adrData.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListRefNo("756172");
        adrData.setTechRecordAdrDetailsTankTankDetailsTankStatementProductList("productList1");
    }

    private void setTankSelectToProductListWithUnNo() {
        adrData.setTechRecordAdrDetailsTankTankDetailsTankStatementSelect("Product list");
        String[] producListUnNo = { "UnNo1", "UnoNo2" };
        adrData.setTechRecordAdrDetailsTankTankDetailsTankStatementProductListUnNo(Arrays.asList(producListUnNo));
        adrData.setTechRecordAdrDetailsTankTankDetailsTankStatementProductList("productList1");
    }

    // only applicable to vehicle types that contain battery
    private void setBatteryListApplicable() {
        adrData.setTechRecordAdrDetailsListStatementApplicable(true);
        adrData.setTechRecordAdrDetailsBatteryListNumber("88522145");
    }

    private void setDeclarationsSeen() {
        adrData.setTechRecordAdrDetailsBrakeDeclarationsSeen(true);
        adrData.setTechRecordAdrDetailsBrakeDeclarationIssuer("breakDecIssuer");
        adrData.setTechRecordAdrDetailsBrakeEndurance(true);
        adrData.setTechRecordAdrDetailsWeight(70.00);
        adrData.setTechRecordAdrDetailsDeclarationsSeen(true);
    }

    private void setAdditionalExaminerNotes() {
        TechRecordAdrDetailsAdditionalExaminerNote examinerNote = new TechRecordAdrDetailsAdditionalExaminerNote(
                "2024-05-18", "lastExaminer", "examinerNote");
        List<TechRecordAdrDetailsAdditionalExaminerNote> examinerNoteList = new ArrayList<>();
        examinerNoteList.add(examinerNote);
        adrData.setTechRecordAdrDetailsAdditionalExaminerNotes(examinerNoteList);
        adrData.setTechRecordAdrDetailsAdrCertificateNotes("certificateNotes");

    }
}
