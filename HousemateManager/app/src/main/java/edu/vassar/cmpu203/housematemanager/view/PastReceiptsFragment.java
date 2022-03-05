package edu.vassar.cmpu203.housematemanager.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.vassar.cmpu203.housematemanager.R;
import edu.vassar.cmpu203.housematemanager.controller.ContextActivity;
import edu.vassar.cmpu203.housematemanager.databinding.FragmentPastReceiptsBinding;


public class PastReceiptsFragment extends Fragment implements IPastReceiptsView{

    FragmentPastReceiptsBinding binding;
    IPastReceiptsView.Listener listener;
    Map<String, String> receiptLog;
    String date;

    public PastReceiptsFragment(Map<String, String> receiptLog, IPastReceiptsView.Listener listener) {
        this.listener = listener;
        this.receiptLog = receiptLog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FragmentPastReceiptsBinding.inflate(inflater);

        //dropdown menu
        List<String> receiptDates = new ArrayList<>(receiptLog.keySet());
        Spinner housemateSpinner = this.binding.pastReceiptSpinner;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getContext(),
                android.R.layout.simple_spinner_item, receiptDates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        housemateSpinner.setAdapter(adapter);

        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        this.binding.pastReceiptSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                date = (String) parent.getSelectedItem();
                listener.onPastReceiptSelected(PastReceiptsFragment.this);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }

    @Override
    public void updateDisplay() {
        String s = null;

        if (date != null) {
            s = receiptLog.get(date);
            this.binding.pastReceiptsTextView.setText(s);
        }
    }

}