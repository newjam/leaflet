package newjam.leaflet;

import com.codahale.metrics.annotation.Timed;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_HTML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/leaflet")
public class LeafletResource {

  private final static Logger log = LoggerFactory.getLogger(LeafletResource.class);
  private final PrecinctLocatorResource foo;
  private final GeoApiContext geoContext;
  
  public LeafletResource(PrecinctLocatorResource foo, GeoApiContext geoContext) {
    this.foo = foo;
    this.geoContext = geoContext;
  }
  
  @GET @Timed @Produces(TEXT_HTML)
  public LeafletView getHTML(@QueryParam("location") String location) throws Exception {
    return new LeafletView(getJSON(location));
  }
  
  @GET @Timed @Produces(APPLICATION_JSON)
  public Map<String, Object> getJSON(@QueryParam("location") String location) throws Exception {
    log.debug("servicing request for {}", location);
    
    Map<String, Object> model = new HashMap<>();
    model.put("location", location);
    
    if(location == null) {
      log.debug("no location provided");
      model.put("locationMessage", "Please enter an address");
      return model;
    }
    
    GeocodingResult[] results =  GeocodingApi.geocode(geoContext, location).await();
    
    if(results.length < 1) {
      String message = "Could not find coordinates for " + location;
      log.warn(message);
      model.put("locationMessage", message);
      return model;
    }
    
    model.put("location", results[0].formattedAddress);
    
    final double lat = results[0].geometry.location.lat;
    final double lng = results[0].geometry.location.lng;
    
    final String latLngQueryParam = "" + lng + "," + lat;
    log.debug(latLngQueryParam);
    
    Map<String, Object> object = foo.getCaucusSite("json", latLngQueryParam);
    
    if(object.containsKey("none") && (Integer)object.get("none") == 1) {
      String message = "Could not find a precinct for the coordinates " + latLngQueryParam + ".";
      log.warn(message);
      model.put("locationMessage", message );
      return model;
    }
    
    model.put("data", object);
    
    return model;
  }
  
}
