package com.achour.newsapp.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.achour.newsapp.R;
import com.achour.newsapp.databinding.ActivityFingerPrintLoginBinding;
import com.achour.newsapp.ui.listeners.FingerPrintListener;
import com.achour.newsapp.ui.news.NewsActivity;

public class FingerPrintLoginActivity extends AppCompatActivity implements FingerPrintListener {

    private ActivityFingerPrintLoginBinding binding;
    private FingerPrintLoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_finger_print_login);
        binding.setLifecycleOwner(this);

        setupVM();

    }

    private void setupVM() {
        viewModel = new ViewModelProvider(this).get(FingerPrintLoginViewModel.class);

        /* init listeners */
        viewModel.listener = this;

        /* data binding setup */
        binding.setViewModel(viewModel);
        binding.setActivity(this);

        /* call main function */
        viewModel.setupBiometric(this);

    }

    @Override
    public void onStarted() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess() {
        Log.d("TAG", "onSuccess: ]");
        binding.progressBar.setVisibility(View.GONE);
        startActivity(new Intent(this, NewsActivity.class));
        finish();
    }

    @Override
    public void onFailure(String message) {
        binding.progressBar.setVisibility(View.GONE);
        //Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, NewsActivity.class));
        finish();
    }
}