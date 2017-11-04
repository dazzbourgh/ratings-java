package zhi.yest.ratingsjava.components

import com.vk.api.sdk.objects.wall.WallPostFull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class WallScannerSpec extends Specification {
    @Autowired
    WallScanner sut

    def "shouldGetPosts"() {
        when:
        List<WallPostFull> posts = sut.getPosts()
        then:
        posts.size() == 1000
    }
}
