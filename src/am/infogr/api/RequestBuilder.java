package am.infogr.api;

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

import am.infogr.api.response.GraphicsResponse;
import am.infogr.api.response.JsonResponse;
import am.infogr.api.response.GenericResponse;
import am.infogr.api.response.Response;

/**
 * A class for building, formatting and signing the request.
 */
class RequestBuilder {

    private final String consumerKey;
    private final String consumerSecret;
    private final String baseUrl;
    private final String requestMethod;
    private final List<Parameter> parameters;
    private final ResponseType type;

    /**
     * Constructor of the builder, adds API key and signature to parameters,
     * constructs the base URL to pass on to the connection.
     */
    public RequestBuilder(final String consumerKey, final String consumerSecret, final String baseUrl,
            final String requestMethod, final List<Parameter> parameters, ResponseType type) {

        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.baseUrl = baseUrl;
        this.requestMethod = requestMethod;
        this.parameters = new ArrayList<Parameter>(parameters);
        this.type = type;

        this.parameters.add(new Parameter("api_key", consumerKey));
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

            byte[] sigBaseHashed = Helpers.calculateRFC2104HMAC(sigBase, consumerSecret);

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

        switch (type) {
        case JSON:
            return new JsonResponse(connection);
        case GRAPHIC:
            return new GraphicsResponse(connection);
        case GENERIC:
            return new GenericResponse(connection);
        default:
            throw new UnsupportedOperationException("Unexpected response type");
        }
    }
}
