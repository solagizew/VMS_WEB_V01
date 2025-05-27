package vms_web_v01;

import java.io.Serializable;

public class VehicleInsuranceExpiryInfo implements Serializable {

    private String vehicleLicenseNo;
    private String insuranceType;
    private int insuranceDaysRemaining;

    public VehicleInsuranceExpiryInfo() {
    }

    public VehicleInsuranceExpiryInfo(String vehicleLicenseNo, String insuranceType, int insuranceDaysRemaining) {
        this.vehicleLicenseNo = vehicleLicenseNo;
        this.insuranceType = insuranceType;
        this.insuranceDaysRemaining = insuranceDaysRemaining;
    }

    public String getVehicleLicenseNo() {
        return vehicleLicenseNo;
    }

    public void setVehicleLicenseNo(String vehicleLicenseNo) {
        this.vehicleLicenseNo = vehicleLicenseNo;
    }

    public String getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(String insuranceType) {
        this.insuranceType = insuranceType;
    }

    public int getInsuranceDaysRemaining() {
        return insuranceDaysRemaining;
    }

    public void setInsuranceDaysRemaining(int insuranceDaysRemaining) {
        this.insuranceDaysRemaining = insuranceDaysRemaining;
    }

    @Override
    public String toString() {
        return "VehicleInsuranceExpiryInfo{" +
                "vehicleLicenseNo='" + vehicleLicenseNo + '\'' +
                ", insuranceType='" + insuranceType + '\'' +
                ", insuranceDaysRemaining=" + insuranceDaysRemaining +
                '}';
    }
}
