package com.company;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class ChatClient {
    public static void main(String[] args) {
        try {

            /**<Question: Create a "new Socket"1 object.
             * 'localhost' and '5000' are the server's hostname and port number respectively.**/
            Socket socket = new Socket("localhost",5000);//1
            System.out.println("Connected to the server.");

            /**< Question
             * A try block that includes "PrintWriter"1 object to get "output stream"2 using "socket"3
             * Create a new object of Scanner class
             * Declare a String variable to hold the user's message.
             * Create An infinite loop to continuously ask the user for input.
             * Read the user's input using scanner.
             * Send the user's message to the server.

             **/
            try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);//1
                 Scanner scanner = new Scanner(System.in)) {//2
                 String message = "";//3
                 while (true) {
                    System.out.println("Please enter your message! ");

                    message = scanner.nextLine();

                    writer.println(message);
                 }
            }
            /**< Question: Catch any "IOExceptions" that may occur and
             * print the stack trace to the console.        **/ //printStackTrace
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
