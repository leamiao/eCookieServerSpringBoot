package com.memd.ecookie.service;

import com.memd.ecookie.entity.ProductEntity
import com.memd.ecookie.exception.ServiceException
import com.memd.ecookie.repository.ProductRepository

import spock.lang.Specification

public class ProductServiceSpec extends Specification {
	def "search test 1"() {
		given:
		ProductService productService = new ProductService();
		def products = new ArrayList<>();
		products.add(this.createProductByName("p1"));
		products.add(this.createProductByName("p2"));
		
		ProductRepository productRepository = Mock() {
			findAll(_) >> products
		}
		productService.productRepository = productRepository;
		
		when:
		def results = productService.search(null, null, null);
		
		then:
		results == products
	}
	
	def createProductByName(String name) {
		def product = new ProductEntity();
		product.setName(name);
		product.setId(10);
		
		return product;
	}
	
	def "save product test 1"() {
		given:
		ProductService productService = new ProductService();
		
		def product = this.createProductByName("p1");
		product.setPrice(-1.0);
		
		ProductRepository productRepository = Mock() {
		}
		productService.productRepository = productRepository;
		
		when:
		def results = productService.saveProductEntity(product, false);
		
		then:
		thrown ServiceException
	}

}
