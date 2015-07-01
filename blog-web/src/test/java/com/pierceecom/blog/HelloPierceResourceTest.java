package com.pierceecom.blog;

import javax.ws.rs.core.Response;
import org.junit.Test;
import static org.junit.Assert.*;

public class HelloPierceResourceTest {
    
    HelloPierceResource resource;
    
    public HelloPierceResourceTest() {
        resource = new HelloPierceResource();
    }

    @Test
    public void testHello() {
        Response helloResponse = resource.hello();
        String hello = (String)helloResponse.getEntity();
        assertEquals("{\"message\":\"Hello Pierce\"}", hello);
    }
    
}
