package com.example.lesson_411.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lesson_411.R;
import com.example.lesson_411.ui.ItemClickListener;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private ArrayList<String> list;
    private ItemClickListener listener;

    public NoteAdapter() {
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    public ArrayList<String> getList() {
        return list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(String txt) {
        list.add(txt);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtTittle;

        public ViewHolder(@NonNull View itemView, ItemClickListener listener) {
            super(itemView);
            txtTittle = itemView.findViewById(R.id.txtTitle);
            itemView.setOnClickListener(v -> {
                listener.onItemClick(getAdapterPosition(), list.get(getAdapterPosition()));
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(view.getRootView().getContext());
                    dialog.setTitle("Are you sure about that!?").setPositiveButton("No...", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int witch) {
                        }
                    }).setNegativeButton("Yes of course!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int witch) {
                            list.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            notifyItemRangeChanged(getAdapterPosition(), list.size());
                        }
                    }).show();
                    return true;
                }
            });
        }

        public void onBind(String s) {
            txtTittle.setText(s);
        }
    }

    public void setClickListener(ItemClickListener listener) {
        this.listener = listener;
    }
}