package com.commit451.elasticdragdismisslayout.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.commit451.elasticdragdismisslayout.ElasticDragDismissCallback;
import com.commit451.elasticdragdismisslayout.ElasticDragDismissLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Testing using a RecyclerView
 */
public class DetailRecyclerViewActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, DetailRecyclerViewActivity.class);
        return intent;
    }

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.draggable_frame)
    ElasticDragDismissLinearLayout dragLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_recyclerview);
        ButterKnife.bind(this);
        toolbar.setTitle("Countries");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CountriesAdapter adapter = new CountriesAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dragLayout.addListener(new ElasticDragDismissCallback() {
            @Override
            public void onDrag(float elasticOffset, float elasticOffsetPixels, float rawOffset, float rawOffsetPixels) {
            }

            @Override
            public void onDragDismissed() {
                // if we drag dismiss downward then the default reversal of the enter
                // transition would slide content upward which looks weird. So reverse it.
                if (dragLayout.getTranslationY() > 0) {
                    //TODO
                    //                            getWindow().setReturnTransition(
                    //                                    TransitionInflater.from(AboutActivity.this)
                    //                                            .inflateTransition(R.transition.about_return_downward));
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    finishAfterTransition();
                } else {
                    finish();
                }
            }
        });
        if (Build.VERSION.SDK_INT >= 21) {
            dragLayout.addListener(new SystemChromeFader(this));
        }
    }

    static class CountriesAdapter extends RecyclerView.Adapter<CountriesViewHolder> {

        String[] countries;

        public CountriesAdapter(Context context) {
            countries = context.getResources().getStringArray(R.array.countries);

        }

        @Override
        public CountriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return CountriesViewHolder.inflate(parent);
        }

        @Override
        public void onBindViewHolder(CountriesViewHolder holder, int position) {
            holder.bind(countries[position]);
        }

        @Override
        public int getItemCount() {
            return countries.length;
        }
    }

    static class CountriesViewHolder extends RecyclerView.ViewHolder {

        public static CountriesViewHolder inflate(ViewGroup parent) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_game, parent, false);
            return new CountriesViewHolder(view);
        }

        @BindView(R.id.text)
        TextView mText;

        public CountriesViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(String country) {
            mText.setText(country);
        }
    }
}
