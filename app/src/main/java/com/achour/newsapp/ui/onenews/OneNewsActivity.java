package com.achour.newsapp.ui.onenews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.achour.newsapp.R;
import com.achour.newsapp.data.models.NewsModel;
import com.achour.newsapp.databinding.ActivityOneNewsBinding;
import com.achour.newsapp.utils.ConstantsUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class OneNewsActivity extends AppCompatActivity {

    private ActivityOneNewsBinding binding;
    private NewsModel newsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_one_news);
        binding.setLifecycleOwner(this);

        getArgsAndPopulateFields();

    }

    private void getArgsAndPopulateFields() {
        newsModel = (NewsModel) getIntent().getSerializableExtra(ConstantsUtils.NEWS_MODEL);

        binding.dateTV.setText(newsModel.getPublishedAt());
        binding.titleTV.setText(newsModel.getTitle());
        binding.authorTV.setText(newsModel.getAuthor());
        binding.descriptionTV.setText(newsModel.getDescription());

        //binding.contentTV.setText(StringUtils.repeatString(newsModel.getDescription(), 5));
        String content = newsModel.getDescription() + "\n\n" + ConstantsUtils.LOREM;
        binding.contentTV.setText(content);

        Glide.with(this)
                .load(newsModel.getUrlToImage())
                .apply(RequestOptions.centerCropTransform())
                .into(binding.bgIV);
    }

    public void exitActivity(View v) {
        finish();
    }
}