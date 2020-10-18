package com.sdn.aivimapandroid.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.google.android.gms.maps.model.LatLng;
import com.sdn.aivimapandroid.R;

import static java.lang.Math.abs;
import static java.lang.Math.atan;

public class AiviUtils {

    static Bitmap getDestinationBitmap() {
        float height = 20f;
        float width = 20f;
        Bitmap bitmap = Bitmap.createBitmap((int) width, (int) height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
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

//
//            rotation = (float) (Math.toDegrees(atan(lngDifference / latDifference)) + 180);
//
//
//            rotation = (float) (90 - Math.toDegrees(atan(lngDifference / latDifference)) + 270);
        } else if (start.latitude >= end.latitude && start.longitude >= end.longitude) {
            rotation = (float) (Math.toDegrees(atan(lngDifference / latDifference)) + 180);

//            rotation =
//                    (float) (90 - Math.toDegrees(atan(lngDifference / latDifference)) + 270);
        } else if (start.latitude < end.latitude && start.longitude >= end.longitude) {
            rotation =
                    (float) (90 - Math.toDegrees(atan(lngDifference / latDifference)) + 270);
        }
        return rotation;
    }
}
