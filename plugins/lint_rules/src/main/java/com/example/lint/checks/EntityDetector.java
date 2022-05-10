package com.example.lint.checks;


import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiModifier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UClass;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class EntityDetector extends Detector implements Detector.UastScanner {
    private static final String EXPLANATION = "当前修改的Java文件，需要为它添加serialVersionUID,请参考下面的链接";

    public static final Issue ISSUE =
            Issue.create("twl_entity",
                    "需要添加serialVersionUID",
                    EXPLANATION,
                    Category.SECURITY, 5, Severity.ERROR,
                    new Implementation(EntityDetector.class, Scope.JAVA_FILE_SCOPE));

    @Nullable
    @Override
    public List<String> applicableSuperClasses() {
        return Arrays.asList(Serializable.class.getCanonicalName());
    }

    @Override
    public void visitClass(@NotNull JavaContext context, @NotNull UClass node) {
        if (!node.isInterface()
                && !node.hasModifierProperty(PsiModifier.ABSTRACT)
                && !node.isEnum()
                && (context.getEvaluator().implementsInterface(node, Serializable.class.getCanonicalName(), true))) {

            PsiField field = node.findFieldByName("serialVersionUID", false);
            if (field == null) {
                context.report(
                        ISSUE, node,
                        context.getNameLocation(node), EXPLANATION);
            }
        }
    }
}
