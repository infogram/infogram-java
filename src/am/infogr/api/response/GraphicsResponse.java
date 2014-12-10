package am.infogr.api.response;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.HttpURLConnection;

import javax.imageio.ImageIO;

/**
 * Implementation of the Response class specific for a graphics response.
 */
public class GraphicsResponse extends Response {

	private BufferedImage graphic;
	
	public GraphicsResponse(HttpURLConnection connection) {
		super(connection);
	}

	public BufferedImage getGraphic() throws IOException {
		if (graphic != null) return graphic;
		
		graphic = ImageIO.read(connection.getInputStream());
		return graphic;
	}
}
