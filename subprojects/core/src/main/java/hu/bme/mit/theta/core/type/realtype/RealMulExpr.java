/*
 *  Copyright 2017 Budapest University of Technology and Economics
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package hu.bme.mit.theta.core.type.realtype;

import hu.bme.mit.theta.core.model.Valuation;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.abstracttype.MulExpr;
import hu.bme.mit.theta.core.type.realtype.RealType;

import java.util.List;

import static com.google.common.collect.ImmutableList.toImmutableList;
import static hu.bme.mit.theta.core.type.realtype.RealExprs.Real;
import static hu.bme.mit.theta.core.utils.TypeUtils.cast;

public final class RealMulExpr extends MulExpr<RealType> {

	private static final int HASH_SEED = 2707;
	private static final String OPERATOR_LABEL = "*";

	private RealMulExpr(final Iterable<? extends Expr<RealType>> ops) {
		super(ops);
	}

	public static RealMulExpr of(final Iterable<? extends Expr<RealType>> ops) {
		return new RealMulExpr(ops);
	}

	public static RealMulExpr create(final List<? extends Expr<?>> ops) {
		return RealMulExpr.of(ops.stream().map(op -> cast(op, Real())).collect(toImmutableList()));
	}

	@Override
	public RealType getType() {
		return Real();
	}

	@Override
	public RealLitExpr eval(final Valuation val) {
		int prod = 1;
		for (final Expr<RealType> op : getOps()) {
			final RealLitExpr opVal = (RealLitExpr) op.eval(val);
			prod *= opVal.getValue();
		}
		return Real(prod);
	}

	@Override
	public RealMulExpr with(final Iterable<? extends Expr<RealType>> ops) {
		if (ops == getOps()) {
			return this;
		} else {
			return RealMulExpr.of(ops);
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof RealMulExpr) {
			final RealMulExpr that = (RealMulExpr) obj;
			return this.getOps().equals(that.getOps());
		} else {
			return false;
		}
	}

	@Override
	protected int getHashSeed() {
		return HASH_SEED;
	}

	@Override
	protected String getOperatorLabel() {
		return OPERATOR_LABEL;
	}

}
