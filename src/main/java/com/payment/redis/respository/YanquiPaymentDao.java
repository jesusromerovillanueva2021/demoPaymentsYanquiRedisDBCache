package com.payment.redis.respository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.payment.redis.entity.YanquiPayment;

import java.util.List;

@Repository
public class YanquiPaymentDao {

    public static final String HASH_KEY = "yanquipayment";

    @Autowired
    private RedisTemplate template;

    public YanquiPayment save(YanquiPayment  yanquiPayment){
        template.opsForHash().put(HASH_KEY, yanquiPayment.getId(), yanquiPayment);
        return  yanquiPayment;
    }

    public List<YanquiPayment> findAll(){
        return template.opsForHash().values(HASH_KEY);
    }

    public YanquiPayment findProductById(int id){
        return (YanquiPayment) template.opsForHash().get(HASH_KEY,id);
    }


    public String deleteProduct(int id){
        template.opsForHash().delete(HASH_KEY,id);
        return "payment removed !!";
    }
}
