package com.codecool.wob.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Data
@AllArgsConstructor
public class ApiRequester {
    private URL url;

    public String getData() {
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int resCode = conn.getResponseCode();

            if (resCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + resCode);
            } else {
                StringBuilder sb = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    sb.append(scanner.nextLine());
                }

                scanner.close();
                return sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
