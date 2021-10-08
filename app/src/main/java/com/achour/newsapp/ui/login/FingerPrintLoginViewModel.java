package com.achour.newsapp.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModel;

import com.achour.newsapp.ui.listeners.FingerPrintListener;

import java.util.concurrent.Executor;

public class FingerPrintLoginViewModel extends ViewModel {

    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    public FingerPrintListener listener;


    public void setupBiometric(AppCompatActivity activity) {
        executor = ContextCompat.getMainExecutor(activity);
        biometricPrompt = new BiometricPrompt(activity,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                System.out.println("ErrorCode : " + errorCode);
                System.out.println("ErrorStr : " + errString);
                listener.onFailure(errString.toString());
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                listener.onSuccess();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                System.out.println("failed!");
                listener.onFailure("Failed");
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login - App News")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }
}
