package net.erchen.adventofcode2021.day15;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.erchen.codingpuzzlescommon.matrix.Matrix;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.stream.IntStream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.joining;

public class Chiton {
    private final Matrix<PathNode> cave;

    public static Chiton fromInput(String input) {
        return Chiton.builder()
            .cave(Matrix.fromInput(input, "\n", "", field -> new PathNode(Integer.parseInt(field))))
            .build();
    }

    public static String multipleMap(String input) {
        return IntStream.range(0, 5).mapToObj(y ->
                Arrays.stream(input.split("\n"))
                    .map(line -> IntStream.range(0, 5)
                        .mapToObj(x -> Arrays.stream(line.split(""))
                            .map(l -> Integer.parseInt(l) + x + y)
                            .map(c -> (c > 9) ? c - 9 : c)
                            .map(String::valueOf)
                            .collect(joining()))
                        .collect(joining("")))
                    .collect(joining("\n")))
            .collect(joining("\n"));

    }

    @Builder(access = AccessLevel.PRIVATE)
    public Chiton(Matrix<PathNode> cave) {
        this.cave = cave;
        cave.field(0, 0).getValue().setTotalCosts(0);

        var todo = new PriorityQueue<Matrix<PathNode>.Field>(1, comparingInt(x -> x.getValue().getTotalCosts()));
        var done = new HashSet<Matrix<PathNode>.Field>();
        todo.add(cave.field(0, 0));

        while (todo.size() > 0) {
            var current = todo.poll();
            done.add(current);
            current.getAdjacents()
                .filter(adjacent -> !done.contains(adjacent))
                .forEach(adjacent -> {
                    var totalCosts = adjacent.getValue().getCosts() + current.getValue().getTotalCosts();
                    if (totalCosts < adjacent.getValue().getTotalCosts()) {
                        adjacent.getValue().setPredecessor(current);
                        adjacent.getValue().setTotalCosts(totalCosts);
                        todo.remove(adjacent);
                        todo.add(adjacent);
                    }
                });
        }
    }

    public int calculateMinimalRisk() {
        return cave.field(cave.dimension() - 1, cave.dimension() - 1).getValue().getTotalCosts();
    }

    public LinkedList<Matrix<PathNode>.Field> bestRoute() {
        var route = new LinkedList<Matrix<PathNode>.Field>();
        var routeItem = cave.field(cave.dimension() - 1, cave.dimension() - 1);
        while (routeItem.getValue().getPredecessor() != null) {
            route.addFirst(routeItem);
            routeItem = routeItem.getValue().getPredecessor();
        }
        route.addFirst(routeItem);
        return route;
    }


    @Data
    @RequiredArgsConstructor
    public static class PathNode {
        private final int costs;
        private Matrix<PathNode>.Field predecessor = null;
        private int totalCosts = Integer.MAX_VALUE;
    }

}
