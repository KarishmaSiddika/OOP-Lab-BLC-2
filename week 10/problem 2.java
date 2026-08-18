import java.io.*;
import java.net.*;

public class CalculatorServer {
    public static void main(String[] args) {
        int port = 5000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Calculator Server started on port " + port);
            System.out.println("Waiting for client connection...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected: " + socket.getInetAddress());

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            while (true) {
               
                String operator = in.readUTF();

                if (operator.equalsIgnoreCase("exit")) {
                    System.out.println("Client requested to close connection.");
                    break;
                }

                double num1 = in.readDouble();
                double num2 = in.readDouble();

                double result;
                String errorMessage = null;

                switch (operator) {
                    case "+":
                        result = num1 + num2;
                        break;
                    case "-":
                        result = num1 - num2;
                        break;
                    case "*":
                        result = num1 * num2;
                        break;
                    case "/":
                        if (num2 == 0) {
                            result = 0;
                            errorMessage = "Error: Division by zero";
                        } else {
                            result = num1 / num2;
                        }
                        break;
                    default:
                        result = 0;
                        errorMessage = "Error: Unknown operator '" + operator + "'";
                }

                if (errorMessage != null) {
                    out.writeUTF(errorMessage);
                } else {
                    out.writeUTF("Result: " + result);
                }
                out.flush();

                System.out.println("Computed: " + num1 + " " + operator + " " + num2
                        + " -> " + (errorMessage != null ? errorMessage : result));
            }

            in.close();
            out.close();
            socket.close();

        } catch (IOException e) {
            System.err.println("Server exception: " + e.getMessage());
        }
    }
}
