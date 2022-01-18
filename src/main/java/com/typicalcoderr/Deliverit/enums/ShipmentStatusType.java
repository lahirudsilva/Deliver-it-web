package com.typicalcoderr.Deliverit.enums;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Thu
 * Time: 12:22 AM
 */
public enum ShipmentStatusType {

    PENDING("pending"),ACCEPTED("accepted"),REJECTED("rejected"),CANCELED("canceled");

    private final String type;

    ShipmentStatusType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
