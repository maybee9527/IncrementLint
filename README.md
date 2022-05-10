# 介绍
适用于AGP 7.0、lint api 30.0.4的增量lint。

仓库主要演示AGP7.0上 增量lint原理，完整的diff逻辑，并没有写出。

# 运行说明

## 1、发布lint_hook、lint_rules

> .local文件夹下已经发布了一个版本，方便各位能直接运行起来，如果想自己改动代码的，可以运行指令发布到本地
直接在命令行运行 ./gradlew :plugins:publishReleasePublicationToProjectRepository

## 2、修改lint-diff.txt
为了简化代码、方便演示，这里需要手动写入增量文件信息写入到lint-diff.txt,注意需要绝对路径

真正集成的时候，可以参考其他大佬写的GitUtils实现

## 3、运行lint
命令行执行 ./gradlew :app:lintQaDebug 即可
lint报告会在app/build/tlint目录生成

需要注意的是，官方提供的lint任务可缓存，如果再次运行，记得删除 app/build/intermediates/lint_partial_results 文件夹，避免该任务直接跳过。

