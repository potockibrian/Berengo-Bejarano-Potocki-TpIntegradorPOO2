package unq.travel.reportes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.Catalogo;
import unq.travel.catalogo.Categoria;
import unq.travel.catalogo.ItemCatalogo;
import unq.travel.cupos.Cupo;
import unq.travel.cupos.RangoDeFechas;

class ReporteOcupacionYCupoDisponibleTest {

	private LocalDate fecha1 = LocalDate.of(2026, 1, 1);;
	private LocalDate fecha2 = LocalDate.of(2026, 1, 2);;
	private LocalDate fecha3 = LocalDate.of(2026, 1, 3);;
	private LocalDate fecha4 = LocalDate.of(2026, 1, 4);
	
	private ReporteOcupacionYCupoDisponible reporte;
	private ItemCatalogo item1;
	private ItemCatalogo item2;
	private RangoDeFechas periodo1 = new RangoDeFechas(fecha2, fecha3);
	private RangoDeFechas periodo2 = new RangoDeFechas(fecha1, fecha4);
	private Catalogo catalogo;
	private Cupo cupo1;
	private Cupo cupo2;
	private Cupo cupoIdentidad;
	private Exportador exportador;
	
	@BeforeEach
	void setUp() throws Exception {
		
		exportador = mock(Exportador.class);
		
		cupo1 = new Cupo(5, 1);
		cupo2 = new Cupo(10, 5);
		cupoIdentidad = new Cupo(0);
		catalogo = mock(Catalogo.class);
		item1 = mock(ItemCatalogo.class);
		item2 = mock(ItemCatalogo.class);
		
		when(item1.categorias()).thenReturn(Set.of(Categoria.ALOJAMIENTO));
		when(item2.categorias()).thenReturn(Set.of(Categoria.ALOJAMIENTO, Categoria.TRASLADO));
		
		when(item1.cuposEnPeriodoDeCategoria(periodo1, Categoria.ALOJAMIENTO)).thenReturn(cupo1);
		when(item1.cuposEnPeriodoDeCategoria(periodo1, Categoria.TRASLADO)).thenReturn(cupoIdentidad);
		
		when(item2.cuposEnPeriodoDeCategoria(periodo1, Categoria.ALOJAMIENTO)).thenReturn(cupo1);
		when(item2.cuposEnPeriodoDeCategoria(periodo1, Categoria.TRASLADO)).thenReturn(cupo2);
		
		when(catalogo.getItems()).thenReturn(List.of(item1, item2));
		
		reporte = new ReporteOcupacionYCupoDisponible(catalogo, periodo1);
		
	}

	@Test
	void testPeriodo() {

		assertEquals(periodo1, reporte.periodo());
		
	}

	@Test
	void testCuposPorCategoria() {

		List<Map.Entry<Categoria, Cupo>> cupos = reporte.getCuposPorCategoria();
		
		assertEquals(2, cupos.size());
		
		assertEquals(Categoria.ALOJAMIENTO, cupos.getLast().getKey());
		assertEquals(Categoria.TRASLADO, cupos.getFirst().getKey());
		
		assertEquals(8, cupos.getLast().getValue().cantidadDeCuposDisponibles());
		assertEquals(2, cupos.getLast().getValue().cantidadDeVentas());
		assertEquals(10, cupos.getLast().getValue().getTopeMaximo());
		
		assertEquals(5, cupos.getFirst().getValue().cantidadDeCuposDisponibles());
		assertEquals(5, cupos.getFirst().getValue().cantidadDeVentas());
		assertEquals(10, cupos.getFirst().getValue().getTopeMaximo());
		
	}
	
	@Test
	void testExportar() {
		
		when(exportador.exportar(reporte)).thenReturn("Exportado");
		
		assertEquals("Exportado", reporte.exportar(exportador));
		
	}

}
