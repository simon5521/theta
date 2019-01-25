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

import java.util.Collection;

import hu.bme.mit.theta.core.decl.ParamDecl;
import hu.bme.mit.theta.core.decl.VarDecl;
import hu.bme.mit.theta.core.stmt.AssignStmt;
import hu.bme.mit.theta.core.stmt.AssumeStmt;
import hu.bme.mit.theta.core.stmt.HavocStmt;
import hu.bme.mit.theta.core.stmt.SkipStmt;
import hu.bme.mit.theta.core.stmt.StmtVisitor;
import hu.bme.mit.theta.core.type.Type;

final class ParamCollectorStmtVisitor implements StmtVisitor<Collection<ParamDecl<?>>, Void> {

    private static final class LazyHolder {
        private final static ParamCollectorStmtVisitor INSTANCE = new ParamCollectorStmtVisitor();
    }

    private ParamCollectorStmtVisitor() {
    }

    static ParamCollectorStmtVisitor getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public Void visit(final SkipStmt stmt, final Collection<ParamDecl<?>> vars) {
        return null;
    }

    @Override
    public Void visit(final AssumeStmt stmt, final Collection<ParamDecl<?>> params) {
        ExprUtils.collectParams(stmt.getCond(), params);
        return null;
    }

    @Override
    public <DeclType extends Type> Void visit(final AssignStmt<DeclType> stmt, final Collection<ParamDecl<?>> params) {
        ExprUtils.collectParams(stmt.getExpr(), params);
        return null;
    }

    @Override
    public <DeclType extends Type> Void visit(final HavocStmt<DeclType> stmt, final Collection<ParamDecl<?>> params) {

        //do nothing becouse there is no parameter, only variables
        return null;
    }

}
