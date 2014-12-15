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
public class GenericResponse implements Response {

    private static final int SUCCESSFUL_RESPONSE_CODE_FROM = 200;
    private static final int SUCCESSFUL_RESPONSE_CODE_TO = 299;

    protected HttpURLConnection connection;
    private final Map<String, List<String>> headers;
    private final int responseCode;
    private final String responseMessage;
    protected boolean inputStreamUsed;

    /**
     * Construct a generic response object for the given connection.
     * @param connection
     * @throws IOException if there was a problem connecting to the server.
     */
    public GenericResponse(final HttpURLConnection connection) throws IOException {

        this.connection = connection;
        this.responseCode = connection.getResponseCode();
        this.responseMessage = connection.getResponseMessage();
        this.headers = connection.getHeaderFields();
        inputStreamUsed = false;
        
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
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * Get the response message.
     * 
     * @return The response message of the server.
     */
    public String getResponseMessage() throws IOException {
        return responseMessage;
    }

    
    /**
     * Indicates whether the call was successful.
     * 
     * @return true if response status code indicates success, false otherwise.
     */
    public boolean success() {

        return responseCode >= SUCCESSFUL_RESPONSE_CODE_FROM
                && responseCode <= SUCCESSFUL_RESPONSE_CODE_TO;
    }

    /**
     * Return the InputStream containing the response body.
     * @throws IOException if an I/O error occurs while creating the output stream.
     */
    public InputStream getInputStream() throws IOException {
        inputStreamUsed = true;
        return connection.getInputStream();
    }
}
