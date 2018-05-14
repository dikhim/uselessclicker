package org.dikhim.jclicker.controllers;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.dikhim.jclicker.Clicker;
import org.dikhim.jclicker.model.MainApplication;
import org.dikhim.jclicker.server.socket.SocketServer;
import org.dikhim.jclicker.util.Converters;

public class SocketServerController {
    private SocketServer server;
    private MainApplication mainApplication = Clicker.getApplication().getMainApplication();
    @FXML
    private Button btnStartServer;

    @FXML
    private Button btnStopServer;

    @FXML
    private Button btnRegresh;

    @FXML
    private TextField txtPort;

    @FXML
    private TextField txtStatus;

    @FXML
    private ListView<String> listClients;
    ObservableList<String> clientList = FXCollections.observableArrayList();


    @FXML
    private void initialize() {
        server = mainApplication.getSocketServer();
        listClients.setItems(clientList);
        
        bindConfig();
    }

    @FXML
    void startServer(ActionEvent event) {
        server.setPort(Integer.parseInt(txtPort.getText()));
        server.start();
        refresh();
    }

    @FXML
    void stopServer(ActionEvent event) {
        server.stop();
        refresh();
    }

    public void refresh() {
        try {
            Thread.currentThread().sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String status = server.getStatus();
        txtStatus.setText(status);
        txtPort.setText(String.valueOf(server.getPort()));
        clientList.clear();
        clientList.addAll(server.getClientsInfo());
    }

    public void print(String text) {
        System.out.println(text);
    }

    @FXML
    void refreshInfo(ActionEvent event) {
        refresh();
    }

    private void bindConfig() {
        Bindings.bindBidirectional(txtPort.textProperty(), server.portProperty(), Converters.getStringToNumberConvertor());
    }
}
