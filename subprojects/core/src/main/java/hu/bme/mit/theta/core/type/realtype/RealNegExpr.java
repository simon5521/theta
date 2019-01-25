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
import hu.bme.mit.theta.core.type.abstracttype.NegExpr;
import hu.bme.mit.theta.core.type.realtype.RealType;

import static hu.bme.mit.theta.core.type.realtype.RealExprs.Real;
import static hu.bme.mit.theta.core.utils.TypeUtils.cast;

public final class RealNegExpr extends NegExpr<RealType> {

	private static final int HASH_SEED = 3359;
	private static final String OPERATOR_LABEL = "-";

	private RealNegExpr(final Expr<RealType> op) {
		super(op);
	}

	public static RealNegExpr of(final Expr<RealType> op) {
		return new RealNegExpr(op);
	}

	public static RealNegExpr create(final Expr<?> op) {
		final Expr<RealType> newOp = cast(op, Real());
		return RealNegExpr.of(newOp);
	}

	@Override
	public RealType getType() {
		return Real();
	}

	@Override
	public RealLitExpr eval(final Valuation val) {
		final RealLitExpr opVal = (RealLitExpr) getOp().eval(val);
		return opVal.neg();
	}

	@Override
	public RealNegExpr with(final Expr<RealType> op) {
		if (op == getOp()) {
			return this;
		} else {
			return RealNegExpr.of(op);
		}
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof RealNegExpr) {
			final RealNegExpr that = (RealNegExpr) obj;
			return this.getOp().equals(that.getOp());
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
