package com.ahmedsameha1.journal;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddEntryActivity extends AppCompatActivity {

    private EditText entry_text;
    private AppDatabase appDatabase;
    private String user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_entry);
        entry_text = findViewById(R.id.entry_text);
        appDatabase = AppDatabase.getsInstance(this);
        user_email = getIntent().getStringExtra(MainActivity.USER_EMAIL);
        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( entry_text.getText().length() == 0 ) {
                    Toast.makeText(AddEntryActivity.this, "Your entry is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    AppExecutor.getsInstance().getExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            User user = appDatabase.userDao().getUserByEmail(user_email);
                            if (user == null ) {
                                appDatabase.userDao().insert(new User(user_email));
                            }
                            appDatabase.entryDao().insert(new Entry(entry_text.getText().toString(), user_email));
                        }
                    });
                    finish();
                }
            }
        });
    }
}
