package am.infogr.api;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import am.infogr.api.response.EmptyResponse;
import am.infogr.api.response.GraphicsResponse;
import am.infogr.api.response.JsonResponse;
import am.infogr.api.response.PdfResponse;

/**
 * Simple API call library. Supports the calls described in
 * http://developers.infogr.am/rest-api.
 */
public class InfogramAPI {

    private final String consumer_key;
    private final String consumer_secret;

    public InfogramAPI(final String consumer_key, final String consumer_secret) {
        this.consumer_key = consumer_key;
        this.consumer_secret = consumer_secret;
    }

    /**
     * Get the metadata of the infographic.
     * 
     * @param id
     *            The ID of the infographic.
     * @return A JsonResponse containing the infographic metadata.
     */
    public JsonResponse getInfographic(final String id) throws MalformedURLException, IOException {
        RequestBuilder requestBuilder = RequestBuilder.getInfographicBuilder(consumer_key, consumer_secret, id);
        JsonResponse response = (JsonResponse) requestBuilder.sendRequest();

        return response;
    }

    /**
     * Get all infographics.
     * 
     * @return A JsonResponse containing the list of all infographics of the
     *         current user.
     */
    public JsonResponse getInfographicList() throws MalformedURLException, IOException {
        RequestBuilder requestBuilder = RequestBuilder.getAllInfographicsBuilder(consumer_key,
                consumer_secret);
        JsonResponse response = (JsonResponse) requestBuilder.sendRequest();

        return response;
    }

    /**
     * Get all infographics of a given user.
     * 
     * @param userID
     *            The ID (username) of the user.
     * @return A JsonResponse containing the list of all infographics of the
     *         specified user.
     */
    public JsonResponse getUserInfographicList(final String userID) throws MalformedURLException, IOException {
        RequestBuilder requestBuilder = RequestBuilder.getUserInfographicsBuilder(consumer_key,
                consumer_secret, userID);
        JsonResponse response = (JsonResponse) requestBuilder.sendRequest();

        return response;
    }

    /**
     * Get all themes of the current user.
     * 
     * @return A JsonResponse containing the list of all themes of the current
     *         user.
     */
    public JsonResponse getThemeList() throws MalformedURLException, IOException {
        RequestBuilder requestBuilder = RequestBuilder.getThemeListBuilder(consumer_key, consumer_secret);
        JsonResponse response = (JsonResponse) requestBuilder.sendRequest();

        return response;
    }

    /**
     * Create a new infographic. For the parameter format, see
     * http://developers.infogr.am/rest-api/content-schema.html
     * 
     * @param content
     *            The content of the infographic as a JSON string.
     * @param theme_id
     *            The ID of the theme.
     * @param optParams
     *            A key value map of optional parameters.
     * @return A JsonResponse containing of the metadata of the created
     *         infographic.
     */
    public JsonResponse createInfographic(final String content, final int theme_id,
            final Map<String, String> optParams) throws MalformedURLException, IOException {
        RequestBuilder requestBuilder = RequestBuilder.postInfographicBuilder(consumer_key, consumer_secret,
                content, theme_id, optParams);
        JsonResponse response = (JsonResponse) requestBuilder.sendRequest();

        return response;
    }

    /**
     * Update an infographic. For the parameters and their format, see
     * http://developers.infogr.am/rest-api/put-infographics-id.html
     * 
     * @param id
     *            The infographic ID
     * @param parameters
     *            A key value map of optional parameters.
     */
    public EmptyResponse updateInfographic(final String id, final Map<String, String> parameters)
            throws MalformedURLException, IOException {
        RequestBuilder requestBuilder = RequestBuilder.putInfographicBuilder(consumer_key, consumer_secret,
                id, parameters);
        EmptyResponse response = (EmptyResponse) requestBuilder.sendRequest();

        return response;
    }

    /**
     * Delete an infographic.
     * 
     * @param id
     *            The ID of the infographic to be deleted.
     */
    public EmptyResponse deleteInfographic(final String id) throws MalformedURLException, IOException {
        RequestBuilder requestBuilder = RequestBuilder.deleteInfographicBuilder(consumer_key,
                consumer_secret, id);
        EmptyResponse response = (EmptyResponse) requestBuilder.sendRequest();

        return response;
    }

    /**
     * Get an image (PNG) of the infographic.
     * 
     * @param id
     *            The ID of the infographic.
     * @return A GraphicsResponse containing the image.
     */
    public GraphicsResponse getInfographicPng(final String id) throws MalformedURLException, IOException {
        RequestBuilder requestBuilder = RequestBuilder.getInfographicPngBuilder(consumer_key,
                consumer_secret, id);
        GraphicsResponse response = (GraphicsResponse) requestBuilder.sendRequest();

        return response;
    }

    /**
     * Get a PDF of the infographic.
     * 
     * @param id
     *            The ID of the infographic
     * @return A PdfResponse containing the PDF (as a stream only).
     */
    public PdfResponse getInfographicPdf(final String id) throws MalformedURLException, IOException {
        RequestBuilder requestBuilder = RequestBuilder.getInfographicPdfBuilder(consumer_key,
                consumer_secret, id);
        PdfResponse response = (PdfResponse) requestBuilder.sendRequest();

        return response;

    }
}
