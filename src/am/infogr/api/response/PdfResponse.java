package am.infogr.api.response;

import java.net.HttpURLConnection;

/**
 * Implementation of the Response class specific for a PDF response. Currently
 * only provides the inherited getInputStream method.
 */
public class PdfResponse extends Response {

    public PdfResponse(HttpURLConnection connection) {
        super(connection);
    }
}
