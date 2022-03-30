Run `test.sh` to run a 20 thread parallel build repeatedly

The build contains 10 kotlin modules and 10 non-kotlin modules to try and
provoke a surefire run on a java project to start before the first kotlin
module run.

The desired order of events for the failure is:

1. a surefire test run clones System.properties
2. a kotlin compile execution sets the idea system properties
3. the surefire test comples and restores the old System.properties
4. kotlin compile plugin attempts to use the properties and fails

# Collected Errors

this machine is:
```
nproc: 8
Linux version 5.15.21-1-lts (linux-lts@archlinux) (gcc (GCC) 11.1.0, GNU ld (GNU Binutils) 2.36.1) #1 SMP Sun, 06 Feb 2022 07:58:26 +0000
java -version
openjdk version "1.8.0_282"
OpenJDK Runtime Environment Corretto-8.282.08.1 (build 1.8.0_282-b08)
OpenJDK 64-Bit Server VM Corretto-8.282.08.1 (build 25.282-b08, mixed mode)
mvn -version
Apache Maven 3.8.4 (9b656c72d54e5bacbed989b64718c159fe39b537)
```

```
[ERROR] Failed to execute goal org.jetbrains.kotlin:kotlin-maven-plugin:1.6.10:compile (compile) on project lib-5: Compilation failure
[ERROR] java.lang.NullPointerException
[ERROR] 	at java.io.Reader.<init>(Reader.java:78)
[ERROR] 	at java.io.InputStreamReader.<init>(InputStreamReader.java:113)
[ERROR] 	at com.intellij.ide.plugins.PluginManagerCore.readBrokenPluginFile(PluginManagerCore.java:249)
[ERROR] 	at com.intellij.ide.plugins.PluginManagerCore.getBrokenPluginVersions(PluginManagerCore.java:241)
[ERROR] 	at com.intellij.ide.plugins.PluginManagerCore.createLoadingResult(PluginManagerCore.java:822)
[ERROR] 	at com.intellij.ide.plugins.DescriptorListLoadingContext.createSingleDescriptorContext(DescriptorListLoadingContext.java:64)
[ERROR] 	at com.intellij.ide.plugins.PluginManagerCore.registerExtensionPointAndExtensions(PluginManagerCore.java:1318)
[ERROR] 	at com.intellij.core.CoreApplicationEnvironment.registerExtensionPointAndExtensions(CoreApplicationEnvironment.java:287)
[ERROR] 	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$Companion.registerApplicationExtensionPointsAndExtensionsFrom(KotlinCoreEnvironment.kt:631)
[ERROR] 	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$Companion.createApplicationEnvironment(KotlinCoreEnvironment.kt:601)
[ERROR] 	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$Companion.getOrCreateApplicationEnvironment(KotlinCoreEnvironment.kt:558)
[ERROR] 	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$Companion.getOrCreateApplicationEnvironmentForProduction(KotlinCoreEnvironment.kt:539)
[ERROR] 	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$Companion.createForProduction(KotlinCoreEnvironment.kt:483)
[ERROR] 	at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.createCoreEnvironment(K2JVMCompiler.kt:227)
[ERROR] 	at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.doExecute(K2JVMCompiler.kt:153)
[ERROR] 	at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.doExecute(K2JVMCompiler.kt:52)
[ERROR] 	at org.jetbrains.kotlin.cli.common.CLICompiler.execImpl(CLICompiler.kt:92)
[ERROR] 	at org.jetbrains.kotlin.cli.common.CLICompiler.execImpl(CLICompiler.kt:44)
[ERROR] 	at org.jetbrains.kotlin.cli.common.CLITool.exec(CLITool.kt:98)
[ERROR] 	at org.jetbrains.kotlin.maven.KotlinCompileMojoBase.execCompiler(KotlinCompileMojoBase.java:228)
[ERROR] 	at org.jetbrains.kotlin.maven.K2JVMCompileMojo.execCompiler(K2JVMCompileMojo.java:237)
[ERROR] 	at org.jetbrains.kotlin.maven.K2JVMCompileMojo.execCompiler(K2JVMCompileMojo.java:55)
[ERROR] 	at org.jetbrains.kotlin.maven.KotlinCompileMojoBase.execute(KotlinCompileMojoBase.java:209)
[ERROR] 	at org.jetbrains.kotlin.maven.K2JVMCompileMojo.execute(K2JVMCompileMojo.java:222)
[ERROR] 	at org.apache.maven.plugin.DefaultBuildPluginManager.executeMojo(DefaultBuildPluginManager.java:137)
[ERROR] 	at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:210)
[ERROR] 	at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:156)
[ERROR] 	at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:148)
[ERROR] 	at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject(LifecycleModuleBuilder.java:117)
[ERROR] 	at org.apache.maven.lifecycle.internal.builder.multithreaded.MultiThreadedBuilder$1.call(MultiThreadedBuilder.java:196)
[ERROR] 	at org.apache.maven.lifecycle.internal.builder.multithreaded.MultiThreadedBuilder$1.call(MultiThreadedBuilder.java:186)
[ERROR] 	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
[ERROR] 	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
[ERROR] 	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
[ERROR] 	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
[ERROR] 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
[ERROR] 	at java.lang.Thread.run(Thread.java:748)
```

```
[ERROR] java.lang.NoClassDefFoundError: Could not initialize class com.intellij.openapi.util.BuildNumber$Holder
[ERROR] 	at com.intellij.openapi.util.BuildNumber.currentVersion(BuildNumber.java:297)
[ERROR] 	at com.intellij.ide.plugins.PluginManagerCore.getBuildNumber(PluginManagerCore.java:876)
[ERROR] 	at com.intellij.ide.plugins.PluginManagerCore.lambda$createLoadingResult$16(PluginManagerCore.java:822)
[ERROR] 	at com.intellij.ide.plugins.DescriptorListLoadingContext.getDefaultVersion(DescriptorListLoadingContext.java:145)
[ERROR] 	at com.intellij.ide.plugins.IdeaPluginDescriptorImpl.readExternal(IdeaPluginDescriptorImpl.java:166)
[ERROR] 	at com.intellij.ide.plugins.PluginDescriptorLoader.loadDescriptorFromJar(PluginDescriptorLoader.java:94)
[ERROR] 	at com.intellij.ide.plugins.PluginManagerCore.registerExtensionPointAndExtensions(PluginManagerCore.java:1325)
[ERROR] 	at com.intellij.core.CoreApplicationEnvironment.registerExtensionPointAndExtensions(CoreApplicationEnvironment.java:287)
[ERROR] 	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$Companion.registerApplicationExtensionPointsAndExtensionsFrom(KotlinCoreEnvironment.kt:631)
[ERROR] 	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$Companion.createApplicationEnvironment(KotlinCoreEnvironment.kt:601)
[ERROR] 	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$Companion.getOrCreateApplicationEnvironment(KotlinCoreEnvironment.kt:558)
[ERROR] 	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$Companion.getOrCreateApplicationEnvironmentForProduction(KotlinCoreEnvironment.kt:539)
[ERROR] 	at org.jetbrains.kotlin.cli.jvm.compiler.KotlinCoreEnvironment$Companion.createForProduction(KotlinCoreEnvironment.kt:483)
[ERROR] 	at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.createCoreEnvironment(K2JVMCompiler.kt:227)
[ERROR] 	at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.doExecute(K2JVMCompiler.kt:153)
[ERROR] 	at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.doExecute(K2JVMCompiler.kt:52)
[ERROR] 	at org.jetbrains.kotlin.cli.common.CLICompiler.execImpl(CLICompiler.kt:92)
[ERROR] 	at org.jetbrains.kotlin.cli.common.CLICompiler.execImpl(CLICompiler.kt:44)
[ERROR] 	at org.jetbrains.kotlin.cli.common.CLITool.exec(CLITool.kt:98)
[ERROR] 	at org.jetbrains.kotlin.maven.KotlinCompileMojoBase.execCompiler(KotlinCompileMojoBase.java:228)
[ERROR] 	at org.jetbrains.kotlin.maven.K2JVMCompileMojo.execCompiler(K2JVMCompileMojo.java:237)
[ERROR] 	at org.jetbrains.kotlin.maven.K2JVMCompileMojo.execCompiler(K2JVMCompileMojo.java:55)
[ERROR] 	at org.jetbrains.kotlin.maven.KotlinCompileMojoBase.execute(KotlinCompileMojoBase.java:209)
[ERROR] 	at org.jetbrains.kotlin.maven.K2JVMCompileMojo.execute(K2JVMCompileMojo.java:222)
[ERROR] 	at org.apache.maven.plugin.DefaultBuildPluginManager.executeMojo(DefaultBuildPluginManager.java:137)
[ERROR] 	at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:210)
[ERROR] 	at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:156)
[ERROR] 	at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:148)
[ERROR] 	at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject(LifecycleModuleBuilder.java:117)
[ERROR] 	at org.apache.maven.lifecycle.internal.builder.multithreaded.MultiThreadedBuilder$1.call(MultiThreadedBuilder.java:196)
[ERROR] 	at org.apache.maven.lifecycle.internal.builder.multithreaded.MultiThreadedBuilder$1.call(MultiThreadedBuilder.java:186)
[ERROR] 	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
[ERROR] 	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
[ERROR] 	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
[ERROR] 	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
[ERROR] 	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
[ERROR] 	at java.lang.Thread.run(Thread.java:748)
[ERROR] 
```
