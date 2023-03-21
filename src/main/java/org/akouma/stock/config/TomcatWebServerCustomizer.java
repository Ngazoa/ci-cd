package org.akouma.stock.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class TomcatWebServerCustomizer implements
        WebServerFactoryCustomizer<TomcatServletWebServerFactory>
{
    /**
     * Customize the specified
     * @param factory the web server factory to customize
     */
    @Override
    public void customize(TomcatServletWebServerFactory factory)
    {
        factory.addConnectorCustomizers(connector ->
                connector.setAttribute("relaxedQueryChars", "<>[\\]^`{|}"));
    }
}