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

  

## 2. 测试表结构

````mysql
CREATE TABLE `test`.`t_test` (
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
````

