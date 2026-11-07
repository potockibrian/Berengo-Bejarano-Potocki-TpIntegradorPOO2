package unq.travel.traslado;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import libraries.TrasladoVIP;

class EstrategiaTrasladoVIPTest {
	
	private EstrategiaTraslado estrategia;
	private Reserva reserva;
	private TrasladoVIP libreria;
	private Direccion direccion;
	
	@BeforeEach
	void setUp() {
		
		reserva = mock(Reserva.class);
		libreria = mock(TrasladoVIP.class);
		estrategia = new EstrategiaTrasladoVIP(libreria);
		
	}
	
	@Test
	void testCostoTraslado() {
		
		when(reserva.costoServicios()).thenReturn(1000f);
		
		estrategia.costoTasasTraslado(reserva);

		verify(libreria).calcularCosto(1000f);
		
	}
	
}