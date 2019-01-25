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

import com.google.common.collect.ImmutableList;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.realtype.RealType;

public final class RealExprs {

	private RealExprs() {
	}

	public static RealType Real() {
		return RealType.getInstance();
	}

	public static RealLitExpr Real(final double value) {
		return RealLitExpr.of(value);
	}

	public static RealAddExpr Add(final Iterable<? extends Expr<RealType>> ops) {
		return RealAddExpr.of(ops);
	}

	public static RealSubExpr Sub(final Expr<RealType> leftOp, final Expr<RealType> rightOp) {
		return RealSubExpr.of(leftOp, rightOp);
	}

	public static RealNegExpr Neg(final Expr<RealType> op) {
		return RealNegExpr.of(op);
	}

	public static RealMulExpr Mul(final Iterable<? extends Expr<RealType>> ops) {
		return RealMulExpr.of(ops);
	}

	public static RealDivExpr Div(final Expr<RealType> leftOp, final Expr<RealType> rightOp) {
		return RealDivExpr.of(leftOp, rightOp);
	}

	public static ModExpr Mod(final Expr<RealType> leftOp, final Expr<RealType> rightOp) {
		return ModExpr.of(leftOp, rightOp);
	}

	public static RemExpr Rem(final Expr<RealType> leftOp, final Expr<RealType> rightOp) {
		return RemExpr.of(leftOp, rightOp);
	}

	public static RealEqExpr Eq(final Expr<RealType> leftOp, final Expr<RealType> rightOp) {
		return RealEqExpr.of(leftOp, rightOp);
	}

	public static RealNeqExpr Neq(final Expr<RealType> leftOp, final Expr<RealType> rightOp) {
		return RealNeqExpr.of(leftOp, rightOp);
	}

	public static RealLtExpr Lt(final Expr<RealType> leftOp, final Expr<RealType> rightOp) {
		return RealLtExpr.of(leftOp, rightOp);
	}

	public static RealLeqExpr Leq(final Expr<RealType> leftOp, final Expr<RealType> rightOp) {
		return RealLeqExpr.of(leftOp, rightOp);
	}

	public static RealGtExpr Gt(final Expr<RealType> leftOp, final Expr<RealType> rightOp) {
		return RealGtExpr.of(leftOp, rightOp);
	}

	public static RealGeqExpr Geq(final Expr<RealType> leftOp, final Expr<RealType> rightOp) {
		return RealGeqExpr.of(leftOp, rightOp);
	}

	/*
	 * Convenience methods
	 */

	public static RealAddExpr Add(final Expr<RealType> op1, final Expr<RealType> op2) {
		return RealAddExpr.of(ImmutableList.of(op1, op2));
	}

	public static RealAddExpr Add(final Expr<RealType> op1, final Expr<RealType> op2, final Expr<RealType> op3) {
		return RealAddExpr.of(ImmutableList.of(op1, op2, op3));
	}

	public static RealAddExpr Add(final Expr<RealType> op1, final Expr<RealType> op2, final Expr<RealType> op3,
								  final Expr<RealType> op4) {
		return RealAddExpr.of(ImmutableList.of(op1, op2, op3, op4));
	}

	public static RealAddExpr Add(final Expr<RealType> op1, final Expr<RealType> op2, final Expr<RealType> op3,
								  final Expr<RealType> op4, final Expr<RealType> op5) {
		return RealAddExpr.of(ImmutableList.of(op1, op2, op3, op4, op5));
	}

	////

	public static RealMulExpr Mul(final Expr<RealType> op1, final Expr<RealType> op2) {
		return RealMulExpr.of(ImmutableList.of(op1, op2));
	}

	public static RealMulExpr Mul(final Expr<RealType> op1, final Expr<RealType> op2, final Expr<RealType> op3) {
		return RealMulExpr.of(ImmutableList.of(op1, op2, op3));
	}

	public static RealMulExpr Mul(final Expr<RealType> op1, final Expr<RealType> op2, final Expr<RealType> op3,
								  final Expr<RealType> op4) {
		return RealMulExpr.of(ImmutableList.of(op1, op2, op3, op4));
	}

	public static RealMulExpr Mul(final Expr<RealType> op1, final Expr<RealType> op2, final Expr<RealType> op3,
								  final Expr<RealType> op4, final Expr<RealType> op5) {
		return RealMulExpr.of(ImmutableList.of(op1, op2, op3, op4, op5));
	}

}
