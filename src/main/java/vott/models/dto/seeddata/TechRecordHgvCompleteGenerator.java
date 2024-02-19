package vott.models.dto.seeddata;

import vott.models.dto.techrecordsv3.TechRecordHgvComplete;

public class TechRecordHgvCompleteGenerator extends AbstractTechRecordGenerator<TechRecordHgvComplete> {
    public TechRecordHgvCompleteGenerator(TechRecordHgvComplete content) {
        super(content);
    }

    public TechRecordHgvComplete randomizeHgvUniqueValues(TechRecordHgvComplete trHGV ){
        trHGV.setVin(randomString(MAX_VIN_LENGTH));
        //partial vin not set as this is created in dynamo based on vin
        trHGV.setPrimaryVrm(randomString(MAX_VRM_LENGTH));
        return trHGV;
    }
}
