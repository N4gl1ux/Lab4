package com.example.lab4;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class AddNoteActivity extends AppCompatActivity {

    EditText editNote;
    EditText editTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        this.editNote = findViewById(R.id.editNote);
        this.editTitle = findViewById(R.id.editTitle);
    }

    public void onBtnSaveAndCloseClick(View view) {
        String noteToAdd = this.editNote.getText().toString();
        String noteTitleToAdd = this.editTitle.getText().toString();

        if (noteToAdd.trim().length() > 0 && noteTitleToAdd.trim().length() > 0) {
            SharedPreferences sharedPref = this.getSharedPreferences(Constants.NOTES_FILE, this.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();

            Set<String> savedSet = sharedPref.getStringSet(Constants.NOTES_ARRAY_KEY, null);
            Set<String> newSet = new HashSet<>();

            if (savedSet != null) {
                newSet.addAll(savedSet);
            }

            String combinedNote = "Title: " + noteTitleToAdd + "\n\nContent: " + noteToAdd;
            newSet.add(combinedNote);

            editor.putStringSet(Constants.NOTES_ARRAY_KEY, newSet);
            editor.apply();

            finish();
        }
        else if (noteToAdd.trim().length() == 0 && noteTitleToAdd.trim().length() > 0){

            Toast.makeText(this, "Please enter your note's Content!!!", Toast.LENGTH_SHORT).show();
        }
        else if (noteToAdd.trim().length() > 0 && noteTitleToAdd.trim().length() == 0){

            Toast.makeText(this, "Please enter your note's Title!!!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "Empty note can not be saved!!!", Toast.LENGTH_SHORT).show();

            finish();
        }
    }
}
