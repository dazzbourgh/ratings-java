package zhi.yest.ratingsjava.components;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.wall.WallPostFull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class WallScanner {
    @Value("${vk.service.key}")
    private String accessToken;
    @Value("${vk.user.id}")
    private String userId;
    @Value("${vk.app.id}")
    private String appId;
    private ServiceActor actor;
    private VkApiClient vk;
    private static Integer WALL_ID = -31352730;

    @PostConstruct
    private void authenticate() {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient);
        try {
            actor = new ServiceActor(Integer.parseInt(appId), accessToken);
        } catch (Exception e) {
            throw new Error("Unable to authenticate.", e);
        }
    }

    public List<WallPostFull> getPosts() throws ClientException, ApiException {
        List<WallPostFull> posts = new ArrayList<>(1000);
        for (int i = 0; i < 10; i++) {
            posts.addAll(vk.wall()
                    .get(actor)
                    .count(100)
                    .offset(i * 100)
                    .ownerId(WALL_ID)
                    .execute()
                    .getItems());
        }
        return posts;
    }
}
