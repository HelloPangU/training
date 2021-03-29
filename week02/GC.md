总结

### 串行GC

Serial GC、ParNewGC

串行 GC 对年轻代使用 mark-copy（标记-复制） 算法，对老年代使用 mark-sweep-compact（标记-清除-整理）算法

```java
java -XX:+PrintGCDetails -Xmx1g -Xms1g -XX:+PrintGCDateStamps -XX:+UseSerialGC  GCLogAnalysis
java -XX:+PrintGCDetails -Xmx1g -Xms1g -XX:+PrintGCDateStamps -XX:+UseParNewGC  GCLogAnalysis
```

串行GC日志

```java
SerialGC:
[GC (Allocation Failure) [DefNew: 279616K->34943K(314560K), 0.0352181 secs] 279616K->85956K(1013632K), 0.0352652 secs] [Times: user=0.01 sys=0.02, real=0.04 secs]
[GC (Allocation Failure) [DefNew: 314548K->34941K(314560K), 0.0471173 secs] 365561K->163651K(1013632K), 0.0471604 secs] [Times: user=0.04 sys=0.01, real=0.04 secs]
```

```java
ParNewGC:
[GC (Allocation Failure) [ParNew: 279616K->34942K(314560K), 0.0222171 secs] 279616K->81779K(1013632K), 0.0222626 secs] [Times: user=0.02 sys=0.03, real=0.02 secs]
[GC (Allocation Failure) [ParNew: 314558K->34944K(314560K), 0.0303910 secs] 361395K->164746K(1013632K), 0.0304368 secs] [Times: user=0.03 sys=0.03, real=0.03 secs]
```

### 并行ＧＣ

ParallelGC、ParallelOldGC 目标增加吞吐量

年轻代和老年代的垃圾回收都会触发 STW 事件，在年轻代使用 标记-复制（mark-copy）算法，在老年代使用 标记-清除-整理（mark-sweep-compact）算法

```java
java -XX:+PrintGCDetails -Xmx1g -Xms1g -XX:+PrintGCDateStamps -XX:+UseParallelGC  -XX:ParallelGCThreads=4 GCLogAnalysis
```

```java
ParallelGC:
[GC (Allocation Failure) [PSYoungGen: 262144K->43517K(305664K)] 262144K->79240K(1005056K), 0.0233802 secs] [Times: user=0.03 sys=0.01, real=0.03 secs]
[GC (Allocation Failure) [PSYoungGen: 305661K->43503K(305664K)] 341384K->150444K(1005056K), 0.0390429 secs] [Times: user=0.01 sys=0.04, real=0.04 secs]
[Full GC (Ergonomics) [PSYoungGen: 39799K->0K(232960K)] [ParOldGen: 625404K->324949K(699392K)] 665203K->324949K(932352K), [Metaspace: 2519K->2519K(1056768K)], 0.0716379 secs] [Times: user=0.10 sys=0.04,real=0.08 secs]
```

ＣＭＳ　ＧＣ

其对年轻代采用并行 STW 方式的 mark-copy (标记-复制)算法，对老年代主要使用并发 mark-sweep (
标记-清除)算法。

CMS GC 的设计目标是避免在老年代垃圾收集时出现长时间的卡顿，主要通过两种手段来达成此目标：
1. 不对老年代进行整理，而是使用空闲列表（free-lists）来管理内存空间的回收。
2. 在 mark-and-sweep （标记-清除） 阶段的大部分工作和应用线程一起并发执行。
也就是说，在这些阶段并没有明显的应用线程暂停。但值得注意的是，它仍然和应用线程争抢CPU 时。
默认情况下，CMS 使用的并发线程数等于 CPU 核心数的 1/4。
如果服务器是多核 CPU，并且主要调优目标是降低 GC 停顿导致的系统延迟，那么使用 CMS 是个很明智
的选择。进行老年代的并发回收时，可能会伴随着多次年轻代的 minor GC

```java
java -XX:+PrintGCDetails -Xmx1g -Xms1g -XX:+PrintGCDateStamps -XX:+UseConcMarkSweepGC -XX:ParallelGCThreads=4 GCLogAnalysis
```

![1616986791717](C:\Users\le.yang\AppData\Roaming\Typora\typora-user-images\1616986791717.png)

```java
CMS GC:
[GC (Allocation Failure) [ParNew: 153344K->17024K(153344K), 0.0277689 secs] 564831K->473539K(1031552K), 0.0278259 secs] [Times: user=0.05 sys=0.00, real=0.03 secs]
[GC (CMS Initial Mark) [1 CMS-initial-mark: 456515K(878208K)] 476338K(1031552K), 0.0003800 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
[CMS-concurrent-mark-start]
[CMS-concurrent-mark: 0.010/0.010 secs] [Times: user=0.01 sys=0.01, real=0.01 secs]
[CMS-concurrent-preclean-start]
[CMS-concurrent-preclean: 0.002/0.002 secs] [Times: user=0.01 sys=0.00, real=0.00 secs]
[CMS-concurrent-abortable-preclean-start]
[GC (Allocation Failure) [ParNew: 153344K->17024K(153344K), 0.0359191 secs] 609859K->515333K(1031552K), 0.0359720 secs] [Times: user=0.03 sys=0.02, real=0.04 secs]
```

Ｇ１　Ｇｃ

Garbage-First,最主要的设计目标是：将 STW 停顿的时间和分布，变成可预期且可配置的

堆不再分成年轻代和老年代，而是划分为多个（通常是2048个）可以存放对象的小块堆区域(smaller heap regions)。每个小块，可能一会被定义成 Eden 区，一会被指定为Survivor区或者Old 区。在逻辑上，所有的 Eden 区和 Survivor 区合起来就是年轻代，所有的 Old 区拼在一起那就是老年代。

G1 的另一项创新是，在并发阶段估算每个小堆块存活对象的总数。构建回收集的原则是： 垃圾最多的小块会被优先收集。这也是 G1 名称的由来

![1616988108626](C:\Users\le.yang\AppData\Roaming\Typora\typora-user-images\1616988108626.png)

```java
java -XX:+PrintGCDetails -Xmx1g -Xms1g -XX:+PrintGCDateStamps -XX:+UseG1GC -XX:MaxGCPauseMillis=50 GCLogAnalysis
```

常用参数配置：

-XX：+UseG1GC：启用 G1 GC；
-XX：G1NewSizePercent：初始年轻代占整个 Java Heap 的大小，默认值为 5%；
-XX：G1MaxNewSizePercent：最大年轻代占整个 Java Heap 的大小，默认值为 60%；
-XX：G1HeapRegionSize：设置每个 Region 的大小，单位 MB，需要为 1、2、4、8、16、32 中的某个值，默认是堆内存的1/2000。如果这个值设置比较大，那么大对象就可以进入 Region 了；
-XX：ConcGCThreads：与 Java 应用一起执行的 GC 线程数量，默认是 Java 线程的 1/4，减少这个参数的数值可能会提升并行回收的效率，提高系统内部吞吐量。如果这个数值过低，参与回收垃圾的线程不足，也会导致并行回收机制耗时加长；
-XX：+InitiatingHeapOccupancyPercent（简称 IHOP）：G1 内部并行回收循环启动的阈值，默认为 Java Heap的 45%。这个可以理解为老年代使用大于等于 45% 的时候，JVM 会启动垃圾回收。这个值非常重要，它决定了在什么时间启动老年代的并行回收；
-XX：G1HeapWastePercent：G1停止回收的最小内存大小，默认是堆大小的 5%。GC 会收集所有的 Region 中的对象，但是如果下降到了 5%，就会停下来不再收集了。就是说，不必每次回收就把所有的垃圾都处理完，可以遗留少量的下次处理，这样也降低了
单次消耗的时间；
-XX：G1MixedGCCountTarget：设置并行循环之后需要有多少个混合 GC 启动，默认值是 8 个。老年代 Regions的回收时间通常比年轻代的收集时间要长一些。所以如果混合收集器比较多，可以允许 G1 延长老年代的收集时间。

-XX：+G1PrintRegionLivenessInfo：这个参数需要和 -XX:+UnlockDiagnosticVMOptions 配合启动，打印 JVM 的调试信息，每个Region 里的对象存活信息。
-XX：G1ReservePercent：G1 为了保留一些空间用于年代之间的提升，默认值是堆空间的 10%。因为大量执行回收的地方在年轻代（存活时间较短），所以如果你的应用里面有比较大的堆内存空间、比较多的大对象存活，这里需要保留一些内存。
-XX：+G1SummarizeRSetStats：这也是一个 VM 的调试信息。如果启用，会在 VM 退出的时候打印出 Rsets 的详细总结信息。如果启用 -XX:G1SummaryRSetStatsPeriod 参数，就会阶段性地打印 Rsets 信息。
-XX：+G1TraceConcRefinement：这个也是一个 VM 的调试信息，如果启用，并行回收阶段的日志就会被详细打印出来。
-XX：+GCTimeRatio：这个参数就是计算花在 Java 应用线程上和花在 GC 线程上的时间比率，默认是 9，跟新生代内存的分配比例一致。这个参数主要的目的是让用户可以控制花在应用上的时间，G1 的计算公式是 100/（1+GCTimeRatio）。这样如果参数设置为
9，则最多 10% 的时间会花在 GC 工作上面。Parallel GC 的默认值是 99，表示 1% 的时间被用在 GC 上面，这是因为 Parallel GC 贯穿整个 GC，而 G1 则根据 Region 来进行划分，不需要全局性扫描整个内存堆。
-XX：+UseStringDeduplication：手动开启 Java String 对象的去重工作，这个是JDK8u20 版本之后新增的参数，主要用于相同String 避免重复申请内存，节约 Region 的使用。
-XX：MaxGCPauseMills：预期 G1 每次执行 GC 操作的暂停时间，单位是毫秒，默认值是 200 毫秒，G1 会尽量保证控制在这个范围
内。

