package com.example.saadi1.leafdetection;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImage;
import com.yalantis.ucrop.UCrop;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.Manifest.permission_group.CAMERA;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AnalyzeActivity extends AppCompatActivity {
    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";


    private EditText mTitleField;
    public Bitmap bitmap;
    private EditText mBodyField;
    private ImageView imageView;
    private static final int GALLERY_REQUEST = 1;
    private Uri imageUri = null;

    private static final int RC_CAMERA_PERMISSIONS = 102;
    private Uri mFileUri;
    private static final int TC_PICK_IMAGE = 101;
    public String asl;
    private Uri mCropImageUri;
    String alk;
    TextView tv;

    Button btnStart;
    ImageView ivImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyze);
        btnStart = findViewById(R.id.button);
        ivImage = findViewById(R.id.imageView);


        listener();
        listener2();
        //    tv=(TextView)findViewById(R.id.textView);

    }

    private void listener() {
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
    }

    private void listener2() {
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImage();
            }
        });
    }


    private void pickImage() {
        CropImage.startPickImageActivity(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            UCrop uCrop = UCrop.of(imageUri, Uri.fromFile(new File(getCacheDir(), "Selected_image.png")));
            uCrop = advancedConfig(uCrop);
            uCrop.start(this);


        }

        if (requestCode == UCrop.REQUEST_CROP && data != null) {
            final Uri resultUri = UCrop.getOutput(data);

            Intent intent = new Intent(getApplicationContext(), Wait.class);
            intent.putExtra("uri", resultUri);
            //gh();
            startActivity(intent);

        }
        if (resultCode == UCrop.RESULT_ERROR) {
            handleCropError(data);
        }

    }

    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) Log.e(TAG, "handleCropError: ", cropError);
    }

    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();
        options.setFreeStyleCropEnabled(true);


        options.setMaxScaleMultiplier(5);
        options.setCropGridColor(Color.YELLOW);
        options.setCropFrameColor(Color.YELLOW);
        options.setCropFrameStrokeWidth(5);

        /*\options.setToolbarColor(ContextCompat.getColor(this, R.color.purple));
        options.setStatusBarColor(ContextCompat.getColor(this, R.color.purple));
        options.setActiveWidgetColor(ContextCompat.getColor(this, R.color.purple));*/

        return uCrop.withOptions(options);
    }


}
