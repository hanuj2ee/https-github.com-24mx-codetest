# codetest
================================================================================

CONTENTS:

    1 Worksample - Description
    2 Assignment - Blog REST-service
    3 Installing Blog project
    4 Integration Test

================================================================================

================================================================================
1 Worksample - Description
================================================================================
If you do not already have one or more projects you're proud of and has
opportunity to submit, please perform the following worksample:

The goal of the test is to show how you would normally take on a 
java-based programming task.

The assessment will be based on following criterias:
 - Clean design
 - Reuse (of code and 3rd party products)
 - Maintainability
 - Ability to test
 - Java Knowledge Base
 - Coding style

You decide how "deep" you go when implementing the task to satisfy the criterias.

================================================================================
2 Assignment - Blog REST-service
================================================================================

Write a RESTful service that implement a simple version of serving blog-posts.
A "Post" have the attributes [id:String, title:String, content:String]

We would like to see:
 - A client (command line or webapp) that calls a the servive with the correct arguments (and handles all possible/plausible errors) using REST API
    - The client should be able to add, list, view and delete posts.
 - A blog server that can serve a REST-service according to the RAML-specification below. 
    - That is:
        - List all posts
        - Create a post
        - Get one post by id
        - Delete one post by id

The REST service should support the following resources
--------------------------------------------------------------------------------
#%RAML 0.8
title: Simple blog post API
version: 1
baseUri: http://localhost:8080/blog-web/
mediaType: application/json
/posts
    get:
        description: List all posts
    post: 
        description: Creates a new post
        body:
            application/json: !!null
            example: |
                { "id":"1",
                  "title":"First title",
                  "content":"First content"}
                }
        responses:
            201: 
                description: Also returns location of created resource
                uriParameters:
                    postId:
                        type: string
                        example: http://localhost:8080/blog-web/posts/1
    /{postId}
        get:
            description: Get one post by id
            uriParameters:
                 postId:
                   type: string
            responses:
                200:
                    body: 
                        application/json:
                            example: |
                                { "id":"1",
                                  "title":"First title",
                                  "content":"First content"}
                                }
                204: 
                    description: If post with id does not exist // noContent
        delete:
            description: Delete one post by id
            uriParameters:
                 postId:
                   type: string
            responses:
                200: 
                    description: If post with id does was successfully deleted
                204: 
                    description: If post with id does not exist // noContent

-------------------------------------------------------------------------------- 

References:
https://jersey.java.net/documentation/latest/client.html
http://raml.org/


================================================================================
3 Installing the Blog Ping project
================================================================================

Please follow the steps below:

1. Download and install Maven 3. You can download it from http://maven.apache.org/download.html.
2. Download Payara, to run embedded: http://www.payara.co.uk/downloads
    Payara Micro is enough to run
3. Run the command: "mvn install" in the root folder of the distributionfolder.
4. Run the blog-web
    > java -jar payara-micro-4.1.152.1.jar --deploy blog-web\target\blog-web.war
5. Go to address: http://localhost:8080/blog-web/hello-pierce. The server should respond with a text that reads: {"message":"Hello Pierce"}.
6. Now basic environment and Blog Ping project is installed correctly.

================================================================================
4 Integration Test
================================================================================

The integration tests is (and should be) located under the maven artifact "integration test". 
These tests should test the expected behavior of a correct implementation of Blog posts. 

There is a simple test to start with. You run the test with "mvn test-Dtest =*TestIntegr" .

The tests and flow is a suggestion and may not use the most convinient APIs to do it, you may change.
You should fill in with extra tests (and expect that we do to ;) )
