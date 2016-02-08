package ru.rambler.libs.rate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import ru.rambler.rate.R;

public class RateActivity extends AppCompatActivity {

    static final int RESULT_CODE_LATER = 0x100;
    static final int RESULT_CODE_CANCEL = 0x101;
    static final int RESULT_CODE_RATED = 0x102;
    static final String EXTRA_STARS = "stars";

    static String ARG_CONFIG = "configuration";
    private boolean isStarted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration config = (Configuration) getIntent().getSerializableExtra(ARG_CONFIG);

        setTheme(config.getTheme());
        setContentView(config.getLayout());

        findAndSetElementText(R.id.title, config.getTitleText(), config.getTitleStringId());
        findAndSetElementText(R.id.message, config.getMessageText(), config.getMessageStringId());
        findAndSetElementText(R.id.btn_later, config.getLabelLaterText(), config.getLabelLaterStringId())
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setResult(RESULT_CODE_LATER);
                        finish();
                    }
                });
        findAndSetElementText(R.id.btn_cancel, config.getLabelCancelText(), config.getLabelCancelStringId())
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setResult(RESULT_CODE_CANCEL);
                        finish();
                    }
                });
        ((ImageView) findViewById(R.id.icon)).setImageResource(config.getIconId());

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ratingBar.postDelayed(new ActionOnRate(rating), 100);
            }
        });
    }

    private View findAndSetElementText(int viewId, String text, int stringId) {
        TextView target = (TextView) findViewById(viewId);
        if (stringId > 0) {
            target.setText(stringId);
        } else {
            target.setText(text);
        }
        return target;
    }

    private class ActionOnRate implements Runnable {
        private final float stars;

        public ActionOnRate(float stars) {
            this.stars = stars;
        }

        @Override
        public void run() {
            if (!isStarted) {
                return;
            }
            setResult(RESULT_CODE_RATED, new Intent().putExtra(EXTRA_STARS, stars));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        isStarted = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        isStarted = false;
    }
}
