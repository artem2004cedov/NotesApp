package com.example.notesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.example.notesapp.Adapter.NotesListAdapter;
import com.example.notesapp.Datebese.RoomDB;
import com.example.notesapp.Model.Notes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recycler_home;
    private FloatingActionButton fad_add;
    private NotesListAdapter notesListAdapter;
    private List<Notes> notes = new ArrayList<>();
    private RoomDB database;
    SearchView searchView_home1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        searchView_home1 = findViewById(R.id.searchView_home1);

        recycler_home = findViewById(R.id.recycler_home);
        fad_add = findViewById(R.id.fad_add);

        database = RoomDB.getInstance(this);
        notes = database.mainDAO().getAll();

        updateRecycler(notes);

        fad_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),NotesTakerActivity.class);
                startActivityForResult(intent,101);
            }
        });

//        searchView_home1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filter(newText);
//                return true;
//            }
//        });
    }

//    Топ технологии
    private void filter(String newText) {
        List<Notes> filterList = new ArrayList<>();
        for (Notes singNotes : notes) {
            if (singNotes.getTitle().toLowerCase().contains(newText.toLowerCase())
            || singNotes.getNotes().toLowerCase().contains(newText.toLowerCase())) {
                filterList.add(singNotes);
            }
        }
        notesListAdapter.filterList(filterList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            if (resultCode  == Activity.RESULT_OK) {
                Notes new_notes = (Notes) data.getSerializableExtra("note");
                database.mainDAO().insert(new_notes);
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
//                ошибка                 notesList.add(roomDB.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == 102) {
            if (requestCode == Activity.RESULT_OK) {
                Notes new_Notes = (Notes) data.getSerializableExtra("note");
                database.mainDAO().update(new_Notes.getID(), new_Notes.getTitle(),new_Notes.getNotes());
                notes.clear();
                notes.addAll(database.mainDAO().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void updateRecycler(List<Notes> notesList) {
        recycler_home.setHasFixedSize(true);
        recycler_home.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesListAdapter = new NotesListAdapter(getApplicationContext(),notesList,notesClickList);
        recycler_home.setAdapter(notesListAdapter);
    }

    private final NotesClickList notesClickList = new NotesClickList() {
        @Override
        public void onClick(Notes notes) {
            Intent intent = new Intent(MainActivity.this,NotesTakerActivity.class);
            intent.putExtra("old_note", notes);
            startActivityForResult(intent,102);
        }

        @Override
        public void onLongClick(Notes notes, CardView cardView) {

        }
    };
}