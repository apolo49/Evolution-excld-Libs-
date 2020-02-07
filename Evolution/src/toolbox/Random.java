package toolbox;

import java.io.File;
import java.math.BigInteger;

import fileHandling.log.Logger;

public class Random extends java.util.Random{


	private static final long serialVersionUID = -2057624158626154913L;
	private static final File file = new File(System.getenv("APPDATA")+"\\Evolution\\logs\\Latest.txt");
    private final AtomicBigInteger seed;
    private static final BigInteger multiplier = BigInteger.valueOf(0x5DEECE66DL);
    private static final BigInteger mask = BigInteger.valueOf((1L << 48) - 1);
    private static final BigInteger addend = BigInteger.valueOf(0xBL);
    private double nextNextGaussian;
    private boolean haveNextNextGaussian = false;
    private static final AtomicBigInteger seedUniquifier = new AtomicBigInteger(new BigInteger("8682522807148012"));
    
    public synchronized double nextGaussian() {
        if (haveNextNextGaussian) {
            haveNextNextGaussian = false;
            return nextNextGaussian;
        } else {
            double v1, v2, s;
            do {
                v1 = 2 * nextDouble() - 1; // between -1 and 1
                v2 = 2 * nextDouble() - 1; // between -1 and 1
                s = v1 * v1 + v2 * v2;
            } while (s >= 1 || s == 0);
            double multiplier = StrictMath.sqrt(-2 * StrictMath.log(s)/s);
            nextNextGaussian = v2 * multiplier;
            haveNextNextGaussian = true;
            return v1 * multiplier;
        }
    }
	
	private static BigInteger seedUniquifier() {
        for (;;) {
            BigInteger current = seedUniquifier.get();
            BigInteger next = current.multiply(new BigInteger("1181783497276652981"));
            if (seedUniquifier.compareAndSet(current, next))
                return next;
        }
    }
	
    public Random() {
        this(seedUniquifier().xor(new BigInteger(""+System.nanoTime())));
    }
    
    public Random(BigInteger seed) {
        if (getClass() == Random.class)
            this.seed = new AtomicBigInteger(initialScramble(seed));
        else {
            this.seed = new AtomicBigInteger(BigInteger.ZERO);
            setSeed(seed);
        }
    }
    
    private static BigInteger initialScramble(BigInteger seed) {
        return (seed.xor(multiplier)).and(mask);
    }
    
    public synchronized void setSeed(BigInteger seed) {
        this.seed.set(initialScramble(seed));
        haveNextNextGaussian = false;
    }
    
    protected int next(int bits) {
        BigInteger oldseed, nextseed;
        AtomicBigInteger seed = this.seed;
        do {
            oldseed = seed.get();
            nextseed = (oldseed.multiply(multiplier).add(addend)).add(mask);
        } while (!seed.compareAndSet(oldseed, nextseed));
        return (int)(nextseed.shiftRight((Integer.MAX_VALUE - bits))).intValue();
    }
    
    public BigInteger nextBigInteger() {
    	Logger.main("Making new random bigInteger.", 0, file);
        return ((BigInteger.valueOf((next(Integer.MAX_VALUE)))).shiftLeft(Integer.MAX_VALUE)).add(BigInteger.valueOf(next(Integer.MAX_VALUE)));
    }
	
}
