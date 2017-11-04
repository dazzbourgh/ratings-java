package zhi.yest.ratingsjava.components;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.WallPostFull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WallScanner {
    @Autowired
    private VkApiClientBean vk;
    private static Integer WALL_ID = -31352730;

    public List<WallPostFull> getPosts() throws ClientException, ApiException {
        List<WallPostFull> posts = new ArrayList<>(1000);
        for (int i = 0; i < 10; i++) {
            posts.addAll(vk.wall()
                    .get(vk.getActor())
                    .count(100)
                    .offset(i * 100)
                    .ownerId(WALL_ID)
                    .execute()
                    .getItems());
        }
        return posts;
    }
}
