import java.io.*;
import java.net.*;
import java.util.*;

public class HTTPServer {
    private ServerSocket serverSocket;
    private GameState gameState;

    public HTTPServer(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.gameState = new GameState();
    }

    public void start() throws IOException {
        System.out.println("Server started on port " + serverSocket.getLocalPort());
        
        while (true) {
            Socket clientSocket = serverSocket.accept();
            handleClient(clientSocket);
        }
    }

    private void handleClient(Socket clientSocket) {
        try (
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        ) {
            // Read the request
            String line;
            StringBuilder requestBuilder = new StringBuilder();
            Map<String, String> headers = new HashMap<>();
            String body = null;

            line = in.readLine();
            if (line == null) {
                throw new IOException("Client closed the connection.");
            }
            requestBuilder.append(line).append("\r\n");

            // Extract method and path
            String[] requestLine = line.split(" ");
            String method = requestLine[0];
            String path = requestLine[1];

            // Read headers
            while (!(line = in.readLine()).isBlank()) {
                requestBuilder.append(line).append("\r\n");
                String[] header = line.split(": ");
                headers.put(header[0], header[1]);
            }

            // If POST, read the body
            if ("POST".equalsIgnoreCase(method)) {
                int contentLength = Integer.parseInt(headers.getOrDefault("Content-Length", "0"));
                char[] bodyChars = new char[contentLength];
                in.read(bodyChars, 0, contentLength);
                body = new String(bodyChars);
            }

            // Process request
            String response = processRequest(method, path, body);

            // Send the response
            out.print(response);
            out.flush();
        } catch (IOException e) {
            System.err.println("Error handling client request: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String processRequest(String method, String path, String body) {
        // Initialize a StringBuilder to construct the response
        StringBuilder responseBuilder = new StringBuilder();

        // Write the status line
        responseBuilder.append("HTTP/1.1 200 OK\r\n");

        // Write the necessary headers for CORS and content type
        responseBuilder.append("Access-Control-Allow-Origin: *\r\n");
        responseBuilder.append("Access-Control-Allow-Methods: GET, POST, OPTIONS\r\n");
        responseBuilder.append("Access-Control-Allow-Headers: Content-Type\r\n");
        responseBuilder.append("Access-Control-Max-Age: 86400\r\n");
        responseBuilder.append("Content-Type: application/json\r\n");

        // Leave a blank line between headers and body
        responseBuilder.append("\r\n");

        // Handle different paths and methods
        if (path.equals("/") && method.equals("GET")) {
            // Return the game state as JSON
            responseBuilder.append(gameState.toJson());
        } else if (path.startsWith("/action") && method.equals("POST")) {
            // Apply action to the game state
            try {
                gameState.applyActionFromJson(body);
                // Return updated game state as JSON
                responseBuilder.append(gameState.toJson());
            } catch (Exception e) {
                // Handle any exceptions from applying the action and create an error response
                responseBuilder = new StringBuilder();
                responseBuilder.append("HTTP/1.1 400 Bad Request\r\n");
                responseBuilder.append("Access-Control-Allow-Origin: *\r\n");
                responseBuilder.append("Content-Type: application/json\r\n");
                responseBuilder.append("\r\n");
                responseBuilder.append("{\"error\":\"" + e.getMessage() + "\"}");
            }
        }

        // Return the response as a string
        return responseBuilder.toString();
    }

    public static void main(String[] args) {
        int port = 12650; // Set the server to run on port 12650
        try {
            HTTPServer server = new HTTPServer(port);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}