package com.java_e_wallet.e_wallet_service.config;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import lombok.Getter;

@Getter
public class Config {
    private Long AccessTokenTTL; // in minutes
    private Long RefreshTokenTTL; // in minutes
    private String TokenSecretKey;
    private String TokenIssuer;

    private static Config configInstance;

    private Config() {}

    private Optional<JSONObject> readConfig() {
        JSONParser parser = new JSONParser();

        String configFilePath = System.getenv("JAVA_E_WALLET_CONF");

        try {
            JSONObject data = (JSONObject) parser.parse(new FileReader(configFilePath));

            return Optional.of(data);

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            return Optional.empty();

        } catch (IOException ex) {
            ex.printStackTrace();
            return Optional.empty();

        } catch (ParseException ex) {
            ex.printStackTrace();
            return Optional.empty();

        }
    }

    public static void Init() {
        if (configInstance == null) {
            configInstance = new Config();
        }

        Optional<JSONObject> data = configInstance.readConfig();
        if (!data.isPresent()) {
            System.err.println("Failed to load configuration.");
        }

        JSONObject configData = data.get();

        configInstance.AccessTokenTTL = (Long) configData.get("access_token_ttl");
        configInstance.RefreshTokenTTL = (Long) configData.get("refresh_token_ttl");
        configInstance.TokenSecretKey = (String) configData.get("token_secret_key");
        configInstance.TokenIssuer = (String) configData.get("token_issuer");
    }

    public static Config getConfigInstance() {
        if (configInstance == null) {
            throw new IllegalStateException("Config must be initialized first by calling Init().");
        }
        return configInstance;
    }
}
