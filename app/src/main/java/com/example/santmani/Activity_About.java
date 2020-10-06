package com.example.santmani;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

import java.util.Objects;

public class Activity_About extends AppCompatActivity {

    //In-App Review Declaration
    ReviewInfo reviewInfo;
    private ReviewManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__about);
        Objects.requireNonNull(getSupportActionBar()).setTitle("About");

        //Calling method for In-App Review
        initReviewInfo();

        //Normal
        findViewById(R.id.feedback_email).setOnClickListener(v -> feedback_email());

        findViewById(R.id.share_me).setOnClickListener(v -> shareApp());


        //App_Version
        TextView versionName = findViewById(R.id.app_version);
        versionName.setText(String.format("Version %s", BuildConfig.VERSION_NAME));
    }

    private void feedback_email() {
        Toast.makeText(Objects.requireNonNull(getApplicationContext()).getApplicationContext(), "Thankyou for your feedback!", Toast.LENGTH_SHORT).show();

        Intent email = new Intent(Intent.ACTION_SENDTO);
        email.setData(Uri.parse("mailto:contact@santmani.in" +
                "?subject=Santmani Feedback"));
        email.putExtra(Intent.EXTRA_SUBJECT, "Santmani Feedback");
        startActivity(email);
    }

    private void shareApp() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String url = "https://play.google.com/store/apps/details?id=com.santmani";
        String shareBody = "A One-Stop-Shop e-commerce store for religious goods, Order now.\n\n" +
                "Download our app from PlayStore:\n" + url;
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Santmani");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));

    }

    private void initReviewInfo() {
        //In-App Review
        manager = ReviewManagerFactory.create(this);
        Task<ReviewInfo> request = manager.requestReviewFlow();
        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                reviewInfo = task.getResult();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (reviewInfo != null) {
            Task<Void> flow = manager.launchReviewFlow(Activity_About.this, reviewInfo);
            flow.addOnCompleteListener(task -> {
                // The flow has finished. The API does not indicate whether the user
                // reviewed or not, or even whether the review dialog was shown. Thus, no
                // matter the result, we continue our app flow.
            });
        }
        super.onBackPressed();
    }
}