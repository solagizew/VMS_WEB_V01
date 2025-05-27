package vms_web_v01;

import java.io.Serializable;

public class VehicleInspectionExpiryInfo implements Serializable {

    private String vehicleLicenseNo;
    private int inspectionDaysRemaining;

    public VehicleInspectionExpiryInfo() {
    }

    public VehicleInspectionExpiryInfo(String vehicleLicenseNo, int inspectionDaysRemaining) {
        this.vehicleLicenseNo = vehicleLicenseNo;
        this.inspectionDaysRemaining = inspectionDaysRemaining;
    }

    public String getVehicleLicenseNo() {
        return vehicleLicenseNo;
    }

    public void setVehicleLicenseNo(String vehicleLicenseNo) {
        this.vehicleLicenseNo = vehicleLicenseNo;
    }

    public int getInspectionDaysRemaining() {
        return inspectionDaysRemaining;
    }

    public void setInspectionDaysRemaining(int inspectionDaysRemaining) {
        this.inspectionDaysRemaining = inspectionDaysRemaining;
    }

    @Override
    public String toString() {
        return "VehicleInspectionExpiryInfo{" +
                "vehicleLicenseNo='" + vehicleLicenseNo + '\'' +
                ", inspectionDaysRemaining=" + inspectionDaysRemaining +
                '}';
    }
}
