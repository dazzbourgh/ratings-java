package zhi.yest.ratingsjava.components

import com.vk.api.sdk.objects.wall.WallPostFull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class PostProcessorSpec extends Specification {
    @Autowired
    PostProcessor sut
    @Autowired
    WallScanner scanner
    List<WallPostFull> posts

    void setup() {
        posts = scanner.getPosts()
    }

    def "should return global top"() {
        given:
        List<String> audios
        when:
        audios = sut.getGlobalTop(posts)
        then:
        audios.size() == 10
    }
}
