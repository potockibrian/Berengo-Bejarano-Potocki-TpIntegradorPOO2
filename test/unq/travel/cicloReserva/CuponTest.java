package unq.travel.cicloReserva;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CuponTest {

	private Cupon cupon;
	
	@BeforeEach
	void setUp() throws Exception {
		
		cupon = new Cupon(5);
		
	}

	@Test
	void testDescuento() {

		cupon.setDescuento(10);
		
		assertEquals(10, cupon.getDescuento());
		
	}

}
