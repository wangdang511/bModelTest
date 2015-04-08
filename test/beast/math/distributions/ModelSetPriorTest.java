package beast.math.distributions;

import org.junit.Test;

import beast.core.parameter.IntegerParameter;
import beast.evolution.substitutionmodel.NucleotideRevJumpSubstModel;
import beast.evolution.substitutionmodel.NucleotideRevJumpSubstModelTest;
import junit.framework.TestCase;

public class ModelSetPriorTest extends TestCase {
	
	@Test
	public void testUniformModelSetPrior() throws Exception {
		ModelSetPrior prior = new ModelSetPrior();
		NucleotideRevJumpSubstModel sm = NucleotideRevJumpSubstModelTest.getSubstModel();
		prior.initByName("x", sm.modelIndicatorInput.get(), 
				"substModel", sm);
		
		for (int i = 0; i < sm.getModelCount(); i++) {
			sm.modelIndicatorInput.get().assignFromWithoutID(new IntegerParameter(i +""));
			assertEquals(-Math.log(31), prior.calculateLogP(), 1e-13);
		}
	}

	@Test
	public void testUniformParameterCountPrior() throws Exception {
		ModelSetPrior prior = new ModelSetPrior();
		NucleotideRevJumpSubstModel sm = NucleotideRevJumpSubstModelTest.getSubstModel();
		prior.initByName("x", sm.modelIndicatorInput.get(), 
				"substModel", sm,
				"priorType", "uniformOnParameterCount");
		
		// JC69
		sm.modelIndicatorInput.get().assignFromWithoutID(new IntegerParameter(0 +""));
		assertEquals(Math.log(1.0/6.0), prior.calculateLogP(), 1e-13);
			
		// HKY
		sm.modelIndicatorInput.get().assignFromWithoutID(new IntegerParameter(1 +""));
		assertEquals(Math.log(1.0/6.0), prior.calculateLogP(), 1e-13);

		// 3 parameter TN93, etc
		sm.modelIndicatorInput.get().assignFromWithoutID(new IntegerParameter(2 +""));
		assertEquals(Math.log(1.0/6.0/8.0), prior.calculateLogP(), 1e-13);

		// 4 parameter
		sm.modelIndicatorInput.get().assignFromWithoutID(new IntegerParameter(15 +""));
		assertEquals(Math.log(1.0/6.0/13.0), prior.calculateLogP(), 1e-13);

		// 5 parameter
		sm.modelIndicatorInput.get().assignFromWithoutID(new IntegerParameter(29 +""));
		assertEquals(Math.log(1.0/6.0/7.0), prior.calculateLogP(), 1e-13);

		// GTR
		sm.modelIndicatorInput.get().assignFromWithoutID(new IntegerParameter(30 +""));
		assertEquals(Math.log(1.0/6.0), prior.calculateLogP(), 1e-13);
	}

}
