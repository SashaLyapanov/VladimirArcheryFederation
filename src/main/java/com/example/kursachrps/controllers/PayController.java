package com.example.kursachrps.controllers;

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
        return "9294b692-9a32-48b5-afc2-f8b1abffe321";
    }

//    @PostMapping("/toPay")
//    public String getLinkToPay() throws IOException, InterruptedException {
//        String unikPayId = gen_uuid();
//
////        String curl = "curl https://api.yookassa.ru/v3/payments \\\n" +
////                "  -X POST \\\n" +
////                "  -u 323790:test_pltUB_Z4CVcSkg3AC83PV2YsE3nvQsO4LUZfiOLo4bA \\\n" +
////                "  -H 'Idempotence-Key: "  + unikPayId + "' \\\n" +
////                "  -H 'Content-Type: application/json' \\\n" +
////                "  -d '{\n" +
////                "        \"amount\": {\n" +
////                "          \"value\": \"100.00\",\n" +
////                "          \"currency\": \"RUB\"\n" +
////                "        },\n" +
////                "        \"capture\": true,\n" +
////                "        \"confirmation\": {\n" +
////                "          \"type\": \"redirect\",\n" +
////                "          \"return_url\": \"https://www.example.com/return_url\"\n" +
////                "        },\n" +
////                "        \"description\": \"Заказ №1\"\n" +
////                "      }'";
//
////        ProcessBuilder pb = new ProcessBuilder(curl.split(" "));
//        ProcessBuilder pb = new ProcessBuilder("curl", "https://api.yookassa.ru/v3/payments", "-X", "POST", "-u", "323790:test_pltUB_Z4CVcSkg3AC83PV2YsE3nvQsO4LUZfiOLo4bA",
//                "-H", "'Idempotence-Key:", unikPayId+"'", "-H", "'Content-Type:",
//                "application/json'", "-d", "'{", "\"amount\":", "{", "\"value\":", "\"100.00\",", "\"currency\":", "\"RUB\"", "},", "\"capture\":", "true,",
//                "\"confirmation\":", "{", "\"type\":", "\"redirect\",", "\"return_url\":", "\"https://www.example.com/return_url\"", "},",
//                "\"description\":", "\"Заказ №1\"", "}'");
//        Process process = pb.start();
//        InputStream inputStream = process.getInputStream();
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//
//        StringBuilder sb = new StringBuilder();
//
//        reader.lines().forEach(line -> {
//            sb.append(line);
//        });
//        reader.close();
//
//        process.waitFor();
//
//        process.destroy();
//
//        return sb.toString();
//    }

    @PostMapping("/toPay")
    public String getLinkToPay() throws IOException, InterruptedException {
        URL url = new URL("https://api.yookassa.ru/v3/payments");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        http.setRequestMethod("POST");
        http.setDoOutput(true);
        http.setRequestProperty("Idempotence-Key", "9294b692-9a32-48b5-afc2-f8b1abffe321");
        http.setRequestProperty("Content-Type", "application/json");
        http.setRequestProperty("Authorization", "Basic MzIzNzkwOnRlc3RfcGx0VUJfWjRDVmNTa2czQUM4M1BWMllzRTNudlFzTzRMVVpmaU9MbzRiQQ==");

        String data = "{\n        \"amount\": {\n          \"value\": \"100.00\",\n          \"currency\": \"RUB\"\n        },\n        \"capture\": true,\n        \"confirmation\": {\n          \"type\": \"redirect\",\n          \"return_url\": \"https://www.example.com/return_url\"\n        },\n        \"description\": \"Заказ №1\"\n      }";

        byte[] out = data.getBytes(StandardCharsets.UTF_8);

        OutputStream stream = http.getOutputStream();
        stream.write(out);

        String response = http.getResponseCode() + " " + http.getResponseMessage();
        System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
        http.disconnect();
        return stream.toString();
    }
}
