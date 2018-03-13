package com.anonymous.shelves;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeActivity extends AppCompatActivity {

    Toolbar mToolbar;
    SharedPreferences current_user;
    SharedPreferences.Editor editor;

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        current_user = this.getSharedPreferences(getString(R.string.shared_preference_name), MODE_PRIVATE);
        editor = current_user.edit();

        mToolbar = findViewById(R.id.home_act_tool);
        setSupportActionBar(mToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_signout, menu);
        MenuItem menuItem = menu.findItem(R.id.sign_out_option);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.sign_out_option){

            editor.putString(getString(R.string.shared_preference_username), "");
            editor.commit();

            AuthUI.getInstance()
                    .signOut(HomeActivity.this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {

                            editor.putBoolean(getString(R.string.shared_preference_login_boolean), true);
                            editor.commit();

                            Intent toMain = new Intent(HomeActivity.this, MainActivity.class);
                            toMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(toMain);
                        }
                    });

        }

        return true;
    }
}