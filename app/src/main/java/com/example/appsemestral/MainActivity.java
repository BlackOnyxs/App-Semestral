package com.example.appsemestral;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appsemestral.fragments.AddArticleFragment;
import com.example.appsemestral.fragments.ArticleDetailFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemClickListener{
    private FloatingActionButton fab;
    private RecyclerView mArticlesRV;
    private ArticlesAdapter mArticlesAdapter;
    private TextView tvEmpty, tvTitle;
    private FirestoreAPI mFirestoreAPI;
    private ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab_add);
        tvEmpty = findViewById(R.id.tv_empty);
        tvTitle = findViewById(R.id.tv_title);
        mArticlesRV = findViewById(R.id.rv_homeArticles);
        pb = findViewById(R.id.pb_main);

        configRv();

        mFirestoreAPI = FirestoreAPI.getInstance();
        listenArticles();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AddArticleFragment(mArticlesAdapter.getArticles()).show(getSupportFragmentManager(), "");
            }
        });

    }

    private void configRv(){
        mArticlesAdapter = new ArticlesAdapter(new ArrayList<>(), getApplicationContext(), this);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mArticlesRV.addItemDecoration(itemDecoration);

        mArticlesRV.setLayoutManager(manager);
        mArticlesRV.setAdapter(mArticlesAdapter);
    }

    public void listenArticles() {
        if ( mFirestoreAPI != null ) {
            mFirestoreAPI.getArticlesCollection().addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if ( error == null ) {
                        if ( value != null ) {
                            try {
                                for (DocumentSnapshot d:
                                        value.getDocuments()) {
                                    Articulos a = d.toObject(Articulos.class);
                                    if ( a.getId() == null ) {
                                        a.setId(d.getId());
                                    }
                                    if ( a.getTitulo() != null ) {
                                        mArticlesAdapter.add(a);
                                    }
                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            pb.setVisibility(View.GONE);
                            mArticlesRV.setVisibility(View.VISIBLE);
                            tvTitle.setVisibility(View.VISIBLE);
                            tvEmpty.setVisibility(View.GONE);
                        }
                    }else{
                        if ( error.getMessage() != null ){
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        }
    }


    @Override
    public void onClickListener(Articulos article) {
        new ArticleDetailFragment(article, mArticlesAdapter.getArticles()).show(getSupportFragmentManager(), "");
    }

    @Override
    public void onLongClickListener(Articulos article) {
        new AlertDialog.Builder(this)
                .setTitle("Borrar Artículo")
                .setMessage("¿Quieres borrar "+ article.getTitulo() + "?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFle(article);
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }).setNegativeButton(getString(R.string.dialog_cancel_btn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }

    private void deleteFle(Articulos article) {
        mFirestoreAPI.getArticleById(article.getId()).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getApplicationContext(), R.string.firestore_success_deleted, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), R.string.firestore_error_delete, Toast.LENGTH_SHORT).show();
            }
        });
    }
}