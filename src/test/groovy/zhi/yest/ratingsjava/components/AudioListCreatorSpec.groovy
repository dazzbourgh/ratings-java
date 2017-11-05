package zhi.yest.ratingsjava.components

import spock.lang.Specification
import zhi.yest.ratingsjava.cache.Cache
import zhi.yest.ratingsjava.util.VkApiClientBean

class AudioListCreatorSpec extends Specification {
    AudioListCreator audioListCreator
    def vk = Mock(VkApiClientBean)
    def cache = Mock(Cache)

    def setup() {
        audioListCreator = new AudioListCreator()
        audioListCreator.vk = vk
        audioListCreator.cache = cache
    }

    def "should add"() {
        given:
        def audioId = "99225586_456240212"
        List<String> tracks = Collections.singletonList("")
        when:
        audioListCreator.addTracksToTop(Collections.singletonList(audioId), AudioListCreator.Top.GLOBAL)
        then:
        1 * cache.getTop(AudioListCreator.Top.GLOBAL) >> tracks
        2 * vk.submit(_)
        1 * cache.refreshTop(AudioListCreator.Top.GLOBAL, _)
    }
}
