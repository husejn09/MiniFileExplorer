// File: FajlUnos.java
package com.example.demo;

public class FajlUnos {
    private final int id;
    private final String naziv;
    private final String datumIzmjene;
    private final String tip;

    public FajlUnos(int id, String naziv, String datumIzmjene, String tip) {
        this.id = id;
        this.naziv = naziv;
        this.datumIzmjene = datumIzmjene;
        this.tip = tip;
    }

    public int getId() {
        return id;
    }

    public String getNaziv() {
        return naziv;
    }

    public String getDatumIzmjene() {
        return datumIzmjene;
    }

    public String getTip() {
        return tip;
    }
}
