package com.uniminuto.empleados.models;

import java.util.ArrayList;

public interface FirebaseReponseListener<T> {
    void onDataReceived(ArrayList<T> data);
    void onCancelled(Exception error);

}
