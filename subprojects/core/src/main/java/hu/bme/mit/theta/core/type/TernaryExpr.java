package hu.bme.mit.theta.core.type;

import com.google.common.collect.ImmutableList;
import hu.bme.mit.theta.common.Utils;
import hu.bme.mit.theta.core.utils.TypeUtils;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public abstract class TernaryExpr<Op1Type extends  Type, Op2Type extends  Type, Op3Type extends  Type,ReturnType extends Type> implements Expr<ReturnType> {

    private final Expr<Op1Type> op1;
    private final Expr<Op2Type> op2;
    private final Expr<Op3Type> op3;


    private volatile int hashCode = 0;


    protected TernaryExpr(Expr<Op1Type> op1, Expr<Op2Type> op2, Expr<Op3Type> op3) {
        this.op1 = op1;
        this.op2 = op2;
        this.op3 = op3;
    }

    public Expr<Op1Type> getOp1(){
        return op1;
    }

    public Expr<Op2Type> getOp2(){
        return op2;
    }

    public Expr<Op3Type> getOp3(){
        return op3;
    }



    @Override
    public final int getArity() {
        return 3;
    }

    @Override
    public final List<Expr<?>> getOps() {
        return ImmutableList.of(op1,op2,op3);
    }

    @Override
    public final TernaryExpr<Op1Type,Op2Type,Op3Type,ReturnType> withOps(final List<? extends Expr<?>> ops) {
        checkNotNull(ops);
        checkArgument(ops.size() == 3);
        final Op1Type opType1 = getOp1().getType();
        final Op2Type opType2 = getOp2().getType();
        final Op3Type opType3 = getOp3().getType();
        final Expr<Op1Type> newOp1 = TypeUtils.cast(ops.get(0), opType1);
        final Expr<Op2Type> newOp2 = TypeUtils.cast(ops.get(1), opType2);
        final Expr<Op3Type> newOp3 = TypeUtils.cast(ops.get(2), opType3);
        return with(op1, op2, op3);
    }

    @Override
    public final int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = getHashSeed();
            result = 31 * result + getOp1().hashCode();
            result = 33 * result + getOp2().hashCode();
            result = 37 * result + getOp3().hashCode();
            hashCode = result;
        }
        return result;
    }

    @Override
    public final String toString() {
        return Utils.lispStringBuilder(getOperatorLabel()).add(op1).add(op2).add(op3).toString();
    }

    public abstract TernaryExpr<Op1Type, Op2Type,Op3Type,ReturnType> with(final Expr<Op1Type> op1, final Expr<Op2Type> op2, final Expr<Op3Type> op3 );

    public abstract TernaryExpr<Op1Type, Op2Type,Op3Type,ReturnType> withOp1(final Expr<Op1Type> op1);
    public abstract TernaryExpr<Op1Type, Op2Type,Op3Type,ReturnType> withOp2(final Expr<Op2Type> op2);
    public abstract TernaryExpr<Op1Type, Op2Type,Op3Type,ReturnType> withOp3(final Expr<Op3Type> op3);


    protected abstract int getHashSeed();

    protected abstract String getOperatorLabel();


}
