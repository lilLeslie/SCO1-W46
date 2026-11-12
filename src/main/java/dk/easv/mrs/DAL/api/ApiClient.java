package dk.easv.mrs.DAL.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class ApiClient {
    private final String urlString;

    public ApiClient(String urlString) throws MalformedURLException {
        this.urlString = urlString;
    }

    public JSONArray getJsonArray(String query) {
        try {
            URL url = new URL(urlString + query);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder infoString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    infoString.append(scanner.nextLine());
                }
                scanner.close();

                JSONParser parser = new JSONParser();

                return (JSONArray) parser.parse(String.valueOf(infoString));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getJsonObject(String query) {
        try {

            URL url = new URL(urlString + query);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200){
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder infoString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()){
                    infoString.append(scanner.nextLine());
                }
                scanner.close();

                JSONParser parser = new JSONParser();
                return (JSONObject) parser.parse(String.valueOf(infoString));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
