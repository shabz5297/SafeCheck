package uk.edu.le.safecheck.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import uk.edu.le.safecheck.model.Defect;
import java.util.List;

@Dao
public interface DefectDao {

    @Insert
    void insert(Defect defect);

    @Query("SELECT * FROM defects WHERE checkId = :checkId")
    LiveData<List<Defect>> getDefectsForCheck(int checkId);

    @Query("SELECT COUNT(*) FROM defects WHERE checkId = :checkId")
    int getDefectCount(int checkId);
}