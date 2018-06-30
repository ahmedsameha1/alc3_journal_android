package com.ahmedsameha1.journal;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity implements EntriesAdapter.ItemClickListener {
    public static final String USER_EMAIL = "user_email";

    private RecyclerView recyclerView;
    private AppDatabase appDatabase;
    private String user_email;
    private EntriesAdapter entriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_email = getIntent().getStringExtra(USER_EMAIL);
        appDatabase = AppDatabase.getsInstance(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        entriesAdapter = new EntriesAdapter(this);
        recyclerView.setAdapter(entriesAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                AppExecutor.getsInstance().getExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        appDatabase.entryDao().delete(entriesAdapter.getEntries().get(viewHolder.getAdapterPosition()));
                    }
                });
            }
        }).attachToRecyclerView(recyclerView);
        getEntries();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEntryActivity.class);
                intent.putExtra(MainActivity.USER_EMAIL, user_email);
                startActivity(intent);
            }
        });
    }

    private void getEntries() {
        LiveData<List<Entry>> liveData = appDatabase.entryDao().getEntries(user_email);
        liveData.observe(this, new Observer<List<Entry>>() {
            @Override
            public void onChanged(@Nullable List<Entry> entries) {
                entriesAdapter.setEntries(entries);
            }
        });
    }


    @Override
    public void itemClick(int position) {
        Intent intent = new Intent(this, EditEntryActivity.class);
        intent.putExtra(EditEntryActivity.ENTRY_ID, entriesAdapter.getEntries().get(position).getId());
        startActivity(intent);
    }
}
