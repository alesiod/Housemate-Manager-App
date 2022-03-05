package edu.vassar.cmpu203.housematemanager.view;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import edu.vassar.cmpu203.housematemanager.databinding.FragmentHouseBinding;
import edu.vassar.cmpu203.housematemanager.model.House;
import edu.vassar.cmpu203.housematemanager.model.Housemate;


public class HouseFragment extends Fragment implements IHouseView {

    FragmentHouseBinding binding;
    IHouseView.Listener listener;

    public HouseFragment(IHouseView.Listener listener) {this.listener = listener;}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentHouseBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {


        this.binding.houseAddButton.setOnClickListener((clickedView) -> {

            // get the item name
            Editable nameEditable = binding.houseNameEditText.getText();
            String name = nameEditable.toString();
            nameEditable.clear();

            if (name.equals("")) {
                Snackbar.make(view, "Please enter a name", Snackbar.LENGTH_LONG).show();
            } else {
                listener.onAddHousemate(name, HouseFragment.this);
            }
        });

        this.binding.houseRmButton.setOnClickListener((clickedView) -> {

            // get the item name
            Editable nameEditable = binding.houseNameEditText.getText();
            String name = nameEditable.toString();
            nameEditable.clear();

            if (name.equals("")) {
                Snackbar.make(view, "Please enter a name", Snackbar.LENGTH_LONG).show();
            } else {
                listener.onRemovedHousemate(name, HouseFragment.this);
            }
        });

    }

    @Override
    public void updateDisplay(House house) {
        String s = house.toString();
        this.binding.houseTextView.setText(s);

        List<Housemate> housemates = house.getHousemates();
        AutoCompleteTextView nameEditText = this.binding.houseNameEditText;
        ArrayAdapter<Housemate> adapter = new ArrayAdapter<>(this.getContext(),
                android.R.layout.simple_list_item_1, housemates);
        nameEditText.setAdapter(adapter);
    }

    @Override
    public void noSuchHousemateError() {
        Snackbar.make(this.binding.getRoot(), "No such housemate. Please provide a name from the list.", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void duplicateHousemateError() {
        Snackbar.make(this.binding.getRoot(), "Housemate already exists.", Snackbar.LENGTH_LONG).show();
    }
}