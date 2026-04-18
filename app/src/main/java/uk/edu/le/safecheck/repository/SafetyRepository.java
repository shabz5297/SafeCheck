package uk.edu.le.safecheck.repository;


import android.content.Context;
import androidx.lifecycle.LiveData;

import uk.edu.le.safecheck.dao.DefectDao;
import uk.edu.le.safecheck.dao.SafetyCheckDao;
import uk.edu.le.safecheck.database.AppDatabase;
import uk.edu.le.safecheck.model.Defect;
import uk.edu.le.safecheck.model.SafetyCheck;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SafetyRepository {

    private final SafetyCheckDao safetyCheckDao;
    private final DefectDao defectDao;
    // ExecutorService for background thread writes — required by assignment
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public SafetyRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        safetyCheckDao = db.safetyCheckDao();
        defectDao = db.defectDao();
    }

    // All writes happen on background thread via ExecutorService
    public void insertCheck(SafetyCheck check) {
        executor.execute(() -> safetyCheckDao.insert(check));
    }

    public void insertDefect(Defect defect) {
        executor.execute(() -> defectDao.insert(defect));
    }

    public void deleteCheck(SafetyCheck check) {
        executor.execute(() -> safetyCheckDao.delete(check));
    }

    public void insertCheckAndDefect(SafetyCheck check, String defectDesc, String severity) {
        executor.execute(() -> {
            long checkId = safetyCheckDao.insert(check);
            Defect defect = new Defect();
            defect.checkId = (int) checkId;
            defect.description = defectDesc;
            defect.severity = severity;
            defectDao.insert(defect);
        });
    }

    // Reads return LiveData — Room handles threading automatically
    public LiveData<List<SafetyCheck>> getAllChecks() {
        return safetyCheckDao.getAllChecks();
    }

    public LiveData<SafetyCheck> getCheckById(int id) {
        return safetyCheckDao.getCheckById(id);
    }

    public LiveData<List<Defect>> getDefectsForCheck(int checkId) {
        return defectDao.getDefectsForCheck(checkId);
    }
}