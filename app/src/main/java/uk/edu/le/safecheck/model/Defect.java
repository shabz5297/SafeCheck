package uk.edu.le.safecheck.model;


import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "defects",
        foreignKeys = @ForeignKey(
                entity = SafetyCheck.class,
                parentColumns = "checkId",
                childColumns = "checkId",
                onDelete = ForeignKey.CASCADE  // cascading delete — 2.3 requirement
        )
)
public class Defect {

    @PrimaryKey(autoGenerate = true)
    public int defectId;
    public int checkId;              // FK to SafetyCheck
    public String description;       // e.g. "Cracked Mirror"
    public String severity;          // "Low" or "High"
}