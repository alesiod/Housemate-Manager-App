package edu.vassar.cmpu203.housematemanager.view;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import edu.vassar.cmpu203.housematemanager.databinding.MainBinding;


public class MainView implements IMainView{

    private MainBinding binding;
    private FragmentActivity activity;

    public MainView(FragmentActivity activity){
        this.binding = MainBinding.inflate(activity.getLayoutInflater());
        this.activity = activity;
    }

    @Override
    public View getRootView() {
        return this.binding.getRoot();
    }

    @Override
    public void displayFragment(Fragment fragment) {

        this.activity.getSupportFragmentManager()
                .beginTransaction()
                .replace(this.binding.fragmentContainerView.getId(), fragment)
                .commitNow();
    }

}

