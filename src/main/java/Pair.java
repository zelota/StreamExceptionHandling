/**
 * The Pair type used here is another generic type that can be found in the Apache Commons lang library,
 * or you can simply implement your own.
 * Anyway, it is just a type that can hold two values.
 *
 * @param <F>
 * @param <S>
 */
public class Pair<F, S> {

    public final F fst;

    public final S snd;

    private Pair( F fst, S snd ) {
        this.fst = fst;
        this.snd = snd;
    }

    public static <F, S> Pair<F, S> of( F fst, S snd ) {
        return new Pair<>(fst, snd);
    }

}
