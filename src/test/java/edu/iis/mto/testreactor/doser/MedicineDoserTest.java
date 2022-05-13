package edu.iis.mto.testreactor.doser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
		DosingResult dosingResult = medicineDoserToTest.dose(defaultReceipe);
		assertEquals(DosingResult.SUCCESS, dosingResult);
	}

	@Test
	void incorrectRecipeWrongNumberExpectingIllegalArgumentException() {
		medicineDoserToTest.add(defaultMedicinePackage);
		Receipe incorrectReceipeWrongNumber = Receipe.of(defaultMedicine, defaultDose, 0);
		assertThrows(IllegalArgumentException.class, () -> medicineDoserToTest.dose(incorrectReceipeWrongNumber));
	}

	@Test
	void incorrectRecipeWrongDoseExpectingInsufficient() {
		medicineDoserToTest.add(defaultMedicinePackage);
		Receipe incorrectReceipeNotEnoughMedicine = Receipe.of(defaultMedicine, defaultDose, 11);
		assertThrows(InsufficientMedicineException.class, () -> medicineDoserToTest.dose(incorrectReceipeNotEnoughMedicine));
	}

	@Test
	void incorrectRecipeWrongMedicineExpectingUnavailableMedicineException() {
		medicineDoserToTest.add(defaultMedicinePackage);
		Receipe incorrectReceipeUnavailableMedicine = Receipe.of(Medicine.of("UNAVAILABLE_MEDICINE"), defaultDose, 1);
		assertThrows(UnavailableMedicineException.class, () -> medicineDoserToTest.dose(incorrectReceipeUnavailableMedicine));
	}



}
