package toolbox;

import java.math.BigInteger;

@FunctionalInterface

public interface BigIntegerBinaryOperator{
	
	BigInteger ApplyAsBigInteger(BigInteger right, BigInteger left);
}
