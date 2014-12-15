package net.infogram.api.response;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface Response {

    /** Get the headers of the HTTP response. */
    public Map<String, List<String>> getHeaders();
    
    /** Get the HTTP response code. */
    public int getHttpStatusCode();
    
    /** Get the HTTP response message. */
    public String getHttpStatusMessage();
    
    /** Returns true if the call was successful, false otherwise. */
    public boolean isSuccessful();
    
    /** Get the InputStream of the response body.
     * @throws IOException if there is no response body (e.g. 404 Not Found).
     */
    public InputStream getResponseBody() throws IOException;
    
}
