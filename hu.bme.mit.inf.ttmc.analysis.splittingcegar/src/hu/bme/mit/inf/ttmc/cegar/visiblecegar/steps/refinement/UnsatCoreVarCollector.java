package hu.bme.mit.inf.ttmc.cegar.visiblecegar.steps.refinement;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hu.bme.mit.inf.ttmc.cegar.common.data.ConcreteTrace;
import hu.bme.mit.inf.ttmc.cegar.common.data.SolverWrapper;
import hu.bme.mit.inf.ttmc.cegar.common.data.StopHandler;
import hu.bme.mit.inf.ttmc.cegar.common.steps.AbstractCEGARStep;
import hu.bme.mit.inf.ttmc.cegar.common.utils.visualization.Visualizer;
import hu.bme.mit.inf.ttmc.cegar.visiblecegar.data.VisibleAbstractState;
import hu.bme.mit.inf.ttmc.cegar.visiblecegar.data.VisibleAbstractSystem;
import hu.bme.mit.inf.ttmc.common.logging.Logger;
import hu.bme.mit.inf.ttmc.core.expr.Expr;
import hu.bme.mit.inf.ttmc.core.type.BoolType;
import hu.bme.mit.inf.ttmc.core.type.Type;
import hu.bme.mit.inf.ttmc.formalism.common.decl.VarDecl;
import hu.bme.mit.inf.ttmc.formalism.sts.STS;
import hu.bme.mit.inf.ttmc.formalism.utils.impl.FormalismUtils;
import hu.bme.mit.inf.ttmc.solver.Solver;
import hu.bme.mit.inf.ttmc.solver.SolverStatus;

public class UnsatCoreVarCollector extends AbstractCEGARStep implements VarCollector {

	public UnsatCoreVarCollector(final SolverWrapper solvers, final StopHandler stopHandler, final Logger logger, final Visualizer visualizer) {
		super(solvers, stopHandler, logger, visualizer);
	}

	@Override
	public Collection<VarDecl<? extends Type>> collectVars(final VisibleAbstractSystem system, final List<VisibleAbstractState> abstractCounterEx,
			final ConcreteTrace concreteCounterEx) {

		final int traceLength = concreteCounterEx.size();
		assert (traceLength < abstractCounterEx.size());

		final STS sts = system.getSTS();
		final Solver solver = solvers.getSolver();

		solver.push();
		solver.track(sts.unrollInit(0));
		for (int i = 0; i < traceLength + 1; ++i) {
			// TODO: if the expression is an AND, are the operands added separately?
			solver.track(sts.unroll(abstractCounterEx.get(i).getExpression().toExpr(), i));

			if (i > 0)
				solver.track(sts.unrollTrans(i - 1));
			solver.track(sts.unrollInv(i));
		}

		solver.check();

		assert (solver.getStatus() == SolverStatus.UNSAT);

		final Set<VarDecl<? extends Type>> vars = new HashSet<>();

		for (final Expr<? extends BoolType> uc : solver.getUnsatCore())
			FormalismUtils.collectVars(sts.foldin(uc, 0), vars);

		solver.pop();

		return vars;
	}

}
