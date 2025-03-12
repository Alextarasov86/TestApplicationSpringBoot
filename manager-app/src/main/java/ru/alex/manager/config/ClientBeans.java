package ru.alex.manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.web.client.RestClient;
import ru.alex.manager.client.RestClientProductsRestClient;
import ru.alex.manager.security.OAuthClientHttpRequestInterceptor;

@Configuration
public class ClientBeans {

//    @Bean
//    public RestClientProductsRestClient productsRestClient(
//            @Value("${selmag.services.catalogue.uri:http://localhost:8080}") String catalogueBaseUri,
//            @Value("${selmag.services.catalogue.username:}") String catalogueUsername,
//            @Value("${selmag.services.catalogue.password:}") String cataloguePassword){
//        return new RestClientProductsRestClient(RestClient.builder().
//                baseUrl(catalogueBaseUri).
//                requestInterceptor(new BasicAuthenticationInterceptor(catalogueUsername,
//                        cataloguePassword)).
//                build());
//    }

    @Bean
    public RestClientProductsRestClient productsRestClient(
            @Value("http://localhost:8081") String catalogueBaseUrl,
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientRepository authorizedClientRepository,
            @Value("${selmag.services.catalogue.registration-id:keycloak}") String registrationId) {
        return new RestClientProductsRestClient(RestClient.builder().
                baseUrl(catalogueBaseUrl).
                requestInterceptor(new OAuthClientHttpRequestInterceptor(
                        new DefaultOAuth2AuthorizedClientManager(
                                clientRegistrationRepository,
                                authorizedClientRepository),
                        registrationId)).build());
    }
}
