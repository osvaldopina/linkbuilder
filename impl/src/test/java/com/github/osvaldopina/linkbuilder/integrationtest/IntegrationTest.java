package com.github.osvaldopina.linkbuilder.integrationtest;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
@WebAppConfiguration
@org.springframework.boot.test.IntegrationTest("server.port:0")
@Ignore
public class IntegrationTest {

    @Test
    public void test() {

    }



}
