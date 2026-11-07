package unq.travel.cicloReserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.traslado.Reserva;
import unq.travel.traslado.Voucher;

class EstadoEnCheckinTest {
	
	private Reserva reserva;
	private EstadoDeReserva estado;
	private ItemCatalogo item;
	private List<ItemCatalogo> items;
	private MailSender mailSender;
	private Cliente cliente;
	
	@BeforeEach
	void setUp() {
		
		mailSender = mock(MailSender.class);
		items = mock(List.class);
		item = mock(ItemCatalogo.class);
		reserva = mock(Reserva.class);
		cliente = mock(Cliente.class);
		
		estado = new EstadoEnCheckin(reserva);
		
	}
	
	@Test
	void testAgregarItem() {

		assertThrows(OperacionInvalidaEstadoException.class, () -> estado.agregarItem(item));
		
		verify(items, never()).add(any(ItemCatalogo.class));
		
	}
	
	@Test
	void testQuitarItem() {

		assertThrows(OperacionInvalidaEstadoException.class, () -> estado.quitarItem(item));
		
		verify(items, never()).remove(any(ItemCatalogo.class));
		
	}
	
	@Test
	void testConfirmar() {

		assertThrows(OperacionInvalidaEstadoException.class, () -> estado.confirmar());
		
	}
	
	@Test
	void testCheckin() {

		assertThrows(OperacionInvalidaEstadoException.class, () -> estado.checkin());
		
	}
	
	@Test
	void testEjecutar() {
		
		estado.ejecutar();
		
		verify(reserva).doEjecutar();
		
	}
	
	@Test
	void testCompletar() {

		assertThrows(OperacionInvalidaEstadoException.class, () -> estado.completar());
		
	}
	
	@Test
	void testCancelar() {
		
		estado.cancelar();
		
		reserva.doCancelarFromCheckin();
		
	}	
	
	@Test
	void testEstadoTerminal() {
		
		assertFalse(estado.esEstadoTerminal());
		
	}
	
	@Test
	void testEstadoCancelado() {
		
		assertFalse(estado.esEstadoCancelado());
		
	}
	
	@Test
	void testEstadoFinalizado() {
		
		assertFalse(estado.esEstadoFinalizado());
		
	}
	
	@Test
	void testEstadoEnCurso() {
		
		assertFalse(estado.esEstadoEnCurso());
		
	}
	
	@Test
	void testEstadoConfirmado() {
		
		assertFalse(estado.esEstadoConfirmado());
		
	}
	
}