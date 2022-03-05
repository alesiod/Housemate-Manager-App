package edu.vassar.cmpu203.housematemanager.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import edu.vassar.cmpu203.housematemanager.controller.ControllerActivity;
import edu.vassar.cmpu203.housematemanager.databinding.FragmentChoreListBinding;
import edu.vassar.cmpu203.housematemanager.databinding.FragmentDebtsBinding;
import edu.vassar.cmpu203.housematemanager.model.House;
import edu.vassar.cmpu203.housematemanager.model.Housemate;

public class DebtsFragment  extends Fragment implements IDebtsView{

    FragmentDebtsBinding binding;
    IDebtsView.Listener listener;
    List<Housemate> debtors;
    Housemate debtor = new Housemate();
    // debtor must be initialized as not null or else there is big error
    // and app will crash if you go to debts screen before adding housemates to house
    Housemate creditor;
    House house;

    public DebtsFragment(List<Housemate> debtors, House house, IDebtsView.Listener listener) {
        this.listener=listener;
        this.debtors = debtors;
        this.house = house;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentDebtsBinding.inflate(inflater);

        //dropdown menu 1
        Context context = this.getActivity().getApplicationContext();
        Spinner debtorSpinner = this.binding.DebtorSpinner;
        ArrayAdapter<Housemate> adapter = new ArrayAdapter<Housemate>(context,
                android.R.layout.simple_spinner_item, debtors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        debtorSpinner.setAdapter(adapter);

        //dropdown menu 2
        Context context2 = this.getActivity().getApplicationContext();
        Spinner otherPersonSpinner = this.binding.OtherPersonSpinner;
        ArrayAdapter<Housemate> adapter2 = new ArrayAdapter<Housemate>(context2,
                android.R.layout.simple_spinner_item, debtors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        otherPersonSpinner.setAdapter(adapter2);

        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        this.binding.DebtorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                debtor = (Housemate) parent.getSelectedItem();
                listener.onDebtorSelected(DebtsFragment.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        this.binding.OtherPersonSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                creditor = (Housemate) parent.getSelectedItem();
                listener.onCreditorSelected(DebtsFragment.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        this.binding.AddDebtButton.setOnClickListener((clickedView) -> {
            Editable amountEditable = binding.debtAmountEditText.getText();
            String amountTxt = amountEditable.toString();
            double amount = Integer.parseInt(amountTxt);

            amountEditable.clear();
            listener.onAddedDebt(debtor, creditor, amount, this);
            this.updateDisplay(this.house);
        });

        this.binding.RmDebtButton.setOnClickListener((clickedView) -> {
            Editable amountEditable = binding.debtAmountEditText.getText();
            String amountTxt = amountEditable.toString();
            double amount = Integer.parseInt(amountTxt);
            amount = amount*-1; //because removing debt

            amountEditable.clear();
            listener.onRemovedDebt(debtor, creditor, amount, this);
            this.updateDisplay(house);
        });

    }

    @Override
    public void updateDisplay(House house) {
        Log.d("Housemate Manager", "Debts are updating display");

        String s = debtor.getDebtsStringForm();

        this.binding.debtTextView.setText(s);


    }

    @Override
    public void negativeAmountError() {
        Snackbar.make(this.binding.getRoot(), "Please enter a positive amount.", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void debtDNEError() {
        Snackbar.make(this.binding.getRoot(), "No such debt to be removed.", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void overdraftError(Housemate debtor, Housemate creditor, double tag) {
        Log.d("Housemate Manager", "an overdraft occurred");
        Snackbar.make(this.binding.getRoot(), creditor + " now owes " + tag + " to " + debtor , Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void samePersonError() {
        Log.d("Housemate Manager", "Debtor and Creditor were same");
        Snackbar.make(this.binding.getRoot(), "A housemate cannot owe themselves money!", Snackbar.LENGTH_LONG).show();
    }

}
