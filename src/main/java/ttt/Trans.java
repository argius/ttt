package ttt;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public final class Trans {

    static String applyRows(String text, Consumer<List<String>> f) {
        List<String> a = textToRows(text);
        f.accept(a);
        return String.join("\n", a);
    }

    public static String sort(String s) {
        return applyRows(s, Collections::sort);
    }

    public static String reverse(String s) {
        return applyRows(s, Collections::reverse);
    }

    public static String expand(String s) {
        List<String> a = new ArrayList<>();
        for (String row : textToRows(s)) {
            a.add(row);
            a.add("");
        }
        return String.join("\n", a);
    }

    public static String flip(String s) {
        final List<String> a = new ArrayList<>();
        List<String> rows = textToRows(s);
        final int nMaxCols = rows.stream().mapToInt(x ->rowToList(x).size()).max().orElseThrow(RuntimeException::new);
        final int nMaxRows = rows.size();
        for (int y = 0; y < nMaxCols ; y++) {
            String[] row = new String[nMaxRows];
            Arrays.fill(row, "");
            for (int x = 0; x < nMaxRows ; x++) {
                try {
                    row[x] = Objects.toString(rowToList(rows.get(x)).get(y), "");
                } catch (IndexOutOfBoundsException e) {
                    // ignore
                }
            }
            a.add(String.join("\t", row));
        }
        return String.join("\n", a);
    }

    public static String zip(String s) {
        final List<String> a = new ArrayList<>();
        for (String row : textToRows(s)) {
            List<String> cols = rowToList(row);
            a.addAll(cols);
        }
        return String.join("\n", a);
    }

    static <T> List<T> toList(Stream<T> stream) {
        return stream.collect(Collectors.toList());
    }

    static List<String> textToRows(String text) {
        return toList(Stream.of(text.split("\r?\n")));
    }

    static List<String> rowToList(String rowTsv) {
        return toList(Stream.of(rowTsv.split("\t")));
    }

    static List<String> rowListToTsv(String rowTsv) {
        return toList(Stream.of(rowTsv.split("\t")));
    }

}
