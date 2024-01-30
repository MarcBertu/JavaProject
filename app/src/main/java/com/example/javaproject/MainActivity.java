package com.example.javaproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private String currentPhotoPath = "";
    private final String finalPath = "/storage/emulated/0/Android/data/com.example.javaproject/files/Pictures/JPEG_20240105_164518_860960947747885681.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Comment souhaitez-vous importer votre CV ?");

        String[] choices = {"Prendre une photo", "Choisir une photo", "Choisir un document"};
        builder.setItems(choices, (dialog, which) -> {
            switch (which) {
                case 0:
                    takePhoto();
                    break;
                case 1:
                    choosePhoto();
                    break;
                case 2:
                    chooseDocument();
                    break;
            }
        });

        AlertDialog dialog = builder.create();
        //dialog.show();

        action();
    }

    private void takePhoto() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.javaproject.provider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 100);
            }
        }

       /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
           startActivityForResult(takePictureIntent, 100);
       }
        */
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.FRANCE).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void choosePhoto() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 101);
    }

    private void chooseDocument() {
        Intent pickDocument = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        pickDocument.addCategory(Intent.CATEGORY_OPENABLE);
        pickDocument.setType("application/pdf");
        startActivityForResult(pickDocument, 102);
    }

    private void action() {
        Bitmap bitmap100 = BitmapFactory.decodeFile(finalPath);
        Bitmap bitmap30 = BitmapFactory.decodeFile(finalPath);
        Bitmap bitmap50 = BitmapFactory.decodeFile(finalPath);
        Bitmap bitmap70 = BitmapFactory.decodeFile(finalPath);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Original
        bitmap100.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray100 = byteArrayOutputStream .toByteArray();

        // Compress 30 %
        bitmap30.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byte[] byteArray30 = byteArrayOutputStream .toByteArray();

        // Compress 50 %
        bitmap50.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
        byte[] byteArray50 = byteArrayOutputStream .toByteArray();

        // Compress 70 %
        bitmap70.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream);
        byte[] byteArray70 = byteArrayOutputStream .toByteArray();

        // 100%
        BitmapFactory.Options options100 = new BitmapFactory.Options();
        options100.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(byteArray100, 0, byteArray100.length, options100);
        // 30%
        BitmapFactory.Options options30 = new BitmapFactory.Options();
        options30.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(byteArray30, 0, byteArray30.length, options30);
        // 50%
        BitmapFactory.Options options50 = new BitmapFactory.Options();
        options50.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(byteArray50, 0, byteArray50.length, options50);
        // 70%
        BitmapFactory.Options options70 = new BitmapFactory.Options();
        options70.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(byteArray70, 0, byteArray30.length, options70);

        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        byte[][] byteList= {
                byteArray100,
                byteArray30,
                byteArray50,
                byteArray70
        };

        for(int i = 0; i < 4; i++) {
            String imageFileName = "JPEG_" + i;

            File image = null;
            try {
                image = File.createTempFile(
                        imageFileName,  /* prefix */
                        ".jpg",         /* suffix */
                        storageDir      /* directory */
                );
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if(image.exists()) {
                try (FileOutputStream outputStream = new FileOutputStream(image)) {
                    outputStream.write(byteList[i]);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            Log.i("Image path", image.getAbsolutePath());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i("Image path", currentPhotoPath);

        if (resultCode == Activity.RESULT_OK && data != null) {
            if(requestCode == 100) {


            } else {
                Log.i("Data", String.valueOf(data.getData()));
            }
        }
    }


}