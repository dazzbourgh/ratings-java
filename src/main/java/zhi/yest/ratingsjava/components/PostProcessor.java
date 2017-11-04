package zhi.yest.ratingsjava.components;

import com.vk.api.sdk.objects.wall.WallPostFull;
import com.vk.api.sdk.objects.wall.WallpostAttachmentType;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PostProcessor {

    public List<String> getGlobalTop(List<WallPostFull> posts) {
        return posts.stream()
                .filter(this::isRelease)
                .sorted(Comparator.comparing(o -> o.getReposts().getCount()))
                .limit(10)
                .map(this::getAudioId)
                .collect(Collectors.toList());
    }

    private String getAudioId(WallPostFull post) {
        return post.getAttachments()
                .stream()
                .filter(a -> a.getType().equals(WallpostAttachmentType.AUDIO))
                .limit(1)
                .map(a -> a.getAudio().getOwnerId() + "_" + a.getAudio().getId())
                .collect(Collectors.joining());
    }

    private boolean isRelease(WallPostFull post) {
        String text = post.getText();
        return (text.contains("#speeeedyRelease") || text.contains("#speeeedyExclusive"))
                && text.contains("Label:") && text.contains("Style:")
                && (new Date().toInstant().getNano() - post.getDate() < 1);
    }
}
