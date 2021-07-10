# Spring Cloud学习笔记

## 一. springcloud

![microservice](./resource/image/microservice.svg)

### 1. 版本选择

​		https://docs.spring.io/spring-cloud/docs/current/reference/html/

## 二、服务注册与发现

### 1. eureka

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



















