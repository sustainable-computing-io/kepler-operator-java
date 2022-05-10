package io.sustainablecomputing;

import io.fabric8.kubernetes.api.model.Pod;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.Matcher.Result;
import io.javaoperatorsdk.operator.processing.dependent.Updater;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependentResource;
import java.util.Objects;

@KubernetesDependent(labelSelector = PodUpdater.INJECT_FREQUENCY_LABEL)
public class PodUpdater extends KubernetesDependentResource<Pod, Kepler> implements
    Updater<Pod, Kepler> {

  public static final String INJECT_FREQUENCY_LABEL = "kepler.sustainable-computing.io/inject-frequency";

  public PodUpdater() {
    super(Pod.class);
  }

  @Override
  public Result<Pod> match(Pod actualResource, Kepler primary, Context<Kepler> context) {
    // update the pod with 
    return Result.nonComputed(false);
  }
}
