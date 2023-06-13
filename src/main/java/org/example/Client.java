package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
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
        String date;
        int sum;

        while (true) {
            System.out.println("Enter 'purchaseTitle', 'yyyy.mm.dd', 'sum' separated by a space"
                    + " or 'end' to break");

            input = scanner.nextLine();

            if (input.equalsIgnoreCase("end")) {
                System.out.println("Ciao!");
                break;
            }

            try {
                String[] parts = input.split(" ");
                purchase = parts[0];
                date = parts[1];
                sum = Integer.parseInt(parts[2]);
            }  catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Invalid input, try again");
                continue;
            }

            try (Socket socket = new Socket(Config.HOST, Config.PORT);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
            ) {

//                Request request = new Request("булка", Utils.TIME_FORMATTER.format(LocalDateTime.now()), 200);
                Request request = new Request(purchase, date, sum);
                out.println(Utils.mapper.writeValueAsString(request));

                String responseAsString = in.readLine();
                System.out.println("Response: " + responseAsString);
                Response response = Utils.responseMapper.readValue(responseAsString, new TypeReference<Response>() {});
                System.out.println(response);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
