package com.example.lendingService;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.assertj.core.api.BDDAssertions;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.junit.StubRunnerRule;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerPort;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import wiremock.org.apache.http.impl.conn.Wire;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureWireMock(port = 8081)
@AutoConfigureStubRunner(ids = "com.example:fraudDetect", stubsMode = StubRunnerProperties.StubsMode.LOCAL
,consumerName = "lendingService")
class LendingServiceApplicationTests {

	/*@Rule
	public StubRunnerRule stubRunnerRule=new StubRunnerRule()
			.downloadStub("com.exmple","fraudDetect").withPort(6545);*/
	@StubRunnerPort("fraudDetect")int port;

	@Test
	void validate_a_person_identity() {

		String response="[\"roger\",\"verified\"]";
		WireMock.stubFor(WireMock.get("/fraud").willReturn(WireMock.aResponse().withBody(response).withStatus(200)));


		String forObject = new RestTemplate().getForObject("http://localhost:"+port+"/fraud", String.class);
		BDDAssertions.then(forObject).isEqualTo(response);
	}

	@Test
	void validate_a_person_identity1() {

		String response="[\"pradeep\",\"verified\"]";
		//WireMock.stubFor(WireMock.get("/frauds").willReturn(WireMock.aResponse().withBody(response).withStatus(200)));

		String forObject = new RestTemplate().getForObject("http://localhost:6545/frauds", String.class);
		BDDAssertions.then(forObject).isEqualTo(response);
	}

}
