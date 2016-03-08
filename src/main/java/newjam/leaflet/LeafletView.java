package newjam.leaflet;

import io.dropwizard.views.View;
import java.util.Map;

public class LeafletView extends View {

  private final Map<String, Object> model;
  
  public LeafletView(Map<String, Object> model) {
    super("leaflet.mustache");
    this.model = model;
  }
  
  public Map<String, Object> getModel() {
    return model;
  }
  
}