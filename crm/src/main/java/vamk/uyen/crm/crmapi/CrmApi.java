package vamk.uyen.crm.crmapi;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class CrmApi {


    public Flux<UserTest> getAllUsers() {

        WebClient webClient = WebClient.builder().baseUrl("https://jsonplaceholder.typicode.com/").build();

        return webClient.get()
                .uri("/users")
                .retrieve()
                .bodyToFlux(UserTest.class);
    }
}
