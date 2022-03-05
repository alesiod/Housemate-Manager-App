package edu.vassar.cmpu203.housematemanager.view;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import edu.vassar.cmpu203.housematemanager.databinding.FragmentReceiptCreateBinding;
import edu.vassar.cmpu203.housematemanager.model.House;
import edu.vassar.cmpu203.housematemanager.model.Housemate;

public class ReceiptCreateFragment extends Fragment implements IReceiptCreateView {


    FragmentReceiptCreateBinding binding;
    Listener listener;
    List<Housemate> debtors;

    public ReceiptCreateFragment(List<Housemate> debtors, IReceiptCreateView.Listener listener) {
        this.listener = listener;
        this.debtors = debtors;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentReceiptCreateBinding.inflate(inflater);

        //dropdown menu
        Context context = this.getActivity().getApplicationContext();
        Spinner housemateSpinner = this.binding.receiptPayerSpinner;
        ArrayAdapter<Housemate> adapter = new ArrayAdapter<Housemate>(context,
                android.R.layout.simple_spinner_item, debtors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        housemateSpinner.setAdapter(adapter);

        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        this.binding.receiptCreateButton.setOnClickListener((clickedView) -> {
            if (debtors.size() == 0) {
                Snackbar.make(view, "Please add at least one housemate.", Snackbar.LENGTH_LONG).show();
            } else {
                Housemate payer = (Housemate) this.binding.receiptPayerSpinner.getSelectedItem();
                listener.onSelectPayer(payer);
            }
        });
    }
}
