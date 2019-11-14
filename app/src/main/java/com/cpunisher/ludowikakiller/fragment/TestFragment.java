package com.cpunisher.ludowikakiller.fragment;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.cpunisher.ludowikakiller.R;
import com.cpunisher.ludowikakiller.util.Utils;
import com.cpunisher.ludowikakiller.dao.SentenceDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class TestFragment extends Fragment {

    private TextView tvSentence;
    private String word;
    private boolean tip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_test, null);

        tvSentence = view.findViewById(R.id.sentence);

        FloatingActionButton fabCheck = view.findViewById(R.id.fab);
        fabCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = tvSentence.getText().toString();
                String replacement = "<font color=\'green\'>" + word + "</font>";
                s = s.replaceAll("[a-z]*_{2,}", replacement);
                tvSentence.setText(Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY));
            }
        });

        FloatingActionButton fabGo = view.findViewById(R.id.go);
        fabGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                word = getNextWord();
                tip = false;
            }
        });

        final FloatingActionButton fabTip = view.findViewById(R.id.tip);
        fabTip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tip) {
                    String s = tvSentence.getText().toString();
                    String replacement = "<font color=\'green\'>" + word.charAt(0) + "</font>";
                    s = s.replaceFirst("_", replacement);
                    tvSentence.setText(Html.fromHtml(s, Html.FROM_HTML_MODE_LEGACY));
                    tip = true;
                } else {
                    Snackbar.make(fabTip, getString(R.string.tipped), Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    /**
     * 从数据库文件中随机读取一个单词
     * @return 随机读取的单词
     */
    private String getNextWord() {
        Random random = new Random();
        int count = SentenceDao.getInstance(getContext()).getSentenceCount();
        int id = random.nextInt(count);
        String sentence = SentenceDao.getInstance(getContext()).getSentenceById(id);
        String word = sentence.substring(sentence.indexOf("{") + 1, sentence.lastIndexOf("}"));

        tvSentence.setText(sentence.replace("{" + word + "}", Utils.buildSequence('_', word.length() + 1)));
        return word;
    }
}
