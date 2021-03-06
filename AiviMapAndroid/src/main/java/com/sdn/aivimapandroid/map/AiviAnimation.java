package com.sdn.aivimapandroid.map;

import android.animation.ValueAnimator;
import android.view.animation.LinearInterpolator;

public class AiviAnimation {

    public static ValueAnimator polyLineAnimator() {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(2000);
        return valueAnimator;
    }

    public static ValueAnimator cabAnimator(int playSpeed, Boolean fromTracking) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        if (fromTracking) valueAnimator.setDuration(1000 / playSpeed);
        else valueAnimator.setDuration(3000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        return valueAnimator;
    }

}
