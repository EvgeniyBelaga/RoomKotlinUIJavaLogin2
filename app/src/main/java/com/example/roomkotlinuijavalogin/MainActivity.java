package com.example.roomkotlinuijavalogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;


/**
 * @version V.2019.06.03_roomkotlinjavalogin
 *
 * todo: Commit message: [2019.06.03_15:26 Evgeniy] Done a,b. Fixed bug 'result = userDao.findByName(name)'
 *
 * Description: Used as prototype for other app
 *
 */

public class MainActivity extends AppCompatActivity {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // done_todo-0603_a: Add button to logcat the user table including PK (primary key)
    // done_todo-0603_b: Add auto-generate PK in user table (see commented code)
    // todo-0603_c: Use query fun 'findByName' to find User row match for login
    // todo-0603_d: Create query fun 'findUserPasswordMatch' use AND to retrieve the User object with this pair
    // todo-0603_e: Add to layout 2 editText & button to submit new user, add the user to table, logcat the whole table
    //
    // todo-0603_f: Add a new table called Session, it's columns are:
    //   1) Auto generated PK :int
    //   2) ProjectKey  :int
    //   3) QzKey       :int
    //   4) ReportKey   :int
    //   5) UserKey     :int
    // todo-0603_g: Add a session query:
    //   1) In layout add EditTExt+submit button horizontol linear layout
    //   2) Create query fun 'findSessionByUser' that logcats the Session row when given username
    //   3) When name entered in editText & submitted then show in logcat the user's session
    // todo-0603_h: Add a update to session table:
    //   1) Add EditTExt fields for Project, Qz,Report keys + submit button
    //   2) On every submit create a new session row in table for the current logged in user
    //
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    private UserViewModel mUserViewModel;

    final String SP_FILE_NAME= "SP";
    final String SP_BOOLEAN_KEY= "b";

    EditText etUserName;
    EditText etPassword;
    Button btnSubmit;
    Button btnLogOut;
    Button btnShowLog;
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
            public void onChanged(@Nullable final List<User> users) {
                // Update the cached copy of the words in the adapter.
                //adapter.setWords(words);
                Log.d("Login",""+ users.size());
                MyUtils.users= users;
                Log.d("Login","MyUtils.users.size"+MyUtils.users.size());
            }
        });
        //Log.d("Login",""+ mUserViewModel.getAllUsers().getValue().size());

        //User[] usersByName= mUserViewModel.findUserByNameViewModel("user1");
//        for(int i=0; i<MyUtils.usersByName.length; i++){
//            Log.d("Login", "MainActivity: UsersByName: "+MyUtils.usersByName[i].toString());
//        }
        //mUserViewModel.findUserByNameViewModel("user1");

//        MyAsync myAsync= new MyAsync();
//        myAsync.execute();
        mUserViewModel.findUserByNameViewModel("user1");

        initilizeOnSubmitButtonPress();

        initilizeOnLogautButtonPress();

        initilizeShowLogButton();
    }

    public void initilizeLayout(){
        etUserName= findViewById(R.id.etUserName);
        etPassword= findViewById(R.id.etPassword);
        btnSubmit= findViewById(R.id.btnSubmit);
        btnLogOut= findViewById(R.id.btnLogOut);
        btnShowLog= findViewById(R.id.btnShowLog);
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

    public void initilizeShowLogButton(){
        btnShowLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i=0; i<MyUtils.users.size(); i++){
                    Log.d("Login", "MainActivity.ShowLog: "+MyUtils.users.get(i).toString());
                }

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

    public class MyAsync extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
//            MyUtils.usersByName=mUserViewModel.findUserByNameViewModel("user2");
//            for(int i=0; i<MyUtils.usersByName.length; i++){
//                Log.d("Login", "MainActivity.MyAsync: UserByName: "+ MyUtils.usersByName[i].toString());
//            }

            return null;
        }
    }
}
