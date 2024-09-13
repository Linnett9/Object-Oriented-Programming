package NetworkProtocol;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import java.io.StringReader;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

// Client server communication 
public class ServerClient {
    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;
    private static final String TOKEN = e843e840-6b2b-49c5-80cf-1ba9f525a839
    public void connect() throws Exception {
        socket = new Socket("cs5001-p3.dynv6.net", 8080);
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
//login to server with token
public boolean login() throws IOException {
        JsonObject loginJson = Json.createObjectBuilder()
            .add("action", "login")
            .add("data", Json.createObjectBuilder().add("token", TOKEN))
            .build();

        sendJson(loginJson);
        return processResponse(reader.readLine()).getString("result").equals("ok");
    }
//Sends a drawing to the server
    public String addDrawing(JsonObject drawingData) throws Exception {
        JsonObject addDrawingJson = Json.createObjectBuilder()
            .add("action", "addDrawing")
            .add("data", drawingData)
            .build();

        sendJson(addDrawingJson);

        JsonObject response = processResponse(reader.readLine());
        if (response.getString("result").equals("ok")) {
            return response.getJsonObject("data").getString("id");
        } else {
            throw new Exception(response.getString("message"));
        }
    }
// Gets draeing from the server
    public JsonArray getDrawings() throws Exception {
        JsonObject request = Json.createObjectBuilder()
            .add("action", "getDrawings")
            .build();

        sendJson(request);

        return processResponse(reader.readLine()).asJsonArray();
    }
//Updates drawing from the server
    public void updateDrawing(String id, JsonObject properties) throws Exception {
        JsonObject updateDrawingJson = Json.createObjectBuilder()
            .add("action", "updateDrawing")
            .add("data", Json.createObjectBuilder()
                .add("id", id)
                .add("properties", properties))
            .build();

        sendJson(updateDrawingJson);
        processResponse(reader.readLine());
    }
//Deletes a given drawing from the server
    public void deleteDrawing(String id) throws Exception {
        JsonObject deleteDrawingJson = Json.createObjectBuilder()
            .add("action", "deleteDrawing")
            .add("data", Json.createObjectBuilder()
                .add("id", id))
            .build();

        sendJson(deleteDrawingJson);
        processResponse(reader.readLine());
    }
//Sends Json object to the srver
    private void sendJson(JsonObject json) throws Exception {
        try (JsonWriter jsonWriter = Json.createWriter(writer)) {
            jsonWriter.writeObject(json);
            writer.newLine();
            writer.flush();
        }
    }
//Processes json object
    private JsonObject processResponse(String response) throws Exception {
        try (JsonReader jsonReader = Json.createReader(new StringReader(response))) {
            JsonObject jsonResponse = jsonReader.readObject();
            if (jsonResponse.getString("result").equals("ok") || jsonResponse.containsKey("data")) {
                return jsonResponse;
            } else {
                throw new Exception(jsonResponse.getString("message"));
            }
        }
    }
//Closes the connection 
    public void close() throws Exception {
        writer.close();
        reader.close();
        socket.close();
    }
}
