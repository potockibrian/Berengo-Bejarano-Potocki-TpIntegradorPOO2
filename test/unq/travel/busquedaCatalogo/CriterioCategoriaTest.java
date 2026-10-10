package unq.travel.busquedaCatalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.Categoria;
import unq.travel.catalogo.ItemCatalogo;

class CriterioCategoriaTest {

	private ItemCatalogo item;
	private Criterio criterio;

	@BeforeEach
	void setUp() {
		item = mock(ItemCatalogo.class);
		criterio = new CriterioCategoria(Categoria.ALOJAMIENTO);
	}

	@Test
	void testCumpleCuandoElItemEsDeLaCategoria() {

		when(item.esDeCategoria(Categoria.ALOJAMIENTO)).thenReturn(true);

		assertTrue(criterio.cumpleConCriterio(item));

	}

	@Test
	void testNoCumpleCuandoElItemNoEsDeLaCategoria() {

		when(item.esDeCategoria(Categoria.ALOJAMIENTO)).thenReturn(false);

		assertFalse(criterio.cumpleConCriterio(item));

	}

}
