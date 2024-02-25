package pl.artur.zaczek.fit.user.app;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = FitUserAppApplication.class)
@ActiveProfiles(profiles = "test")
class FitUserAppApplicationTestsResponse {

	@Test
	void contextLoads() {
	}

}
