package vott.models.dto.seeddata;

import vott.models.dto.techrecordsv3.TechRecordSmallTrlComplete;

public class TechRecordSmallTrlCompleteGenerator extends AbstractTechRecordGenerator<TechRecordSmallTrlComplete> {
    public TechRecordSmallTrlCompleteGenerator(TechRecordSmallTrlComplete content) {
        super(content);
    }

    public TechRecordSmallTrlComplete randomizeSmallTrlUniqueValues(TechRecordSmallTrlComplete trSmallTRL ){
        trSmallTRL.setVin(randomString(MAX_VIN_LENGTH));
        //partial vin not set as this is created in dynamo based on vin
        trSmallTRL.setTrailerId(randomString(MAX_TRAILER_ID_LENGTH));
        return trSmallTRL;
    }
}
