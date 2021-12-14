package com.payment.redis.yanquiPaymentController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.redis.entity.YanquiPayment;
import com.payment.redis.kafkaProducer.Producer;
import com.payment.redis.respository.YanquiPaymentDao;

@RestController
@RequestMapping("/yanquipayment")
@EnableCaching
public class YanquiPaymentController {
	@Autowired
    private YanquiPaymentDao dao;
	
	private final Producer producer;
	
	@Autowired
	public YanquiPaymentController(Producer producer) {
		this.producer=producer;
	}

    @PostMapping
    public YanquiPayment save(@RequestBody YanquiPayment yanquiPayment) {
    	this.producer.sendMessage(String.valueOf(yanquiPayment.getAmount()));
        return dao.save(yanquiPayment);
    }

    @GetMapping
    public List<YanquiPayment> getAllProducts() {
        return dao.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id",value = "yanquipayment")
    public YanquiPayment findProduct(@PathVariable int id) {
        return dao.findProductById(id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id",value = "yanquipayment")
    public String remove(@PathVariable int id) {
        return dao.deleteProduct(id);
    }
}
