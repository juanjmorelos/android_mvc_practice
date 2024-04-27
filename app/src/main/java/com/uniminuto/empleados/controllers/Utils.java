package com.uniminuto.empleados.controllers;

import android.app.Activity;
import android.content.Intent;

import com.uniminuto.empleados.models.FragmentsController;
import com.uniminuto.empleados.views.ContentActivity;

public class Utils {
    public static void goToFragment(FragmentsController fragment, Activity activity) {
        Intent i = new Intent(activity, ContentActivity.class);
        i.putExtra("fragment", fragment.toString());
        activity.startActivity(i);
        activity.finish();
    }

    public static FragmentsController getFragmentFromPutExtra(String fragment) {
        switch (fragment) {
            case "ADD_EMPLOYEE":
                return FragmentsController.ADD_EMPLOYEE;
            case "ADD_POSITION":
                return FragmentsController.ADD_POSITION;
            case "VIEW_EMPLOYEES":
                return FragmentsController.VIEW_EMPLOYEES;
        }
        return FragmentsController.VIEW_EMPLOYEES;
    }
}
