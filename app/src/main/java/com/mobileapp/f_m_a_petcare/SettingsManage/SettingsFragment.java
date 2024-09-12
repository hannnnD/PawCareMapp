package com.mobileapp.f_m_a_petcare.SettingsManage;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.Toast;
import com.mobileapp.f_m_a_petcare.R;

public class SettingsFragment extends Fragment {

    private Switch switchStorage, switchNotification, switchCalendar;
    private static final int PERMISSION_REQUEST_CODE = 123;
    private SharedPreferences sharedPreferences;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);

        switchStorage = view.findViewById(R.id.switchStorage);
        switchNotification = view.findViewById(R.id.switchNotification);
        switchCalendar = view.findViewById(R.id.switchCalendar);

        setupSwitches();
        updateSwitchStates();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateSwitchStates();
    }

    private void setupSwitches() {
        setupSwitch(switchStorage, Manifest.permission.READ_EXTERNAL_STORAGE, "storage_permission");
        setupSwitch(switchNotification, Manifest.permission.POST_NOTIFICATIONS, "notification_permission");
        setupSwitch(switchCalendar, Manifest.permission.READ_CALENDAR, "calendar_permission");
    }

    private void setupSwitch(Switch switchView, String permission, String preferenceKey) {
        switchView.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked && !checkPermission(permission)) {
                requestPermission(permission);
            } else {
                updatePermissionState(preferenceKey, isChecked);
            }
        });
    }

    private boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(getContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission(String permission) {
        if (ContextCompat.checkSelfPermission(getContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                showPermissionRationaleDialog(permission);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, PERMISSION_REQUEST_CODE);
            }
        } else {
            updatePermissionState(getPreferenceKeyForPermission(permission), true);
        }
    }

    private void showPermissionRationaleDialog(String permission) {
        new AlertDialog.Builder(getContext())
                .setTitle("Permission needed")
                .setMessage("This permission is needed because of...")
                .setPositiveButton("OK", (dialog, which) -> {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, PERMISSION_REQUEST_CODE);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                    updatePermissionState(getPreferenceKeyForPermission(permission), false);
                })
                .create().show();
    }

    private void updateSwitchStates() {
        updateSwitchState(switchStorage, Manifest.permission.READ_EXTERNAL_STORAGE, "storage_permission");
        updateSwitchState(switchNotification, Manifest.permission.POST_NOTIFICATIONS, "notification_permission");
        updateSwitchState(switchCalendar, Manifest.permission.READ_CALENDAR, "calendar_permission");
    }

    private void updateSwitchState(Switch switchView, String permission, String preferenceKey) {
        boolean isGranted = checkPermission(permission);
        switchView.setChecked(isGranted);
        updatePermissionState(preferenceKey, isGranted);
    }

    private void updatePermissionState(String key, boolean isGranted) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, isGranted);
        editor.apply();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                boolean isGranted = grantResults[i] == PackageManager.PERMISSION_GRANTED;
                updatePermissionState(getPreferenceKeyForPermission(permissions[i]), isGranted);
                updateSwitchState(getSwitchForPermission(permissions[i]), permissions[i], getPreferenceKeyForPermission(permissions[i]));
            }
        }
    }

    private String getPreferenceKeyForPermission(String permission) {
        switch (permission) {
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                return "storage_permission";
            case Manifest.permission.POST_NOTIFICATIONS:
                return "notification_permission";
            case Manifest.permission.READ_CALENDAR:
                return "calendar_permission";
            default:
                return "";
        }
    }

    private Switch getSwitchForPermission(String permission) {
        switch (permission) {
            case Manifest.permission.READ_EXTERNAL_STORAGE:
                return switchStorage;
            case Manifest.permission.POST_NOTIFICATIONS:
                return switchNotification;
            case Manifest.permission.READ_CALENDAR:
                return switchCalendar;
            default:
                return null;
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public void reloadData(){
        Toast.makeText(getActivity(), "Reload", Toast.LENGTH_SHORT).show();
    }

}