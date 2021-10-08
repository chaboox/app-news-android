package com.achour.newsapp.ui.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.achour.newsapp.BuildConfig;
import com.achour.newsapp.R;
import com.achour.newsapp.data.models.NewsModel;
import com.achour.newsapp.databinding.ActivityNewsBinding;
import com.achour.newsapp.ui.listeners.RetrieveListener;
import com.achour.newsapp.ui.onenews.OneNewsActivity;
import com.achour.newsapp.utils.ConstantsUtils;

public class NewsActivity extends AppCompatActivity implements RetrieveListener, NewsAdapter.OnItemClickListenerInterface {

    private static final String TAG = "NewsActivity";
    private ActivityNewsBinding binding;
    private NewsViewModel viewModel;
    private NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_news);
        binding.setLifecycleOwner(this);

        setupRV();
        setupVM();

    }


    private void setupRV() {
        adapter = new NewsAdapter();
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    private void setupVM() {
        /* init view model */
        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);

        /* init listeners */
        viewModel.retrieveListener = this;

        /* data binding setup */
        binding.setViewModel(viewModel);
        binding.setActivity(this);
        binding.sourceTV.setText(BuildConfig.SOURCE_NAME);

        /* call main function */
        viewModel.getNews();

        /* observe on the mutable live data that contains the list */
        viewModel.listMLD.observe(this, newsList -> {
            if (newsList.size() == 0) binding.emptyListTV.setVisibility(View.VISIBLE);
            else binding.emptyListTV.setVisibility(View.GONE);

            adapter.setList(this, newsList);
        });

    }


    /**
     * overriding retrieve methods
     **/

    @Override
    public void onStartedRetrieve() {
        Log.d(TAG, "onStartedRetrieve: ");
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessRetrieve() {
        Log.d(TAG, "onSuccessRetrieve: ");
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onFailureRetrieve(String message) {
        Log.d(TAG, "onFailureRetrieve: " + message);
        binding.progressBar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * overriding recycler onClick methods
     **/

    @Override
    public void onItemClick(NewsModel newsModel) {
        Log.d(TAG, "onItemClick: " + newsModel);
        Intent intent = new Intent(this, OneNewsActivity.class);
        intent.putExtra(ConstantsUtils.NEWS_MODEL, newsModel);
        startActivity(intent);
    }
}