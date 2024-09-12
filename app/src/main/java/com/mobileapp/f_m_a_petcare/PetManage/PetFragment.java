package com.mobileapp.f_m_a_petcare.PetManage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mobileapp.f_m_a_petcare.DB.DatabaseHelper;
import com.mobileapp.f_m_a_petcare.MainActivity;
import com.mobileapp.f_m_a_petcare.R;

import java.util.List;

public class PetFragment extends Fragment implements PetAdapter.OnPetClickListener {
    private RecyclerView recyclerViewPets;
    private PetAdapter petAdapter;
    private List<Pet> petList;
    private FloatingActionButton buttonAddPet;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Pet currentPet;
    private DatabaseHelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pet, container, false);

        recyclerViewPets = view.findViewById(R.id.recyclerViewPets);
        buttonAddPet = view.findViewById(R.id.buttonAddPet);
        recyclerViewPets.setLayoutManager(new LinearLayoutManager(getContext()));

        dbHelper = new DatabaseHelper(getContext());
        petList = dbHelper.getAllPets();
        petAdapter = new PetAdapter(petList, this);
        recyclerViewPets.setAdapter(petAdapter);

        buttonAddPet.setOnClickListener(v -> showAddPetDialog());

        return view;
    }

    private void showAddPetDialog() {
        AddPetDialogFragment addPetDialog = new AddPetDialogFragment();
        addPetDialog.show(getChildFragmentManager(), "AddPetDialog");
    }

    public void addPet(Pet pet) {
        long id = dbHelper.addPet(pet);
        if (id != -1) {
            petList.add(pet);
            petAdapter.notifyDataSetChanged();

            // Notify MainActivity
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).onDataUpdated();
            }
        } else {
            Toast.makeText(getContext(), "Không thể thêm thú cưng", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPetClick(Pet pet) {
        // Implement pet click action (e.g., show details)
    }

    @Override
    public void onEditClick(Pet pet) {
        showEditPetDialog(pet);
    }

    private void showEditPetDialog(Pet pet) {
        EditPetDialogFragment editDialog = EditPetDialogFragment.newInstance(pet);
        editDialog.show(getChildFragmentManager(), "EditPetDialog");
    }

    public void updatePet(Pet updatedPet) {
        int rowsAffected = dbHelper.updatePet(updatedPet);
        if (rowsAffected > 0) {
            int index = -1;
            for (int i = 0; i < petList.size(); i++) {
                if (petList.get(i).getId().equals(updatedPet.getId())) {
                    index = i;
                    break;
                }
            }
            if (index != -1) {
                petList.set(index, updatedPet);
                petAdapter.notifyItemChanged(index);
            }
        } else {
            Toast.makeText(getContext(), "Không thể cập nhật thú cưng", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteClick(Pet pet) {
        dbHelper.deletePet(pet.getId());
        petList.remove(pet);
        petAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAddImageClick(Pet pet) {
        currentPet = pet;
        openImageChooser();
    }

    @Override
    public void onPetImageClick(Pet pet) {
        showViewImgPetDialog(pet);
    }

    private void showViewImgPetDialog(Pet pet) {
        ViewImgPetDialogFragment dialog = ViewImgPetDialogFragment.newInstance(pet);
        dialog.show(getChildFragmentManager(), "ViewImgPetDialog");
    }

    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            String imagePath = imageUri.toString();
            if (currentPet != null) {
                currentPet.addImagePath(imagePath);
                dbHelper.addPetImage(currentPet.getId(), imagePath);
                updatePet(currentPet);
                Toast.makeText(getContext(), "Đã thêm ảnh mới vào bộ sưu tập", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (dbHelper != null) {
            dbHelper.close();
        }
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