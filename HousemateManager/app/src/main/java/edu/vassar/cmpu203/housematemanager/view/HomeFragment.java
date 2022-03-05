package edu.vassar.cmpu203.housematemanager.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.vassar.cmpu203.housematemanager.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment implements IHomeView{

    FragmentHomeBinding binding;
    Listener listener;

    public HomeFragment(Listener listener) {this.listener = listener;}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.binding = FragmentHomeBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Inventory Button
        this.binding.homeInventoryButton.setOnClickListener((clickedView) -> this.listener.onViewInventory());

        // Shopping List Button
        this.binding.homeShoppingListButton.setOnClickListener((clickedView) -> this.listener.onViewShoppingList());

        // Receipt Button
        this.binding.homeReceiptButton.setOnClickListener((clickedView) -> this.listener.onViewReceipt());

        // Chore List Button
        this.binding.homeChoreListButton.setOnClickListener((clickedView) -> this.listener.onViewChoreList());

        // Housemate Button
        this.binding.homeHouseButton.setOnClickListener((clickedView) -> this.listener.onViewHousemates());

        // Debts Button
        this.binding.homeDebtsButton.setOnClickListener((clickedView) -> this.listener.onViewDebts());

        // Past Receipts Button
        this.binding.homePastReceiptsButton.setOnClickListener((clickedView) -> this.listener.onViewPastReceipts());



    }
}