package unq.travel.notificaciones;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.cicloReserva.Cliente;
import unq.travel.cicloReserva.EstadoDeReserva;
import unq.travel.traslado.Reserva;

class SuscriptorFacturadorTest {

	private EstadoDeReserva estado;
	private Reserva reserva;
	private Suscriptor suscriptor;
	private Cliente cliente;
	
	@BeforeEach
	void setUp() throws Exception {
	
		cliente = mock(Cliente.class);
		estado = mock(EstadoDeReserva.class);
		reserva = mock(Reserva.class);
		
		suscriptor = new SuscriptorFacturador();
	
	}

	@Test
	void testNotify() {

		suscriptor.notify(reserva, estado);
		
		verify(estado).generarFactura(reserva);
		
	}

}