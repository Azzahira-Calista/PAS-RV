package com.example.pasrv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class AdapterRC extends RecyclerView.Adapter<AdapterRC.MyViewHolder> {

    private Context context;
    private List<Model> listMovie;
    private AdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName, tvReleaseDate;
        public ImageView imageList;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
            imageList = itemView.findViewById(R.id.imageList);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMovieSelected(listMovie.get(getAdapterPosition()));
                }
            });
        }
    }

    public AdapterRC(Context context, List<Model> listMovie, AdapterListener listener) {
        this.context = context;
        this.listMovie = listMovie;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AdapterRC.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        MyViewHolder myView = new MyViewHolder(item);

        item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onDataLongClicked(listMovie.get(myView.getAdapterPosition()));
                return true;
            }
        });
        return myView;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRC.MyViewHolder holder, int position) {
        final Model contact = this.listMovie.get(position);

        holder.tvName.setText(contact.getJudul());
        holder.tvReleaseDate.setText(contact.getReleaseDate());

        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500" + contact.getPoster()).into(holder.imageList);
    }

    @Override
    public int getItemCount() {
        return this.listMovie.size();
    }
    public interface AdapterListener{

        void onDataLongClicked(Model contact);

        void onMovieSelected(Model contact);
    }
}
