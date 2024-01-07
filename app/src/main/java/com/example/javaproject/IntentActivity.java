package com.example.javaproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IntentActivity extends AppCompatActivity {

    private final static int TAKE_PICTURE_INTENT = 100;
    private final static int CHOOSE_PICTURE_FROM_GALLERY = 101;
    private final static int CHOOSE_PDF_FROM_DOCUMENT = 102;

    private File tempFile = null;

    private ImageView imageView;

    /**
     * Dans ce fichier vous trouverez les différentes façons de récupérer le fichier média
     * que l'on souhaite traité
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent);

        this.imageView = findViewById(R.id.image_from_intent);

        takePhoto();

        //takeFromGallery();

        //takeFromDocuments();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE_INTENT:
                    displayImageOrDocument(TAKE_PICTURE_INTENT);
                    break;
                case CHOOSE_PICTURE_FROM_GALLERY:
                    dataFromGalleryOrDocument(data, CHOOSE_PICTURE_FROM_GALLERY);
                    break;
                case CHOOSE_PDF_FROM_DOCUMENT:
                    dataFromGalleryOrDocument(data, CHOOSE_PDF_FROM_DOCUMENT);
                    break;
                default:
                    break;
            }

            if (tempFile != null && tempFile.exists()) {
                try {
                    this.getBase64FromFile(tempFile);
                }
                catch (IOException ioException) {
                    ioException.printStackTrace();
                    // TODO: 07/01/2024 Gérer l'erreur
                }
            }
        }
    }

    /**
     *  Méthode Intent
     */

    // Prendre une photo
    private void takePhoto() {

        // Création du fichier
        try {
            this.createCustomTempFile(TAKE_PICTURE_INTENT);
        }
        catch (IOException e) {
            e.printStackTrace();

            //TODO: Traitez l'erreur
        }

        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // On vérifie qu'une caméra est disponible
        if(takePhotoIntent.resolveActivity(getPackageManager()) != null) {
            if(tempFile != null && tempFile.exists()) {

                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.javaproject",
                        tempFile);

                takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePhotoIntent, TAKE_PICTURE_INTENT);
            }
        }
        else {
            // Aucune caméra de disponible
            // Décidez de ce que vous souhaitez faire dans ce cas
        }
    }

    // Photo venant de la gallerie d'image
    private void takeFromGallery() {

        // Création du fichier
        try {
            this.createCustomTempFile(CHOOSE_PICTURE_FROM_GALLERY);
        }
        catch (IOException e) {
            e.printStackTrace();

            //TODO: Traitez l'erreur
        }

        Intent fromGalleryIntent = new Intent(Intent.ACTION_PICK);
        fromGalleryIntent.setType("image/jpg");
        // On vérifie qu'une caméra est disponible
        if(fromGalleryIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(fromGalleryIntent, "Ouvrir avec"), CHOOSE_PICTURE_FROM_GALLERY);
        }
        else {
            // Aucune application de stockage d'image
            // Décidez de ce que vous souhaitez faire dans ce cas
            //TODO: Traitez le problème
        }
    }

    // PDF venant des documents de l'utilisateur
    private void takeFromDocuments() {

        // Création du fichier
        try {
            this.createCustomTempFile(CHOOSE_PDF_FROM_DOCUMENT);
        }
        catch (IOException e) {
            e.printStackTrace();

            //TODO: Traitez l'erreur
        }

        Intent pdfFromDocumentIntent = new Intent(Intent.ACTION_GET_CONTENT);
        pdfFromDocumentIntent.setType("application/pdf");
        // On vérifie qu'une caméra est disponible
        if(pdfFromDocumentIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(pdfFromDocumentIntent, "Ouvrir avec"), CHOOSE_PDF_FROM_DOCUMENT);
        }
        else {
            // Aucune application de stockage de documents
            // Décidez de ce que vous souhaitez faire dans ce cas
        }
    }

    /**
     *  Méthodes utilitaires
     */

    private void createCustomTempFile(int requestCode) throws IOException {
        // Création d'un fichier temporaire
        // Permet de récupérer l'image entière de la photo prise
        // Sans la création d'un fichier nous ne recevrons que une image simplifié et réduite ( Affichage flou en grand format )

        // Nom du fichier temporaire le temps du traitement
        String filename;
        String extension;

        if(requestCode == CHOOSE_PDF_FROM_DOCUMENT) {
            filename = "PDF_" + System.currentTimeMillis() + "_file";
            extension = ".pdf";
        }
        else {
            filename = "JPEG_" + System.currentTimeMillis() + "_img";
            extension = ".jpg";
        }


        // On récupère le stockage reservé à l'application par Android
        File parentFolder = new File(getFilesDir().getAbsolutePath());

        this.tempFile = File.createTempFile(filename, extension, parentFolder);
    }

    /**
     *
     *  LES méthodes qui effectue un processus de traitement puis affiche l'image
     *
     */

    private void dataFromGalleryOrDocument(@Nullable Intent data, int requestCode) {
        if(data != null && data.getData() != null) {
            final Uri imageUri = data.getData();

            // On écrit dans le fichier les données de l'image
            // Faciliter le traitement
            if (tempFile.exists()) {
                try (OutputStream outputStream = new FileOutputStream(tempFile)) {

                    InputStream inputStream = getContentResolver().openInputStream(imageUri);

                    if (inputStream != null) {
                        byte[] buffer = new byte[4 * 1024]; // or other buffer size
                        int read;

                        while ((read = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, read);
                        }

                        outputStream.flush();
                        inputStream.close();
                    }
                    else {
                        // TODO: 07/01/2024 Gérer l'erreur
                    }
                }
                catch (IOException ioException) {
                    ioException.printStackTrace();
                    // TODO: 07/01/2024 Gérer l'erreur
                }

                displayImageOrDocument(requestCode);
            }
        }
        else {
            // TODO : Traitez l'erreur
        }
    }


    private void displayImageOrDocument(int requestCode) {
        // On récupère le fichier
        if (tempFile != null && tempFile.exists()) {

            /**
             * Une fois à l'intérieur de cette condition on peut faire ce que l'on veut du fichier
             */

            // On créer la bitmap
            Bitmap photoBitmap = BitmapFactory.decodeFile(tempFile.getAbsolutePath());

            // On vérifie l'orientation et si nécessaire on effectue une rotation de la bitmap avant de l'afficher
            int orientation = 0;

            if (requestCode != CHOOSE_PDF_FROM_DOCUMENT) {
                try {
                    orientation = BitmapUtils.getOrientation(tempFile);

                    if (orientation != 0) {
                        photoBitmap = BitmapUtils.rotateBitmap(photoBitmap, orientation);
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();

                    //TODO: Traitez l'erreur
                }
            }
            else {
                // Récupérons la bitmap de la première page du pdf (thumbnail)
                try {
                    photoBitmap = BitmapUtils.getFirstPageBitmapFromPdf(tempFile);
                }
                catch (IOException ioExceptionPdf) {
                    ioExceptionPdf.printStackTrace();
                    // TODO: 07/01/2024 Gérer l'erreur
                }
            }

            if(photoBitmap != null) {
                // Pour obtenir le thumbnail (optionnel))
                final int THUMBSIZE = 64;

                Bitmap ThumbImage =
                        ThumbnailUtils.extractThumbnail(
                                BitmapFactory.decodeFile(tempFile.getAbsolutePath()),
                                THUMBSIZE,
                                THUMBSIZE
                        );

                this.imageView.setImageBitmap(photoBitmap);
            }

        }
        else {
            // L'utilisateur à pu sortir de l'application -> callback non supprimé par le Garbage collector
        }
    }


    /**
     *  La méthode pour récupérer le base64 d'une image prise à l'instant et d'une image tiré de gallerie du téléphone
     *  correspond au même processus étant tout les deux de type jpeg
     */

    private String getBase64FromFile(@NonNull File file) throws IOException {

        String encodedBase64String = "";

        if(file.exists()) {
            InputStream inputStream = new FileInputStream(file.getAbsolutePath());
            byte[] bytes;
            byte[] buffer = new byte[8192]; // La taille peut être modifié -> A tester
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            try {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            bytes = output.toByteArray();
            encodedBase64String = Base64.encodeToString(bytes, Base64.DEFAULT);

            inputStream.close();
            output.close();
        }

        Log.i("Base 64", encodedBase64String);

        return encodedBase64String;
    }
}