package eu.vrtime.bootsoap.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext context) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(context);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}

	// This is importat!
	// name of bean defines the the url. Without it is not working. wsdl url is /ws/UserService.wsdl?
	@Bean(name="UserService")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("UserPort");
		wsdl11Definition.setServiceName("UserService");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://www.vrtime.eu/bootsoap/gen");
		wsdl11Definition.setSchema(schema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema countriesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("user.xsd"));
	}

}
