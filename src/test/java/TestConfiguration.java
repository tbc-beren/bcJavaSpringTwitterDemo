import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("io.swagger.api")
@ComponentScan("com.blackcodex.demo.spring.twitter")
public class TestConfiguration {
}
