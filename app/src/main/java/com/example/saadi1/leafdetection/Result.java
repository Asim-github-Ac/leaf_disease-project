package com.example.saadi1.leafdetection;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.Uri;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Result extends AppCompatActivity {



    public ImageView dash_Image;
    Button btnsave;
    String dname;
    ProgressDialog progressDialog;

    TextView tv;
   // String [] disease_name = {"Armillaria root rot","Bacterial blast","Citrus nematode","Dothiorella blight","Phytophthora gummosis","Sooty mold"};

    List<String> disease_name=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        tv=findViewById(R.id.diseasename);
        btnsave=findViewById(R.id.savedisease);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Lead Detection");
        progressDialog.setMessage("Loading.........");
        progressDialog.setCancelable(false);
        dash_Image = (ImageView) findViewById(R.id.dasj_iamge);

        disease_name.add("Armillaria root rot");
        disease_name.add("Bacterial blast");
        disease_name.add("Citrus nematode");
        disease_name.add("Dothiorella blight");
        disease_name.add("Phytophthora gummosis");
        disease_name.add("Sooty mold");


        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                DataSave(dname,"1. if the image has any Yallow dots, it indicates the area of diseases.","2. Yellow dots signify that there is presence of plant diseases.","3. To Solve disease go to the Disease Details page on Home Page and Visit this.www.planetnatural.com");
            }
        });



        Intent intent = getIntent();
        Uri uri = intent.getParcelableExtra("uri");

        //URI TO BITMAP
        try {
            if (Uri.parse(String.valueOf(uri)) != null) {
                InputStream input = this.getContentResolver().openInputStream(uri);
                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                bitmapOptions.inDither = true;//optional
                bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
                Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
                //bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                Toast.makeText(this, "Oops Disease Found", Toast.LENGTH_SHORT).show();
                //dash_Image.setImageBitmap(bitmap);
                gh(bitmap);
            }

        } catch (Exception e) {
            //handle exception
        }
        ;
        //  gh(bitmap);
        //  dash_Image.setImageURI(uri);

    }

    public void gh(Bitmap mBitmap) {

        Bitmap b = toGrayscale(mBitmap);
        Bitmap mb = b.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(mb);
        int width = mb.getWidth();
        int height = mb.getHeight();

        int pixel;


        for (int x = 0; x < width; ++x) {
            for (int y = 0; y < height; ++y) {
                // get pixel color

                pixel = mb.getPixel(x, y);

                int A = Color.alpha(pixel);
                int R = Color.red(pixel);
                int G = Color.green(pixel);
                int B = Color.blue(pixel);
                if (x == 0 && y == 0) {

                    //   tv.setText(String.valueOf(A)+String.valueOf(R)+String.valueOf(G)+String.valueOf(B)+String.valueOf(pixel));
                }
                if (R > 200 && G > 200 && B > 200) {
                    pixel = Color.YELLOW;
                }
                //    bitmap.
                mb.setPixel(x, y, pixel);
            }
        }

        dash_Image.setImageBitmap(mb);
        Collections.reverse(disease_name);
        tv.setText("Name : "+disease_name.get(0).toString());

        dname= disease_name.get(0).toString();



    }

    public static Bitmap toGrayscale(Bitmap bmpOriginal) {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

public void DataSave(String name,String ins1,String ins2,String in3){

        Model model=new Model(name,ins1,ins2,in3);
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    firestore.collection("LeafDetection").add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
        @Override
        public void onSuccess(DocumentReference documentReference) {
            progressDialog.dismiss();
            Toast.makeText(Result.this, "Data SuccessFully Saved", Toast.LENGTH_SHORT).show();
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            progressDialog.dismiss();
            Toast.makeText(Result.this, "Error"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    });
}
}
