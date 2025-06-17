package com.mycompany.mavenproject3.view;

import javafx.scene.control.Button;
import javafx.scene.text.Font;

/** Utility class with helper methods for JavaFX UI components. */
public final class UIUtils {
    private UIUtils() {
        // Utility class
    }

    /**
     * Applies a consistent style to a button used across the application.
     *
     * @param btn button to style
     */
    public static void estilizarBotao(Button btn) {
        btn.setFont(Font.font("Georgia", 15));
        btn.setStyle("-fx-background-color: linear-gradient(to bottom, #ffcc00, #b8860b);" +
                " -fx-text-fill: black; -fx-background-radius: 10px;");
        btn.setPrefHeight(45);
    }
}
