package toolbox;

import java.math.BigInteger;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public final class AtomicBigInteger extends Number implements java.io.Serializable{

	private static final long serialVersionUID = 1354208247948225650L;

    private final AtomicReference<BigInteger> atomicRefValue;
    
	private volatile BigInteger value;

	public AtomicBigInteger(BigInteger initialValue) {
        value = initialValue;
        this.atomicRefValue = new AtomicReference<>(Objects.requireNonNull(initialValue)); 
    }
	
	public final void set(BigInteger newValue) {
        value = newValue;
        atomicRefValue.set(newValue);
    }
	
	public BigInteger getAndIncrement() {
		return atomicRefValue.getAndAccumulate(BigInteger.ONE, (previous, x) -> previous.add(x));
	}
	
	public BigInteger get() {
		return value;
	}
	
	public final BigInteger getAndUpdate(BigIntegerUnaryOperator updateFunction) {
        BigInteger prev, next;
        do {
            prev = get();
            next = updateFunction.applyAsBigInteger(prev);
        } while (!compareAndSet(prev, next));
        return prev;
    }
	
	public final BigInteger updateAndGet(BigIntegerUnaryOperator updateFunction) {
        BigInteger prev, next;
        do {
            prev = get();
            next = updateFunction.applyAsBigInteger(prev);
        } while (!compareAndSet(prev, next));
        return next;
    }
	
	public final BigInteger getAndAccumulate(BigInteger x, BigIntegerBinaryOperator accumulatorFunction) {
		BigInteger prev, next;
		do {
			prev = get();
			next = accumulatorFunction.ApplyAsBigInteger(prev, x);
		} while (!compareAndSet(prev, next));
			return prev;
		}
  
	public final BigInteger accumulateAndGet(BigInteger x, BigIntegerBinaryOperator accumulatorFunction) {
		BigInteger prev, next;
		do {
			prev = get();
			next = accumulatorFunction.ApplyAsBigInteger(prev, x);
		}while (!compareAndSet(prev, next));
			return next;
		}

	@Override
	public double doubleValue() {
		return 0;
	}

	@Override
	public float floatValue() {
		return 0;
	}

	@Override
	public int intValue() {
		return 0;
	}

	@Override
	public long longValue() {
		return 0;
	}

	public boolean compareAndSet(BigInteger expect, BigInteger update) {
		boolean didSet = atomicRefValue.compareAndSet(expect, update);
		if (didSet) {
			value = atomicRefValue.get();
			return didSet;
		}else {
			return didSet;
		}
	}
}