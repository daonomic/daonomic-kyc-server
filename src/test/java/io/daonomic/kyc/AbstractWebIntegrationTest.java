package io.daonomic.kyc;

import io.daonomic.kyc.common.Ports;
import io.daonomic.kyc.configuration.ApiConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.*;

import java.util.List;

@Test(groups = "integration")
@ContextConfiguration(classes = ApiConfiguration.class)
public abstract class AbstractWebIntegrationTest extends AbstractTestNGSpringContextTests {
    protected RestTemplate restTemplate = new RestTemplate();
    protected String baseUrl;

    protected static List<Integer> ports = Ports.nextPorts(3);
    protected int httpPort = ports.get(0);

    @Override
    @BeforeClass(alwaysRun = true, dependsOnMethods = "springTestContextBeforeTestClass")
    protected void springTestContextPrepareTestInstance() throws Exception {
        System.setProperty("httpPort", "" + ports.get(0));
        super.springTestContextPrepareTestInstance();
    }

    @BeforeClass
    public void setUp() throws Exception {
        baseUrl = "http://127.0.0.1:" + httpPort;
    }

    @AfterClass
    public void tearDown() throws Exception {
    }

    @BeforeMethod
    public void before() throws Exception {
    }

    @AfterMethod
    public void after() throws Exception {
    }
}
