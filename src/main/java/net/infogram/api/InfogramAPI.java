package net.infogram.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.infogram.api.response.Response;

/**
 * Simple API call library for proxying the calls described in
 * http://developers.infogr.am/rest.
 */
public class InfogramAPI {

    private final String apiKey;
    private final String apiSecret;

    public InfogramAPI(final String apiKey, final String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    /**
     * Constructs a Response object containing the response code, message,
     * headers and body.
     *
     * @param requestMethod
     *            GET, POST, PUT or DELETE
     * @param target
     *            The target of the call, e.g. infographics
     * @param parameters
     *            A key-value map of parameters excluding api_key and api_sig.
     * @return A Response object or null if there was a problem with the
     *         request.
     * @throws java.io.IOException in case of I/O errors
     */
    public Response sendRequest(final String requestMethod, final String target,
            final Map<String, String> parameters) throws IOException {

        String baseUrl = String.format("%s/%s", Constants.URL_BASE, target);

        List<Parameter> paramList = new ArrayList<Parameter>();

        if (parameters != null) {
            paramList = new ArrayList<Parameter>(parameters.size());
            for (Map.Entry<String, String> pair : parameters.entrySet()) {
                paramList.add(new Parameter(pair.getKey(), pair.getValue()));
            }
        }

        RequestBuilder req = new RequestBuilder(apiKey, apiSecret, baseUrl, requestMethod,
                paramList);

        return req.sendRequest();
    }
}
