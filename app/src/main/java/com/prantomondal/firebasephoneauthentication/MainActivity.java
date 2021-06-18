package com.prantomondal.firebasephoneauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.prantomondal.firebasephoneauthentication.databinding.ActivityMainBinding;

import java.util.concurrent.TimeUnit;
//.....................................27.48.....................................

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
                //verified without needing to send or enter a verification code
                // 2 - Auto retrieval On some devices Google play service can automatically
                // detect the incoming verification SMS and perform verification without user action

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                //This callback is invoked an invalid request for verification is made
                //for instance if the phone number format is not valid...

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                //The SMS verification code has been sent to the provided phone number,we
                //now need to ask the user to enter the code and then construct a credential
                //by combining the code with a verification ID.
            }
        };

        binding.phoneContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = binding.phoneEt.getText().toString().trim();
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(MainActivity.this, "Please enter phone number...", Toast.LENGTH_SHORT).show();
                }
                else{
                    startPhoneNumberVerification(phone);
                }
            }
        });

        binding.resendCodeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = binding.phoneEt.getText().toString().trim();
                if(TextUtils.isEmpty(phone)){
                    Toast.makeText(MainActivity.this, "Please enter phone number...", Toast.LENGTH_SHORT).show();
                }else{
                    resendVerificationCode(phone, forceResendingToken);
                }
            }
        });

        //codeSubmissionBtn click : input verification code, validate,verify phone number with verification code
        binding.codeSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = binding.codeEt.getText().toString().trim();
                if(TextUtils.isEmpty(code)){
                    Toast.makeText(MainActivity.this, "Please enter verification code...", Toast.LENGTH_SHORT).show();
                }
                else{
                    verifyPhoneNumberWithCode(mVerificationId, code);
                }

            }
        });
    }

    private void startPhoneNumberVerification(String phone) {
        pd.setMessage("Verifying phone umber");
        pd.show();

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)// Phone number must be with country code
                .setPhoneNumber(phone)//the timeout unit
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)//Activity(for callback binding)
                .setCallbacks(mCallbacks)//OnVerificationStateChangedCallbacks
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void resendVerificationCode(String phone, PhoneAuthProvider.ForceResendingToken token) {
        pd.setMessage("Resending code");
        pd.show();

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phone) // Phone number must be with country code
                        .setTimeout(60L, TimeUnit.SECONDS)//the timeout unit
                        .setActivity(this)//Activity(for callback binding)
                        .setCallbacks(mCallbacks)//OnVerificationStateChangedCallbacks
                        .setForceResendingToken(token)
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }


    private void verifyPhoneNumberWithCode(String VerificationId, String code) {
        pd.setMessage("Verifying code");
        pd.show();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerificationId,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        pd.setMessage("Logging In");

        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        
                    }
                });
    }

}