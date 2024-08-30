// File: MainUI.java
package com.example.demo;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainUI {
    private TableView<FajlUnos> tabela;
    private TextField unosPolje;
    private TextField pretragaPolje;
    private int trenutniId = 1;
    private VBox vbox;
    private List<FajlUnos> originalItems;

    public BorderPane createMainLayout() {
        Button odaberiFajloveDugme = new Button("Odaberi Fajlove");
        odaberiFajloveDugme.setOnAction(e -> odaberiFajlove());
        Label pretragaLabela = new Label("Pretraga:");
        pretragaPolje = new TextField();
        pretragaPolje.setPrefWidth(200);
        Button pretraziDugme = new Button("Pretraži");
        pretraziDugme.setOnAction(e -> pretraziFajlove());
        HBox pretragaBox = new HBox(10);
        pretragaBox.setPadding(new Insets(5));
        pretragaBox.getChildren().addAll(pretragaLabela, pretragaPolje, pretraziDugme);

        vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(odaberiFajloveDugme, pretragaBox);

        tabela = new TableView<>();
        tabela.setPrefWidth(600);
        tabela.setPrefHeight(400);
        TableColumn<FajlUnos, Integer> idKolona = new TableColumn<>("ID");
        TableColumn<FajlUnos, String> nazivKolona = new TableColumn<>("Naziv");
        TableColumn<FajlUnos, String> datumIzmjeneKolona = new TableColumn<>("Datum Izmjene");
        TableColumn<FajlUnos, String> tipKolona = new TableColumn<>("Tip");
        idKolona.setCellValueFactory(new PropertyValueFactory<>("id"));
        nazivKolona.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        datumIzmjeneKolona.setCellValueFactory(new PropertyValueFactory<>("datumIzmjene"));
        tipKolona.setCellValueFactory(new PropertyValueFactory<>("tip"));
        tabela.getColumns().addAll(idKolona, nazivKolona, datumIzmjeneKolona, tipKolona);

        Button obrisiDugme = new Button("Obriši");
        obrisiDugme.setOnAction(e -> {
            FajlUnos odabraniUnos = tabela.getSelectionModel().getSelectedItem();
            if (odabraniUnos != null) {
                tabela.getItems().remove(odabraniUnos);
                originalItems.remove(odabraniUnos); // Remove from the original list as well
            }
        });
        unosPolje = new TextField();
        unosPolje.setPromptText("Unos...");
        Button unesiDugme = new Button("Unesi");
        unesiDugme.setOnAction(e -> unesiFajlUnos());
        HBox dugmadBox = new HBox(10);
        dugmadBox.setPadding(new Insets(5));
        dugmadBox.getChildren().addAll(obrisiDugme, unosPolje, unesiDugme);

        BorderPane glavniPane = new BorderPane();
        glavniPane.setTop(vbox);
        glavniPane.setCenter(tabela);
        glavniPane.setBottom(dugmadBox);

        return glavniPane;
    }

    private void odaberiFajlove() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Odaberi Fajlove");
        List<File> odabraniFajlovi = fileChooser.showOpenMultipleDialog(null);

        if (odabraniFajlovi != null) {
            List<FajlUnos> unosi = new ArrayList<>();
            for (File fajl : odabraniFajlovi) {
                String naziv = fajl.getName();
                String datumIzmjene = "2023-05-31"; // This is a placeholder
                String tip = getTipFajla(naziv);
                FajlUnos noviUnos = new FajlUnos(trenutniId++, naziv, datumIzmjene, tip);
                unosi.add(noviUnos);
            }
            tabela.getItems().addAll(unosi);
            if (originalItems == null) {
                originalItems = new ArrayList<>(unosi); // Initialize the original list
            } else {
                originalItems.addAll(unosi);
            }
        }
    }

    private void pretraziFajlove() {
        String pretragaTekst = pretragaPolje.getText().trim();
        if (originalItems != null) {
            List<FajlUnos> rezultatiPretrage = originalItems.stream()
                    .filter(unos -> unos.getNaziv().equalsIgnoreCase(pretragaTekst)
                            || String.valueOf(unos.getId()).equals(pretragaTekst))
                    .toList();
            tabela.getItems().clear();
            tabela.getItems().addAll(rezultatiPretrage);
        }
    }

    private void unesiFajlUnos() {
        String naziv = unosPolje.getText().trim();
        if (!naziv.isEmpty()) {
            String datumIzmjene = "2023-05-31"; // This is a placeholder
            String tip = getTipFajla(naziv);
            FajlUnos noviUnos = new FajlUnos(trenutniId++, naziv, datumIzmjene, tip);
            tabela.getItems().add(noviUnos);
            if (originalItems == null) {
                originalItems = new ArrayList<>();
            }
            originalItems.add(noviUnos);
            unosPolje.clear();
        }
    }

    private String getTipFajla(String nazivFajla) {
        int indeksEkstenzije = nazivFajla.lastIndexOf('.');
        if (indeksEkstenzije != -1) {
            return nazivFajla.substring(indeksEkstenzije + 1).toUpperCase();
        }
        return "Fajl";
    }
}
