package com.keystone.delivery_service.service.impl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.keystone.delivery_service.service.GoogleTokenVerifierService;

@Service
public class GoogleTokenVerifierServiceImpl
        implements GoogleTokenVerifierService {

    @Value("${google.client.id}")
    private String googleClientId;
    @Override
    public GoogleIdToken.Payload verify(String idTokenString)
            throws GeneralSecurityException, IOException {

        System.out.println("=================================");
        System.out.println("CLIENT ID : " + googleClientId);
        System.out.println("TOKEN LENGTH : " + idTokenString.length());

        GoogleIdTokenVerifier verifier =
                new GoogleIdTokenVerifier.Builder(
                        new NetHttpTransport(),
                        GsonFactory.getDefaultInstance())
                        .setAudience(Collections.singletonList(googleClientId))
                        .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);

        System.out.println("VERIFY RESULT : " + idToken);

        if (idToken == null) {
            throw new RuntimeException("Invalid Google Token");
        }

        GoogleIdToken.Payload payload = idToken.getPayload();

        System.out.println("EMAIL : " + payload.getEmail());
        System.out.println("AUDIENCE : " + payload.getAudience());

        return payload;
    }
}