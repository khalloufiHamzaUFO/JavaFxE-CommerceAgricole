package templates.equipement;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class TestMapController implements Initializable {

    public static double lon;
    public static double lat;

    @FXML
    private WebView webview;
    private WebEngine webengine;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webengine = webview.getEngine();

        url = this.getClass().getResource("leaflettmap.html");
        webengine.load(url.toString());


    }

    private void tt(ActionEvent event) {


        lat = (Double) webview.getEngine().executeScript("lat");
        lon = (Double) webview.getEngine().executeScript("lon");


        System.out.println("Lat: " + lat);
        System.out.println("LOn " + lon);


    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        Stage primarystage = null;
        Stage window = primarystage;
        Parent rootRec2 = FXMLLoader.load(getClass().getResource("../terrain/AfficherTerrain.fxml"));
        Scene rec2 = new Scene(rootRec2);
        Stage app = (Stage) ((Node) event.getSource()).getScene().getWindow();
        app.setScene(rec2);
        app.show();
    }

    private class JavaApp {
        public void exit() {
            Platform.exit();
        }

    }
}