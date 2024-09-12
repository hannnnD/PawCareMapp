package com.mobileapp.f_m_a_petcare;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Toast;
import android.Manifest;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataUpdateListener{
    private List<DataUpdateListener> listeners = new ArrayList<>();
    private ViewPager2 mViewPager;
    private BottomNavigationView mBottomNavigationView;
    private static final int PERMISSION_REQUEST_CODE = 1001;
    private static final int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.view_pager);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        mViewPager.setAdapter(adapter);

        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                switch (position) {
                    case 0:
                        mBottomNavigationView.setSelectedItemId(R.id.menu_pet);
                        break;
                    case 1:
                        mBottomNavigationView.setSelectedItemId(R.id.menu_noti);
                        break;
                    case 2:
                        mBottomNavigationView.setSelectedItemId(R.id.menu_home);
                        break;
                    case 3:
                        mBottomNavigationView.setSelectedItemId(R.id.menu_health);
                        break;
                    case 4:
                        mBottomNavigationView.setSelectedItemId(R.id.menu_settings);
                        break;
                }
            }
        });

        mBottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_pet) {
                    mViewPager.setCurrentItem(0);
                } else if (itemId == R.id.menu_noti) {
                    mViewPager.setCurrentItem(1);
                } else if (itemId == R.id.menu_home) {
                    mViewPager.setCurrentItem(2);
                } else if (itemId == R.id.menu_health) {
                    mViewPager.setCurrentItem(3);
                } else if (itemId == R.id.menu_settings) {
                    mViewPager.setCurrentItem(4);
                }
                return true;
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // For Android 13 and above, we use READ_MEDIA_IMAGES
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE);
            } else {
                pickImage();
            }
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // For Android 6.0 to Android 12, we use READ_EXTERNAL_STORAGE
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            } else {
                pickImage();
            }
        } else {
            // For Android 5.1 and below, no runtime permission is needed
            pickImage();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                pickImage();
            } else {
                Toast.makeText(this, "Permission denied. You can still select images through the system picker.", Toast.LENGTH_LONG).show();
                pickImage(); // Even if permission is denied, we can use the system picker
            }
        }
    }

    private void pickImage() {
        Intent intent = new Intent(MediaStore.ACTION_PICK_IMAGES);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null && data.getData() != null) {
                Uri imageUri = data.getData();
                // Use this URI to display or process the selected image
                // For example, you might want to save this URI to your database
                saveImageUriToDatabase(imageUri.toString());
            }
        }
    }

    private void saveImageUriToDatabase(String uriString) {
        // Implement this method to save the URI to your database
        // This will depend on how your database is structured
    }

    public void addDataUpdateListener(DataUpdateListener listener) {
        listeners.add(listener);
    }

    public void removeDataUpdateListener(DataUpdateListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void onDataUpdated() {
        for (DataUpdateListener listener : listeners) {
            listener.onDataUpdated();
        }
    }
}