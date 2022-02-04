package com.example.notesapp;

import androidx.cardview.widget.CardView;

import com.example.notesapp.Model.Notes;

public interface NotesClickList {
    void onClick(Notes notes);
    void onLongClick(Notes notes, CardView cardView);
}
