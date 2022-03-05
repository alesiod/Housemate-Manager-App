package edu.vassar.cmpu203.housematemanager.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import edu.vassar.cmpu203.housematemanager.databinding.FragmentAuthBinding;


public class AuthFragment extends Fragment implements IAuthView {

        private static final String IS_REGISTERED = "isRegistered";

        private final Listener listener;
        private FragmentAuthBinding binding;
        private boolean isRegistered = false;

        public AuthFragment(@NonNull Listener listener) {
            this.listener = listener;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            this.binding = FragmentAuthBinding.inflate(inflater);
            return this.binding.getRoot();
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//            super.onViewCreated(view, savedInstanceState);
//
//            if (savedInstanceState != null && savedInstanceState.getBoolean(IS_REGISTERED))
//                activateRegisteredConfig();

            this.binding.NewHouseButton.setOnClickListener( (clickedView) ->{
                String username = this.binding.houseNameEditText.getText().toString();
                String password = this.binding.HousePasswordEditText.getText().toString();
                AuthFragment.this.listener.onRegister(
                        username, password,AuthFragment.this);
            });

            this.binding.SignInButton.setOnClickListener( (clickedView) ->{
                String username = this.binding.houseNameEditText.getText().toString();
                String password = this.binding.HousePasswordEditText.getText().toString();
                AuthFragment.this.listener.onSigninAttempt(
                        username, password, AuthFragment.this);
            });
        }

        @Override
        public void onSaveInstanceState(@NonNull Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putBoolean(IS_REGISTERED, this.isRegistered);
        }

        @Override
        public void onRegisterSuccess() {
            activateRegisteredConfig();
            this.displayMessage("Registration Successful");
        }

        // prevent multiple registration attempts
        private void activateRegisteredConfig(){
            this.isRegistered = true;
            this.binding.NewHouseButton.setEnabled(false);
        }

        @Override
        public void onInvalidCredentials() {
            displayMessage("Invalid Credentials");
        }

        @Override
        public void onUserAlreadyExists() {
            displayMessage("House Already Exists!");
        }

        private void displayMessage(String msg){
            Snackbar.make(this.binding.getRoot(),
                    msg,
                    Snackbar.LENGTH_LONG)
                    .show();
        }


}

