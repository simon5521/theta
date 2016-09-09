package hu.bme.mit.inf.theta.analysis.splittingcegar.ui.performance;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import hu.bme.mit.inf.theta.analysis.splittingcegar.ui.formatters.Formatter;
import hu.bme.mit.inf.theta.analysis.splittingcegar.ui.formatters.impl.ExcelFormatter;
import hu.bme.mit.inf.theta.cegar.common.CEGARBuilder;
import hu.bme.mit.inf.theta.cegar.interpolatingcegar.InterpolatingCEGARBuilder;
import hu.bme.mit.inf.theta.cegar.interpolatingcegar.InterpolatingCEGARBuilder.InterpolationMethod;

public class PerfTestFischer extends PerfTestBase {

	private static final int TIMEOUT = 5 * 60 * 1000;
	private static final Formatter FORMATTER = new ExcelFormatter();

	@SuppressWarnings("serial")
	@Test
	@Ignore
	public void testFischer() {
		final IModelLoader loader = new SystemFileModelLoader();

		final List<TestCase> testCases = new ArrayList<TestCase>() {
			{
				add(new TestCase("src/test/resources/models/fischer/fischer2.system", true, loader));
				add(new TestCase("src/test/resources/models/fischer/fischer2_bad.system", false, loader));
				add(new TestCase("src/test/resources/models/fischer/fischer3_bool.system", true, loader));
				add(new TestCase("src/test/resources/models/fischer/fischer3_bool_bad.system", false, loader));
			}
		};
		final List<CEGARBuilder> configurations = new ArrayList<CEGARBuilder>() {
			{
				add(new InterpolatingCEGARBuilder().logger(null).visualizer(null)
						.interpolationMethod(InterpolationMethod.Craig).incrementalModelChecking(true)
						.useCNFTransformation(false));
				add(new InterpolatingCEGARBuilder().logger(null).visualizer(null)
						.interpolationMethod(InterpolationMethod.Sequence).incrementalModelChecking(true)
						.useCNFTransformation(false));
			}
		};

		run(testCases, configurations, TIMEOUT, FORMATTER);
	}

}