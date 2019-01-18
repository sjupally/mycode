package com.abhaya.vehicle.tracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication //(exclude = ElasticsearchAutoConfiguration.class)
@ComponentScan("com.abhaya.vehicle.tracking")
public class Application 
{
  public static void main(String[] args) 
  {
    SpringApplication.run(Application.class, args);
  }
  
/*  @Bean
  public ServletWebServerFactory servletContainer() 
  {
    TomcatServletWebServerFactory tomcat =   new TomcatServletWebServerFactory() 
    {
          @Override
          protected void postProcessContext(Context context) {
            SecurityConstraint securityConstraint = new SecurityConstraint();
            securityConstraint.setUserConstraint("CONFIDENTIAL");
            SecurityCollection collection = new SecurityCollection();
            collection.addPattern("/*");
            securityConstraint.addCollection(collection);
            context.addConstraint(securityConstraint);
          }
        };
    tomcat.addAdditionalTomcatConnectors(redirectConnector());
    return tomcat;
  }

  private Connector redirectConnector() {
    Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
    connector.setScheme("http");
    connector.setPort(8099);
    connector.setSecure(false);
    connector.setRedirectPort(8443);
    return connector;
  }*/
}
