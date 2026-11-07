package unq.travel.traslado;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VoucherTest {

	private Voucher voucher;
	
	@BeforeEach
	void setUp() throws Exception {

		voucher = new Voucher(500f);
		
	}
	
	@Test
	void test() {
		
		assertEquals(500f, voucher.getMonto());
		
	}

}
