package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.example.util.Utils;
import org.example.net.Request;
import org.example.net.Config;
import org.example.net.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        Scanner scanner = new Scanner((System.in));

        String input;
        String purchase;
        int sum;

        while (true) {
            System.out.println("Enter 'purchaseTitle_sum' or 'end' to break");

            input = scanner.nextLine();

            if (input.equalsIgnoreCase("end")) {
                System.out.println("Ciao!");
                break;
            }

            try {
                String[] parts = input.split(" ");
                purchase = parts[0];
                sum = Integer.parseInt(parts[1]);
            }  catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid input, try again");
                continue;
            }

            try (Socket socket = new Socket(Config.HOST, Config.PORT);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ) {

//                Request request = new Request("булка", Utils.TIME_FORMATTER.format(LocalDateTime.now()), 200);
                Request request = new Request(purchase, "2022.02.08", sum);
                out.println(Utils.mapper.writeValueAsString(request));

                String responseAsString = in.readLine();
                System.out.println("Response: " + responseAsString);

                Response response = Utils.mapper.readValue(responseAsString, new TypeReference<Response>() {});

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}