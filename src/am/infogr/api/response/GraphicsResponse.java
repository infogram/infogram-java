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

    public BufferedImage getGraphic() throws Exception {
        if (graphic != null)
            return graphic;
        if (inputStreamUsed)
            throw new Exception("Response stream already used elsewhere");

        graphic = ImageIO.read(connection.getInputStream());
        inputStreamUsed = true;
        return graphic;
    }
}
