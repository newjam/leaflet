package newjam.leaflet;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import javax.validation.constraints.NotNull;

public class LeafletConfiguration extends Configuration  {

  @NotNull
  private String googleServerKey;
  
  @JsonProperty
  public String getGoogleServerKey() {
    return googleServerKey;
  }
  
  @JsonProperty
  public void setGoogleServerKey(String googleServerKey) {
    this.googleServerKey = googleServerKey;
  }
  
}