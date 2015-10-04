package com.treak.treak.helpers;

import android.content.Context;
import android.view.View;
import android.support.design.widget.Snackbar;

/**
 * Created by Daryl on 10/3/15.
 */
public class UtilityMethods {

    public void showSnackbar(View view, String s) {
        Snackbar.make(view, s, Snackbar.LENGTH_LONG)
                .setAction("Dismiss", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .show();
    }
}
