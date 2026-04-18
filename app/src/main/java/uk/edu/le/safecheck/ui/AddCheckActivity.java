package uk.edu.le.safecheck.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import uk.edu.le.safecheck.R;
import uk.edu.le.safecheck.model.Defect;
import uk.edu.le.safecheck.model.SafetyCheck;
import uk.edu.le.safecheck.viewmodel.SafetyViewModel;

import java.time.LocalDate;

public class AddCheckActivity extends AppCompatActivity {

    private SafetyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_check);

        viewModel = new ViewModelProvider(this).get(SafetyViewModel.class);

        EditText etVehicleReg = findViewById(R.id.etVehicleReg);
        EditText etDriverName = findViewById(R.id.etDriverName);
        EditText etDefectDesc = findViewById(R.id.etDefectDescription);
        RadioGroup rgStatus = findViewById(R.id.rgStatus);
        RadioGroup rgSeverity = findViewById(R.id.rgSeverity);
        Button btnSave = findViewById(R.id.btnSave);

        // Bind draftDescription to ViewModel — survives rotation (2.2 requirement)
        etDefectDesc.setText(viewModel.draftDescription.getValue());
        etDefectDesc.addTextChangedListener(new android.text.TextWatcher() {
            public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            public void onTextChanged(CharSequence s, int st, int b, int c) {
                viewModel.draftDescription.setValue(s.toString());
            }
            public void afterTextChanged(android.text.Editable s) {}
        });

        btnSave.setOnClickListener(v -> {
            String reg = etVehicleReg.getText().toString().trim();

            // Input validation — requirement 2.3
            if (reg.isEmpty()) {
                Toast.makeText(this,
                        "Please enter vehicle details", Toast.LENGTH_SHORT).show();
                return;
            }

            String driver = etDriverName.getText().toString().trim();
            int selectedStatus = rgStatus.getCheckedRadioButtonId();
            String status = selectedStatus == R.id.rbPass ? "Pass" : "Fail";

            SafetyCheck check = new SafetyCheck();
            check.vehicleRegistration = reg;
            check.driverName = driver;
            check.overallStatus = status;
            check.date = LocalDate.now().toString();

            // Insert check then defect if description provided
            String defectDesc = etDefectDesc.getText().toString().trim();

            if (!defectDesc.isEmpty()) {
                int selectedSeverity = rgSeverity.getCheckedRadioButtonId();
                String severity = selectedSeverity == R.id.rbHigh ? "High" : "Low";

                // Use repository directly for chained insert with returned ID
                viewModel.insertCheckAndDefect(check, defectDesc, severity);
            } else {
                viewModel.insertCheck(check);
            }

            viewModel.draftDescription.setValue("");
            finish(); // go back to list
        });
    }
}