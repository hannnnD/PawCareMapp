package com.mobileapp.f_m_a_petcare.PetManage;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.mobileapp.f_m_a_petcare.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class AddPetDialogFragment extends DialogFragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTextPetName, editTextPetBreed, editTextPetGender, editTextPetColor, editTextPetWeight, editTextPetHeight;
    private Button buttonChooseImage, buttonAddPet;
    private String selectedImagePath;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale, radioButtonFemale;
    private EditText editTextPetBirthday;
    private Button buttonSelectBirthday;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_pet_dialog, container, false);

        editTextPetName = view.findViewById(R.id.editTextPetName);
        editTextPetBreed = view.findViewById(R.id.editTextPetBreed);
        editTextPetBirthday = view.findViewById(R.id.editTextPetBirthday);
        editTextPetColor = view.findViewById(R.id.editTextPetColor);
        editTextPetWeight = view.findViewById(R.id.editTextPetWeight);
        editTextPetHeight = view.findViewById(R.id.editTextPetHeight);
        buttonChooseImage = view.findViewById(R.id.buttonChooseImage);
        buttonAddPet = view.findViewById(R.id.buttonAddPet);
        radioGroupGender = view.findViewById(R.id.radioGroupGender);
        radioButtonMale = view.findViewById(R.id.radioButtonMale);
        radioButtonFemale = view.findViewById(R.id.radioButtonFemale);

        buttonSelectBirthday = view.findViewById(R.id.buttonSelectBirthday);

        buttonSelectBirthday.setOnClickListener(v -> showDatePicker());
        editTextPetBirthday.setOnClickListener(v -> showDatePickerDialog());
        buttonChooseImage.setOnClickListener(v -> chooseImage());
        buttonAddPet.setOnClickListener(v -> addPet());

        return view;
    }

    private void showDatePicker() {
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select birthday")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTheme(R.style.CustomMaterialCalendar)
                .build();

        datePicker.addOnPositiveButtonClickListener(selection -> {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            String formattedDate = sdf.format(new Date(selection));
            editTextPetBirthday.setText(formattedDate);
        });

        datePicker.show(getParentFragmentManager(), "DATE_PICKER");
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            selectedImagePath = uri.toString();
            Toast.makeText(getContext(), "Image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDatePickerDialog() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                    editTextPetBirthday.setText(date);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void addPet() {
        String name = editTextPetName.getText().toString();
        String breed = editTextPetBreed.getText().toString();
        String birthday = editTextPetBirthday.getText().toString();
        String color = editTextPetColor.getText().toString();
        String weight = editTextPetWeight.getText().toString();
        String height = editTextPetHeight.getText().toString();
        String gender = radioButtonMale.isChecked() ? "Đực" : "Cái";

        if (name.isEmpty() || breed.isEmpty() || birthday.isEmpty() || color.isEmpty() || weight.isEmpty() || height.isEmpty() || selectedImagePath == null) {
            Toast.makeText(getContext(), "Please fill all fields and select an image", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            float weightValue = Float.parseFloat(weight);
            float heightValue = Float.parseFloat(height);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Weight and height must be numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        Pet newPet = new Pet(UUID.randomUUID().toString(), name, breed, gender, birthday, color, weight, height, selectedImagePath);
        ((PetFragment) getParentFragment()).addPet(newPet);
        dismiss();
    }
}