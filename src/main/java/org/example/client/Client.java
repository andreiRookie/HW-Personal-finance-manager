package org.example.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.example.Utils;
import org.example.server.Config;
import org.example.server.Response;

import javax.crypto.spec.PSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        Scanner scanner = new Scanner((System.in));

        while (true) {
            System.out.println("Enter 'purchaseTitle'_'sum' or 'end' to break");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("end")) {
                System.out.println("Ciao!");
                break;
            }

            String[] parts = input.split(" ");
            String purchase = parts[0];
            int sum = Integer.parseInt(parts[1]);

            try (Socket socket = new Socket(Config.HOST, Config.PORT);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ) {

//                Request request = new Request("булка", Utils.TIME_FORMATTER.format(LocalDateTime.now()), 200);
                Request request = new Request(purchase, "2022.02.08", sum);

                String requestJson = Utils.mapper.writeValueAsString(request);
                System.out.println(requestJson);

                out.println(requestJson);

                String responseAsString = in.readLine();
                System.out.println(responseAsString);

                Utils.mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);

                Response response = Utils.mapper.readValue(responseAsString, new TypeReference<Response>() {});
                System.out.println(response.getCategory() + " " + response.getSum());


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
