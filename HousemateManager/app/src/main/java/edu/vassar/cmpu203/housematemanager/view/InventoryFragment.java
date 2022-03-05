package edu.vassar.cmpu203.housematemanager.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.vassar.cmpu203.housematemanager.databinding.FragmentInventoryBinding;
import edu.vassar.cmpu203.housematemanager.model.Inventory;
import edu.vassar.cmpu203.housematemanager.model.Item;


public class InventoryFragment extends Fragment implements IInventoryView{

    FragmentInventoryBinding binding;
    Listener listener;

    public InventoryFragment(IInventoryView.Listener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentInventoryBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        this.binding.inventoryAddItemButton.setOnClickListener((clickedView) -> {

            Editable nameEditable = binding.inventoryNameEditText.getText();
            String name = nameEditable.toString();

            Editable qtyEditable = binding.inventoryQtyEditText.getText();
            String qtyString = qtyEditable.toString();

            if (name.equals("") || qtyString.equals("")) {
                Log.d("Housemate Manager", "empty field detected");
                Snackbar.make(view, "Please enter name and quantity of item.", Snackbar.LENGTH_LONG).show();
            } else {
                nameEditable.clear();
                qtyEditable.clear();

                int qty = Integer.parseInt(qtyString);

                listener.onInventoryAddedItem(name, qty, InventoryFragment.this);
            }
        });

        this.binding.inventoryRmItemButton.setOnClickListener((clickedView) -> {

            Editable nameEditable = binding.inventoryNameEditText.getText();
            String name = nameEditable.toString();

            Editable qtyEditable = binding.inventoryQtyEditText.getText();
            String qtyString = qtyEditable.toString();

            if (name.equals("") || qtyString.equals("")) {
                Log.d("Housemate Manager", "empty field detected");
                Snackbar.make(view, "Please enter name and quantity of item.", Snackbar.LENGTH_LONG).show();
            } else {
                nameEditable.clear();
                qtyEditable.clear();

                int qty = Integer.parseInt(qtyString);

                listener.onInventoryRemovedItem(name, qty, InventoryFragment.this);
            }
        });

        // Shopping List Button
        this.binding.inventoryShoppingListButton.setOnClickListener((clickedView) -> this.listener.onViewShoppingList());
    }

    @Override
    public void updateDisplay(Inventory inventory) {
        Log.d("Housemate Manager", "InventoryFragment updating display");

        // Inventory View
        String s = inventory.toString();
        this.binding.inventoryTextView.setText(s);

        // Autocomplete view
        List<Item> items = new ArrayList<>(inventory.getItems().values());
        AutoCompleteTextView nameEditText = this.binding.inventoryNameEditText;
        ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(this.getContext(),
                android.R.layout.simple_list_item_1, items);
        nameEditText.setAdapter(adapter);
    }

    @Override
    public void noSuchItemError() {
        Snackbar.make(this.binding.getRoot(), "No such item. Please provide item from the list.", Snackbar.LENGTH_LONG).show();
    }


}