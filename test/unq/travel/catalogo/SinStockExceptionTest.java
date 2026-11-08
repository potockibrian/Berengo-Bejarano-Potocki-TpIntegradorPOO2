package unq.travel.catalogo;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SinStockExceptionTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testSinStockException() {

		try {
			
			throw new SinStockException("error");
			
		} catch (SinStockException e) {
			
			assertEquals("error", e.getMessage());
			
		}
			
	}

}
