package zhi.yest.ratingsjava.components

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class SchedulerSpec extends Specification {
    @Autowired
    Scheduler scheduler

    def "should create global top"() {
        when:
        scheduler.globalTop()
        then:
        true
    }
}
