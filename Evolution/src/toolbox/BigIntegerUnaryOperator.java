package toolbox;

import java.math.BigInteger;
import java.util.Objects;

@FunctionalInterface
public interface BigIntegerUnaryOperator {
	
	BigInteger applyAsBigInteger(BigInteger operand);
	
	default BigIntegerUnaryOperator andThen(BigIntegerUnaryOperator after) {
		Objects.requireNonNull(after);
        return (BigInteger t) -> after.applyAsBigInteger(applyAsBigInteger(t));
	}
	
	static BigIntegerUnaryOperator identity() {
        return t -> t;
    }
	
}
