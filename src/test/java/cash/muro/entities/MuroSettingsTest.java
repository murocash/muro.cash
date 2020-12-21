package cash.muro.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

class MuroSettingsTest {

	private static MuroSettings settings;
	
	@BeforeAll
	static void init() {
		settings = new MuroSettings(1, 5, 4, 2);
	}
	
	@Test
	void testPageRequest() {
		assertEquals(PageRequest.of(2, 4), settings.pageRequest(2));
	}

	@Test
	void testLimitList() {
		List<Integer> list = IntStream.range(0, settings.getRowsPerPage()).boxed().collect(Collectors.toList());
		assertEquals(list.size(), settings.getRowsPerPage());
		int newSize = settings.limitList(list).size();
		assertEquals(newSize, settings.getFreeRows());
		assertEquals(settings.limitList(list).get(newSize - 1), newSize - 1);
	}

}
