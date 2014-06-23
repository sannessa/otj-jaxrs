/**
 * Copyright (C) 2012 Ness Computing, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.opentable.jaxrs;

import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Feature;

import com.google.inject.Binder;
import com.google.inject.binder.LinkedBindingBuilder;
import com.google.inject.multibindings.MapBinder;
import com.google.inject.multibindings.Multibinder;

public final class JaxRsBinder
{
    private JaxRsBinder()
    {
    }

    public static LinkedBindingBuilder<Feature> bindFeatureForAllClients(final Binder binder)
    {
        return bindFeatureForGroup(binder, PrivateFeatureGroup.WILDCARD);
    }

    public static LinkedBindingBuilder<Feature> bindFeatureForGroup(final Binder binder, final JaxRsFeatureGroup feature)
    {
        return MapBinder.newMapBinder(binder, JaxRsFeatureGroup.class, Feature.class).addBinding(feature);
    }

    public static LinkedBindingBuilder<ContainerRequestFilter> bindRequestFilter(final Binder binder)
    {
        return Multibinder.newSetBinder(binder, ContainerRequestFilter.class).addBinding();
    }

    public static LinkedBindingBuilder<ContainerResponseFilter> bindResponseFilter(final Binder binder)
    {
        return Multibinder.newSetBinder(binder, ContainerResponseFilter.class).addBinding();
    }
}
