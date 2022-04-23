package io.sustainablecomputing;

import io.fabric8.kubernetes.api.model.Volume;
import io.fabric8.kubernetes.api.model.VolumeBuilder;
import io.fabric8.kubernetes.api.model.VolumeMount;
import io.fabric8.kubernetes.api.model.VolumeMountBuilder;
import io.fabric8.kubernetes.api.model.apps.DaemonSet;
import io.fabric8.kubernetes.api.model.apps.DaemonSetBuilder;
import io.javaoperatorsdk.operator.ReconcilerUtils;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.Creator;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependentResource;

public class DaemonSetCreator extends KubernetesDependentResource<DaemonSet, Kepler> implements
    Creator<DaemonSet, Kepler> {

  private static final String KEPLER_CONTAINER_NAME = "kepler-exporter";

  public DaemonSetCreator() {
    super(DaemonSet.class);
  }

  @Override
  public DaemonSet create(DaemonSet target, Kepler primary, Context<Kepler> context) {
    final var daemonSet = ReconcilerUtils.loadYaml(DaemonSet.class, getClass(), "daemonset.yml");
    final var builder = new DaemonSetBuilder(daemonSet);
    if (context.managedDependentResourceContext()
        .getMandatory(KeplerReconciler.IS_OPENSHIFT, Boolean.class)) {
      final var kernelSrc = volumeMount("kernel-src", "/usr/src/kernels");
      final var kernelDebug = volumeMount("kernel-debug", "/sys/kernel/debug");
      builder.editSpec()
          .editTemplate()
          .editSpec()
          .editMatchingContainer(cb -> KEPLER_CONTAINER_NAME.equals(cb.getName()))
          .addToVolumeMounts(kernelSrc, kernelDebug)
          .endContainer()
          .addToVolumes(volume(kernelSrc), volume(kernelDebug))
          .endSpec()
          .endTemplate()
          .endSpec();
    }
    return builder.build();
  }

  private VolumeMount volumeMount(String name, String mountPath) {
    return new VolumeMountBuilder().withName(name).withMountPath(mountPath).build();
  }

  private Volume volume(VolumeMount mount) {
    return new VolumeBuilder().withName(mount.getName())
        .withNewHostPath(mount.getMountPath(), "Directory").build();
  }
}
