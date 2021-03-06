/*
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
package com.opentable.jaxrs.exceptions;

import java.util.Map;
import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.jaxrs.base.ProviderBase;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import org.slf4j.MDC;

@Provider
public class JsonMessageReaderMapper extends OpenTableJaxRsExceptionMapper<JsonParseException>
{
    private static final Set<String> CLASS_NAMES = ImmutableSet.of(JacksonJsonProvider.class.getName(), ProviderBase.class.getName());

    @Inject
    public JsonMessageReaderMapper() {
        super(Status.INTERNAL_SERVER_ERROR);
    }

    @Override
    public Response toResponse(JsonParseException exception) {
        for (StackTraceElement e : exception.getStackTrace()) {
            if (CLASS_NAMES.contains(e.getClassName())) {
                final Map<String, String> response = ImmutableMap.of(
                        "code", "400",
                        "requestid", MoreObjects.firstNonNull(MDC.get("requestid"), ""),
                        "message", MoreObjects.firstNonNull(exception.getMessage(), "(no message)"));

                return Response.status(400)
                        .entity(response)
                        .type(MediaType.APPLICATION_JSON_TYPE)
                        .build();
            }
        }

        return super.toResponse(exception);
    }
}
