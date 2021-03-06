package online.meetforyou.whatsappapp.Adapters;

import android.support.v4.os.IResultReceiver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import online.meetforyou.whatsappapp.Fragments.CallsFragment;
import online.meetforyou.whatsappapp.Fragments.ChatsFragments;
import online.meetforyou.whatsappapp.Fragments.StatusFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull  FragmentManager fm) {
        super(fm);
    }



    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new ChatsFragments();
            case 1:
                return new StatusFragment();
            case 2:
                return new CallsFragment();
            default:
                return new ChatsFragments();

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position==0) {
            title = "CHATS";
        }
        if (position==1) {
            title = "GOOGLE";
        }
        if (position==2) {
            title = "YOUTUBE";
        }


        return title;
    }
}
