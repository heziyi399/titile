---
typora-copy-images-to: ..\..\Pictures\typora图片
---



# Spring Boot的优点有：
减少开发，测试时间和努力。
使用JavaConfig有助于避免使用XML。
避免大量的Maven导入和各种版本冲突。
提供意见发展方法。
通过提供默认值快速开始开发。
基于环境的配置 使用这些属性，您可以将您正在使用的环境传递到应用程序：-Dspring.profiles.active = {enviornment}。在加载主应用程序属性文件后，Spring将在（application{environment} .properties）中加载后续的应用程序属性文件。

1）Spring Boot可以建立独立的Spring应用程序；
2）内嵌了如Tomcat，Jetty和Undertow这样的容器，也就是说可以直接跑起来，用不着再做部署工作了；
3）无需再像Spring那样搞一堆繁琐的xml文件的配置；没有web.xml文件。只需添加用@ Configuration注释的类，然后添加用@Bean注释的方法，Spring将自动加载对象并像以前一样对其进行管理。您甚至可以将@Autowired添加到bean方法中，以使Spring自动装入需要的依赖关系中。
4）可以自动配置Spring。SpringBoot将原有的XML配置改为Java配置，将bean注入改为使
用注解注入的方式(@Autowire)，并将多个xml、properties配置浓缩在一个appliaction.yml
配置文件中。
5）提供了一些现有的功能，如量度工具，表单数据验证以及一些外部配置这样的一些第三方功能；
6）整合常用依赖（开发库，例如spring-webmvc、jackson-json、validation-api和tomcat
等），提供的POM可以简化Maven的配置。当我们引入核心依赖时，SpringBoot会自引入其
他依赖。

# 注解





## @Column注解

用来标识实体类中属性与数据表中字段的对应关系

@Column可以标注在属性前或getter方法前

Example 1:  指定字段“tradeNo”交易编号的长度为50，且值不能为null

```java
@Column(name = "tradeNo", length = 50, nullable = false)



private String tradeNo;
```

Example 2:    指定字段“totalAmount”交易金额的精度（长度）为10，小数点位数为2位，且值不能为null

```java
@Column(name = "totalAmount", precision = 10, scale = 2, nullable = false)
private BigDecimal totalAmount;
```






 `@ConfigurationProperties`的基本用法非常简单:我们为每个要捕获的外部属性提供一个带有字段的类。请注意以下几点:

- 前缀定义了哪些外部属性将绑定到类的字段上
- 根据 Spring Boot 宽松的绑定规则，类的属性名称必须与外部属性的名称匹配
- 我们可以简单地用一个值初始化一个字段来定义一个默认值
- 类本身可以是包私有的
- 类的字段必须有公共 setter 方法

对于 Spring Boot，创建一个 MailModuleProperties 类型的 bean，我们可以通过下面几种方式将其添加到应用上下文中

首先，我们可以通过添加 @Component 注解让 Component Scan 扫描到
![img](http://rgyb.sunluomeng.top/%E5%85%AC%E4%BC%97%E8%B4%A6%E5%8F%B7%E6%96%87%E7%AB%A0/%E5%BA%94%E7%94%A8%E7%B1%BB%E6%96%87%E7%AB%A0/_image/2019-07-23/2019-07-24-16-27-33%402x.png)

很显然，只有当类所在的包被 Spring `@ComponentScan` 注解扫描到才会生效，默认情况下，该注解会扫描在主应用类下的所有包结构

我们也可以通过 Spring 的 Java Configuration 特性实现同样的效果:



只要 MailModuleConfiguration 类被 Spring Boot 应用扫描到，我们就可以在应用上下文中访问 MailModuleProperties bean

我们还可以使用 `@EnableConfigurationProperties` 注解让我们的类被 Spring Boot 所知道，在该注解中其实是用了`@Import(EnableConfigurationPropertiesImportSelector.class)` 实现，



## @PropertySource@ImportResource

@PropertySource是加载指定的配置文件。

@PropertySource("classpath:person.properties")

@ConfigurationProperties(prefix = "person")

@Component

@Validated

public class Person {

}

![image-20220331184244620](C:\Users\heziyi6\Pictures\typora图片\image-20220331184244620.png)

@ConfigurationProperties注解默认只能读取系统全局的配置文件，要加载非全局的配置，需要使用@PropertySource加载文件

@ImportResource的作用是导入Spring的配置文件，让配置文件里面的内容生效。

Spring Boot里面没有Spring的配置文件，我们自己编写的配置文件，也不能自动识别；
想让Spring的配置文件生效，加载进来；@ImportResource标注在一个配置类上

在主配置类上使用@ImportResource加载Spring的配置文件。

![image-20220331184339356](C:\Users\heziyi6\Pictures\typora图片\image-20220331184339356.png)

## @Scheduled

使用 @Scheduled 非常容易，直接创建一个 Spring Boot 项目，并且添加 web 依赖 spring-bootstarter-web ，项目创建成功后，添加 @EnableScheduling 注解，开启定时任务



```java
@SpringBootApplication
@EnableScheduling
public class ScheduledApplication {
public static void main(String[] args) {
SpringApplication.run(ScheduledApplication.class, args);
}
}
```

接下来配置定时任务：

```java
@Scheduled(fixedRate = 2000)
public void fixedRate() {
System.out.println("fixedRate>>>"+new Date());
}
@Scheduled(fixedDelay = 2000)
public void fixedDelay() {
System.out.println("fixedDelay>>>"+new Date());
}
@Scheduled(initialDelay = 2000,fixedDelay = 2000)
public void initialDelay() {
System.out.println("initialDelay>>>"+new Date());
}
```

1. 首先使用 @Scheduled 注解开启一个定时任务。
2. fixedRate 表示任务执行之间的时间间隔，具体是指两次任务的开始时间间隔，即第二次任务开始时，第一次任务可能还没结束。
3. fixedDelay 表示任务执行之间的时间间隔，具体是指本次任务结束到下次任务开始之间的时间间隔
4. initialDelay表示首次任务启动的延迟时间
5. 所有时间单位都是毫秒









# 什么是嵌⼊式服务器？

我们为什么要使⽤嵌⼊式服务器呢?

思考⼀下在你的虚拟机上部署应⽤程序需要些什么。
第⼀步：安装 J a v a
第⼆步：安装 W e b 或者是应⽤程序的服务器（ To m a t / W b e s p h e r e / W e b l o g i c 等等）

第三步：部署应⽤程序 w a r 包
如果我们想简化这些步骤，应该如何做呢？
让我们来思考如何使服务器成为应⽤程序的⼀部分？
你只需要⼀个安装了 J a v a 的虚拟机，就可以直接在上⾯部署应⽤程序了，
这个想法是嵌⼊式服务器的起源。
当我们创建⼀个可以部署的应⽤程序的时候，我们将会把服务器（ 例如，t o m c a t）嵌⼊到可部署的服务器中。
例如，对于⼀个 S p r i n g B o o t 应⽤程序来说，你可以⽣成⼀个包含 E m b e d d e d To m c a t 的应⽤程序 j a r 。你就可
以想运⾏正常 J a v a 应⽤程序⼀样来运⾏ web 应⽤程序了。
嵌⼊式服务器就是我们的可执⾏单元包含服务器的⼆进制⽂件（ 例如，t o m c a t . j a r）。

# SpringBoot-启动流程

详细：[SpringBoot-启动流程 - 楠予 - 博客园 (cnblogs.com)](https://www.cnblogs.com/Narule/p/14253754.html)

**主要流程如下**

0.启动main方法开始

1.**初始化配置**：通过类加载器，（loadFactories）读取classpath下所有的spring.factories配置文件，创建一些初始配置对象；通知监听者应用程序启动开始，创建环境对象environment，用于读取环境配置 如 application.yml

2.**创建应用程序上下文**-createApplicationContext，创建 bean工厂对象

3.**刷新上下文（启动核心）**
3.1 配置工厂对象，包括上下文类加载器，对象发布处理器，beanFactoryPostProcessor
3.2 注册并实例化bean工厂发布处理器，并且调用这些处理器，对包扫描解析(主要是class文件)
3.3 注册并实例化bean发布处理器 beanPostProcessor
3.4 初始化一些与上下文有特别关系的bean对象（创建tomcat服务器）
3.5 实例化所有bean工厂缓存的bean对象（剩下的）
3.6 发布通知-通知上下文刷新完成（启动tomcat服务器）

4.**通知监听者-启动程序完成**

启动中，大部分对象都是BeanFactory对象通过反射创建

SpringBoot的启动解析代码过多，下文是整体流程的部分主要代码

启动[#](https://www.cnblogs.com/Narule/p/14253754.html#1458309436)

启动程序：

```java
Copyimport org.springframework.boot.SpringApplication;//启动类
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication //启动必要注解
public class YourApplication {
	//运行main方法启动springboot
	public static void main(String[] args) {
		SpringApplication.run(YourApplication.class, args);//启动类静态run方法
	}
    
}
```

启动类[#](https://www.cnblogs.com/Narule/p/14253754.html#108174239)

`org.springframework.boot.SpringApplication` 包含主流程方法

启动类在运行静态run方法的时候，是先创建一个SpringApplication对象，再运行对象的run方法，工厂初始配置在构造函数中完成，run方法定义运行总体流程

```java
Copy// 静态方法 org.springframework.boot.SpringApplication.run(Class<?>[], String[])
public static ConfigurableApplicationContext run(Class<?>[] primarySources, String[] args) {
    return new SpringApplication(primarySources).run(args);
}

// 构造方法
public SpringApplication(ResourceLoader resourceLoader, Class<?>... primarySources) {
    //.......... 
    //// 1.(loadFactories)读取classpath下所有的spring.factories配置文件 ////
    // 配置应用程序启动前的初始化对象
    setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class)); 
    // 配置应用程序启动前的监听器
    setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));
    this.mainApplicationClass = deduceMainApplicationClass();
}

// 对象run方法 开始启动程序
public ConfigurableApplicationContext run(String... args) {
    //......
    // 通知监听者启动开始
    listeners.starting(); 
    try {
        // 创建应用程序环境 配置文件在此处读取(application.properties application.yml)
        ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
        //// 2.创建应用程序上下文...此处创建了beanfactory ////
        context = createApplicationContext();
        //// 3.刷新上下文（spring启动核心） ////
        refreshContext(context);

        //// 4.启动完成通知...... ////
        listeners.started(context);
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

## 初始化配置

外部约定配置文件加载顺序：
springboot 启动还会扫描以下位置的application.properties或者application.yml文件作为Spring boot的默认配置文件

**springboot启动应用程序之前，会创建一些初始化对象和监听器**

这个操作在构造方法中完成，根据配置文件，创建`ApplicationContextInitializer.class`,`ApplicationListener.class`两个接口的实现类，至于具体创建那些类对象，根据下面的方法逻辑去做

```
org.springframework.boot.SpringApplication.getSpringFactoriesInstances()` ->
`org.springframework.core.io.support.SpringFactoriesLoader.loadFactoryNames()` ->
`org.springframework.core.io.support.SpringFactoriesLoader.loadSpringFactories()`->
`createSpringFactoriesInstances()
Copy//构造方法中的初始化对象创建
setInitializers((Collection) getSpringFactoriesInstances(ApplicationContextInitializer.class)); 
setListeners((Collection) getSpringFactoriesInstances(ApplicationListener.class));

//看一下getSpringFactoriesInstances方法
private <T> Collection<T> getSpringFactoriesInstances(Class<T> type) {
    return getSpringFactoriesInstances(type, new Class<?>[] {});
}

private <T> Collection<T> getSpringFactoriesInstances(Class<T> type, Class<?>[] parameterTypes, Object... args) {
    ClassLoader classLoader = getClassLoader();
    // 获取初始化类的类名
    Set<String> names = new LinkedHashSet<>(SpringFactoriesLoader.loadFactoryNames(type, classLoader));
    // 通过这些类名实例化对象
    List<T> instances = createSpringFactoriesInstances(type, parameterTypes, classLoader, args, names);
    AnnotationAwareOrderComparator.sort(instances);
    return instances;
}

// 读取配置方法
// 更详深层的代码在org.springframework.core.io.support.SpringFactoriesLoader.loadSpringFactories(ClassLoader)
public static List<String> loadFactoryNames(Class<?> factoryType, @Nullable ClassLoader classLoader) {
    String factoryTypeName = factoryType.getName();
    return loadSpringFactories(classLoader).getOrDefault(factoryTypeName, Collections.emptyList());   
}
// loadSpringFactories(classLoader)读取运行环境中所有META-INF/spring.factories配置
```

通过上面的方法，

spring-boot-2.2.8.RELEASE.jar/META-INF/spring.factories的文件中是这样，

```F#
Copy# Application Context Initializers
org.springframework.context.ApplicationContextInitializer=\
org.springframework.boot.context.ConfigurationWarningsApplicationContextInitializer,\
org.springframework.boot.context.ContextIdApplicationContextInitializer,\
org.springframework.boot.context.config.DelegatingApplicationContextInitializer,\
org.springframework.boot.rsocket.context.RSocketPortInfoApplicationContextInitializer,\
org.springframework.boot.web.context.ServerPortInfoApplicationContextInitializer
```

如果只读取这一个文件，`loadFactoryNames(ApplicationContextInitializer.class,classLoader)`读取返回的就是下面的数组:

```java
Copy[org.springframework.context.ApplicationContextInitializer,
 org.springframework.boot.context.ConfigurationWarningsApplicationContextInitializer,
 org.springframework.boot.context.ContextIdApplicationContextInitializer,
 org.springframework.boot.context.config.DelegatingApplicationContextInitializer,
 org.springframework.boot.web.context.ServerPortInfoApplicationContextInitializer]
```

通过`Class.forName(className)`获取这些类的Class，最后反射`newInstance`创建这些对象

**创建好这些对象后，启动监听器**

```java
Copylisteners.starting();  // 这里也是一些调用操作
```

**读取application配置**

监听器启动之后，会读取application.properties 或者 application.yml文件

```java
CopyConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments); //此处application.properties配置文件会被读取
```

## 创建应用上下文

初始化和配置好后，开始创建应用程序上下文，createApplicationContext ，关键的工厂BeanFactory就是此处创建，具体逻辑如下

```java
Copy// 创建应用程序上下文
context = createApplicationContext();

protected ConfigurableApplicationContext createApplicationContext() {
    // 上下文创建的判断逻辑
    Class<?> contextClass = this.applicationContextClass;
    if (contextClass == null) {
        try {
            switch (this.webApplicationType) {
                case SERVLET:
                    contextClass = Class.forName(DEFAULT_SERVLET_WEB_CONTEXT_CLASS);
                    break;
                case REACTIVE:
                    contextClass = Class.forName(DEFAULT_REACTIVE_WEB_CONTEXT_CLASS);
                    break;
                default:
                    contextClass = Class.forName(DEFAULT_CONTEXT_CLASS);
            }
        }
        catch (ClassNotFoundException ex) {
            throw new IllegalStateException(
                "Unable create a default ApplicationContext, please specify an ApplicationContextClass", ex);
        }
    }
    return (ConfigurableApplicationContext) BeanUtils.instantiateClass(contextClass);
}

public static final String DEFAULT_SERVLET_WEB_CONTEXT_CLASS = "org.springframework.boot."
			+ "web.servlet.context.AnnotationConfigServletWebServerApplicationContext";
// 默认是创建这个类
```

这里通过this.webApplicationType判断创建具体的应用上下文，也是反射创建对象，默认创建的是`org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext`对象，看一下这个类的基本信息

```java
Copypublic class AnnotationConfigServletWebServerApplicationContext extends ServletWebServerApplicationContext
		implements AnnotationConfigRegistry {
    // 构造方法
	public AnnotationConfigServletWebServerApplicationContext() {
		this.reader = new AnnotatedBeanDefinitionReader(this);
		this.scanner = new ClassPathBeanDefinitionScanner(this);
	}
}
```

**创建工厂对象**

此类实现了很多接口，其中一个超父类是`org.springframework.context.support.GenericApplicationContext`
jvm机制，创建对象的时候，先运行父类的构造方法，所以创建了beanFactory

```java
Copy// 超级父类 GenericApplicationContext的构造方法
public GenericApplicationContext() {
    this.beanFactory = new DefaultListableBeanFactory();//创建工厂对象
}
```

## 刷新应用上下文

创建好上下文之后，开始刷新上下文，这里做了很多

工厂配置，bean处理器配置，类的扫描，解析，bean定义，bean类信息缓存，服务器创建，bean实例化，动态代理对象的创建等，

**spring中注册bean信息和实例化bean是两件事情。**

注册bean信息不是创建bean对象，是解析bean类获取详细信息，会创建BeanDefinition对象，携带bean类的字节码和方法等信息，把BeanDefinition对象注册保存到工厂BeanDefinitionMap中。

工厂实例化bean时直接BeanDefinitionMap.get(beanName) 获取bean的字节码信息，通过反射创建对象，然后将bean对象保存到singletonObjects中。

```java
CopyrefreshContext(context); //刷新上下文
```

默认实际对应的是`org.springframework.context.support.AbstractApplicationContext`类的`refresh()`方法

```java
Copy@Override
public void refresh() throws BeansException, IllegalStateException {
    synchronized (this.startupShutdownMonitor) {
        //......
        // 3.1配置工厂对象
        prepareBeanFactory(beanFactory);
        try {       
            postProcessBeanFactory(beanFactory);
            
            // 3.2注册并实例化bean工厂处理器,并调用他们
            invokeBeanFactoryPostProcessors(beanFactory);
            
            // 3.3注册并实例化bean处理器
            registerBeanPostProcessors(beanFactory);
            
            // 3.4 初始化一些与上下文有特别关系的bean对象（创建tomcat）
            onRefresh();
            
            // 3.5 实例化所有bean工厂缓存的bean对象（剩下的）.
            finishBeanFactoryInitialization(beanFactory);
            
            // 3.6 发布通知-通知上下文刷新完成（包括启动tomcat）
            finishRefresh();
        }
        catch (BeansException ex) {// ......Propagate exception to caller.
            throw ex;
        }
        finally {// ......
            resetCommonCaches();
        }
    }
}
```

配置工厂对象，包括上下文类加载器，bean工厂发布处理器[#](https://www.cnblogs.com/Narule/p/14253754.html#3514053100)

工厂创建好后，首先配置的是类加载器，然后是一些对象发布处理器（拦截器）

```java
Copy//// 3.1配置工厂对象 
prepareBeanFactory(beanFactory);

protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {
    // 设置类加载器
    beanFactory.setBeanClassLoader(getClassLoader());
    beanFactory.setBeanExpressionResolver(new StandardBeanExpressionResolver(beanFactory.getBeanClassLoader()));
    beanFactory.addPropertyEditorRegistrar(new ResourceEditorRegistrar(this, getEnvironment()));

    // 添加BeanPostProcessor
    beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));
    beanFactory.ignoreDependencyInterface(EnvironmentAware.class);
    beanFactory.ignoreDependencyInterface(EmbeddedValueResolverAware.class);
	// ......
    }
}
```

注册并实例化bean工厂发布处理器,并调用他们[#](https://www.cnblogs.com/Narule/p/14253754.html#2561003777)

过程主要是工厂发布处理器的创建和调用，逻辑较多

```java
Copy//// 3.2注册并实例化bean工厂处理器,并调用他们
invokeBeanFactoryPostProcessors(beanFactory);

protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
    PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors());
    // ......
}

// PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors
public static void invokeBeanFactoryPostProcessors(
			ConfigurableListableBeanFactory beanFactory, List<BeanFactoryPostProcessor> beanFactoryPostProcessors) {
        postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
        for (String ppName : postProcessorNames) {
            if (!processedBeans.contains(ppName) && beanFactory.isTypeMatch(ppName, Ordered.class)) {
                // 创建BeanDefinitionRegistryPostProcessor处理器
                currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
                processedBeans.add(ppName);
            }
        }
    	// 调用这些处理器
        invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
    	// ...

}

// 循环调用
private static void invokeBeanDefinitionRegistryPostProcessors(
			Collection<? extends BeanDefinitionRegistryPostProcessor> postProcessors, BeanDefinitionRegistry registry) {
    for (BeanDefinitionRegistryPostProcessor postProcessor : postProcessors) {
        postProcessor.postProcessBeanDefinitionRegistry(registry);
    }
}
```

BeanDefinitionRegistryPostProcessor的子类对象在此处创建并调`postProcessBeanDefinitionRegistry`方法。

其中`org.springframework.context.annotation.ConfigurationClassPostProcessor`就是BeanDefinitionRegistryPostProcessor的子类，是一个spring的类解析器，扫描包下所有的类，解析出bean类，注册到bean工厂由此类主要参与，其中有不少递归

注册并实例化bean发布处理器[#](https://www.cnblogs.com/Narule/p/14253754.html#3942679693)

```java
Copy//// 3.3注册并实例化bean处理器
registerBeanPostProcessors(beanFactory);
```

BeanFactoryPostProcessors 和 BeanPostProcessors是有区别的

BeanFactoryPostProcessors 是工厂发布处理器，定义什么是bean，知道哪些是bean类，解析class文件，包括注解解析，成员对象依赖解析等；BeanPostProcessors主要在BeanFactoryPostProcessors调用完之后工作

一般在bean对像的创建之前或之后，BeanFactory调用这些bean处理器拦截处理，Spring代理对象的创建也是通过beanPostProcessor处理器来实现

bean发布处理器生产AOP代理对象[#](https://www.cnblogs.com/Narule/p/14253754.html#76517631)

AnnotationAwareAspectJAutoProxyCreator实现了BeanPostProcessors，在bean被工厂创建之后，BeanFactory调用拦截器的postProcessAfterInitialization做拦截处理。此拦截器处理器实际执行的是父类`org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator`的方法

比如一个UserServiceImp类有@service注解，并且有切点Aspectj注解增强方法，bean工厂创建userServiceImp后，代理拦截器检测到AOP相关注解，会创建动态代理对象userServiceImp$$EnhancerBySpringCGLIB并返代理对象，而不是返回userServiceImp

Spring工厂部分bean创建拦截代码逻辑

```java
Copy// org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(String, Object, RootBeanDefinition)
// bean初始化
protected Object initializeBean(String beanName, Object bean, @Nullable RootBeanDefinition mbd) {
    invokeAwareMethods(beanName, bean);
    Object wrappedBean = bean;
    if (mbd == null || !mbd.isSynthetic()) {
        // 初始化之前，拦截
        wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
    }
    invokeInitMethods(beanName, wrappedBean, mbd);
    if (mbd == null || !mbd.isSynthetic()) {
        // 初始化之后拦截
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
    }
    return wrappedBean;
}

@Override
public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName)
    throws BeansException {
    Object result = existingBean;
    for (BeanPostProcessor processor : getBeanPostProcessors()) {
        // 循环bean发布处理器调用postProcessAfterInitialization方法  
        Object current = processor.postProcessAfterInitialization(result, beanName);
        if (current == null) {
            return result;
        }
        result = current;
    }
    return result;
}
```

AbstractAutoProxyCreator在此循环中被调用，比如在userServiceImp服务类上有事务注解@Transactional，一般就会被拦截生成代理对象，添加额外的处理事务的功能代码，返回增强的代理对象

初始化一些与上下文有特别关系的bean对象[#](https://www.cnblogs.com/Narule/p/14253754.html#2746572497)

默认tomcat服务器的创建就是此方法完成，此处定义特别的bean创建，一般是服务器有关或个性化对象，

```java
Copy//// 3.4 初始化一些与上下文有特别关系的bean对象
onRefresh();

// org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext
// 子类context重写
@Override
protected void onRefresh() {
    super.onRefresh();
    try {
        createWebServer(); //创建服务器
    }
    catch (Throwable ex) {
        throw new ApplicationContextException("Unable to start web server", ex);
    }
}

private void createWebServer() {
    WebServer webServer = this.webServer;
    ServletContext servletContext = getServletContext();
    if (webServer == null && servletContext == null) {
        ServletWebServerFactory factory = getWebServerFactory();
        this.webServer = factory.getWebServer(getSelfInitializer()); 
        // 默认创建tomcat服务器
        // org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory.getWebServer()
    }
    // ......
    initPropertySources();
}
```

实例化所有bean工厂缓存的bean对象[#](https://www.cnblogs.com/Narule/p/14253754.html#2536500090)

服务器启动后，创建spring工厂里面缓存的bean信息（没有被创建的单例）

```java
Copy//// 3.5 实例化所有bean工厂缓存的bean对象（剩下的）.
finishBeanFactoryInitialization(beanFactory);

protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
    // ......
    // Instantiate all remaining (non-lazy-init) singletons.
    beanFactory.preInstantiateSingletons();
}

// 子类org.springframework.beans.factory.support.DefaultListableBeanFactory实现方法，完成剩下的单例bean对象创建
@Override
public void preInstantiateSingletons() throws BeansException {
    List<String> beanNames = new ArrayList<>(this.beanDefinitionNames);
    for (String beanName : beanNames) {
        RootBeanDefinition bd = getMergedLocalBeanDefinition(beanName);
        if (!bd.isAbstract() && bd.isSingleton() && !bd.isLazyInit()) {
                getBean(beanName); //创建还没有实例化的bean对象
        }
    }
}
```

发布通知-通知上下文刷新完成[#](https://www.cnblogs.com/Narule/p/14253754.html#3057980703)

上下文初始化完成之后，启动tomcat服务器

```java
CopyfinishRefresh();

// super.finishRefresh
protected void finishRefresh() {
    // ...... 发布刷行完成事件
    // Publish the final event.
    publishEvent(new ContextRefreshedEvent(this));
}

// org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.finishRefresh()
@Override
protected void finishRefresh() {
    super.finishRefresh();
    WebServer webServer = startWebServer();// 启动服务器
    if (webServer != null) {
        publishEvent(new ServletWebServerInitializedEvent(webServer, this));
    }
}
```

**通知监听者-启动程序完成**[#](https://www.cnblogs.com/Narule/p/14253754.html#786624579)

发布通知监听器启动完成，监听器会根据事件类型做个性化操作

```java
Copylisteners.started(context);
listeners.running(context);

void started(ConfigurableApplicationContext context) {
    for (SpringApplicationRunListener listener : this.listeners) {
        listener.started(context);
    }
}

void running(ConfigurableApplicationContext context) {
    for (SpringApplicationRunListener listener : this.listeners) {
        listener.running(context);
    }
}

@Override
public void started(ConfigurableApplicationContext context) {
    context.publishEvent(new ApplicationStartedEvent(this.application, this.args, context));
}

@Override
public void running(ConfigurableApplicationContext context) {
    context.publishEvent(new ApplicationReadyEvent(this.application, this.args, context));
}
```

@Configuration(proxyBeanMethods = false)
标记了@Configuration Spring底层会给配置创建cglib动态代理。 作用：就是防止每次调用本类的Bean方法而重新创建对象，Bean是默认单例的

@EnableConfigurationProperties(ServerProperties.class)
启用可以在配置类设置的属性 对应的类
@xxxConditional根据当前不同的条件判断，决定这个配置类是否生效？

x

# @JsonComponent

@JsonComponent 是Spring boot的核心注解，使用@JsonComponent 之后就不需要手动将Jackson的序列化和反序列化手动加入ObjectMapper了。使用这个注解就够了。

是@Component提供JsonSerializer 、 JsonDeserializer或KeyDeserializer实现，以便在使用JsonComponentModule时JsonComponentModule Jackson 注册。 可用于直接注释实现或包含它们作为内部类的类。 例如：


```java
 @JsonComponent
 public class CustomerJsonComponent {
 public static class Serializer extends JsonSerializer<Customer> {

     // ...

 }

 public static class Deserializer extends JsonDeserializer<Customer> {

     // ...

 }
```

使用这个注解日期格式化：

```java
/**
 * 全局日期格式化
 */
@JsonComponent
public class DateFormatConfig {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 日期格式化
     */
    public static class DateJsonSerializer extends JsonSerializer<Date> {
        @Override
        public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(dateFormat.format(date));
        }
    }

    /**
     * 解析日期字符串
     */
    public static class DateJsonDeserializer extends JsonDeserializer<Date> {
        @Override
        public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
            try {
                return dateFormat.parse(jsonParser.getText());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
```

例二：

```java
@JsonComponent
  @NoArgsConstructor
  private class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider sp) throws IOException{
      gen.writeString(value.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
  }
}
```







# 多环境配置

在Spring Boot中，多环境配置的文件名需要满足application-{profile}.properties的格式，其中{profile}对应你的环境标识，

 application-dev.properties：开发环境。 

application-test.properties：测试环境。 

application-prod.properties：生产环境。 

至于具体哪个配置文件会被加载，需要在application.properties文件中通过spring.profiles.active属性来设置，其值对应配置文件中的{profile}值。如spring.profiles.active=test就会加载application-test.properties配置文件内容。 下面，以不同环境配置不同的服务端口为例，进行样例实验。 针对各环境新建不同的配置文件application-dev.properties、application-test.properties、application-prod.properties。 在这三个文件中均设置不同的server.port属性，例如，dev环境设置为1111，test环境设置为2222，prod环境设置为3333。 application.properties中设置spring.profiles.active=dev，意为默认以dev环境设置。 测试不同配置的加载。 执行java-jar xxx.jar，可以观察到服务端口被设置为1111，也就是默认的开发环境（dev）。 

执行java-jar xxx.jar--spring.profiles.active=test，可以观察到服务端口被设置为2222，也就是测试环境的配置（test）。 

执行java-jar xxx.jar--spring.profiles.active=prod，可以观察到服务端口被设置为3333，也就是生产环境的配置（prod）。 

按照上面的实验，可以如下总结多环境的配置思路： 在application.properties中配置通用内容，并设置sprin0g.profiles.active=dev，以开发环境为默认配置。 在application-{profile}.properties中配置各个环境不同的内容。 通过命令行方式去激活不同环境的配置。

 加载顺序 

1）在命令行中传入的参数。

2）SPRING_APPLICATION_JSON中的属性。SPRING_APPLICATION_JSON是以JSON格式配置在系统环境变量中的内容。 

3）java：comp/env中的JNDI属性。

4）Java的系统属性，可以通过System.getProperties（）获得的内容。

5）操作系统的环境变量。

6）通过random.*配置的随机属性。

7）位于当前应用jar包之外，针对不同{profile}环境的配置文件内容，例如application-{profile}.properties或是YAML定义的配置文件。

8）位于当前应用jar包之内，针对不同{profile}环境的配置文件内容，例如application-{profile}.properties或是YAML定义的配置文件。

9）位于当前应用jar包之外的ap plication.properties和YAML配置内容。

10）位于当前应用jar包之内的application.properties和YAML配置内容。

11） 在@Configuration注解修改的类中，通过@PropertySource注解定义的属性。

12） 应用默认属性，使用SpringApplication.setDefaultProperties定义的内容。







# addResourceHandlers()

```java
//对静态资源的配置
@Override
public void addResourceHandlers(ResourceHandlerRegistry registry) {

   String os = System.getProperty("os.name");

   if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
registry.addResourceHandler("/smallapple/**")
            // /apple/**表示在磁盘apple目录下的所有资源会被解析为以下的路径
.addResourceLocations("file:G:/itemsource/smallapple/") //媒体资源
.addResourceLocations("classpath:/META-INF/resources/");  //swagger2页面
} else {  //linux 和mac
registry.addResourceHandler("/smallapple/**")
            .addResourceLocations("file:/resources/smallapple/")   //媒体资源
.addResourceLocations("classpath:/META-INF/resources/");  //swagger2页面;
}
}
// 如果访问路径是addResourceHandler中的filepath 这个路径 那么就 映射到访问本地的addResourceLocations 的参数的这个路径上，
```



# 如何理解springboot中的starter'?

　　使用spring-+springmvc框架进行开发的时候，如果需要引入mybatis框架，那么需要在xml中定义需要的bean对象，这个过程很明显是很麻烦的，如果需要引入额外的其他组件，那么也需要进行复杂的配置，因此在springboot中引入了starter
　　starter就是一个jar包，写一个@Configuration的配置类，将这些bean定义在其中，**然后在starter包的META-INF/spring.factoriest中写入配置类，那么springboot程序在启动的时候就会按照约定来加载该配置类**，开发人员只需要将相应的starter包依赖进应用中，进行相关的属性配置，就可以进行代码开发，而不需要单独进行bean对象的配置 

# 给容器注册组件

![image-20220216045314995](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220216045314995.png)

![image-20220216045538061](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220216045538061.png)

除此之外，还可以用importselector接口：

![image-20220216045742335](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220216045742335.png)

方法的返回值是要导入到容器中的组件全类名

然后在@Import(MyImport....class)

获得某个bean:applicationContext.getBean(xxx)()

![image-20220216050157654](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220216050157654.png)

还可以用importBeanDefinitionRegitray手动注册

用Factorybean注册组件：

![image-20220216051144978](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220216051144978.png)

![image-20220216051213649](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220216051213649.png)

![image-20220216051053658](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220216051053658.png)

# 配置文件原理

SpringBoot内置支持的组件在**spring-boot-autoconfigure-2.2.2.RELEASE.jar** 里面。如该**包下的jdbc分包下的DataSourceAutoConfiguration**

![img](https://pic3.zhimg.com/80/v2-424af7413e15f2657999b7ccde2a2996_1440w.png)



**其中的EnableConfigurationProperties注解用于加载用户配置的参数信息。**

**DataSourceProperties的作用就是标识配置文件中那些信息是DataSource使用的，标识的方式就是使用“spring.datasource”前缀。**

![img](https://pic3.zhimg.com/80/v2-4ccd033fd808ebbf3ea7adf91ba1a3d6_1440w.png)



**--根据分析，可以得出结论：**自动加载的配置类信息都在 Xxx**AutoConfiguration**里面，配置的属性参数都在Xxxxx**Properties**里面。

![img](https://pic4.zhimg.com/80/v2-98fdaa5758d40191cf671ac792d39593_1440w.jpg)



自动配置

![image-20220209102517707](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220209102517707.png)

在Spring Boot产生之前，Spring项目会存在多个配置文件，例如web.xml、application.xml，应用程序自身也需要多个配置文件，同时需要编写程序读取这些配置文件。现在Spring Boot简化了Spring项目配置的管理和读取，仅需要一个application.properties文件，并提供了多种读取配置文件的方式。 

可以用一句话来描述整个过程：Spring Boot通过@EnableAutoConfiguration注解开启自动配置，加载spring.factories中注册的各种AutoConfiguration类，当某个AutoConfiguration类满足其注解@Conditional指定的生效条件（Starters提供的依赖、配置或Spring容器中是否存在某个Bean等）时，实例化该AutoConfiguration类中定义的Bean（组件等），并注入Spring容器，就可以完成依赖框架的自动配置。

在未使用Spring Boot的情况下，Bean的生命周期由Spring来管理，然而Spring无法自动配置@Configuration注解的类。而Spring Boot的核心功能之一就是根据约定自动管理该注解标注的类。用来实现该功能的组件之一便是@EnableAutoConfiguration注解。 @EnableAutoConfiguration位于spring-boot-autoconfigure包内，当使用@SpringBootApplication注解时，@EnableAutoConfiguration注解会自动生效。 ==@EnableAutoConfiguration的主要功能是启动Spring应用程序上下文时进行自动配置，它会尝试猜测并配置项目可能需要的Bean。==自动配置通常是基于项目classpath中引入的类和已定义的Bean来实现的。在此过程中，被自动配置的组件来自项目自身和项目依赖的jar包中。 举个例子：如果将tomcat-embedded.jar添加到classpath下，那么@EnableAutoConfiguration会认为你准备使用TomcatServletWebServerFactory类，并帮你初始化相关配置。与此同时，如果自定义了基于ServletWebServerFactory的Bean，那么@EnableAutoConfiguration将不会进行TomcatServletWebServerFactory类的初始化。这一系列的操作判断都由Spring Boot来完成。



-----------------------------------------------------------------------------------------------

正如上文所说，@EnableAutoConfiguration会猜测你需要使用的Bean，但如果在实战中你并不需要它预置初始化的Bean，可通过该注解的exclude或excludeName参数进行有针对性的排除。

如果开发者不需要Spring Boot的某一项自动配置，该如何实现呢？比如，当不需要数据库的自动配置时，可通过以下两种方式让其自动配置失效。 

```java 
// 通过@SpringBootApplication排除DataSourceAutoConfiguration
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) 
public class SpringLearnApplication {
}
或
 // 通过@EnableAutoConfiguration排除DataSourceAutoConfiguration
@Configuration
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)
public class DemoConfiguration {
}    
```

需要注意的是，被@EnableAutoConfiguration注解的类所在package还具有特定的意义，通常会被作为扫描注解@Entity的根路径。这也是在使用@SpringBootApplication注解时需要将被注解的类放在顶级package下的原因，如果放在较低层级，它所在package的同级或上级中的类就无法被扫描到。 而对于入口类和其main方法来说，并不依赖@SpringBootApplication注解或@EnableAuto-Configuration注解，也就是说该注解可以使用在其他类上，而非入口类上。 

在Spring Boot的全局配置文件中，可以配置与修改多个参数，读者想了解参数的详细说明和描述，可以查看官方文档说明：https：//docs.spring.io/spring-boot/docs/2.1.4.RELEASE/reference/htmlsingle/#common-application-properties。 

Spring Boot的核心注解@SpringBootApplication是一个组合注解，主要组合了@SpringBootConfiguration、@EnableAutoConfiguration和@ComponentScan注解。源代码可以从spring-boot-autoconfigure-2.1.4.RELEASE.jar依赖包中查看org/springframework/boot/autoconfigure/SpringBootApplication.java。 

@SpringBootConfiguration:Spring Boot的配置类；标注在某个类上，表示这是一个Spring Boot的配置类；
@Configuration:配置类上来标注这个注解；配置类 ----- 配置文件；配置类也是容器中的一个组件；

@Component
@EnableAutoConfiguration：开启自动配置功能；以前我们需要配置的东西，Spring Boot帮我们自动配置；@EnableAutoConfiguration告诉SpringBoot开启自动配置，会帮我们自动去加载 自动配置类
@ComponentScan ： 扫描包 相当于在spring.xml 配置中<context:comonent-scan> 但是并没有指定basepackage，如果没有指定spring底层会自动扫描当前配置类所有在的包


定制Banner Spring Boot项目启动时，在控制台可以看到如图4.3所示的默认启动图案。 如果开发者希望指定自己的启动信息，又该如何配置呢？首先，在src/main/resources目录下新建banner.txt文件，并在文件中添加任意字符串内容，如“#Hello，Spring Boot!”。然后，重新启动Spring Boot项目，将发现控制台启动信息已经发生改变。如果开发者想把启动字符串信息换成字符串图案，可以通过如下操作实现。 

## 三种方式读取配置文件的内容 



Spring Boot提供了3种方式读取项目的application.properties配置文件的内容。这3种方式分别为Environment类、@Value注解以及@ConfigurationProperties注解。 

 Environment是一个通用的读取应用程序运行时的环境变量的类，可以通过key-value方式读取application.properties、命令行输入参数、系统属性、操作系统环境变量等。下面通过一个实例来演示如何使用Environment类读取application.properties配置文件的内容。 

@Import(EnableAutoConfigurationImportSelector.class) 关键点！
可以看到，在@EnableAutoConfiguration注解内使用到了@import注解来完成导入配置的功能，而
EnableAutoConfigurationImportSelector 实现了DeferredImportSelectorSpring内部在解析@Import注解时会调
用getAutoConfigurationEntry方法，这块属于Spring的源码，有点复杂，我们先不管它是怎么调用的。 下面是2.3.5.RELEASE
实现源码：

```java
protected AutoConfigurationEntry getAutoConfigurationEntry(AnnotationMetadata annotationMetadata) {
if (!isEnabled(annotationMetadata)) {
 return EMPTY_ENTRY;
 }
 AnnotationAttributes attributes = getAttributes(annotationMetadata);
 //
 List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);
 configurations = removeDuplicates(configurations);
 Set<String> exclusions = getExclusions(annotationMetadata, attributes);
checkExcludedClasses(configurations, exclusions);
configurations.removeAll(exclusions);
configurations = getConfigurationClassFilter().filter(configurations);
fireAutoConfigurationImportEvents(configurations, exclusions);
return new AutoConfigurationEntry(configurations, exclusions);
 }
```

getAutoConfigurationEntry方法进行扫描具有META-INF/spring.factories文件的jar包。



**任何一个springboot应用，都会引入spring-boot-autoconfigure，而spring.factories文件就在该包下面**。spring.factories文件是Key=Value形式，多个Value时使用,隔开，该文件中定义了关于初始化，监听器等信息，而真正使自动配置生效的key是
org.springframework.boot.autoconfigure.EnableAutoConfiguration，如下所示：

等同于

```
@Import({
Auto Configure
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration,\
 ...省略
org.springframework.boot.autoconfigure.websocket.WebSocketMessagingAutoConfiguration,\
org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration
})
```

每一个这样的 xxxAutoConfiguration类都是容器中的一个组件，都加入到容器中；用他们来做自动配置；

==我新建一个类，但是我不用 `@Service`注解，也就是说，它是个普通的类，那么我们如何使它也成为一个 Bean 让 Spring 去管理呢？==只需要`@Configuration` 和`@Bean`两个注解即可，如下：

```java
public class TestService {
    public String sayHello () {
        return "Hello Spring Boot!";
    }
}
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfig {
    @Bean
    public TestService getTestService() {
        return new TestService();
    }
}
```

`@Configuration`表示该类是个配置类，`@Bean`表示该方法返回一个 Bean。这样就把`TestService`作为 Bean 让 Spring 去管理了，在其他地方，我们如果需要使用该 Bean，和原来一样，直接使用`@Resource`注解注入进来即可使用，非常方便。

```java
@Resource
private TestService testService;
```

另外，部署配置方面，原来 Spring 有多个 xml 和 properties配置，在 Spring Boot 中只需要个 application.yml即可

第04 课：Spring Boot 中的项目属性配置
我们知道，在项目中，很多时候需要用到一些配置的信息，这些信息可能在测试环境和
生产环境下会有不同的配置，后面根据实际业务情况有可能还会做修改，针对这种情
况，我们不能将这些配置在代码中写死，最好就是写到配置文件中。比如可以把这些信
息写到application.yml 文件中。
1. 少量配置信息的情形
    举个例子，在微服务架构中，最常见的就是某个服务需要调用其他服务来获取其提供的相关信息，那么在该服务的配置文件中需要配置被调用的服务地址，比如在当前服务里，我们需要调用订单微服务获取订单相关的信息，假设订单服务的端口号是8002，那我们可以做如下配置：

  ```yaml
  server:
     port: 8001
  
  #配置微服务的地址url:
  url:
  
    orderUrl: http://localhost:8002
  ```

  然后在业务代码中如何获取到这个配置的订单服务地址呢？我们可以使用@Value 注解来解决。在对应的类中加上一个属性，在属性上使用@Value 注解即可获取到配置文件中的配置信息，如下

  

```java
@RestController
@RequestMapping("/test")
public class ConfigController {
private static final Logger LOGGER =
LoggerFactory.getLogger(ConfigController.class);
@Value("${url.orderUrl}")
private String orderUrl;
@RequestMapping("/config")
public String testConfig() {
    LOGGER.info("=====获取的订单服务地址为：{}", orderUrl);
return "success";
}
}
```

![image-20210810104350881](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210810104350881.png)

2. 多个配置信息的情形
这里再引申一个问题，随着业务复杂度的增加，一个项目中可能会有越来越多的微服
务，某个模块可能需要调用多个微服务获取不同的信息，那么就需要在配置文件中配置
多个微服务的地址。可是，在需要调用这些微服务的代码中，如果这样一个个去使用
@Value 注解引入相应的微服务地址的话，太过于繁琐，也不科学。
所以，在实际项目中，业务繁琐，逻辑复杂的情况下，需要考虑封装一个或多个配置
类。举个例子：假如在当前服务中，某个业务需要同时调用订单微服务、用户微服务和
购物车微服务，分别获取订单、用户和购物车相关信息，然后对这些信息做一定的逻辑
处理。那么在配置文件中，我们需要将这些微服务的地址都配置好：

```yaml
#配置多个微服务的地址

url:

#订单微服务的地址

 orderUrl: http://localhost:8002

#用户微服务的地址

 userUrl: http://localhost:8003

#购物车微服务的地址

 shoppingUrl: http://localhost:8004
```

也许实际业务中，远远不止这三个微服务，甚至十几个都有可能。对于这种情况，我们
可以先定义一个MicroServiceUrl 类来专门保存微服务的url，如下：

```java
@Component
@ConfigurationProperties(prefix = "url")
public class MicroServiceUrl {
private String orderUrl;
private String userUrl;
private String shoppingUrl;
// 省去get 和set 方法
}
```

细心的朋友应该可以看到，使用@ConfigurationProperties 注解并且使用prefix 来指定一个前缀，然后该类中的属性名就是配置中去掉前缀后的名字，一一对应即可。
即：前缀名+ 属性名就是配置文件中定义的key。同时，该类上面需要加上
@Component 注解，把该类作为组件放到Spring 容器中，让Spring 去管理，我们使用的时候直接注入即可。
需要注意的是，使用@ConfigurationProperties 注解需要导入它的依赖：

```xml
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-configuration-processor</artifactId>
<optional>true</optional>
</dependency>
```

接下来写个Controller 来测试一下。此时，不需要在代码中一个个引入这些微服务的url 了，直接通过@Resource 注解将刚刚写好配置类注入进来即可使用了，非常方便。如下：

```java
@RestController@RequestMapping("/test")public class TestController {
private static final Logger LOGGER =
LoggerFactory.getLogger(TestController.class);
@Resource
private MicroServiceUrl microServiceUrl;
@RequestMapping("/config")
public String testConfig() {
LOGGER.info("=====获取的订单服务地址为：{}",
microServiceUrl.getOrderUrl());
LOGGER.info("=====获取的用户服务地址为：{}",
microServiceUrl.getUserUrl());
LOGGER.info("=====获取的购物车服务地址为：{}",
microServiceUrl.getShoppingUrl());
return "success";
}
```

生产环境与开发环境：

新建两个配置文件： application-dev.yml 和application-pro.yml，分别用
来对开发环境和生产环境进行相关配置。这里为了方便，我们分别设置两个访问端口号，开发环境用8001，生产环境用8002

```yaml
spring:
 profiles:
  active:
   - dev
```



这样就可以在开发的时候，指定读取application-dev.yml 文件，访问的时候使用8001 端口，部署到服务器后，只需要将application.yml 中指定的文件改成application-pro.yml 即可，然后使用8002 端口访问，非常方便。





 ## 以HttpEncodingAutoConfiguration（Http编码自动配置）为例解释自动配置原理

```java
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(ServerProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@ConditionalOnClass(CharacterEncodingFilter.class)
@ConditionalOnProperty(prefix = "server.servlet.encoding", value = "enabled", matchIfMissing = true)
public class HttpEncodingAutoConfiguration {

private final Encoding properties;

public HttpEncodingAutoConfiguration(ServerProperties properties) {
this.properties = properties.getServlet().getEncoding();
 }

@Bean
@ConditionalOnMissingBean
public CharacterEncodingFilter characterEncodingFilter() {
CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
filter.setEncoding(this.properties.getCharset().name());
 filter.setForceRequestEncoding(this.properties.shouldForce(Encoding.Type.REQUEST));
filter.setForceResponseEncoding(this.properties.shouldForce(Encoding.Type.RESPONSE));
return filter;
}
```

@Configuration(proxyBeanMethods = false)
标记了@Configuration Spring底层会给配置创建cglib动态代理。 作用：就是防止每次调用本类的Bean方法而重新创建对象，Bean是默认单例的
@EnableConfigurationProperties(ServerProperties.class)
启用可以在配置类设置的属性 对应的类
@xxxConditional根据当前不同的条件判断，决定这个配置类是否生效

细节
一但这个配置类生效；这个配置类就会给容器中添加各种组件；这些组件的属性是从对应的properties类中获取的，这些类里面的每一个属性又是和配置文件绑定的；
所有在配置文件中能配置的属性都是在xxxxProperties类中封装；配置文件能配置什么就可以参照某个功能对应的这个属性类
还记得这个过滤器吧， 对，就是我们以前设置编码的， 我们现在不需要去web.xml配置过滤器了， 只需要往容器中注入该过滤器。它的值都是通过properties设置的

![image-20210803101946459](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210803101946459.png)

properties.getServlet().getEncoding().setCharset(UTF_8); 就等于如下配置：server.servlet.encoding.charset=UTF‐8





@Conditional扩展注解作用           （判断是否满足当前指定条件）
@ConditionalOnJava                        系统的java版本是否符合要求
@ConditionalOnBean                      容器中存在指定Bean；
@ConditionalOnMissingBean         容器中不存在指定Bean；
@ConditionalOnExpression            满足SpEL表达式指定

@ConditionalOnMissingClass         系统中没有指定的类
@ConditionalOnSingleCandidate    容器中只有一个指定的Bean，或者这个Bean是首选Bean
@ConditionalOnProperty                系统中指定的属性是否有指定的值
@ConditionalOnResource                  类路径下是否存在指定资源文件
@ConditionalOnWebApplication       当前是web环境
@ConditionalOnNotWebApplication
                                                                 当前不是web环境
@ConditionalOnJndi                             JNDI存在指定项

![image-20210803102300054](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210803102300054.png)

# @ConfigurationProperties注解原理

注意：使用这个注解需要set方法，不然注解失效

ConfigurationPropertiesBindingPostProcessor这个bean后置处理器，就是来处理bean属性的绑定的，这个bean后置处理器后文将称之为properties后置处理器。你需要知道以下几件事：

ioc容器context的enviroment.propertySources记录着系统属性、应用属性以及springboot的默认配置文件application.properties中的配置属性等。properties后置处理器就是从其中找到匹配的配置项绑定到bean的属性上去的。
属性绑定是有覆盖性的，操作系统环境变量可以覆盖配置文件application.properties, java系统属性可以覆盖操作系统环境变量。



@ConfigurationProperties是springboot新加入的注解，主要用于配置文件中的指定键值对映射到一个java实体类上。

例子：如果现在有一个类`People`,只有一个基本属性`name`，那么配置文件中的值是如何绑定的。

```java
public class People {
    private String name;
    //getter, setter方法略
}
```

最终会调用到Binder类的findProperty方法，如下

```java
private ConfigurationProperty findProperty(ConfigurationPropertyName name,
		Context context) {
	if (name.isEmpty()) {
		return null;
	}
    	//遍历属性资源文件，按照上文提到的属性资源顺序，直到根据name参数找到第一个不为空的属性
	//才返回。
	return context.streamSources()
			.map((source) -> source.getConfigurationProperty(name))
			.filter(Objects::nonNull).findFirst().orElse(null);
}
```

context.streamSource()返回一个流式对象Stream\<ConfigurationPropertySource>, ConfigurationPropertySource是属性资源的描述接口，提供了通过属性名称获取特定属性的接口方法。

我们接着看context.stream方法做了什么？

```java
public Stream<ConfigurationPropertySource> streamSources() {
	if (this.sourcePushCount > 0) {
		return this.source.stream();
	}
	return StreamSupport.stream(Binder.this.sources.spliterator(), false);
}
```

springboot启动时，Binder.this.sources实际上就是SpringConfigurationPropertySources类。这个类有一个成员变量sources,存储着springboot启动过程中采集到的属性资源，就是2.1节讲到的MutablePropertySources。

```java
/** 子类 MutablePropertySources**/
private final Iterable<PropertySource<?>> sources
```

lamda表达式真正流式遍历执行的时候，会调用到SpringConfigurationPropertySources$SourcesIterator的重写hasNext方法，而hasNext方法最终会调用到SpringConfigurationPropertySources这个类的adapt方法，这是一个适配器方法，它将PropertySource属性资源转化为ConfigurationPropertySource。

这样才能继续执行流式lamda表达式中的map方法，map((source) -> source.getConfigurationProperty(name))

原文链接：https://blog.csdn.net/gs_albb/article/details/85019466

## springboot的aop

源码：

```java
@Configuration
@ConditionalOnClass({EnableAspectJAutoProxy.class, Aspect.class, Advice.class, AnnotatedElement.class})
@ConditionalOnProperty(
    prefix = "spring.aop",
    name = {"auto"},
    havingValue = "true",
    matchIfMissing = true
)
public class AopAutoConfiguration {
    public AopAutoConfiguration() {
    }

    @Configuration
    @EnableAspectJAutoProxy(
        proxyTargetClass = true
    )
    @ConditionalOnProperty(
        prefix = "spring.aop",
        name = {"proxy-target-class"},
        havingValue = "true",
        matchIfMissing = true//从这里看到是默认使用cglib动态代理的
    )
    public static class CglibAutoProxyConfiguration {
        public CglibAutoProxyConfiguration() {
        }
    }

    @Configuration
    @EnableAspectJAutoProxy(
        proxyTargetClass = false
    )
    @ConditionalOnProperty(
        prefix = "spring.aop",
        name = {"proxy-target-class"},
        havingValue = "false",
        matchIfMissing = false
    )
    public static class JdkDynamicAutoProxyConfiguration {
        public JdkDynamicAutoProxyConfiguration() {
        }
    }
    
    注解：@ConditionalOnProperty
        public @interface ConditionalOnProperty {
         // 数组，获取对应property名称的值，与name不可同时使用
    String[] value() default {};

    // 配置属性名称的前缀，比如spring.http.encoding
    String prefix() default "";

    // 数组，配置属性完整名称或部分名称
    // 可与prefix组合使用，组成完整的配置属性名称，与value不可同时使用 name用来从application.properties中读取某个属性值
    String[] name() default {};

    // 可与name组合使用，比较获取到的属性值与havingValue给定的值是否相同，相同才加载配置
    String havingValue() default "";
          
        boolean matchIfMissing() default false;

        // 缺少该配置属性时是否可以加载。如果为true，没有该配置属性时也会正常加载；反之则不会生效
}
```

比如：

在配置文件为：

```stylus
spring.datasource.type=org.apache.tomcat.jdbc.pool.DataSource
```

然后有一个类：

```less
@ConditionalOnProperty(name = "spring.datasource.type", havingValue = "org.apache.tomcat.jdbc.pool.DataSource",
        matchIfMissing = true)
static class Tomcat {
  // 省略内部代码
}
```

# spring boot解决跨域问题？

实现webmvcconfigure接口重写addcorsmapping方法

# servlet概述



Servlet是一个基于Java技术的Web组件，由容器管理，生成动态内容。像其他基于Java技术的组件一样，Servlet是与平台无关的Java类格式，它们被编译为与具体平台无关的字节码，可以被基于Java技术的Web Server动态加载并运行。容器（有时称为Servlet引擎）是Web服务器为支持Servlet功能扩展的部分。客户端通过Servlet容器实现请求/应答模型与Servlet交互。 Servlet容器是Web Server或Application Server的一部分，其提供基于请求/响应模型的网络服务，解码基于MIME的请求，并且格式化基于MIME的响应。Servlet容器也包含了管理Servlet生命周期的能力，Servlet是运行在Servlet容器内的。Servlet 容器可以嵌入宿主的Web Server中，或者通过Web Server的本地扩展API单独作为附加组件安装。Servelt容器也可能内嵌或安装到包含Web功能的Application Server中。 所有Servlet容器必须支持基于HTTP协议的请求/响应模型，并且可以选择性支持基于HTTPS协议的请求/应答模型。容器必须实现的HTTP协议版本包含HTTP/1.0和HTTP/1.1。 Servlet容器应该使Servlet执行在一个安全限制的环境中。在Java平台标准版（J2SE，v.1.3或更高）或者Java平台企业版(Java EE，v.1.3或更高)的环境下，这些限制应该被放置在Java平台定义的安全许可架构中。比如，为了保证容器的其他组件不受负面影响，高端的Application Server可能会限制Thread对象的创建。常见的比较经典的Servlet容器实现有Tomcat和Jetty。 



Servlet 3.0提供的异步处理能力  Web应用程序中提供异步处理最基本的动机是处理需要很长时间才能完成的请求。这些比较耗时的请求可能是一个缓慢的数据库查询，可能是对外部REST API的调用，也可能是其他一些耗时的I/O操作。这种耗时较长的请求可能会快速耗尽Servlet容器线程池中的线程并影响应用的可伸缩性。 在Servlet3.0规范前，Servlet容器对Servlet都是以每个请求对应一个线程这种1:1的模式进行处理的，如图6-1所示（本节Servlet容器固定使用Tomcat来进行讲解）。 



每当用户发起一个请求时，Tomcat容器就会分配一个线程来运行具体的Servlet。在这种模式下，当在Servlet内执行比较耗时的操作，比如访问了数据库、同步调用了远程rpc，或者进行了比较耗时的计算时，当前分配给Servlet执行任务的线程会一直被该Servlet持有，不能及时释放掉后供其他请求使用，而Tomcat内的容器线程池内线程是有限的，当线程池内线程用尽后就不能再对新来的请求进行及时处理了，所以这大大限制了服务器能提供的并发请求数量。 为了解决上述问题，在Servlet 3.0规范中引入了异步处理请求的能力，处理线程可以及时返回容器并执行其他任务











# 多数据源时的配置

![image-20210824211958311](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210824211958311.png)

![image-20210824212031000](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210824212031000.png)

![image-20210824212928795](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210824212928795.png)





# 针对windows和linux进行不同的静态资源配置

```java
public void addResourceHandlers(ResourceHandlerRegistry registry) {

   String os = System.getProperty("os.name");

   if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
registry.addResourceHandler("/smallapple/**")
            // /apple/**表示在磁盘apple目录下的所有资源会被解析为以下的路径
.addResourceLocations("file:G:/itemsource/smallapple/") //媒体资源
.addResourceLocations("classpath:/META-INF/resources/");  //swagger2页面
} else {  //linux 和mac
registry.addResourceHandler("/smallapple/**")
            .addResourceLocations("file:/resources/smallapple/")   //媒体资源
.addResourceLocations("classpath:/META-INF/resources/");  //swagger2页面;
}
}
```

 

# springboot源代码学习

初始化过程：

![image-20211216120047345](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211216120047345.png)

run方法执行流程：

![image-20211216131451601](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211216131451601.png)

![image-20211216131524829](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211216131524829.png)







准备上下文环境：

![image-20211215181507610](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211215181507610.png)

在prepareContext方法中

![image-20211215184539336](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211215184539336.png)

有7个初始化器



扫描注解、设置环境：



```java
AnnotationConfigServletWebApplicationContext.java
public AnnotationConfigServletWebApplicationContext(DefaultListableBeanFactory beanFactory) {
		super(beanFactory);
		this.reader = new AnnotatedBeanDefinitionReader(this);
		this.scanner = new ClassPathBeanDefinitionScanner(this);
	}

    @Override
public void setEnvironment(ConfigurableEnvironment environment) {
   super.setEnvironment(environment);
   this.reader.setEnvironment(environment);
   this.scanner.setEnvironment(environment);
}
```

准备好上下文之后刷新应用上下文：

```
SpringApplication 类中
```

![image-20211216112750594](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211216112750594.png)

![image-20211216113034895](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211216113034895.png)

这个接口的实现类AbstracApplicationContext中实现了refresh方法

![image-20211216113122420](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20211216113122420.png)

```java
@Override
public void refresh() throws BeansException, IllegalStateException {
   synchronized (this.startupShutdownMonitor) {
      StartupStep contextRefresh = this.applicationStartup.start("spring.context.refresh");

      // 刷新上下文环境，初始化上下文环境，对系统环境变量或者系统属性进行准备和校验
      prepareRefresh();

      // Tell the subclass to refresh the internal bean factory.
      ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

      // Prepare the bean factory for use in this context.
      prepareBeanFactory(beanFactory);

      try {
         // Allows post-processing of the bean factory in context subclasses.
          //提供了子类覆盖的额外处理，即子类处理自定义的beanfactorypostprocess
         postProcessBeanFactory(beanFactory);

         StartupStep beanPostProcess = this.applicationStartup.start("spring.context.beans.post-process");
         // Invoke factory processors registered as beans in the context.激活各种beanfactory处理器
         invokeBeanFactoryPostProcessors(beanFactory);
//注册拦截bean创建的bean处理器，即注册beanpostProcessor
         // Register bean processors that intercept bean creation.
         registerBeanPostProcessors(beanFactory);
         beanPostProcess.end();
//初始化上下文中的资源文件如国际化文件的处理
         // Initialize message source for this context.
         initMessageSource();
//初始化上下文事件广播器
         // Initialize event multicaster for this context.
         initApplicationEventMulticaster();

         // Initialize other special beans in specific context subclasses.给子类拓展初始化其他bean
         onRefresh();

         // Check for listener beans and register them.在所有的bean中查找listener bean然后注册到广播器中
         registerListeners();
//初始化非延迟加载的bean
         // Instantiate all remaining (non-lazy-init) singletons.
         finishBeanFactoryInitialization(beanFactory);
//完成刷新过程，通知生命周期处理器刷新过程，同时发出contextRefreshEvent通知别人
         // Last step: publish corresponding event.
         finishRefresh();
      }

      catch (BeansException ex) {
         if (logger.isWarnEnabled()) {
            logger.warn("Exception encountered during context initialization - " +
                  "cancelling refresh attempt: " + ex);
         }

         // Destroy already created singletons to avoid dangling resources.
         destroyBeans();

         // Reset 'active' flag.
         cancelRefresh(ex);

         // Propagate exception to caller.
         throw ex;
      }

      finally {
         // Reset common introspection caches in Spring's core, since we
         // might not ever need metadata for singleton beans anymore...
         resetCommonCaches();
         contextRefresh.end();
      }
   }
}
```









# 监控配置

我们可以引入 spring-boot-start-actuator 依赖，直接使用 REST 方式来获取进程的运行期性能参数，从而达到监控的目的，比较方便。但是 Spring Boot 只是个微框架，没有提供相应的服务发现与注册的配套功能，没有外围监控集成方案，没有外围安全管理方案，所以在微服务架构中，还需要 Spring Cloud 来配合一起使用。



# 静态资源的加载

默认Springboot将从如下位置按如下优先级(从高到低)加载jar包对应前端静态资源：



```bash
1.jar包同级static目录
2.jar包同级public目录
3.jar包同级resource目录
4.jar包/META-INF/resources
```





# 热部署

springboot有3中热部署方式：

1.使用springloaded配置pom.xml文件，使用mvn spring-boot:run启动

2.使用springloaded本地加载启动，配置jvm参数

-javaagent:<jar包地址> -noverify

3.使用devtools工具包，操作简单，但是每次需要重新部署
这里主要讲解一下第三种热部署方式的使用，因为在网上查找资源时，总会发现很多人就是springloaded和devtools都使用了，其实是多余的操作，而且第三种操作简单快捷。

```xml
  <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
            <scope>true</scope>
        </dependency>

```



```yml
spring:
  devtools:
    restart:
      enabled: true  #设置开启热部署
      additional-paths: src/main/java #重启目录
      exclude: WEB-INF/**
  freemarker:
    cache: false  

```

setting:

![image-20210723175125613](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210723175125613.png)

![image-20210723175146035](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210723175146035.png)

选择register

![image-20210723175239294](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210723175239294.png)

- 修改类–>保存：应用会重启
- 修改配置文件–>保存：应用会重启
- 修改页面–>保存：应用不会重启，但会重新加载，页面会刷新





# 防重复提交拦截器

```java
@Component
public abstract class RepeatSubmitInterceptor extends HandlerInterceptorAdapter
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if (handler instanceof HandlerMethod)
        {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            RepeatSubmit annotation = method.getAnnotation(RepeatSubmit.class);
            if (annotation != null)
            {
                if (this.isRepeatSubmit(request))
                {
                    AjaxResult ajaxResult = AjaxResult.error("不允许重复提交，请稍后再试");
                    ServletUtils.renderString(response, JSON.marshal(ajaxResult));
                    return false;
                }
            }
            return true;
        }
        else
        {
            return super.preHandle(request, response, handler);
        }
    }

    /**
     * 验证是否重复提交由子类实现具体的防重复提交的规则
     * 
     * @param request
     * @return
     * @throws Exception
     */
    public abstract boolean isRepeatSubmit(HttpServletRequest request) throws Exception;
}
```

servlet Utils:

```java
package com.ruoyi.common.utils;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.ruoyi.common.core.text.Convert;

/**
 * 客户端工具类
 * 
 * @author ruoyi
 */
public class ServletUtils
{
    /**
     * 定义移动端请求的所有可能类型
     */
    private final static String[] agent = { "Android", "iPhone", "iPod", "iPad", "Windows Phone", "MQQBrowser" };

    /**
     * 获取String参数
     */
    public static String getParameter(String name)
    {
        return getRequest().getParameter(name);
    }

    /**
     * 获取String参数
     */
    public static String getParameter(String name, String defaultValue)
    {
        return Convert.toStr(getRequest().getParameter(name), defaultValue);
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name)
    {
        return Convert.toInt(getRequest().getParameter(name));
    }

    /**
     * 获取Integer参数
     */
    public static Integer getParameterToInt(String name, Integer defaultValue)
    {
        return Convert.toInt(getRequest().getParameter(name), defaultValue);
    }

    /**
     * 获取request
     */
    public static HttpServletRequest getRequest()
    {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response
     */
    public static HttpServletResponse getResponse()
    {
        return getRequestAttributes().getResponse();
    }

    /**
     * 获取session
     */
    public static HttpSession getSession()
    {
        return getRequest().getSession();
    }

    public static ServletRequestAttributes getRequestAttributes()
    {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

    /**
     * 将字符串渲染到客户端
     * 
     * @param response 渲染对象
     * @param string 待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string)
    {
        try
        {
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 是否是Ajax异步请求
     * 
     * @param request
     */
    public static boolean isAjaxRequest(HttpServletRequest request)
    {
        String accept = request.getHeader("accept");
        if (accept != null && accept.indexOf("application/json") != -1)
        {
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1)
        {
            return true;
        }

        String uri = request.getRequestURI();
        if (StringUtils.inStringIgnoreCase(uri, ".json", ".xml"))
        {
            return true;
        }

        String ajax = request.getParameter("__ajax");
        if (StringUtils.inStringIgnoreCase(ajax, "json", "xml"))
        {
            return true;
        }
        return false;
    }

    /**
     * 判断User-Agent 是不是来自于手机
     */
    public static boolean checkAgentIsMobile(String ua)
    {
        boolean flag = false;
        if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;")))
        {
            // 排除 苹果桌面系统
            if (!ua.contains("Windows NT") && !ua.contains("Macintosh"))
            {
                for (String item : agent)
                {
                    if (ua.contains(item))
                    {
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }
}
```

注意这个：

  public static ServletRequestAttributes getRequestAttributes()
    {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }

类型转化器：

```
Convert
```

```
package org.springframework.web.context.request;

public abstract class RequestContextHolder
{

}
```





# 如何理解 Spring Boot 中的 Starter

[SpringBoot之如何自定义一个starter模块_码农飞哥的博客-CSDN博客_自定义一个starter](https://blog.csdn.net/u014534808/article/details/107966241)

Starters是什么：
Starters可以理解为启动器，它包含了一系列可以集成到应用里面的依赖包，你可以一站式集成Spring及其他技术，而不需要到处找示例代码和依赖包。如你想使用Spring JPA访问数据库，只要加入spring-boot-starter-data-jpa启动器依赖就能使用了。Starters包含了许多项目中需要用到的依赖，它们能快速持续的运行，都是一系列得到支持的管理传递性依赖。

[SpringBoot系列之starter原理 - 孔令翰 - 博客园 (cnblogs.com)](https://www.cnblogs.com/klhans/p/14900337.html)

**starter 依赖只负责引入相关的功能 jar 包，而不会主动的将 bean 注册到 IOC 容器中。**

至于为什么普通 jar 包中没有 pom 文件，我猜测与 maven 的打包机制有关，可能是将 class 文件与 pom 文件分别打包，导致普通 jar 包中看不到 pom.xml。这个知识点属于 maven 的范畴了，等研究 maven 的时候再写文章告诉大家（基础真的很重要）。



**starter 与自动装配**

我们在上文中看到，starter 依赖只会帮我们引入对应功能的 jar 包，而没有主动将 bean 注册到 IOC 容器中（甚至连 class 文件都没有）。那 springboot 是怎么做到“开箱即用”的呢？这就与springboot 的 **AutoConfiguration** 也就是自动装配有关系了，关于自动装配的内容在我的另一篇博文[《SpringBoot系列之启动流程3-自动装配与@SpringBootApplication注解》](https://klhans.com/springboot-annotation-SpringBootApplication.html)中有详细介绍。简单来说就是 springboot 在 classpath 下搜索所有 jar 包中的 `spring.factories`文件，将其汇总为将要注册进IOC 容器中的 bean 蓝本。

加载完这些蓝本类之后，不是直接注册进 IOC 容器中，因为直接注册的话太多了，光是注册可能就要花很久，况且有些 bean 我这个应用可能根本用不到呢，那我就白花那么长时间启动了。所以springboot还要借助**条件装配**，看一下是否满足条件，满足的话才会进行相应功能的自动装配，将相应的bean 注册进 IOC 容器，否则忽略该自动装配。

举个实际的例子：`AutoConfiguration =\ JdbcTemplateAutoConfiguration` 也是自动装配中的一项：![image-20210606185008271](https://i.loli.net/2021/06/06/Yc3LAi8EI9F7gJy.png)

我们找到这个类看一下源码：

![image-20210606185934074](https://i.loli.net/2021/06/06/zZMFYq48bsaXgIt.png)

这个类上有一些注解，其中`@ConditionalOnClass({ DataSource.class, JdbcTemplate.class })`标识出：**这个配置类只有在有DataSource.class和JdbcTemplate.class这两个 class 文件的时候才生效**，而jdbc-starter 的依赖包中，就包含这两个类，所以`JdbcTemplateAutoConfiguration`才能自动装配生效！

动示例工程时，SpringBoot会自动扫描启动类所在包下的所有类，而如果还去扫描所有的jar包的话，又是具体怎么做到的？不妨从入口类调试一把，在SpringApplication.run(DemoApplication.class, args)打断点，一直追踪到getSpringFactoriesInstances这块：

![image-20220308085315148](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220308085315148.png)

查看SpringFactoriesLoader.loadFactoryNames的方法注释：

使用给定的类加载器从META-INF / spring.factories加载给定类型的工厂实现的完全限定类名。

有点眼熟，这里的spring.factories刚好也存在于mybatis-spring-boot-autoconfigure.jar中，

![image-20220308085353844](C:\Users\heziyi6\AppData\Roaming\Typora\typora-user-images\image-20220308085353844.png)





总结

starter 是 springboot 提供的开箱即用的组件，这些组件本身不提供主动注册 Bean 的功能，而是向应用中**引入依赖**，后续 springboot 应用启动，触发自动装配的时候，发现starter 引入的相关依赖，就会将对应功能的 bean 装配进 IOC 容器中。

Starters命名：

Spring Boot官方的启动器都是以spring-boot-starter-命名的，代表了一个特定的应用类型。第三方的启动器不能以spring-boot开头命名，它们都被Spring Boot官方保留。一般一个第三方的应该这样命名，像mybatis的mybatis-spring-boot-starter。

springboot常用的starter有哪些
spring-boot-starter-web 嵌入tomcat和web开发需要servlet与jsp支持
spring-boot-starter-data-jpa 数据库支持
spring-boot-starter-data-redis redis数据库支持
spring-boot-starter-data-solr solr支持
mybatis-spring-boot-starter 第三方的mybatis集成starter

使用spring + springmvc使用，如果需要引入mybatis等框架，需要到xml中定义mybatis需要的beanstarter就是定义一个starter的jar包，写一个@Configuration配置类、将这些bean定义在里面，然后在starter包的META-INF/spring.factories中写入该配置类，springboot会按照约定来加载该配置类
开发人员只需要将相应的starter包依赖进应用，进行相应的属性配置（使用默认配置时，不需要配置），就可以直接进行代码开发，使用对应的功能了，比如mybatis-spring-boot--starter，springboot-starter-redis

什么是嵌入式服务器？为什么要使用嵌入式服务器?
节省了下载安装tomcat，应用也不需要再打war包，然后放到webapp目录下再运行
只需要一个安装了 Java 的虚拟机，就可以直接在上面部署应用程序了
springboot已经内置了tomcat.jar，运行main方法时会去启动tomcat，并利用tomcat的spi机制加载springmvc

[自定义spring boot starter三部曲之一：准备工作_程序员欣宸的博客-CSDN博客](https://blog.csdn.net/boling_cavalry/article/details/82956512)





# 如何使用Spring Boot实现异常处理？
Spring提供了一种使用ControllerAdvice处理异常的非常有用的方法。 我们通过实现一个ControlerAdvice类，来处理控制器类抛出的所有异常。



# springboot的事务



在Spring Boot中，当我们使用了`spring-boot-starter-jdbc`或`spring-boot-starter-data-jpa`依赖的时候，框架会自动默认分别注入DataSourceTransactionManager或JpaTransactionManager。所以我们不需要任何额外配置就可以用`@Transactional`注解进行事务的使用。

以之前实现的[《使用Spring Data JPA访问MySQL》](http://blog.didispace.com/spring-boot-learning-21-3-4/)的示例作为基础工程进行事务的使用学习

```java
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void test() throws Exception {

		// 创建10条记录
		userRepository.save(new User("AAA", 10));
		userRepository.save(new User("BBB", 20));
		userRepository.save(new User("CCC", 30));
		userRepository.save(new User("DDD", 40));
		userRepository.save(new User("EEE", 50));
		userRepository.save(new User("FFF", 60));
		userRepository.save(new User("GGG", 70));
		userRepository.save(new User("HHH", 80));
		userRepository.save(new User("III", 90));
		userRepository.save(new User("JJJ", 100));

		// 省略后续的一些验证操作
	}

}
```

可以看到，在这个单元测试用例中，使用UserRepository对象连续创建了10个User实体到数据库中，下面我们人为的来制造一些异常，看看会发生什么情况。

通过`@Max(50)`来为User的age设置最大值为50，这样通过创建时User实体的age属性超过50的时候就可以触发异常产生。

```java
@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Max(50)
    private Integer age;

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

}
```

执行测试用例，可以看到控制台中抛出了如下异常，关于age字段的错误：

此时查数据库中的User表：

[![img](https://blog.didispace.com/images/pasted-329.png)](https://blog.didispace.com/images/pasted-329.png)

可以看到，测试用例执行到一半之后因为异常中断了，前5条数据正确插入而后5条数据没有成功插入，如果这10条数据需要全部成功或者全部失败，那么这时候就可以使用事务来实现，做法非常简单，我们只需要在test函数上添加`@Transactional`注解即可。

```java
@Test
@Transactional
public void test() throws Exception {

    // 省略测试内容

}
```

再来执行该测试用例，可以看到控制台中输出了回滚日志（Rolled back transaction for test context），



再看数据库中，User表就没有AAA到EEE的用户数据了，成功实现了自动回滚。

这里主要通过单元测试演示了如何使用`@Transactional`注解来声明一个函数需要被事务管理，通常我们单元测试为了保证每个测试之间的数据独立，会使用`@Rollback`注解让每个单元测试都能在结束时回滚。而真正在开发业务逻辑时，我们通常在service层接口中使用`@Transactional`来对各个业务逻辑进行事务管理的配置，例如：

```java
public interface UserService {
    
    @Transactional
    User update(String name, String password);
    
}
```

## 事务详解

上面的例子中我们使用了默认的事务配置，可以满足一些基本的事务需求，但是当我们项目较大较复杂时（比如，有多个数据源等），这时候需要在声明事务时，指定不同的事务管理器。对于不同数据源的事务管理配置可以见[《Spring Data JPA的多数据源配置》](http://blog.didispace.com/spring-boot-learning-21-3-8/)中的设置。在声明事务时，只需要通过value属性指定配置的事务管理器名即可，例如：`@Transactional(value="transactionManagerPrimary")`。

除了指定不同的事务管理器之后，还能对事务进行隔离级别和传播行为的控制，下面分别详细解释：

## 隔离级别

隔离级别是指若干个并发的事务之间的隔离程度，与我们开发时候主要相关的场景包括：脏读取、重复读、幻读。

我们可以看`org.springframework.transaction.annotation.Isolation`枚举类中定义了五个表示隔离级别的值：

```
public enum Isolation {
    DEFAULT(-1),
    READ_UNCOMMITTED(1),
    READ_COMMITTED(2),
    REPEATABLE_READ(4),
    SERIALIZABLE(8);
}
```

- `DEFAULT`：这是默认值，表示使用底层数据库的默认隔离级别。对大部分数据库而言，通常这值就是：`READ_COMMITTED`。
- `READ_UNCOMMITTED`：该隔离级别表示一个事务可以读取另一个事务修改但还没有提交的数据。该级别不能防止脏读和不可重复读，因此很少使用该隔离级别。
- `READ_COMMITTED`：该隔离级别表示一个事务只能读取另一个事务已经提交的数据。该级别可以防止脏读，这也是大多数情况下的推荐值。
- `REPEATABLE_READ`：该隔离级别表示一个事务在整个过程中可以多次重复执行某个查询，并且每次返回的记录都相同。即使在多次查询之间有新增的数据满足该查询，这些新增的记录也会被忽略。该级别可以防止脏读和不可重复读。
- `SERIALIZABLE`：所有的事务依次逐个执行，这样事务之间就完全不可能产生干扰，也就是说，该级别可以防止脏读、不可重复读以及幻读。但是这将严重影响程序的性能。通常情况下也不会用到该级别。

指定方法：通过使用`isolation`属性设置，例如：

```
@Transactional(isolation = Isolation.DEFAULT)
```

## 传播行为

所谓事务的传播行为是指，如果在开始当前事务之前，一个事务上下文已经存在，此时有若干选项可以指定一个事务性方法的执行行为。

我们可以看`org.springframework.transaction.annotation.Propagation`枚举类中定义了6个表示传播行为的枚举值：

```java
public enum Propagation {
    REQUIRED(0),
    SUPPORTS(1),
    MANDATORY(2),
    REQUIRES_NEW(3),
    NOT_SUPPORTED(4),
    NEVER(5),
    NESTED(6);
}
```

- `REQUIRED`：如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。
- `SUPPORTS`：如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行。
- `MANDATORY`：如果当前存在事务，则加入该事务；如果当前没有事务，则抛出异常。
- `REQUIRES_NEW`：创建一个新的事务，如果当前存在事务，则把当前事务挂起。
- `NOT_SUPPORTED`：以非事务方式运行，如果当前存在事务，则把当前事务挂起。
- `NEVER`：以非事务方式运行，如果当前存在事务，则抛出异常。
- `NESTED`：如果当前存在事务，则创建一个事务作为当前事务的嵌套事务来运行；如果当前没有事务，则该取值等价于`REQUIRED`。

指定方法：通过使用`propagation`属性设置，例如：

```java
@Transactional(propagation = Propagation.REQUIRED)
```



首先要说的，就是异常并没有被”捕获“ 到，导致事务并没有回滚。我们在业务层代码
中，也许已经考虑到了异常的存在，或者编辑器已经提示我们需要抛出异常，但是这里
面有个需要注意的地方：并不是说我们把异常抛出来了，有异常了事务就会回滚，我们
来看一个例子：

```java
@Servicepublic class UserServiceImpl implements UserService {
@Resource
private UserMapper userMapper;
@Override
@Transactional
    public void isertUser2(User user) throws Exception {
// 插入用户信息
userMapper.insertUser(user);
// 手动抛出异常
throw new SQLException("数据库异常");
}
}

```

我们看上面这个代码，其实并没有什么问题，手动抛出一个SQLException 来模拟实际中操作数据库发生的异常，在这个方法中，既然抛出了异常，那么事务应该回滚，实际却不如此，读者可以使用我源码中controller 的接口，通过postman 测试一下，就会发现，仍然是可以插入一条用户数据的。

Spring Boot 默认的事务规则是遇到运行异常（RuntimeException）和程序错误（Error）才会回滚。比如上面我们的例子中抛出的RuntimeException 就没有问题，但是抛出SQLException 就无法回滚了。针对非运行时异常，如果要进行事务回滚的话，可以在@Transactional 注解中使用rollbackFor 属性来指定异常，比如@Transactional(rollbackFor =Exception.class)，这样就没有问题了，所以在实际项目中，一定要指定异常。



# springboot与缓存 @Cacheable



![image-20211010144903923](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20211010144903923.png)

也可以自定义key的生成策略：

```java
  @Bean("mygenerator")
    public KeyGenerator keyGenerator()
    {
return new KeyGenerator() {
    @Override
    public Object generate(Object target, Method method, Object... objects) {
        
        return method.getName()+"["+ Arrays.asList(objects)+"]";
    }
```

然后使用上面的keyGenerator：

@Cacheable(value = {"emp"},keyGenerator = "mygenerator")

可以使用的spel表达式：

![image-20211010145627417](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20211010145627417.png)



@Cacheable(condition ="#a0>1")

当第一个参数值大于一才进行缓存 unless = "#a0==2" 如果第一个参数的值是2则不缓存

@CachePut:既调用方法，又更新缓存数据 修改了数据库的某个数据，同时更新缓存

@CacheEvict

![image-20211010174633297](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20211010174633297.png)



下面是，把3种Serializer保存到Redis中的结果：

1,所有的KeySerializer和HashKeySerializer都使用StringRedisSerializer，用其它Serializer的没有什么意义
2,上面序列化后的值，是保存到redis中的值，从Redis中读取回Java中后，值的内容都是一样的。
![img](https://imgconvert.csdnimg.cn/aHR0cHM6Ly9pbWcyMDE4LmNuYmxvZ3MuY29tL2Jsb2cvMTE1NjgzNC8yMDE5MDMvMTE1NjgzNC0yMDE5MDMyNTE1NDMyNjA0OC0yNzMzMjM0MTMucG5n?x-oss-process=image/format,png)

从上面的结果不难看出，

1，用StringRedisSerializer进行序列化的值，在Java和Redis中保存的内容是一样的

2，用Jackson2JsonRedisSerializer进行序列化的值，在Redis中保存的内容，比Java中多了一对双引号。




spring.mvc.static-path-pattern=/resources/**

Spring框架提供为应用透明添加缓存的支持，核心思想是，将抽象应用到缓存方法，基于缓存中可用信息减少方法的执行。缓存逻辑的应用是透明的，不会干扰调用者。只要通过 @EnableCaching 注解开启缓存支持，Spring Boot就会自动配置缓存基础结构。



```javascript
let base = '';
//传送json格式的post请求
export const postRequest = (url, params) => {
  return axios({
    method: 'post',
    url: `${base}${url}`,
    data: params
 })
}
```



# 参数验证

可以用的验证注解：

常见的校验类别注解见下表：

限制	说明
@Null	限制只能为null
@NotNull	限制必须不为null
@AssertFalse	限制必须为false
@AssertTrue	限制必须为true
@DecimalMax(value)	限制必须为一个不大于指定值的数字
@DecimalMin(value)	限制必须为一个不小于指定值的数字
@Digits(integer,fraction)	限制必须为一个小数，且整数部分的位数不能超过integer，小数部分的位数不能超过fraction
@Future	限制必须是一个将来的日期
@Max(value)	限制必须为一个不大于指定值的数字
@Min(value)	限制必须为一个不小于指定值的数字
@Past	限制必须是一个过去的日期
@Pattern(value)	限制必须符合指定的正则表达式
@Size(max,min)	限制字符长度必须在min到max之间
@Past	验证注解的元素值（日期类型）比当前时间早
@NotEmpty	验证注解的元素值不为null且不为空（字符串长度不为0、集合大小不为0）
@NotBlank	验证注解的元素值不为空（不为null、去除首位空格后长度为0），不同于@NotEmpty，@NotBlank只应用于字符串且在比较时会去除字符串的空格
@Email	验证注解的元素值是Email，也可以通过正则表达式和flag指定自定义的email格式




在构建任何程序的过程中，参数验证这一步骤都是难以避免的。比较传统的解决方式通常是在函数或者方法中编写验证相关的业务逻辑。为了将验证从业务代码中抽离，Spring提供了一种方式——Spring Validation。本节将介绍如何借助Spring Validation，使参数验证变得简洁通用。


基础验证Bean Validation

 Bean Validation是Spring Validation的基础一环，是由JCP（Java Community Process）定义的一个标准化的JavaBean验证API。这个API提供一组注解，用以标注对应元素的验证形式，但并未提供具体实现。对应功能需要依赖相应的框架工具来实现。其提供的注解如下所示： ·　

@Null：被标注的元素必须为Null。 ·　

@NotNull：被标注的元素必须不为Null。 ·　

@AssertTrue：被标注的元素必须为True。 ·　

@AssertFalse：被标注的元素必须为False。 ·　

@Min(value)：被标注的元素必须是一个数字，其值必须大于等于指定的最小值。 @Max(value)：被标注的元素必须是一个数字，其值必须小于等于指定的最大值。 @DecimalMin(value)：被标注的元素必须是一个数字，其值必须大于等于指定的最小值。 ·　

@DecimalMax(value)：被标注的元素必须是一个数字，其值必须小于等于指定的最大值。 ·　

@Size(max, min)：被标注的元素的大小必须在指定的范围内。 ·　

@Digits (integer, fraction)：被标注的元素必须是一个数字，其值必须在可接受的范围内。 ·　

@Past：被标注的元素必须是一个过去的日期。 ·　

@Future：被标注的元素必须是一个将来的日期。 ·　

@Pattern(value)：被标注的元素必须符合指定的正则表达式。

高级验证Spring Validation Spring Validation包含Bean Validation的实现——Hibernate Validation，并且提供了更多与Spring相关的功能。 

（1）为了使用到以上功能，需要引入Spring Validation依赖。 

2）使用注解标注需要验证的元素。以SubmitArticleQuery.java为例：

```java

import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@Setter
@Getter
public class SubmitArticleQuery {
    //标题
    @NotNull(message = "Title must not be null.")
    private String title;
    //副标题
    @NotNull(message = "Headline must not be null.")
    private String headline;
    //正文
    @NotNull(message = "Content must not be null.")
    private String content;
    //作者名
    @Author
    @NotNull(message = "Author must not be null.")
    private String author;
}
```

 （3）使用@Validated标注需要验证的入参。以HtmlController.java为例：

```java

@RequiredArgsConstructor
@Controller
public class HtmlController {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final BlogProperties blogProperties;
    private final ArticleService articleService;

    @GetMapping("/")
    public String blog(Model model) {
        model.addAttribute("title", blogProperties.getTitle());
        model.addAttribute("banner", blogProperties.getBanner());
        model.addAttribute("articles", StreamSupport.stream(articleRepository.findAllByOrderByAddedAtDesc().spliterator(), true)
                .map(this::render)
                .collect(Collectors.toList()));
        return "blog";
    }

    @GetMapping("/article/{slug}")
    public String article(@PathVariable String slug, Model model) {
        Article article = articleRepository.findBySlug(slug);
        if (article == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This article does not exist");
        }
        RenderedArticle renderedArticle = render(article);
        model.addAttribute("title", renderedArticle.getTitle());
        model.addAttribute("article", renderedArticle);
        return "article";
    }

    @GetMapping("/writing")
    public String article(Model model) {
        //用于填写页面的展示
        Iterable<User> userList = userRepository.findAll();
        model.addAttribute("title", "writing");
        model.addAttribute("users", userList);
        return "writing";
    }

    @PostMapping(value = "/article", headers = "Accept=application/xml", produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public Article submitArticleAndGetXml(@RequestBody @Validated SubmitArticleQuery query) {
        return submitArticle(query);
    }

    @PostMapping("/article")
    @ResponseBody
    public Article submitArticleAndGetJson(@RequestBody @Validated SubmitArticleQuery query) {
        return submitArticle(query);
    }

    private Article submitArticle(SubmitArticleQuery query) {
        //用于接收POST请求
        User author = userRepository.findByLogin(query.getAuthor());
        if (author == null) {
            //返回400错误码
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user does not exist");
        }
        return articleService.saveArticle(query, author);
    }

    private RenderedArticle render(Article article) {
        return new RenderedArticle()
                .setTitle(article.getTitle())
                .setHeadline(article.getHeadline())
                .setSlug(article.getSlug())
                .setContent(article.getContent())
                .setAuthor(article.getAuthor())
                .setAddedAt(CommonUtil.format(article.getAddedAt()));
    }

}

```

![image-20210722165321511](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210722165321511.png)





# HandlerInterceptor和HandlerInterceptorAdapter

在SpringBoot中我们可以使用HandlerInterceptorAdapter这个适配器来实现自己的拦截器。这样就可以拦截所有的请求并做相应的处理。

**应用场景**

- 日志记录，可以记录请求信息的日志，以便进行信息监控、信息统计等。
- 权限检查：如登陆检测，进入处理器检测是否登陆，如果没有直接返回到登陆页面。
- 性能监控：典型的是慢日志。

在HandlerInterceptorAdapter中主要提供了以下的方法：
preHandle：在方法被调用前执行。在该方法中可以做类似校验的功能。如果返回true，则继续调用下一个拦截器。如果返回false，则中断执行，也就是说我们想调用的方法 不会被执行，但是你可以修改response为你想要的响应。
postHandle：在方法执行后调用。
afterCompletion：在整个请求处理完毕后进行回调，也就是说视图渲染完毕或者调用方已经拿到响应。

在HandlerInterceptorAdapter中主要提供了以下的方法：

- preHandle：在方法被调用前执行。在该方法中可以做类似校验的功能。如果返回true，则继续调用下一个拦截器。如果返回false，则中断执行，也就是说我们想调用的方法 不会被执行，但是你可以修改response为你想要的响应。
- postHandle：在方法执行后调用。
- afterCompletion：在整个请求处理完毕后进行回调，也就是说视图渲染完毕或者调用方已经拿到响应

有时候我们可能只需要实现三个回调方法中的某一个，如果实现HandlerInterceptor接口的话，三个方法必须实现，不管你需不需要，此时spring提供了一个HandlerInterceptorAdapter适配器（种适配器设计模式的实现），允许我们只实现需要的回调方法。
这样在我们业务中比如要记录系统日志，日志肯定是在afterCompletion之后记录的，否则中途失败了，也记录了，那就扯淡了。一定是程序正常跑完后，我们记录下那些对数据库做个增删改的操作日志进数据库。所以我们只需要继承HandlerInterceptorAdapter，并重写afterCompletion一个方法即可，因为preHandle默认是true。

1. 拦截器执行顺序是按照Spring配置文件中定义的顺序而定的。
2. 会先按照顺序执行所有拦截器的preHandle方法，一直遇到return false为止，比如第二个preHandle方法是return false，则第三个以及以后所有拦截器都不会执行。若都是return true，则按顺序加载完preHandle方法。
3. 然后执行主方法（自己的controller接口），若中间抛出异常，则跟return false效果一致，不会继续执行postHandle，只会倒序执行afterCompletion方法。
4. 在主方法执行完业务逻辑（页面还未渲染数据）时，按倒序执行postHandle方法。若第三个拦截器的preHandle方法return false，则会执行第二个和第一个的postHandle方法和afterCompletion（postHandle都执行完才会执行这个，也就是页面渲染完数据后，执行after进行清理工作）方法。（postHandle和afterCompletion都是倒序执行）



# spring security和shiro的异同

相同点

1、认证功能2、授权功能3、加密功能4、会话管理5、缓存支持

6、rememberMe功能

不同点

1、Spring Security 基于Spring 开发，项目若使用 Spring 作为基础，配合 Spring Security 做权限更加方便，而 Shiro 需要和 Spring 进行整合开发；

2、Spring Security 功能比 Shiro 更加丰富些，例如安全维护方面；

3、Spring Security 社区资源相对比 Shiro 更加丰富；

Spring Security对Oauth、OpenID也有支持,Shiro则需要自己手动实现。而且Spring Security的权限细粒度更高

spring security 接口 RequestMatcher 用于匹配路径,对路径做特殊的请求，类似于shiro的抽象类 PathMatchingFilter，但是 RequestMatcher 作用粒度更细

 

4、Shiro 的配置和使用比较简单，Spring Security 上手复杂些；

5、Shiro 依赖性低，不需要任何框架和容器，可以独立运行.

6、shiro 不仅仅可以使用在web中，还支持非web项目它可以工作在任何应用环境中。在集群会话时Shiro最重要的一个好处或许就是它的会话是独立于容器的。

apache shiro的话，简单，易用，功能也强大，spring官网就是用的shiro，可见shiro的强大。

 



# SpringSecurity

Spring Security所解决的问题就是安全访问控制，而安全访问控制功能其实就是对所有进入系统的请求进行拦截，校验每个请求是否能够访问它所期望的资源。根据前边知识的学习，可以通过Filter或AOP等技术来实现，Spring
Security对Web资源的保护是靠Filter实现的





## 1.无处不在的 Authentication

玩过 Spring Security 的小伙伴都知道，在 Spring Security 中有一个非常重要的对象叫做 Authentication，我们可以在任何地方注入 Authentication 进而获取到当前登录用户信息，Authentication 本身是一个接口，它有很多实现类：

在这众多的实现类中，我们最常用的就是 UsernamePasswordAuthenticationToken 了，但是当我们打开这个类的源码后，却发现这个类平平无奇，他只有两个属性、两个构造方法以及若干个 get/set 方法；当然，他还有更多属性在它的父类上。

但是从它仅有的这两个属性中，我们也能大致看出，这个类就保存了我们登录用户的基本信息。那么我们的登录信息是如何存到这两个对象中的？这就要来梳理一下登录流程了。

## 2.登录流程

在 Spring Security 中，认证与授权的相关校验都是在一系列的过滤器链中完成的，在这一系列的过滤器链中，和认证相关的过滤器就是 UsernamePasswordAuthenticationFilter，



```java
/**
* Security配置类
*
* @author zhoubin
* @since 1.0.0

*/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
@Autowired
private IAdminService adminService;
@Autowired
private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
@Autowired
private RestfulAccessDeniedHandler restfulAccessDeniedHandler;
@Override
protected void configure(AuthenticationManagerBuilder auth) tadminows
Exception {
auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder())
;
}

@Override
protected void configure(HttpSecurity http) tadminows Exception {
//使用JWT，不需要csrf
http.csrf()
.disable()
//基于token，不需要session
.sessionManagement()
.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
.and()
.authorizeRequests()
//允许登录访问
.antMatchers("/login","/logout")
.permitAll()
//除上面外，所有请求都要求认证
.anyRequest()
.authenticated()
.and()
//禁用缓存
.headers()
.cacheControl();
//添加jwt 登录授权过滤器
http.addFilterBefore(jwtAuthenticationTokenFilter(),
UsernamePasswordAuthenticationFilter.class);
//添加自定义未授权和未登录结果返回
http.exceptionHandling()
.accessDeniedHandler(restfulAccessDeniedHandler)
.authenticationEntryPoint(restAuthenticationEntryPoint);
}
@Bean
public PasswordEncoder passwordEncoder() {
return new BCryptPasswordEncoder();
}
@Override
@Bean
public UserDetailsService userDetailsService() {
//获取登录用户信息
return username -> {
Admin admin = adminService.getAdminByUserName(username);
if (null != admin) {
return admin;
}
return null;
};
}
@Bean
public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
return new JwtAuthenticationTokenFilter();
}
}
```

## 3.认证流程

AuthenticationProvider
通过前面的Spring Security认证流程我们得知，认证管理器（AuthenticationManager）委托AuthenticationProvider完成认证工作。
AuthenticationProvider是一个接口，定义如下：

```java
public interface AuthenticationProvider {
Authentication authenticate(Authentication authentication) throws AuthenticationException;
boolean supports(Class<?> var1);
}
```

authenticate()方法定义了认证的实现过程，它的参数是一Authentication，里面包含了登录用户所提交的用户、密码等。而返回值也是一个Authentication，这个Authentication则是在认证成功后，将用户的权限及其他信息重新组装后生成。
Spring Security中维护着一个List\<AuthenticationProvider> 列表，存放多种认证方式，不同的认证方式使用不同的AuthenticationProvider。如使用用户名密码登录时，使用AuthenticationProvider1，短信登录时使用
AuthenticationProvider2等等这样的例子很多。
每个AuthenticationProvider需要实现supports（）方法来表明自己支持的认证方式，如我们使用表单方式认证，在提交请求时Spring Security会生成UsernamePasswordAuthenticationToken，它是一个Authentication，里面封装着用户提交的用户名、密码信息。而对应的，哪个AuthenticationProvider来处理它？
我们在DaoAuthenticationProvider的基类AbstractUserDetailsAuthenticationProvider发现:

```java
public boolean supports(Class<?> authentication) {
return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
}
```

也就是说当web表单提交用户名密码时，Spring Security由DaoAuthenticationProvider处理。

我们来看一下Authentication(认证信息)的结构，它是一个接口，我们之前提到的UsernamePasswordAuthenticationToken就是它的实现之一：

```java
public interface Authentication extends Principal, Serializable { 
Collection<? extends GrantedAuthority> getAuthorities(); 
Object getCredentials(); 
Object getDetails(); 
Object getPrincipal(); 
boolean isAuthenticated();
void setAuthenticated(boolean var1) throws IllegalArgumentException;
}
```

![image-20210816150429067](C:\Users\14172\AppData\Roaming\Typora\typora-user-images\image-20210816150429067.png)

UserDetailsService
1）认识UserDetailsService
现在咱们现在知道DaoAuthenticationProvider处理了web表单的认证逻辑，认证成功后既得到一个Authentication(UsernamePasswordAuthenticationToken实现)，里面包含了身份信息（Principal）。这个身份信息就是一个Object ，大多数情况下它可以被强转为UserDetails对象。
DaoAuthenticationProvider中包含了一个UserDetailsService实例，它负责根据用户名提取用户信息UserDetails(包含密码)，而后DaoAuthenticationProvider会去对比UserDetailsService提取的用户密码与用户提交的密码是否匹配作为认证成功的关键依据，因此可以通过将自定义的UserDetailsService 公开为spring bean来定义自定义身份验证。

```java
public interface UserDetailsService {
UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}

```

很多人把DaoAuthenticationProvider和UserDetailsService的职责搞混淆，其实UserDetailsService只负责从特定的地方（通常是数据库）加载用户信息，仅此而已。而DaoAuthenticationProvider的职责更大，它完成完整的认
证流程，同时会把UserDetails填充至Authentication。

```java
public interface UserDetails extends Serializable {
Collection<? extends GrantedAuthority> getAuthorities();
String getPassword();
String getUsername();
boolean isAccountNonExpired();
boolean isAccountNonLocked();
boolean isCredentialsNonExpired();
boolean isEnabled();
}
```

它和Authentication接口很类似，比如它们都拥有username，authorities。Authentication的getCredentials()与UserDetails中的getPassword()需要被区分对待，前者是用户提交的密码凭证，后者是用户实际存储的密码，认证
其实就是对这两者的比对。Authentication中的getAuthorities()实际是由UserDetails的getAuthorities()传递而形成的。还记得Authentication接口中的getDetails()方法吗？其中的UserDetails用户详细信息便是经过了
AuthenticationProvider认证之后被填充的。
通过实现UserDetailsService和UserDetails，我们可以完成对用户信息获取方式以及用户信息字段的扩展。
Spring Security提供的InMemoryUserDetailsManager(内存认证)，JdbcUserDetailsManager(jdbc认证)就是UserDetailsService的实现类，主要区别无非就是从内存还是从数据库加载用户。

自定义UserDetailsService

```java
@Service
public class SpringDataUserDetailsService implements UserDetailsService {
@Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//登录账号
System.out.println("username="+username);
//根据账号去数据库查询...
//这里暂时使用静态数据
UserDetails userDetails =
User.withUsername(username).password("123").authorities("p1").build();
return userDetails;
}
}

```



重启工程，请求认证，SpringDataUserDetailsService的loadUserByUsername方法被调用 ，查询用户信息





## 添加自定义未授权及未登录的结果返回

```java
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxxx.server.pojo.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
/**
* 当未登录或者token失效时访问接口时，自定义的返回结果
*
* @author zhoubin
* @since 1.0.0
*/
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
@Override
public void commence(HttpServletRequest request, HttpServletResponse
response, AuthenticationException authException) tadminows IOException,
ServletException {
response.setCharacterEncoding("UTF-8");
response.setContentType("application/json");
PrintWriter out = response.getWriter();
RespBean bean = RespBean.error("权限不足，请联系管理员！");
bean.setCode(401);
out.write(new ObjectMapper().writeValueAsString(bean));
out.flush();
out.close();
}
}
```

RestfulAccessDeniedHandler.java



```java
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xxxx.server.pojo.RespBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
/**
* 当访问接口没有权限时，自定义返回结果类
*
* @author zhoubin
* @since 1.0.0
*/
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
@Override
public void handle(HttpServletRequest request, HttpServletResponse response,
AccessDeniedException e) tadminows IOException, ServletException {
response.setCharacterEncoding("UTF-8");
response.setContentType("application/json");
PrintWriter out = response.getWriter();
RespBean bean = RespBean.error("权限不足，请联系管理员！");
bean.setCode(403);
out.write(new ObjectMapper().writeValueAsString(bean));
out.flush();
out.close();
}
}
```



## 授权

```java
@GetMapping(value = "/r/r1",produces = {"text/plain;charset=UTF‐8"})
public String r1(){
return " 访问资源1";
}
/**
* 测试资源2
* @return
*/
@GetMapping(value = "/r/r2",produces = {"text/plain;charset=UTF‐8"})
public String r2(){
return " 访问资源2";
}
-------------------------------------------------
//在安全配置类WebSecurityConfig.java中配置授权规则：
.antMatchers("/r/r1").hasAuthority("p1")
.antMatchers("/r/r2").hasAuthority("p2")
             
```

完整的：

```java
@Override
protected void configure(HttpSecurity http) throws Exception {
http
.authorizeRequests()
.antMatchers("/r/r1").hasAuthority("p1")
.antMatchers("/r/r2").hasAuthority("p2")
.antMatchers("/r/**").authenticated()
.anyRequest().permitAll()
.and()
.formLogin().successForwardUrl("/login‐success");
}
```



使用BCryptPasswordEncoder

1、在安全配置类中定义BCryptPasswordEncoder

```java
@Bean
public PasswordEncoder passwordEncoder() {
return new BCryptPasswordEncoder();
}
```

![image-20210824224114740](C:\Users\14172\Pictures\typora图片\image-20210824224114740.png)

## 会话

用户认证通过后，为了避免用户的每次操作都进行认证可将用户的信息保存在会话中。spring security提供会话管理，认证通过后将身份信息放入SecurityContextHolder上下文，SecurityContext与当前线程进行绑定，方便获取用户身份。

编写LoginController，实现/r/r1、/r/r2的测试资源，并修改loginSuccess方法，注意getUsername方法，SpringSecurity获取当前登录用户信息的方法为SecurityContextHolder.getContext().getAuthentication()

```java
@RestController
public class LoginController {
/**
* 用户登录成功
* @return
*/
@RequestMapping(value = "/login‐success",produces = {"text/plain;charset=UTF‐8"})
public String loginSuccess(){
String username = getUsername();
    return username + " 登录成功";
}
/**
* 获取当前登录用户名
* @return
*/
private String getUsername(){
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
if(!authentication.isAuthenticated()){
return null;
}
Object principal = authentication.getPrincipal();
String username = null;
if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
username =
((org.springframework.security.core.userdetails.UserDetails)principal).getUsername();
} else {
username = principal.toString();
}
return username;
}
/**
* 测试资源1
* @return
*/
@GetMapping(value = "/r/r1",produces = {"text/plain;charset=UTF‐8"})
public String r1(){
String username = getUsername();
return username + " 访问资源1";
}
```

会话控制
我们可以通过以下选项准确控制会话何时创建以及Spring Security如何与之交互

![image-20210824224446254](C:\Users\14172\Pictures\typora图片\image-20210824224446254.png)

若使用stateless，则说明Spring Security对登录成功的用户不会创建Session了，你的应用程序也不会允许新建session。并且它会暗示不使用cookie，所以每个请求都需要重新进行身份验证。这种无状态架构适用于REST API及其无状态认证机制。









什么是CSRF攻击？
CSRF代表跨站请求伪造。这是一种攻击，迫使最终用户在当前通过身份验证的Web应用程序上执行不需要的操作。CSRF攻击专门针对状态改变请求，而不是数据窃取，因为攻击者无法查看对伪造请求的响应。



# springboot的优化

针对目前的容器优化，目前来说没有太多地方，需要考虑如下几个点

- 线程数
- 超时时间
- jvm优化

针对上述的优化点来说，首先线程数是一个重点，初始线程数和最大线程数，初始线程数保障启动的时候，如果有大量用户访问，能够很稳定的接受请求。

而最大线程数量用来保证系统的稳定性，而超时时间用来保障连接数不容易被压垮，如果大批量的请求过来，延迟比较高，不容易把线程打满。这种情况在生产中是比较常见的 ，一旦网络不稳定，宁愿丢包也不愿意把机器压垮。

jvm优化一般来说没有太多场景，无非就是加大初始的堆，和最大限制堆,当然也不是无限增大，根据的情况进快速开始

在spring boot配置文件中application.yml，添加以下配置

```
server:
  tomcat:
    min-spare-threads: 20
    max-threads: 100
  connection-timeout: 5000
```

这块对tomcat进行了一个优化配置，最大线程数是100，初始化线程是20,超时时间是5000ms

## Jvm优化

这块主要不是谈如何优化，jvm优化是一个需要场景化的，没有什么太多特定参数，一般来说在server端运行都会指定如下参数

> 初始内存和最大内存基本会设置成一样的，具体大小根据场景设置，-server是一个必须要用的参数，至于收集器这些使用默认的就可以了，除非有特定需求。

### 1.使用-server模式

设置JVM使用server模式。64位JDK默认启动该模式

```
java -server -jar springboot-1.0.jar
```

### 2.指定堆参数

这个根据服务器的内存大小，来设置堆参数。

- -Xms :设置Java堆栈的初始化大小
- -Xmx :设置最大的java堆大小

```
java -server -Xms512m -Xmx768m  -jar springboot-1.0.jar
```

设置初始化堆内存为512MB，最大为768MB。

### 3.远程Debug

在服务器上将启动参数修改为：

```
java -Djavax.net.debug=
ssl -Xdebug -Xnoagent -Djava.compiler=
NONE -Xrunjdwp:transport=
dt_socket,server=y,suspend=
n,address=8888 -jar springboot-1.0.jar
```

这个时候服务端远程Debug模式开启，端口号为8888。

通常我们的web服务都输部署在服务器上的，在window使用jconsole是很方便的，相对于Linux就有一些麻烦了，需要进行一些设置。

**1.查看hostname,首先使用**

```
hostname -i
```

查看，服务器的hostname为127.0.0.1，这个是不对的，需要进行修改

**2.修改hostname**

修改/etc/hosts文件，将其第一行的“127.0.0.1 localhost.localdomain localhost”，修改为：“192.168.44.128 localhost.localdomain localhost”.“192.168.44.128”为实际的服务器的IP地

**3.重启Linux，在服务器上输入hostname -i，查看实际设置的IP地址是否为你设置的**

**4.启动服务，参数为：**

```
java -jar -Djava.rmi.server.hostname=192.168.44.128 -
Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=911 -
Dcom.sun.management.jmxremote.ssl=false -
Dcom.sun.management.jmxremote.authenticate=false jantent-1.0-SNAPSHOT.jar
```

ip为192.168.44.128，端口为911 。

**5.打开Jconsole，进行远程连接,输入IP和端口即可**

**![图片](C:\Users\14172\Pictures\typora图片\640.webp)**

















#  SpringBootServletInitializer

```
要配置应用程序，要么覆盖configure(SpringApplicationBuilder)方法（调用SpringApplicationBuilder.sources(Class...) ）或使初始化程序本身成为@Configuration 。 如果您将SpringBootServletInitializer与其他WebApplicationInitializers结合使用，您可能还需要添加@Ordered注释来配置特定的启动顺序。
请注意，只有在构建 war 文件并部署它时才需要 WebApplicationInitializer。 如果您更喜欢运行嵌入式 Web 服务器，那么您根本不需要它
```

为了支持可以不使用web.xml,提供了ServletContainerInitializer，它可以通过SPI机制，当启动web容器的时候，会自动到添加的相应jar包下找到META-INF/services下以ServletContainerInitializer的全路径名称命名的文件，它的内容为ServletContainerInitializer实现类的全路径，将它们实例化


```java
@SpringBootApplication(scanBasePackages={"net.mingsoft"})
public class MSServletInitializer extends SpringBootServletInitializer {

   @Override
   protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
      return builder.sources(MSApplication.class);
   }
}
```





# spring boot的spi机制

（待写）

# springboot的自动配置注解

springboot配置文件的装配过程

1、springboot在启动的时候会加载主配置类，开启了@EnableAutoConfiguration。

2、@EnableAutoConfiguration的作用：

- 利用AutoConfigurationImportSelector给容器导入一些组件。
- 查看selectImports方法的内容，返回一个AutoConfigurationEntry

```java
AutoConfigurationEntry autoConfigurationEntry = getAutoConfigurationEntry(autoConfigurationMetadata,
      annotationMetadata);
------
List<String> configurations = getCandidateConfigurations(annotationMetadata, attributes);
------
protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
		List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),
				getBeanClassLoader());
		Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you "
				+ "are using a custom packaging, make sure that file is correct.");
		return configurations;
	}
```

- 可以看到SpringFactoriesLoader.loadFactoryNames，继续看又调用了loadSpringFactories方法，获取META-INF/spring.factories资源文件

```java
public static List<String> loadFactoryNames(Class<?> factoryType, @Nullable ClassLoader classLoader) {
		String factoryTypeName = factoryType.getName();
		return loadSpringFactories(classLoader).getOrDefault(factoryTypeName, Collections.emptyList());
	}

	private static Map<String, List<String>> loadSpringFactories(@Nullable ClassLoader classLoader) {
		MultiValueMap<String, String> result = cache.get(classLoader);
		if (result != null) {
			return result;
		}

		try {
			Enumeration<URL> urls = (classLoader != null ?
					classLoader.getResources(FACTORIES_RESOURCE_LOCATION) :
					ClassLoader.getSystemResources(FACTORIES_RESOURCE_LOCATION));
			result = new LinkedMultiValueMap<>();
			while (urls.hasMoreElements()) {
				URL url = urls.nextElement();
				UrlResource resource = new UrlResource(url);
				Properties properties = PropertiesLoaderUtils.loadProperties(resource);
				for (Map.Entry<?, ?> entry : properties.entrySet()) {
					String factoryTypeName = ((String) entry.getKey()).trim();
					for (String factoryImplementationName : StringUtils.commaDelimitedListToStringArray((String) entry.getValue())) {
						result.add(factoryTypeName, factoryImplementationName.trim());
					}
				}
			}
			cache.put(classLoader, result);
			return result;
		}
		catch (IOException ex) {
			throw new IllegalArgumentException("Unable to load factories from location [" +
					FACTORIES_RESOURCE_LOCATION + "]", ex);
		}
	}
```

总结：将类路径下 META-INF/spring.factories 里面配置的所有EnableAutoConfiguration的值加入到了容器中；每一个xxxAutoConfiguration类都是容器中的一个组件，最后都加入到容器中，用来做自动配置，每一个自动配置类都可以进行自动配置功能

使用HttpEncodingAutoConfiguration来解释自动装配原理

```java
/*
表名这是一个配置类，
*/
@Configuration(proxyBeanMethods = false)
/*
启动指定类的ConfigurationProperties功能,进入HttpProperties查看，将配置文件中对应的值和HttpProperties绑定起来，并把HttpProperties加入到ioc容器中
*/
@EnableConfigurationProperties(HttpProperties.class)
/*
spring底层@Confitional注解，根据不同的条件判断，如果满足指定的条件，整个配置类里面的配置就会生效
此时表示判断当前应用是否是web应用，如果是，那么配置类生效
*/
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
/*
判断当前项目由没有这个类CharacterEncodingFilter，springmvc中进行乱码解决的过滤器
*/
@ConditionalOnClass(CharacterEncodingFilter.class)
/*
判断配置文件中是否存在某个配置：spring.http.encoding.enabled
如果不存在，判断也是成立的，
即使我们配置文件中不配置spring.http.encoding.enabled=true，也是默认生效的
*/
@ConditionalOnProperty(prefix = "spring.http.encoding", value = "enabled", matchIfMissing = true)
public class HttpEncodingAutoConfiguration {

    //和springboot的配置文件映射
	private final HttpProperties.Encoding properties;

    //只有一个有参构造器的情况下，参数的值就会从容器中拿
	public HttpEncodingAutoConfiguration(HttpProperties properties) {
		this.properties = properties.getEncoding();
	}

    //给容器中添加一个组件，这个组件的某些值需要从properties中获取
	@Bean
	@ConditionalOnMissingBean//判断容器中是否有此组件
	public CharacterEncodingFilter characterEncodingFilter() {
		CharacterEncodingFilter filter = new OrderedCharacterEncodingFilter();
		filter.setEncoding(this.properties.getCharset().name());
		filter.setForceRequestEncoding(this.properties.shouldForce(Type.REQUEST));
		filter.setForceResponseEncoding(this.properties.shouldForce(Type.RESPONSE));
		return filter;
	}

	@Bean
	public LocaleCharsetMappingsCustomizer localeCharsetMappingsCustomizer() {
		return new LocaleCharsetMappingsCustomizer(this.properties);
	}

	private static class LocaleCharsetMappingsCustomizer
			implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>, Ordered {

		private final HttpProperties.Encoding properties;

		LocaleCharsetMappingsCustomizer(HttpProperties.Encoding properties) {
			this.properties = properties;
		}

		@Override
		public void customize(ConfigurableServletWebServerFactory factory) {
			if (this.properties.getMapping() != null) {
				factory.setLocaleCharsetMappings(this.properties.getMapping());
			}
		}

		@Override
		public int getOrder() {
			return 0;
		}
	}
}

```

根据当前不同的条件判断，决定这个配置类是否生效！

总结：

​		1、springboot启动会加载大量的自动配置类

​		2、查看需要的功能有没有在springboot默认写好的自动配置类中华

​		3、查看这个自动配置类到底配置了哪些组件

​		4、给容器中自动配置类添加组件的时候，会从properties类中获取属性

@Conditional：自动配置类在一定条件下才能生效

| @Conditional扩展注解            | 作用                                     |
| ------------------------------- | ---------------------------------------- |
| @ConditionalOnJava              | 系统的java版本是否符合要求               |
| @ConditionalOnBean              | 容器中存在指定Bean                       |
| @ConditionalOnMissingBean       | 容器中不存在指定Bean                     |
| @ConditionalOnExpression        | 满足SpEL表达式                           |
| @ConditionalOnClass             | 系统中有指定的类                         |
| @ConditionalOnMissingClass      | 系统中没有指定的类                       |
| @ConditionalOnSingleCandidate   | 容器中只有一个指定的Bean，或者是首选Bean |
| @ConditionalOnProperty          | 系统中指定的属性是否有指定的值           |
| @ConditionalOnResource          | 类路径下是否存在指定资源文件             |
| @ConditionOnWebApplication      | 当前是web环境                            |
| @ConditionalOnNotWebApplication | 当前不是web环境                          |
| @ConditionalOnJndi              | JNDI存在指定项                           |





# springboot源码(二)：自动装配原理

​		在之前的课程中我们讲解了springboot的启动过程，其实在面试过程中问的最多的可能是自动装配的原理，而自动装配是在启动过程中完成，只不过在刚开始的时候我们选择性的跳过了，下面详细讲解自动装配的过程。



1、在springboot的启动过程中，有一个步骤是创建上下文，如果不记得可以看下面的代码：

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
			ApplicationArguments applicationArguments = new DefaultApplicationArguments(args);
			ConfigurableEnvironment environment = prepareEnvironment(listeners, applicationArguments);
			configureIgnoreBeanInfo(environment);
			Banner printedBanner = printBanner(environment);
			context = createApplicationContext();
			exceptionReporters = getSpringFactoriesInstances(SpringBootExceptionReporter.class,
					new Class[] { ConfigurableApplicationContext.class }, context);
            //此处完成自动装配的过程
			prepareContext(context, environment, listeners, applicationArguments, printedBanner);
			refreshContext(context);
			afterRefresh(context, applicationArguments);
			stopWatch.stop();
			if (this.logStartupInfo) {
				new StartupInfoLogger(this.mainApplicationClass).logStarted(getApplicationLog(), stopWatch);
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

2、在prepareContext方法中查找load方法，一层一层向内点击，找到最终的load方法

```java
//prepareContext方法
	private void prepareContext(ConfigurableApplicationContext context, ConfigurableEnvironment environment,
			SpringApplicationRunListeners listeners, ApplicationArguments applicationArguments, Banner printedBanner) {
		context.setEnvironment(environment);
		postProcessApplicationContext(context);
		applyInitializers(context);
		listeners.contextPrepared(context);
		if (this.logStartupInfo) {
			logStartupInfo(context.getParent() == null);
			logStartupProfileInfo(context);
		}
		// Add boot specific singleton beans
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		beanFactory.registerSingleton("springApplicationArguments", applicationArguments);
		if (printedBanner != null) {
			beanFactory.registerSingleton("springBootBanner", printedBanner);
		}
		if (beanFactory instanceof DefaultListableBeanFactory) {
			((DefaultListableBeanFactory) beanFactory)
					.setAllowBeanDefinitionOverriding(this.allowBeanDefinitionOverriding);
		}
		if (this.lazyInitialization) {
			context.addBeanFactoryPostProcessor(new LazyInitializationBeanFactoryPostProcessor());
		}
		// Load the sources
		Set<Object> sources = getAllSources();
		Assert.notEmpty(sources, "Sources must not be empty");
        //load方法完成该功能
		load(context, sources.toArray(new Object[0]));
		listeners.contextLoaded(context);
	}


	/**
	 * Load beans into the application context.
	 * @param context the context to load beans into
	 * @param sources the sources to load
	 * 加载bean对象到context中
	 */
	protected void load(ApplicationContext context, Object[] sources) {
		if (logger.isDebugEnabled()) {
			logger.debug("Loading source " + StringUtils.arrayToCommaDelimitedString(sources));
		}
        //获取bean对象定义的加载器
		BeanDefinitionLoader loader = createBeanDefinitionLoader(getBeanDefinitionRegistry(context), sources);
		if (this.beanNameGenerator != null) {
			loader.setBeanNameGenerator(this.beanNameGenerator);
		}
		if (this.resourceLoader != null) {
			loader.setResourceLoader(this.resourceLoader);
		}
		if (this.environment != null) {
			loader.setEnvironment(this.environment);
		}
		loader.load();
	}

	/**
	 * Load the sources into the reader.
	 * @return the number of loaded beans
	 */
	int load() {
		int count = 0;
		for (Object source : this.sources) {
			count += load(source);
		}
		return count;
	}
```

3、实际执行load的是BeanDefinitionLoader中的load方法，如下：

```java
	//实际记载bean的方法
	private int load(Object source) {
		Assert.notNull(source, "Source must not be null");
        //如果是class类型，启用注解类型
		if (source instanceof Class<?>) {
			return load((Class<?>) source);
		}
        //如果是resource类型，启动xml解析
		if (source instanceof Resource) {
			return load((Resource) source);
		}
        //如果是package类型，启用扫描包，例如@ComponentScan
		if (source instanceof Package) {
			return load((Package) source);
		}
        //如果是字符串类型，直接加载
		if (source instanceof CharSequence) {
			return load((CharSequence) source);
		}
		throw new IllegalArgumentException("Invalid source type " + source.getClass());
	}
```

4、下面方法将用来判断是否资源的类型，是使用groovy加载还是使用注解的方式

```java
	private int load(Class<?> source) {
        //判断使用groovy脚本
		if (isGroovyPresent() && GroovyBeanDefinitionSource.class.isAssignableFrom(source)) {
			// Any GroovyLoaders added in beans{} DSL can contribute beans here
			GroovyBeanDefinitionSource loader = BeanUtils.instantiateClass(source, GroovyBeanDefinitionSource.class);
			load(loader);
		}
        //使用注解加载
		if (isComponent(source)) {
			this.annotatedReader.register(source);
			return 1;
		}
		return 0;
	}
```

5、下面方法判断启动类中是否包含@Component注解，但是会神奇的发现我们的启动类中并没有该注解，继续更进发现MergedAnnotations类传入了一个参数SearchStrategy.TYPE_HIERARCHY，会查找继承关系中是否包含这个注解，@SpringBootApplication-->@SpringBootConfiguration-->@Configuration-->@Component,当找到@Component注解之后，会把该对象注册到AnnotatedBeanDefinitionReader对象中

```java
private boolean isComponent(Class<?> type) {
   // This has to be a bit of a guess. The only way to be sure that this type is
   // eligible is to make a bean definition out of it and try to instantiate it.
   if (MergedAnnotations.from(type, SearchStrategy.TYPE_HIERARCHY).isPresent(Component.class)) {
      return true;
   }
   // Nested anonymous classes are not eligible for registration, nor are groovy
   // closures
   return !type.getName().matches(".*\\$_.*closure.*") && !type.isAnonymousClass()
         && type.getConstructors() != null && type.getConstructors().length != 0;
}

	/**
	 * Register a bean from the given bean class, deriving its metadata from
	 * class-declared annotations.
	 * 从给定的bean class中注册一个bean对象，从注解中找到相关的元数据
	 */
	private <T> void doRegisterBean(Class<T> beanClass, @Nullable String name,
			@Nullable Class<? extends Annotation>[] qualifiers, @Nullable Supplier<T> supplier,
			@Nullable BeanDefinitionCustomizer[] customizers) {

		AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(beanClass);
		if (this.conditionEvaluator.shouldSkip(abd.getMetadata())) {
			return;
		}

		abd.setInstanceSupplier(supplier);
		ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(abd);
		abd.setScope(scopeMetadata.getScopeName());
		String beanName = (name != null ? name : this.beanNameGenerator.generateBeanName(abd, this.registry));

		AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);
		if (qualifiers != null) {
			for (Class<? extends Annotation> qualifier : qualifiers) {
				if (Primary.class == qualifier) {
					abd.setPrimary(true);
				}
				else if (Lazy.class == qualifier) {
					abd.setLazyInit(true);
				}
				else {
					abd.addQualifier(new AutowireCandidateQualifier(qualifier));
				}
			}
		}
		if (customizers != null) {
			for (BeanDefinitionCustomizer customizer : customizers) {
				customizer.customize(abd);
			}
		}

		BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, beanName);
		definitionHolder = AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder, this.registry);
		BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, this.registry);
	}

	/**
	 * Register the given bean definition with the given bean factory.
	 * 注册主类，如果有别名可以设置别名
	 */
	public static void registerBeanDefinition(
			BeanDefinitionHolder definitionHolder, BeanDefinitionRegistry registry)
			throws BeanDefinitionStoreException {

		// Register bean definition under primary name.
		String beanName = definitionHolder.getBeanName();
		registry.registerBeanDefinition(beanName, definitionHolder.getBeanDefinition());

		// Register aliases for bean name, if any.
		String[] aliases = definitionHolder.getAliases();
		if (aliases != null) {
			for (String alias : aliases) {
				registry.registerAlias(beanName, alias);
			}
		}
	}

//@SpringBootApplication
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
		@Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
public @interface SpringBootApplication {}

//@SpringBootConfiguration
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Configuration
public @interface SpringBootConfiguration {}

//@Configuration
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Configuration {}
```

当看完上述代码之后，只是完成了启动对象的注入，自动装配还没有开始，下面开始进入到自动装配。

6、自动装配入口，从刷新容器开始

```java
@Override
	public void refresh() throws BeansException, IllegalStateException {
		synchronized (this.startupShutdownMonitor) {
			// Prepare this context for refreshing.
			prepareRefresh();

			// Tell the subclass to refresh the internal bean factory.
			ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

			// Prepare the bean factory for use in this context.
			prepareBeanFactory(beanFactory);

			try {
				// Allows post-processing of the bean factory in context subclasses.
				postProcessBeanFactory(beanFactory);

				// Invoke factory processors registered as beans in the context.
                // 此处是自动装配的入口
				invokeBeanFactoryPostProcessors(beanFactory);
            }
```

7、在invokeBeanFactoryPostProcessors方法中完成bean的实例化和执行

```java
/**
	 * Instantiate and invoke all registered BeanFactoryPostProcessor beans,
	 * respecting explicit order if given.
	 * <p>Must be called before singleton instantiation.
	 */
	protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        //开始执行beanFactoryPostProcessor对应实现类,需要知道的是beanFactoryPostProcessor是spring的扩展接口，在刷新容器之前，该接口可以用来修改bean元数据信息
		PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors(beanFactory, getBeanFactoryPostProcessors());

		// Detect a LoadTimeWeaver and prepare for weaving, if found in the meantime
		// (e.g. through an @Bean method registered by ConfigurationClassPostProcessor)
		if (beanFactory.getTempClassLoader() == null && beanFactory.containsBean(LOAD_TIME_WEAVER_BEAN_NAME)) {
			beanFactory.addBeanPostProcessor(new LoadTimeWeaverAwareProcessor(beanFactory));
			beanFactory.setTempClassLoader(new ContextTypeMatchClassLoader(beanFactory.getBeanClassLoader()));
		}
	}
```

8、查看invokeBeanFactoryPostProcessors的具体执行方法

```java
	public static void invokeBeanFactoryPostProcessors(
			ConfigurableListableBeanFactory beanFactory, List<BeanFactoryPostProcessor> beanFactoryPostProcessors) {

		// Invoke BeanDefinitionRegistryPostProcessors first, if any.
		Set<String> processedBeans = new HashSet<>();

		if (beanFactory instanceof BeanDefinitionRegistry) {
			BeanDefinitionRegistry registry = (BeanDefinitionRegistry) beanFactory;
			List<BeanFactoryPostProcessor> regularPostProcessors = new ArrayList<>();
			List<BeanDefinitionRegistryPostProcessor> registryProcessors = new ArrayList<>();
			//开始遍历三个内部类，如果属于BeanDefinitionRegistryPostProcessor子类，加入到bean注册的集合，否则加入到regularPostProcessors
			for (BeanFactoryPostProcessor postProcessor : beanFactoryPostProcessors) {
				if (postProcessor instanceof BeanDefinitionRegistryPostProcessor) {
					BeanDefinitionRegistryPostProcessor registryProcessor =
							(BeanDefinitionRegistryPostProcessor) postProcessor;
					registryProcessor.postProcessBeanDefinitionRegistry(registry);
					registryProcessors.add(registryProcessor);
				}
				else {
					regularPostProcessors.add(postProcessor);
				}
			}

			// Do not initialize FactoryBeans here: We need to leave all regular beans
			// uninitialized to let the bean factory post-processors apply to them!
			// Separate between BeanDefinitionRegistryPostProcessors that implement
			// PriorityOrdered, Ordered, and the rest.
			List<BeanDefinitionRegistryPostProcessor> currentRegistryProcessors = new ArrayList<>();

			// First, invoke the BeanDefinitionRegistryPostProcessors that implement PriorityOrdered.
            //通过BeanDefinitionRegistryPostProcessor获取到对应的处理类“org.springframework.context.annotation.internalConfigurationAnnotationProcessor”，但是需要注意的是这个类在springboot中搜索不到，这个类的完全限定名在AnnotationConfigEmbeddedWebApplicationContext中，在进行初始化的时候会装配几个类，在创建AnnotatedBeanDefinitionReader对象的时候会将该类注册到bean对象中，此处可以看到internalConfigurationAnnotationProcessor为bean名称，容器中真正的类是ConfigurationClassPostProcessor
			String[] postProcessorNames =
					beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
            //首先执行类型为PriorityOrdered的BeanDefinitionRegistryPostProcessor
            //PriorityOrdered类型表明为优先执行
			for (String ppName : postProcessorNames) {
				if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
                    //获取对应的bean
					currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
                    //用来存储已经执行过的BeanDefinitionRegistryPostProcessor
					processedBeans.add(ppName);
				}
			}
			sortPostProcessors(currentRegistryProcessors, beanFactory);
			registryProcessors.addAll(currentRegistryProcessors);
            //开始执行装配逻辑
			invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
			currentRegistryProcessors.clear();

			// Next, invoke the BeanDefinitionRegistryPostProcessors that implement Ordered.
            //其次执行类型为Ordered的BeanDefinitionRegistryPostProcessor
            //Ordered表明按顺序执行
			postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
			for (String ppName : postProcessorNames) {
				if (!processedBeans.contains(ppName) && beanFactory.isTypeMatch(ppName, Ordered.class)) {
					currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
					processedBeans.add(ppName);
				}
			}
			sortPostProcessors(currentRegistryProcessors, beanFactory);
			registryProcessors.addAll(currentRegistryProcessors);
			invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
			currentRegistryProcessors.clear();

			// Finally, invoke all other BeanDefinitionRegistryPostProcessors until no further ones appear.
            //循环中执行类型不为PriorityOrdered，Ordered类型的BeanDefinitionRegistryPostProcessor
			boolean reiterate = true;
			while (reiterate) {
				reiterate = false;
				postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
				for (String ppName : postProcessorNames) {
					if (!processedBeans.contains(ppName)) {
						currentRegistryProcessors.add(beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class));
						processedBeans.add(ppName);
						reiterate = true;
					}
				}
				sortPostProcessors(currentRegistryProcessors, beanFactory);
				registryProcessors.addAll(currentRegistryProcessors);
				invokeBeanDefinitionRegistryPostProcessors(currentRegistryProcessors, registry);
				currentRegistryProcessors.clear();
			}

			// Now, invoke the postProcessBeanFactory callback of all processors handled so far.	
            //执行父类方法，优先执行注册处理类
			invokeBeanFactoryPostProcessors(registryProcessors, beanFactory);
            //执行有规则处理类
			invokeBeanFactoryPostProcessors(regularPostProcessors, beanFactory);
		}

		else {
			// Invoke factory processors registered with the context instance.
			invokeBeanFactoryPostProcessors(beanFactoryPostProcessors, beanFactory);
		}

		// Do not initialize FactoryBeans here: We need to leave all regular beans
		// uninitialized to let the bean factory post-processors apply to them!
		String[] postProcessorNames =
				beanFactory.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false);

		// Separate between BeanFactoryPostProcessors that implement PriorityOrdered,
		// Ordered, and the rest.
		List<BeanFactoryPostProcessor> priorityOrderedPostProcessors = new ArrayList<>();
		List<String> orderedPostProcessorNames = new ArrayList<>();
		List<String> nonOrderedPostProcessorNames = new ArrayList<>();
		for (String ppName : postProcessorNames) {
			if (processedBeans.contains(ppName)) {
				// skip - already processed in first phase above
			}
			else if (beanFactory.isTypeMatch(ppName, PriorityOrdered.class)) {
				priorityOrderedPostProcessors.add(beanFactory.getBean(ppName, BeanFactoryPostProcessor.class));
			}
			else if (beanFactory.isTypeMatch(ppName, Ordered.class)) {
				orderedPostProcessorNames.add(ppName);
			}
			else {
				nonOrderedPostProcessorNames.add(ppName);
			}
		}

		// First, invoke the BeanFactoryPostProcessors that implement PriorityOrdered.
		sortPostProcessors(priorityOrderedPostProcessors, beanFactory);
		invokeBeanFactoryPostProcessors(priorityOrderedPostProcessors, beanFactory);

		// Next, invoke the BeanFactoryPostProcessors that implement Ordered.
		List<BeanFactoryPostProcessor> orderedPostProcessors = new ArrayList<>(orderedPostProcessorNames.size());
		for (String postProcessorName : orderedPostProcessorNames) {
			orderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
		}
		sortPostProcessors(orderedPostProcessors, beanFactory);
		invokeBeanFactoryPostProcessors(orderedPostProcessors, beanFactory);

		// Finally, invoke all other BeanFactoryPostProcessors.
		List<BeanFactoryPostProcessor> nonOrderedPostProcessors = new ArrayList<>(nonOrderedPostProcessorNames.size());
		for (String postProcessorName : nonOrderedPostProcessorNames) {
			nonOrderedPostProcessors.add(beanFactory.getBean(postProcessorName, BeanFactoryPostProcessor.class));
		}
		invokeBeanFactoryPostProcessors(nonOrderedPostProcessors, beanFactory);

		// Clear cached merged bean definitions since the post-processors might have
		// modified the original metadata, e.g. replacing placeholders in values...
		beanFactory.clearMetadataCache();
	}
```

9、开始执行自动配置逻辑（启动类指定的配置，非默认配置），可以通过debug的方式一层层向里进行查找，会发现最终会在ConfigurationClassParser类中，此类是所有配置类的解析类，所有的解析逻辑在parser.parse(candidates)中

```java
public void parse(Set<BeanDefinitionHolder> configCandidates) {
		for (BeanDefinitionHolder holder : configCandidates) {
			BeanDefinition bd = holder.getBeanDefinition();
			try {
                //是否是注解类
				if (bd instanceof AnnotatedBeanDefinition) {
					parse(((AnnotatedBeanDefinition) bd).getMetadata(), holder.getBeanName());
				}
				else if (bd instanceof AbstractBeanDefinition && ((AbstractBeanDefinition) bd).hasBeanClass()) {
					parse(((AbstractBeanDefinition) bd).getBeanClass(), holder.getBeanName());
				}
				else {
					parse(bd.getBeanClassName(), holder.getBeanName());
				}
			}
			catch (BeanDefinitionStoreException ex) {
				throw ex;
			}
			catch (Throwable ex) {
				throw new BeanDefinitionStoreException(
						"Failed to parse configuration class [" + bd.getBeanClassName() + "]", ex);
			}
		}
    	//执行配置类
		this.deferredImportSelectorHandler.process();
	}
-------------------
    	protected final void parse(AnnotationMetadata metadata, String beanName) throws IOException {
		processConfigurationClass(new ConfigurationClass(metadata, beanName));
	}
-------------------
    protected void processConfigurationClass(ConfigurationClass configClass) throws IOException {
		if (this.conditionEvaluator.shouldSkip(configClass.getMetadata(), ConfigurationPhase.PARSE_CONFIGURATION)) {
			return;
		}

		ConfigurationClass existingClass = this.configurationClasses.get(configClass);
		if (existingClass != null) {
			if (configClass.isImported()) {
				if (existingClass.isImported()) {
					existingClass.mergeImportedBy(configClass);
				}
				// Otherwise ignore new imported config class; existing non-imported class overrides it.
				return;
			}
			else {
				// Explicit bean definition found, probably replacing an import.
				// Let's remove the old one and go with the new one.
				this.configurationClasses.remove(configClass);
				this.knownSuperclasses.values().removeIf(configClass::equals);
			}
		}

		// Recursively process the configuration class and its superclass hierarchy.
		SourceClass sourceClass = asSourceClass(configClass);
		do {
            //循环处理bean,如果有父类，则处理父类，直至结束
			sourceClass = doProcessConfigurationClass(configClass, sourceClass);
		}
		while (sourceClass != null);

		this.configurationClasses.put(configClass, configClass);
	}
```

10、继续跟进doProcessConfigurationClass方法，此方式是支持注解配置的核心逻辑

```java
/**
	 * Apply processing and build a complete {@link ConfigurationClass} by reading the
	 * annotations, members and methods from the source class. This method can be called
	 * multiple times as relevant sources are discovered.
	 * @param configClass the configuration class being build
	 * @param sourceClass a source class
	 * @return the superclass, or {@code null} if none found or previously processed
	 */
	@Nullable
	protected final SourceClass doProcessConfigurationClass(ConfigurationClass configClass, SourceClass sourceClass)
			throws IOException {

        //处理内部类逻辑，由于传来的参数是启动类，并不包含内部类，所以跳过
		if (configClass.getMetadata().isAnnotated(Component.class.getName())) {
			// Recursively process any member (nested) classes first
			processMemberClasses(configClass, sourceClass);
		}

		// Process any @PropertySource annotations
        //针对属性配置的解析
		for (AnnotationAttributes propertySource : AnnotationConfigUtils.attributesForRepeatable(
				sourceClass.getMetadata(), PropertySources.class,
				org.springframework.context.annotation.PropertySource.class)) {
			if (this.environment instanceof ConfigurableEnvironment) {
				processPropertySource(propertySource);
			}
			else {
				logger.info("Ignoring @PropertySource annotation on [" + sourceClass.getMetadata().getClassName() +
						"]. Reason: Environment must implement ConfigurableEnvironment");
			}
		}

		// Process any @ComponentScan annotations
        // 这里是根据启动类@ComponentScan注解来扫描项目中的bean
		Set<AnnotationAttributes> componentScans = AnnotationConfigUtils.attributesForRepeatable(
				sourceClass.getMetadata(), ComponentScans.class, ComponentScan.class);
		if (!componentScans.isEmpty() &&
				!this.conditionEvaluator.shouldSkip(sourceClass.getMetadata(), ConfigurationPhase.REGISTER_BEAN)) {
            
			for (AnnotationAttributes componentScan : componentScans) {
				// The config class is annotated with @ComponentScan -> perform the scan immediately
                //遍历项目中的bean，如果是注解定义的bean，则进一步解析
				Set<BeanDefinitionHolder> scannedBeanDefinitions =
						this.componentScanParser.parse(componentScan, sourceClass.getMetadata().getClassName());
				// Check the set of scanned definitions for any further config classes and parse recursively if needed
				for (BeanDefinitionHolder holder : scannedBeanDefinitions) {
					BeanDefinition bdCand = holder.getBeanDefinition().getOriginatingBeanDefinition();
					if (bdCand == null) {
						bdCand = holder.getBeanDefinition();
					}
					if (ConfigurationClassUtils.checkConfigurationClassCandidate(bdCand, this.metadataReaderFactory)) {
                        //递归解析，所有的bean,如果有注解，会进一步解析注解中包含的bean
						parse(bdCand.getBeanClassName(), holder.getBeanName());
					}
				}
			}
		}

		// Process any @Import annotations
        //递归解析，获取导入的配置类，很多情况下，导入的配置类中会同样包含导入类注解
		processImports(configClass, sourceClass, getImports(sourceClass), true);

		// Process any @ImportResource annotations
        //解析@ImportResource配置类
		AnnotationAttributes importResource =
				AnnotationConfigUtils.attributesFor(sourceClass.getMetadata(), ImportResource.class);
		if (importResource != null) {
			String[] resources = importResource.getStringArray("locations");
			Class<? extends BeanDefinitionReader> readerClass = importResource.getClass("reader");
			for (String resource : resources) {
				String resolvedResource = this.environment.resolveRequiredPlaceholders(resource);
				configClass.addImportedResource(resolvedResource, readerClass);
			}
		}

		// Process individual @Bean methods
        //处理@Bean注解修饰的类
		Set<MethodMetadata> beanMethods = retrieveBeanMethodMetadata(sourceClass);
		for (MethodMetadata methodMetadata : beanMethods) {
			configClass.addBeanMethod(new BeanMethod(methodMetadata, configClass));
		}

		// Process default methods on interfaces
        // 处理接口中的默认方法
		processInterfaces(configClass, sourceClass);

		// Process superclass, if any
        //如果该类有父类，则继续返回，上层方法判断不为空，则继续递归执行
		if (sourceClass.getMetadata().hasSuperClass()) {
			String superclass = sourceClass.getMetadata().getSuperClassName();
			if (superclass != null && !superclass.startsWith("java") &&
					!this.knownSuperclasses.containsKey(superclass)) {
				this.knownSuperclasses.put(superclass, configClass);
				// Superclass found, return its annotation metadata and recurse
				return sourceClass.getSuperClass();
			}
		}

		// No superclass -> processing is complete
		return null;
	}

```

11、查看获取配置类的逻辑

```java
processImports(configClass, sourceClass, getImports(sourceClass), true);

	/**
	 * Returns {@code @Import} class, considering all meta-annotations.
	 */
	private Set<SourceClass> getImports(SourceClass sourceClass) throws IOException {
		Set<SourceClass> imports = new LinkedHashSet<>();
		Set<SourceClass> visited = new LinkedHashSet<>();
		collectImports(sourceClass, imports, visited);
		return imports;
	}
------------------
    	/**
	 * Recursively collect all declared {@code @Import} values. Unlike most
	 * meta-annotations it is valid to have several {@code @Import}s declared with
	 * different values; the usual process of returning values from the first
	 * meta-annotation on a class is not sufficient.
	 * <p>For example, it is common for a {@code @Configuration} class to declare direct
	 * {@code @Import}s in addition to meta-imports originating from an {@code @Enable}
	 * annotation.
	 * 看到所有的bean都以导入的方式被加载进去
	 */
	private void collectImports(SourceClass sourceClass, Set<SourceClass> imports, Set<SourceClass> visited)
			throws IOException {

		if (visited.add(sourceClass)) {
			for (SourceClass annotation : sourceClass.getAnnotations()) {
				String annName = annotation.getMetadata().getClassName();
				if (!annName.equals(Import.class.getName())) {
					collectImports(annotation, imports, visited);
				}
			}
			imports.addAll(sourceClass.getAnnotationAttributes(Import.class.getName(), "value"));
		}
	}
```

12、继续回到ConfigurationClassParser中的parse方法中的最后一行,继续跟进该方法：

```java
this.deferredImportSelectorHandler.process()
-------------
public void process() {
			List<DeferredImportSelectorHolder> deferredImports = this.deferredImportSelectors;
			this.deferredImportSelectors = null;
			try {
				if (deferredImports != null) {
					DeferredImportSelectorGroupingHandler handler = new DeferredImportSelectorGroupingHandler();
					deferredImports.sort(DEFERRED_IMPORT_COMPARATOR);
					deferredImports.forEach(handler::register);
					handler.processGroupImports();
				}
			}
			finally {
				this.deferredImportSelectors = new ArrayList<>();
			}
		}
---------------
  public void processGroupImports() {
			for (DeferredImportSelectorGrouping grouping : this.groupings.values()) {
				grouping.getImports().forEach(entry -> {
					ConfigurationClass configurationClass = this.configurationClasses.get(
							entry.getMetadata());
					try {
						processImports(configurationClass, asSourceClass(configurationClass),
								asSourceClasses(entry.getImportClassName()), false);
					}
					catch (BeanDefinitionStoreException ex) {
						throw ex;
					}
					catch (Throwable ex) {
						throw new BeanDefinitionStoreException(
								"Failed to process import candidates for configuration class [" +
										configurationClass.getMetadata().getClassName() + "]", ex);
					}
				});
			}
		}
------------
    /**
		 * Return the imports defined by the group.
		 * @return each import with its associated configuration class
		 */
		public Iterable<Group.Entry> getImports() {
			for (DeferredImportSelectorHolder deferredImport : this.deferredImports) {
				this.group.process(deferredImport.getConfigurationClass().getMetadata(),
						deferredImport.getImportSelector());
			}
			return this.group.selectImports();
		}
	}
------------
    public DeferredImportSelector getImportSelector() {
			return this.importSelector;
		}
------------
    @Override
		public void process(AnnotationMetadata annotationMetadata, DeferredImportSelector deferredImportSelector) {
			Assert.state(deferredImportSelector instanceof AutoConfigurationImportSelector,
					() -> String.format("Only %s implementations are supported, got %s",
							AutoConfigurationImportSelector.class.getSimpleName(),
							deferredImportSelector.getClass().getName()));
			AutoConfigurationEntry autoConfigurationEntry = ((AutoConfigurationImportSelector) deferredImportSelector)
					.getAutoConfigurationEntry(getAutoConfigurationMetadata(), annotationMetadata);
			this.autoConfigurationEntries.add(autoConfigurationEntry);
			for (String importClassName : autoConfigurationEntry.getConfigurations()) {
				this.entries.putIfAbsent(importClassName, annotationMetadata);
			}
		}

```

个人理解：在启动类的方法中会加载springboot环境，进行一些刷新容器、初始化上下文等工作，其中源码有一个load方法会把需要自动配置的bean加载到容器中，主要是在源码中会扫描到springbootapplication注解，这个注解是一个复合注解 包括enableautoconfiguration componentscan configuration,其中componentscan是用于确认容器所在路径 在之后进行扫描的时候会扫描这个路径下的所有bean configuration是用来表明这是一个配置类 enableautoconfiguraion会加载容器需要的组件，也是spring.factories路径下的一些组件
