package vott.models.dao;
import lombok.Data;
@Data
public class AuthIntoServices {
    private String id;
    private String technical_record_id;
    private String cocIssueDate;
    private String dateReceived;
    private String datePending;
    private String dateAuthorised;
    private String dateRejected;
}
