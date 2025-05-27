package vms_web_v01;

import java.io.Serializable;

public class VehicleInspectionInfo implements Serializable {

    private int inspectionId;
    private String vehicleLicenseNo;
    private String inspectionCenter;
    private String inspectionCertificateNo;
    private String inspectionDate;         // format: dd-MM-yyyy
    private String inspectionExpiryDate;   // format: dd-MM-yyyy
    private String inspectionResult;
    private String status;                  // Pending, Passed, Failed, etc.
    private String updatedAt;               // format: dd-MM-yyyy HH:mm:ss
    private String updatedBy;

    public VehicleInspectionInfo() {}

    public int getInspectionId() {
        return inspectionId;
    }

    public void setInspectionId(int inspectionId) {
        this.inspectionId = inspectionId;
    }

    public String getVehicleLicenseNo() {
        return vehicleLicenseNo;
    }

    public void setVehicleLicenseNo(String vehicleLicenseNo) {
        this.vehicleLicenseNo = vehicleLicenseNo;
    }

    public String getInspectionCenter() {
        return inspectionCenter;
    }

    public void setInspectionCenter(String inspectionCenter) {
        this.inspectionCenter = inspectionCenter;
    }

    public String getInspectionCertificateNo() {
        return inspectionCertificateNo;
    }

    public void setInspectionCertificateNo(String inspectionCertificateNo) {
        this.inspectionCertificateNo = inspectionCertificateNo;
    }

    public String getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(String inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    public String getInspectionExpiryDate() {
        return inspectionExpiryDate;
    }

    public void setInspectionExpiryDate(String inspectionExpiryDate) {
        this.inspectionExpiryDate = inspectionExpiryDate;
    }

    public String getInspectionResult() {
        return inspectionResult;
    }

    public void setInspectionResult(String inspectionResult) {
        this.inspectionResult = inspectionResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
