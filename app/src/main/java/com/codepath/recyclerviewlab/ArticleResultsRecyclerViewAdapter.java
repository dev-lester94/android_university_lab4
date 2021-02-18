package com.codepath.recyclerviewlab;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.recyclerviewlab.models.Article;

import java.util.List;
import android.os.Handler;

public class ArticleResultsRecyclerViewAdapter extends RecyclerView.Adapter<ArticleResultsRecyclerViewAdapter.ArticleViewHolder> {

    private List<Article> articles;
    private Context context;
    private String savedQuery = "";



    public ArticleResultsRecyclerViewAdapter(List<Article> articles, String savedQuery) {
        this.articles = articles;
        this.savedQuery = savedQuery;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        Log.d("ArticleViewHolder", "onCreataeViewHolder");

        View view = LayoutInflater.from(context)
                .inflate(R.layout.fragment_article_result_first_page, parent, false);
        return new ArticleViewHolder(view);

    }
/*
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Article article = articles.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_LOADING:
                LoadingViewHolder vh1 = (LoadingViewHolder) holder;
                vh1.bind(article);
                Log.d("LoadingViewHolder", "loading");
                break;
            case VIEW_TYPE_ARTICLE:
                ArticleViewHolder vh2 = (ArticleViewHolder) holder;
                vh2.bind(article);
                Log.d("ArticleViewHolder", "article");
                break;
                //configureViewHolder2(vh2, position);

        }
    }*/


    /*private void configureViewHolder2(ArticleViewHolder vh2, int position) {
        Article article = articles.get(position);
        if(article!=null){

        }
    }*/

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        Log.i("position", "postion: " + position );
        holder.bind(article, position);
    }
    /*
    @Override
    public int getItemViewType(int position) {
        Log.d("position", ""+ position);
        Log.d("articles.size()", "" + articles.size());

        if (articles.size() % 11 == 0) {
            Log.i("VIEW_TYPE_LOADING", "VIEW_TYPE_LOADING");
            return VIEW_TYPE_LOADING;
        } else {
            Log.i("VIEW_TYPE_ARTICLE", "VIEW_TYPE_ARTICLE");

            return VIEW_TYPE_ARTICLE;
        }
    }*/

    @Override
    public int getItemCount() {
        return articles.size();

    }

    public void updateQuery(String savedQuery) {
        this.savedQuery = savedQuery;
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder{

        TextView articlePubDate;
        TextView articleHeadline;
        TextView articleSnippet;
        TextView firstPageHeader;
        ContentLoadingProgressBar progressBar;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            articlePubDate = itemView.findViewById(R.id.article_pub_date);
            articleHeadline = itemView.findViewById(R.id.article_headline);
            articleSnippet = itemView.findViewById(R.id.article_snippet);
            firstPageHeader = itemView.findViewById(R.id.first_page_header);
            progressBar = itemView.findViewById(R.id.progress);
        }

        public void bind(final Article article, int position) {
            if(position == 0){
                firstPageHeader.setVisibility(View.VISIBLE);
                firstPageHeader.setText(String.format(context.getResources().getString(R.string.first_page), savedQuery));
            }else{
                firstPageHeader.setVisibility(View.GONE);
            }


                articlePubDate.setText(article.publishDate);
                articleHeadline.setText(article.headline.printHeadline);
                //Log.d("headline", article.headline.printHeadline);
                articleSnippet.setText(article.snippet);




        }
    }
    /*
    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        ContentLoadingProgressBar progressBar;
        public LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress);
            progressBar.show();
        }

        public void bind(Article article) {
            progressBar.show();
        }
    }*/
}
