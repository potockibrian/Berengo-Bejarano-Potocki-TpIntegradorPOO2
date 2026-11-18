package unq.travel.reportes;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import unq.travel.catalogo.Categoria;
import unq.travel.catalogo.ServicioIndividual;
import unq.travel.cupos.CalendarioDeCupos;
import unq.travel.cupos.RangoDeFechas;

public class RegistroDeVentasTest {
	private RegistroDeVentas registro;
	private ServicioIndividual servicio;
	private ServicioIndividual otroServicio;
	private RangoDeFechas periodo;
	
	@BeforeEach
	public void setUp() {
		registro = new RegistroDeVentas();
		
		servicio = new ServicioIndividual(
				"Hotel",
				"Habitacion doble",
				"RES001",
				"Proveedor A",
				Categoria.ALOJAMIENTO,
				1000,
				0,
				new CalendarioDeCupos(10));
		
		otroServicio = new ServicioIndividual(
				"Excursión",
			    "Excursión guiada",
			    "RES002",
			    "Proveedor B",
			    Categoria.EXCURSION,
			    2000f,
			    0f,
			    new CalendarioDeCupos(10));
		
		periodo = new RangoDeFechas(
				LocalDate.of(2026, 10, 10),
				LocalDate.of(2026, 10, 15));
	}
	
	@Test
	public void inicialmenteNoHayVentasRegistradas() {
		assertEquals(0, registro.cantidadDeVentasDe(servicio, periodo));
	}
	
	@Test
	public void registraUnaVentaDeUnServicio() {
		RegistroVenta venta = new RegistroVenta(
				servicio,
				LocalDate.of(2026, 10, 12),
				1000);
		
		registro.registrarVenta(venta);
		
		assertEquals(1, registro.cantidadDeVentasDe(servicio, periodo));
	}
	
	@Test
	public void devuelveLasVentasDelProductoDentroDelPeriodo() {
		RegistroVenta venta = new RegistroVenta(
				servicio,
				LocalDate.of(2026, 10, 12),
				1000);
		
		registro.registrarVenta(venta);
		
		List<RegistroVenta> ventas = registro.ventasDe(servicio, periodo);
		
		assertEquals(1, ventas.size());
		assertSame(venta, ventas.get(0));
	}
	
	@Test
    public void noIncluyeVentasAnterioresAlPeriodo() {
        registro.registrarVenta(new RegistroVenta(
            servicio,
            LocalDate.of(2026, 10, 9),
            1000));

        assertEquals(0, registro.cantidadDeVentasDe(servicio, periodo));
    }

    @Test
    public void noIncluyeVentasPosterioresAlPeriodo() {
        registro.registrarVenta(new RegistroVenta(
            servicio,
            LocalDate.of(2026, 10, 16),
            1000));

        assertEquals(0, registro.cantidadDeVentasDe(servicio, periodo));
    }

    @Test
    public void incluyeVentasRealizadasEnLosLimitesDelPeriodo() {
        registro.registrarVenta(new RegistroVenta(
            servicio,
            LocalDate.of(2026, 10, 10),
            1000));

        registro.registrarVenta(new RegistroVenta(
            servicio,
            LocalDate.of(2026, 10, 15),
            1000));

        assertEquals(2, registro.cantidadDeVentasDe(servicio, periodo));
    }

    @Test
    public void cuentaTodasLasVentasDelMismoProductoEnElPeriodo() {
        registro.registrarVenta(new RegistroVenta(
            servicio,
            LocalDate.of(2026, 10, 11),
            1000));

        registro.registrarVenta(new RegistroVenta(
            servicio,
            LocalDate.of(2026, 10, 12),
            900));

        registro.registrarVenta(new RegistroVenta(
            servicio,
            LocalDate.of(2026, 10, 13),
            950));

        assertEquals(3, registro.cantidadDeVentasDe(servicio, periodo));
    }

    @Test
    public void noCuentaLasVentasDeOtrosProductos() {
        registro.registrarVenta(new RegistroVenta(
            otroServicio,
            LocalDate.of(2026, 10, 12),
            2000));

        assertEquals(0, registro.cantidadDeVentasDe(servicio, periodo));
    }
    
    @Test
    public void calculaElImporteTotalDeLasVentasDeUnProductoEnUnPeriodo() {
        registro.registrarVenta(new RegistroVenta(
            servicio,
            LocalDate.of(2026, 10, 11),
            1000f
        ));

        registro.registrarVenta(new RegistroVenta(
            servicio,
            LocalDate.of(2026, 10, 12),
            900f
        ));

        registro.registrarVenta(new RegistroVenta(
            servicio,
            LocalDate.of(2026, 10, 13),
            950f
        ));

        assertEquals(2850f, registro.importeTotalDe(servicio, periodo), 0.01f);
    }
    
    @Test
    public void calculaElPrecioPromedioDeLasVentasDeUnProductoEnUnPeriodo() {
        registro.registrarVenta(new RegistroVenta(
            servicio,
            LocalDate.of(2026, 10, 11),
            1000f
        ));

        registro.registrarVenta(new RegistroVenta(
            servicio,
            LocalDate.of(2026, 10, 12),
            900f
        ));

        registro.registrarVenta(new RegistroVenta(
            servicio,
            LocalDate.of(2026, 10, 13),
            950f
        ));

        assertEquals(950f, registro.precioPromedioDe(servicio, periodo), 0.01f);
    }
    
    @Test
    public void devuelveCeroComoPrecioPromedioCuandoNoHayVentas() {
        assertEquals(0f, registro.precioPromedioDe(servicio, periodo), 0.01f);
    }
    
}
