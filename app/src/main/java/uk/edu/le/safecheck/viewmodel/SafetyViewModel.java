package uk.edu.le.safecheck.viewmodel;


import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import uk.edu.le.safecheck.model.Defect;
import uk.edu.le.safecheck.model.SafetyCheck;
import uk.edu.le.safecheck.repository.SafetyRepository;

import java.util.List;

public class SafetyViewModel extends AndroidViewModel {

    private final SafetyRepository repository;
    private final LiveData<List<SafetyCheck>> allChecks;

    // Survives rotation — this is what passes the rotation test

    public MutableLiveData<String> draftDescription = new MutableLiveData<>("");

    public SafetyViewModel(@NonNull Application application) {
        super(application);
        repository = new SafetyRepository(application);
        allChecks = repository.getAllChecks();
    }

    public LiveData<List<SafetyCheck>> getAllChecks() { return allChecks; }

    public LiveData<SafetyCheck> getCheckById(int id) {
        return repository.getCheckById(id);
    }

    public LiveData<List<Defect>> getDefectsForCheck(int checkId) {
        return repository.getDefectsForCheck(checkId);
    }

    public void insertCheck(SafetyCheck check) { repository.insertCheck(check); }

    public void insertDefect(Defect defect) { repository.insertDefect(defect); }

    public void deleteCheck(SafetyCheck check) { repository.deleteCheck(check); }

    public void insertCheckAndDefect(SafetyCheck check, String defectDesc, String severity) {
        repository.insertCheckAndDefect(check, defectDesc, severity);
    }
}