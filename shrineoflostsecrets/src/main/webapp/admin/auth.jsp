<%@ page import="com.google.cloud.datastore.*" %>
<%@ page import="java.util.Optional" %>
<%
    // Initialize Google Datastore client
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    
    // Assume we have a single entity in 'auth' kind to simplify
    Query<Entity> query = Query.newEntityQueryBuilder().setKind("auth").build();
    QueryResults<Entity> results = datastore.run(query);
    Entity latestEntity = results.hasNext() ? results.next() : null;

    // Fetch existing values or set to empty if not present
    String accessToken = latestEntity != null ? latestEntity.getString("access_token") : "";
    String refreshToken = latestEntity != null ? latestEntity.getString("refresh_token") : "";
    String scope = latestEntity != null ? latestEntity.getString("scope") : "";
    String state = latestEntity != null ? latestEntity.getString("state") : "";
    String tokenType = latestEntity != null ? latestEntity.getString("token_type") : "";
    long expiresIn = latestEntity != null && latestEntity.contains("expires_in") ? latestEntity.getLong("expires_in") : 0; // New expires_in field

    // Check if the form was submitted
    boolean isFormSubmitted = request.getMethod().equalsIgnoreCase("POST");
    if (isFormSubmitted) {
        // Extract form data
        accessToken = request.getParameter("access_token");
        refreshToken = request.getParameter("refresh_token");
        scope = request.getParameter("scope");
        state = request.getParameter("state");
        tokenType = request.getParameter("token_type");
        expiresIn = Long.parseLong(request.getParameter("expires_in")); // Extract expires_in from form

        // Create or update entity
        Key key = latestEntity != null ? latestEntity.getKey() : datastore.allocateId(datastore.newKeyFactory().setKind("auth").newKey());
        Entity.Builder builder = Entity.newBuilder(key)
            .set("access_token", accessToken)
            .set("refresh_token", refreshToken)
            .set("scope", scope)
            .set("state", state)
            .set("token_type", tokenType)
            .set("expires_in", expiresIn); // Add expires_in to the entity

        Entity authEntity = builder.build();

        // Save the entity
        datastore.put(authEntity);
    }
%>
<html>
<body>
    <h2>Update Auth Parameters</h2>
    <form action="" method="POST">
        Access Token: <input type="text" name="access_token" value="<%= accessToken %>" /><br/>
        Refresh Token: <input type="text" name="refresh_token" value="<%= refreshToken %>" /><br/>
        Scope: <input type="text" name="scope" value="<%= scope %>" /><br/>
        State: <input type="text" name="state" value="<%= state %>" /><br/>
        Token Type: <input type="text" name="token_type" value="<%= tokenType %>" /><br/>
        Expires In: <input type="number" name="expires_in" value="<%= expiresIn %>" /><br/> <!-- New expires_in field -->
        <input type="submit" value="Submit" />
    </form>
</body>
</html>
