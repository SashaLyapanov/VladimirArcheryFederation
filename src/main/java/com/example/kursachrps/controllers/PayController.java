package com.example.kursachrps.controllers;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/v1/pay")
public class PayController {

    public String gen_uuid() {
        return "9294b692-798456-48b5-afc2-f8b1abff123";
    }


    @PostMapping("/toPay")
    public String getLinkToPay() throws IOException, InterruptedException, JSONException {
        URL url = new URL("https://api.yookassa.ru/v3/payments");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Idempotence-Key", "9294b692-9a32-48b5-afc2-f8b1abffe3358");
        http.setRequestProperty("Content-Type", "application/json");
        http.setRequestProperty("Authorization", "Basic MzIzNzkwOnRlc3RfcGx0VUJfWjRDVmNTa2czQUM4M1BWMllzRTNudlFzTzRMVVpmaU9MbzRiQQ==");

        String data = "{\n        \"amount\": {\n          \"value\": \"100.00\",\n          \"currency\": \"RUB\"\n        },\n        \"capture\": true,\n        \"confirmation\": {\n          \"type\": \"redirect\",\n          \"return_url\": \"http://localhost:3000/\"\n        },\n        \"description\": \"Заказ №1\"\n      }";

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        http.disconnect();

        JSONObject jsonObject = new JSONObject(response.toString());
        System.out.println(jsonObject);

        JSONObject confirmation = jsonObject.getJSONObject("confirmation");

        System.out.println(confirmation);

        String confirmationUrl = confirmation.getString("confirmation_url");

        System.out.println(confirmationUrl);

//        jsonObject.getString("confirmation_url");
//        JSONArray confirmation = jsonObject.getJSONArray("confirmation");
//        confirmation.getString(2);
//        System.out.println(confirmation.getString(1));
//        System.out.println(confirmation.getJSONObject(1).getString("confirmation_url"));
//        System.out.println(jsonObject.getString("confirmation_url"));

        return confirmationUrl;
    }
}
