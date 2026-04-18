package uk.edu.le.safecheck.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "safety_checks")
public class SafetyCheck {

    @PrimaryKey(autoGenerate = true)
    public int checkId;
    public String date;
    public String vehicleRegistration;
    public String driverName;
    public String overallStatus; // "Pass" or "Fail"
}