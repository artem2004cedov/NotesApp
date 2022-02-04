package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.notesapp.Model.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesTakerActivity extends AppCompatActivity {
    private ImageView imageView_Save;
    private EditText editText_Title,editText_notes;
    private Notes notes;
    private boolean isOldNotes = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);

        imageView_Save = findViewById(R.id.imageView_Save);
        editText_Title = findViewById(R.id.editText_Title);
        editText_notes = findViewById(R.id.editText_notes);

        notes = new Notes();
        try {
            notes = (Notes) getIntent().getSerializableExtra("old_note");
            editText_Title.setText(notes.getTitle());
            editText_notes.setText(notes.getNotes());
            isOldNotes = true;
        }catch (Exception e) {
            e.printStackTrace();
        }

        imageView_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editText_Title.getText().toString();
                String note = editText_notes.getText().toString();

                if (note.isEmpty()) {
                    Toast.makeText(NotesTakerActivity.this, "Добавьте заметку ", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleDateFormat format = new SimpleDateFormat("EEE, d MMM yyy HH:mm a");
                Date date = new Date();

                if (!isOldNotes) {
                    notes = new Notes();
                }

                notes.setTitle(title);
                notes.setNotes(note);
                notes.setDate(format.format(date));

                Intent intent = new Intent();
                intent.putExtra("note",notes);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }
        });
    }
}