package com.example.saadi1.leafdetection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class SaveD_Data extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Model> modelList=new ArrayList<>();
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_ddata);
        recyclerView=findViewById(R.id.savedata);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("List Data");
        progressDialog.setMessage("Loading.....");
        progressDialog.show();
        GetData();


    }
    public void GetData(){
        FirebaseFirestore firestore=FirebaseFirestore.getInstance();
        firestore.collection("LeafDetection").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                if (queryDocumentSnapshots.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(SaveD_Data.this, "Record Not Found", Toast.LENGTH_SHORT).show();
                }else {

                    List<Model> models=queryDocumentSnapshots.toObjects(Model.class);
                    modelList.addAll(models);
                    progressDialog.dismiss();
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(new DiseaseAdapter(getApplicationContext(),modelList));

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(SaveD_Data.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}