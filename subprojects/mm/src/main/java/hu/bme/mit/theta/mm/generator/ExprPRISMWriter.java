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
package hu.bme.mit.theta.mm.generator;

import hu.bme.mit.theta.common.DispatchTable;
import hu.bme.mit.theta.core.type.*;
import hu.bme.mit.theta.core.type.anytype.IteExpr;
import hu.bme.mit.theta.core.type.anytype.PrimeExpr;
import hu.bme.mit.theta.core.type.anytype.RefExpr;
import hu.bme.mit.theta.core.type.arithmetic.*;
import hu.bme.mit.theta.core.type.arraytype.ArrayReadExpr;
import hu.bme.mit.theta.core.type.arraytype.ArrayWriteExpr;
import hu.bme.mit.theta.core.type.booltype.*;
import hu.bme.mit.theta.core.type.inttype.*;
import hu.bme.mit.theta.core.type.inttype.ModExpr;
import hu.bme.mit.theta.core.type.inttype.RemExpr;
import hu.bme.mit.theta.core.type.rattype.*;
import hu.bme.mit.theta.core.type.realtype.*;
import hu.bme.mit.theta.core.type.templogic.*;
import hu.bme.mit.theta.core.type.operator.EventProbabilityOperator;
import hu.bme.mit.theta.core.type.operator.PropertyOperator;
import hu.bme.mit.theta.core.type.operator.RewardOperator;
import hu.bme.mit.theta.core.type.operator.SteadyStateProbabilityOperator;

public final class ExprPRISMWriter {

	private final DispatchTable<String> table;
	private final DispatchTable<String> typetable;

	private static class LazyHolder {
		private static ExprPRISMWriter INSTANCE = new ExprPRISMWriter();
	}

	public static ExprPRISMWriter instance() {
		return LazyHolder.INSTANCE;
	}

	private ExprPRISMWriter() {
		table = DispatchTable.<String>builder()

				// Boolean

				.addCase(NotExpr.class, e -> prefixUnary(e, "! "))

				.addCase(ImplyExpr.class, e -> infixBinary(e, " -> "))

				.addCase(IffExpr.class, e -> infixBinary(e, " iff "))

				.addCase(AndExpr.class, e -> infixMultiary(e, " & "))

				.addCase(OrExpr.class, e -> infixMultiary(e, " | "))

				.addCase(XorExpr.class, e -> infixBinary(e, " xor "))

				.addCase(TrueExpr.class, e -> "true")

				.addCase(FalseExpr.class, e -> "false")

				.addCase(ForallExpr.class, this::forall)

				.addCase(ExistsExpr.class, this::exists)

				// Temporal logic

				.addCase(NextExpr.class, e -> prefixUnary(e, "X "))

				.addCase(FutureExpr.class, e -> prefixUnary(e, "F "))

				.addCase(GlobalExpr.class, e -> prefixUnary(e, "G "))

				.addCase(UntilExpr.class, e -> infixBinary(e," U "))

				.addCase(BoundedUntilExpr.class, e -> infinixTernary(e," U<="," "))

				// Property operators

				.addCase(EventProbabilityOperator.class, e -> "P")

				.addCase(SteadyStateProbabilityOperator.class, e -> "S")

				.addCase(RewardOperator.class, e -> "R")

				//Property operator arithmetics

				.addCase(GetExactValue.class, e -> binaryOperatorArthimetric(e,"=?"))

				.addCase(GetMaxValue.class, e -> binaryOperatorArthimetric(e,"max=?"))

				.addCase(GetMinValue.class, e -> binaryOperatorArthimetric(e, "min=?"))

				.addCase(GT.class, e -> ternaryOperatorArthimetric(e, "<=") )

				.addCase(LT.class, e -> ternaryOperatorArthimetric(e, ">="))

				// Integer

				.addCase(IntAddExpr.class, e -> infixMultiary(e, " + "))

				.addCase(IntSubExpr.class, e -> infixBinary(e, " - "))

				.addCase(IntNegExpr.class, e -> prefixUnary(e, "-"))

				.addCase(IntMulExpr.class, e -> infixMultiary(e, " * "))

				.addCase(IntDivExpr.class, e -> infixBinary(e, " / "))

				.addCase(ModExpr.class, e -> infixBinary(e, " mod "))

				.addCase(RemExpr.class, e -> infixBinary(e, " rem "))

				.addCase(IntEqExpr.class, e -> infixBinary(e, " = "))

				.addCase(IntNeqExpr.class, e -> infixBinary(e, " /= "))

				.addCase(IntGeqExpr.class, e -> infixBinary(e, " >= "))

				.addCase(IntGtExpr.class, e -> infixBinary(e, " > "))

				.addCase(IntLeqExpr.class, e -> infixBinary(e, " <= "))

				.addCase(IntLtExpr.class, e -> infixBinary(e, " < "))

				.addCase(IntLitExpr.class, e -> e.getValue() + "")

				.addCase(IntToRatExpr.class, e -> prefixUnary(e, "(rat)"))


				// Real

				.addCase(RealAddExpr.class, e -> infixMultiary(e, " + "))

				.addCase(RealSubExpr.class, e -> infixBinary(e, " - "))

				.addCase(RealNegExpr.class, e -> prefixUnary(e, "-"))

				.addCase(RealMulExpr.class, e -> infixMultiary(e, " * "))

				.addCase(RealDivExpr.class, e -> infixBinary(e, " / "))

				.addCase(RealEqExpr.class, e -> infixBinary(e, " = "))

				.addCase(RealNeqExpr.class, e -> infixBinary(e, " /= "))

				.addCase(RealGeqExpr.class, e -> infixBinary(e, " >= "))

				.addCase(RealGtExpr.class, e -> infixBinary(e, " > "))

				.addCase(RealLeqExpr.class, e -> infixBinary(e, " <= "))

				.addCase(RealLtExpr.class, e -> infixBinary(e, " < "))

				.addCase(RealLitExpr.class, e -> e.getValue() + "")


				// Rational

				.addCase(RatAddExpr.class, e -> infixMultiary(e, " + "))

				.addCase(RatSubExpr.class, e -> infixBinary(e, " - "))

				.addCase(RatNegExpr.class, e -> prefixUnary(e, "-"))

				.addCase(RatMulExpr.class, e -> infixMultiary(e, " * "))

				.addCase(RatDivExpr.class, e -> infixBinary(e, " / "))

				.addCase(RatEqExpr.class, e -> infixBinary(e, " = "))

				.addCase(RatNeqExpr.class, e -> infixBinary(e, " /= "))

				.addCase(RatGeqExpr.class, e -> infixBinary(e, " >= "))

				.addCase(RatGtExpr.class, e -> infixBinary(e, " > "))

				.addCase(RatLeqExpr.class, e -> infixBinary(e, " <= "))

				.addCase(RatLtExpr.class, e -> infixBinary(e, " < "))

				.addCase(RatLitExpr.class, e -> e.getNum() + "%" + e.getDenom())

				// Array

				.addCase(ArrayReadExpr.class, this::arrayRead)

				.addCase(ArrayWriteExpr.class, this::arrayWrite)

				// General

				.addCase(RefExpr.class, e -> e.getDecl().getName())

				.addCase(IteExpr.class, this::ite)

				.addCase(PrimeExpr.class, e -> postfixUnary(e, "'"))

				.addDefault(e -> {
					throw new UnsupportedOperationException("Expression not supported: " + e.toString());
				})

				.build();

		typetable = DispatchTable.<String>builder()
				.addCase(IntType.class,e -> "int")
				.addCase(RealType.class,e -> "double")
				.addCase(BoolType.class,e -> "bool")
				.addDefault(e -> {
					throw new UnsupportedOperationException("Type is not supported: "+e.toString());
				}).build();

	}

	public String write(final Expr<?> expr) {
		return table.dispatch(expr);
	}

	public String write(final Type type) {
		return typetable.dispatch(type);
	}

	private String writeWithBrackets(final Expr<?> expr) {
		final boolean bracket = expr.getArity() > 0;
		return (bracket ? "(" : "") + write(expr) + (bracket ? ")" : "");
	}

	private String prefixUnary(final UnaryExpr<?, ?> expr, final String operator) {
		return operator + writeWithBrackets(expr.getOp());
	}

	private String postfixUnary(final UnaryExpr<?, ?> expr, final String operator) {
		return writeWithBrackets(expr.getOp()) + operator;
	}

	private String infixBinary(final BinaryExpr<?, ?> expr, final String operator) {
		return writeWithBrackets(expr.getLeftOp()) + operator + writeWithBrackets(expr.getRightOp());
	}

	private String infinixTernary(final TernaryExpr<?,?,?,?> expr, final String op1, final String op2){
		return (writeWithBrackets(expr.getOp1()) + op1 + writeWithBrackets(expr.getOp2()) + op2 + writeWithBrackets(expr.getOp3()));
	}

	private String infixMultiary(final MultiaryExpr<?, ?> expr, final String operator) {
		final StringBuilder sb = new StringBuilder();
		final int ops = expr.getOps().size();
		for (int i = 0; i < ops; ++i) {
			sb.append(writeWithBrackets(expr.getOps().get(i)));
			if (i != ops - 1) {
				sb.append(operator);
			}
		}
		return sb.toString();
	}

	private String binaryOperatorArthimetric(BinaryOperatorArthimetric<?> e, String operator){
		return write(((PropertyOperator) e.getOps().get(0)))+operator+" ["+write((Expr<BoolType>) e.getOps().get(1))+"];";
	}

	private String ternaryOperatorArthimetric(TernaryOperatorArthimetric<?> e, String operator){
		return write(((PropertyOperator) e.getOps().get(0)))+operator+write((Expr<RealType>) e.getOps().get(1))+" ["+write((Expr<BoolType>) e.getOps().get(2))+"];";
	}

	private String forall(final ForallExpr e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}

	private String exists(final ExistsExpr e) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("TODO: auto-generated method stub");
	}

	private String arrayRead(final ArrayReadExpr<?, ?> e) {
		return writeWithBrackets(e.getArray()) + "[" + write(e.getIndex()) + "]";
	}

	private String arrayWrite(final ArrayWriteExpr<?, ?> e) {
		return writeWithBrackets(e.getArray()) + "[" + write(e.getIndex()) + " <- " + write(e.getElem()) + "]";
	}

	private String ite(final IteExpr<?> expr) {
		final StringBuilder sb = new StringBuilder();
		sb.append("if ");
		sb.append(writeWithBrackets(expr.getCond()));
		sb.append(" then ");
		sb.append(writeWithBrackets(expr.getThen()));
		sb.append(" else ");
		sb.append(writeWithBrackets(expr.getElse()));
		return sb.toString();
	}

}
