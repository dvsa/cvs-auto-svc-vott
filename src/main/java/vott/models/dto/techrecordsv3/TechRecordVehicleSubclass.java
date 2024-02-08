package vott.models.dto.techrecordsv3;

import com.google.gson.annotations.SerializedName;

import javax.annotation.processing.Generated;
import java.util.HashMap;
import java.util.Map;

@Generated("jsonschema2pojo")
public enum TechRecordVehicleSubclass {

    @SerializedName("n")
    N("n"),
    @SerializedName("p")
    P("p"),
    @SerializedName("a")
    A("a"),
    @SerializedName("s")
    S("s"),
    @SerializedName("c")
    C("c"),
    @SerializedName("l")
    L("l"),
    @SerializedName("t")
    T("t"),
    @SerializedName("e")
    E("e"),
    @SerializedName("m")
    M("m"),
    @SerializedName("r")
    R("r"),
    @SerializedName("w")
    W("w");
    private final String value;
    private final static Map<String, TechRecordVehicleSubclass> CONSTANTS = new HashMap<String, TechRecordVehicleSubclass>();

    static {
        for (TechRecordVehicleSubclass c : values()) {
            CONSTANTS.put(c.value, c);
        }
    }

    TechRecordVehicleSubclass(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public String value() {
        return this.value;
    }

    public static TechRecordVehicleSubclass fromValue(String value) {
        TechRecordVehicleSubclass constant = CONSTANTS.get(value);
        if (constant == null) {
            throw new IllegalArgumentException(value);
        } else {
            return constant;
        }
    }

}
