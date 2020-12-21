package com.example.appsemestral.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appsemestral.ArticlesAdapter;
import com.example.appsemestral.Articulos;
import com.example.appsemestral.FirestoreAPI;
import com.example.appsemestral.MainActivity;
import com.example.appsemestral.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class ArticleDetailFragment extends DialogFragment implements DialogInterface.OnShowListener {

    private Articulos currentArticle;
    private EditText etTitle, etContent;
    private TextView tvTimeStamp;
    private ArrayList<Articulos> articles;
    private FirestoreAPI mFirestoreAPI;

    public ArticleDetailFragment(Articulos article, ArrayList<Articulos> articles){
        this.currentArticle = article;
        this.articles = articles;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setPositiveButton(R.string.dialog_ok_btn, null)
                .setNegativeButton(R.string.dialog_cancel_btn, null);

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_article_detail, null);
        builder.setView(view);

        etTitle = view.findViewById(R.id.et_title);
        etContent = view.findViewById(R.id.et_description);
        tvTimeStamp = view.findViewById(R.id.tv_timeStamp);

        etTitle.setText(currentArticle.getTitulo());
        etContent.setText(currentArticle.getContenido());

        tvTimeStamp.setText(getString(R.string.timestamp_s, currentArticle.getFecha()));
        mFirestoreAPI = FirestoreAPI.getInstance();

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);
        return dialog;
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        final AlertDialog dialog = (AlertDialog)getDialog();

        if ( dialog != null ) {
            Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);

            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( validateName(etTitle, etTitle) ) {
                        currentArticle.setTitulo(etTitle.getText().toString().trim());
                        currentArticle.setContenido(etContent.getText().toString().trim());
                        mFirestoreAPI.getArticleById(currentArticle.getId()).set(currentArticle)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getContext(), R.string.firestore_success_updated, Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), R.string.firestore_error_updated, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

        }
    }

    private boolean validateName( EditText etTitle, EditText etContent ) {
        boolean isValid = true;
        if ( etTitle.getText().toString().trim().isEmpty() ) {
            etTitle.setError(getActivity().getString(R.string.title_required_message));
            isValid = false;
        }else{
            if ( !ArticlesAdapter.validateUpdate(etTitle.getText().toString().trim(), articles) ){
                etTitle.setError(getActivity().getString(R.string.title_repet_message));
                isValid = false;
            }
        }
        if ( etContent.getText().toString().trim().isEmpty() ) {
            etContent.setError(getActivity().getString(R.string.title_required_message));
            isValid = false;
        }

        return isValid;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }
}