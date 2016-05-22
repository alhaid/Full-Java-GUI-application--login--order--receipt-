/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pizzaproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author H
 */
public class PizzaProject extends Application {

    static Stage stage1 = new Stage();
    static Stage stage2 = new Stage();
    static Stage stage3 = new Stage();

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        Parent root2 = FXMLLoader.load(getClass().getResource("FXMLDocument2.fxml"));
        Parent root3 = FXMLLoader.load(getClass().getResource("FXMLDocument3.fxml"));

        Scene scene = new Scene(root);
        Scene scene2 = new Scene(root2);
        Scene scene3 = new Scene(root3);

        stage1.setScene(scene);
        stage2.setScene(scene2);
        stage3.setScene(scene3);

        showStage1();
    }

    public static void showStage1() {
        stage1.show();
        stage2.hide();
        stage3.hide();
    }

    public static void showStage2() {
        stage1.hide();
        stage2.show();
        stage3.hide();
    }

    public static void showStage3() {
        stage1.hide();
        stage2.hide();
        stage3.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
