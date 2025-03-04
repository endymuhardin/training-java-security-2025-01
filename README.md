# Training Java Security 2025-01 #

Materi Training

* Application Security Overview
* OWASP Top 10 Overview
* PCI-DSS
* Secure App Fundamentals

  * Hash, Encode, Encrypt
  * Symmetric vs Asymmetric Encryption
  * Public Key Infrastructure
  * HTTPS Protocol
  * Authentication

    * Multi Factor Authentication
    * Non-human authentication

  * Password Management

    * Password Storage
    * Password Reset
  
  * Authorization
  * Session Management

* OWASP Top 10 In-Depth
* OWASP WebGoat
* SAST
* DAST

## Menjalankan Keycloak ##

```
docker compose -f docker-compose-keycloak.yml up
```

## OAuth 2 ##

[![OAuth Overview](img/oauth-openid-overview.png)](img/oauth-openid-overview.png)

### Flow Authorization Code ###

[![OAuth Authcode](img/oauth-authcode.png)](img/oauth-authcode.png)

1. Create client di Keycloak

    [![Create client authcode](img/00-create-client-authcode.png)](img/00-create-client-authcode.png)

    [![Create client app di keycloak](img/01-client-id.png)](img/01-client-id.png)

2. Client secret di Keycloak

    [![Client secret](img/02-client-secret.png)](img/02-client-secret.png)

3. Endpoints untuk melakukan OAuth dance

    [![Endpoints](img/03-endpoints.png)](img/03-endpoints.png)
  
4. Authorization request dilakukan ke URL berikut : [http://localhost:20000/realms/pelatihan/protocol/openid-connect/auth?response_type=code&client_id=aplikasi-hr&redirect_uri=https://example.com/](http://localhost:20000/realms/pelatihan/protocol/openid-connect/auth?response_type=code&client_id=aplikasi-hr&redirect_uri=https://example.com/)

5. Login ke keycloak dengan credential:

    * Username : `user001`
    * Password : `abcd1234`

6. Dapatkan authcode dari URL hasil redirect, misalnya seperti ini

    ```
    https://example.com/?session_state=59cc5b94-956c-4581-a683-a99832a2df39&iss=http%3A%2F%2Flocalhost%3A20000%2Frealms%2Fpelatihan&code=52b7c206-4a05-4e5a-af77-817a09f2d879.59cc5b94-956c-4581-a683-a99832a2df39.32e2b62b-ef73-4597-8cd7-fceea67496c8
    ```

7. Gunakan authcode di token request

    [![Token request](img/04-authorization-request-body.png)](img/04-authorization-request-body.png)

8. Authentication menggunakan Basic Auth dengan credential `client_id` dan `client_secret`

    [![Token Request Auth](img/04-authorization-request-auth.png)](img/04-authorization-request-auth.png)

9. Mendapatkan `access_token` dalam format JWT

    ```json
    {
      "access_token": "eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJaV3kxM2N4eWJhcDVzckd0R1hNRlQzU2Z4VDM1bHJuMllNRVRRNXpPNzRRIn0.eyJleHAiOjE3NDEwNjQwMzEsImlhdCI6MTc0MTA2MzczMSwiYXV0aF90aW1lIjoxNzQxMDYzMzgzLCJqdGkiOiI1Mzc1YTNlMC01NGY3LTQxMjAtYWMwNC1iZmQyYTk5MGNhY2IiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjIwMDAwL3JlYWxtcy9wZWxhdGloYW4iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiZjBkMDI2YmQtOTE5Yy00NzQxLWIzMzctNWJiZDlkYTM4YzJkIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYXBsaWthc2ktaHIiLCJzaWQiOiI1OWNjNWI5NC05NTZjLTQ1ODEtYTY4My1hOTk4MzJhMmRmMzkiLCJhY3IiOiIwIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHBzOi8vZXhhbXBsZS5jb20iXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJkZWZhdWx0LXJvbGVzLXBlbGF0aWhhbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IlVzZXIgMDAxIiwicHJlZmVycmVkX3VzZXJuYW1lIjoidXNlcjAwMSIsImdpdmVuX25hbWUiOiJVc2VyIiwiZmFtaWx5X25hbWUiOiIwMDEiLCJlbWFpbCI6InVzZXIwMDFAeW9wbWFpbC5jb20ifQ.NSXSbcgOCKruS_E1L3e47lHWqr_suSYn-vHh_SElon7ppzMYnJBR2ckp-8_fNruvJRgV6Zug5updYPF_aGWqudw1Iht9smoeCuPp_BGfI8mM1qm8F7j3xuCu9yMIQ_5kSBp-zjZNX5tk44m8eL3ICj92gCx8zN38pT1UTWCTsqlZy7fWfDIvKcYQ_FbiJNnvK1BhNVEvzu1TqO1pYm5B2QQljqTY6ALi6SlGKkRW2uvSeWI55xJELcag8O1bpG6sdUWAvc_dFJbcKF5sQY_QR1D2RK_lEmkGe6UHSCPgiocxLP8noOkab7ApDFPUh5jzYaaoo3FMbMutMTRPYtgFLg",
      "expires_in": 300,
      "refresh_expires_in": 1800,
      "refresh_token": "eyJhbGciOiJIUzUxMiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI1N2VlYjNmYy05YjdmLTRiNmYtYTdiNy1hZTNlYWE1ZmE5YTAifQ.eyJleHAiOjE3NDEwNjU1MzEsImlhdCI6MTc0MTA2MzczMSwianRpIjoiZDNjNWYyZjItYzgxYS00YWU1LTg5YzItMzhlZTUxNTQ3ZmI1IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDoyMDAwMC9yZWFsbXMvcGVsYXRpaGFuIiwiYXVkIjoiaHR0cDovL2xvY2FsaG9zdDoyMDAwMC9yZWFsbXMvcGVsYXRpaGFuIiwic3ViIjoiZjBkMDI2YmQtOTE5Yy00NzQxLWIzMzctNWJiZDlkYTM4YzJkIiwidHlwIjoiUmVmcmVzaCIsImF6cCI6ImFwbGlrYXNpLWhyIiwic2lkIjoiNTljYzViOTQtOTU2Yy00NTgxLWE2ODMtYTk5ODMyYTJkZjM5Iiwic2NvcGUiOiJyb2xlcyBwcm9maWxlIGJhc2ljIGFjciB3ZWItb3JpZ2lucyBlbWFpbCJ9.mlADNuZBu2JXdnRIKgcjAPMBLXOLRBxu8b0Sm-wMg0plwCaybCV_1YWivaEPUqIm3uQyZMBMoKcZGfnSQilahw",
      "token_type": "Bearer",
      "not-before-policy": 0,
      "session_state": "59cc5b94-956c-4581-a683-a99832a2df39",
      "scope": "profile email"
    }
    ```

10. Isinya bisa dilihat di [JWT.io](https://jwt.io/)

    [![JWT.io](img/05-jwt-io.png)](img/05-jwt-io.png)


### Flow Implicit ###

[![Flow Implicit](img/oauth-implicit.png)](img/oauth-implicit.png)

1. Buat implicit client di Keycloak

    [![Implicit client](img/create-client-implicit.png)](img/create-client-implicit.png)

2. Lakukan authorization request ke url berikut

    ```
    http://localhost:20000/realms/pelatihan/protocol/openid-connect/auth?response_type=token&client_id=aplikasi-absen&redirect_uri=https://example.com/
    ```

3. Login ke keycloak dengan credential:

    * Username : `user001`
    * Password : `abcd1234`

4. Mendapatkan access token langsung di redirect url seperti ini

    ```
    https://example.com/#session_state=25d571a9-6a1a-4d2d-809c-ba9c8eee8344&iss=http%3A%2F%2Flocalhost%3A20000%2Frealms%2Fpelatihan&access_token=eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJaV3kxM2N4eWJhcDVzckd0R1hNRlQzU2Z4VDM1bHJuMllNRVRRNXpPNzRRIn0.eyJleHAiOjE3NDEwNzA4NTAsImlhdCI6MTc0MTA2OTk1MCwiYXV0aF90aW1lIjoxNzQxMDY5OTUwLCJqdGkiOiJhYjdkYjI5Zi0xYWRkLTRhZGQtOGVhNS01OWFjMWUzNTZhNzkiLCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjIwMDAwL3JlYWxtcy9wZWxhdGloYW4iLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiZjBkMDI2YmQtOTE5Yy00NzQxLWIzMzctNWJiZDlkYTM4YzJkIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYXBsaWthc2ktYWJzZW4iLCJzaWQiOiIyNWQ1NzFhOS02YTFhLTRkMmQtODA5Yy1iYTljOGVlZTgzNDQiLCJhY3IiOiIxIiwiYWxsb3dlZC1vcmlnaW5zIjpbImh0dHBzOi8vZXhhbXBsZS5jb20iXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJkZWZhdWx0LXJvbGVzLXBlbGF0aWhhbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwibmFtZSI6IlVzZXIgMDAxIiwicHJlZmVycmVkX3VzZXJuYW1lIjoidXNlcjAwMSIsImdpdmVuX25hbWUiOiJVc2VyIiwiZmFtaWx5X25hbWUiOiIwMDEiLCJlbWFpbCI6InVzZXIwMDFAeW9wbWFpbC5jb20ifQ.auegNh0NWnr6gFzE1DiFRaCoiJ1V5C6vwbFtBaPTlT3tGLsivqyz6anPhQ1rBZKtatW_34pdKipYL2tfWMVPPz8EK_eS16KPmcCo-mo7Irddgsd3s426fzVgGkI62BOqP90xq0fETX4KhsXygqlP6jTqv7PJVGuAYsjnPcgm2b3BPfCmgX1Tgacb4PKdPIQf5Gm6Hiw4Beh4KlGpRFukiBryWtx0SXoDL6RD6zusw8uhBf1tvgzaOtFVOE6EA9TZ0An2HfDCgjSX5Hq5o36-Uqr76EFwljGL9CS-qkSYS6Qfc8jvI_O4uIBtazDVoxuNddsnxzNjMyDRF_VpPcreUQ&token_type=Bearer&expires_in=900
    ```

### Flow Resource Owner Password ###

[![Resource Owner Password](img/resource-owner-password.png)](img/resource-owner-password.png)

### Flow Client Credentials ###

[![Client Credentials](img/client-credentials.png)](img/client-credentials.png)

### Flow Device ###

[![Device](img/device-flow.png)](img/device-flow.png)

# Referensi #

* [OWASP Top Ten](https://owasp.org/www-project-top-ten/)
* [PCI-DSS Specification](https://www.pcisecuritystandards.org/document_library/)
* [Enkripsi Data dalam Aplikasi - Artikel](https://software.endy.muhardin.com/java/enkripsi-data-dalam-aplikasi/)
* [Enkripsi Data dalam Aplikasi - Video](https://youtube.com/playlist?list=PL9oC_cq7OYbxlqa42LwWaukJ1gIxc_-9U&si=rpaTkNmPunyYzB_F)
* [Seri Video Application Security](https://youtube.com/playlist?list=PL9oC_cq7OYbwClMMWLTgXr3zz9sQ_JW76&si=j41nD0cjekUu8eNy)
* [Spesifikasi OAuth 2](https://datatracker.ietf.org/doc/html/rfc6749)
* [Spesifikasi PKCE](https://www.rfc-editor.org/rfc/rfc7636)
* [OAuth for Device](https://datatracker.ietf.org/doc/html/rfc8628)
* [Best Practices OAuth 2](https://datatracker.ietf.org/doc/html/rfc9700)
* [OWASP Web Goat](https://owasp.org/www-project-webgoat/)