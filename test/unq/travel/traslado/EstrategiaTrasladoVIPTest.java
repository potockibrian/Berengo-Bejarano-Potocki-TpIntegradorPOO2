package unq.travel.traslado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EstrategiaTrasladoVIPTest {
	
	private EstrategiaTraslado estrategia;
	private Reserva reserva;
	
	@BeforeEach
	void setUp() {
		
		reserva = mock(Reserva.class);
		estrategia = new EstrategiaTrasladoVIP(reserva);
		
	}
	
	@Test
	void testCostoTraslado() {
		
		estrategia.costoTraslado();
		
		verify(reserva).getPrecioTotalServicios();
		
	}
	
	@Test
	void testHayDisponibilidad() {
		
		assertTrue(estrategia.hayDisponibilidad());
		
	}
	
}