package com.sdn.aivimapandroid.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.sdn.aivimapandroid.R;

import java.util.List;
import java.util.Locale;

import static java.lang.Math.abs;
import static java.lang.Math.atan;

public class AiviUtils {

    static Bitmap getDestinationBitmap() {
        float height = 20f;
        float width = 20f;
        Bitmap bitmap = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        canvas.drawRect(0F, 0F, width, height, paint);
        return bitmap;
    }

    static Bitmap getCarBitmap(Context context) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_car);
        return Bitmap.createScaledBitmap(bitmap, 50, 100, false);
    }

    static float getRotation(LatLng start, LatLng end) {
        Double latDifference = abs(start.latitude - end.latitude);
        Double lngDifference = abs(start.longitude - end.longitude);
        float rotation = -1F;
        if (start.latitude < end.latitude && start.longitude < end.longitude) {
            rotation = (float) Math.toDegrees(atan(lngDifference / latDifference));
        } else if (start.latitude >= end.latitude && start.longitude < end.longitude) {
            rotation = (float) (90 - Math.toDegrees(atan(lngDifference / latDifference)) + 90);
        } else if (start.latitude >= end.latitude && start.longitude >= end.longitude) {
            rotation = (float) (Math.toDegrees(atan(lngDifference / latDifference)) + 180);
        } else if (start.latitude < end.latitude && start.longitude >= end.longitude) {
            rotation = (float) (90 - Math.toDegrees(atan(lngDifference / latDifference)) + 270);
        }
        return rotation;
    }

    public static String splitDate(String word, String character) {
        String[] words = word.split(character);
        if (words.length > 0 && words[0].contains(".")) {
            String[] word2 = words[1].split("\\.");
            return words[0] + " " + word2[0];
        } else
            return words[0];
    }

    public static String getCompleteAddressString(Context context, double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null && addresses.size() > 0) {
                Address returnedAddress = addresses.get(0);

                StringBuilder strReturnedAddress = new StringBuilder("");
                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            } else {
                Log.i("address", "address not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }
}
