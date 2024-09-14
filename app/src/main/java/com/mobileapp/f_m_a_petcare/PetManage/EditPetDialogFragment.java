package com.mobileapp.f_m_a_petcare.PetManage;

import android.app.Activity;
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
import com.mobileapp.f_m_a_petcare.MainActivity;
import com.mobileapp.f_m_a_petcare.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class EditPetDialogFragment extends DialogFragment {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText editTextPetName, editTextPetBreed, editTextPetBirthday, editTextPetColor, editTextPetWeight, editTextPetHeight;
    private Button buttonChooseImage, buttonSaveChanges, buttonSelectBirthday;
    private String selectedImagePath;
    private Pet petToEdit;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale, radioButtonFemale;

    public static EditPetDialogFragment newInstance(Pet pet) {
        EditPetDialogFragment fragment = new EditPetDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("pet", pet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            petToEdit = (Pet) getArguments().getSerializable("pet");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_pet_dialog, container, false);

        editTextPetName = view.findViewById(R.id.editTextPetName);
        editTextPetBreed = view.findViewById(R.id.editTextPetBreed);
        editTextPetBirthday = view.findViewById(R.id.editTextPetBirthday);
        editTextPetColor = view.findViewById(R.id.editTextPetColor);
        editTextPetWeight = view.findViewById(R.id.editTextPetWeight);
        editTextPetHeight = view.findViewById(R.id.editTextPetHeight);
        buttonChooseImage = view.findViewById(R.id.buttonChooseImage);
        buttonSaveChanges = view.findViewById(R.id.buttonSaveChanges);
        buttonSelectBirthday = view.findViewById(R.id.buttonSelectBirthday);
        radioGroupGender = view.findViewById(R.id.radioGroupGender);
        radioButtonMale = view.findViewById(R.id.radioButtonMale);
        radioButtonFemale = view.findViewById(R.id.radioButtonFemale);

        buttonSelectBirthday.setOnClickListener(v -> showDatePicker());
        populateFields();

        buttonChooseImage.setOnClickListener(v -> chooseImage());
        buttonSaveChanges.setOnClickListener(v -> saveChanges());

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

    private void populateFields() {
        if (petToEdit != null) {
            editTextPetName.setText(petToEdit.getTenThu());
            editTextPetBreed.setText(petToEdit.getTenGiong());
            editTextPetBirthday.setText(petToEdit.getNgaySinh());
            editTextPetColor.setText(petToEdit.getMauSac());
            editTextPetWeight.setText(petToEdit.getCanNang());
            editTextPetHeight.setText(petToEdit.getChieuCao());
            selectedImagePath = String.valueOf(petToEdit.getImagePath());
            if ("Đực".equals(petToEdit.getGioiTinh())) {
                radioButtonMale.setChecked(true);
            } else {
                radioButtonFemale.setChecked(true);
            }
        }
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

    private void saveChanges() {
        String name = editTextPetName.getText().toString();
        String breed = editTextPetBreed.getText().toString();
        String color = editTextPetColor.getText().toString();
        String gender = radioButtonMale.isChecked() ? "Đực" : "Cái";
        String birthday = editTextPetBirthday.getText().toString();
        String weight = editTextPetWeight.getText().toString();
        String height = editTextPetHeight.getText().toString();

        // Kiểm tra xem các trường có được điền đầy đủ hay không
        if (name.isEmpty() || breed.isEmpty() || birthday.isEmpty() || color.isEmpty() || weight.isEmpty() || height.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra định dạng số cho cân nặng và chiều cao
        try {
            float weightValue = Float.parseFloat(weight);
            float heightValue = Float.parseFloat(height);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Weight and height must be numbers", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cập nhật đối tượng Pet
        petToEdit.setTenThu(name);
        petToEdit.setTenGiong(breed);
        petToEdit.setGioiTinh(gender);
        petToEdit.setNgaySinh(birthday);
        petToEdit.setMauSac(color);
        petToEdit.setCanNang(weight);
        petToEdit.setChieuCao(height);

        // Nếu có hình ảnh mới, cập nhật đường dẫn hình ảnh
        if (selectedImagePath != null) {
            petToEdit.setImagePath(selectedImagePath);
        }

        // Cập nhật Pet trong Fragment cha
        ((PetFragment) getParentFragment()).updatePet(petToEdit);

        // Gọi onDataUpdated() trong MainActivity để thông báo các Fragment khác
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).onDataUpdated();
        }

        // Đóng dialog sau khi hoàn thành
        dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}