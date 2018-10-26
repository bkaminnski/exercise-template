package com.hclc.exercises;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.json.JsonObject;
import javax.ws.rs.client.ClientBuilder;

import static org.assertj.core.api.Assertions.assertThat;

public class TemplateTest {

    private Template template;

    @BeforeEach
    void before() {
        template = new Template();
    }

    @Test
    void simple() {
        String result = template.doSomething("Else", 1);
        assertThat(result).isEqualTo("didSomethingElse1Time(s)");
    }

    @ParameterizedTest(name = "{index} => doing something ''{0}'', {1} time(s)")
    @CsvSource({
            "Else, 1",
            "Interesting, 3"
    })
    void parameterized(String what, int howManyTimes) {
        String result = template.doSomething(what, howManyTimes);
        assertThat(result).isEqualTo("didSomething" + what + howManyTimes + "Time(s)");
    }

    @Test
    void restApiGet_notReallyAUnitTest_butUsefulForWritingIntegrationTests() {
        String fullUrl = ClientBuilder.newClient()
                .target("https://75130224-6664-4b4e-9906-67140fe70475.mock.pstmn.io/hclc")
                .request()
                .get()
                .readEntity(JsonObject.class)
                .getString("fullUrl");
        assertThat(fullUrl).isEqualTo("http://highcohesionloosecoupling.com");
    }
}
