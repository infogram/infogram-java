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
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import am.infogr.api.response.EmptyResponse;
import am.infogr.api.response.GraphicsResponse;
import am.infogr.api.response.JsonResponse;
import am.infogr.api.response.Response;

/**
 * A class for building, formatting and signing the request.
 */
public class RequestBuilder {
	
	private final String consumer_key;
	private final String consumer_secret;
	private final String baseUrl;
	private final String requestMethod;
	private final List<Parameter> parameters;
	private final ResponseType type;
	
	
	private static RequestBuilder getInfographicBuilder(final String consumer_key, final String consumer_secret, final String id, final ResponseType type) {
		String baseUrl = String.format("%s/infographics/%s", Constants.URL_BASE, id);
		String requestMethod = "GET";
		List<Parameter> parameters = new ArrayList<Parameter>();
		switch(type) {
		case JSON:
			parameters.add(new Parameter("format", "json"));
			break;
		case PNG:
			parameters.add(new Parameter("format", "png"));
			break;
		case PDF:
			parameters.add(new Parameter("format", "pdf"));
			break;
		default:
			// not expected to get here since called from class methods
		}
		
		return new RequestBuilder(consumer_key, consumer_secret, baseUrl, requestMethod, parameters, type);
	}
	
	/**
	 * Create a request builder for the "GET Infographic" call.
	 */
	public static RequestBuilder getInfographicBuilder(final String consumer_key, final String consumer_secret, final String id) {
		return getInfographicBuilder(consumer_key, consumer_secret, id, ResponseType.JSON);
	}
	
	/**
	 * Create a request builder for the "GET PNG Infographic" call.
	 */
	public static RequestBuilder getInfographicPngBuilder(final String consumer_key, final String consumer_secret, final String id) {
		return getInfographicBuilder(consumer_key, consumer_secret, id, ResponseType.PNG);
	}
	
	/**
	 * Create a request builder for the "GET PDF Infographic" call.
	 */
	public static RequestBuilder getInfographicPdfBuilder(final String consumer_key, final String consumer_secret, final String id) {
		return getInfographicBuilder(consumer_key, consumer_secret, id, ResponseType.PDF);
	}

	/**
	 * Create a request builder for the "GET All Infographics" call.
	 */
	public static RequestBuilder getAllInfographicsBuilder(final String consumer_key, final String consumer_secret) {
		String baseUrl = Constants.URL_BASE + "/infographics";
		String requestMethod = "GET";
		List<Parameter> parameters = new ArrayList<Parameter>();
		return new RequestBuilder(consumer_key, consumer_secret, baseUrl, requestMethod, parameters, ResponseType.JSON);
	}
	
	/**
	 * Create a request builder for the "GET User Infographics" call.
	 */
	public static RequestBuilder getUserInfographicsBuilder(final String consumer_key, final String consumer_secret, String userID) {
		String baseUrl = String.format("%s/users/%s/infographics", Constants.URL_BASE, userID);
		String requestMethod = "GET";
		List<Parameter> parameters = new ArrayList<Parameter>();
		return new RequestBuilder(consumer_key, consumer_secret, baseUrl, requestMethod, parameters, ResponseType.JSON);
	}
	
	/**
	 * Create a builder for the "GET Themes" call.
	 */
	public static RequestBuilder getThemeListBuilder(final String consumer_key, final String consumer_secret) {
		String baseUrl = Constants.URL_BASE + "/themes";
		String requestMethod = "GET";
		List<Parameter> parameters = new ArrayList<Parameter>();
		return new RequestBuilder(consumer_key, consumer_secret, baseUrl, requestMethod, parameters, ResponseType.JSON);
	}
	
	/**
	 * Create a builder for the "POST Infographic" call.
	 */
	public static RequestBuilder postInfographicBuilder(final String consumer_key, final String consumer_secret, final String content, final int theme_id, final Map<String, String> optParams) {
		String baseUrl = Constants.URL_BASE + "/infographics";
		String requestMethod = "POST";
		List<Parameter> parameters = new ArrayList<Parameter>();
		parameters.add(new Parameter("content", content));
		parameters.add(new Parameter("theme_id", Integer.toString(theme_id)));
		
		if (optParams != null) {
			// add 'published' parameter if present and valid
			if (optParams.containsKey("published")) {
				String published = optParams.get("published");
				if (published.equals("true") || published.equals("false")) {
					parameters.add(new Parameter("published", published));
				}
			}
			
			// add 'title' if present
			if (optParams.containsKey("title")) {
				parameters.add(new Parameter("title", optParams.get("title")));
			}
		}
		
		return new RequestBuilder(consumer_key, consumer_secret, baseUrl, requestMethod, parameters, ResponseType.JSON);
	}
	
	/**
	 * Create a builder for the "PUT Infographic" call.
	 */
	public static RequestBuilder putInfographicBuilder(final String consumer_key, final String consumer_secret, final String id, final Map<String, String> parameterMap) {
		String baseUrl = String.format("%s/infographics/%s", Constants.URL_BASE, id);
		String requestMethod = "PUT";
		List<Parameter> parameters = new ArrayList<Parameter>();
		
		if (parameterMap != null) {
			// add 'published' parameter if present and valid
			if (parameterMap.containsKey("published")) {
				String published = parameterMap.get("published");
				if (published.equals("true") || published.equals("false")) {
					parameters.add(new Parameter("published", published));
				}
			}
			
			// add 'title' parameter if present
			if (parameterMap.containsKey("title")) {
				parameters.add(new Parameter("title", parameterMap.get("title")));
			}
			
			// add 'content' parameter if present
			if (parameterMap.containsKey("content")) {
				parameters.add(new Parameter("content", parameterMap.get("content")));
			}
		}
		
		return new RequestBuilder(consumer_key, consumer_secret, baseUrl, requestMethod, parameters, ResponseType.EMPTY);
	}
	
	/**
	 * Create a builder for the "PUT Infographic" call.
	 */
	public static RequestBuilder deleteInfographicBuilder(final String consumer_key, final String consumer_secret, final String id) {
		String baseUrl = String.format("%s/infographics/%s", Constants.URL_BASE, id);
		String requestMethod = "DELETE";
		List<Parameter> parameters = new ArrayList<Parameter>();
	
		return new RequestBuilder(consumer_key, consumer_secret, baseUrl, requestMethod, parameters, ResponseType.EMPTY);
	}
	
	/**
	 * Constructor of the builder. To be called by one of the static class methods.
	 */
	private RequestBuilder(final String consumer_key, final String consumer_secret, final String baseUrl, final String requestMethod, final List<Parameter> parameters, ResponseType type) {
		
		this.consumer_key = consumer_key;
		this.consumer_secret = consumer_secret;
		this.baseUrl = baseUrl;
		this.requestMethod = requestMethod;
		this.parameters = new ArrayList<Parameter>(parameters);
		this.type = type;
		
		this.parameters.add(new Parameter("api_key", consumer_key));
		String signature = computeSignature(baseUrl, requestMethod, this.parameters);
		this.parameters.add(new Parameter("api_sig", signature));
	}
	
	/**
	 * Computes the signature to use in the request authentication.
	 * @param baseUrl
	 * @param requestMethod
	 * @param parameters List of parameters as Parameter objects
	 * @return A string containing only the signature.
	 */
	private String computeSignature(final String baseUrl, final String requestMethod, final List<Parameter> parameters) {
		try {
			String sigBase = URLEncoder.encode(requestMethod, "UTF-8") + "&" + URLEncoder.encode(baseUrl, "UTF-8");
			Collections.sort(parameters);
			
			String paramString = Helpers.encodedParameterStringFromList(parameters);
			
			sigBase += "&" + URLEncoder.encode(paramString, "UTF-8");
			
			byte[] sigBaseHashed = Helpers.calculateRFC2104HMAC(sigBase, consumer_secret);
			
			return DatatypeConverter.printBase64Binary(sigBaseHashed);
			
		}
		catch(java.io.UnsupportedEncodingException e) { return null; }
		catch(InvalidKeyException e) { return null; }
		catch(SignatureException e) { return null; }
	}
	
	public Response sendRequest() throws MalformedURLException, IOException {
		HttpURLConnection connection = ConnectionManager.sendRequest(baseUrl, requestMethod, parameters);
		
		switch(type) {
		case JSON:
			return new JsonResponse(connection);
		case PNG:
			return new GraphicsResponse(connection);
		case EMPTY:
			return new EmptyResponse(connection);
		default:
			throw new UnsupportedOperationException("Unexpected response type");
		}
	}
}
