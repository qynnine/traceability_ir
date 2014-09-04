package nju;

import nju.type.SimilarityMatrix;
import nju.type.SingleLink;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by niejia on 14-5-18.
 */
public class _ {
    public static List<File> filesList;

    public static void abort(String m) {
        System.err.println(m);
        System.err.flush();
        Thread.dumpStack();
        System.exit(1);
    }

    public static void msg(String m) {
        System.out.println(m);
    }

    @Nullable
    public static String readFile(@NotNull String path) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(path));
            return Charset.forName("UTF-8").decode(ByteBuffer.wrap(encoded)).toString();
        } catch (IOException e) {
            return null;
        }
    }

    public static void writeFile(@NotNull String input, String path) {
        Path outPath = Paths.get(path);
        Charset charset = Charset.forName("UTF-8");

        try (BufferedWriter writer = Files.newBufferedWriter(outPath, charset)) {
            writer.write(input, 0, input.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    // catch file in folder with specific extension
    public static List<File> filterFile(@NotNull String path, String extension) {
        List<File> files = new ArrayList<>();
        final File folder = new File(path);
        for (File file : folder.listFiles()) {
            if (file.getAbsolutePath().endsWith("." + extension)) {
                files.add(file);
            }
        }
        return files;
    }

    public static List<File> filesInFolder(final File folder) {
        filesList = new ArrayList<>();
        filesInFolder(filesList, folder);
        return filesList;
    }

    private static void filesInFolder(List<File> files, File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                filesInFolder(filesList, fileEntry);
            } else {
                files.add(fileEntry);
            }
        }
    }

    public static SimilarityMatrix sort(SimilarityMatrix sims) {
        SimilarityMatrix sorted_sims = new SimilarityMatrix();

        // store sorted sourceIds
        List<String> sorted_ids = new ArrayList<>();
        for (String id : sims.sourceArtifactsIds()) {
            sorted_ids.add(id);
        }
        Collections.sort(sorted_ids);

        // sort sims by A-z order with sourceId, then descending with score
        for (String sourceId : sorted_ids) {
            Map<String, Double> links = sims.getLinksForSourceId(sourceId);
            ArrayList<SingleLink> linksList = new ArrayList<>();
            for (String targetId : links.keySet()) {
                double score = sims.getScoreForLink(sourceId, targetId);
                linksList.add(new SingleLink(sourceId, targetId, score));
            }

            Collections.sort(linksList, Collections.reverseOrder());

            for (SingleLink sg : linksList) {
                sorted_sims.addLink(sg.getSourceArtifactId(), sg.getTargetArtifactId(), sg.getScore());
            }
        }
        return sorted_sims;
    }
}
