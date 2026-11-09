package unq.travel.metodosDePago;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyFloat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class BilleteraVirtualTest {

	private BilleteraVirtual metodo;
	private BilleteraVirtualAPI api;
	
	@BeforeEach
	void setUp() {
		
		api = mock(BilleteraVirtualAPI.class);
		metodo = new BilleteraVirtual(api);
		
	}

    @Test
    void testPagoSinSaldoSuficienteLanzaExcepcion() {
    	
    	when(api.validarSaldoSuficiente(anyFloat()))
    		.thenReturn(false);
        
        assertThrows(
        		PagoInvalidoException.class, 
        		() -> metodo.pagar(3000f));
        
        verify(api).validarSaldoSuficiente(anyFloat());
        verify(api, never()).bloquearSaldo();
        verify(api, never()).acreditarAlVendedor();
        verify(api, never()).notificarPush(any());
    }
	
    @Test
    void testPagoConSaldoSuficienteBloqueaAcreditaYNotifica() {
    	when(api.validarSaldoSuficiente(anyFloat()))
    		.thenReturn(true);
    	when(api.acreditarAlVendedor()).thenReturn("OP-789");
    	
    	metodo.pagar(3000f);
    	
    	verify(api).validarSaldoSuficiente(anyFloat());
    	verify(api).bloquearSaldo();
    	verify(api).acreditarAlVendedor();
    	verify(api).notificarPush("OP-789");
    	
    	assertEquals("OP-789", metodo.getCodigoTransaccion());
    }
}