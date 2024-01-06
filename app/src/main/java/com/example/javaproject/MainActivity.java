package com.example.javaproject;

import static android.Manifest.permission.READ_MEDIA_IMAGES;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private final static int TAKE_PICTURE_INTENT = 100;
    private final static int CHOOSE_PICTURE_FROM_GALLERY = 101;
    private final static int CHOOSE_PDF_FROM_DOCUMENT = 102;

    private final static int NUMBER_OF_MAXIMUM_BYTE = 921600; // 1 KB = 1024 Byte -> 900 KB = 900 * 1024 = 921600

    private Bitmap originalBitmap = null;

    private final static String pathToOriginalImage = Environment.getExternalStorageDirectory().getPath()+ "/myPictures/img_4.jpg";
    private File parentDirectory;

    /**
     * Ordre du fichier :
     * - Méthode onCreate
     * - la méthode OnActivityResult
     * - Les méthodes pour lancer le processus dédié selon le choix de l'utilisateur
     * - La méthode de compression
     */

    /**
     * Résultats des test:
     * - 1 ère image (Après analyse ne subit pas la compression) :
     *      - Taille original : 400 KB -> 409600 bytes
     *      - Taille 70 % compression : 328 KB
     *      - Taille 100 % compression : 76 KB
     *
     * - 2 ère image (Après analyse ne subit pas la compression) :
     *      - Taille original : 700 KB -> 716800 bytes
     *      - Taille 70 % compression : 604 KB
     *      - Taille 100 % compression : 150 KB
     *
     * - 3 ère image (Après analyse doit être compressée) :
     *      - Taille original : 2.3 MB -> 2411724
     *      - Taille 70 % compression : 282 KB
     *      - Taille 100 % compression : 58 KB
     */

    /**
     *  Après analyse des résultats si dessus, il est plus intéressant de compresser une image
     *  Qui possède au minimum une taille de 1MB (pour être sûr nous viserons les 900 KB)
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{ READ_MEDIA_IMAGES, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        ImageView originalView = findViewById(R.id.original_image);
        ImageView compressView = findViewById(R.id.compress_image);
        Button compressButton = findViewById(R.id.compress_image_button);

        // On récupère l'image

        File file = new File(pathToOriginalImage);

        if (file.exists()) {
            Log.i("File exist :", "YES");

            parentDirectory = file.getParentFile();

            // On créer une bitmap à partir de l'image original
            originalBitmap = BitmapFactory.decodeFile(file.getPath());

            if (originalBitmap != null) {
                originalView.setImageBitmap(originalBitmap);
                compressView.setImageBitmap(originalBitmap); // Permettra de mieux remarquer la différence après compression

                // Si le fichier existe alors on regarde sa taille
                // Si le fichier dépasse la limite enregistrer alors on le compress
                if(file.length() >= NUMBER_OF_MAXIMUM_BYTE) {
                    // Compressons l'image
                    compressBitmap(originalBitmap, compressView);
                }
            }
        }
        else {
            Log.i("File exist :", "NO");
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {
            switch (requestCode) {
                case TAKE_PICTURE_INTENT:
                    break;
                case CHOOSE_PICTURE_FROM_GALLERY:
                    break;
                case CHOOSE_PDF_FROM_DOCUMENT:
                    break;
                default:
                    break;
            }
        }
    }

    // Prendre une photo
    private void takePhoto() {

    }

    // Photo venant de la gallerie d'image


    // PDF ( plus tard )


    // La méthode compression

    private void compressBitmap(Bitmap bitmap, ImageView compressImageView) {
        if (bitmap == null) {
            Log.i("Bitmap status", "Bitmap is null");
        }
        else {
            Bitmap compressBitmap;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream);
            byte[] bytesArray = byteArrayOutputStream.toByteArray();

            compressBitmap = BitmapFactory.decodeByteArray(bytesArray, 0, bytesArray.length);
            compressImageView.setImageBitmap(compressBitmap);

            saveCompressedBitmap(bytesArray);
        }
    }

    // Méthode pour enregistrer l'image compressé -> Pouvoir comparer la taille du fichier
    private void saveCompressedBitmap(byte[] bytesArray) {

        String pathOfCompressedImage = "/JPEG_CP_4_30.jpg";

        File outputFile = new File(parentDirectory, pathOfCompressedImage);

        try {
            // La condition permet de vérifier que le fichier à bien était créer
            // mais également de le créer de force si le cas échouant
            if(outputFile.createNewFile()) {
                FileOutputStream outputStream = new FileOutputStream(outputFile);
                outputStream.write(bytesArray);
                outputStream.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (outputFile.exists()) {
            Log.i("File created :", "YES");
        }
        else {
            Log.i("File created :", "NO");
        }
    }

}