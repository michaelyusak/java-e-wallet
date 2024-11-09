package com.java_e_wallet.e_wallet_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.java_e_wallet.e_wallet_service.config.Config;

@SpringBootApplication
public class EWalletServiceApplication {

	public static void main(String[] args) {
		Config.Init();

		SpringApplication.run(EWalletServiceApplication.class, args);
	}

}
