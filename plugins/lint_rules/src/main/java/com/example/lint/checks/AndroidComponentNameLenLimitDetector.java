package com.example.lint.checks;


import com.android.annotations.NonNull;
import com.android.annotations.Nullable;
import com.android.tools.lint.client.api.JavaEvaluator;
import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.intellij.psi.PsiClass;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UClass;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.visitor.AbstractUastVisitor;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 * AndroidComponentNameLenLimitDetector Detector
 * <p>
 * Android 组件 全类名 长度检测
 * 超过91字符 报错
 * <p>
 * 实际效果：
 * 检测 安卓组件 Application、Activity、Service、BroadcastReceiver、ContentProvider
 * <p>
 */
public class AndroidComponentNameLenLimitDetector extends Detector implements Detector.UastScanner {

    private static final Class<? extends Detector> DETECTOR_CLASS = AndroidComponentNameLenLimitDetector.class;
    private static final EnumSet<Scope> DETECTOR_SCOPE = Scope.JAVA_FILE_SCOPE;

    //唯一标示标识
    private static final String ISSUE_ID = "twl_issue_android_component_name_len_limit";
    //简介
    private static final String ISSUE_DESCRIPTION = "四大组件 全类名字符长度不应大于91";
    //详细解释
    private static final String ISSUE_EXPLANATION = "四大组件 全类名字符长度不应大于91，当前长度=";
    //错误所属的类别（性能、安全、可用性等)
    private static final Category ISSUE_CATEGORY = Category.CORRECTNESS;
    //优先级
    private static final int ISSUE_PRIORITY = 9;
    //Lint 警告级别
    private static final Severity ISSUE_SEVERITY = Severity.ERROR;

    private static final int MAX_LIMIT = 91;


    //将 Issue 与这个类关联起来
    private static final Implementation IMPLEMENTATION = new Implementation(
            DETECTOR_CLASS,
            DETECTOR_SCOPE
    );
    public static final Issue ISSUE = Issue.create(
            ISSUE_ID,
            ISSUE_DESCRIPTION,
            ISSUE_EXPLANATION,
            ISSUE_CATEGORY,
            ISSUE_PRIORITY,
            ISSUE_SEVERITY,
            IMPLEMENTATION
    );

    @Override
    public void visitClass(@NotNull JavaContext context, @NotNull UClass declaration) {
        super.visitClass(context, declaration);
    }

    //返回我们所有感兴趣的类，即返回的类都被会检查
    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        return Collections.<Class<? extends UElement>>singletonList(UClass.class);
    }

    //重写该方法，创建自己的处理器
    @Nullable
    @Override
    public UElementHandler createUastHandler(@NonNull final JavaContext context) {
        return new UElementHandler() {
            @Override
            public void visitClass(@NonNull UClass node) {
                node.accept(new NamingConventionVisitor(context, node));
            }
        };
    }


    public static class NamingConventionVisitor extends AbstractUastVisitor {

        JavaContext context;

        UClass uClass;

        public NamingConventionVisitor(JavaContext context, UClass uClass) {
            this.context = context;
            this.uClass = uClass;
        }

        @Override
        public boolean visitClass(@NonNull UClass node) {
            String fullName = node.getQualifiedName();

            if (fullName!=null && fullName.length() > MAX_LIMIT
                    && isAndroidComponent(node.getJavaPsi(), context.getEvaluator())
            ) {
                context.report(ISSUE, context.getNameLocation(node), ISSUE_EXPLANATION + fullName.length() + "，全类名=" + fullName);
                return true;
            }
            return false;
        }

        private boolean isAndroidComponent(@NonNull PsiClass cls,
                                           @NonNull JavaEvaluator evaluator) {
            return evaluator.extendsClass(cls, CLASS_ACTIVITY, false)
                    || evaluator.extendsClass(cls, CLASS_APPLICATION, false)
                    || evaluator.extendsClass(cls, CLASS_SERVICE, false)
                    || evaluator.extendsClass(cls, CLASS_CONTENTPROVIDER, false)
                    || evaluator.extendsClass(cls, CLASS_BROADCASTRECEIVER, false);
        }


    }

    //来自 com.android.tools:common:26.5.3 com.android.SdkConstants
    private static final String CLASS_APPLICATION = "android.app.Application";
    private static final String CLASS_ACTIVITY = "android.app.Activity";
    private static final String CLASS_SERVICE = "android.app.Service";
    private static final String CLASS_BROADCASTRECEIVER = "android.content.BroadcastReceiver";
    private static final String CLASS_CONTENTPROVIDER = "android.content.ContentProvider";
}
