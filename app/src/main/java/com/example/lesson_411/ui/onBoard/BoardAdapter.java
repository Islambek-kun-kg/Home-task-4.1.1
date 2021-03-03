package com.example.lesson_411.ui.onBoard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.lesson_411.R;
import com.example.lesson_411.ui.home.NoteFragment;

import org.w3c.dom.Text;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.BoardHolder> {
    private final String[] titles = new String[]{"Fast", "Free", "Powerful"};
    private final String[] description = new String[]{"Скорость", "Свободный", "Полезный"};
    private final int[] images = new int[]{R.raw.rocket,
            R.raw.signature, R.raw.pacman_loading};
    private onStartClickListener listener;

    public interface onStartClickListener {
        void onClick();
    }

    public void setListener(onStartClickListener listener) {
        this.listener = listener;
    }

    public BoardAdapter() {
    }

    @NonNull
    @Override
    public BoardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_board, parent, false);
        return new BoardHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class BoardHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle, descTitle;
        private Button btnStart;
        private LottieAnimationView lottieView;

        public BoardHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitleForBoard);
            lottieView = itemView.findViewById(R.id.lottieView);
            descTitle = itemView.findViewById(R.id.txtDescForBoard);
            btnStart = itemView.findViewById(R.id.btnStartForBoard);
            btnStart.setOnClickListener(v -> {
                listener.onClick();
            });
        }

        public void onBind(int position) {
            lottieView.setAnimation(images[position]);
            txtTitle.setText(titles[position]);
            descTitle.setText(description[position]);
            if (position == 2) btnStart.setVisibility(View.VISIBLE);
            else btnStart.setVisibility(View.INVISIBLE);
        }
    }
}