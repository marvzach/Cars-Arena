package com.marvin.cararenaa.ui;

import android.graphics.Typeface;
import android.os.Bundle;


import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.marvin.cararenaa.ui.ProfileActivity;
import com.marvin.cararenaa.R;
import com.marvin.cararenaa.SavedCarDetailsActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.findbutton) Button mFindRestaurantsButton;
    @BindView(R.id.appNameTextView) TextView mAppNameTextView;
    @BindView(R.id.savedcarsButton) Button mSavedcarsButton;
    @BindView(R.id.editText) EditText mlocation;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    getSupportActionBar().setTitle("Welcome, " + user.getDisplayName() + "!");
                }else {

                }
            }
        };

        Typeface caviarFont = Typeface.createFromAsset(getAssets(), "fonts/CaviarDreams.ttf");
        mAppNameTextView.setTypeface(caviarFont);
        mFindRestaurantsButton.setOnClickListener(this);
        mSavedcarsButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        if(v == mFindRestaurantsButton) {
            String build = mlocation.getText().toString();
            Toast.makeText(MainActivity.this, "searching ..", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, CararenaListActivity.class);
            intent.putExtra("build", build);
            startActivity(intent);
        }
        if (v == mSavedcarsButton) {
            Intent intent = new Intent(MainActivity.this, SavedCarDetailsActivity.class);
            startActivity(intent);
        }
    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == R.id.action_logout){
            logout();
            return true;
        }
        if(id == R.id.action_viewsaved){
            viewsaved();
            return true;
        }
        if(id == R.id.action_editprofile){
            edittprofile();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


    public void viewsaved() {

        Intent intent = new Intent(MainActivity.this, SavedCarDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }
    public void edittprofile() {

        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }



    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
    @Override
    public void onResume(){
        super.onResume();
        mAuth.addAuthStateListener(mAuthListener);
    }
}
