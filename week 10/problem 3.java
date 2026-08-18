import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    private static final int SERVER_PORT = 12346;
    private static final List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        System.out.println("Chat server starting on port " + SERVER_PORT + "...");

        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Broadcast a message to every connected client
    static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    static void removeClient(ClientHandler client) {
        clients.remove(client);
        broadcast(client.getUsername() + " has left the chat.", client);
        System.out.println(client.getUsername() + " disconnected. Active clients: " + clients.size());
    }

    static class ClientHandler implements Runnable {
        private final Socket socket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        String getUsername() {
            return username;
        }

        void sendMessage(String message) {
            out.println(message);
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // First message from client is treated as their username
                out.println("Enter your username:");
                username = in.readLine();

                System.out.println(username + " has joined. Active clients: " + clients.size());
                broadcast(username + " has joined the chat!", this);

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.equalsIgnoreCase("/quit")) {
                        break;
                    }
                    System.out.println(username + ": " + message);
                    broadcast(username + ": " + message, this);
                }

            } catch (IOException e) {
                System.out.println("Connection lost with " + username);
            } finally {
                removeClient(this);
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
