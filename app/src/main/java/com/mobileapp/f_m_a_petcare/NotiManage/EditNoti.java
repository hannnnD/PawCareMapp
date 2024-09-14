//package com.mobileapp.f_m_a_petcare.NotiManage;
//
//import android.app.DatePickerDialog;
//import android.app.TimePickerDialog;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.Spinner;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.DialogFragment;
//
//import com.mobileapp.f_m_a_petcare.DB.DatabaseHelper;
//import com.mobileapp.f_m_a_petcare.MainActivity;
//import com.mobileapp.f_m_a_petcare.PetManage.Pet;
//import com.mobileapp.f_m_a_petcare.R;
//
//import java.io.Serializable;
//import java.util.Calendar;
//import java.util.List;
//
//public class EditNoti extends DialogFragment {
//
//    private TextView dateTextView, timeTextView;
//    private Spinner petSpinner, reminderTypeSpinner;
//    private Button saveButton, datePickerButton, timePickerButton;
//    private DatabaseHelper dbHelper;
//    private Reminder reminderToEdit;
//
//    public static EditNoti newInstance(Reminder reminder) {
//        EditNoti fragment = new EditNoti();
//        Bundle args = new Bundle();
//        args.putParcelable("reminder", reminder);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            reminderToEdit = getArguments().getParcelable("reminder");
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_edit_noti, container, false);
//
//        dbHelper = new DatabaseHelper(getContext());
//
//        dateTextView = view.findViewById(R.id.dateTextView);
//        timeTextView = view.findViewById(R.id.timeTextView);
//        petSpinner = view.findViewById(R.id.petSpinner);
//        reminderTypeSpinner = view.findViewById(R.id.reminderTypeSpinner);
//        saveButton = view.findViewById(R.id.saveButton);
//        datePickerButton = view.findViewById(R.id.datePickerButton);
//        timePickerButton = view.findViewById(R.id.timePickerButton);
//
//        setupSpinners();
//        setupListeners();
//        populateFields();
//
//        return view;
//    }
//
//    private void setupSpinners() {
//        // Setup pet spinner
//        List<Pet> pets = dbHelper.getAllPets();
//        ArrayAdapter<Pet> petAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, pets);
//        petAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        petSpinner.setAdapter(petAdapter);
//
//        // Setup reminder type spinner
//        String[] reminderTypes = {"Cho ăn", "Tắm", "Khám bệnh", "Tiêm phòng"};
//        ArrayAdapter<String> reminderTypeAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, reminderTypes);
//        reminderTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        reminderTypeSpinner.setAdapter(reminderTypeAdapter);
//    }
//
//    private void setupListeners() {
//        datePickerButton.setOnClickListener(v -> showDatePicker());
//        timePickerButton.setOnClickListener(v -> showTimePicker());
//        saveButton.setOnClickListener(v -> saveChanges());
//    }
//
//    private void populateFields() {
//        if (reminderToEdit != null) {
//            dateTextView.setText(reminderToEdit.getDate());
//            timeTextView.setText(reminderToEdit.getTime());
//
//            // Set selected pet in spinner
//            for (int i = 0; i < petSpinner.getCount(); i++) {
//                Pet pet = (Pet) petSpinner.getItemAtPosition(i);
//                if (pet.getId().equals(reminderToEdit.getPetId())) {
//                    petSpinner.setSelection(i);
//                    break;
//                }
//            }
//
//            // Set selected reminder type in spinner
//            for (int i = 0; i < reminderTypeSpinner.getCount(); i++) {
//                if (reminderTypeSpinner.getItemAtPosition(i).toString().equals(reminderToEdit.getReminderType())) {
//                    reminderTypeSpinner.setSelection(i);
//                    break;
//                }
//            }
//        }
//    }
//
//    private void showDatePicker() {
//        final Calendar c = Calendar.getInstance();
//        int year = c.get(Calendar.YEAR);
//        int month = c.get(Calendar.MONTH);
//        int day = c.get(Calendar.DAY_OF_MONTH);
//
//        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
//                (view, year1, monthOfYear, dayOfMonth) -> {
//                    String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
//                    dateTextView.setText(date);
//                }, year, month, day);
//        datePickerDialog.show();
//    }
//
//    private void showTimePicker() {
//        final Calendar c = Calendar.getInstance();
//        int hour = c.get(Calendar.HOUR_OF_DAY);
//        int minute = c.get(Calendar.MINUTE);
//
//        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
//                (view, hourOfDay, minute1) -> {
//                    String time = String.format("%02d:%02d", hourOfDay, minute1);
//                    timeTextView.setText(time);
//                }, hour, minute, true);
//        timePickerDialog.show();
//    }
//
//    private void saveChanges() {
//        Pet selectedPet = (Pet) petSpinner.getSelectedItem();
//        String date = dateTextView.getText().toString();
//        String time = timeTextView.getText().toString();
//        String reminderType = reminderTypeSpinner.getSelectedItem().toString();
//
//        if (selectedPet != null && !date.isEmpty() && !time.isEmpty()) {
//            reminderToEdit.setPetId(selectedPet.getId());
//            reminderToEdit.setDate(date);
//            reminderToEdit.setTime(time);
//            reminderToEdit.setReminderType(reminderType);
//
//            long result = dbHelper.updateReminder(reminderToEdit);
//            if (result != -1) {
//                Toast.makeText(getContext(), "Reminder updated successfully", Toast.LENGTH_SHORT).show();
//                if (getActivity() instanceof MainActivity) {
//                    ((MainActivity) getActivity()).onDataUpdated();
//                }
//                dismiss();
//            } else {
//                Toast.makeText(getContext(), "Error updating reminder", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
//        }
//    }
//}