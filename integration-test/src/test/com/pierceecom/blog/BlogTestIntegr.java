package com.pierceecom.blog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 * TODO, Consider it part of the test to replace HttpURLConnection with better
 * APIs (for example Jersey-client, JSON-P etc-) to call REST-service
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BlogTestIntegr {

    private static final String POST_1 = "{\"id\":\"1\",\"title\":\"First title\",\"content\":\"First content\"}";
    private static final String POST_2 = "{\"id\":\"2\",\"title\":\"Second title\",\"content\":\"Second content\"}";
    private static final String POSTS_URI = "http://localhost:8080/blog-web/posts/";

    
    public BlogTestIntegr() {
    }

    @Test
    public void test_1_BlogWithoutPosts() {
        String output = GET(POSTS_URI, 200);
        assertEquals("[]", output);
    }

    @Test
    public void test_2_AddPosts() {
        String location = POST(POSTS_URI, POST_1);
        assertEquals(POSTS_URI + "1", location);

        location = POST(POSTS_URI, POST_2);
        assertEquals(POSTS_URI + "2", location);
    }

    @Test
    public void test_3_GetPost() {
        String postJson = GET(POSTS_URI + "1", 200);
        assertEquals(POST_1, postJson);

        postJson = GET(POSTS_URI + "2", 200);
        assertEquals(POST_2, postJson);
    }

    @Test
    public void test_4_GetAllPosts() {
        String output = GET(POSTS_URI, 200);
        assertEquals("[" + POST_1 + "," + POST_2 + "]", output);
    }
    
    @Test
    public void test_5_DeletePosts() {
        DELETE(POSTS_URI + "1", 200);        
        // Should now be gone
        GET(POSTS_URI + "1", 204);

        DELETE(POSTS_URI + "2", 200);        
        // Should now be gone
        GET(POSTS_URI + "2", 204);      

    }

    @Test
    public void test_6_GetAllPostsShouldNowBeEmpty() {
        String output = GET(POSTS_URI, 200);
        assertEquals("[]", output);
    }

    /* Helper methods */
    private String GET(String uri, int expectedResponseCode) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            assertEquals(expectedResponseCode, conn.getResponseCode());
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }

            conn.disconnect();
        } catch (MalformedURLException ex) {
            Logger.getLogger(BlogTestIntegr.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BlogTestIntegr.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }

    private String POST(String uri, String json) {
        String location = "";
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            assertEquals(201, conn.getResponseCode());

            location = conn.getHeaderField("Location");
            conn.disconnect();

        } catch (MalformedURLException ex) {
            Logger.getLogger(BlogTestIntegr.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BlogTestIntegr.class.getName()).log(Level.SEVERE, null, ex);
        }
        return location;
    }

    private void DELETE(String uri, int expectedResponseCode) {
        try {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Accept", "application/json");
            assertEquals(expectedResponseCode, conn.getResponseCode());
        } catch (MalformedURLException ex) {
            Logger.getLogger(BlogTestIntegr.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BlogTestIntegr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
