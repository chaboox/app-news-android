package com.achour.newsapp.ui.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.achour.newsapp.data.models.NewsModel;
import com.achour.newsapp.databinding.NewsItemBinding;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private static final String TAG = "NewsAdapter";
    private List<NewsModel> mylist = new ArrayList<>();
    private Context mContext;

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //return new NewsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.client_item, parent, false));
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        NewsItemBinding binding = NewsItemBinding.inflate(layoutInflater, parent, false);
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        //populate views:
        holder.binding.dateTV.setText(mylist.get(position).getPublishedAt());
        holder.binding.titleTV.setText(mylist.get(position).getTitle());
        holder.binding.authorTV.setText(mylist.get(position).getAuthor());

        Glide.with(mContext)
                .load(mylist.get(position).getUrlToImage())

                //caches only the final image, after reducing the resolution
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.centerCropTransform())
                .into(holder.binding.categoryIV);

        holder.binding.cardView.setOnClickListener(view -> onItemClickListener.onItemClick(mylist.get(position)));

    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public void setList(Context context, List<NewsModel> mylist) {
        this.mylist = mylist;
        this.mContext = context;
        notifyDataSetChanged();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        private NewsItemBinding binding;

        NewsViewHolder(NewsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setOnItemClickListener(OnItemClickListenerInterface onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    OnItemClickListenerInterface onItemClickListener;

    public interface OnItemClickListenerInterface {
        void onItemClick(NewsModel newsModel);
    }

}