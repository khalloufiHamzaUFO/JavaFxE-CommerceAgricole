/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package templates.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import jdk.jshell.execution.Util;
import models.user.Utilisateur;
import services.ServiceUser;

import java.io.File;
import java.io.IOException;
import java.security.Provider;


public class LoginRegister extends Application {
    ServiceUser serviceUser = new ServiceUser();

    static Utilisateur u = new Utilisateur("[\"ROLE_Admin\"]");


    @Override
    public void start(Stage primaryStage) {
        System.out.println('1');
        File sessionFile = new File("session.txt");
        if (sessionFile.exists()) {
            try {
                if (u.getRoles()=="[\"ROLE_Admin\"]") {
                    Parent root = FXMLLoader.load(getClass().getResource("../../dashBoard/Home.fxml"));
                    primaryStage.getIcons().add(new Image("/image/bio.png"));
                    primaryStage.setTitle("AgroEasy");
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                System.out.println('4');
            }
            try {
                if (u.getRoles()=="[\"ROLE_USER\"]") {
                    Parent root = FXMLLoader.load(getClass().getResource("../../dashBoardFront/market.fxml"));
                    primaryStage.getIcons().add(new Image("/image/bio.png"));
                    primaryStage.setTitle("AgroEasy");
                    primaryStage.setScene(new Scene(root));
                    primaryStage.show();
                }
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                System.out.println('4');
            }
        } else {
            try {
                Parent root = FXMLLoader.load(getClass().getResource("LoginK.fxml"));
                primaryStage.getIcons().add(new Image("/image/bio.png"));
                primaryStage.setTitle("AgroEasy");
                primaryStage.setScene(new Scene(root));
                primaryStage.show();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
