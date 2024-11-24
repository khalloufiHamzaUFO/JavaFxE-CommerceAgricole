package controllers;

import com.twilio.Twilio;
import com.twilio.type.PhoneNumber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import models.Categorie;
import models.Produit;
import models.Utilisateur;
import services.ServiceCategorie;
import services.ServiceProduit;
import services.ServiceUsers;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class FormController implements Initializable {


    @FXML
    private CheckBox CheckBox;

    @FXML
    private Button btnFileChooser;

    @FXML
    private Button btnSubmitt;

    @FXML
    private Button btnSubmitModif;

    @FXML
    private ChoiceBox<String> choiceBoxCategorie;

    @FXML
    private TextArea tfDescription;

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfPrix;

    @FXML
    private TextField tfFile;

    @FXML
    private Button btnRetour;

    @FXML
    private ImageView imageView;


    private Produit selectedProduit;

    ServiceCategorie serviceCategorie = new ServiceCategorie();
    ObservableList<Categorie> categories = serviceCategorie.getAll();

    //Twwilo
    // Twilio account SID and auth token
    private static final String ACCOUNT_SID = "###############";
    private static final String AUTH_TOKEN = "################";

    // Twilio phone number to send SMS from
    private static final String FROM_PHONE_NUMBER = "+16073262654";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Categorie> categories = serviceCategorie.getAll();
        ObservableList<String> categoryLabels = FXCollections.observableArrayList(
                categories.stream().map(Categorie::getLabel).collect(Collectors.toList()));
        choiceBoxCategorie.setItems(categoryLabels);
        choiceBoxCategorie.setConverter(new StringConverter<String>() {
            @Override
            public String toString(String label) {
                return label;
            }

            @Override
            public String fromString(String label) {
                return label;
            }
        });
    }

    @FXML
    public void handleButtonSubmitModif() throws IOException {
        String nom = tfNom.getText();
        String description = tfDescription.getText();
        String prixStr = tfPrix.getText();
        String imagePath = tfFile.getText();
        boolean statut = CheckBox.isSelected();

        if (nom.isEmpty() || description.isEmpty() || prixStr.isEmpty() || imagePath.isEmpty()) {
            error("Tous les champs sont obligatoires");
            return;
        }

        float prix = 0;
        try {
            prix = Float.parseFloat(prixStr);
            if (prix <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            error( "Prix invalide");
            return;
        }

        String[] allowedExtensions = {"jpg", "jpeg", "png"};
        if (!isValidFileExtension(imagePath, allowedExtensions)) {
            error( "Fichier image invalide");
            return;
        }

        Categorie selectedCategorie = selectedCategorie();
        if (selectedCategorie == null) {
            error("Veuillez sélectionner une catégorie");
            return;
        }

        ServiceProduit serviceProduit = new ServiceProduit();
        List<Produit> produits = serviceProduit.getAll();
        produits.removeIf(p -> p.getId() == selectedProduit.getId());
        //  System.out.println(produits);

        for (Produit p : produits) {
            if (p != selectedProduit && p.getNom().equals(nom)) {
                error( "Nom déjà utilisé par un autre produit");
                return;
            }
        }

        selectedProduit.setNom(nom);
        selectedProduit.setDescription(description);
        selectedProduit.setPrix(prix);
        selectedProduit.setImage(imagePath);
        selectedProduit.setCategorie(selectedCategorie);
        selectedProduit.setStatus(statut);

        serviceProduit.modifier(selectedProduit);

        tfNom.setText("");
        tfDescription.setText("");
        tfPrix.setText("");
        choiceBoxCategorie.getSelectionModel().clearSelection();
        tfFile.setText("");
        infomat("Produit a été mis a jour avec succée !");
    }


    @FXML
    public void handleButtonAjout() throws IOException {
        String nom = tfNom.getText();
        String description = tfDescription.getText();
        String prixStr = tfPrix.getText();
        String imagePath = tfFile.getText();
        boolean statut = CheckBox.isSelected();

        if (nom.isEmpty() || description.isEmpty() || prixStr.isEmpty() || imagePath.isEmpty()) {
            error( "Tous les champs sont obligatoires");
            return;
        }

        float prix = 0;
        try {
            prix = Float.parseFloat(prixStr);
            if (prix <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            error( "Prix invalide");
            return;
        }

        String[] allowedExtensions = {"jpg", "jpeg", "png"};
        if (!isValidFileExtension(imagePath, allowedExtensions)) {
            error("Fichier image invalide");
            return;
        }

        Categorie selectedCategorie = serviceCategorie.getByLabel(choiceBoxCategorie.getValue());
        if (selectedCategorie == null) {
            infomat("Veuillez sélectionner une catégorie");
            return;
        }

        Produit produit = new Produit(nom, prix, description, statut, imagePath, selectedCategorie);

        ServiceProduit serviceProduit = new ServiceProduit();
        serviceProduit.ajouter(produit);

        tfNom.setText("");
        tfDescription.setText("");
        tfPrix.setText("");
        choiceBoxCategorie.getSelectionModel().clearSelection();
        tfFile.setText("");

        if (produit.getStatus()) {
            sendEmails(produit);
            twiloSms(produit);
        }

        infomat("Produit a été ajouté qvec succée !");
    }


    public boolean isValidFileExtension(String filePath, String[] allowedExtensions) {
        String extension = "";

        int i = filePath.lastIndexOf('.');
        if (i > 0) {
            extension = filePath.substring(i + 1);
        }

        for (String allowedExtension : allowedExtensions) {
            if (extension.equalsIgnoreCase(allowedExtension)) {
                return true;
            }
        }
        return false;
    }

    public Categorie selectedCategorie() {
        String selectedCategoryLabel = choiceBoxCategorie.getValue();
        Categorie selectedCategorie = null;
        for (Categorie categorie : categories) {
            if (categorie.getLabel().equals(selectedCategoryLabel)) {
                selectedCategorie = categorie;
                break;
            }
        }
        return selectedCategorie;
    }

    @FXML
    void testMethode() {
        Categorie selectedCategorie = selectedCategorie();
        if (selectedCategorie != null) {
            System.out.println("Selected category: " + selectedCategorie);
        } else {
            System.out.println("No category selected");
        }
    }

    @FXML
    public void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(btnFileChooser.getScene().getWindow());
        if (selectedFile != null) {
            String fileName = selectedFile.getName();
            Path sourcePath = selectedFile.toPath();
            Path destinationPath = Paths.get("src", "image", fileName);
            try {
                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("File copied to: " + destinationPath.toAbsolutePath());
                Image image = new Image(selectedFile.toURI().toString());
                //imageView.setImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (selectedFile == null) {
            // DO nothing
        } else
            System.out.println(selectedFile.getName());
        tfFile.setText(selectedFile.getName());

    }

    public void setSelectedProduit(Produit produit) {
        selectedProduit = produit;
        tfNom.setText(selectedProduit.getNom());
        tfPrix.setText(String.valueOf(selectedProduit.getPrix()));
        tfDescription.setText(selectedProduit.getDescription());
        choiceBoxCategorie.setValue(selectedProduit.getCategorie().getLabel());
        CheckBox.setSelected(selectedProduit.getStatus());
        tfFile.setText(selectedProduit.getImage());
    }

    public void twiloSms(Produit produit) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        ServiceUsers serviceUsers = new ServiceUsers();
        List<Utilisateur> userList = serviceUsers.getAll();

        // Message content
        for (Utilisateur user : userList) {
            String toPhoneNumber = user.getTelephone();
            String messageBody = "Hello " + user.getNom() + ", here are the details for the product \n" + produit.getNom()
                    + "\n" + "price: " + produit.getPrix() + "\n" + "Description: " + produit.getDescription();

            try {
                // Send message using Twilio API
                com.twilio.rest.api.v2010.account.Message message = com.twilio.rest.api.v2010.account.Message.creator(
                        new PhoneNumber(toPhoneNumber),
                        new PhoneNumber(FROM_PHONE_NUMBER),
                        messageBody).create();

                // Print message SID on success
                System.out.println("SMS sent successfully to " + toPhoneNumber + ". Message SID: " + message.getSid());
            } catch (Exception e) {
                // Handle exception on failure
                System.err.println("Failed to send SMS to " + toPhoneNumber + ": " + e.getMessage());
            }
        }
    }


    public void sendEmails(Produit s) {
        String html = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<title>New product added</title>\n" +
                "\t<style>\n" +
                "\t\tbody {\n" +
                "\t\t\tbackground-color: DarkSalmon;\n" +
                "\t\t\tfont-family: Arial, sans-serif;\n" +
                "\t\t\tfont-size: 16px;\n" +
                "\t\t\tcolor: white;\n" +
                "\t\t\tmargin: 0;\n" +
                "\t\t\tpadding: 0;\n" +
                "\t\t}\n" +
                "\t\th1 {\n" +
                "\t\t\tfont-size: 36px;\n" +
                "\t\t\tcolor: GhostWhite\n;\n" +
                "\t\t\tmargin: 20px;\n" +
                "\t\t\tpadding: 10px;\n" +
                "\t\t\tborder: 3px solid GhostWhite\n;\n" +
                "\t\t\tbackground-color: white;\n" +
                "\t\t\ttext-align: center;\n" +
                "\t\t}\n" +
                "\t\tp {\n" +
                "\t\t\tmargin: 20px;\n" +
                "\t\t\tpadding: 10px;\n" +
                "\t\t\tborder: 3px solid GhostWhite\n;\n" +
                "\t\t\tbackground-color: white;\n" +
                "\t\t\tcolor: black;\n" +

                "\t\t}\n" +
                "\t</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<h1>" + s.getNom() + "</h1>\n" +
                "<p>" + s.getDescription() + "</p>" +
                "<p> Sous la categorie :" + s.getCategorie().getLabel() + "</p>" +
                "<h2> Prix :" + s.getPrix() + " dt</h2>" +
                "</body>\n" +
                "</html>\n";
        ServiceUsers serviceUsers = new ServiceUsers();
        List<Utilisateur> userList = serviceUsers.getAll();

        // Set email properties
        String from = "hamza.khalloofi@gmail.com";
        String username = "hamza.khalloofi@gmail.com";
        String password = "mzhmtazqquhpagrl";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.port", 465);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", 465);

        // Create a session with the email server
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Loop through all users and send them an email
            for (Utilisateur user : userList) {
                // create MimeMessage object
                MimeMessage message = new MimeMessage(session);

                // set from and to address
                message.setFrom(new InternetAddress(from));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getEmail()));

                // set subject and message text
                message.setSubject("New product added. come and check it out");
                message.setContent(html, "text/html");

                // send message
                Transport.send(message);

                System.out.println("Email sent successfully to " + user.getEmail());
            }
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void infomat(String s){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Care");
        alert.setContentText(s);
        alert.showAndWait();
    }


    public void error(String s){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Attetion !");
        alert.setContentText(s);
        alert.showAndWait();
    }
}
