package com.typicalcoderr.Deliverit.enums;

/**
 * Created by IntelliJ IDEA.
 * User: Lahiru
 * Date: Thu
 * Time: 12:03 AM
 */
public enum DriverStatusType {
    AVAILABLE("available"), ASSIGN_SLOTS_FULL("assign-slots full"), UNAVAILABLE("unavailable");

    private final String type;

    DriverStatusType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
