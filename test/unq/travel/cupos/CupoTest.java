package unq.travel.cupos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.*;

class CupoTest {

	private Cupo cupo;
	
	@BeforeEach
	void setUp() throws Exception {
	
		cupo = new Cupo(5);
	
	}

	@Test
	void testCantidadDeCuposDisponibles() {

		cupo.reservar();
		
		assertEquals(cupo.getTopeMaximo() - cupo.cantidadDeVentas(),  cupo.cantidadDeCuposDisponibles());
		
	}
	
	@Test
	void testCantidadDeVentas() {
		
		cupo.reservar();
		
		assertEquals(1, cupo.cantidadDeVentas());
		
	}
	
	@Test
	void testLiberar() {
		
		cupo.reservar();
		
		cupo.liberar();
		
		assertEquals(0, cupo.cantidadDeVentas());
		
	}
	
	@Test
	void testTieneDisponibilidad() {
		
		assertTrue(cupo.tieneDisponibilidad());
		
	}
	
	@Test
	void testReservarSinDisponibilidad() {
		
		for(int i=0; i<5; i++) cupo.reservar();
		
		assertThrows(SinStockException.class, () -> cupo.reservar());
		
		assertFalse(cupo.tieneDisponibilidad());
		
	}
	
	@Test
	void testTopeMaximo() {
		
		assertEquals(5, cupo.getTopeMaximo());
		
	}

}
