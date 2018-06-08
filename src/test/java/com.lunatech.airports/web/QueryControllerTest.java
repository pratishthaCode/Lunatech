package com.lunatech.airports.web;

import com.lunatech.airports.db.CountryRepo;
import com.lunatech.airports.model.Country;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QueryControllerTest {

    @MockBean
    private CountryRepo countryRepository;

    @Autowired
    private QueryController queryController;

    private static Country netherland() {
        return Country.builder().id(1).code("IN").name("Netherland").build();
    }

    @Test
    public void LoadQueryController() {

    }
}
