package unq.travel.cicloReserva;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.traslado.Reserva;
import unq.travel.traslado.Voucher;

class EstadoCanceladoTest {
	
	private Reserva reserva;
	private EstadoDeReserva estado;
	private ItemCatalogo item;
	private List<ItemCatalogo> items;
	private MailSender mailSender;
	private Cliente cliente;
	
	@BeforeEach
	void setUp() {
		
		cliente = mock(Cliente.class);
		mailSender = mock(MailSender.class);
		items = mock(List.class);
		item = mock(ItemCatalogo.class);
		reserva = mock(Reserva.class);
		
		estado = new EstadoCancelado(reserva);
		
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

		assertThrows(OperacionInvalidaEstadoException.class, () -> estado.ejecutar());
		
	}
	
	@Test
	void testCompletar() {

		assertThrows(OperacionInvalidaEstadoException.class, () -> estado.completar());
		
	}
	
	@Test
	void testCancelar() {

		assertThrows(OperacionInvalidaEstadoException.class, () -> estado.cancelar());
		
	}	
	
	@Test
	void testEstadoTerminal() {
		
		assertTrue(estado.esEstadoTerminal());
		
	}
	
	@Test
	void testEstadoCancelado() {
		
		assertTrue(estado.esEstadoCancelado());
		
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