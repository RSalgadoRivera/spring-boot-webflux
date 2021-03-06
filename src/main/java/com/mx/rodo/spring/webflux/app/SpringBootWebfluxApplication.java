package com.mx.rodo.spring.webflux.app;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.mx.rodo.spring.webflux.app.models.dao.IProductoDao;
import com.mx.rodo.spring.webflux.app.models.documents.Producto;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {

	@Autowired
	private IProductoDao productoDao;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;

	private static final Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebfluxApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		mongoTemplate.dropCollection("productos").subscribe();
		
		Flux.just(new Producto("Tv Panasonic", 13200.99), 
				new Producto("Tv Sony", 15500.00),
				new Producto("Laptop Dell", 25000.00), 
				new Producto("Moto g7", 4000.99),
				new Producto("Moto g7", 4000.99),
				new Producto("Moto g7", 4000.99),
				new Producto("Moto g7", 4000.99),
				new Producto("Moto g7", 4000.99),
				new Producto("Moto g7", 4000.99),
				new Producto("Moto g7", 4000.99))
				.flatMap(producto -> {
					producto.setCreateAt(new Date());
					return productoDao.save(producto);
					})
				.subscribe(producto -> log.info("Insert: ".concat(producto.getId()).concat(" ").concat(producto.getNombre())));

	}

}
