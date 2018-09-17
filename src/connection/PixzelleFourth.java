package sr.pago.sdk.connection;

/**
 * Class for having a generic object with four types, primary used in checking errors
 *
 * @author  Rodolfo Pena - * Sr. Pago All rights reserved.
 * @version 1.0
 * @since   2014-03-25
 */
public class PixzelleFourth<F,S,T,Fo> {
    public final F first;
    public final S second;
    public final T third;
    public final Fo fourth;

    public PixzelleFourth(F first, S second, T third, Fo fourth) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.fourth = fourth;
    }

    /**
     * Compute a hash code using the hash codes of the underlying objects
     *
     * @return a hashcode of the Pair
     */
    @Override
    public int hashCode() {
        return (first == null ? 0 : first.hashCode()) ^
                (second == null ? 0 : second.hashCode()) ^
                (third == null ? 0 : third.hashCode()) ^
                (fourth == null ? 0 : fourth.hashCode());
    }

    /**
     * Convenience method for creating an appropriately typed pair.
     * @param a the first object in the fourth
     * @param b the second object in the fourth
     * @param c the third object in the fourth
     * @param d the fourth object in the fourth
     * @return a Fourth that is templatized with the types of a, b, c, d
     */
    public static <A, B, C, D> PixzelleFourth<A, B, C, D> create(A a, B b, C c, D d) {
        return new PixzelleFourth<>(a, b, c, d);
    }

    /**
     * Checks the two objects for equality by delegating to their respective
     * {@link Object#equals(Object)} methods.
     *
     * @param o the {@link PixzelleFourth} to which this one is to be checked for equality
     * @return true if the underlying objects of the Pair are both considered
     *         equal
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PixzelleFourth)) {
            return false;
        }
        PixzelleFourth<?, ?, ?, ?> p = (PixzelleFourth<?, ?, ?, ?>) o;
        return p.first.equals(first) && p.second.equals(second) && p.third.equals(third) && p.fourth.equals(fourth);
    }
}
