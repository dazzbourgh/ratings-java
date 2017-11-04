package zhi.yest.ratingsjava.components;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class AudioListCreator {
    @Autowired
    VkApiClientBean vk;
    @Value("${vk.add.to.album}")
    String addToAlbum;

    //TODO: change to actual REST template
    public void addTracksToTop(List<String> tracks, Top top) throws ClientException, ApiException {
        tracks.forEach(track -> {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getForObject(addToAlbum + top, String.class, "");
        });
    }

    public enum Top {
        GLOBAL("global"),
        PROGRESSIVE_HOUSE("global"),
        DUBSTEP("global"),
        TRAP("global"),
        FUTURE_HOUSE("global"),
        BASS_HOUSE("global"),
        HARDSTYLE("global");

        private String name;

        Top(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }
}
