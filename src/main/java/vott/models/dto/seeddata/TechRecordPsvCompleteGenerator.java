package vott.models.dto.seeddata;

import vott.models.dto.techrecordsv3.TechRecordPsvComplete;

public class TechRecordPsvCompleteGenerator extends AbstractTechRecordGenerator<TechRecordPsvComplete> {
    public TechRecordPsvCompleteGenerator(TechRecordPsvComplete content) {
        super(content);
    }

    public TechRecordPsvComplete randomizePsvUniqueValues(TechRecordPsvComplete trPSV ){
        trPSV.setVin(randomString(MAX_VIN_LENGTH));
        //partial vin not set as this is created in dynamo based on vin
        trPSV.setPrimaryVrm(randomString(MAX_VRM_LENGTH));
        return trPSV;
    }
}
