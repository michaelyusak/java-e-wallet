package com.java_e_wallet.e_wallet_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.java_e_wallet.e_wallet_service.adaptor.Kafka;
import com.java_e_wallet.e_wallet_service.config.Config;

@SpringBootApplication
@EnableTransactionManagement
public class EWalletServiceApplication {

	public static void main(String[] args) {
		Config.Init();

		Kafka.Init();

		SpringApplication.run(EWalletServiceApplication.class, args);
	}

}
