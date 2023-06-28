package no.nav.portal.statusholder.controller;

import no.nav.portal.statusholder.Dto.ServiceStatus;
import no.nav.portal.statusholder.Dto.StatusDto;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.OffsetDateTime;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Util {


    public static boolean checkStatusEndpoint(String urlString){
        String bodyString;
        try {
            HttpURLConnection connection = getConnectionToServicePollEndpoint(urlString);
            bodyString = readBody(connection);
            connection.disconnect();
        }
        catch (IOException e){
            System.out.println("Error: " + e);
            return false;
        }
        JsonObject jsonObject = toJson(bodyString);
        System.out.println(checkForStatus(jsonObject));
        return checkForStatus(jsonObject);
    }

    private static HttpURLConnection getConnectionToServicePollEndpoint(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        return con;
    }

    private static Boolean checkForStatus(JsonObject jsonRecord){
        return jsonRecord.getString("status") != null && !jsonRecord.getString("status").equals("");

    }


    private static String readBody(HttpURLConnection con) throws IOException {

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        return content.toString();
    }

    private static JsonObject toJson(String str){
        JsonReader jsonReader = Json.createReader(new StringReader(str));
        JsonObject object = jsonReader.readObject();
        jsonReader.close();
        return object;
    }
}
