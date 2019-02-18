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
package hu.bme.mit.theta.core.utils;

import static com.google.common.base.Preconditions.checkNotNull;

import hu.bme.mit.theta.core.decl.Decl;
import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.LitExpr;
import hu.bme.mit.theta.core.type.Type;
import hu.bme.mit.theta.core.type.booltype.BoolLitExpr;
import hu.bme.mit.theta.core.type.booltype.BoolType;
import hu.bme.mit.theta.core.type.inttype.IntLitExpr;
import hu.bme.mit.theta.core.type.inttype.IntType;
import hu.bme.mit.theta.core.type.realtype.RealLitExpr;
import hu.bme.mit.theta.core.type.realtype.RealType;

/**
 * Utility functions related to types.
 */
public final class TypeUtils {

	private TypeUtils() {
	}

	/**
	 * Cast a declaration to a given type.
	 *
	 * @param decl Original declaration
	 * @param type Type
	 * @return Casted declaration
	 */
	public static <T extends Type> Decl<T> cast(final Decl<?> decl, final T type) {
		checkNotNull(decl);
		checkNotNull(type);

		if (decl.getType().equals(type)) {
			@SuppressWarnings("unchecked")
			final Decl<T> result = (Decl<T>) type;
			return result;
		} else {
			throw new ClassCastException("The type of declaration " + decl + " is not of type " + type);
		}
	}

	/**
	 * Cast a variable declaration to a given type.
	 *
	 * @param decl Original declaration
	 * @param type Type
	 * @return Casted declaration
	 */
	public static <T extends Type> VarDecl<T> cast(final VarDecl<?> decl, final T type) {
		checkNotNull(decl);
		checkNotNull(type);

		if (decl.getType().equals(type)) {
			@SuppressWarnings("unchecked")
			final VarDecl<T> result = (VarDecl<T>) decl;
			return result;
		} else {
			throw new ClassCastException("The type of declaration " + decl + " is not of type " + type);
		}
	}

	/**
	 * Cast an expression to a given type.
	 *
	 * @param expr Original expression
	 * @param type Type
	 * @return Casted expression
	 */
	public static <T extends Type> Expr<T> cast(final Expr<?> expr, final T type) {
		checkNotNull(expr);
		checkNotNull(type);

		if (expr.getType().equals(type)) {
			@SuppressWarnings("unchecked")
			final Expr<T> result = (Expr<T>) expr;
			return result;
		} else {
			throw new ClassCastException("The type of expression " + expr + " is not of type " + type);
		}
	}

	public static  LitExpr<?> cast(final Object object){
		checkNotNull(object);

		if (object instanceof LitExpr<?>){
			final LitExpr<?> result=(LitExpr<?>) object;
			return result;
		} else if (object instanceof Double){
			final LitExpr<RealType> result= RealLitExpr.of((Double)object);
			return result;
		} else if (object instanceof Integer){
			final LitExpr<IntType> result= IntLitExpr.of((Integer)object);
			return result;
		} else if (object instanceof Boolean){
			final LitExpr<BoolType> result= BoolLitExpr.of((Boolean) object);
			return result;
		} else {
			throw new UnsupportedOperationException();
		}
	}

}
