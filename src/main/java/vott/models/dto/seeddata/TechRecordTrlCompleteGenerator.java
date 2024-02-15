package vott.models.dto.seeddata;

import vott.models.dto.techrecordsv3.TechRecordTrlComplete;

public class TechRecordTrlCompleteGenerator extends AbstractTechRecordGenerator<TechRecordTrlComplete> {
    public TechRecordTrlCompleteGenerator(TechRecordTrlComplete content) {
        super(content);
    }

    public TechRecordTrlComplete randomizeTrlUniqueValues(TechRecordTrlComplete trTRL ){
        trTRL.setVin(randomString(MAX_VIN_LENGTH));
        //partial vin not set as this is created in dynamo based on vin
        trTRL.setTrailerId(randomString(MAX_TRAILER_ID_LENGTH));
        return trTRL;
    }
}
