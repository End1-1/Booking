package com.booking.utils;

import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

public class AnimateView {
    public static void animate(View v1, final View v2) {

        if (v1 != null) {
            TranslateAnimation ta = new TranslateAnimation(0, 0, 0, v1.getHeight());
            ta.setDuration(200);
            ta.setFillAfter(true);
            ta.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
                @Override
                public void onAnimationStart(android.view.animation.Animation animation) {

                }

                @Override
                public void onAnimationEnd(android.view.animation.Animation animation) {

                    if (v2 != null) {
                        TranslateAnimation ta2 = new TranslateAnimation(0, 0, v2.getHeight(), 0);
                        ta2.setDuration(200);
                        ta2.setFillAfter(true);
                        ta2.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(android.view.animation.Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(android.view.animation.Animation animation) {

                            }

                            @Override
                            public void onAnimationRepeat(android.view.animation.Animation animation) {

                            }
                        });
                        v2.startAnimation(ta2);
                    }
                }

                @Override
                public void onAnimationRepeat(android.view.animation.Animation animation) {

                }
            });
            v1.startAnimation(ta);
        } else if (v2 != null) {
            TranslateAnimation ta2 = new TranslateAnimation(0, 0, v2.getHeight(), 0);
            ta2.setDuration(200);
            ta2.setFillAfter(true);
            ta2.setAnimationListener(new android.view.animation.Animation.AnimationListener() {
                @Override
                public void onAnimationStart(android.view.animation.Animation animation) {

                }

                @Override
                public void onAnimationEnd(android.view.animation.Animation animation) {

                }

                @Override
                public void onAnimationRepeat(android.view.animation.Animation animation) {

                }
            });
            v2.startAnimation(ta2);
        }
    }
}
