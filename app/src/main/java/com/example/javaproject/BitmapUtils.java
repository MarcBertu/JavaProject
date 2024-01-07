package com.example.javaproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.pdf.PdfRenderer;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BitmapUtils {

    private BitmapUtils() {
        // empty
    }

    public static int getOrientation(@NonNull File file) throws IOException {

        // Il y a des chance que cette partie du code ne fonctionne pas sur un émulateur ( Aucune raison valable trouvé )
        // Aucun crash mais l'orientation sera identique peut importe l'orientation du téléphone

        if(file.exists()) {
            ExifInterface exifInterface = new ExifInterface(file.getAbsolutePath());

            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            switch(orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    return 90;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    return 180;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    return 270;
                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    return 0;
            }
        }

        return 0;


    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        matrix.postRotate(orientation);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        return rotatedBitmap;
    }

    public static Bitmap getFirstPageBitmapFromPdf(@NonNull File pdfFile) throws IOException {
        ParcelFileDescriptor fileDescriptor = ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY);
        PdfRenderer pdfRenderer = new PdfRenderer(fileDescriptor);

        PdfRenderer.Page firstPage = pdfRenderer.openPage(0);

        Bitmap bitmap = Bitmap.createBitmap(firstPage.getWidth(), firstPage.getHeight(), Bitmap.Config.ARGB_8888);
        firstPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        return bitmap;
    }
}
