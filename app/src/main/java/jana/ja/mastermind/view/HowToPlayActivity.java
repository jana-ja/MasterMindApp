package jana.ja.mastermind.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import jana.ja.mastermind.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.analytics.FirebaseAnalytics;

public class HowToPlayActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageView;
    private TextView textView, pageNumberTextView;
    private MaterialButton buttonBack, buttonNext;

    private int currentScreen;
    private final int maxScreen = 5;

    private int[] imageIds = new int[]{R.drawable.htp_screen1, R.drawable.htp_screen1, R.drawable.htp_screen2, R.drawable.htp_screen3, R.drawable.htp_screen4};
    private int[] textIds = new int[]{R.string.htp_screen1, R.string.htp_screen1_5, R.string.htp_screen2, R.string.htp_screen3, R.string.htp_screen4};;

    FirebaseAnalytics mFirebaseAnalytics;

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

        // analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Bundle bundle = new Bundle();
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.TUTORIAL_BEGIN, bundle);

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
            // when on first page -> finish activity with back button
            if(currentScreen == 1){
                this.finish();
                return;
            }
            // going back from last page -> next button is no longer finish button
            if(currentScreen == maxScreen){
                buttonNext.setText(getString(R.string.button_text_htp_next));
            }
            currentScreen--;


        } else if (id == R.id.htp_next_button) {
            buttonBack.setClickable(true);
            // when on last page -> finish activity with next button
            if(currentScreen == maxScreen) {
                // analytics
                Bundle bundle = new Bundle();
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.TUTORIAL_COMPLETE, bundle);
                this.finish();
                return;
            }
            currentScreen++;
            // switching to last page -> next button becomes finish button
            if(currentScreen == maxScreen)
                buttonNext.setText(getString(R.string.button_text_htp_finish));
        }
        refreshView();
    }

}
