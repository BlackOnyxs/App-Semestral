package com.example.appsemestral;

import  android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder> {

    private ArrayList<Articulos> articles;
    private OnItemClickListener listener;

    public ArticlesAdapter(ArrayList<Articulos> articles, Context context, OnItemClickListener listener) {
        this.articles = articles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticlesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        Articulos currentArticle = articles.get(position);

        holder.tvFileName.setText(currentArticle.getTitulo());
        holder.onItemClickListener(currentArticle, listener);

    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void add(Articulos article) {
        if (!containsFile(article.getTitulo(), articles)) {
            articles.add(article);
            notifyItemInserted(articles.size() - 1);
        } else if (containsFile(article.getTitulo(), articles)) {
            final int index = articles.indexOf(article);
            articles.set(index, article);
            notifyItemChanged(index);
        }
    }

    public ArrayList<Articulos> getArticles(){
        return this.articles;
    }
    public void delete(Articulos article) {
        if (articles.contains(article)) {
            final int index = articles.indexOf(article);
            articles.remove(index);
            notifyItemRemoved(index);
        }
    }

    public static boolean containsFile(String title, ArrayList<Articulos> articles) {
        boolean contains = false;
        for (Articulos a :
                articles) {
            if (title.equals(a.getTitulo())) {
                contains = true;
                break;
            }
        }
        return contains;
    }

    static class ArticlesViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView tvFileName;

        public ArticlesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFileName = itemView.findViewById(R.id.tv_fileName);
            view = itemView;
        }

        void onItemClickListener(final Articulos article, final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(article);
                }
            });

            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongClickListener(article);
                    return true;
                }
            });
        }
    }
}
