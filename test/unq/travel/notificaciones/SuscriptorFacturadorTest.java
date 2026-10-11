package unq.travel.notificaciones;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SuscriptorFacturadorTest {

	private EstadoDeReserva estado;
	private Reserva reserva;
	private Suscritor suscriptor;
	private Cliente cliente;
	
	@BeforeEach
	void setUp() throws Exception {
	
		cliente = mock(Cliente.class);
		estado = mock(EstadoDeReserva.class);
		reserva = mock(Reserva.class);
		
		suscriptor = new SuscriptorFacturador(mailSender);
	
	}

	@Test
	void testNotify() {

		suscriptor.notify(reserva, estado);
		
		verify(estado).generarFactura(reserva);
		
	}

}