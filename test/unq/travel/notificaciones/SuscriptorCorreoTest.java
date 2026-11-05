package unq.travel.notificaciones;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.cicloReserva.Cliente;
import unq.travel.cicloReserva.*;
import unq.travel.cicloReserva.MailSender;
import unq.travel.traslado.Reserva;

class SuscriptorCorreoTest {

	private EstadoDeReserva estado;
	private Reserva reserva;
	private MailSender mailSender;
	private Suscriptor suscriptor;
	private Cliente cliente;
	private EstadoDeReserva estadoNuevo;
	private EstadoDeReserva estadoAnterior;
	
	@BeforeEach
	void setUp() throws Exception {
	
		cliente = mock(Cliente.class);
		reserva = mock(Reserva.class);
		estadoAnterior = mock(EstadoDeReserva.class);
		mailSender = mock(MailSender.class);
		when(reserva.getCliente()).thenReturn(cliente);
		
		suscriptor = new SuscriptorCorreo(mailSender);
	
	}

	@Test
	void testNotifyEstadoNuevoConfirmado() {

		estadoNuevo = mock(EstadoConfirmado.class);
		when(estadoNuevo.esEstadoConfirmado()).thenReturn(true);
		
		suscriptor.notify(reserva, estadoAnterior, estadoNuevo);
		
		verify(mailSender).enviarMail(
				  eq(reserva.getCliente().getDireccionDeEmail())
				, eq("Reserva confirmada")
				, eq("Se ha confirmado vuestra reserva")
				, eq(null));
		
	}

	@Test
	void testNotifyEstadoNuevoEnCurso() {

		estadoNuevo = mock(EstadoEnCurso.class);
		when(estadoNuevo.esEstadoEnCurso()).thenReturn(true);
		
		suscriptor.notify(reserva, estadoAnterior, estadoNuevo);
		
		verify(mailSender).enviarMail(
				  eq(reserva.getCliente().getDireccionDeEmail())
				, eq("Reserva en curso")
				, eq("Se ha ejecutado vuestra reserva")
				, eq(null));
		
	}

	@Test
	void testNotifyEstadoNuevoFinalizada() {

		estadoNuevo = mock(EstadoFinalizado.class);
		when(estadoNuevo.esEstadoFinalizado()).thenReturn(true);
		
		suscriptor.notify(reserva, estadoAnterior, estadoNuevo);
		
		verify(mailSender).enviarMail(
				  eq(reserva.getCliente().getDireccionDeEmail())
				, eq("Reserva finalizada")
				, eq("Se ha finalizado vuestra reserva")
				, eq(null));
		
	}

	@Test
	void testNotifyEstadoNuevoNiConfirmadoNiEnCursoNiFinalizado() {

		estadoNuevo = mock(EstadoCancelado.class);
		
		suscriptor.notify(reserva, estadoAnterior, estadoNuevo);
		
		verify(mailSender, never()).enviarMail(
				  any()
				, any()
				, any()
				, any());
		
	}

}
