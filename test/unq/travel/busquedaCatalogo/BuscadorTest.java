package unq.travel.busquedaCatalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.Catalogo;
import unq.travel.catalogo.ItemCatalogo;

class BuscadorTest {

	private ItemCatalogo hotel;
	private ItemCatalogo museo;
	private ItemCatalogo traslado;
	private Catalogo catalogo;
	private Criterio criterio;
	private Buscador buscador;

	@BeforeEach
	void setUp() {
		hotel = mock(ItemCatalogo.class);
		museo = mock(ItemCatalogo.class);
		traslado = mock(ItemCatalogo.class);
		criterio = mock(Criterio.class);

		catalogo = mock(Catalogo.class);
		when(catalogo.getItems()).thenReturn(List.of(hotel, museo, traslado));

		buscador = new Buscador(catalogo);
	}

	@Test
	void testDevuelveSoloLosItemsQueCumplenElCriterio() {

		when(criterio.cumpleConCriterio(hotel)).thenReturn(true);
		when(criterio.cumpleConCriterio(traslado)).thenReturn(true);

		assertEquals(List.of(hotel, traslado), buscador.buscar(criterio));

	}

	@Test
	void testDevuelveListaVaciaCuandoNingunItemCumpleElCriterio() {

		assertTrue(buscador.buscar(criterio).isEmpty());

	}

	@Test
	void testDevuelveTodosLosItemsCuandoTodosCumplenElCriterio() {

		when(criterio.cumpleConCriterio(hotel)).thenReturn(true);
		when(criterio.cumpleConCriterio(museo)).thenReturn(true);
		when(criterio.cumpleConCriterio(traslado)).thenReturn(true);

		assertEquals(List.of(hotel, museo, traslado), buscador.buscar(criterio));

	}

	@Test
	void testDevuelveListaVaciaCuandoElCatalogoNoTieneItems() {

		when(catalogo.getItems()).thenReturn(List.of());

		assertTrue(buscador.buscar(criterio).isEmpty());

	}

}
