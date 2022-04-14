package com.example.mastermind.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mastermind.R;
import com.google.android.material.button.MaterialButton;

public class HowToPlayActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private TextView textView, pageNumberTextView;
    private MaterialButton buttonBack, buttonNext;

    private int currentScreen;
    private final int maxScreen = 4;

    private int[] imageIds = new int[]{R.drawable.htp_screen1, R.drawable.htp_screen2, R.drawable.htp_screen3, R.drawable.htp_screen4};
    private int[] textIds = new int[]{R.string.htp_screen1, R.string.htp_screen2, R.string.htp_screen3, R.string.htp_screen4};;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtoplay);

        this.imageView = findViewById(R.id.htp_imageView);
        this.textView = findViewById(R.id.htp_textView);
        this.pageNumberTextView = findViewById(R.id.htp_page_number);
        this.buttonBack = findViewById(R.id.htp_back_button);
        this.buttonNext = findViewById(R.id.htp_next_button);

        buttonBack.setOnClickListener(this);
        buttonNext.setOnClickListener(this);

        currentScreen = 1;
        buttonBack.setClickable(false);

        refreshView();
    }

    private void refreshView() {
        imageView.setImageResource(imageIds[currentScreen-1]);
        textView.setText(getText(textIds[currentScreen-1]));
        pageNumberTextView.setText(currentScreen + "/" + maxScreen);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.htp_back_button) {
            buttonNext.setClickable(true);
            currentScreen--;
            if(currentScreen == 1)
                buttonBack.setClickable(false);

        } else if (id == R.id.htp_next_button) {
            buttonBack.setClickable(true);
            currentScreen++;
            if(currentScreen == maxScreen)
                buttonNext.setClickable(false);

        }
        refreshView();
    }
}
