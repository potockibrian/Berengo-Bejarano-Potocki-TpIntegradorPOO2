package unq.travel.reportes;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.ItemCatalogo;
import unq.travel.cupos.RangoDeFechas;

public class ReporteServiciosMasReservadosTest {

    private RangoDeFechas periodo;
    private RegistroDeVentas registroDeVentas;
    private ItemCatalogo servicio1;
    private ItemCatalogo servicio2;
    private ItemCatalogo paquete;
    private Map<ItemCatalogo, Integer> items;
    private ReporteServiciosMasReservados reporte;

    @BeforeEach
    void setUp() {
        periodo = new RangoDeFechas(
            LocalDate.of(2026, 10, 10),
            LocalDate.of(2026, 10, 15)
        );

        registroDeVentas = mock(RegistroDeVentas.class);

        servicio1 = mock(ItemCatalogo.class);
        servicio2 = mock(ItemCatalogo.class);
        paquete = mock(ItemCatalogo.class);

        items = new HashMap<>();
        items.put(servicio1, 3);
        items.put(servicio2, 7);
        items.put(paquete, 5);

        reporte = new ReporteServiciosMasReservados(
            periodo, items, registroDeVentas
        );
    }
    
    @Test
    void ordenaLosItemsDeMayorAMenorCantidadDeVentas() {
        var itemsOrdenados = reporte.getItemsOrdenados();

        assertEquals(3, itemsOrdenados.size());

        assertSame(servicio2, itemsOrdenados.get(0).getKey());
        assertEquals(7, itemsOrdenados.get(0).getValue());

        assertSame(paquete, itemsOrdenados.get(1).getKey());
        assertEquals(5, itemsOrdenados.get(1).getValue());

        assertSame(servicio1, itemsOrdenados.get(2).getKey());
        assertEquals(3, itemsOrdenados.get(2).getValue());
    }
    
    @Test
    void devuelveElPrecioPromedioEfectivamenteCobrado() {
        when(registroDeVentas.precioPromedioDe(servicio1, periodo))
            .thenReturn(950f);

        assertEquals(
            950f,
            reporte.getPrecioPromedioCobrado(servicio1),
            0.01f
        );

        verify(registroDeVentas).precioPromedioDe(servicio1, periodo);
    }
    
    @Test
    void devuelveCeroCuandoElProductoNoTieneVentas() {
        when(registroDeVentas.precioPromedioDe(servicio1, periodo))
            .thenReturn(0f);

        assertEquals(
            0f,
            reporte.getPrecioPromedioCobrado(servicio1),
            0.01f
        );
    }
    
    @Test
    void noSeModificaCuandoCambiaElMapOriginal() {
        items.clear();

        assertEquals(3, reporte.getItemsOrdenados().size());
    }
}