package com.api;


import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.After;
import org.junit.Before;

public class BaseAPI
{
    RequestSpecification reqSpec;

    @Before
    public void setUp() throws Exception
    {
        start();
    }

    private void start() throws Exception
    {
        RestAssured.baseURI="https://my.api.mockaroo.com/";
    }

    @After
    public void tearDown()
    {

    }

}
