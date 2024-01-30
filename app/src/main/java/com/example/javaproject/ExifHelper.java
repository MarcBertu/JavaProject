package com.example.javaproject;

public class ExifHelper {

    private ExifHelper() {

    }

    private void getOrientation() {

    }

    private void getData() {

    }

    /**
     *
     */

    /*public void inti() {
        InputStream imageName = getResources().openRawResource(R.raw.first_pic_large);

        ImageView imageView = findViewById(R.id.image_view);

        Bitmap bitmap = BitmapFactory.decodeStream(imageName);

        Log.i("IMAGE WIDTH", String.valueOf(bitmap.getWidth()));
        Log.i("IMAGE HEIGHT", String.valueOf(bitmap.getHeight()));
        Log.i("IMAGE Size", String.valueOf(bitmap.getAllocationByteCount()));

        Handler handler = new Handler();

        Thread t = new Thread() {
            public void run() {

                // Compression de l'image
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 10, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream .toByteArray();
                Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                Bitmap finalBitmap = resizeImage(bmp);


                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("IMAGE WIDTH", String.valueOf(finalBitmap.getWidth()));
                        Log.i("IMAGE HEIGHT", String.valueOf(finalBitmap.getHeight()));
                        Log.i("IMAGE Size", String.valueOf(finalBitmap.getAllocationByteCount()));
                        imageView.setImageBitmap(finalBitmap);
                    }
                });
            }
        };
        //t.start();





        //imageView.setImageBitmap(bitmap);
    }

    public Bitmap resizeImage(Bitmap image) {

        int width = image.getWidth();
        int height = image.getHeight();

        int scaleWidth = width / 1;
        int scaleHeight = height / 1;

        if (image.getByteCount() <= 1000000)
            return image;

        return Bitmap.createScaledBitmap(image, scaleWidth, scaleHeight, false);
    }

    if(resultCode == Activity.RESULT_OK &&  data != null ) {
        // Appareil photo
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");

        Uri uri = data.getData();



            *//*ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 30, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream .toByteArray();

            String b64 = Base64.encodeToString(byteArray, Base64.DEFAULT);

            String filename = "2345676FGTvfezgrer";
            String extension = ".jpeg";

            File tempFile;

            try {
                tempFile = File.createTempFile(filename, extension);

                Log.i("Image Path", tempFile.getPath());

                int orientation = ExifHelper.getImageInfo(this, Uri.parse(tempFile.getPath()));

                Log.i("Image orientation", String.valueOf(orientation));
            }
            catch (Exception e) {
                e.printStackTrace();
            }*//*

        File f3 = new File(uri.getPath());
        Log.i("File Size 1", String.valueOf(f3.length()/1024));

        if (!f3.exists())
            f3.mkdirs();
        OutputStream outStream = null;
        File file = new File(uri.getPath() + ".jpeg");
        try {
            outStream = new FileOutputStream(file);
            Log.i("File Size 2", String.valueOf(f3.length()));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, outStream);
            Log.i("File Size 3", String.valueOf(f3.length()));
            outStream.close();
            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int orientation = ExifHelper.getImageInfo(this, Uri.parse(f3.getPath()));

        Log.i("Image orientation", String.valueOf(orientation));
    }*/

}
