package Testing;

import AdventureModel.AdventureGame;
import AdventureModel.Troll;
import AdventureModel.TrollFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TrollTests {
    @Test
    void testTrollFactory() {
        TrollFactory tf = new TrollFactory();

        try {
            Troll t = tf.createTroll("BaseTroll");
            t.getRequiredItems();
        } catch (ClassNotFoundException e) { fail(); }
    }

    @Test
    void testTrollFactoryInvalidName() {
        TrollFactory tf = new TrollFactory();

        try {
            Troll t = tf.createTroll("foo");
            fail();
        } catch (ClassNotFoundException ignored) {}
    }

    @Test
    void testTrollFactoryEmptyDirectory() {
        TrollFactory tf = new TrollFactory();

        try {
            tf.createTroll("EmptyTroll");
            fail();
        } catch (ClassNotFoundException ignored) {}
    }

    @Test
    void testTrollFactoryClassNotTroll() {
        // TODO: Add test
    }
}
