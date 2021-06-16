package com.prantomondal.firebasephoneauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
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

    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.phoneLl.setVisibility(View.VISIBLE); //show phone layout
        binding.codeLl.setVisibility(View.GONE); //hint code layout

        firebaseAuth = FirebaseAuth.getInstance();

        //init progress dialog
        pd = new ProgressDialog(this);
        pd.setTitle("Please wait...");
        pd.setCanceledOnTouchOutside(false);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                //this callback will be invoked in two situations
                //1- Instant verification. In some cases the phone number can be instantly
                //verified without needing to send or enter a verification code.

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

            }
        };
    }
}