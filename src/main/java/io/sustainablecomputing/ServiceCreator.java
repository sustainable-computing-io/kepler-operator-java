package io.sustainablecomputing;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.javaoperatorsdk.operator.ReconcilerUtils;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.Creator;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependentResource;

public class ServiceCreator extends KubernetesDependentResource<Service, Kepler> implements
    Creator<Service, Kepler> {

  public ServiceCreator() {
    super(Service.class);
  }

  @Override
  protected Service desired(Kepler primary, Context<Kepler> context) {
    final var service = ReconcilerUtils.loadYaml(Service.class, getClass(), "service.yml");
    return new ServiceBuilder(service)
        .editSpec()
        .editFirstPort()
        .withPort(primary.getSpec().getPort())
        .endPort()
        .endSpec()
        .build();
  }
}
