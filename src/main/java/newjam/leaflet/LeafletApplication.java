package newjam.leaflet;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import org.glassfish.jersey.client.proxy.WebResourceFactory;

public class LeafletApplication extends Application<LeafletConfiguration> {
  
  public static void main(String[] args) throws Exception {
    new LeafletApplication().run(args);
  }
  
  @Override
  public void run(LeafletConfiguration configuration, Environment environment) throws Exception {
    Client client = ClientBuilder.newClient();
    WebTarget target = client.target("http://41dems.org/");
    PrecinctLocatorResource precinctFinder = WebResourceFactory.newResource(PrecinctLocatorResource.class, target);
    GeoApiContext context = new GeoApiContext().setApiKey(configuration.getGoogleServerKey());
    environment.jersey().register(new LeafletResource(precinctFinder, context));
  }
  
  @Override
  public void initialize(Bootstrap<LeafletConfiguration> bootstrap) {
    bootstrap.addBundle(new ViewBundle<>());
  }
  
}