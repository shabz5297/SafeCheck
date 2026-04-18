package uk.edu.le.safecheck.ui.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import uk.edu.le.safecheck.R;
import uk.edu.le.safecheck.model.Defect;

import java.util.ArrayList;
import java.util.List;

public class DefectAdapter extends RecyclerView.Adapter<DefectAdapter.ViewHolder> {

    private List<Defect> defects = new ArrayList<>();

    public void setDefects(List<Defect> defects) {
        this.defects = defects;
        notifyDataSetChanged();
    }

    public List<Defect> getDefects() { return defects; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_defect, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Defect defect = defects.get(position);
        holder.tvDesc.setText(defect.description);
        holder.tvSeverity.setText("Severity: " + defect.severity);
    }

    @Override
    public int getItemCount() { return defects.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvDesc, tvSeverity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDesc = itemView.findViewById(R.id.tvDefectDesc);
            tvSeverity = itemView.findViewById(R.id.tvDefectSeverity);
        }
    }
}