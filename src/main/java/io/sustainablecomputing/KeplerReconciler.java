package io.sustainablecomputing;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.openshift.client.OpenShiftClient;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ContextInitializer;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;

@ControllerConfiguration(name = "kepler",dependents = {
    @Dependent(type = ServiceCreator.class),
    @Dependent(type = DaemonSetCreator.class)
})
public class KeplerReconciler implements Reconciler<Kepler>, ContextInitializer<Kepler> {

  public static final String IS_OPENSHIFT = "OPENSHIFT_MARKER";
  private final boolean isOpenShift;

  public KeplerReconciler(KubernetesClient client) {
    isOpenShift = client.isAdaptable(OpenShiftClient.class);
  }

  @Override
  public UpdateControl<Kepler> reconcile(Kepler resource, Context context) {
    return UpdateControl.noUpdate();
  }

  @Override
  public void initContext(Kepler kepler, Context<Kepler> context) {
    context.managedDependentResourceContext().put(IS_OPENSHIFT, isOpenShift);
  }
}





