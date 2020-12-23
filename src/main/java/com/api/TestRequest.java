package com.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

import org.apache.http.HttpStatus;
import org.junit.Test;
import org.json.JSONObject;
import com.sun.org.glassfish.gmbal.Description;

public class TestRequest extends BaseAPI {
    @Test
    @Description("Test if store is empty")
    public void test_empty()
    {
        reqSpec = given().header("X-API-Key", "67e515e0");
        reqSpec.when()
                .get("/api/books")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("size()", is(0));
    }
    @Test
    @Description("Save book with missing field")
    public void test_without_title_author()
    {
        reqSpec = given().header("X-API-Key", "67e515e0");
        JSONObject requestParams = new JSONObject();
        reqSpec.body(requestParams.toString());
        reqSpec.when()
                .put("/api/books")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .assertThat()
                .body("error", equalTo("Field 'title,author' is required."));
    }
    @Test
    @Description("Save book with valid field")
    public void test_with_valid_field()
    {
        reqSpec = given().header("X-API-Key", "67e515e0");
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "");
        requestParams.put("author", "");
        reqSpec.body(requestParams.toString());
        reqSpec.when()
                .put("/api/books")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .assertThat()
                .body("error", equalTo("Field 'title,author' cannot be empty."));
    }
    @Test
    @Description("Save book with read only ID")
    public void test_read_only_ID_save()
    {
        reqSpec = given().header("X-API-Key", "67e515e0");
        JSONObject requestParams = new JSONObject();
        requestParams.put("id", "11");
        requestParams.put("title", "kitap");
        requestParams.put("author", "mehmet ali");
        reqSpec.body(requestParams.toString());
        reqSpec.when()
                .put("/api/books")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .assertThat()
                .body("error", equalTo("Field 'id' is read-only"));
    }
    @Test
    @Description("Book saved succesfully")
    public void test_save_book()
    {
        reqSpec = given().header("X-API-Key", "67e515e0");
        JSONObject requestParams = new JSONObject();
        requestParams.put("title", "test");
        requestParams.put("author", "joe doe");
        reqSpec.body(requestParams.toString());
        reqSpec.when()
                .put("/api/books")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body("title", equalTo("kitap"))
                .and()
                .body("author", equalTo("mehmet ali"));
    }
}
