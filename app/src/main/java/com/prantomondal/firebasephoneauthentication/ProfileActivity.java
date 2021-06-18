package com.prantomondal.firebasephoneauthentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.prantomondal.firebasephoneauthentication.databinding.ActivityMainBinding;
import com.prantomondal.firebasephoneauthentication.databinding.ActivityProfileBinding;

public class ProfileActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;

    private ActivityProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        checkUserStatus();

        //log out button clicked and logout user
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                checkUserStatus();

            }
        });
    }

    private void checkUserStatus() {
        //get current user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null){
            ////user is logged in
            String phone = firebaseUser.getPhoneNumber();
            binding.phoneTv.setText(phone);


        }
        else{
            //user is not logged in
            finish();
        }
    }
}