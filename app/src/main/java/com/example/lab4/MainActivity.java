package com.example.lab4;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> allNotesListItems = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ListView notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.notesList = findViewById(R.id.notesList);
        this.adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.allNotesListItems);
        this.notesList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_note) {

            Intent i = new Intent(this, AddNoteActivity.class);
            startActivity(i);
            return true;
        }
        else if (item.getItemId() == R.id.remove_note) {

            if(notesList.getCount() == 0){

                Toast.makeText(this, "There is no Notes to delete!!!", Toast.LENGTH_SHORT).show();
            }
            else {

                Intent i = new Intent(this, DeleteNoteActivity.class);
                startActivity(i);
            }
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }
}