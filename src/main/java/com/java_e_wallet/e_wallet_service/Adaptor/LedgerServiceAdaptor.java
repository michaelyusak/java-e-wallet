package com.java_e_wallet.e_wallet_service.adaptor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java_e_wallet.e_wallet_service.config.Config;
import com.java_e_wallet.e_wallet_service.dto.ResponseDTO;
import com.java_e_wallet.e_wallet_service.exception.AppException;

public class LedgerServiceAdaptor {
    private static final ObjectMapper mapper = new ObjectMapper();

    private static String baseUrl;

    public static void Init() {
        baseUrl = Config.getLedgerServiceAddress();
    }

    public static ResponseDTO getUserLedgers(Long userId) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(baseUrl + "/ledgers/" + String.valueOf(userId)))
                .GET().build();

        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request,
                    HttpResponse.BodyHandlers.ofString());

            return mapper.readValue(response.body(), ResponseDTO.class);
        } catch (Exception ex) {
            throw new AppException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "failed to get ledger from ledger service",
                    ex.getMessage());
        }
    }
}
