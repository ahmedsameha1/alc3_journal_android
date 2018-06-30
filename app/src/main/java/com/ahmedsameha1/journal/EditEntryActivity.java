package com.ahmedsameha1.journal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditEntryActivity extends AppCompatActivity {
    public static final String ENTRY_ID = "entry_id";
    private int entry_id;
    private AppDatabase appDatabase;
    private EditText entry_text;
    private Entry entry;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entry);
        entry_id = getIntent().getIntExtra(ENTRY_ID, 0);
        entry_text = findViewById(R.id.entry_text);
        appDatabase = AppDatabase.getsInstance(this);
        FloatingActionButton fab =  findViewById(R.id.fab);
        LiveData<Entry> liveData = appDatabase.entryDao().getEntryById(entry_id);
        liveData.observe(this, new Observer<Entry>() {
            @Override
            public void onChanged(@Nullable Entry entry) {
                if ( entry == null ) return;
                EditEntryActivity.this.entry = entry;
                entry_text.setText(entry.getText());
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( entry_text.getText().length() == 0 ) {
                    Toast.makeText(EditEntryActivity.this, "Your entry is empty!", Toast.LENGTH_SHORT).show();
                } else {
                    AppExecutor.getsInstance().getExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            entry.setText(entry_text.getText().toString());
                            appDatabase.entryDao().update(entry);
                        }
                    });
                    finish();
                }
            }
        });
    }

}
