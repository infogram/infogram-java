package net.infogram.api.response;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * A class containing the data of the HTTP response. The response body
 * is provided as an InputStream, subclasses implement methods for retrieving
 * the response body in a specific format.
 *
 */
public class SimpleResponse implements Response {

    private static final int SUCCESSFUL_RESPONSE_CODE_FROM = 200;
    private static final int SUCCESSFUL_RESPONSE_CODE_TO = 299;

    protected HttpURLConnection connection;
    private final Map<String, List<String>> headers;
    private final int responseCode;
    private final String responseMessage;

    /**
     * Construct a generic response object for the given connection.
     * @param connection a HttpURLConnection
     * @throws IOException if there was a problem connecting to the server.
     */
    public SimpleResponse(final HttpURLConnection connection) throws IOException {

        this.connection = connection;
        this.responseCode = connection.getResponseCode();
        this.responseMessage = connection.getResponseMessage();
        this.headers = connection.getHeaderFields();

    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    @Override
    public int getHttpStatusCode() {
        return responseCode;
    }

    @Override
    public String getHttpStatusMessage() {
        return responseMessage;
    }

    
    @Override
    public boolean isSuccessful() {

        return responseCode >= SUCCESSFUL_RESPONSE_CODE_FROM
                && responseCode <= SUCCESSFUL_RESPONSE_CODE_TO;
    }

    @Override
    public InputStream getResponseBody() throws IOException {
        if (responseCode >= 200 && responseCode < 300) {
            return connection.getInputStream();
        }
        else {
            return connection.getErrorStream();
        }
    }
}
