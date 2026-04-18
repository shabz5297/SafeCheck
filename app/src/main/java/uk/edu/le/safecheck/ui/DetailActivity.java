package uk.edu.le.safecheck.ui;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import uk.edu.le.safecheck.R;
import uk.edu.le.safecheck.model.Defect;
import uk.edu.le.safecheck.ui.adapter.DefectAdapter;
import uk.edu.le.safecheck.viewmodel.SafetyViewModel;




import java.util.List;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        int checkId = getIntent().getIntExtra("CHECK_ID", -1);

        TextView tvDate = findViewById(R.id.tvDetailDate);
        TextView tvVehicle = findViewById(R.id.tvDetailVehicle);
        TextView tvDriver = findViewById(R.id.tvDetailDriver);
        TextView tvStatus = findViewById(R.id.tvDetailStatus);
        RecyclerView rvDefects = findViewById(R.id.rvDefects);
        Button btnEmail = findViewById(R.id.btnEmailReport);

        DefectAdapter defectAdapter = new DefectAdapter();
        rvDefects.setAdapter(defectAdapter);
        rvDefects.setLayoutManager(new LinearLayoutManager(this));

        SafetyViewModel viewModel = new ViewModelProvider(this).get(SafetyViewModel.class);

        viewModel.getCheckById(checkId).observe(this, check -> {
            if (check != null) {
                tvDate.setText("Date: " + check.date);
                tvVehicle.setText("Vehicle: " + check.vehicleRegistration);
                tvDriver.setText("Driver: " + check.driverName);
                tvStatus.setText("Status: " + check.overallStatus);

                btnEmail.setOnClickListener(v -> {
                    StringBuilder body = new StringBuilder();
                    List<Defect> defects = defectAdapter.getDefects();
                    for (Defect d : defects) {
                        body.append("- ")
                                .append(d.description)
                                .append(" (")
                                .append(d.severity)
                                .append(")\n");
                    }

                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("message/rfc822");
                    emailIntent.putExtra(
                            Intent.EXTRA_SUBJECT,
                            "Safety Defect Report: " + check.vehicleRegistration
                    );
                    emailIntent.putExtra(Intent.EXTRA_TEXT, body.toString());
                    startActivity(Intent.createChooser(emailIntent, "Send via"));
                });
            }
        });

        viewModel.getDefectsForCheck(checkId).observe(this, defectAdapter::setDefects);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}