package vott.config;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ApiProperties {
    @SerializedName("baseUrl")
    private String baseUrl;
    @SerializedName("branch")
    private String branch;

    public String getBranchSpecificUrl() {
        String branch = this.branch.startsWith("/") ? this.branch.substring(1) : this.branch;
        return baseUrl + (baseUrl.endsWith("/") ? branch : "/" + branch);
    }
}
