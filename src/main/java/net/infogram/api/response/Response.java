package net.infogram.api.response;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface Response {

    /**
     Get the headers of the HTTP response.
     @return Map containing HTTP response headers
    */
    public Map<String, List<String>> getHeaders();

    /**
     Get the HTTP response code.
     @return HTTP response status code
    */
    public int getHttpStatusCode();

    /**
     Get the HTTP response message.
     @return HTTP response status text
    */
    public String getHttpStatusMessage();

    /**
     Returns true if the call was successful, false otherwise.
     @return boolean, true if the HTTP request was successful
    */
    public boolean isSuccessful();

    /** Get the InputStream of the response body.
     * @throws IOException if there is no response body (e.g. 404 Not Found).
     * @return InputStream containing HTTP response body
     */
    public InputStream getResponseBody() throws IOException;

}
