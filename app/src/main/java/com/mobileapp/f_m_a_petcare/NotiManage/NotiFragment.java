package com.mobileapp.f_m_a_petcare.NotiManage;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mobileapp.f_m_a_petcare.DB.DatabaseHelper;
import com.mobileapp.f_m_a_petcare.DataUpdateListener;
import com.mobileapp.f_m_a_petcare.MainActivity;
import com.mobileapp.f_m_a_petcare.PetManage.Pet;
import com.mobileapp.f_m_a_petcare.R;

import java.util.Calendar;
import java.util.List;

public class NotiFragment extends Fragment implements DataUpdateListener {
    private TextView dateTextView, timeTextView;
    private Spinner petSpinner, reminderTypeSpinner;
    private Button saveButton, datePickerButton, timePickerButton;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_noti, container, false);

        dbHelper = new DatabaseHelper(getContext());

        dateTextView = view.findViewById(R.id.dateTextView);
        timeTextView = view.findViewById(R.id.timeTextView);
        petSpinner = view.findViewById(R.id.petSpinner);
        reminderTypeSpinner = view.findViewById(R.id.reminderTypeSpinner);
        saveButton = view.findViewById(R.id.saveButton);
        datePickerButton = view.findViewById(R.id.datePickerButton);
        timePickerButton = view.findViewById(R.id.timePickerButton);

        setupSpinners();
        setupListeners();

        return view;
    }

    private void setupSpinners() {
        // Thiết lập Spinner cho danh sách pet
        List<Pet> pets = dbHelper.getAllPets();
        ArrayAdapter<Pet> petAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, pets);
        petAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        petSpinner.setAdapter(petAdapter);

        // Thiết lập Spinner cho loại nhắc nhở
        String[] reminderTypes = {"Cho ăn", "Tắm", "Khám bệnh", "Tiêm phòng"};
        ArrayAdapter<String> reminderTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, reminderTypes);
        reminderTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reminderTypeSpinner.setAdapter(reminderTypeAdapter);
    }

    private void setupListeners() {
        datePickerButton.setOnClickListener(v -> showDatePicker());
        timePickerButton.setOnClickListener(v -> showTimePicker());
        saveButton.setOnClickListener(v -> saveReminder());
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

    private void saveReminder() {
        Pet selectedPet = (Pet) petSpinner.getSelectedItem();
        String date = dateTextView.getText().toString();
        String time = timeTextView.getText().toString();
        String reminderType = reminderTypeSpinner.getSelectedItem().toString();

        if (selectedPet != null && !date.isEmpty() && !time.isEmpty()) {
            long id = dbHelper.addReminder(selectedPet.getId(), date, time, reminderType);
            if (id != -1) {
                // Đặt AlarmManager với reminderId
                setAlarm(id, selectedPet.getId(), date, time, reminderType);
                Toast.makeText(getContext(), "Đã lưu lịch nhắc nhở", Toast.LENGTH_SHORT).show();

                // Notify MainActivity about the data update
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).onDataUpdated();
                }
            } else {
                Toast.makeText(getContext(), "Lỗi khi lưu lịch nhắc nhở", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAlarm(long reminderId, String petId, String date, String time, String reminderType) {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), ReminderReceiver.class);
        intent.putExtra("reminderId", reminderId);
        intent.putExtra("petId", petId);
        intent.putExtra("reminderType", reminderType);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), (int) reminderId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Chuyển đổi ngày giờ thành milliseconds
        Calendar calendar = Calendar.getInstance();
        String[] dateParts = date.split("/");
        String[] timeParts = time.split(":");
        calendar.set(Calendar.YEAR, Integer.parseInt(dateParts[2]));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateParts[1]) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateParts[0]));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeParts[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeParts[1]));
        calendar.set(Calendar.SECOND, 0);

        long alarmTime = calendar.getTimeInMillis();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof MainActivity) {
            ((MainActivity) context).addDataUpdateListener(this);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).removeDataUpdateListener(this);
        }
    }

    @Override
    public void onDataUpdated() {
        // Reload data
        setupSpinners();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void reloadData(){
        Toast.makeText(getActivity(), "Reload", Toast.LENGTH_SHORT).show();
    }
}