package newjam.leaflet;

import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("gmap/q16.tmpl")
public interface PrecinctLocatorResource {
  
  @GET @Produces(APPLICATION_JSON)
  Map<String, Object> getCaucusSite(@QueryParam("fmt") String format, @QueryParam("ll") String coordinates);
  
}
