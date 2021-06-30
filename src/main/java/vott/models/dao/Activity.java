package vott.models.dao;

import lombok.Data;

@Data
public class Activity {

       // private String id;
        private String testStationID;
        private String testerID;
        private String activityID;
        private String parentID;
        private String activityType;
        private String startTime;
        private String endTime;
        private String notes;

    }
