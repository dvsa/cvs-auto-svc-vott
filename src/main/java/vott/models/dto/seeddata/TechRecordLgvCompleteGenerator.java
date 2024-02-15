package vott.models.dto.seeddata;

import vott.models.dto.techrecordsv3.TechRecordLgvComplete;

public class TechRecordLgvCompleteGenerator extends AbstractTechRecordGenerator<TechRecordLgvComplete> {
    public TechRecordLgvCompleteGenerator(TechRecordLgvComplete content) {
        super(content);
    }

    public TechRecordLgvComplete randomizeLgvUniqueValues(TechRecordLgvComplete trLGV ){
        trLGV.setVin(randomString(MAX_VIN_LENGTH));
        //partial vin not set as this is created in dynamo based on vin
        trLGV.setPrimaryVrm(randomString(MAX_VRM_LENGTH));
        return trLGV;
    }
}
