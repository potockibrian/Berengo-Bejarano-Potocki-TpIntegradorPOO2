package unq.travel.catalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CatalogoTest {

	private Catalogo catalogo;
	private ItemCatalogo hotel;
	private ItemCatalogo museo;

	@BeforeEach
	void setUp() {
		catalogo = new Catalogo();
		hotel = mock(ItemCatalogo.class);
		museo = mock(ItemCatalogo.class);
	}

	@Test
	void testUnCatalogoNuevoNoTieneItems() {

		assertTrue(catalogo.getItems().isEmpty());

	}

	@Test
	void testAlAgregarUnItemElCatalogoLoContiene() {

		catalogo.agregarItem(hotel);

		assertEquals(List.of(hotel), catalogo.getItems());

	}

	@Test
	void testAlAgregarDosItemsLosContieneEnElOrdenAgregado() {

		catalogo.agregarItem(hotel);
		catalogo.agregarItem(museo);

		assertEquals(List.of(hotel, museo), catalogo.getItems());

	}

	@Test
	void testAlEliminarUnItemDejaDeContenerloYConservaLosDemas() {

		catalogo.agregarItem(hotel);
		catalogo.agregarItem(museo);

		catalogo.eliminarItem(hotel);

		assertEquals(List.of(museo), catalogo.getItems());

	}

	@Test
	void testEliminarUnItemQueNoEstaEnElCatalogoLanzaExcepcion() {

		catalogo.agregarItem(hotel);

		assertThrows(ItemInexistenteException.class, () -> catalogo.eliminarItem(museo));

	}

	@Test
	void testModificarLaListaDeItemsDevueltaNoModificaElCatalogo() {

		catalogo.agregarItem(hotel);

		catalogo.getItems().clear();

		assertEquals(List.of(hotel), catalogo.getItems());

	}

}
