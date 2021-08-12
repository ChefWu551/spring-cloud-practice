# Spring Cloud学习笔记

## 一. springcloud

![microservice](./resource/image/microservice.svg)

### 1. 版本选择

​		https://docs.spring.io/spring-cloud/docs/current/reference/html/

## 二、服务注册与发现

### 1. eureka

#### 1.1. 使用

- 父级引入依赖管理

  ```xml
   <dependencyManagement>
     <dependencies>
       <dependency>
         <groupId>org.springframework.cloud</groupId>
         <artifactId>spring-cloud-dependencies</artifactId>
         <version>Hoxton.SR1</version>
         <type>pom</type>
         <scope>import</scope>
       </dependency>
     </dependencies>
   </dependencyManagement>
  ```

- 服务端

  - 引入服务相关依赖

  ```xml
  <dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
  </dependency>
  ```

  - 添加允许服务端启动注解

    ```
    @EnableEurekaServer
    ```

- 客户端

  - 引入客户端相关依赖

    ```xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    ```

  - 添加允许客户端启动注解

    ```
    @EnableEurekaClient
    ```

#### 1.2. 集群版本

![eureka-server-client-cluster](./resource/image/eureka-server-client-cluster.png)

##### 1.2.1. eureka集群

	- Eureka1:

```properties
server.port=7001

eureka.instance.hostname=eureka7001.com
# 表示不向注册中心注册自己
eureka.client.register-with-eureka=false
# 表示自己端就是注册中心，我的职责就是维护服务器实例，并不需要检索服务
eureka.client.fetch-registry=false
eureka.client.service-url.defaultZone=http://eureka7002.com:7002/eureka/
```

- Eureka2:

```properties
server.port=7002

eureka.instance.hostname=eureka7002.com
# 表示不向注册中心注册自己
eureka.client.register-with-eureka=false
# 表示自己端就是注册中心，我的职责就是维护服务器实例，并不需要检索服务
eureka.client.fetch-registry=false
# 在7001注册7002服务
eureka.client.service-url.defaultZone=http://eureka7001.com:7001/eureka/
```

##### 1.2.2. 支付服务集群

- payment-server1

```properties
server.port=8081
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/sc_payment?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8
spring.datasource.username=root
spring.datasource.password=rootroot
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
mybatis.mapperLocations=classpath:mapper/*.xml
mybatis.typeAliasesPackage=com.wuyuefeng.model

spring.application.name=cloud-payment-server

eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.client.service-url.defaultZone=http://eureka7001.com:7001/eureka, http://eureka7002.com:7002/eureka
```

- payment-server2

```properties
server.port=8082
spring.datasource.url=jdbc:mysql://127.0.0.1:3306/sc_payment?characterEncoding=utf8&useSSL=false&serverTimezone=GMT%2B8
spring.datasource.username=root
spring.datasource.password=rootroot
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
mybatis.mapperLocations=classpath:mapper/*.xml
mybatis.typeAliasesPackage=com.wuyuefeng.model

spring.application.name=cloud-payment-server

eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.client.service-url.defaultZone=http://eureka7001.com:7001/eureka, http://eureka7002.com:7002/eureka
```

##### 1.2.3. 订单中心修改

- 指定支付服务的路径

  **地址使用eureka注册中心的地址，而不是服务本身的地址：http://CLOUD-PAYMENT-SERVER**

- 使用template调用负载均衡

  **在template bean中添加注解@Loadblanced**

#### 1.3. 服务发现

- @DiscoveryEnable
- 引入discoveryClient bean，然后根据对应的bean获取当前eureka服务的信息
  - 实例的数量
  - 实例的端口号及对应的ip

#### 1.4. CAP原则

   	CAP原则又称CAP定理，指的是在一个分布式	系统中，一致性（Consistency）、可用性可用性（Availability）、分区容错性（Partition tolerance）。CAP 原则指的是，这三个要最多只能同时实现两点，不可能三者兼顾。

- eureka使用的AP原则

##### 1.4.1. 自我保护原则

##### 1.4.2. 关闭服务端自我保护机制

```properties
# 关闭自我保护机制
eureka.server.enable-self-preservation=false
eureka.server.evication-interval-in-ms=2000
```

##### 1.4.3. 设置client端心跳包发送

```properties
# 想服务端发送心跳的时间间隔
eureka.instance.lease-renewal-interval-in-seconds=1
# 服务端在收到最后一次心跳后等待时间上线，超时会注销服务
eureka.instance.lease-expireation-duration-in-seconds=2
```

### 2. zookeeper

- 添加相关依赖

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
</dependency>
```

- 解决依赖版本和zk版本冲突问题

  ```xml
  <dependency>
  	<groupId>org.springframework.cloud</groupId>
  	<artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
    <exclusions>
    <!-- 排除自带的zookeeper版本 -->  
      <exclusion>
        <groupId>org.apache.zookeeper</groupId>
        <artifactId>zookeeper</artifactId>
      </exclusion>
    </exclusions>
    <!-- 引入新的版本 -->
    <dependecy>
      <groupId>org.apache.zookeeper</groupId>
      <artifactId>zookeeper</artifactId>
      <version>${zk.version}</version>
    </dependecy>
  </dependency>
  ```


### 3. consul

### 4. Nacos（推荐）

## 三、服务负载与调用

### 1. Ribbon

​	基于netflix ribbon实现的一套客户端**负载均衡**的工具，主要功能是提供客户端的软件**负载均衡算法和服务调用**；ribbon客户端组件提供一些完善的配置项，如链接超时，重试等。简单的说，就是在配置文件中列出load Balancer（LB）后面的所有机器，ribbon会自动的帮助你基于某种规则（如简单轮询、随机链接等）去链接这些机器。

#### 1.1. 负载均衡分类

 - 进程内LB: 本地负载均衡客户端（例如：ribbon）,在调用微服务接口的时候，会在注册中心上获取注册信息服务列表之后缓存到jvm本地，从而在本地实现rpc远程服务调用技术。

 - 集中式LB: 服务端负载均衡（例如：nginx）：

   请求 -> nginx -> ribbon->server1/server2/..

#### 1.2. 二说restTemplate

​	todo: - 源码解读

- getForEntity

#### 1.3. eureka集成ribbon

#### 1.4. LB的均衡算法

- ribbon默认使用轮训的方式实现负载均衡

- IRule接口，根据当前接口选择自己的负载均衡算法，扩展负载均衡器

  - 在springboot中注册IRule对应的组件
  - 实现对应的负载均衡代码

- todo: - Ribbon自带的规则源码解读

- 实现继承使用

- eureka自带的负载均衡算法

  - ```
    RoundRobinRule
    ```

- Todo-: 手写负载均衡算法

  - 继承loadBalance接口，实现方法
  - 注册为对应的bean，且当前bean必须返回对应的负载均衡的service
  - 通过@autowire注入bean，代码获取对应的service
  - 并通过restTemplate进行远程调用，测试LB算法是否使用

#### 1.5. 替换LB默认的负载均衡算法

- 新建一个工具包，不能包含在app包下及app所在当前包，否则

  ```
  ```

  

- 自定义类，书写自己的规则

  - 在springboot中注入自己的bean
  - 直接返回一个规则的实例，并且打印日志

- 启动类添加@RibbonClient(name = "service name", configuration=自定义规则.class)

### 2. LoadBalancer

### 3. Feign

### 4. OpenFeign（推荐）

​	feign是一个声明式webservice客户端。使用feign能让web service客户端更简单；他的使用方法是**定一个服务接口**，然后在上面添加注解(@FeignClient)。spring cloud对Feign进行了封装，使其支持spring mvc标准注解和httpMessageConverters. Feign可以与Eureka和Ribbon组合使用以支持负载均衡。

​	ribbon和feign之间的区别：

​		ribbon本身是一个负载均衡，通过调用restTemplate进行封装后，进行负载均衡的调用，而feign则是在此基础上进行了一层封装，解决一个接口被多处调用的问题，通常会针对每个微服务自行封装一些客户端类来包装这些依赖服务的调用。通过对接口增加一个feign的注解就可以完成服务端的接口绑定（类似@mapper注解标注的Dao接口）

![eureka-feign-client](resource/image/eureka-feign-client.png)

#### 4.1. 使用

- 引入依赖

  ```xml
  <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-openfeign</artifactId>
  </dependency>
  ```

- Main方法添加注解、接口添加注解及对应的方法

  ```java
  @EnableFeignClients
  ```

  ```java
  @Component
  @FeignClient("CLOUD-PAYMENT-SERVER")
  public interface PaymentFeignService {
  
      @GetMapping("/pay/account/{id}")
      JSONObject getAccountInfo(@PathVariable("id") Integer id);
  }
  ```

- 添加controller并测试

  ```java
  @RestController
  @Slf4j
  public class PaymentFeignController {
  
      @Resource
      PaymentFeignService service;
  
      @GetMapping("/consumer/account/{id}")
      public JSONObject getAccountInfo(@PathVariable Integer id){
          return service.getAccountInfo(id);
      }
  }
  ```

#### 4.2. 超时控制

​	类似restTemplate通过注解声明组件的bean，并对bean进行配置

#### 4.3. 日志增强

​	包含了请求的接口，请求接口的方法，状态码，等相关的debug调试信息，

#### 4.4. 思考: 为什么不直接用方法调用而用feign

## 四、服务熔断/降级/限流

### 1. Hystrix

​		服务雪崩，hystrix解决服务降级，延迟和容错机制，处理超时和异常的场景，能够保证在一个依赖出问题的情况下，不会导致整体服务的失败，避免级联故障，以提高分布式系统的弹性。

​		当某个服务单元发生故障之后，通过断路器的故障监控，向调用方返回一个符合可预期，可处理的备选响应（fallback），而不是长时间的等待。

#### 1.1. 服务降级-fallBack

##### 1.1.1. 发生情况

-  程序异常
- 超时: 直接返回处理方法的结果
- 服务熔断出发服务降级
- 线程池/信号量打满也会导致服务降级

##### 1.1.2. 实现

- 服务端添加注解标签

```java
@EnableCircuitBreaker
```

- 创建服务降级的处理方法

```java
// 若请求超时，直接返回提示内容，以此达到服务降级的效果
public String timeSleepHandler() {
    return "服务器繁忙，请稍后再试";
}
```

- 指定方法若要服务降级，指向服务降级的方法

```java
@HystrixCommand(fallbackMethod = "timeSleepHandler", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
})
public String timeSleep() throws InterruptedException {
    TimeUnit.SECONDS.sleep(5);
    return "线程池: " + Thread.currentThread().getName();
}
```

##### 1.1.3. 实现全局默认的服务降级

- 添加默认服务降级的方法的注解

```
@DefaultProperties(defaultFallback = "globalFallBackHandler")
```

- 创建全局服务降级方法

```java
 // 有自定义的走自定义，无自定义直接走全局
    public String globalFallBackHandler() {
        return "服务器繁忙，请稍后再试，全局服务降级";
    }
```

- 对异常、超时的方法添加指定注解

```
@HystrixCommand
```

##### 1.1.4. feign服务使用服务降级

​	同1.1.2的内容，只是要打开feign对应的服务降级的控制

- properties文件打开feign hystrix服务降级

```properties
feign.hystrix.enabled=true
```

- 启动类的@EnableCircuitBreaker修改成@EnableHystrix

- 针对各个方法的自定义服务降级

  - 实现该服务的接口，并且实现其方法，对方法进行实现，保证若有超时、异常等，此时方法生效，并注册成组件
  - 在接口添加@FeignClient(value = "CLOUD-PAYMENT-SERVER", fallback = PaymentFeignFallBackService.class)，fallback指向处理的接口

  - 此时若有服务宕机、接口超时、接口异常等信息，都会统统用该方法返回结果

#### 1.2. 服务熔断-fallDown

​		达到最大服务访问后，直接拒绝访问，然后调用服务降级的方法，并返回友好提示，降级->熔断->恢复调用链路

​		熔断一共分成三种，熔断打开，熔断半开，熔断关闭，如果根据方法定义的熔断情况，如果熔断关闭状态下，服务器负载过大，此时熔断会处于半开状态，会有一部分的请求失败，大量负载导致熔断关闭后，此时熔断全开，过段时间服务可以自行恢复，此时服务熔断关闭。

- 添加服务熔断代码

```java
@HystrixCommand(fallbackMethod = "circuitBreakerHandler", commandProperties = {
        @HystrixProperty(name = "circuitBreaker.enabled", value = "true"), // 开启断路器
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"), // 异常情况超过最小阈值时，熔断器将会从关闭状态编程半开状态
        @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds", value = "10000"), // 时间范围
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60") // 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(int id) {
        if (id < 0) throw new ArithmeticException("id不能是负数");

        String serialNum = UUID.randomUUID().toString();
        return Thread.currentThread().getName() + ". 调用成功，流水号： " + serialNum;
    }

    public String circuitBreakerHandler(int id) {
        return "服务目前熔断，正在恢复中.... 您输入的id： " + id;
    }
```

- 测试接口
  - 使用jmeter请求接口： /hystrix/fallDown/-5，并发3000
  - 同时使用postman请求接口：/hystrix/fallDown/5
  - 此时会发现postman请求的接口会返回服务熔断的提醒，过一段时间请求后，恢复正常

#### 1.3. 服务限流-fallLimit

​		秒杀高并发等操作，控制单位时间内的qps

#### 1.4. 项目构建

	- 引入相关依赖
	- 编写超时接口，线程睡眠三秒
	- 并发工具JMETER测试对应的接口，200线程，100个并发，1秒，进行超时请求接口测试
	- 正常的接口因为负载会响应变慢，超时的接口依然还是很慢

#### 1.5. 改进措施

- 添加consumer的feign模块
- 调用上面建立好的服务（hystrix payment）
- 高并发测试 consumer feign的服务，会发现需要等待，或者是直接返回超时（犯规的数据不优雅，界面直接 white page error），此时并发量太大，甚至可能导致宕机
- Hystrix解决的问题
  - 超时不再等待
    - 对方服务器（pay server）超时或宕机了，调用（consumer）不能一直卡死等待，必须有服务降级
    - 对方服务ok，调用者consumer自己出故障或者有自我要求（自己的等待时间小于服务提供者），自己处理降级

#### 1.6. hystrix dashboard

### 2. Resillience4j

### 3. springCloud Alibaba sentienl（推荐）

## 五、服务网关

### 1. Zuul路由网关

### 2. spring-cloud-Gateway(推荐)

​	This project provides an API Gateway built on top of the Spring Ecosystem, including: Spring 5, Spring Boot 2 and Project Reactor. Spring Cloud Gateway aims to provide a simple, yet effective way to route to APIs and provide cross cutting concerns to them such as: security, monitoring/metrics, and resiliency.

​	基于webflux中的reactor-netty响应式编程组件，底层使用了netty通讯框架；基于异步非阻塞模型进行开发的。

#### 2.1. 特性

- 动态路由-route
- 断言-predicate : 有效时间(类似过期时间的机制)、带指定cookie请求认证、请求头过滤等各种过滤规则
- 过滤器-filter

### 3. 扩展

- 谈谈微服务网关的理解
- 为什么要用gateway不用zuul
  - Zuul 1.x版本是一个基于阻塞I/O的api gateway，因此不支持任何长链接，每次io操作都是从工作线程中选择一个执行，请求线程被阻塞到工作线程完成
  - spring cloud gateway使用非阻塞API
- 

## 六、服务分布式配置-spring cloud config

### 1. Config

​	统一管理配置，管理各个环境的配置信息。

![spring-cloud-config](./resource/image/spring-cloud-config.jpeg)

#### 1.1. 模拟配置中心获取配置

- 在github上面建立相关的配置中心仓库，放入配置文件

```
https://github.com/ChefWu551/spring-cloud-config
```

- 新建配置中心服务，

```java
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableConfigServer
@EnableEurekaClient
public class ConfigApp {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApp.class, args);
    }
}
```

- 配置中心配置项指向代码仓库

```yaml
server:
  port: 8088

spring:
  application:
    name: cloud-config-center
  cloud:
    config:
      server:
        git:
          uri: https://github.com/ChefWu551/spring-cloud-config.git
          search-paths:
            - spring-cloud-config
          username: 565948592@qq.com # 注意，这里需要账号密码，否认鉴权失败
          password: wyf23188551
      label: master

eureka:
  client:
    service-url:
      defaultZone: http://localhost:7001/eureka
```

- 访问测试

```
localhost:8088/master/config-dev.yml
```

#### 1.2. 新服务通过配置中心读取配置

- 引入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

- 添加配置项

```yml
server:
  port: 8089

spring:
  application:
    name: config-client
  cloud:
    config:
      label: master
      name: config
      profile: dev
      uri: http://localhost:8088

eureka:
  client:
    service-url:
      defaultZone: http://localhost:7001/eureka

# 暴露监控点
management:
  endpoints:
    web:
      exposure:
        include: "*"
```

- 获取配置

  localhost:8089/getConfigValue

```java
@RestController
@RefreshScope
public class testController {

    @Value("${config.info}")
    private String value;

    @RequestMapping("getConfigValue")
    public String getConfigValue() {
        return value;
    }
}
```

- 更新配置

  localhost:8089/actuator/refresh

  ！！！！重要

  - 这里要注意的是SpringBoot1.5.x的请求是http://localhost:8762/bus/refresh，
  - 2.x变成：http://localhost:8762/actuator/bus-refresh

- 再次获取更新的配置

  localhost:8089/getConfigValue

#### 1.3. 分布式配置的动态刷新

​	配置文件改变，config的客户端的配置信息是不会改变的，但是config server会发生改变，解决方案：

- 重启config client

### 2. Nacos（推荐）

## 七、服务开发-spring boot

### 1. Bus

​	spring cloud bus 是用来将分布式系统的节点与轻量级消息系统连接起来的框架，他整合了java的事件处理机制和消息中间件的功能。

​	 通过对某个事件进行监听，根据更新的情况通过总线的消息队列，将监听的信息通知到各个对应的服务上面去。

![spring-cloud-bus](./resource/image/spring-cloud-bus.png)

#### 1.1. 安装rabbitMQ

#### 1.2. Bus消息总线的设计思想及原理

 - 设计思想

   - 方案一：利用消息总线出发一个客户端/bus/refresh，而刷新所有客户端的配置
   - 方案二：利用消息总线出发一个服务端configserver的/bus/refresh端点，而刷新所有的客户端
   - 综上显然第二种更符合设计原则，因为业务服务器没有必要承担非业务性的东西，例如更新并且通知其他服务拉取最新的配置

- 原理

  - ConfigClient实例都监听MQ中同一个topic（默认是springCloudBus），当一个服务刷新数据的时候，它会把这个信息放入到topic中，这样其他监听同一个topic的服务就能得到通知，然后去更新自身的配置

  ![spring-cloud-bus-server](./resource/image/spring-cloud-bus-server.png)

#### 1.3. 实现

##### 1.3.1. Cloud-config-server

- 依赖添加rabbitmq-bus支持
- 配置启动文件 - 链接mq的属性文件
- 配置启动文件 - 暴露bus刷新配置的端点 /actuator/bus-refresh

##### 1.3.2. cloud-config-client

- 依赖添加rabbitmq-bus支持
- 配置启动文件链接mq的属性文件及暴露bus刷新配置的端点

##### 1.3.3. cloud-config-client-1

- 依赖添加rabbitmq-bus支持
- 配置启动文件链接mq的属性文件及暴露bus刷新配置的端

##### 1.3.4. 测试

- 发送更新请求，请求配置中心 config-server的 /actuator/bus-refresh 刷新
- 请求各个client端，然后看看配置是否更新了

##### 1.3.5. 指定通知某个服务进行更新

- 请求Config-server 的接口 /actuator/bus-refresh/{destination}

  - destination参数：eureka里面的 server name：**cloud-payment-server:port**

    ```properties
    spring.application.name=cloud-payment-server:port
    ```

### 2. Nacos（推荐）

## 其他知识补充



### 1. dependencyManagement与dependencies区别

​	dependencyManagement一般在maven最外层的pom使用，用于声明依赖对应的版本号，本身没有实际作用，在子模块中引用相同依赖的时候不需要指定依赖的版本号，子模块引用的依赖会根据继承关系直接找到dependencymanagement里面的版本号，然后直接引用，而dependcies里面声明的依赖可以直接被子模块引用，不需要重复声明的

### 2. Maven 标签

- `<type>`
  - 默认不指定的时候是jar
- `<scope>`
  - compile : 指定依赖需要参与到，编译、打包、运行等阶段
  - Provided: 只参与编译、测试、运行，但是在打包的时候会做一个exclude的动作，因为可能应用所挂在的容器服务已经提供了exclude所需要的包
  - runtime：无需参与编译，但是会参与到项目的测试和运行阶段，与compile相比，被依赖项目无需参与编译
  - test: 仅仅在测试的时候使用
  - system: 与provided类似，但是依赖不会从maven仓库中找，而是从本地仓库获取，有systemPath来指定jar所在的路径
  - Import: 只会在dependecyManagement中使用，标识从其他的pom中导入dependency的配置，例如B项目导入A项目中的包配置

### 3. servlet生命周期

### 4. 正向代理和反向代理

​	forward proxy and reverse proxy

​	出入原则；

	- 出即正向代理，也就是将客户端发出的请求代理到服务端，正向代理，例如我们的翻墙软件
	- 入既反向代理，也就是负责接收请求，将请求代理到服务器端

![正向代理与反向代理](.\resource\image\正向代理与反向代理.png)

#### 4.1. 代理的作用

- 可以缓存一些常用的静态资源，加快访问速度（动态热点新闻等信息，例如吴亦凡因猥亵未成年人少女被监禁），类似的CDN也有这个功能
- 相当于在客户端和服务端中间进行了一层转发，保证内网安全，阻止web攻击
- 访问原来无法访问的资源，例如翻墙



