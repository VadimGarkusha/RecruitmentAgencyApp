package com.example.vadimgarkusha.vadym_victor_assignment4;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;

public final class DialogBuilder {
    private static Context context;
    public DialogBuilder(Context c) {
        context = c;
    }

    static public void showMessage(String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        final android.app.AlertDialog dialog = builder.create();
        dialog.show();

        final Handler handler  = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        };

        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });

        handler.postDelayed(runnable, 2900);
    }
}
