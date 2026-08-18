// CalculatorServer.java
import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class CalculatorServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        ExecutorService pool = Executors.newFixedThreadPool(5);
        System.out.println("Server started...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            pool.execute(new ClientHandler(clientSocket));
        }
    }
}

class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        ) {
            String request = in.readLine();
            String[] parts = request.split(" ");
            double a = Double.parseDouble(parts[0]);
            String op = parts[1];
            double b = Double.parseDouble(parts[2]);

            double result = switch (op) {
                case "+" -> a + b;
                case "-" -> a - b;
                case "*" -> a * b;
                case "/" -> b != 0 ? a / b : Double.NaN;
                default -> Double.NaN;
            };

            out.println("Result: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
// CalculatorClient.java
import java.io.*;
import java.net.*;

public class CalculatorClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 12345);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        // Example request: "10 + 5"
        out.println("10 + 5");
        String response = in.readLine();
        System.out.println("Server response: " + response);

        socket.close();
    }
}
