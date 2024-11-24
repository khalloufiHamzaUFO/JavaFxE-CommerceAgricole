package dashBoardFront;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import models.Produit;

public class ItemController {
    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLable;

    @FXML
    private ImageView img;

    private Produit p;
    private MyListener myListener;
    public static final String CURRENCY = "dt";


    @FXML
    private void click(MouseEvent mouseEvent) {
        myListener.onClickListener(p);
    }

    public void setData(Produit p, MyListener myListener) {
        this.p = p;
        this.myListener = myListener;
        nameLabel.setText(p.getNom());
        priceLable.setText(CURRENCY + p.getPrix());
        Image image = new Image(getClass().getResourceAsStream("/image/"+p.getImage()));
        img.setImage(image);
    }
}
