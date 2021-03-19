package com.example.lesson_411.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.example.lesson_411.App;
import com.example.lesson_411.R;
import com.example.lesson_411.models.NoteModel;
import com.example.lesson_411.ui.interfaces.ItemClickListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.model.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> implements Filterable {
    private final ArrayList<NoteModel> list = new ArrayList<>();
    private ArrayList<DocumentSnapshot> documentSnapshotArrayList = new ArrayList<>();
    private ArrayList<NoteModel> searchList = new ArrayList<>();
    private ItemClickListener listener;
    private Context context;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static NoteAdapter KEY = null;

    public NoteAdapter(Context context) {
        this.context = context;
        list.addAll(App.getDataBase().noteModelDao().getAll());
        searchList.addAll(list);
        db.collection("note").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                documentSnapshotArrayList.addAll(Objects.requireNonNull(task.getResult()).getDocuments());
            }
        });
    }

    public ArrayList<NoteModel> getSearchList() {
        return searchList;
    }

    public static void setKEY(Context context) {
        KEY = new NoteAdapter(context);
    }

    public ArrayList<DocumentSnapshot> getDocumentSnapshotArrayList() {
        return documentSnapshotArrayList;
    }

    public static NoteAdapter getKEY() {
        return KEY;
    }

    public ArrayList<NoteModel> getList() {
        return list;
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
        NoteModel model = list.get(position);
        holder.onBind(model);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void getList(int position, NoteModel noteModel) {
        list.set(position, noteModel);
        notifyDataSetChanged();
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

    private Filter search = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<NoteModel> list = new ArrayList<>();
            if (constraint == null || constraint.length() == 0)
                documentSnapshotArrayList.forEach(documentSnapshot -> {
                    NoteModel model = documentSnapshot.toObject(NoteModel.class);
                    list.add(model);
                });
            else {
                String txt = constraint.toString().toLowerCase().trim();
                for (NoteModel i : searchList) {
                    if (i.getTitle().toLowerCase().contains(txt)) list.add(i);
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = list;
            return filterResults;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public NoteModel getItem(int position) {
        return list.get(position);
    }

    public void remove(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public Filter getFilter() {
        return search;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtTittle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTittle = itemView.findViewById(R.id.txtTitle);
//            txtTime = itemView.findViewById(R.id.txtTimeForHomeFragment);
            itemView.setOnClickListener(v -> listener.onItemClick(getAdapterPosition(),
                    documentSnapshotArrayList.get(getAdapterPosition()).getId()));
            itemView.setOnLongClickListener(v -> {
                new AlertDialog.Builder(context).setMessage("Are you sure about that!?").setNegativeButton("No...", null)
                        .setPositiveButton("Yes", (dialog, which) -> {
                            db.collection("note").document(documentSnapshotArrayList.get(getAdapterPosition()).
                                    getId()).delete().addOnCompleteListener(task -> {
                                if (task.isSuccessful())
                                    App.getDataBase().noteModelDao().delete(list.get(getAdapterPosition()));
                            });
                        }).create().show();
                return false;
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