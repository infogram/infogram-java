package net.infogram.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import net.infogram.api.response.SimpleResponse;
import net.infogram.api.response.Response;

/**
 * A class for building, formatting and signing the request.
 */
class RequestBuilder {

    private final String apiKey;
    private final String apiSecret;
    private final String baseUrl;
    private final String requestMethod;
    private final List<Parameter> parameters;

    /**
     * Constructor of the builder, adds API key and signature to parameters,
     * constructs the base URL to pass on to the connection.
     */
    public RequestBuilder(final String apiKey, final String apiSecret, final String baseUrl,
            final String requestMethod, final List<Parameter> parameters) {

        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.baseUrl = baseUrl;
        this.requestMethod = requestMethod;
        this.parameters = new ArrayList<Parameter>(parameters);

        this.parameters.add(new Parameter("api_key", apiKey));
        String signature = computeSignature(baseUrl, requestMethod, this.parameters);
        this.parameters.add(new Parameter("api_sig", signature));
    }

    /**
     * Computes the signature to use in the request authentication.
     * 
     * @param baseUrl
     * @param requestMethod
     * @param parameters
     *            List of parameters as Parameter objects
     * @return A string containing only the signature.
     */
    private String computeSignature(final String baseUrl, final String requestMethod,
            final List<Parameter> parameters) {
        try {
            String sigBase = URLEncoder.encode(requestMethod, "UTF-8") + "&"
                    + URLEncoder.encode(baseUrl, "UTF-8");
            Collections.sort(parameters);

            String paramString = Helpers.encodedParameterStringFromList(parameters);

            sigBase += "&" + URLEncoder.encode(paramString, "UTF-8");

            byte[] sigBaseHashed = Helpers.calculateRFC2104HMAC(sigBase, apiSecret);

            return DatatypeConverter.printBase64Binary(sigBaseHashed);

        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        } catch (InvalidKeyException e) {
            return null;
        } catch (SignatureException e) {
            return null;
        }
    }

    public Response sendRequest() throws MalformedURLException, IOException {

        HttpURLConnection connection = ConnectionManager.sendRequest(baseUrl, requestMethod, parameters);

        return new SimpleResponse(connection);
    }
}
