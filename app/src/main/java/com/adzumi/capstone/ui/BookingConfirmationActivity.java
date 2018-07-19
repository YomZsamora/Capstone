package com.adzumi.capstone.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.adzumi.capstone.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookingConfirmationActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.finishButton)
    Button mFinishButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_confirmation);
        ButterKnife.bind(this);

        mFinishButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mFinishButton) {
            Intent intent =  new Intent(BookingConfirmationActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }
}
