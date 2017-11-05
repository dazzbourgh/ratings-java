package zhi.yest.ratingsjava.components;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zhi.yest.ratingsjava.cache.Cache;
import zhi.yest.ratingsjava.util.VkApiClientBean;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AudioListCreator {
    @Autowired
    VkApiClientBean vk;
    @Autowired
    Cache cache;
    @Value("${vk.access.token}")
    private String accessToken;
    @Value("${vk.group.id}")
    private String groupId;

    public void addTracksToTop(List<String> tracks, Top top) throws ClientException, ApiException {
        removeTracksFromTop(top);
        vk.submit(getParams("audio.addToPlaylist", Top.GLOBAL, tracks));
        cache.refreshTop(top, tracks);
    }

    private void removeTracksFromTop(Top top) {
        List<String> tracks = cache.getTop(top);
        if (tracks.isEmpty())
            return;
        vk.submit(getParams("audio.removeFromPlaylist", top, tracks));
    }

    private Map<String, String> getParams(String method, Top top, List<String> tracks) {
        return Map.of("method", method,
                "owner_id", getOwner(tracks),
                "playlist_id", top.toString(),
                "audio_ids", tracks.stream()
                        .collect(Collectors.joining(","))
        );
    }

    //TODO: this doesn't work as owner is different for some reason
    private String getOwner(@NotEmpty List<String> tracks) {
        return tracks.get(0).split("_")[0];
    }

    //TODO: set actual ids
    public enum Top {
        GLOBAL("1"),
        PROGRESSIVE_HOUSE("global"),
        DUBSTEP("global"),
        TRAP("global"),
        FUTURE_HOUSE("global"),
        BASS_HOUSE("global"),
        HARDSTYLE("global");

        private String id;

        Top(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return this.id;
        }
    }
}
