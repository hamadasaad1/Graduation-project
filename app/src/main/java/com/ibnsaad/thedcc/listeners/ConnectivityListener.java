package com.ibnsaad.thedcc.listeners;

public interface ConnectivityListener {
    void getConnectionType(String connectionType);
    void isConnected(boolean isConnected);
}
