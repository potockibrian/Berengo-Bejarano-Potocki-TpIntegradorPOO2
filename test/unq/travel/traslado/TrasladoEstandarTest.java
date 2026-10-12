package unq.travel.traslado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrasladoEstandarTest {
	
	private EstrategiaTraslado estrategia;
	
	@BeforeEach
	void setUp() {
		
		estrategia = new TrasladoEstandar(10, mock(Direccion.class));
		
	}
	
	@Test
	void testCostoTraslado() {
		
		assertNotEquals(estrategia.costoTraslado(), 0);
		
	}
	
	@Test
	void testHayDisponibilidad() {
		
		assertTrue(estrategia.hayDisponibilidad());
		
	}
	
}