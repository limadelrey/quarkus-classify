package org.example.be;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.response.Response;
import org.example.be.model.entity.StatusEnum;
import org.example.be.repository.ClassificationRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@QuarkusTestResource(FakeKafkaResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClassificationIntegrationTests {

    private static final String BASE_ENDPOINT = "/api/v1/image-classification";

    private static final String ID = "8443a2b1-d27d-423c-9ac2-ed63c0891b12";

    @Inject
    ClassificationRepository classificationRepository;

    @Test
    @Order(1)
    public void testGetAllClassifications() {
        final String expected = "[" +
                "{" +
                "\"id\":\"8443a2b1-d27d-423c-9ac2-ed63c0891b12\"," +
                "\"name\":\"Classificação #2\"," +
                "\"description\":\"\"," +
                "\"url\":\"https://quarkus-hackaton.s3.eu-west-1.amazonaws.com/8443a2b1-d27d-423c-9ac2-ed63c0891b12\"," +
                "\"status\":\"ACTIVE\"," +
                "\"created_at\":\"2020-07-21 19:12:07\"" +
                "}" +
                "]";

        final Response response = given()
                .when()
                .get(BASE_ENDPOINT)
                .thenReturn();

        assertEquals(200, response.statusCode());

        assertEquals(expected, response.body().asString());
    }

    @Test
    @Order(2)
    public void testGetOneClassification() {
        final String expected = "{" +
                "\"id\":\"8443a2b1-d27d-423c-9ac2-ed63c0891b12\"," +
                "\"name\":\"Classificação #2\"," +
                "\"description\":\"\"," +
                "\"url\":\"https://quarkus-hackaton.s3.eu-west-1.amazonaws.com/8443a2b1-d27d-423c-9ac2-ed63c0891b12\"," +
                "\"status\":\"ACTIVE\"," +
                "\"tags\":[" +
                "{\"id\":1,\"name\":\"area\",\"confidence\":59.8976135253906},{\"id\":2,\"name\":\"structure\",\"confidence\":50.0617027282715},{\"id\":3,\"name\":\"history\",\"confidence\":18.8024425506592},{\"id\":4,\"name\":\"roof\",\"confidence\":14.4672164916992},{\"id\":5,\"name\":\"tourist\",\"confidence\":12.8294620513916},{\"id\":6,\"name\":\"historical\",\"confidence\":8.47606754302979}" +
                "]," +
                "\"created_at\":\"2020-07-21 19:12:07\"," +
                "\"updated_at\":\"2020-07-21 19:12:08\"" +
                "}";

        final Response response = given()
                .pathParam("id", ID)
                .when()
                .get(BASE_ENDPOINT + "/{id}")
                .thenReturn();

        assertEquals(200, response.statusCode());

        assertEquals(expected, response.body().asString());
    }

    @Test
    @Order(3)
    public void testDeleteClassification() {
        given()
                .pathParam("id", ID)
                .when()
                .delete(BASE_ENDPOINT + "/{id}")
                .then()
                .statusCode(204);

        assertSame(classificationRepository.findById(UUID.fromString(ID)).getClassificationResult().getStatus(), StatusEnum.DELETED);
    }
}
