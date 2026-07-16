package com.keystone.delivery_service.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

public interface GoogleTokenVerifierService {

    GoogleIdToken.Payload verify(String idToken)
            throws GeneralSecurityException, IOException;

}