package am.infogr.api;

import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

class Helpers {

    static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

    public static byte[] calculateRFC2104HMAC(final String data, final String key)
            throws InvalidKeyException, SignatureException {

        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);
            return mac.doFinal(data.getBytes());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String encodedParameterStringFromList(final List<Parameter> parameters) {
        try {
            String s = "";
            for (Parameter p : parameters) {
                if (s.length() > 0)
                    s += "&";
                s += URLEncoder.encode(p.key, "UTF-8") + "="
                        + URLEncoder.encode(p.value, "UTF-8").replace("+", "%20");
                // (need spaces to be %20 but are + by default)
            }
            return s;
        } catch (java.io.UnsupportedEncodingException e) {
            return null;
        }
    }
}
