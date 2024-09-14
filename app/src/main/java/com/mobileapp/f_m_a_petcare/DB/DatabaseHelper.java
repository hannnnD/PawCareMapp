package com.mobileapp.f_m_a_petcare.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobileapp.f_m_a_petcare.NotiManage.Reminder;
import com.mobileapp.f_m_a_petcare.PetManage.Pet;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PetCare.db";
    private static final int DATABASE_VERSION = 1;

    // Bảng Pet
    public static final String TABLE_PETS = "pets";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TEN_THU = "ten_thu";
    public static final String COLUMN_TEN_GIONG = "ten_giong";
    public static final String COLUMN_GIOI_TINH = "gioi_tinh";
    public static final String COLUMN_NGAY_SINH = "ngay_sinh";
    public static final String COLUMN_MAU_SAC = "mau_sac";
    public static final String COLUMN_CAN_NANG = "can_nang";
    public static final String COLUMN_CHIEU_CAO = "chieu_cao";
    public static final String COLUMN_IMAGE_PATH = "image_path";

    // Bảng PetImages
    public static final String TABLE_PET_IMAGES = "pet_images";
    public static final String COLUMN_PET_ID = "pet_id";
    public static final String COLUMN_IMAGE_PATH_COLLECTION = "image_path";

    // Bảng Reminder
    public static final String TABLE_REMINDERS = "reminders";
    public static final String COLUMN_REMINDER_ID = "reminder_id";
    public static final String COLUMN_REMINDER_DATE = "reminder_date";
    public static final String COLUMN_REMINDER_TIME = "reminder_time";
    public static final String COLUMN_REMINDER_TYPE = "reminder_type";

    // SQL tạo bảng Pet
    private static final String CREATE_TABLE_PETS = "CREATE TABLE " + TABLE_PETS + "("
            + COLUMN_ID + " TEXT PRIMARY KEY,"
            + COLUMN_TEN_THU + " TEXT,"
            + COLUMN_TEN_GIONG + " TEXT,"
            + COLUMN_GIOI_TINH + " TEXT,"
            + COLUMN_NGAY_SINH + " TEXT,"
            + COLUMN_MAU_SAC + " TEXT,"
            + COLUMN_CAN_NANG + " TEXT,"
            + COLUMN_CHIEU_CAO + " TEXT,"
            + COLUMN_IMAGE_PATH + " TEXT"
            + ")";

    // SQL tạo bảng PetImages
    private static final String CREATE_TABLE_PET_IMAGES = "CREATE TABLE " + TABLE_PET_IMAGES + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PET_ID + " TEXT,"
            + COLUMN_IMAGE_PATH_COLLECTION + " TEXT,"
            + "FOREIGN KEY(" + COLUMN_PET_ID + ") REFERENCES " + TABLE_PETS + "(" + COLUMN_ID + ")"
            + ")";

    // SQL tạo bảng Reminder
    private static final String CREATE_TABLE_REMINDERS = "CREATE TABLE " + TABLE_REMINDERS + "("
            + COLUMN_REMINDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_PET_ID + " TEXT,"
            + COLUMN_REMINDER_DATE + " TEXT,"
            + COLUMN_REMINDER_TIME + " TEXT,"
            + COLUMN_REMINDER_TYPE + " TEXT,"
            + "FOREIGN KEY(" + COLUMN_PET_ID + ") REFERENCES " + TABLE_PETS + "(" + COLUMN_ID + ")"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PETS);
        db.execSQL(CREATE_TABLE_PET_IMAGES);
        db.execSQL(CREATE_TABLE_REMINDERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý nâng cấp cơ sở dữ liệu nếu cần
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PETS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PET_IMAGES);
        onCreate(db);
    }

    // Phương thức thêm pet mới
    public long addPet(Pet pet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, pet.getId());
        values.put(COLUMN_TEN_THU, pet.getTenThu());
        values.put(COLUMN_TEN_GIONG, pet.getTenGiong());
        values.put(COLUMN_GIOI_TINH, pet.getGioiTinh());
        values.put(COLUMN_NGAY_SINH, pet.getNgaySinh());
        values.put(COLUMN_MAU_SAC, pet.getMauSac());
        values.put(COLUMN_CAN_NANG, pet.getCanNang());
        values.put(COLUMN_CHIEU_CAO, pet.getChieuCao());
        values.put(COLUMN_IMAGE_PATH, pet.getImagePath());

        long id = db.insert(TABLE_PETS, null, values);

        // Thêm các ảnh vào bảng pet_images
        for (String imagePath : pet.getImagePaths()) {
            ContentValues imageValues = new ContentValues();
            imageValues.put(COLUMN_PET_ID, pet.getId());
            imageValues.put(COLUMN_IMAGE_PATH_COLLECTION, imagePath);
            db.insert(TABLE_PET_IMAGES, null, imageValues);
        }

        db.close();
        return id;
    }

    // Phương thức cập nhật thông tin pet
    public int updatePet(Pet pet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEN_THU, pet.getTenThu());
        values.put(COLUMN_TEN_GIONG, pet.getTenGiong());
        values.put(COLUMN_GIOI_TINH, pet.getGioiTinh());
        values.put(COLUMN_NGAY_SINH, pet.getNgaySinh());
        values.put(COLUMN_MAU_SAC, pet.getMauSac());
        values.put(COLUMN_CAN_NANG, pet.getCanNang());
        values.put(COLUMN_CHIEU_CAO, pet.getChieuCao());
        values.put(COLUMN_IMAGE_PATH, pet.getImagePath());

        int rowsAffected = db.update(TABLE_PETS, values, COLUMN_ID + " = ?", new String[]{pet.getId()});

        // Xóa tất cả ảnh cũ và thêm lại ảnh mới
        db.delete(TABLE_PET_IMAGES, COLUMN_PET_ID + " = ?", new String[]{pet.getId()});
        for (String imagePath : pet.getImagePaths()) {
            ContentValues imageValues = new ContentValues();
            imageValues.put(COLUMN_PET_ID, pet.getId());
            imageValues.put(COLUMN_IMAGE_PATH_COLLECTION, imagePath);
            db.insert(TABLE_PET_IMAGES, null, imageValues);
        }

        db.close();
        return rowsAffected;
    }

    // Phương thức xóa pet
    public void deletePet(String petId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PETS, COLUMN_ID + " = ?", new String[]{petId});
        db.delete(TABLE_PET_IMAGES, COLUMN_PET_ID + " = ?", new String[]{petId});
        db.close();
    }

    // Phương thức lấy tất cả pet
    public List<Pet> getAllPets() {
        List<Pet> petList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PETS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Pet pet = new Pet(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_THU)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_GIONG)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GIOI_TINH)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NGAY_SINH)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAU_SAC)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CAN_NANG)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CHIEU_CAO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH))
                );
                pet.setImagePaths(getPetImages(pet.getId()));
                petList.add(pet);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return petList;
    }

    // Phương thức lấy một pet theo ID
    public Pet getPet(String petId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PETS, null, COLUMN_ID + "=?", new String[]{petId}, null, null, null);
        Pet pet = null;
        if (cursor != null && cursor.moveToFirst()) {
            pet = new Pet(
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_THU)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN_GIONG)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GIOI_TINH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NGAY_SINH)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MAU_SAC)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CAN_NANG)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CHIEU_CAO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH))
            );
            pet.setImagePaths(getPetImages(petId));
            cursor.close();
        }
        db.close();
        return pet;
    }

    // Phương thức lấy danh sách ảnh của một pet
    private ArrayList<String> getPetImages(String petId) {
        ArrayList<String> imagePaths = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PET_IMAGES, new String[]{COLUMN_IMAGE_PATH_COLLECTION},
                COLUMN_PET_ID + "=?", new String[]{petId}, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                imagePaths.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_PATH_COLLECTION)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return imagePaths;
    }

    // Phương thức thêm ảnh mới cho pet
    public void addPetImage(String petId, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PET_ID, petId);
        values.put(COLUMN_IMAGE_PATH_COLLECTION, imagePath);
        db.insert(TABLE_PET_IMAGES, null, values);
        db.close();
    }

    // Phương thức xóa ảnh của pet
    public void deletePetImage(String petId, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PET_IMAGES, COLUMN_PET_ID + " = ? AND " + COLUMN_IMAGE_PATH_COLLECTION + " = ?",
                new String[]{petId, imagePath});
        db.close();
    }

    //Reminder method
    public long addReminder(String petId, String date, String time, String reminderType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PET_ID, petId);
        values.put(COLUMN_REMINDER_DATE, date);
        values.put(COLUMN_REMINDER_TIME, time);
        values.put(COLUMN_REMINDER_TYPE, reminderType);
        long id = db.insert(TABLE_REMINDERS, null, values);
        db.close();
        return id;
    }

    public List<Reminder> getAllReminders() {
        List<Reminder> reminders = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_REMINDERS + " ORDER BY "
                + COLUMN_REMINDER_DATE + " ASC, "
                + COLUMN_REMINDER_TIME + " ASC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Reminder reminder = new Reminder(
                        cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_REMINDER_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REMINDER_DATE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REMINDER_TIME)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REMINDER_TYPE))
                );
                reminders.add(reminder);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return reminders;
    }



    // Thêm phương thức để xóa lịch nhắc nhở
    public void deleteReminder(long reminderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_REMINDERS, COLUMN_REMINDER_ID + " = ?", new String[]{String.valueOf(reminderId)});
        db.close();
    }

    public Reminder getReminder(long reminderId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_REMINDERS, null, COLUMN_REMINDER_ID + "=?",
                new String[]{String.valueOf(reminderId)}, null, null, null);
        Reminder reminder = null;
        if (cursor != null && cursor.moveToFirst()) {
            reminder = new Reminder(
                    cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_REMINDER_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PET_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REMINDER_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REMINDER_TIME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REMINDER_TYPE))
            );
            cursor.close();
        }
        db.close();
        return reminder;
    }

    public void updateReminder(long id, String petId, String date, String time, String reminderType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PET_ID, petId);
        values.put(COLUMN_REMINDER_DATE, date);
        values.put(COLUMN_REMINDER_TIME, time);
        values.put(COLUMN_REMINDER_TYPE, reminderType);

        db.update(TABLE_REMINDERS, values, COLUMN_REMINDER_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

}