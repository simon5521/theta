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
import hu.bme.mit.theta.core.type.abstracttype.SubExpr;
import hu.bme.mit.theta.core.type.realtype.RealType;

import static hu.bme.mit.theta.core.type.realtype.RealExprs.Real;
import static hu.bme.mit.theta.core.utils.TypeUtils.cast;

public final class RealSubExpr extends SubExpr<RealType> {

	private static final int HASH_SEED = 4547;
	private static final String OPERATOR = "-";

	private RealSubExpr(final Expr<RealType> leftOp, final Expr<RealType> rightOp) {
		super(leftOp, rightOp);
	}

	public static RealSubExpr of(final Expr<RealType> leftOp, final Expr<RealType> rightOp) {
		return new RealSubExpr(leftOp, rightOp);
	}

	public static RealSubExpr create(final Expr<?> leftOp, final Expr<?> rightOp) {
		final Expr<RealType> newLeftOp = cast(leftOp, Real());
		final Expr<RealType> newRightOp = cast(rightOp, Real());
		return RealSubExpr.of(newLeftOp, newRightOp);
	}

	@Override
	public RealType getType() {
		return Real();
	}

	@Override
	public RealLitExpr eval(final Valuation val) {
		final RealLitExpr leftOpVal = (RealLitExpr) getLeftOp().eval(val);
		final RealLitExpr rightOpVal = (RealLitExpr) getRightOp().eval(val);
		return leftOpVal.sub(rightOpVal);
	}

	@Override
	public SubExpr<RealType> with(final Expr<RealType> leftOp, final Expr<RealType> rightOp) {
		if (leftOp == getLeftOp() && rightOp == getRightOp()) {
			return this;
		} else {
			return RealSubExpr.of(leftOp, rightOp);
		}
	}

	@Override
	public SubExpr<RealType> withLeftOp(final Expr<RealType> leftOp) {
		return with(leftOp, getRightOp());
	}

	@Override
	public SubExpr<RealType> withRightOp(final Expr<RealType> rightOp) {
		return with(getLeftOp(), rightOp);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof RealSubExpr) {
			final RealSubExpr that = (RealSubExpr) obj;
			return this.getLeftOp().equals(that.getLeftOp()) && this.getRightOp().equals(that.getRightOp());
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
		return OPERATOR;
	}

}