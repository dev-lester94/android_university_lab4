package com.codepath.recyclerviewlab;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.recyclerviewlab.models.Article;
import com.codepath.recyclerviewlab.networking.CallbackResponse;
import com.codepath.recyclerviewlab.networking.NYTimesApiClient;

import java.util.ArrayList;
import java.util.List;


/**
 * A fragment representing a list of Items.
 * <p/>
 * interface.
 */
public class ArticleResultFragment extends Fragment {

    private NYTimesApiClient client = new NYTimesApiClient();
    ArrayList<Article> articles;
    RecyclerView rvList;
    ArticleResultsRecyclerViewAdapter adapter;
    EndlessRecyclerViewScrollListener scrollListener;
    String savedQuery = "";
    ContentLoadingProgressBar progressBar;



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ArticleResultFragment() {
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        SearchView item = (SearchView) menu.findItem(R.id.action_search).getActionView();
        item.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                loadNewArticlesByQuery(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article_result_list, container, false);
        rvList = (RecyclerView) view.findViewById(R.id.list);
        articles = new ArrayList<>();
        adapter = new ArticleResultsRecyclerViewAdapter(articles, savedQuery);
        rvList.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvList.setLayoutManager(linearLayoutManager);
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadNextDataFromApi(page);
            }
        };
        rvList.addOnScrollListener(scrollListener);
        progressBar = view.findViewById(R.id.progress);
        return view;
    }

    private void loadNextDataFromApi(final int page) {
        client.getArticlesByQuery(new CallbackResponse<List<Article>>() {
            @Override
            public void onSuccess(final List<Article> addMoreArticles) {
                /*progressBar.setVisibility(View.VISIBLE);
                Log.d("ArticleResultFragment", String.format("Successfully loaded articles from page %d", page));

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("runable" , "runable");
                        articles.addAll(addMoreArticles);
                        adapter.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);

                    }
                },1000);*/

                articles.addAll(addMoreArticles);
                adapter.notifyDataSetChanged();
                Log.d("ArticleResultFragment", String.format("Successfully loaded articles from page %d", page));




            }

            @Override
            public void onFailure(Throwable error) {
                Log.d("ArticleResultFragment", "Failure load article " + error.getMessage());
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT);
            }
        }, savedQuery, page);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void loadNewArticlesByQuery(String query) {
        savedQuery = query;
        Log.d("ArticleResultFragment", "loading articles for query " + query);
        Toast.makeText(getContext(), "Loading articles for \'" + query + "\'", Toast.LENGTH_SHORT).show();
        // TODO(Checkpoint 3): Implement this method to populate articles
        client.getArticlesByQuery(new CallbackResponse<List<Article>>() {
            @Override
            public void onSuccess(List<Article> loadedArticles) {
                Log.d("ArticleResultFragment", "Successfully loaded articles");
                    //articles.clear();
                    articles.addAll(loadedArticles);
                    adapter.updateQuery(savedQuery);
                    adapter.notifyDataSetChanged();
                    //String textString = String.format(getResources().getString(R.string.first_page), savedQuery);

            }

            @Override
            public void onFailure(Throwable error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ArticleResultFragment", "Failure loading articles " + error.getMessage());
            }
        },query);


    }

    private void loadArticlesByPage(final int page) {
        // TODO(Checkpoint 4): Implement this method to do infinite scroll
    }
}
