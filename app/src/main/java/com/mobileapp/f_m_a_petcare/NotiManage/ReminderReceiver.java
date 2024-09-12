package com.mobileapp.f_m_a_petcare.NotiManage;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.mobileapp.f_m_a_petcare.DB.DatabaseHelper;
import com.mobileapp.f_m_a_petcare.PetManage.Pet;
import com.mobileapp.f_m_a_petcare.R;

public class ReminderReceiver extends BroadcastReceiver {
    private static final String CHANNEL_ID = "PetCareChannel";
    private static final int NOTIFICATION_ID = 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        long reminderId = intent.getLongExtra("reminderId", -1);
        String petId = intent.getStringExtra("petId");
        String reminderType = intent.getStringExtra("reminderType");

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        Pet pet = dbHelper.getPet(petId);

        if (pet != null) {
            String message = "Đã đến giờ " + reminderType + " cho " + pet.getTenThu();
            showNotification(context, message);

            // Xóa lịch nhắc nhở sau khi hiển thị thông báo
            if (reminderId != -1) {
                dbHelper.deleteReminder(reminderId);
            }
        }
    }

    private void showNotification(Context context, String message) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Pet Care Notifications", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_noti)
                .setContentTitle("Nhắc nhở chăm sóc thú cưng")
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}