package unq.travel.notificaciones;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.cicloReserva.Cliente;
import unq.travel.cicloReserva.EstadoDeReserva;
import unq.travel.cicloReserva.MailSenderI;
import unq.travel.traslado.Reserva;

class SuscriptorCorreoTest {

	private EstadoDeReserva estado;
	private Reserva reserva;
	private MailSenderI mailSender;
	private Suscriptor suscriptor;
	private Cliente cliente;
	
	@BeforeEach
	void setUp() throws Exception {
	
		cliente = mock(Cliente.class);
		estado = mock(EstadoDeReserva.class);
		reserva = mock(Reserva.class);
		mailSender = mock(MailSenderI.class);
		
		suscriptor = new SuscriptorCorreo(mailSender);
	
	}

	@Test
	void testNotify() {

		suscriptor.notify(reserva, estado);
		
		verify(estado).notificarPorEmail(reserva,mailSender);
		
	}

}
