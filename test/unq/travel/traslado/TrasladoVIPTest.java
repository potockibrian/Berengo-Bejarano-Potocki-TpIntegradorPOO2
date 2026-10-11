package unq.travel.traslado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TrasladoVIPTest {
	
	private EstrategiaTraslado estrategia;
	private Reserva reserva;
	
	@BeforeEach
	void setUp() {
		
		reserva = mock(Reserva.class);
		estrategia = new TrasladoVIP(reserva);
		
	}
	
	@Test
	void testCostoTraslado() {
		
		estrategia.costoTraslado();
		
		verify(reserva).getPrecioTotalServicios();
		
	}
	
}