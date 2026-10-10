package unq.travel.metodosDePago;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class BilleteraVirtualTest {

	private MetodoDePago metodo;
	private BilleteraVirtualAPI api;
	
	@BeforeEach
	void setUp() {
		
		api = mock(BilleteraVirtualAPI.class);
		metodo = new BilleteraVirtual(api);
		
	}

    @Test
    void testPagoSinSaldoSuficienteLanzaExcepcion() {
    	
    	when(api.validarSaldoSuficiente(any())).thenReturn(false);
        
        assertThrows(RuntimeException.class, () -> metodo.pagar(3000f));
        
        verify(api).validarSaldoSuficiente(any());
    
    }
	
    @Test
    void testPagoCuandoHaySaldoValidaLosDatosYSeBloqueaElSaldo() {
    	
    	when(api.validarSaldoSuficiente(any())).thenReturn(true);
    	
        metodo.pagar(3000f);
        
        verify(api).validarSaldoSuficiente(any());
        verify(api).bloquearSaldo();
        verify(api, never()).desbloquearSaldo();
    
    }
    
	@Test
	void testPagoCuandoHaySaldoYSeConfirmaSeDesbloqueaElSaldoSeAcreditaAlVendedorYSeNotifica() {
		
		when(api.validarSaldoSuficiente(any())).thenReturn(true);
		
		metodo.pagar(3000f);
		metodo.confirmar();
		
		verify(api).desbloquearSaldo();
		verify(api).acreditarAlVendedor();
		verify(api).notificar();
		
	}

}