package com.mobileapp.f_m_a_petcare.HealthManage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileapp.f_m_a_petcare.R;

import java.util.List;


public class SymptomAdapter extends RecyclerView.Adapter<SymptomAdapter.SymptomViewHolder> {

    private List<Symptom> symptoms;
    private OnSymptomClickListener listener;

    public SymptomAdapter(List<Symptom> symptoms, OnSymptomClickListener listener) {
        this.symptoms = symptoms;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SymptomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_symptom, parent, false);
        return new SymptomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomViewHolder holder, int position) {
        Symptom symptom = symptoms.get(position);
        holder.bind(symptom);
    }

    @Override
    public int getItemCount() {
        return symptoms.size();
    }

    // Cập nhật danh sách triệu chứng
    public void updateSymptomsList(List<Symptom> newSymptoms) {
        this.symptoms = newSymptoms;
        notifyDataSetChanged();
    }

    public interface OnSymptomClickListener {
        void onSymptomClick(Symptom symptom);
    }

    class SymptomViewHolder extends RecyclerView.ViewHolder {
        ImageView symptomImage;
        TextView symptomTitle;
        TextView symptomDescription;

        SymptomViewHolder(@NonNull View itemView) {
            super(itemView);
            symptomImage = itemView.findViewById(R.id.symptomImage);
            symptomTitle = itemView.findViewById(R.id.symptomTitle);
            symptomDescription = itemView.findViewById(R.id.symptomDescription);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onSymptomClick(symptoms.get(position));
                }
            });
        }

        void bind(Symptom symptom) {
            symptomImage.setImageResource(symptom.getImageResId());
            symptomTitle.setText(symptom.getTitle());
            symptomDescription.setText(symptom.getShortDescription());
        }
    }
}
