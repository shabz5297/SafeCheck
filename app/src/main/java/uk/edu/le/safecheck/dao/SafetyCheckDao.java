package uk.edu.le.safecheck.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import uk.edu.le.safecheck.model.SafetyCheck;
import java.util.List;

@Dao
public interface SafetyCheckDao {

    @Insert
    long insert(SafetyCheck safetyCheck);

    @Query("SELECT * FROM safety_checks ORDER BY checkId DESC")
    LiveData<List<SafetyCheck>> getAllChecks();

    @Query("SELECT * FROM safety_checks WHERE checkId = :id")
    LiveData<SafetyCheck> getCheckById(int id);

    @Delete
    void delete(SafetyCheck safetyCheck);
}