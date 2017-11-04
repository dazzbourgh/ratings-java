package zhi.yest.ratingsjava.components;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class VkApiClientBean extends VkApiClient {
    private static TransportClient client = new HttpTransportClient();
    @Getter
    private ServiceActor actor;
    @Value("${vk.service.key}")
    private String accessToken;
    @Value("${vk.app.id}")
    private String appId;

    public VkApiClientBean() {
        super(client);
    }

    @PostConstruct
    private void authenticate() {
        try {
            actor = new ServiceActor(Integer.parseInt(appId), accessToken);
        } catch (Exception e) {
            throw new Error("Unable to authenticate.", e);
        }
    }
}
