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

### 2. zookeeper

### 3. consul

### 4. Nacos（推荐）

## 三、服务负载与调用

### 1. Ribbon

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



















