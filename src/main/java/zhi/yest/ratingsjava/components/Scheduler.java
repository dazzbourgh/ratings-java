package zhi.yest.ratingsjava.components;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.wall.WallPostFull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scheduler {
    @Autowired
    WallScanner scanner;
    @Autowired
    PostProcessor processor;
    @Autowired
    AudioListCreator creator;

    @Scheduled(cron = "0 5 * * * *")
    private void globalTop() throws ClientException, ApiException {
        List<WallPostFull> posts = scanner.getPosts();
        List<String> globalTop = processor.getGlobalTop(posts);
        creator.addTracksToTop(globalTop, AudioListCreator.Top.GLOBAL);
    }
}
