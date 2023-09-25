package com.example.appveterinaria;

public class Animales {
    private int value;
    private String text;

    public Animales(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return text; // Esto determina qué se mostrará en el Spinner
    }


}
