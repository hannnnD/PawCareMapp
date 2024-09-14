package com.mobileapp.f_m_a_petcare.PetManage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.mobileapp.f_m_a_petcare.R;

import java.util.ArrayList;

public class FullScreenImageDialogFragment extends DialogFragment {
    private ArrayList<String> imagePaths;
    private int currentPosition;

    public static FullScreenImageDialogFragment newInstance(ArrayList<String> imagePaths, int position) {
        FullScreenImageDialogFragment fragment = new FullScreenImageDialogFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("imagePaths", imagePaths);
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imagePaths = getArguments().getStringArrayList("imagePaths");
            currentPosition = getArguments().getInt("position");
        }
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_screen_image_dialog, container, false);

        ViewPager2 viewPager = view.findViewById(R.id.viewPagerFullScreenImage);
        FullScreenImageAdapter adapter = new FullScreenImageAdapter(getContext(), imagePaths);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(currentPosition, false);

        return view;
    }



}