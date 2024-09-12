package com.mobileapp.f_m_a_petcare.PetManage;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.mobileapp.f_m_a_petcare.R;

import java.util.ArrayList;
import java.util.List;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {
    private List<Pet> pets;
    private OnPetClickListener listener;

    public interface OnPetClickListener {
        void onPetClick(Pet pet);
        void onEditClick(Pet pet);
        void onDeleteClick(Pet pet);
        void onAddImageClick(Pet pet);
        void onPetImageClick(Pet pet);
    }

    public PetAdapter(List<Pet> petList, OnPetClickListener listener) {
        this.pets = petList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pet, parent, false);
        return new PetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
        Pet pet = pets.get(position);
        holder.bind(pet);
    }

    @Override
    public int getItemCount() {
        return pets.size();
    }

    class PetViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPet;
        private TextView textViewPetName;
        private ImageButton buttonAddImage;
        private ImageButton editButton;
        private ImageButton deleteButton;

        public PetViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPet = itemView.findViewById(R.id.imagePet);
            textViewPetName = itemView.findViewById(R.id.textPetName);
            buttonAddImage = itemView.findViewById(R.id.buttonAddImage);
            editButton = itemView.findViewById(R.id.buttonEditPet);
            deleteButton = itemView.findViewById(R.id.buttonDeletePet);
        }

        public void bind(Pet pet) {
            textViewPetName.setText(pet.getTenThu());
            ArrayList<String> imagePaths = pet.getImagePaths();
            if (!imagePaths.isEmpty()) {
                Glide.with(itemView.getContext())
                        .load(Uri.parse(imagePaths.get(0)))
                        .into(imageViewPet);
            } else {
                imageViewPet.setImageResource(R.drawable.ic_add_photo);
            }

            itemView.setOnClickListener(v -> listener.onPetClick(pet));
            buttonAddImage.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onAddImageClick(pet);
                }
            });
            editButton.setOnClickListener(v -> listener.onEditClick(pet));
            deleteButton.setOnClickListener(v -> listener.onDeleteClick(pet));
            imageViewPet.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onPetImageClick(pet);
                }
            });
        }
    }
}