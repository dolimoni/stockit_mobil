package org.besystem.stockit.stockit.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.besystem.stockit.stockit.Fragments.Login_Fragment;
import org.besystem.stockit.stockit.R;
import org.besystem.stockit.stockit.Services.Session;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SharedPreferences sp=getSharedPreferences("loginPref", Context.MODE_PRIVATE);
        String login = sp.getString("login", "");

        if(login.equals("true")){
            Intent l_intent = new Intent(LoginActivity.this, Customers.class);
            startActivity(l_intent);

            // close this activity
            LoginActivity.this.finish();
        }else{
            Fragment androidFragment = new Login_Fragment();
            this.setDefaultFragment(androidFragment);
        }

    }


    public static void replaceLoginFragment(){

    }

    // This method is used to set the default fragment that will be shown.
    private void setDefaultFragment(Fragment defaultFragment)
    {
        this.replaceFragment(defaultFragment);
    }

    // Replace current Fragment with the destination Fragment.
    public void replaceFragment(Fragment destFragment)
    {
        // First get FragmentManager object.
        FragmentManager fragmentManager = this.getSupportFragmentManager();

        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Replace the layout holder with the required Fragment object.
        fragmentTransaction.replace(R.id.frameContainer, destFragment);

        // Commit the Fragment replace action.
        fragmentTransaction.commit();
    }


}