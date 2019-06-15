# Spring Boot启动流程

在使用Spring Boot搭建项目时，有时需要在项目启动时做一些事情，比如初始化数据库、加载配置文件等等。

Spring Boot为我们提供了以下几种方案：

- ​	实现`ApplicationRunner` 接口或者实现`CommandLineRunner`接口

- ​	利用`Spring`的事件机制

## 1.实现`ApplicationRunner`或`CommandLineRunner`接口

如果需要在Application启动的时候执行一些代码，可以通过实现`SpringBoot`提供的`ApplicationRunner`和`CommandLineRunner`接口来达到目的。

`ApplicationRunner`和`CommandLineRunner`在`SpringApplication.run()`结束之前，在所有的beans加载完成之后执行。用于执行一些初始化操作（如加载缓存、读取配置文件、创建线程池等）。

### 1.1 源码

这两个接口工作方式相同，都只提供一个`run`方法，源码如下：

`ApplicationRunner` 源码：

```java
@FunctionalInterface
public interface ApplicationRunner {

	void run(ApplicationArguments args) throws Exception;
}
```

`CommandLineRunner`源码：

```java
@FunctionalInterface
public interface CommandLineRunner {

	void run(String... args) throws Exception;
}
```

不同的是，`ApplicationRunner`通过`ApplicationArguments`接收启动参数，对原始参数进行了封装。`CommandLineRunner` 通过`String`数组接收启动参数，保留原始参数。

### 1.2 使用方式

我们可以通过实现这两个接口之一，并使用`@Component`注解将实现类注册为`bean`。

 

```java
 /**
  *Application启动时进行一些初始化
  */
  @Component
  public class AppInit implements ApplicationRunner {

  @Override
  public void run(ApplicationArguments args) throws Exception {
      System.out.println("进行一些初始化操作...");
  }
  }
```

### 1.3 执行流程跟踪

接下来，通过`SpringBoot`的源码来看看实现了`ApplicationRunner`或者`CommandLineRunner`接口的类是何时被调用的。

首先，`SpringBoot`程序的入口在`Application`类的`main`函数中：

```java
@SpringBootApplication
public class Application {
    // 入口
    public static void main(String[] args) {
    	SpringApplication.run(Application.class,args);
    }
}    
```

进入`SpringApplication.run(Application.class, args)`，最终调用的是`SpringApplication`对象的`run`方法：

```java
public static ConfigurableApplicationContext run(Class<?>[] primarySources,String[] args) {
	return new SpringApplication(primarySources).run(args);
}
```

在`SpringApplication`对象的`run`方法中，通过`ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);`将参数包装成`ApplicationArguments`对象，最后调用`callRunners(context, applicationArguments);`方法处理`Runner`：

```java
public ConfigurableApplicationContext run(String... args) {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ConfigurableApplicationContext context = null;
		Collection<SpringBootExceptionReporter> exceptionReporters = new ArrayList<>();
		configureHeadlessProperty();
		SpringApplicationRunListeners listeners = getRunListeners(args);
		listeners.starting();
		try {
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(
					args);
			ConfigurableEnvironment environment = prepareEnvironment(listeners,
					applicationArguments);
			configureIgnoreBeanInfo(environment);
			Banner printedBanner = printBanner(environment);
			context = createApplicationContext();
			exceptionReporters = getSpringFactoriesInstances(
					SpringBootExceptionReporter.class,
					new Class[] { ConfigurableApplicationContext.class }, context);
			prepareContext(context, environment, listeners, applicationArguments,
					printedBanner);
			refreshContext(context);
			afterRefresh(context, applicationArguments);
			stopWatch.stop();
			if (this.logStartupInfo) {
				new StartupInfoLogger(this.mainApplicationClass)
						.logStarted(getApplicationLog(), stopWatch);
			}
			listeners.started(context);
			callRunners(context, applicationArguments);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, exceptionReporters, listeners);
			throw new IllegalStateException(ex);
		}

		try {
			listeners.running(context);
		}
		catch (Throwable ex) {
			handleRunFailure(context, ex, exceptionReporters, null);
			throw new IllegalStateException(ex);
		}
		return context;
	}
```

查看`callRunners(context, applicationArguments);`方法的源码：

	private void callRunners(ApplicationContext context, ApplicationArguments args) {
		List<Object> runners = new ArrayList<>();
		runners.addAll(context.getBeansOfType(ApplicationRunner.class).values());
		runners.addAll(context.getBeansOfType(CommandLineRunner.class).values());
		AnnotationAwareOrderComparator.sort(runners);
		for (Object runner : new LinkedHashSet<>(runners)) {
			if (runner instanceof ApplicationRunner) {
				callRunner((ApplicationRunner) runner, args);
			}
			if (runner instanceof CommandLineRunner) {
				callRunner((CommandLineRunner) runner, args);
			}
		}
	}

首先，通过`context.getBeansOfType`获取所有实现了`ApplicationRunner`和`CommandLineRunner`接口的实现类的实例bean，放入runners中。

然后，通过`AnnotationAwareOrderComparator.sort`对所有runner进行排序。

最后在for循环中，根据runner的类型分别调用`callRunner`方法。

```java
private void callRunner(ApplicationRunner runner, ApplicationArguments args) {
	try {
		(runner).run(args);
	}
	catch (Exception ex) {
		throw new IllegalStateException("Failed to execute ApplicationRunner", ex);
	}
}
```
```java
private void callRunner(CommandLineRunner runner, ApplicationArguments args) {
	try {
		(runner).run(args.getSourceArgs());
	}
	catch (Exception ex) {
		throw new IllegalStateException("Failed to execute CommandLineRunner", ex);
	}
}
```

