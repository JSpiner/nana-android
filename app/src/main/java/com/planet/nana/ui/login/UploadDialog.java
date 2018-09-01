package com.planet.nana.ui.login;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.ProgressBar;

import com.planet.nana.R;

public class UploadDialog extends AlertDialog {

    public UploadDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_upload);

        setCancelable(false);

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress);
        progressBar.getIndeterminateDrawable().setColorFilter(
                Color.parseColor("#015eff"),
                android.graphics.PorterDuff.Mode.SRC_IN
        );
    }
}
