package unq.travel.busquedaCatalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.ItemCatalogo;

class CriterioDisponibilidadTest {

	private ItemCatalogo item;
	private Criterio criterio;

	@BeforeEach
	void setUp() {
		item = mock(ItemCatalogo.class);
		criterio = new CriterioDisponibilidad();
	}

	@Test
	void testCumpleCuandoElItemTieneDisponibilidad() {

		when(item.tieneDisponibilidad()).thenReturn(true);

		assertTrue(criterio.cumpleConCriterio(item));

	}

	@Test
	void testNoCumpleCuandoElItemNoTieneDisponibilidad() {

		when(item.tieneDisponibilidad()).thenReturn(false);

		assertFalse(criterio.cumpleConCriterio(item));

	}

}
