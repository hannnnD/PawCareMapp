package com.mobileapp.f_m_a_petcare.HealthManage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mobileapp.f_m_a_petcare.R;

public class SymptomDetailDialogFragment extends BottomSheetDialogFragment {

    private static final String ARG_SYMPTOM = "symptom";

    private Symptom symptom;

    public static SymptomDetailDialogFragment newInstance(Symptom symptom) {
        SymptomDetailDialogFragment fragment = new SymptomDetailDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_SYMPTOM, symptom);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            symptom = (Symptom) getArguments().getSerializable(ARG_SYMPTOM);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_symptom_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView detailImage = view.findViewById(R.id.detailImage);
        TextView detailTitle = view.findViewById(R.id.detailTitle);
        TextView detailCause = view.findViewById(R.id.detailCause);
        TextView detailDiagnosis = view.findViewById(R.id.detailDiagnosis);
        TextView detailSolution = view.findViewById(R.id.detailSolution);

        if (symptom != null) {
            detailImage.setImageResource(symptom.getImageResId());
            detailTitle.setText(symptom.getTitle());
            detailCause.setText(symptom.getCause());
            detailDiagnosis.setText(symptom.getDiagnosis());
            detailSolution.setText(symptom.getSolution());
        }
    }
}