package com.shrineoflostsecrets.channel.collector;

import com.google.cloud.datastore.*;
import com.shrineoflostsecrets.channel.collector.Bot;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitchTokenRefresher {
	private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    // Environment Variables for Client ID and Secret
    private static final String CLIENT_ID = System.getenv("CLIENT_ID");
    private static final String CLIENT_SECRET = System.getenv("CLIENT_SECRET");

    public static void main(String[] args) {
        try {
            // Initialize Google Datastore client
            Datastore datastore = DatastoreOptions.getDefaultInstance().getService();

            // Fetch the refresh token from the Datastore
            String refreshToken = getRefreshTokenFromDatastore(datastore);

            // Make POST request to Twitch API
            String response = requestNewTokens(refreshToken);
            JSONObject jsonResponse = new JSONObject(response);

            // Extract new tokens from response
            String newAccessToken = jsonResponse.getString("access_token");
            String newRefreshToken = jsonResponse.getString("refresh_token");

            // Update tokens in Google Datastore
            updateTokensInDatastore(datastore, newAccessToken, newRefreshToken);

            logger.info("Tokens updated successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getRefreshTokenFromDatastore(Datastore datastore) {
        Query<Entity> query = Query.newEntityQueryBuilder().setKind("auth").build();
        QueryResults<Entity> results = datastore.run(query);
        Entity latestEntity = results.hasNext() ? results.next() : null;
        return latestEntity != null ? latestEntity.getString("refresh_token") : "";
    }

    private static String requestNewTokens(String refreshToken) throws IOException {
        URL url = new URL("https://id.twitch.tv/oauth2/token");
        String params = String.format("grant_type=refresh_token&refresh_token=%s&client_id=%s&client_secret=%s", 
                                      refreshToken, CLIENT_ID, CLIENT_SECRET);
        logger.info("params {}", params);

        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            byte[] input = params.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            return response.toString();
        }
    }

    private static void updateTokensInDatastore(Datastore datastore, String accessToken, String refreshToken) {
        KeyFactory keyFactory = datastore.newKeyFactory().setKind("auth");
        Key key = datastore.allocateId(keyFactory.newKey());
        Entity authEntity = Entity.newBuilder(key)
            .set("access_token", accessToken)
            .set("refresh_token", refreshToken)
            .build();

        datastore.put(authEntity);
    }
}