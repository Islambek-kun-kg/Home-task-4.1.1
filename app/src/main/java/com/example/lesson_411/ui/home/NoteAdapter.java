package com.example.lesson_411.ui.home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson_411.R;
import com.example.lesson_411.models.NoteModel;
import com.example.lesson_411.ui.interfaces.ItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private ArrayList<NoteModel> list;
    private ItemClickListener listener;

    public NoteAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position % 2 == 0) holder.itemView.setBackgroundColor(Color.GREEN);
        else holder.itemView.setBackgroundColor(Color.RED);
        holder.onBind(list.get(position));
    }

    public void getList(int position, NoteModel noteModel) {
        list.set(position, noteModel);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(NoteModel noteModel) {
        list.add(0, noteModel);
        notifyItemChanged(list.indexOf(0));
    }

    public void setList(List<NoteModel> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void sortList(List<NoteModel> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public NoteModel getItem(int position) {
        return list.get(position);
    }

    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTittle, txtTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTittle = itemView.findViewById(R.id.txtTitle);
            txtTime = itemView.findViewById(R.id.txtTimeForHomeFragment);
            itemView.setOnClickListener(v -> listener.onItemClick(getAdapterPosition()));
            itemView.setOnLongClickListener(v -> {
                listener.onLongClick(getAdapterPosition());
                return true;
            });
        }

        public void onBind(NoteModel noteModel) {
            txtTittle.setText(noteModel.getTitle());
        }
    }

    public void setClickListener(ItemClickListener listener) {
        this.listener = listener;
    }
}