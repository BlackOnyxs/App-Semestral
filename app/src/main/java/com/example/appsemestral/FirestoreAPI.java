package com.example.appsemestral;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreAPI {

    private static final String ARTICLES_COLLECTION = "Articulos";

    private FirebaseFirestore mFirestore;

    private static FirestoreAPI INSTANCE = null;

    private FirestoreAPI(){
        this.mFirestore = FirebaseFirestore.getInstance();
    }

    public static FirestoreAPI getInstance(){
        if ( INSTANCE == null ){
            INSTANCE = new FirestoreAPI();
        }

        return INSTANCE;
    }

    public FirebaseFirestore getFirestore(){
        return mFirestore;
    }

    //References

    public CollectionReference getArticlesCollection(){
        return mFirestore.collection(ARTICLES_COLLECTION);
    }

    public DocumentReference getArticleById(String uid){
        return getArticlesCollection().document(uid);
    }

    public void uploadArticle(AlertDialog dialog, Articulos article, Context context){
        if ( mFirestore != null ){
            getArticlesCollection().document().set(article).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(context, context.getString(R.string.firestore_successAdd),Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, context.getString(R.string.firestore_errorAdd),Toast.LENGTH_SHORT).show();
                }
            });
        }
    }




}
