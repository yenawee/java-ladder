package techcourse.jcf.mission;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;

class SimpleArrayListTest {
    List<String> original;
    SimpleList simpleList;

    @BeforeEach
    void setup() {
        original = new ArrayList<>();
        simpleList = new SimpleArrayList();
    }

    @Test
    void create() {
        Assertions.assertThatThrownBy(() -> new ArrayList<>(-1)).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new SimpleArrayList(-1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void add() {
        assertThatNoException().isThrownBy(() -> original.add(0, "first"));
        assertThatNoException().isThrownBy(() -> simpleList.add(0, "first"));

        Assertions.assertThatThrownBy(() -> original.add(3, "third")).isInstanceOf(IndexOutOfBoundsException.class);
        Assertions.assertThatThrownBy(() -> simpleList.add(3, "third")).isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void set() {
        original.add("first");
        original.add("second");
        original.add("third");

        assertThat(original.set(2, "replacethird")).isEqualTo("third");

        simpleList.add("first");
        simpleList.add("second");
        simpleList.add("third");

        assertThat(simpleList.set(2, "replacethird")).isEqualTo("third");
    }

    @Test
    void get() {
        original.add("first");
        original.add("second");

        assertThat(original.get(1)).isEqualTo("second");

        simpleList.add("first");
        simpleList.add("second");

        assertThat(simpleList.get(1)).isEqualTo("second");
    }

    @Test
    void contains() {
        original.add("one");
        assertThat(original.contains("one")).isTrue();
        assertThat(original.contains("two")).isFalse();

        simpleList.add("one");
        assertThat(simpleList.contains("one")).isTrue();
        assertThat(simpleList.contains("two")).isFalse();
    }

    @Test
    void indexOf() {
        original.add("1");
        original.add("2");

        assertThat(original.indexOf("1")).isEqualTo(0);

        simpleList.add("1");
        simpleList.add("2");

        assertThat(simpleList.indexOf("1")).isEqualTo(0);

    }

    @Test
    void size() {
        original.add("first");
        assertThat(original.size()).isEqualTo(1);

        simpleList.add("first");
        assertThat(simpleList.size()).isEqualTo(1);
    }

    @Test
    void isEmpty() {
        assertThat(original.isEmpty()).isTrue();
        assertThat(simpleList.isEmpty()).isTrue();
    }

    @Test
    void remove() {
        original.add("removed1");
        assertThat(original.remove("removed1")).isTrue();
        assertThat(original.remove("removed1")).isFalse();
        assertThat(original.size()).isEqualTo(0);

        simpleList.add("removed1");
        assertThat(simpleList.remove("removed1")).isTrue();
        assertThat(simpleList.remove("removed1")).isFalse();
        assertThat(original.size()).isEqualTo(0);
    }

    @Test
    void removeByIndex() {
        original.add("1");
        original.add("2");

        assertThatThrownBy(() -> original.remove(3)).isInstanceOf(IndexOutOfBoundsException.class);
        assertThat(original.remove(1)).isEqualTo("2");
        assertThat(original.size()).isEqualTo(1);

        simpleList.add("1");
        simpleList.add("2");

        assertThatThrownBy(() -> simpleList.remove(3)).isInstanceOf(IndexOutOfBoundsException.class);
        assertThat(simpleList.remove(1)).isEqualTo("2");
        assertThat(simpleList.size()).isEqualTo(1);
    }

    @Test
    void clear() {
        original.add("1");
        original.add("2");
        original.add("3");

        original.clear();
        assertThat(original.size()).isEqualTo(0);
        assertThatThrownBy(() -> original.get(0)).isInstanceOf(IndexOutOfBoundsException.class);

        simpleList.add("1");
        simpleList.add("2");
        simpleList.add("3");

        simpleList.clear();
        assertThat(simpleList.size()).isEqualTo(0);
        assertThatThrownBy(() -> simpleList.get(0)).isInstanceOf(IndexOutOfBoundsException.class);
    }
}
