package com.opentable.jaxrs.clientfactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.junit.Test;
import org.mockito.Mockito;

public class ResteasyClientBuilderTest
{

    @Test
    public void instanceCreatesBuilderImpls()
    {
        JaxRsClientBuilder builder = JaxRsClientBuilder.newInstance();
        assertEquals(JaxRsClientBuilderImpl.class, builder.getClass());
    }

    @Test
    public void builderImplsImplementBuilder()
    {
        JaxRsClientBuilder builder = JaxRsClientBuilder.newInstance();
        assertTrue(JaxRsClientBuilder.class.isAssignableFrom(builder.getClass()));
    }

    @Test
    public void socketTimeoutPropagates() throws NoSuchFieldException, IllegalAccessException
    {
        final JaxRsClientConfig conf = mock(JaxRsClientConfig.class);
        when(conf.socketTimeoutMillis()).thenReturn(6600L);
        final ResteasyClientBuilder underlying = mock(ResteasyClientBuilder.class);

        final Client client = builderWithMockResteasy(underlying).withConfiguration(conf).build();

        Mockito.verify(underlying).socketTimeout(6600, TimeUnit.MILLISECONDS);
    }

    @Test
    public void connectTimeoutPropagates() throws NoSuchFieldException, IllegalAccessException
    {
        final JaxRsClientConfig conf = mock(JaxRsClientConfig.class);
        when(conf.connectTimeoutMillis()).thenReturn(4400L);
        final ResteasyClientBuilder underlying = mock(ResteasyClientBuilder.class);

        final Client client = builderWithMockResteasy(underlying).withConfiguration(conf).build();

        Mockito.verify(underlying).establishConnectionTimeout(4400, TimeUnit.MILLISECONDS);
    }

    private JaxRsClientBuilderImpl builderWithMockResteasy(final ResteasyClientBuilder mock)
    {
        return new JaxRsClientBuilderImpl() {
            @Override
            ResteasyClientBuilder getResteasyClientBuilder() {
                return mock;
            }
        };
    }
}