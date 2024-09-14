package com.mobileapp.f_m_a_petcare.NotiManage;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.mobileapp.f_m_a_petcare.DB.DatabaseHelper;
import com.mobileapp.f_m_a_petcare.MainActivity;
import com.mobileapp.f_m_a_petcare.PetManage.Pet;
import com.mobileapp.f_m_a_petcare.R;

import java.util.Calendar;
import java.util.List;

public class EditNotiDialogFragment extends DialogFragment {
    private TextView dateTextView, timeTextView;
    private Spinner petSpinner, reminderTypeSpinner;
    private Button saveButton, datePickerButton, timePickerButton;
    private DatabaseHelper dbHelper;
    private long reminderId;
    private OnReminderUpdatedListener reminderUpdatedListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_noti, container, false);
        dbHelper = new DatabaseHelper(getContext());

        dateTextView = view.findViewById(R.id.dateTextView);
        timeTextView = view.findViewById(R.id.timeTextView);
        petSpinner = view.findViewById(R.id.petSpinner);
        reminderTypeSpinner = view.findViewById(R.id.reminderTypeSpinner);
        saveButton = view.findViewById(R.id.saveButton);
        datePickerButton = view.findViewById(R.id.datePickerButton);
        timePickerButton = view.findViewById(R.id.timePickerButton);

        reminderId = getArguments().getLong("reminderId");
        loadReminderData(reminderId);
        setupSpinners();

        // Set up DatePicker and TimePicker
        datePickerButton.setOnClickListener(v -> showDatePicker());
        timePickerButton.setOnClickListener(v -> showTimePicker());

        saveButton.setOnClickListener(v -> updateReminder());

        return view;
    }

    private void showDatePicker() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                    dateTextView.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showTimePicker() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                (view, hourOfDay, minute1) -> {
                    String time = String.format("%02d:%02d", hourOfDay, minute1);
                    timeTextView.setText(time);
                }, hour, minute, true);
        timePickerDialog.show();
    }

    private void loadReminderData(long reminderId) {
        Reminder reminder = dbHelper.getReminder(reminderId);
        if (reminder != null) {
            dateTextView.setText(reminder.getDate());
            timeTextView.setText(reminder.getTime());
            // Set selected pet and reminder type
        }
    }

    private void updateReminder() {
        String date = dateTextView.getText().toString();
        String time = timeTextView.getText().toString();
        String reminderType = reminderTypeSpinner.getSelectedItem().toString();
        Pet selectedPet = (Pet) petSpinner.getSelectedItem();

        if (selectedPet != null && !date.isEmpty() && !time.isEmpty()) {
            dbHelper.updateReminder(reminderId, selectedPet.getId(), date, time, reminderType);
            Toast.makeText(getContext(), "Cập nhật nhắc nhở thành công", Toast.LENGTH_SHORT).show();
            dismiss();
            if (reminderUpdatedListener != null) {
                reminderUpdatedListener.onReminderUpdated();
            }
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).onDataUpdated();
            }
        } else {
            Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupSpinners() {
        List<Pet> pets = dbHelper.getAllPets();
        ArrayAdapter<Pet> petAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, pets);
        petAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        petSpinner.setAdapter(petAdapter);

        String[] reminderTypes = {"Cho ăn", "Tắm", "Khám bệnh", "Tiêm phòng"};
        ArrayAdapter<String> reminderTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, reminderTypes);
        reminderTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reminderTypeSpinner.setAdapter(reminderTypeAdapter);
    }

    public interface OnReminderUpdatedListener {
        void onReminderUpdated();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
