package io.sustainablecomputing;

import javax.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@ApplicationScoped
@RegisterRestClient(baseUri = "stork://kepler")
public interface KeplerService {

}
