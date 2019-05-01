package com.wucq.webdemo;

import java.util.Random;

import com.wucq.webdemo.Entity.Product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
public class DemoApplicationTests {
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void test() {
		long productId = 1;
		Product product = restTemplate.getForObject("Http://localhost:" + port + "/product/" + productId,
				Product.class);
		if (product.getPrice() == 200) {
			System.out.println("Yes 200");
		}

		Product newProduct = new Product();
		long newPrice = new Random().nextLong();
		newProduct.setName("new name");
		newProduct.setPrice(newPrice);
		restTemplate.put("Http://localhost:" + port + "/product/" + productId, newProduct);
		Product testProduct = restTemplate.getForObject("Http://localhost:" + port + "/product/" + productId,
				Product.class);

		if (testProduct.getPrice() == newPrice) {
			System.out.println("Yes 200");
		}
	}
}
