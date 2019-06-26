package com.commit451.elasticdragdismisslayout.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.commit451.elasticdragdismisslayout.ElasticDragDismissCallback;
import com.commit451.elasticdragdismisslayout.ElasticDragDismissFrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Sample detail activity
 */
public class DetailActivity extends AppCompatActivity {

    private static final String KEY_CHEESE = "cheese";

    public static Intent newIntent(Context context, Cheese cheese) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(KEY_CHEESE, cheese);
        return intent;
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.draggable_frame)
    ElasticDragDismissFrameLayout draggableFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Cheese cheese = (Cheese) getIntent().getSerializableExtra(KEY_CHEESE);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar.setTitle(cheese.getName());

        draggableFrame.addListener(new ElasticDragDismissCallback() {
            @Override
            public void onDrag(float elasticOffset, float elasticOffsetPixels, float rawOffset, float rawOffsetPixels) {
            }

            @Override
            public void onDragDismissed() {
                // if we drag dismiss downward then the default reversal of the enter
                // transition would slide content upward which looks weird. So reverse it.
                if (draggableFrame.getTranslationY() > 0 && Build.VERSION.SDK_INT >= 21) {
                    getWindow().setReturnTransition(
                            TransitionInflater.from(DetailActivity.this)
                                    .inflateTransition(R.transition.about_return_downward));
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }
        });
        if (Build.VERSION.SDK_INT >= 21) {
            draggableFrame.addListener(new SystemChromeFader(this));
        }
    }
}
