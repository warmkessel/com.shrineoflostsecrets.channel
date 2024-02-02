package com.shrineoflostsecrets.channel.collector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;

public class TwitchTokenRefresher {
    private static final Logger logger = LoggerFactory.getLogger(TwitchTokenRefresher.class);

    private static final String CLIENT_ID = System.getenv("CLIENT_ID");
    private static final String CLIENT_SECRET = System.getenv("CLIENT_SECRET");

    public static void main(String[] args) {
        try {
            Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
            Entity authEntity = getAuthEntityFromDatastore(datastore);

            String refreshToken = authEntity != null ? authEntity.getString("refresh_token") : "";
            String response = requestNewTokens(refreshToken);
//            logger.info("response" = response);
            JSONObject jsonResponse = new JSONObject(response);

            String newAccessToken = jsonResponse.getString("access_token");
            String newRefreshToken = jsonResponse.getString("refresh_token");
            long expiresIn = jsonResponse.getLong("expires_in");

            String scope = ""; // Extract scope

            //            String scope = jsonResponse.getString("scope"); // Extract scope
            String tokenType = jsonResponse.getString("token_type"); // Extract token_type

            updateTokensInDatastore(datastore, newAccessToken, newRefreshToken, expiresIn, scope, tokenType, authEntity);

            
            logger.info("Tokens updated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Entity getAuthEntityFromDatastore(Datastore datastore) {
        Query<Entity> query = Query.newEntityQueryBuilder().setKind("auth").build();
        QueryResults<Entity> results = datastore.run(query);
        return results.hasNext() ? results.next() : null;
    }

    private static String requestNewTokens(String refreshToken) throws IOException {
        URL url = new URL("https://id.twitch.tv/oauth2/token");
        String params = String.format("grant_type=refresh_token&refresh_token=%s&client_id=%s&client_secret=%s", 
                                      refreshToken, CLIENT_ID, CLIENT_SECRET);
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

    private static void updateTokensInDatastore(Datastore datastore, String accessToken, String refreshToken, long expiresIn, String scope, String tokenType, Entity existingEntity) {
        KeyFactory keyFactory = datastore.newKeyFactory().setKind("auth");
        Entity authEntity;
        if (existingEntity != null) {
            authEntity = Entity.newBuilder(existingEntity)
                .set("access_token", accessToken)
                .set("refresh_token", refreshToken)
                .set("expires_in", expiresIn)
                .set("scope", scope) // Include scope in the datastore entity
                .set("token_type", tokenType)
            .build();
        } else {
            Key key = datastore.allocateId(keyFactory.newKey());
            authEntity = Entity.newBuilder(key)
                .set("access_token", accessToken)
                .set("refresh_token", refreshToken)
                .set("expires_in", expiresIn)
                .set("scope", scope) // Include scope in the datastore entity
                .set("token_type", tokenType)
                .build();
        }

        datastore.put(authEntity);
    }
}
