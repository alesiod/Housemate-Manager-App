package edu.vassar.cmpu203.housematemanager.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.vassar.cmpu203.housematemanager.databinding.FragmentReceiptProcessBinding;
import edu.vassar.cmpu203.housematemanager.model.Housemate;
import edu.vassar.cmpu203.housematemanager.model.Receipt;
import edu.vassar.cmpu203.housematemanager.model.ReceiptItem;

public class ReceiptProcessFragment extends Fragment implements IReceiptProcessView{

    FragmentReceiptProcessBinding binding;
    IReceiptProcessView.Listener listener;
    List<Housemate> debtors;
    Housemate debtor;

    public ReceiptProcessFragment(List<Housemate> debtors, IReceiptProcessView.Listener listener) {
        this.listener = listener;
        this.debtors = debtors;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentReceiptProcessBinding.inflate(inflater);

        //dropdown menu
        Spinner housemateSpinner = this.binding.receiptDebtorSpinner;
        ArrayAdapter<Housemate> adapter = new ArrayAdapter<Housemate>(this.getContext(),
                android.R.layout.simple_spinner_item, debtors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        housemateSpinner.setAdapter(adapter);

        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        this.binding.receiptDebtorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                debtor = (Housemate) parent.getSelectedItem();
                listener.onReceiptDebtorSelected(ReceiptProcessFragment.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        this.binding.receiptOptInButton.setOnClickListener((clickedView) -> {
            Editable nameEditable = binding.receiptProcessNameEditText.getText();
            String itemName = nameEditable.toString();

            nameEditable.clear();
            listener.onReceiptItemOptedIn(itemName, debtor,this);
        });

        this.binding.receiptOptOutButton.setOnClickListener((clickedView) -> {
            Editable nameEditable = binding.receiptProcessNameEditText.getText();
            String itemName = nameEditable.toString();
            nameEditable.clear();
            listener.onReceiptItemOptedOut(itemName, debtor,this);
        });

        this.binding.receiptConfirmButton.setOnClickListener((clickedView) -> {
            Snackbar.make(view, "Receipt logged, items stocked, and housemates billed!", Snackbar.LENGTH_LONG).show();
            listener.onReceiptConfirmed();
        });
    }

    public void updateDisplay(Receipt receipt) {
        String s = receipt.toString(debtor);
        this.binding.receiptProcessTextView.setText(s);

        // Autocomplete view
        Map<String, ReceiptItem> itemMap = receipt.getItems();
        List<ReceiptItem> items = new ArrayList<>(itemMap.values());
        AutoCompleteTextView nameEditText = this.binding.receiptProcessNameEditText;
        ArrayAdapter<ReceiptItem> adapter = new ArrayAdapter<ReceiptItem>(this.getContext(),
                android.R.layout.simple_list_item_1, items);
        nameEditText.setAdapter(adapter);
    }

    public void noSuchItemError() {
        Snackbar.make(this.binding.getRoot(), "No such item. Please provide item from the list.", Snackbar.LENGTH_LONG).show();
    }

    public void noDebtorError() {
        Snackbar.make(this.binding.getRoot(), "Payer bought item no one wants. Payer auto opted-in", Snackbar.LENGTH_LONG).show();
    }

}

