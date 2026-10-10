package unq.travel.busquedaCatalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.ItemCatalogo;

class CriterioPrecioMaxTest {

	private ItemCatalogo item;

	@BeforeEach
	void setUp() {
		item = mock(ItemCatalogo.class);
		when(item.getPrecioBase()).thenReturn(15000f);
	}

	@Test
	void testCumpleCuandoElPrecioEsMenorAlMaximo() {

		Criterio criterio = new CriterioPrecioMax(20000f);

		assertTrue(criterio.cumpleConCriterio(item));

	}

	@Test
	void testCumpleCuandoElPrecioEsIgualAlMaximo() {

		Criterio criterio = new CriterioPrecioMax(15000f);

		assertTrue(criterio.cumpleConCriterio(item));

	}

	@Test
	void testNoCumpleCuandoElPrecioEsMayorAlMaximo() {

		Criterio criterio = new CriterioPrecioMax(10000f);

		assertFalse(criterio.cumpleConCriterio(item));

	}

}
