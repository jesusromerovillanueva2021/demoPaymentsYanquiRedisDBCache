package com.payment.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("yanquipayment")
public class YanquiPayment implements Serializable {
	@Id
	private int id;
	private Date date;
	private double amount;
	private int idSenderUser;
	private int idReceiverUser;	
}
