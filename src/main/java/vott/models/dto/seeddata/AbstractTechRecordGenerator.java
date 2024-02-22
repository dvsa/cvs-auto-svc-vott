package vott.models.dto.seeddata;

import com.google.gson.Gson;
import vott.json.GsonInstance;
import vott.models.dto.techrecordsv3.TechRecordV3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;

public abstract class AbstractTechRecordGenerator<T extends TechRecordV3> {

    private final T content;
    private static final Gson gson = GsonInstance.get();
    protected static final String ALLOWED_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    protected static final SecureRandom rnd = new SecureRandom();
    protected static final int MAX_VIN_LENGTH = 21;
    protected static final int MAX_VRM_LENGTH = 8;
    protected static final int MAX_TRAILER_ID_LENGTH = 8;


    public AbstractTechRecordGenerator(T content) {
        this.content = content;
    }

    public Class<?> getClassOfT() {
        return this.content.getClass();
    }

    public T createTechRecordFromJsonFile(String jsonFilePath) {
        try {

            return (T) gson.fromJson(
                    Files.newBufferedReader(Paths.get(jsonFilePath)),
                    getClassOfT());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public T createTechRecordFromJsonString(String json) {
        return (T) gson.fromJson(json, getClassOfT());
    }

    public String createJsonStringFromTechRecord(T techRecord) {
        return gson.toJson(techRecord);
    }


    //randomize unique values to make data created more easily identifiable
    //on tech record creation a new system number will be generated which is part of the unique index for a vehicle in NOP
    //so even if a vin already existed a new record creation in dynamo/NOP is assured
    protected String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++)
            sb.append(ALLOWED_CHARACTERS.charAt(rnd.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }
}



