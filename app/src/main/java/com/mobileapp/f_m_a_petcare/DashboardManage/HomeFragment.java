package com.mobileapp.f_m_a_petcare.DashboardManage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.mobileapp.f_m_a_petcare.DB.DatabaseHelper;
import com.mobileapp.f_m_a_petcare.DataUpdateListener;
import com.mobileapp.f_m_a_petcare.MainActivity;
import com.mobileapp.f_m_a_petcare.NotiManage.Reminder;
import com.mobileapp.f_m_a_petcare.PetManage.Pet;
import com.mobileapp.f_m_a_petcare.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class HomeFragment extends Fragment implements DataUpdateListener {

    private RecyclerView petRecyclerView;
    private RecyclerView reminderRecyclerView;
    private TextView viewAllReminders;
    private DatabaseHelper databaseHelper;

    public HomeFragment() {
        // Required empty public constructor
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
        setupPetRecyclerView();
        setupReminderRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void reloadData(){
        Toast.makeText(getActivity(), "Reload", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        databaseHelper = new DatabaseHelper(getContext());

        petRecyclerView = view.findViewById(R.id.petRecyclerView);
        reminderRecyclerView = view.findViewById(R.id.reminderRecyclerView);
        viewAllReminders = view.findViewById(R.id.viewAllReminders);

        setupPetRecyclerView();
        setupReminderRecyclerView();
        setupViewAllReminders();

        return view;
    }

    private void setupPetRecyclerView() {
        List<Pet> pets = databaseHelper.getAllPets();
        PetAdapter petAdapter = new PetAdapter(pets);
        petRecyclerView.setAdapter(petAdapter);
    }

    private void setupReminderRecyclerView() {
        List<Reminder> reminders = databaseHelper.getAllReminders();
        ReminderAdapter reminderAdapter = new ReminderAdapter(reminders);
        reminderRecyclerView.setAdapter(reminderAdapter);
    }


    private void setupViewAllReminders() {
        viewAllReminders.setOnClickListener(v -> {
        });
    }

    // PetAdapter inner class
    private class PetAdapter extends RecyclerView.Adapter<PetAdapter.PetViewHolder> {

        private List<Pet> pets;

        public PetAdapter(List<Pet> pets) {
            this.pets = pets;
        }

        @NonNull
        @Override
        public PetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemd_pet, parent, false);
            return new PetViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PetViewHolder holder, int position) {
            Pet pet = pets.get(position);
            holder.petName.setText(pet.getTenThu());
            Glide.with(holder.itemView.getContext()).load(pet.getImagePath()).into(holder.petImage);
        }

        @Override
        public int getItemCount() {
            return pets.size();
        }

        class PetViewHolder extends RecyclerView.ViewHolder {
            ImageView petImage;
            TextView petName;

            PetViewHolder(@NonNull View itemView) {
                super(itemView);
                petImage = itemView.findViewById(R.id.petImage);
                petName = itemView.findViewById(R.id.petName);
            }
        }
    }

    // ReminderAdapter inner class
    private class ReminderAdapter extends RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder> {

        private List<Reminder> reminders;

        public ReminderAdapter(List<Reminder> reminders) {
            this.reminders = reminders;
        }

        @NonNull
        @Override
        public ReminderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemd_reminder, parent, false);
            return new ReminderViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ReminderViewHolder holder, int position) {
            Reminder reminder = reminders.get(position);
            Pet pet = databaseHelper.getPet(reminder.getPetId());

            if (pet != null) {
                holder.reminderType.setText(reminder.getReminderType());
                holder.reminderDateTime.setText(reminder.getDate() + " " + reminder.getTime());

                // Use the getReminderTypeIcon method to get the appropriate icon
                int iconResId = getReminderTypeIcon(reminder.getReminderType());
                holder.petImage.setImageResource(iconResId);
            } else {
                Log.e("ReminderAdapter", "Pet not found for reminder: " + reminder.getId());
                holder.petImage.setImageResource(R.drawable.ic_delete);
                holder.reminderType.setText("Unknown Reminder");
                holder.reminderDateTime.setText("Unknown Date/Time");
            }
        }

        @Override
        public int getItemCount() {
            return reminders.size();
        }

        class ReminderViewHolder extends RecyclerView.ViewHolder {
            ImageView petImage;
            TextView reminderType;
            TextView reminderDateTime;
            ImageButton editButton;
            ImageButton deleteButton;

            ReminderViewHolder(@NonNull View itemView) {
                super(itemView);
                petImage = itemView.findViewById(R.id.petImageReminder);
                reminderType = itemView.findViewById(R.id.reminderType);
                reminderDateTime = itemView.findViewById(R.id.reminderDateTime);
                editButton = itemView.findViewById(R.id.editButton);
                deleteButton = itemView.findViewById(R.id.deleteButton);

                editButton.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        // TODO: Implement edit reminder functionality
                    }
                });

                deleteButton.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Reminder reminder = reminders.get(position);
                        databaseHelper.deleteReminder(reminder.getId());
                        reminders.remove(position);
                        notifyItemRemoved(position);
                    }
                });
            }
        }
    }
    private int getReminderTypeIcon(String reminderType) {
        switch (reminderType) {
            case "Cho ăn":
                return R.drawable.ic_feed;
            case "Tắm":
                return R.drawable.ic_bath;
            case "Khám bệnh":
                return R.drawable.ic_vet;
            case "Tiêm phòng":
                return R.drawable.ic_vaccine;
            default:
                return R.drawable.ic_noti;
        }
    }


}