package am.infogr.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import am.infogr.api.response.Response;

/**
 * Simple API call library for proxying the calls described in
 * http://developers.infogr.am/rest.
 */
public class InfogramAPI {

    private final String consumerKey;
    private final String consumerSecret;

    public InfogramAPI(final String consumerKey, final String consumerSecret) {
        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
    }

    /**
     * Constructs the Future object that contains the Response with the data.
     * 
     * @param requestMethod
     *            GET, POST, PUT or DELETE
     * @param target
     *            The target of the call, e.g. infographics
     * @param parameters
     *            A key-value map of parameters excluding api_key and api_sig.
     * @param responseType
     *            the response type for a formatted result
     * @return
     */
    public RunnableFuture<Response> sendAsyncRequest(final String requestMethod, final String target,
            final Map<String, String> parameters, final ResponseType responseType) {
        return new FutureTask<Response>(new Callable<Response>() {
            @Override
            public Response call() throws Exception {
                return sendRequest(requestMethod, target, parameters, responseType);
            }
        });
    }

    /**
     * Constructs a Future object containing a generic Response object providing
     * InputStream only.
     * 
     * @param requestMethod
     *            GET, POST, PUT or DELETE
     * @param target
     *            The target of the call, e.g. infographics
     * @param parameters
     *            A key-value map of parameters excluding api_key and api_sig.
     * @return
     */
    public RunnableFuture<Response> sendAsyncRequest(final String requestMethod, final String target,
            final Map<String, String> parameters) {
        return sendAsyncRequest(requestMethod, target, parameters, ResponseType.GENERIC);
    }

    /**
     * Constructs a Response object containing the response code, message,
     * headers and body
     * 
     * @param requestMethod
     *            GET, POST, PUT or DELETE
     * @param target
     *            The target of the call, e.g. infographics
     * @param parameters
     *            A key-value map of parameters excluding api_key and api_sig.
     * @param responseType
     *            The response type for a formatted result
     * @return A Response object or null if there was a problem with the
     *         request.
     * @throws Exception
     */
    public Response sendRequest(final String requestMethod, final String target,
            final Map<String, String> parameters, final ResponseType responseType) throws Exception {
        try {
            String baseUrl = String.format("%s/%s", Constants.URL_BASE, target);

            List<Parameter> paramList = new ArrayList<Parameter>();

            if (parameters != null) {
                paramList = new ArrayList<Parameter>(parameters.size());
                for (Map.Entry<String, String> pair : parameters.entrySet()) {
                    paramList.add(new Parameter(pair.getKey(), pair.getValue()));
                }
            }

            RequestBuilder req = new RequestBuilder(consumerKey, consumerSecret, baseUrl, requestMethod,
                    paramList, responseType);

            return req.sendRequest();

        } catch (Exception e) {
            throw e;
            // return null;
        }
    }

    /**
     * Constructs a generic Response object containing the response code,
     * message, headers and body (InputStream only)
     * 
     * @param requestMethod
     *            GET, POST, PUT or DELETE
     * @param target
     *            The target of the call, e.g. infographics
     * @param parameters
     *            A key-value map of parameters excluding api_key and api_sig.
     * @return A Response object or null if there was a problem with the
     *         request.
     * @throws Exception
     */
    public Response sendRequest(final String requestMethod, final String target,
            final Map<String, String> parameters) throws Exception {
        return sendRequest(requestMethod, target, parameters, ResponseType.GENERIC);
    }

}
