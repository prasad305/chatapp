package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServer {
    /**
     * <Question: A static final Logger object is created,
     * which will be used for logging information and errors.
     **/

    public static final Logger logger = Logger.getLogger(ChatServer.class.getName());


    public static void main(String[] args) {
        try {
            /**<Question: Create a new ServerSocket object that listens on port 5000 **/
            ServerSocket serverSocket = new ServerSocket(5000);
            /**<Question: Log an informational message indicating that
             * the server has started and is waiting for clients **/
            logger.info("server has started and is waiting for clients");

            while (true) {

                /**<Question: Accept a new client connection and create a Socket object for communication with the client.        **/
                Socket clientSocket = serverSocket.accept();
                /**<Question: Log an informational message indicating that a client has connected with showing IP address         **/
//                        ------------------------------------------------
                logger.info("client connected IP:" + clientSocket.getInetAddress().getHostAddress());


                /**<Question: Create a new Thread object for each client.
                 * The ClientHandler class implements the Runnable interface, so it can be passed to the Thread constructor.
                 * Start the thread
                 **/

                Thread thread = new Thread(new ClientHandler(clientSocket));
                thread.start();

            }
            /**< Question: Catch input output exception and log the error message with severe level **/
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }


        /**
         * <Question:
         * Create a run method which is void.//1
         * Create a try-catch block to handle exceptions.//2
         * Create a BufferedReader object to read input from the client.//3
         * Create a PrintWriter object to send output to the client.//4
         * Declare a String variable to hold the incoming message.//5
         * A loop that continues as long as there is input from the client.//6
         * Log the received message as info containing IP address of the client and incommingMessage//7
         * Broadcast the message to all connected clients using PrintWriter object//8
         * <p>
         * Catch any IOExceptions that may occur and log the error message.//9
         * In a finally block create a try-catch block, attempt to close the client socket in side the try block and//10
         * log a message indicating that the client has disconnected.//11
         * Catch IOExcpetion and log the error in severe level//12
         **/

        @Override//1
        public void run() {//1

            try {//2
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));//3
                PrintWriter out = new PrintWriter(this.clientSocket.getOutputStream(), true);//4

                String incomingMessage;//5
                while ((incomingMessage = bufferedReader.readLine()) != null) {//6
                    InetAddress clientAddress = this.clientSocket.getInetAddress();
                    logger.info("Received from " + clientAddress.getHostAddress() + ": " + incomingMessage);//7
                    out.println(incomingMessage);//8
                }

            } catch (IOException e) {//9
                logger.info("error occurred : " + e.getMessage());//7
            } finally {
                try {//10
                    clientSocket.close();//10
                    logger.info("Client disconnected from " + this.clientSocket.getInetAddress().getHostAddress());//11
                } catch (IOException e) {
                    logger.log(Level.SEVERE, e.getMessage());//12
                }
            }

        }


    }
}
