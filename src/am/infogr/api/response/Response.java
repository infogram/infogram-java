package am.infogr.api.response;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * An abstract class containing the data of the HTTP response. The response body
 * is provided as an InputStream, subclasses implement methods for retrieving
 * the response body in a specific format.
 *
 */
public abstract class Response {

    private static final int SUCCESSFUL_RESPONSE_CODE_FROM = 200;
    private static final int SUCCESSFUL_RESPONSE_CODE_TO = 299;

    protected final HttpURLConnection connection;
    private final Map<String, List<String>> headers;
    private final int responseCode;
    private final String responseMessage;
    private final boolean connected;
    protected boolean inputStreamUsed;

    private IOException connectionException;

    public Response(final HttpURLConnection connection) {
        this.connection = connection;
        headers = connection.getHeaderFields();

        boolean connected = true;
        int responseCode = 0;
        String responseMessage = null;

        try {
            responseCode = connection.getResponseCode();
            responseMessage = connection.getResponseMessage();
        } catch (IOException e) {
            connectionException = e;
            connected = false;
        }

        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.connected = connected;
    }

    /**
     * Get the response headers.
     */
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    /**
     * Get the response code.
     * 
     * @return The response code of the server.
     * @throws IOException
     *             If there was a problem connecting to the server.
     */
    public int getResponseCode() throws IOException {
        if (!connected)
            throw connectionException;
        return responseCode;
    }

    /**
     * Get the response message.
     * 
     * @return The response message of the server.
     * @throws IOException
     *             If there was a problem connecting to the server.
     */
    public String getResponseMessage() throws IOException {
        if (!connected)
            throw connectionException;
        return responseMessage;
    }

    /**
     * Indicates whether the call was successful.
     */
    public boolean success() {

        boolean responseCodeSuccess = responseCode >= SUCCESSFUL_RESPONSE_CODE_FROM
                && responseCode <= SUCCESSFUL_RESPONSE_CODE_TO;
        return connected && responseCodeSuccess;
    }

    /**
     * Return the InputStream containing the response body.
     * 
     */
    public InputStream getInputStream() throws IOException {
        inputStreamUsed = true;
        return connection.getInputStream();
    }
}
