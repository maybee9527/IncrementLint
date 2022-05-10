package com.twl.plugin.lint.hook;

import com.android.tools.lint.client.api.LintRequest;
import com.android.tools.lint.detector.api.Project;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DiffFileUtils {
    private static String TAG = "DiffFileUtils ";
    private static boolean init = false;
    private static int MAX_DEPTH = 10;
    private static String PATH_DIFF = ".local/lint-diff.txt";
    private static String NAME_SETTINGS_GRADLE = "settings.gradle";

    private final static List<File> list = new ArrayList<>();
    private static long lastModify;
    private static File rootDir;
    private static File diffFile;
    private static File settingsFile;

    public synchronized static void checkInput(File input) {
        if (diffFile != null) {
            if (!diffFile.exists()) {
                InfoLogger.debug("diff file not exists,  just check whole project." + diffFile.getPath());
                list.clear();
                lastModify = 0L;
                return;
            } else if (diffFile.lastModified() == lastModify) {
                InfoLogger.debug("diff file has no change ,diff.size = " + list.size());
                return;
            }
        }
        //reset
        diffFile = null;
        list.clear();
        lastModify = 0L;

        //查找rootDir,深度最多10层
        File settingsFile = new File(input.getParentFile(), NAME_SETTINGS_GRADLE);
        for (int i = 0; i < MAX_DEPTH; i++) {
            if (settingsFile.exists() && settingsFile.isFile()) {
                diffFile = new File(settingsFile.getParentFile(), PATH_DIFF);
                InfoLogger.error("find diffFile =" + diffFile.getParent());
                break;
            } else {
                settingsFile = new File(settingsFile.getParentFile().getParentFile(), NAME_SETTINGS_GRADLE);
            }
        }


        if (diffFile == null) {
            InfoLogger.error("bad news, can't find settings.gradle dir with " + input.getParent());
            return;
        }

        if (!diffFile.exists() || !diffFile.isFile()) {
            InfoLogger.debug("diff file not exists,  just check whole project." + diffFile.getPath());
            return;
        }

        lastModify = diffFile.lastModified();

        try (Stream<String> lines = Files.lines(Paths.get(diffFile.toURI()))) {
            List<File> resultList = lines
                    .map(File::new)
                    .filter(File::exists)
                    .collect(Collectors.toList());
            list.addAll(resultList);
            System.out.println("parse diff file success, size = " + list.size());
        } catch (IOException e) {
            System.out.println("parse diff file error, msg = " + e.getMessage());
        }
    }

    public synchronized static void insertFile(LintRequest lintRequest) {
        if (list.isEmpty()) {
            return;
        }
        Collection<Project> projects = lintRequest.getProjects();
        if (projects == null || projects.isEmpty()) {
            InfoLogger.error("no project to lint");
            return;
        }
        InfoLogger.debug("start insert diff file");
        for (Project project : lintRequest.getProjects()) {
            for (File file : list) {
                project.addFile(file);
            }
        }
    }
}
