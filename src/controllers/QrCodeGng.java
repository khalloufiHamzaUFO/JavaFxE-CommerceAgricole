package controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.HashMap;
import java.util.Map;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import com.google.zxing.client.j2se.MatrixToImageWriter;


public class QrCodeGng{
    public static Image generateQRCodeImage(String text, int width, int height) throws WriterException {
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        BitMatrix matrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
        BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(matrix);
        return SwingFXUtils.toFXImage(qrCodeImage, null);
    }
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        Image qrCodeImage = generateQRCodeImage("Hello, world!", 200, 200);
//
//        Stage stage = new Stage();
//        ImageView imageView = new ImageView(qrCodeImage);
//        Scene scene = new Scene(new StackPane(imageView));
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        Application.launch(args);
//    }
}