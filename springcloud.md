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

#### 1.5. 替换LB默认的负载均衡算法

- 新建一个工具包，不能包含在app包下及app所在当前包，否则

  ```
  ```

  

- 自定义类，书写自己的规则

  - 在springboot中注入自己的bean
  - 直接返回一个规则的实例，并且打印日志

- 启动类添加@RibbonClient(name = "service name", configuration=自定义规则.class)

### 2. Loadbalancer

### 3. Feign

### 4. OpenFeign（推荐）

## 四、服务熔断降级

### 1. Hystrix

### 2. Resillience4j

### 3. springCloud Alibaba sentienl（推荐）

## 五、服务网关

### 1. Zuul

### 2. Gateway(推荐)

## 六、服务分布式配置-spring cloud config

### 1. Config

### 2. Nacos（推荐）

## 七、服务开发-spring boot

### 1. Bus

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



















