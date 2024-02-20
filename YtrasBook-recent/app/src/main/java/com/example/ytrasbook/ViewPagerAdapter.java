package com.example.ytrasbook;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0 : return  new Maternelle() ;
            case 1 : return  new Primaire() ;
            case 2 : return  new Secondaire() ;
            case 3 : return  new Cahiers() ;
            case 4 : return  new Accessoires() ;
            case 5 : return  new Evenements() ;
            default: return  new Maternelle();

        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
