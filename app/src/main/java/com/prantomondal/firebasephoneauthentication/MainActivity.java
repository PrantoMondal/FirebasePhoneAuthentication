package com.prantomondal.firebasephoneauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.google.firebase.auth.PhoneAuthProvider;
import com.prantomondal.firebasephoneauthentication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    //biewbinding
    private ActivityMainBinding binding;
    //if code send failed ,will used to resend code
    private PhoneAuthProvider .ForceResendingToken forceResendingToken;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private String mVerificationId; //will hold OTP/Verification code

    private static final String TAG = "MAIN_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}