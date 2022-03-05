package edu.vassar.cmpu203.housematemanager.view;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
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
import java.util.stream.Collectors;

import edu.vassar.cmpu203.housematemanager.databinding.FragmentChoreListBinding;
import edu.vassar.cmpu203.housematemanager.model.Chore;
import edu.vassar.cmpu203.housematemanager.model.ChoreManager;
import edu.vassar.cmpu203.housematemanager.model.Housemate;
import edu.vassar.cmpu203.housematemanager.model.ReceiptItem;

public class ChoreListFragment extends Fragment implements IChoreListView{

    FragmentChoreListBinding binding;
    IChoreListView.Listener listener;

    public ChoreListFragment(IChoreListView.Listener listener) {this.listener = listener;}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentChoreListBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        this.binding.choreAddButton.setOnClickListener((clickedView) -> {

            // get the item name
            Editable nameEditable = binding.choreEditText.getText();
            String name = nameEditable.toString();

            Editable ownerEditable = binding.ownerEditText.getText();
            String owner = ownerEditable.toString();

            Editable assignerEditable = binding.assignerEditText.getText();
            String assigner = assignerEditable.toString();

            nameEditable.clear();
            ownerEditable.clear();
            assignerEditable.clear();

            try {
                if (name.isEmpty() || owner.isEmpty() || assigner.isEmpty()) {
                    Snackbar.make(view, "Please enter all values!", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "Added Successfully!", Snackbar.LENGTH_LONG).show();
                    listener.onChoreListAddedItem(name, owner, assigner, ChoreListFragment.this);
                }
            } catch (Exception e) {
                Snackbar.make(view, "Error" + e.toString(), Snackbar.LENGTH_LONG).show();
                Log.d("Housemate Manager", "TODO some input error" + e.toString());
            }
        });

        this.binding.choreRmButton.setOnClickListener((clickedView) -> {

            // get the item name
            Editable nameEditable = binding.choreEditText.getText();
            String name = nameEditable.toString();

            Editable ownerEditable = binding.ownerEditText.getText();
            String owner = ownerEditable.toString();

            Editable assignerEditable = binding.assignerEditText.getText();
            String assigner = assignerEditable.toString();

            nameEditable.clear();
            ownerEditable.clear();
            assignerEditable.clear();

            try {
                if (name.isEmpty() || owner.isEmpty() || assigner.isEmpty()) {
                    Snackbar.make(view, "Please enter all values!", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "Removed successfully!", Snackbar.LENGTH_LONG).show();
                    listener.onChoreListRemovedItem(name, owner, assigner, ChoreListFragment.this);
                }
            } catch (Exception e) {
                Snackbar.make(view, "Error" + e.toString(), Snackbar.LENGTH_LONG).show();
                Log.d("Housemate Manager", "TODO some input error");
            }

        });

    }


    @Override
    public void updateDisplay(ChoreManager manager, List<Housemate> housemates) {
        String s = manager.toString();
        this.binding.choreListTextView.setText(s);

        // Autocomplete chore name
        List<String> chores = manager.getChoreList().stream()
                .map(Chore::getName)
                .collect(Collectors.toList());
        ArrayAdapter<String> choreAdapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_list_item_1, chores);
        this.binding.choreEditText.setAdapter(choreAdapter);

        // Autocomplete owner & assigner
        ArrayAdapter<Housemate> mateAdapter = new ArrayAdapter<Housemate>(this.getContext(),
                android.R.layout.simple_list_item_1, housemates);
        this.binding.ownerEditText.setAdapter(mateAdapter);
        this.binding.assignerEditText.setAdapter(mateAdapter);
    }

    @Override
    public void hmDNEerror() {
        Snackbar.make(this.binding.getRoot(), "That Housemate does not exist.", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void choreAEerror() {
        Snackbar.make(this.binding.getRoot(), "That chore name already exists.", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void removeError() {
        Snackbar.make(this.binding.getRoot(), "That chore does not exist.", Snackbar.LENGTH_LONG).show();
    }
}
