package com.uniminuto.empleados.controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.uniminuto.empleados.R;

public class ProgressDialog {
    MaterialAlertDialogBuilder builder;
    AlertDialog dialog;

    public ProgressDialog() {

    }

    public void showProgressDialog(Context context) {
        builder = new MaterialAlertDialogBuilder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null);
        builder.setView(view);

        dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);
    }

    public void showProgressDialog(Context context, String message) {
        builder = new MaterialAlertDialogBuilder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null);
        builder.setView(view);
        TextView messageView = view.findViewById(R.id.progressBarMessage);
        messageView.setText(message);

        dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);
    }

    public void hideProgressDialog() {
        if(dialog != null && builder != null) {
            dialog.hide();
            dialog.cancel();
            dialog = null;
            builder = null;
        }
    }
}
