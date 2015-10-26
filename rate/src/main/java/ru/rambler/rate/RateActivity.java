package ru.rambler.rate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class RateActivity extends AppCompatActivity {

    static final int RESULT_CODE_LATER = 0x100;
    static final int RESULT_CODE_CANCEL = 0x101;
    static final int RESULT_CODE_RATED = 0x102;
    static final String EXTRA_STARS = "stars";

    static String ARG_CONFIG = "configuration";
    static String ARG_LAYOUT = "layout";
    static String ARG_TITLE_TEXT = "title_text";
    static String ARG_TITLE_STRING_ID = "title_string_id";
    static String ARG_MESSAGE_TEXT = "message_text";
    static String ARG_MESSAGE_STRING_ID = "message_string_id";
    static String ARG_LABEL_LATER_TEXT = "label_later_text";
    static String ARG_LABEL_LATER_STRING_ID = "label_later_string_id";
    static String ARG_LABEL_CANCEL_TEXT = "label_cancel_text";
    static String ARG_LABEL_CANCEL_STRING_ID = "label_cancel_string_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RamblerRate.Configuration config = (RamblerRate.Configuration) getIntent().getSerializableExtra(ARG_CONFIG);
        setContentView(config.getLayout());

        setupElementText(R.id.title, ARG_TITLE_TEXT, ARG_TITLE_STRING_ID);
        setupElementText(R.id.btn_later, ARG_LABEL_LATER_TEXT, ARG_LABEL_LATER_STRING_ID);
        setupElementText(R.id.btn_cancel, ARG_LABEL_CANCEL_TEXT, ARG_LABEL_CANCEL_STRING_ID);
        setupElementText(R.id.btn_later, ARG_LABEL_LATER_TEXT, ARG_LABEL_LATER_STRING_ID);
    }

    private void setupElementText(int viewId, String intentArgText, String intentArgStringId) {
        String text = getIntent().getStringExtra(intentArgText);
        int resId = getIntent().getIntExtra(intentArgStringId, 0);

        TextView target = (TextView) findViewById(viewId);
        if (resId > 0) {
            target.setText(resId);
        } else {
            target.setText(text);
        }
    }
}
