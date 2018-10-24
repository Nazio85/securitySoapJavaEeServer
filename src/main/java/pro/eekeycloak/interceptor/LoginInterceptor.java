package pro.eekeycloak.interceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class LoginInterceptor extends AbstractPhaseInterceptor<Message> {

    private static final String RESOURCE_TOKEN_NAME = "resource_access";
    private static final String RESOURCE_TOKEN_APP = "test-app";
    private static final String RESOURCE_TOKEN_ROLES = "roles";
    public static final String TOKEN_KEY = "token";

    public LoginInterceptor() {
        super(Phase.RECEIVE);
    }

    public LoginInterceptor(String phase) {
        super(phase);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        String soapMessage = get_input_xml(message);

        if (!soapMessage.equals("")) {
            String token = getToken(soapMessage, "<token xmlns=\"\">", "</token>");

            List<String> roles = new ArrayList<>();

            if (token != null && !token.isEmpty()) {
                roles = parseToken(token);
            }

            if (roles.size() == 0) throw new Fault(new Exception("Token is not have roles"));
            message.put(TOKEN_KEY, roles);
        }
    }

    private String getToken(String str, String open, String close) {
        if (str != null && open != null && close != null) {
            int start = str.indexOf(open);
            if (start != -1) {
                int end = str.indexOf(close, start + open.length());
                if (end != -1) {
                    return str.substring(start + open.length(), end);
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private List<String> parseToken(String token) {
        try {
            String keyString = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvS0oIUSJkKkKzQp55K2MxZnevAqFmTNlsjc+MH7BJxvICbdXtGSTyPRgkz0oWrxzZeAbXaRcdexy79/tm0HBIsCo43de3S+fxwh6eXsUI6/icmGmp1MBCHU40OX88dVODhBOVI1QBVEl3lnLuU6Fwg8nCkwfHTvY3CniumG7s7XNWtOf+eGEt0kuNMlylCcB+UgdcNAdgBnTQwF80vQwFfQzjo8tU6TayrjVzYjFfv6CVBZKyeISaZc1RL5ou8UmR5EqvUO5CjIVtffOb4iMvzqSM8KyyJsWVBJAxyvjfCgG5jIWzSL2c78013KBpV7etXyfG1cxiYB/9FuQu7R08wIDAQAB";
            byte[] decoded = Base64.getDecoder().decode(keyString);

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(decoded);
            PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

            Claims subject = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
            System.out.println(subject);

            Map<String, Object> resourceAccess = (Map<String, Object>)subject.get(RESOURCE_TOKEN_NAME);
            Map<String, Object> app = (Map<String, Object>)resourceAccess.get(RESOURCE_TOKEN_APP);
            return (ArrayList<String>)app.get(RESOURCE_TOKEN_ROLES);
        } catch (Exception e) {
            throw new Fault(new Exception("Token is not valid"));
        }
    }

    public static String get_input_xml(Message message)
    {
        String soapMessage;
        message.put(Message.ENCODING, "UTF-8");

        InputStream input_stream = message.getContent(InputStream.class);

        if (input_stream != null)
            try
            {
                CachedOutputStream cached_input_stream = new CachedOutputStream();

                IOUtils.copy(input_stream,cached_input_stream);
                soapMessage = new String(cached_input_stream.getBytes());
                cached_input_stream.flush();
                input_stream.close();
                cached_input_stream.close();

                //после перехвата отправляем сообщение дальше
                message.setContent(InputStream.class, cached_input_stream.getInputStream());
            }
            catch(Exception e)
            {
                return "DOMTools.get_input_xml(): Cannot get xml from incoming message because of " + e.getMessage() + ".";
            }
        else
            return "DOMTools.get_input_xml(): Cannot get xml from incoming message.";

        return soapMessage;
    }
}
