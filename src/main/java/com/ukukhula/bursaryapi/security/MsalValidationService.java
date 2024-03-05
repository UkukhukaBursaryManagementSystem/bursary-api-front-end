package com.ukukhula.bursaryapi.security;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.auth0.jwt.exceptions.SignatureVerificationException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.*;
import java.util.Date;

public class MsalValidationService
{


    public boolean validateSignature(String webToken)
    {
        DecodedJWT jwt = JWT.decode(webToken);
        System.out.println(jwt.getIssuer());
        Date date = new Date();

        JwkProvider provider = null;
        Jwk jwk =null;

        try {
            provider = new UrlJwkProvider(new URL("https://login.microsoftonline.com/common/discovery/keys"));
            jwk = provider.get(jwt.getKeyId());
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(jwk.getPublicKey());

            byte[] tokenByte = (jwt.getPayload().toString()).getBytes();
            signature.update(tokenByte);
            System.out.println(signature.verify(jwt.getSignature().getBytes()));
            if (signature.verify(jwt.getSignature().getBytes()) || jwt.getExpiresAt().after(date))
            {
                return true;
            }   return false;

        } catch (MalformedURLException e) {
            return false;
        } catch (JwkException e) {
            return false;
        }catch(SignatureVerificationException e){
            return false;
        } catch (NoSuchAlgorithmException e){
            return false;
        } catch (InvalidKeyException e) {
            return false;
        } catch (SignatureException e) {
            return false;
        }
    }
}