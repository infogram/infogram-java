package am.infogr.api.response;

import java.net.HttpURLConnection;

/**
 * Implementation of the Response class specific for an empty body response.
 */
public class EmptyResponse extends Response {

	public EmptyResponse(HttpURLConnection connection) {
		super(connection);
	}

}
