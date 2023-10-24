package vott.updatestore;

import com.google.gson.Gson;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import vott.api.TechnicalRecordsV3;
import vott.api.VehiclesAPI;
import vott.auth.GrantType;
import vott.auth.OAuthVersion;
import vott.auth.TokenService;
import vott.json.GsonInstance;
import vott.models.dto.techrecords.TechRecordPOSTV3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RunWith(SerenityRunner.class)
public class InsertTechRecord {

    private TokenService v1ImplicitTokens;
    private String payloadPath;

    @Before
    public void setUp() throws Exception {
        v1ImplicitTokens = new TokenService(OAuthVersion.V1, GrantType.IMPLICIT);
        payloadPath = "src/main/resources/payloads/";
    }



    @Test
    public void createTechRecord() {

        TechRecordPOSTV3 techRecord = loadTechRecord(payloadPath + "/TechRecordsV3/HGV_2_Axel_Tech_Record_Annual_Test_Multiple_Test_Types.json");
        postTechRecord(techRecord);
    }


    public void postTechRecord(TechRecordPOSTV3 techRecord)
    {
        TechnicalRecordsV3.postTechnicalRecordV3(techRecord, v1ImplicitTokens.getBearerToken());

    }


    private TechRecordPOSTV3 loadTechRecord(String path)  {

        Gson gson = GsonInstance.get();
        try {
            return gson.fromJson(
                    Files.newBufferedReader(Paths.get(path)),
                    TechRecordPOSTV3.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
