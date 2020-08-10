package com.booking.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;

import com.booking.activities.App;

public class Cmn {
    static public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) App.context().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
}
