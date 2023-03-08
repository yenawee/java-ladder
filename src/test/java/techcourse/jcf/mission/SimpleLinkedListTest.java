package techcourse.jcf.mission;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class SimpleLinkedListTest {
    List<String> original;
    SimpleList simpleList;

    @BeforeEach
    void setUp() {
        original = new LinkedList<>();
        simpleList = new SimpleLinkedList();
    }

    @Test
    void add() {
        assertThat(original.add("value")).isTrue();
        assertThat(simpleList.add("value")).isTrue();
    }

    @Test
    void testAdd() {
        assertThatThrownBy(() -> original.add(3, "v")).isInstanceOf(IndexOutOfBoundsException.class);
        assertThatThrownBy(() -> simpleList.add(3, "v")).isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void set() {
        original.add("oldvalue");
        assertThat(original.set(0, "value")).isEqualTo("oldvalue");

        simpleList.add("oldvalue");
        assertThat(simpleList.set(0, "value")).isEqualTo("oldvalue");
    }

    @Test
    void get() {
        original.add("1");
        original.add("2");
        original.add("3");

        assertThat(original.get(1)).isEqualTo("2");

        simpleList.add("1");
        simpleList.add("2");
        simpleList.add("3");

        assertThat(simpleList.get(1)).isEqualTo("2");
    }

    @Test
    void contains() {
        original.add("one");
        original.add("target");
        original.add("targett");

        assertThat(original.contains("target")).isTrue();

        simpleList.add("one");
        simpleList.add("target");
        simpleList.add("targett");

        assertThat(simpleList.contains("target")).isTrue();
    }

    @Test
    void indexOf() {
        original.add("1");
        original.add("2");
        original.add("3");

        assertThat(original.indexOf("3")).isEqualTo(2);
        assertThat(original.indexOf("faketarget")).isEqualTo(-1);

        simpleList.add("1");
        simpleList.add("2");
        simpleList.add("3");

        assertThat(simpleList.indexOf("3")).isEqualTo(2);
        assertThat(simpleList.indexOf("faketarget")).isEqualTo(-1);
    }

    @Test
    void size() {
        assertThat(original.size()).isEqualTo(0);
        original.add("one");
        assertThat(original.size()).isEqualTo(1);
        original.add("two");
        original.add(0, "three");
        assertThat(original.size()).isEqualTo(3);

        assertThat(simpleList.size()).isEqualTo(0);
        simpleList.add("one");
        assertThat(simpleList.size()).isEqualTo(1);
        simpleList.add("two");
        simpleList.add(0, "three");
        assertThat(simpleList.size()).isEqualTo(3);
    }

    @Test
    void isEmpty() {
        assertThat(original.isEmpty()).isTrue();
        assertThat(simpleList.isEmpty()).isTrue();
    }

    @Test
    void remove() {
        original.add("first");
        original.add("second");

        assertThat(original.remove("second")).isTrue();
        assertThat(original.contains("second")).isFalse();
        assertThat(original.size()).isEqualTo(1);

        simpleList.add("first");
        simpleList.add("second");

        assertThat(simpleList.remove("second")).isTrue();
        assertThat(simpleList.contains("second")).isFalse();
        assertThat(simpleList.size()).isEqualTo(1);
    }

    @Test
    void testRemove() {
        original.add("1");
        original.add("2");
        original.add("3");

        assertThat(original.remove(1)).isEqualTo("2");
        assertThat(original.size()).isEqualTo(2);

        simpleList.add("1");
        simpleList.add("2");
        simpleList.add("3");

        assertThat(simpleList.remove(1)).isEqualTo("2");
        assertThat(simpleList.size()).isEqualTo(2);
    }

    @Test
    void clear() {
        original.add("1");
        original.add("2");
        original.add("3");
        original.clear();
        assertThat(original.contains("1")).isFalse();
        assertThat(original.size()).isEqualTo(0);

        simpleList.add("1");
        simpleList.add("2");
        simpleList.add("3");
        simpleList.clear();
        assertThat(simpleList.contains("1")).isFalse();
        assertThat(simpleList.size()).isEqualTo(0);
    }
}
