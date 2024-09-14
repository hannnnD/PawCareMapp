package com.mobileapp.f_m_a_petcare.PetManage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import androidx.fragment.app.DialogFragment;

import com.mobileapp.f_m_a_petcare.R;

import java.util.ArrayList;

public class ViewImgPetDialogFragment extends DialogFragment {
    private Pet pet;
    private ArrayList<String> imagePaths;

    public static ViewImgPetDialogFragment newInstance(Pet pet) {
        ViewImgPetDialogFragment fragment = new ViewImgPetDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("pet", pet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pet = (Pet) getArguments().getSerializable("pet");
            imagePaths = pet.getImagePaths();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_img_pet_dialog, container, false);

        TextView textViewPetName = view.findViewById(R.id.textViewPetName);
        textViewPetName.setText(pet.getTenThu());

        GridView gridView = view.findViewById(R.id.gridViewPetImages);
        PetImageAdapter adapter = new PetImageAdapter(getContext(), pet.getImagePaths());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener((parent, v, position, id) -> {
            showFullScreenImage(position);
        });

        return view;
    }

    private void showFullScreenImage(int position) {
        // Implement this method to show full-screen image
        // You can use another DialogFragment or start a new Activity
        FullScreenImageDialogFragment fullScreenDialog = FullScreenImageDialogFragment.newInstance(imagePaths, position);
        fullScreenDialog.show(getChildFragmentManager(), "FullScreenImage");
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}