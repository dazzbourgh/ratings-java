package zhi.yest.ratingsjava.util;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.Map;

@Component
public class VkApiClientBean extends VkApiClient {
    private static TransportClient client = new HttpTransportClient();
    @Getter
    private ServiceActor actor;
    @Value("${vk.service.key}")
    private String serviceKey;
    @Value("${vk.access.token}")
    private String accessToken;
    @Value("${vk.app.id}")
    private String appId;
    @Value("${vk.api.version}")
    private String apiVersion;
    @Value("${vk.api.path}")
    private String vkApiPath;

    public VkApiClientBean() {
        super(client);
    }

    @PostConstruct
    private void authenticate() {
        try {
            actor = new ServiceActor(Integer.parseInt(appId), serviceKey);
        } catch (Exception e) {
            throw new Error("Unable to authenticate.", e);
        }
    }

    public String submit(Map<String, String> params) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(vkApiPath + params.get("method"))
                .queryParam("access_token", accessToken)
                .queryParam("v", apiVersion);

        params.entrySet().stream()
                .filter(e -> !"method".equals(e.getKey()))
                .forEach(e -> builder.queryParam(e.getKey(), e.getValue()));

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.GET,
                entity,
                String.class);
        return response.getBody();
    }

}
