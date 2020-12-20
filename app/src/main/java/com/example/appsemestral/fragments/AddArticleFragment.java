package com.example.appsemestral.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.appsemestral.R;


public class AddArticleFragment extends DialogFragment implements DialogInterface.OnShowListener {

    public AddArticleFragment() {
        // Required empty public constructor
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setPositiveButton(getContext().getString(R.string.dialog_upload_btn), null)
                .setNegativeButton(getContext().getString(R.string.dialog_cancel_btn), null);

        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_add_article, null);
        builder.setView(view);

        AlertDialog dialog = builder.create();
        dialog.setOnShowListener(this);
        return dialog;
    }

    @Override
    public void onShow(DialogInterface dialog) {

    }
}