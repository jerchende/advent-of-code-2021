package net.erchen.adventofcode2021.day13;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class OrigamiTest {

    @SneakyThrows
    static String sampleInput() {
        return Files.readString(Path.of("src/test/resources/day13/sample.txt"));
    }

    @SneakyThrows
    static String solutionInput() {
        return Files.readString(Path.of("src/test/resources/day13/input.txt"));
    }

    @Test
    void shouldFoldX() {
        var origami = Origami.builder().size(5).input("""
            0,0
            4,0
                        
            fold along x=2""").build();


        assertThat(origami.toString()).isEqualTo("""
            #...#
            .....
            .....
            .....
            .....
            """);

        origami.executeCommands(1);

        assertThat(origami.toString()).isEqualTo("""
            #....
            .....
            .....
            .....
            .....
            """);
    }

    @Test
    void shouldFoldY() {
        var origami = Origami.builder().size(5).input("""
            4,4
            4,0
                        
            fold along y=2""").build();


        assertThat(origami.toString()).isEqualTo("""
            ....#
            .....
            .....
            .....
            ....#
            """);

        origami.executeCommands(1);

        assertThat(origami.toString()).isEqualTo("""
            ....#
            .....
            .....
            .....
            .....
            """);
    }

    @Test
    void shouldExecuteFirstCommand_Sample() {
        var origami = Origami.builder().size(15).input(sampleInput()).build();

        assertThat(origami.countActiveFields()).isEqualTo(18);
        assertThat(origami.toString()).isEqualTo("""
            ...#..#..#.....
            ....#..........
            ...............
            #..............
            ...#....#.#....
            ...............
            ...............
            ...............
            ...............
            ...............
            .#....#.##.....
            ....#..........
            ......#...#....
            #..............
            #.#............
            """);

        origami.executeCommands(1);

        assertThat(origami.toString()).isEqualTo("""
            #.##..#..#.....
            #...#..........
            ......#...#....
            #...#..........
            .#.#..#.###....
            ...............
            ...............
            ...............
            ...............
            ...............
            ...............
            ...............
            ...............
            ...............
            ...............
                        """);
        assertThat(origami.countActiveFields()).isEqualTo(17);
    }

    @Test
    void shouldExecuteFirstCommand_Solution() {
        var origami = Origami.builder().size(1500).input(solutionInput()).build();

        origami.executeCommands(1);
        assertThat(origami.countActiveFields()).isEqualTo(687);
    }

    @Test
    void shouldCrackCode_Solution() {
        var origami = Origami.builder().size(1500).input(solutionInput()).build();

        origami.executeCommands(100);
        assertThat(origami.toString()).contains("####..##..#..#..##..#..#.###..####..##.");
        assertThat(origami.toString()).contains("#....#..#.#.#..#..#.#.#..#..#....#.#..#");
        assertThat(origami.toString()).contains("###..#....##...#....##...###....#..#...");
        assertThat(origami.toString()).contains("#....#.##.#.#..#....#.#..#..#..#...#.##");
        assertThat(origami.toString()).contains("#....#..#.#.#..#..#.#.#..#..#.#....#..#");
        assertThat(origami.toString()).contains("#.....###.#..#..##..#..#.###..####..###");
    }
}