package io.sustainablecomputing;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;

@Version("v1alpha1")
@Group("sustainable-computing.io")
public class Kepler extends CustomResource<KeplerSpec, KeplerStatus> implements Namespaced {}

