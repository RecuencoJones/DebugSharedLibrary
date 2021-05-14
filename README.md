# DebugSharedLibrary

Reproducing an error on consistency between Jenkins execution and Test environment with lesfurets testing library.

## `examples/Jenkinsfile` console output

```
2021-05-14 12:06:53  Started by user Recuenco, David (recuedav)
2021-05-14 12:06:55  Obtained examples/Jenkinsfile from git https://github.com/RecuencoJones/DebugSharedLibrary.git
2021-05-14 12:06:55  Running in Durability level: MAX_SURVIVABILITY
2021-05-14 12:06:55  Loading library DebugSharedLibrary@main
2021-05-14 12:06:55  Selected Git installation does not exist. Using Default
2021-05-14 12:06:55  The recommended git tool is: NONE
2021-05-14 12:06:55  No credentials specified
2021-05-14 12:06:55   > git rev-parse --is-inside-work-tree # timeout=10
2021-05-14 12:06:55  Fetching changes from the remote Git repository
2021-05-14 12:06:55   > git config remote.origin.url https://github.com/RecuencoJones/DebugSharedLibrary.git # timeout=10
2021-05-14 12:06:55  Fetching upstream changes from https://github.com/RecuencoJones/DebugSharedLibrary.git
2021-05-14 12:06:55   > git --version # timeout=10
2021-05-14 12:06:55   > git --version # 'git version 2.26.2'
2021-05-14 12:06:55   > git fetch --tags --force --progress -- https://github.com/RecuencoJones/DebugSharedLibrary.git +refs/heads/*:refs/remotes/origin/* # timeout=10
2021-05-14 12:06:56   > git rev-parse origin/main^{commit} # timeout=10
2021-05-14 12:06:56  Checking out Revision 0630ec8450dcd6bfa38cef646cf9eb2cdca9e663 (origin/main)
2021-05-14 12:06:56   > git config core.sparsecheckout # timeout=10
2021-05-14 12:06:56   > git checkout -f 0630ec8450dcd6bfa38cef646cf9eb2cdca9e663 # timeout=10
2021-05-14 12:06:56  Commit message: "INIT repository"
2021-05-14 12:06:56   > git rev-list --no-walk 6e0a0a9a984bedc952c5d96c2d546d61b24eaa56 # timeout=10
2021-05-14 12:06:56  [Pipeline] Start of Pipeline
2021-05-14 12:06:56  [Pipeline] echo
2021-05-14 12:06:56  pipelines@307b6e8f
2021-05-14 12:06:56  [Pipeline] echo
2021-05-14 12:06:56  com.debug.shared.library.MyPipelines@74ed5447
2021-05-14 12:06:57  [Pipeline] echo
2021-05-14 12:06:57  1.2.3
2021-05-14 12:06:57  [Pipeline] echo
2021-05-14 12:06:57  {credentials={git=git-credentials, docker=docker-credentials}, hosts={git=https://github.com, docker=https://hub.docker.com}}
2021-05-14 12:06:57  [Pipeline] properties
2021-05-14 12:06:57  [Pipeline] echo
2021-05-14 12:06:57  Configured job properties
2021-05-14 12:06:57  [Pipeline] End of Pipeline
2021-05-14 12:06:57  Finished: SUCCESS
```

## `./gradlew test --console=plain` output

```
> Task :compileJava NO-SOURCE
> Task :compileGroovy UP-TO-DATE
> Task :processResources NO-SOURCE
> Task :classes UP-TO-DATE
> Task :compileTestJava NO-SOURCE
> Task :compileTestGroovy UP-TO-DATE
> Task :processTestResources NO-SOURCE
> Task :testClasses UP-TO-DATE

> Task :test FAILED
WARNING: An illegal reflective access operation has occurred
WARNING: Illegal reflective access by org.codehaus.groovy.reflection.CachedClass (file:/path/to/.gradle/caches/modules-2/files-2.1/org.codehaus.groovy/groovy-all/2.4.12/760afc568cbd94c09d78f801ce51aed1326710af/groovy-all-2.4.12.jar) to method java.lang.reflect.AnnotatedElement.lambda$getDeclaredAnnotationsByType$0(java.lang.annotation.Annotation,java.lang.annotation.Annotation)
WARNING: Please consider reporting this to the maintainers of org.codehaus.groovy.reflection.CachedClass
WARNING: Use --illegal-access=warn to enable warnings of further illegal reflective access operations
WARNING: All illegal access operations will be denied in a future release

com.debug.shared.library.MyPipelinesTest > it should run example Jenkinsfile successfully FAILED
    groovy.lang.MissingMethodException at MyPipelinesTest.groovy:10

1 test completed, 1 failed

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':test'.
> There were failing tests. See the report at: file:///path/to/project/build/reports/tests/test/index.html

* Try:
Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output. Run with --scan to get full insights.

* Get more help at https://help.gradle.org

BUILD FAILED in 2s
3 actionable tasks: 1 executed, 2 up-to-date
```

## Test standard output

```
Loading shared library DebugSharedLibrary with version <notNeeded>
pipelines@4be867c6
pipelines@4be867c6
1.2.3
[credentials:[git:git-credentials, docker:docker-credentials], hosts:[git:https://github.com, docker:https://hub.docker.com]]
```

## Test error trace

```
groovy.lang.MissingMethodException: No signature of method: pipelines.setDefaultProperties() is applicable for argument types: () values: []
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance0(Native Method)
	at java.base/jdk.internal.reflect.NativeConstructorAccessorImpl.newInstance(NativeConstructorAccessorImpl.java:64)
	at java.base/jdk.internal.reflect.DelegatingConstructorAccessorImpl.newInstance(DelegatingConstructorAccessorImpl.java:45)
	at java.base/java.lang.reflect.Constructor.newInstanceWithCaller(Constructor.java:500)
	at java.base/java.lang.reflect.Constructor.newInstance(Constructor.java:481)
	at org.codehaus.groovy.reflection.CachedConstructor.invoke(CachedConstructor.java:83)
	at org.codehaus.groovy.runtime.callsite.ConstructorSite$ConstructorSiteNoUnwrapNoCoerce.callConstructor(ConstructorSite.java:105)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCallConstructor(CallSiteArray.java:60)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callConstructor(AbstractCallSite.java:235)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callConstructor(AbstractCallSite.java:263)
	at com.lesfurets.jenkins.unit.PipelineTestHelper$_closure4.doCall(PipelineTestHelper.groovy:231)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:64)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:564)
	at org.codehaus.groovy.reflection.CachedMethod.invoke(CachedMethod.java:93)
	at org.codehaus.groovy.runtime.metaclass.ClosureMetaMethod.invoke(ClosureMetaMethod.java:84)
	at groovy.lang.MetaClassImpl.invokeMissingMethod(MetaClassImpl.java:939)
	at groovy.lang.MetaClassImpl.invokeMissingMethod(MetaClassImpl.java:826)
	at groovy.lang.DelegatingMetaClass.invokeMissingMethod(DelegatingMetaClass.java:234)
	at groovy.lang.MetaClass$invokeMissingMethod$0.call(Unknown Source)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:48)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:113)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:141)
	at com.lesfurets.jenkins.unit.PipelineTestHelper$_closure3.doCall(PipelineTestHelper.groovy:192)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:64)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:564)
	at org.codehaus.groovy.reflection.CachedMethod.invoke(CachedMethod.java:93)
	at org.codehaus.groovy.runtime.metaclass.ClosureMetaMethod.invoke(ClosureMetaMethod.java:84)
	at groovy.lang.ExpandoMetaClass.invokeMethod(ExpandoMetaClass.java:1123)
	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1022)
	at org.codehaus.groovy.runtime.callsite.PogoMetaClassSite.call(PogoMetaClassSite.java:42)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:48)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:113)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:117)
	at Jenkinsfile.run(Jenkinsfile:9)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:64)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:564)
	at org.codehaus.groovy.reflection.CachedMethod.invoke(CachedMethod.java:93)
	at groovy.lang.MetaMethod.doMethodInvoke(MetaMethod.java:325)
	at groovy.lang.MetaMethod$doMethodInvoke.call(Unknown Source)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:48)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:113)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:133)
	at com.lesfurets.jenkins.unit.PipelineTestHelper.callMethod(PipelineTestHelper.groovy:205)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:64)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:564)
	at org.codehaus.groovy.reflection.CachedMethod.invoke(CachedMethod.java:93)
	at groovy.lang.MetaMethod.doMethodInvoke(MetaMethod.java:325)
	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1213)
	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1022)
	at org.codehaus.groovy.runtime.callsite.PogoMetaClassSite.callCurrent(PogoMetaClassSite.java:69)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCallCurrent(CallSiteArray.java:52)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callCurrent(AbstractCallSite.java:154)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callCurrent(AbstractCallSite.java:182)
	at com.lesfurets.jenkins.unit.PipelineTestHelper$_closure3.doCall(PipelineTestHelper.groovy:192)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:64)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:564)
	at org.codehaus.groovy.reflection.CachedMethod.invoke(CachedMethod.java:93)
	at org.codehaus.groovy.runtime.metaclass.ClosureMetaMethod.invoke(ClosureMetaMethod.java:84)
	at groovy.lang.ExpandoMetaClass.invokeMethod(ExpandoMetaClass.java:1123)
	at groovy.lang.MetaClassImpl.invokeMethod(MetaClassImpl.java:1022)
	at org.codehaus.groovy.runtime.callsite.PogoMetaClassSite.call(PogoMetaClassSite.java:42)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:48)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:113)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:117)
	at com.lesfurets.jenkins.unit.PipelineTestHelper.runScriptInternal(PipelineTestHelper.groovy:445)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:64)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:564)
	at org.codehaus.groovy.runtime.callsite.PogoMetaMethodSite$PogoCachedMethodSiteNoUnwrapNoCoerce.invoke(PogoMetaMethodSite.java:210)
	at org.codehaus.groovy.runtime.callsite.PogoMetaMethodSite.callCurrent(PogoMetaMethodSite.java:59)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCallCurrent(CallSiteArray.java:52)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callCurrent(AbstractCallSite.java:154)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callCurrent(AbstractCallSite.java:166)
	at com.lesfurets.jenkins.unit.PipelineTestHelper.runScript(PipelineTestHelper.groovy:418)
	at com.lesfurets.jenkins.unit.PipelineTestHelper$runScript$5.call(Unknown Source)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:48)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:113)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:133)
	at com.lesfurets.jenkins.unit.BasePipelineTest.runScript(BasePipelineTest.groovy:340)
	at com.lesfurets.jenkins.unit.BasePipelineTest$runScript.callCurrent(Unknown Source)
	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCallCurrent(CallSiteArray.java:52)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callCurrent(AbstractCallSite.java:154)
	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callCurrent(AbstractCallSite.java:166)
	at com.debug.shared.library.MyPipelinesTest.it should run example Jenkinsfile successfully(MyPipelinesTest.groovy:10)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:64)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:564)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:59)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:56)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:26)
	at org.junit.runners.ParentRunner$3.evaluate(ParentRunner.java:306)
	at org.junit.runners.BlockJUnit4ClassRunner$1.evaluate(BlockJUnit4ClassRunner.java:100)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:366)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:103)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:63)
	at org.junit.runners.ParentRunner$4.run(ParentRunner.java:331)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:79)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:329)
	at org.junit.runners.ParentRunner.access$100(ParentRunner.java:66)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:293)
	at org.junit.runners.ParentRunner$3.evaluate(ParentRunner.java:306)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:413)
	at org.gradle.api.internal.tasks.testing.junit.JUnitTestClassExecutor.runTestClass(JUnitTestClassExecutor.java:110)
	at org.gradle.api.internal.tasks.testing.junit.JUnitTestClassExecutor.execute(JUnitTestClassExecutor.java:58)
	at org.gradle.api.internal.tasks.testing.junit.JUnitTestClassExecutor.execute(JUnitTestClassExecutor.java:38)
	at org.gradle.api.internal.tasks.testing.junit.AbstractJUnitTestClassProcessor.processTestClass(AbstractJUnitTestClassProcessor.java:62)
	at org.gradle.api.internal.tasks.testing.SuiteTestClassProcessor.processTestClass(SuiteTestClassProcessor.java:51)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:64)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:564)
	at org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:36)
	at org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:24)
	at org.gradle.internal.dispatch.ContextClassLoaderDispatch.dispatch(ContextClassLoaderDispatch.java:33)
	at org.gradle.internal.dispatch.ProxyDispatchAdapter$DispatchingInvocationHandler.invoke(ProxyDispatchAdapter.java:94)
	at com.sun.proxy.$Proxy2.processTestClass(Unknown Source)
	at org.gradle.api.internal.tasks.testing.worker.TestWorker.processTestClass(TestWorker.java:121)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:64)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.base/java.lang.reflect.Method.invoke(Method.java:564)
	at org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:36)
	at org.gradle.internal.dispatch.ReflectionDispatch.dispatch(ReflectionDispatch.java:24)
	at org.gradle.internal.remote.internal.hub.MessageHubBackedObjectConnection$DispatchWrapper.dispatch(MessageHubBackedObjectConnection.java:182)
	at org.gradle.internal.remote.internal.hub.MessageHubBackedObjectConnection$DispatchWrapper.dispatch(MessageHubBackedObjectConnection.java:164)
	at org.gradle.internal.remote.internal.hub.MessageHub$Handler.run(MessageHub.java:414)
	at org.gradle.internal.concurrent.ExecutorPolicy$CatchAndRecordFailures.onExecute(ExecutorPolicy.java:64)
	at org.gradle.internal.concurrent.ManagedExecutorImpl$1.run(ManagedExecutorImpl.java:48)
	at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1130)
	at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:630)
	at org.gradle.internal.concurrent.ThreadFactoryImpl$ManagedThreadRunnable.run(ThreadFactoryImpl.java:56)
	at java.base/java.lang.Thread.run(Thread.java:832)
```