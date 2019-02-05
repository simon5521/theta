package hu.bme.mit.theta.core.type.rangetype;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import hu.bme.mit.theta.common.Utils;
import hu.bme.mit.theta.core.type.Expr;
import hu.bme.mit.theta.core.type.Type;
import hu.bme.mit.theta.core.type.abstracttype.EqExpr;
import hu.bme.mit.theta.core.type.abstracttype.Equational;
import hu.bme.mit.theta.core.type.abstracttype.NeqExpr;
import hu.bme.mit.theta.core.type.inttype.IntExprs;
import hu.bme.mit.theta.core.type.inttype.IntLitExpr;
import hu.bme.mit.theta.core.type.inttype.IntType;

public final class RangeType extends IntType {
    private static final int HASH_SEED = 5441;
    private volatile int hashCode = 0;

    private final int lower;
    private final int upper;

    private RangeType(final int lower, final int upper) {
        checkArgument(lower <= upper);
        this.lower = lower;
        this.upper = upper;
    }

    public static RangeType Range(final int lower, final int upper) {
        return new RangeType(lower, upper);
    }

    public IntLitExpr Int(final int value) {
        checkArgument(value >= lower && value <= upper);
        return IntExprs.Int(value);
    }

    public Stream<IntLitExpr> values() {
        return IntStream.rangeClosed(lower, upper).mapToObj(IntExprs::Int);
    }

    public int getLower() {
        return lower;
    }

    public int getUpper() {
        return upper;
    }

    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = HASH_SEED;
            result = 31 * result + lower;
            result = 31 * result + upper;
            hashCode = result;
        }
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof RangeType) {
            final RangeType that = (RangeType) obj;
            return this.lower == that.lower && this.upper == that.upper;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return Utils.lispStringBuilder("Range").add(lower).add(upper).toString();
    }

}
