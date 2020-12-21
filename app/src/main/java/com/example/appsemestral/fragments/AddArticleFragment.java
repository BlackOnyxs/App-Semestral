package com.example.appsemestral.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.appsemestral.ArticlesAdapter;
import com.example.appsemestral.Articulos;
import com.example.appsemestral.FirestoreAPI;
import com.example.appsemestral.MainActivity;
import com.example.appsemestral.R;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;


public class AddArticleFragment extends DialogFragment implements DialogInterface.OnShowListener {

    private EditText etTitle, etContent;
    private FirestoreAPI mFirestoreAPI;
    private ArrayList<Articulos> articulos;


    public AddArticleFragment(ArrayList<Articulos> articles) {
        this.articulos = articles;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setPositiveButton(getContext().getString(R.string.dialog_upload_btn), null)
                .setNegativeButton(getContext().getString(R.string.dialog_cancel_btn), null);

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_article, null);
        builder.setView(view);

        etTitle = view.findViewById(R.id.et_title);
        etContent = view.findViewById(R.id.et_content);

        mFirestoreAPI = FirestoreAPI.getInstance();

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);
        return dialog;
    }

    private boolean validateName( EditText etTitle, EditText etContent ) {
        boolean isValid = true;
        if ( etTitle.getText().toString().trim().isEmpty() ) {
            etTitle.setError(getActivity().getString(R.string.title_required_message));
            isValid = false;
        }else{
            if ( ArticlesAdapter.containsFile(etTitle.getText().toString().trim(), articulos) ){
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
    public void onShow(DialogInterface dialogInterface) {
            final AlertDialog dialog = (AlertDialog)getDialog();

            if ( dialog != null ) {
                Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                Button negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if ( validateName(etTitle, etContent) ) {
                            mFirestoreAPI.uploadArticle(dialog, configArticle(etTitle, etContent), getContext());
                        }
                    }
                });
                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismiss();
                    }
                });
            }

    }

    private Articulos configArticle(EditText etTitle, EditText etContent) {
        return new Articulos(etTitle.getText().toString().trim(),
                etContent.getText().toString().trim(), new Timestamp(Calendar.getInstance().getTimeInMillis()), null);
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