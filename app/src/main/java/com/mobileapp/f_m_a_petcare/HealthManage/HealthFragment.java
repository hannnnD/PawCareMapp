package com.mobileapp.f_m_a_petcare.HealthManage;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileapp.f_m_a_petcare.R;

import java.util.ArrayList;
import java.util.List;

public class HealthFragment extends Fragment implements SymptomAdapter.OnSymptomClickListener {

    private RecyclerView symptomsRecyclerView;
    private EditText searchEditText;
    private List<Symptom> symptoms;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_health, container, false);

        symptomsRecyclerView = view.findViewById(R.id.symptomsRecyclerView);
        searchEditText = view.findViewById(R.id.searchEditText);

        symptoms = createSymptomsList();
        SymptomAdapter adapter = new SymptomAdapter(symptoms, this);
        symptomsRecyclerView.setAdapter(adapter);

        return view;
    }

    private List<Symptom> createSymptomsList() {
        List<Symptom> symptoms = new ArrayList<>();
        symptoms.add(new Symptom(R.drawable.ic_health, "Ho, khó thở, thở gấp, khò khè",
                "Xem chi tiết",
                "Nguyên nhân: Nhiễm trùng đường hô hấp, dị ứng, bệnh phổi, hoặc viêm phế quản. Hen suyễn, tắc nghẽn đường hô hấp, bệnh tim hoặc phổi.",
                "Chuẩn đoán: Triệu chứng về hô hấp",
                "Hướng giải quyết: Đưa thú cưng đến bác sĩ thú y để kiểm tra. Bác sĩ có thể kê đơn thuốc kháng sinh, thuốc giảm ho, hoặc điều trị theo nguyên nhân cụ thể."));
        // Thêm các triệu chứng khác tương tự
        return symptoms;
    }

    @Override
    public void onSymptomClick(Symptom symptom) {
        SymptomDetailDialogFragment detailFragment = SymptomDetailDialogFragment.newInstance(symptom);
        detailFragment.show(getChildFragmentManager(), "symptom_detail");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void reloadData(){
        Toast.makeText(getActivity(), "Reload", Toast.LENGTH_SHORT).show();
    }

}