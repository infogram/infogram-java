package am.infogr.api.response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * Implementation of the Response class specific for a JSON response.
 */
public class JsonResponse extends GenericResponse {

    private String responseBody;

    public JsonResponse(HttpURLConnection connection) throws IOException {
        super(connection);
    }

    public String getResponseBody() throws Exception {
        if (responseBody != null)
            return responseBody;
        if (inputStreamUsed)
            throw new Exception("Response stream already used elsewhere");

        InputStream is = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String resBody = "";
        String line;

        while ((line = reader.readLine()) != null) {
            resBody = resBody.concat(line);
        }

        this.responseBody = resBody;
        inputStreamUsed = true;

        return resBody;
    }
}
