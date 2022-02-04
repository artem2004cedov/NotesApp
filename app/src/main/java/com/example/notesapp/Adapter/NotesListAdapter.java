package com.example.notesapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.Model.Notes;
import com.example.notesapp.NotesClickList;
import com.example.notesapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapter extends RecyclerView.Adapter<NotesListAdapter.ViewHolder> {

    private Context context;
    private List<Notes> notesList;
    private NotesClickList notesClickList;

    public NotesListAdapter(Context context, List<Notes> notesList, NotesClickList notesClickList) {
        this.context = context;
        this.notesList = notesList;
        this.notesClickList = notesClickList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView_title.setText(notesList.get(position).getTitle());
        holder.textView_title.setSelected(true);

        holder.textView_notes.setText(notesList.get(position).getNotes());

        holder.textView_date.setText(notesList.get(position).getDate());
        holder.textView_date.setSelected(true);

        if (notesList.get(position).isPinned()) {
            holder.imageView_Pin.setImageResource(R.drawable.addicon);
        } else {
            holder.imageView_Pin.setImageResource(0);
        }

        int colorCode = getRandomColor();
        holder.notes_container.setCardBackgroundColor(holder.itemView.getResources().getColor(colorCode,null));

        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notesClickList.onClick(notesList.get(holder.getAdapterPosition()));
            }
        });

        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                notesClickList.onLongClick(notesList.get(holder.getAdapterPosition()),holder.notes_container);
                return true;
            }
        });
    }

//    Топ технолиги
    public void filterList(List<Notes> filterList) {
        notesList = filterList;
        notifyDataSetChanged();
    }

    private int getRandomColor() {
        List<Integer> colorList = new ArrayList<>();
        colorList.add(R.color.color1);
        colorList.add(R.color.color2);
        colorList.add(R.color.color3);
        colorList.add(R.color.color4);
        colorList.add(R.color.color5);

        Random random = new Random();
        int randomColor = random.nextInt(colorList.size());

        return colorList.get(randomColor);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView_title, textView_notes, textView_date;
        public CardView notes_container;
        public ImageView imageView_Pin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_title = itemView.findViewById(R.id.textView_title);
            textView_notes = itemView.findViewById(R.id.textView_notes);
            textView_date = itemView.findViewById(R.id.textView_date);
            notes_container = itemView.findViewById(R.id.notes_container);
            imageView_Pin = itemView.findViewById(R.id.imageView_Pin);

        }
    }
}
