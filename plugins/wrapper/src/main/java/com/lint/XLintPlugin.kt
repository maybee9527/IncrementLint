package com.lint

import com.android.build.gradle.internal.lint.AndroidLintTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

open class XLintPlugin : Plugin<Project> {
    private val mainVersion = "com.twl.lintplugin:lint_hook:"+Version.VERSION;

    override fun apply(project: Project) {
        insertLintHookToClasspath(project)
    }

    private fun insertLintHookToClasspath(project: Project) {
        println("insertLintHookToClasspath = ${project.name}")
        val lintClassPath: Configuration =
            project.configurations.getByName(AndroidLintTask.LINT_CLASS_PATH)
        lintClassPath.incoming.beforeResolve {
            println("lint incoming class= " + lintClassPath.dependencies.javaClass)
            lintClassPath.dependencies.add(project.dependencies.create(mainVersion))
        }

        lintClassPath.incoming.afterResolve {
            println("lint incoming path21 = " + lintClassPath.files.map { it.absolutePath })
        }
        //千万不要设置为SortOrder.DEPENDENCY_FIRST，会导致无法加载修改过的Main
//        lintClassPath.resolutionStrategy {
//            it.sortArtifacts(ResolutionStrategy.SortOrder.DEPENDENCY_FIRST)
//        }
    }
}