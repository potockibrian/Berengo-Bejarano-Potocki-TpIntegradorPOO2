package unq.travel.notificaciones;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.cicloReserva.Cliente;
import unq.travel.cicloReserva.Comprobante;
import unq.travel.cicloReserva.EstadoCancelado;
import unq.travel.cicloReserva.EstadoDeReserva;
import unq.travel.cicloReserva.EstadoFinalizado;
import unq.travel.traslado.Reserva;
import unq.travel.traslado.Voucher;

class SuscriptorFacturadorTest {

	private EstadoDeReserva estadoAnterior;
	private EstadoDeReserva estadoNuevo;
	private Reserva reserva;
	private Suscriptor suscriptor;
	private Cliente cliente;
	
	@BeforeEach
	void setUp() throws Exception {
	
		cliente = mock(Cliente.class);
		estadoAnterior = mock(EstadoDeReserva.class);
		reserva = mock(Reserva.class);
		when(reserva.getCliente()).thenReturn(cliente);
		
		suscriptor = new SuscriptorFacturador();
	
	}

	@Test
	void testNotifyEstadoNuevoFinalizado() {
		
		estadoNuevo = mock(EstadoFinalizado.class);
		when(estadoNuevo.esEstadoFinalizado()).thenReturn(true);
		
		suscriptor.notify(reserva, estadoAnterior, estadoNuevo);
		
		verify(cliente).añadirComprobante(any(Comprobante.class));
		verify(cliente).añadirVoucher(any(Voucher.class));
		
		
	}

	@Test
	void testNotifyEstadoNuevoNoFinalizado() {
		
		estadoNuevo = mock(EstadoCancelado.class);
		
		suscriptor.notify(reserva, estadoAnterior, estadoNuevo);
		
		verify(cliente, never()).añadirComprobante(any(Comprobante.class));
		verify(cliente, never()).añadirVoucher(any(Voucher.class));
		
		
	}

}