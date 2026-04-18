package uk.edu.le.safecheck.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import uk.edu.le.safecheck.dao.DefectDao;
import uk.edu.le.safecheck.dao.SafetyCheckDao;
import uk.edu.le.safecheck.model.Defect;
import uk.edu.le.safecheck.model.SafetyCheck;

@Database(entities = {SafetyCheck.class, Defect.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract SafetyCheckDao safetyCheckDao();
    public abstract DefectDao defectDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "safecheck_database"
                    ).build();
                }
            }
        }
        return INSTANCE;
    }
}