package uk.edu.le.safecheck.ui;


import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import uk.edu.le.safecheck.R;
import uk.edu.le.safecheck.model.SafetyCheck;
import uk.edu.le.safecheck.ui.adapter.SafetyCheckAdapter;
import uk.edu.le.safecheck.viewmodel.SafetyViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private SafetyViewModel viewModel;
    private SafetyCheckAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        FloatingActionButton fab = findViewById(R.id.fabAddCheck);

        adapter = new SafetyCheckAdapter(new SafetyCheckAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SafetyCheck check) {
                // Explicit Intent — opens DetailActivity with the checkId
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("CHECK_ID", check.checkId);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(SafetyCheck check) {
                // Long press to delete — cascading delete requirement 2.3
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete Check")
                        .setMessage("Delete this check and all its defects?")
                        .setPositiveButton("Delete", (d, w) -> viewModel.deleteCheck(check))
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(SafetyViewModel.class);

        // Observe LiveData — updates RecyclerView automatically
        viewModel.getAllChecks().observe(this, checks -> adapter.setChecks(checks));

        fab.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddCheckActivity.class);
            startActivity(intent);
        });
    }
}