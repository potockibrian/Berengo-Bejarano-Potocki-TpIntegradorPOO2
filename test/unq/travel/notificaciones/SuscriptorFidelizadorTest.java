package unq.travel.notificaciones;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.cicloReserva.Cliente;
import unq.travel.cicloReserva.Cupon;
import unq.travel.cicloReserva.EstadoCancelado;
import unq.travel.cicloReserva.EstadoConfirmado;
import unq.travel.cicloReserva.EstadoDeReserva;
import unq.travel.cicloReserva.EstadoFinalizado;
import unq.travel.cicloReserva.EstadoPendiente;
import unq.travel.cicloReserva.MailSender;
import unq.travel.traslado.Reserva;

class SuscriptorFidelizadorTest {

	private EstadoDeReserva estado;
	private Reserva reserva;
	private MailSender mailSender;
	private Suscriptor suscriptor;
	private Cliente cliente;
	
	@BeforeEach
	void setUp() throws Exception {
	
		cliente = mock(Cliente.class);
		estado = mock(EstadoDeReserva.class);
		reserva = mock(Reserva.class);
		mailSender = mock(MailSender.class);
		when(reserva.getCliente()).thenReturn(cliente);
		
		suscriptor = new SuscriptorFidelizador(mailSender);
	
	}

	@Test
	void testNotifySinEstadoAnteriorTerminalYConEstadoActualCancelado() {

		EstadoDeReserva estadoAnterior = mock(EstadoConfirmado.class);
		EstadoDeReserva nuevoEstado = mock(EstadoCancelado.class);
		
		suscriptor.notify(reserva, estadoAnterior, nuevoEstado);
		
		verify(mailSender).enviarMail(
				  eq(reserva.getCliente().getDireccionDeEmail())
				, eq("Reserva cancelada")
				, eq("Se ha cancelado vuestra reserva")
				, any(Cupon.class));
		
	}
	
	@Test
	void testNotifyConEstadoAnteriorTerminal() {

		EstadoDeReserva estadoAnterior = mock(EstadoFinalizado.class);
		EstadoDeReserva nuevoEstado = mock(EstadoCancelado.class);
		
		suscriptor.notify(reserva, estadoAnterior, nuevoEstado);
		
		verify(mailSender, never()).enviarMail(
				  any()
				, any()
				, any()
				, any());
		
	}
	
	@Test
	void testNotifySinEstadoAnteriorTerminalYConEstadoNuevoNoCancelado() {

		EstadoDeReserva estadoAnterior = mock(EstadoConfirmado.class);
		EstadoDeReserva nuevoEstado = mock(EstadoPendiente.class);
		
		suscriptor.notify(reserva, estadoAnterior, nuevoEstado);
		
		verify(mailSender, never()).enviarMail(
				  any()
				, any()
				, any()
				, any());
		
	}
	
}
