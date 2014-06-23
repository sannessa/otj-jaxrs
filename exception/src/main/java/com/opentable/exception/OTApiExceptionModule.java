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
package com.opentable.exception;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;

import com.opentable.jaxrs.JaxRsBinder;

/**
 * Add support for mapping NessApiException subclasses to and from HTTP responses.
 */
public final class OTApiExceptionModule extends AbstractModule
{
    public OTApiExceptionModule()
    {
    }

    @Override
    protected void configure()
    {
        bind(ResponseMapper.class);

        bind(ExceptionClientResponseFilter.class).in(Scopes.SINGLETON);
        JaxRsBinder.bindFeatureForAllClients(binder()).to(ExceptionClientResponseFeature.class);

        // Constructing the binder creates the MapBinder, so we don't have undeclared dependencies
        // even if there end up being no bindings, just an empty map.
        OTApiExceptionBinder.of(binder());
    }

    @Override
    public int hashCode()
    {
        return OTApiExceptionModule.class.hashCode();
    }

    @Override
    public boolean equals(final Object obj)
    {
        return obj instanceof OTApiExceptionModule;
    }
}
