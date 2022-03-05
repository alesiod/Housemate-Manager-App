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

import edu.vassar.cmpu203.housematemanager.databinding.FragmentShoppingListBinding;
import edu.vassar.cmpu203.housematemanager.model.Inventory;
import edu.vassar.cmpu203.housematemanager.model.Item;


public class ShoppingListFragment extends Fragment implements IShoppingListView{

    FragmentShoppingListBinding binding;
    Listener listener;

    public ShoppingListFragment(Listener listener) {this.listener = listener;}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentShoppingListBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        this.binding.shoppingListAddItemButton.setOnClickListener((clickedView) -> {

            Editable nameEditable = binding.shoppingListNameEditText.getText();
            String name = nameEditable.toString();

            Editable qtyEditable = binding.shoppingLIstQtyEditText.getText();
            String qtyString = qtyEditable.toString();

            if (name.equals("") || qtyString.equals("")) {
                Log.d("Housemate Manager", "empty field detected");
                Snackbar.make(view, "Please enter name and quantity of item.", Snackbar.LENGTH_LONG).show();
            } else {
                nameEditable.clear();
                qtyEditable.clear();

                int qty = Integer.parseInt(qtyString);

                listener.onShoppingListAddedItem(name, qty, ShoppingListFragment.this);
            }
        });

        this.binding.shoppingListRmItemButton.setOnClickListener((clickedView) -> {

            Editable nameEditable = binding.shoppingListNameEditText.getText();
            String name = nameEditable.toString();

            Editable qtyEditable = binding.shoppingLIstQtyEditText.getText();
            String qtyString = qtyEditable.toString();

            if (name.equals("") || qtyString.equals("")) {
                Log.d("Housemate Manager", "empty field detected");
                Snackbar.make(view, "Please enter name and quantity of item.", Snackbar.LENGTH_LONG).show();
            } else {
                nameEditable.clear();
                qtyEditable.clear();

                int qty = Integer.parseInt(qtyString);

                listener.onShoppingListRemovedItem(name, qty, ShoppingListFragment.this);
            }

        });

        // Inventory Button
        this.binding.shoppingListInventoryButton.setOnClickListener((clickedView) -> this.listener.onViewInventory());

    }

    @Override
    public void updateDisplay(Inventory inventory) {
        String s = inventory.toStringShoppingList();
        this.binding.shoppingListTextView.setText(s);

        // Autocomplete view
        List<Item> items = new ArrayList<>(inventory.getItems().values());
        AutoCompleteTextView nameEditText = this.binding.shoppingListNameEditText;
        ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(this.getContext(),
                android.R.layout.simple_list_item_1, items);
        nameEditText.setAdapter(adapter);
    }

    @Override
    public void noSuchItemError() {
        Snackbar.make(this.binding.getRoot(), "No such item. Please provide item from the list.", Snackbar.LENGTH_LONG).show();
    }
}