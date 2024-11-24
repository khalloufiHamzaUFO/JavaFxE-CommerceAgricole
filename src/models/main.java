package models;

import controllers.ProduitController;
import services.ServiceCategorie;
import services.ServiceProduit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class main {
    public static void main(String[] args) {

        ServiceCategorie serviceCategorie = new ServiceCategorie();
        ServiceProduit serviceProduit = new ServiceProduit();

        Categorie c = serviceCategorie.getById(4);
        System.out.println(serviceProduit.getByCategorie(c));;
    }

    public static void sendShorty(){
        try {
            // Construct data
            String apiKey = "apikey=" + "NDk2YjM5Mzg3OTYxNGU3MjRjNGY1NTVhNjk1YTY2NzM=";
            String message = "&message=" + "This is your message";
            String sender = "&sender=" + "Jims Autos";
            String numbers = "&numbers=" + "21656497564";

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.txtlocal.com/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            System.out.println(stringBuffer.toString());;
        } catch (Exception e) {
            System.out.println("Error "+e);
        }
    }
}
