package com.arnold.mna.abcinsurance;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

class Functions {

    public static byte[] returnBas64Image(Bitmap thumbnail) {
        //complete code to save image on server
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();
//        String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
        return b;
    }
}
