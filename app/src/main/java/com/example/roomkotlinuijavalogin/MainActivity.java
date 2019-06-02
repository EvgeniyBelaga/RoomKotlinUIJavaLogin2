package com.example.roomkotlinuijavalogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserViewModel mUserViewModel;

    final String SP_FILE_NAME= "SP";
    final String SP_BOOLEAN_KEY= "b";

    EditText etUserName;
    EditText etPassword;
    Button btnSubmit;
    Button btnLogOut;
    //boolean isLogin





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences prefs = getSharedPreferences(SP_FILE_NAME, MODE_PRIVATE);
        //String restoredText = prefs.getString("text", null);
        MyUtils.isLogin= prefs.getBoolean(SP_BOOLEAN_KEY, false);
        Log.d("LoginIs", "onCreate: isLogin: "+MyUtils.isLogin);

        initilizeLayout();

        initilizeLayoutDisplay();

        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mUserViewModel.getAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> words) {
                // Update the cached copy of the words in the adapter.
                //adapter.setWords(words);
                Log.d("Login",""+ words.size());
                MyUtils.users= words;
                Log.d("Login","MyUtils.users.size"+MyUtils.users.size());
            }
        });
        //Log.d("Login",""+ mUserViewModel.getAllUsers().getValue().size());

        initilizeOnSubmitButtonPress();

        initilizeOnLogautButtonPress();
    }

    public void initilizeLayout(){
        etUserName= findViewById(R.id.etUserName);
        etPassword= findViewById(R.id.etPassword);
        btnSubmit= findViewById(R.id.btnSubmit);
        btnLogOut= findViewById(R.id.btnLogOut);
    }

    public void initilizeLayoutDisplay(){
        if(MyUtils.isLogin){
            etUserName.setVisibility(View.GONE);
            etPassword.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.GONE);
            btnLogOut.setVisibility(View.VISIBLE);
        }
        else{
            etUserName.setVisibility(View.VISIBLE);
            etPassword.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.VISIBLE);
            btnLogOut.setVisibility(View.GONE);

            etUserName.setText("");
            etPassword.setText("");
        }
    }

    public void initilizeOnSubmitButtonPress(){
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyUtils.mCheckedUser= new User(etUserName.getText().toString(), etPassword.getText().toString());
                mUserViewModel.loginUser(MyUtils.mCheckedUser);
                initilizeLayoutDisplay();
            }
        });
    }

    public void initilizeOnLogautButtonPress(){
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyUtils.isLogin= false;
                initilizeLayoutDisplay();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("LoginIs", "onDestroy: isLogin: "+MyUtils.isLogin);

        SharedPreferences sp= getSharedPreferences(SP_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.putBoolean(SP_BOOLEAN_KEY, MyUtils.isLogin);
        editor.commit();

    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d("LoginIs", "onStop: isLogin: "+MyUtils.isLogin);

        SharedPreferences sp= getSharedPreferences(SP_FILE_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor= sp.edit();
        editor.putBoolean(SP_BOOLEAN_KEY, MyUtils.isLogin);
        editor.commit();
    }
}
