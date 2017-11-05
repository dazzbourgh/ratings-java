package zhi.yest.ratingsjava.cache;

import org.springframework.stereotype.Component;
import zhi.yest.ratingsjava.components.AudioListCreator;

import java.util.*;

@Component
public class Cache {
    private final Map<AudioListCreator.Top, List<String>> topListMap;

    public Cache() {
        topListMap = new HashMap<>();
        Arrays.stream(AudioListCreator.Top.values())
                .forEach(top -> topListMap.put(top, Collections.emptyList()));
    }

    public void refreshTop(AudioListCreator.Top top, List<String> freshAudios) {
        topListMap.put(top, freshAudios);
    }

    public List<String> getTop(AudioListCreator.Top top) {
        return topListMap.get(top);
    }
}
