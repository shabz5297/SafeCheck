package uk.edu.le.safecheck.ui.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import uk.edu.le.safecheck.R;
import uk.edu.le.safecheck.model.SafetyCheck;

import java.util.ArrayList;
import java.util.List;

public class SafetyCheckAdapter extends RecyclerView.Adapter<SafetyCheckAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(SafetyCheck check);
        void onItemLongClick(SafetyCheck check);
    }

    private List<SafetyCheck> checks = new ArrayList<>();
    private final OnItemClickListener listener;

    public SafetyCheckAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setChecks(List<SafetyCheck> checks) {
        this.checks = checks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_safety_check, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SafetyCheck check = checks.get(position);
        holder.tvDate.setText(check.date);
        holder.tvVehicleReg.setText(check.vehicleRegistration);
        holder.tvDefectCount.setText("Status: " + check.overallStatus);

        holder.itemView.setOnClickListener(v -> listener.onItemClick(check));
        holder.itemView.setOnLongClickListener(v -> {
            listener.onItemLongClick(check);
            return true;
        });
    }

    @Override
    public int getItemCount() { return checks.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate, tvVehicleReg, tvDefectCount;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvVehicleReg = itemView.findViewById(R.id.tvVehicleReg);
            tvDefectCount = itemView.findViewById(R.id.tvDefectCount);
        }
    }
}