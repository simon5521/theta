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
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.NullaryExpr;
import hu.bme.mit.theta.core.type.booltype.BoolLitExpr;
import hu.bme.mit.theta.core.type.realtype.RealType;
import hu.bme.mit.theta.core.type.rattype.RatLitExpr;

import static hu.bme.mit.theta.core.type.booltype.BoolExprs.Bool;
import static hu.bme.mit.theta.core.type.realtype.RealExprs.Real;
import static hu.bme.mit.theta.core.type.rattype.RatExprs.Rat;


public final class RealLitExpr extends NullaryExpr<RealType> implements LitExpr<RealType>, Comparable<RealLitExpr> {

	private static final int HASH_SEED = 4111;
	private volatile int hashCode = 0;

	private final double value;

	private RealLitExpr(final double value) {
		this.value = value;
	}

	public static RealLitExpr of(final double value) {
		return new RealLitExpr(value);
	}

	public double getValue() {
		return value;
	}

	@Override
	public RealType getType() {
		return Real();
	}

	@Override
	public RealLitExpr eval(final Valuation val) {
		return this;
	}

	public RealLitExpr add(final RealLitExpr that) {
		return RealLitExpr.of(this.value + that.value);
	}

	public RealLitExpr sub(final RealLitExpr that) {
		return RealLitExpr.of(this.value - that.value);
	}

	public RealLitExpr neg() {
		return RealLitExpr.of(-this.value);
	}

	public RealLitExpr div(final RealLitExpr that) {
		return RealLitExpr.of(this.value / that.value);
	}

	public RealLitExpr mod(final RealLitExpr that) {
		// Always positive semantics:
		// 5 mod 3 = 2
		// 5 mod -3 = 2
		// -5 mod 3 = 1
		// -5 mod -3 = 1
		double result = this.value % that.value;
		if (result < 0) {
			result += Math.abs(that.value);
		}
		assert result >= 0;
		return RealLitExpr.of(result);
	}

	public RealLitExpr rem(final RealLitExpr that) {
		// Semantics:
		// 5 rem 3 = 2
		// 5 rem -3 = -2
		// -5 rem 3 = 1
		// -5 rem -3 = -1
		final double thisAbs = Math.abs(this.value);
		final double thatAbs = Math.abs(that.value);
		if (this.value < 0 && that.value < 0) {
			double result = thisAbs % thatAbs;
			if (result != 0) {
				result -= thatAbs;
			}
			return new RealLitExpr(result);
		} else if (this.value >= 0 && that.value < 0) {
			return new RealLitExpr(-(thisAbs % thatAbs));
		} else if (this.value < 0 && that.value >= 0) {
			double result = thisAbs % thatAbs;
			if (result != 0) {
				result = thatAbs - result;
			}
			return RealLitExpr.of(result);
		} else {
			return RealLitExpr.of(this.value % that.value);
		}
	}

	public BoolLitExpr eq(final RealLitExpr that) {
		return Bool(this.value == that.value);
	}

	public BoolLitExpr neq(final RealLitExpr that) {
		return Bool(this.value != that.value);
	}

	public BoolLitExpr lt(final RealLitExpr that) {
		return Bool(this.value < that.value);
	}

	public BoolLitExpr leq(final RealLitExpr that) {
		return Bool(this.value <= that.value);
	}

	public BoolLitExpr gt(final RealLitExpr that) {
		return Bool(this.value > that.value);
	}

	public BoolLitExpr geq(final RealLitExpr that) {
		return Bool(this.value >= that.value);
	}

	@Override
	public int hashCode() {
		int result = hashCode;
		if (result == 0) {
			result = HASH_SEED;
			result = 31 * result + ((int)value);
			hashCode = result;
		}
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof RealLitExpr) {
			final RealLitExpr that = (RealLitExpr) obj;
			return this.getValue() == that.getValue();
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return Double.toString(getValue());
	}

	@Override
	public int compareTo(final RealLitExpr that) {
		return Double.compare(this.getValue(), that.getValue());
	}

}
