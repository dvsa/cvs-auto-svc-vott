package vott.models.dto.seeddata;

import vott.models.dto.techrecordsv3.TechRecordCarComplete;

public class TechRecordCarCompleteGenerator extends AbstractTechRecordGenerator<TechRecordCarComplete> {
    public TechRecordCarCompleteGenerator(TechRecordCarComplete content) {
        super(content);
    }

    public TechRecordCarComplete randomizeCarUniqueValues(TechRecordCarComplete trCar ){
        trCar.setVin(randomString(MAX_VIN_LENGTH));
        //partial vin not set as this is created in dynamo based on vin
        trCar.setPrimaryVrm(randomString(MAX_VRM_LENGTH));
        return trCar;
    }
}
