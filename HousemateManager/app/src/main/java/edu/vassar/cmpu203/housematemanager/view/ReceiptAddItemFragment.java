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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.vassar.cmpu203.housematemanager.databinding.FragmentReceiptAddItemBinding;
import edu.vassar.cmpu203.housematemanager.model.Item;
import edu.vassar.cmpu203.housematemanager.model.Receipt;
import edu.vassar.cmpu203.housematemanager.model.ReceiptItem;

public class ReceiptAddItemFragment extends Fragment implements IReceiptAddItemView {


    FragmentReceiptAddItemBinding binding;
    Listener listener;

    public ReceiptAddItemFragment(IReceiptAddItemView.Listener listener) {
        this.listener = listener;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentReceiptAddItemBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        this.binding.receiptAddItemAddButton.setOnClickListener((clickedView) -> {

            Editable nameEditable = binding.receiptAddItemNameEditText.getText();
            String name = nameEditable.toString();

            Editable qtyEditable = binding.receiptAddItemQtyEditText.getText();
            String qtyString = qtyEditable.toString();

            Editable priceEditable = binding.receiptAddItemPriceEditText.getEditableText();
            String priceString = priceEditable.toString();

            if (name.equals("") || qtyString.equals("") || priceString.equals("")) {
                Log.d("Housemate Manager", "empty field detected");
                Snackbar.make(view, "Please enter name, quantity, and price of item.", Snackbar.LENGTH_LONG).show();
            } else {
                nameEditable.clear();
                qtyEditable.clear();
                priceEditable.clear();

                int qty = Integer.parseInt(qtyString);
                double price = Double.parseDouble(priceString);

                listener.onReceiptAddedItem(name, qty, price, ReceiptAddItemFragment.this);
            }

        });

        this.binding.receiptAddItemRmButton.setOnClickListener((clickedView) -> {

            Editable nameEditable = binding.receiptAddItemNameEditText.getText();
            String name = nameEditable.toString();

            Editable qtyEditable = binding.receiptAddItemQtyEditText.getText();
            String qtyString = qtyEditable.toString();

            Editable priceEditable = binding.receiptAddItemPriceEditText.getEditableText();

            if (name.equals("")) {
                Log.d("Housemate Manager", "empty field detected");
                Snackbar.make(view, "Please enter name of item.", Snackbar.LENGTH_LONG).show();
            } else {
                nameEditable.clear();
                qtyEditable.clear();
                priceEditable.clear();

                int qty;

                if (qtyString.equals(""))
                    qty = -1;
                else
                    qty = Integer.parseInt(qtyString);

                listener.onReceiptRemovedItem(name, qty, ReceiptAddItemFragment.this);
            }
        });

        this.binding.receiptAddItemDoneButton.setOnClickListener((clickedView) -> listener.onReceiptItemsDone(ReceiptAddItemFragment.this));
    }

    public void updateDisplay(Receipt receipt) {
        String s = receipt.toString();
        this.binding.receiptAddItemTextView.setText(s);

        // Autocomplete view
        Map<String, ReceiptItem> itemMap = receipt.getItems();
        List<ReceiptItem> items = new ArrayList<>(itemMap.values());
        AutoCompleteTextView nameEditText = this.binding.receiptAddItemNameEditText;
        ArrayAdapter<ReceiptItem> adapter = new ArrayAdapter<ReceiptItem>(this.getContext(),
                android.R.layout.simple_list_item_1, items);
        nameEditText.setAdapter(adapter);
    }

    @Override
    public void noSuchItemError() {
        Snackbar.make(this.binding.getRoot(), "No such item. Please provide item from the list.", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void emptyReceiptError() {
        Snackbar.make(this.binding.getRoot(), "Please add at least one item.", Snackbar.LENGTH_LONG).show();
    }
}
