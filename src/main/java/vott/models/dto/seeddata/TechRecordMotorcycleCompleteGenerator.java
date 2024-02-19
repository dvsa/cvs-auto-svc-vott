package vott.models.dto.seeddata;

import vott.models.dto.techrecordsv3.TechRecordMotorcycleComplete;

public class TechRecordMotorcycleCompleteGenerator extends AbstractTechRecordGenerator<TechRecordMotorcycleComplete> {
    public TechRecordMotorcycleCompleteGenerator(TechRecordMotorcycleComplete content) {
        super(content);
    }

    public TechRecordMotorcycleComplete randomizeMotorcycleUniqueValues(TechRecordMotorcycleComplete trMotorcycle ){
        trMotorcycle.setVin(randomString(MAX_VIN_LENGTH));
        //partial vin not set as this is created in dynamo based on vin
        trMotorcycle.setPrimaryVrm(randomString(MAX_VRM_LENGTH));
        return trMotorcycle;
    }
}
