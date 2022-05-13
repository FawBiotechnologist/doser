package edu.iis.mto.testreactor.doser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.iis.mto.testreactor.doser.infuser.Infuser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.TimeUnit;

@ExtendWith(MockitoExtension.class)
class MedicineDoserTest {
	@Mock
	private Infuser infuserMock;
	@Mock
	private DosageLog dosageLogMock;
	@Mock
	private Clock clockMock;
	private MedicineDoser medicineDoserToTest;
	private Capacity defaultCapacity = Capacity.of(100, CapacityUnit.MILILITER);
	private Capacity tenthOfDefaultCapacity = Capacity.of(10, CapacityUnit.MILILITER);
	private Dose defaultDose = Dose.of(tenthOfDefaultCapacity, Period.of(1, TimeUnit.HOURS));
	private Medicine defaultMedicine = Medicine.of("default medicine");
	private MedicinePackage defaultMedicinePackage = MedicinePackage.of(defaultMedicine, defaultCapacity);
	private Receipe defaultReceipe = Receipe.of(defaultMedicine, defaultDose, 3);

	@BeforeEach
	void setUp() {
		medicineDoserToTest = new MedicineDoser(infuserMock, dosageLogMock, clockMock);
	}

	@Test
	void itCompiles() {
		assertEquals(2, 1 + 1);
	}

	@Test
	void successfulRunWithNoErrors() {
		medicineDoserToTest.add(defaultMedicinePackage);
		medicineDoserToTest.dose(defaultReceipe);
		assertEquals(2, 1 + 1);
	}


}
