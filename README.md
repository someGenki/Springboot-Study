# 用于学习的SpringBoot项目

SpringBoot(sb)纯后端学习用的项目，尽量只依赖sb提供的依赖如`jackson`和`HikariCP`，而不是用`fastjson`和`Druid`。

功能和特点嘛：规范的目录结构和编码规范、尽可能加上的注释、便捷的调试接口、数据库逆向代码生成和文档生成、全局异常捕获、统一返回结果类及状态码枚举、实体类校验、热部署、打包时排除不需要的依赖、添加阿里云仓库地址

版本说明：jdk-11,sb-2.4.x,knife4j-2.0.8,mybitsPlus-3.4.2

maven依赖：

1. sb-web,sb-test,sb-validation（官方的starter）
2. lombok、jjwt，mysql、freemarker
3. sb-devtools（热部署）
4. knife4j（swagger的vue版本）
5. mybatis-plus（mybatis增强工具）
6. hutool-all（hutool工具类）
7. mybatis-plus-generator（代码生成器）
8. screw-core（数据库文档生成器）



## 1. 相关技术的相关说明

- Knife4j：接口的生成和在线调试，浏览器输入`host:port/doc.html`进行访问  官网 https://doc.xiaominfo.com/
- Mybatis-Plus：Mybatis的增强工具。同时使用它的代码生成器 官网 https://baomidou.com/
  - 自3.3.0开始,默认使用雪花算法+UUID(不含中划线)数据库的主键需要 `bigint(64)`格式
  - js无法处理java的长整型，所以可以实体类中id字段可以把`Long`改成`String` 或者对全局jackon进行配置
  - 代码生成器中配置的表前缀为`t_`，数据库的表名为`t_xxx`
  - `CodeGenerator`类运行main方法，输入需要的表名，即可生成Entity、Mapper、Mapper XML、Service、Controller 等各个模块的代码
  - 对面Mapper接口使用`@Autowired`自动注入报红，可以忽视直接运行，也可以去Mapper头顶手动加入`@Repository`或者用java自带的`@Resource`来替换`@Autowried`
- Hutool：国产的一个小而全的Java工具类库 官网 https://www.hutool.cn/docs/
- screw：简洁好用的数据库表结构文档生成工具 https://gitee.com/leshalv/screw
- spring security: 以过滤器为主的安全认证服务框架




## 2. 目录结构和模块划分

````
study
├── study-base-- 单个的springboot项目例子，跟其他模块没关系 
├── study-common-- 存放通用的代码和工具类 
├── study-security-- SpringSecurity相关模块 
├── study-service-- 业务相关代码以及代码生成器 
├── study-web-- web层主要就请求处理和全局异常处理  
├── study-？？ -- ？？ 
└── study-！！ -- ！！ 
````

## 3. 测试表结构

````mysql
CREATE TABLE `t_test` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(32) NOT NULL,
  `money` DECIMAL(8,2) NULL DEFAULT 10.0,
  `status` TINYINT(1) UNSIGNED NULL DEFAULT 0,
  `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
COMMENT = '测试用表';
--  测试数据
INSERT INTO t_test (name) VALUES ('jojo');

CREATE TABLE `t_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(64) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `phone` VARCHAR(11) NULL,
  `role` VARCHAR(64) NULL DEFAULT 'ROLE_USER',
  `create_time` DATETIME NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`))
COMMENT = '用户表';
-- 测试数据
INSERT INTO `t_user` (`username`, `password`, `phone`, `role`) VALUES ('jojo1', 'jojo1', '15080316526', 'ROLE_ADMIN');
INSERT INTO `t_user` (`username`, `password`, `phone`, `role`) VALUES ('dio1', 'dio1', '15080316527', 'ROLE_USER');

````

## 4. 认证和权限控制

**关键字：** 1. JWT、2. UserDetails 、3. Filter 4、前后端分离无session和cookie

1. 引入Spring Security 并编写配置类(继承`WebSecurityConfigurerAdapter`)

2. 在配置类中的configure里禁用session并添加要放行的路径，剩下都将被拦截

3. 并配置被拦截时的处理如**权限不足**(403)或者未授权(401)的错误

4. 编写**自定义的jwt过滤器类**继承(OncePerRequestFilter)

5. 编写User实体类并实现(UserDetails)，UserService服务类去实现(UserDetailsService)

6. 上面俩主要就是让Security从数据库中获取User进一步获取用户名密码角色等信息

7. 添加**自定义的jwt过滤器**对请求头中的jwt进行校验，当没携带token就放行让下一个过滤器处理

8. 当jwt是有效的便提取出里面的用户名，然后进行相关处理

   1. 调用`userService`获取`userDetails`可以进行相关校验（没有userDetails要抛异常）
   2. 放入SecurityContext中，接下来的业务代码就可以从context中获取相关信息来操作

   -------------------------------------------------------------------------------------------------------------------

1. 授权实现参考下面的链接1，讲的简单易懂
2. 数据表中的Role字段要**ROLE_xxx**的形式
3. 在实现了UserDetails接口的实体类中，重写getAuthorities();

参考链接：

1. Spring Security 中的四种权限控制方式 https://blog.csdn.net/u012702547/article/details/106800446/

2. 江南一点雨的Spring Security 系列https://mp.weixin.qq.com/mp/appmsgalbum?action=getalbum&album_id=1319828555819286528
3. 官方参考文档 https://docs.spring.io/spring-security/site/docs/5.4.2/reference/html5/

## 5. WebSocket聊天(STOMP)

1. 引入`spring-boot-starter-websocket`依赖并编写配置类并额外加上`@EnableWebSocketMessageBroker`注解

2. 重写`registerStompEndpoints()`注册端点（端点建立连接用的），还可以在这添加拦截器

   ````java
       @Override    
   	public void registerStompEndpoints(StompEndpointRegistry registry) {
           registry.addEndpoint("/ws")
                   .setAllowedOriginPatterns("*/*")
                   .addInterceptors(new StompConnectInterceptor());
       }
   ````

3. 重写`configureMessageBroker()`来配置消息代理

   ````java
       /**
        * 配置消息代理
        * 1. 启用简单的代理。代理/topic 进行广播  /queue 进行信息交换
        * ----topic和/queue前缀没有任何特殊含义。它们只是区分 pub-sub 与 point-to-point 消息传递的惯例(即许多订阅者与一个消费者)
        * 2. 设置应用端点前缀
        * ----目标头以/app开头的 STOMP 消息将路由到控制器类中的@MessageMapping方法。而不会发布到代理队列或主题中。
        * 3. 设置/user2为用户点对点发送消息的订阅前缀 如 stomp.subscribe("/user2/queue/shouts",()=>{})
        * ----当用户B用上面的js代码订阅之后，用户A要发送消息给B可以先发送到带有@MessageMapping的方法(带上接收者)，
        * ----然后用SimpMessageSendingOperations.convertAndSendToUser(接收人，目的地`/queue/shouts`,消息)发送消息
        * ----然后订阅了这个目的地的用户B就能收到来自A的消息
        */
       @Override
       public void configureMessageBroker(MessageBrokerRegistry config) {
           config.enableSimpleBroker("/topic", "/queue");
           config.setApplicationDestinationPrefixes("/app");
           config.setUserDestinationPrefix("/user2/");
       }
   ````

4. 如需要认证后才允许连接（基于token），可以在拦截器中beforeHandshake写如下代码(难的就是类型转换)

   ````java
   HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
   System.out.println(servletRequest.getParameter("token"));
   // 前端的连接url大概是这样:  ws://localhost:8080/api/ws?token=zbgvgaw#@sd
   ````

5. 为了在浏览器中接收消息，STOMP客户端必须先订阅一个或多个目的地`destination`，并写好回调函数。

6. 处理来自客户端的STOMP消息用`@MessageMapping`注解添加到方法上

7. 处理器的方法的返回值是转发给消息代理的，如果客户端想要这个返回值的话，只能从消息代理订阅

8. 其他看第二个参考链接挺详细的

   ````js
     function connection() {
       //1. 这里携带token，有效的才允许连接
       stomp = Stomp.client('ws://localhost:8080/api/ws?token=jojo1')
       //2. 这里的header对象是建立连接后后端才能拿到的
       stomp.connect(header, function () {
         console.log("连接成功!")
         //3. 连接成功后立即发个消息
         stomp.send("/app/greeting", {}, JSON.stringify(payload));
         stomp.subscribe('/app/subscribe', function (frame) {
           console.log("订阅的服务端消息：" + frame);
         });
         //订阅群聊的消息
         stomp.subscribe('/topic/public', function (frame) {
           document.querySelector('#consoleLog').innerHTML = frame.body;
           console.log("群聊消息：" + frame);
         });
       }, function () {
         console.error("连接失败!")
       });
     }
   ````

   

----

其他注意事项：

1. 关于stomp.connect(header,...)中的header在HttpSessionHandshakeInterceptor拿不到,F12也看不到问题：

   > websocket先发一个http请求请求升级为websocket请求，从现在header里能看到upgrade升级协议的header，uri也表示是http协议。然后发一个ws请求请求鱼服务器建立连接，应该是在这个时候放入的header

2. ChannelInterceptor中用MessageHeaderAccessor.getAccessor配合参数Message<?>可以拿到ws帧

   



参考：

https://www.docs4dev.com/amp/docs/zh/spring-framework/5.1.3.RELEASE/reference/web.html#websocket

https://www.cnblogs.com/goloving/p/10746378.html、

https://www.cnblogs.com/jmcui/p/8999998.html



## 0. TOOD

- [ ] 统一配置文件：yml移动到 study-common模块下

