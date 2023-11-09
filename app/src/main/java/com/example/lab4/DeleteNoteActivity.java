package com.example.lab4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class DeleteNoteActivity extends AppCompatActivity {

    ArrayList<String> allNotesListItems = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_note);

        this.notesList = findViewById(R.id.notesList);
        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.allNotesListItems);
        this.notesList.setAdapter(adapter);

        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapter.getItem(i);
                allNotesListItems.remove(selectedItem);
                adapter.notifyDataSetChanged();
                saveNotes();
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPref = this.getSharedPreferences(Constants.NOTES_FILE, this.MODE_PRIVATE);
        Set<String> savedSet = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, null);

        if(savedSet != null) {
            this.allNotesListItems.clear();
            this.allNotesListItems.addAll(savedSet);
            this.adapter.notifyDataSetChanged();
        }
    }

    private void saveNotes() {
        SharedPreferences sharedPref = this.getSharedPreferences(Constants.NOTES_FILE, this.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putStringSet(Constants.NOTES_ARRAY_KEY, new HashSet<>(this.allNotesListItems));
        editor.apply();
    }
}
