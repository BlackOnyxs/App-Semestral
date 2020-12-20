package com.example.appsemestral;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.appsemestral.fragments.AddArticleFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements OnItemClickListener{
    private FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab_add);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddArticleFragment().show(getSupportFragmentManager(), "");
            }
        });

    }

    @Override
    public void onClickListener(Articulos file) {

    }

    @Override
    public void onLongClickListener(Articulos file) {

    }
}