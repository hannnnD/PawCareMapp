package com.mobileapp.f_m_a_petcare;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.mobileapp.f_m_a_petcare.DashboardManage.HomeFragment;
import com.mobileapp.f_m_a_petcare.HealthManage.HealthFragment;
import com.mobileapp.f_m_a_petcare.NotiManage.NotiFragment;
import com.mobileapp.f_m_a_petcare.PetManage.PetFragment;
import com.mobileapp.f_m_a_petcare.SettingsManage.SettingsFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PetFragment();
            case 1:
                return new NotiFragment();
            case 2:
                return new HomeFragment();
            case 3:
                return new HealthFragment();
            case 4:
                return new SettingsFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}