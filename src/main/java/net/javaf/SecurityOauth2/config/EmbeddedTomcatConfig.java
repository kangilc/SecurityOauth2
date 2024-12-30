package net.javaf.SecurityOauth2.config;

import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedTomcatConfig implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

	@Override
	public void customize(TomcatServletWebServerFactory factory) {
		factory.addConnectorCustomizers((TomcatConnectorCustomizer)
                connector -> connector.setProperty("relaxedQueryChars", "<>[]^`{|}"));
	}

}
