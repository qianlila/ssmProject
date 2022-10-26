# 一个ssm整合图书后台管理系统

## 环境要求

> IDEA
> MySQL 5.7
> Tomcat 8.5
> Maven 3.6

## 创建数据库

```myssql
CREATE DATABASE `ssmbuild`;
USE `ssmbuild`;
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
  `bookID` INT(10) NOT NULL AUTO_INCREMENT COMMENT '书id',
  `bookName` VARCHAR(100) NOT NULL COMMENT '书名',
  `bookCounts` INT(11) NOT NULL COMMENT '数量',
  `detail` VARCHAR(200) NOT NULL COMMENT '描述',
  KEY `bookID` (`bookID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8
INSERT  INTO `books`(`bookID`,`bookName`,`bookCounts`,`detail`)VALUES 
(5,'fofo',13,'1232432'),
(10,'java也还好',122,'别学了');
```

## 基本环境搭建

- 新建一Maven项目， 添加web的支持

### 导入相关的pom依赖

```pom
<dependencies>
        <!--Junit-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
        <!--数据库驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
        <!-- 数据库连接池 -->
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.2</version>
        </dependency>
         <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>servlet-api</artifactId>
        <version>2.5</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>jsp-api</artifactId>
        <version>2.2</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>jstl</artifactId>
        <version>1.2</version>
    </dependency>

    <!--Mybatis-->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.2</version>
    </dependency>
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis-spring</artifactId>
        <version>2.0.2</version>
    </dependency>

    <!--Spring-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.1.9.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>5.1.9.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.8</version>
    </dependency>
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <version>1.8.13</version>
    </dependency>
</dependencies>
```

- Maven资源过滤设置

```pom
 <build>
            <resources>
                <resource>
                    <directory>src/main/java</directory>
                    <includes>
                        <include>**/*.properties</include>
                        <include>**/*.xml</include>
                    </includes>
                    <filtering>true</filtering>
                </resource>
                <resource>
                    <directory>src/main/resources</directory>
                    <includes>
                        <include>**/*.properties</include>
                        <include>**/*.xml</include>
                    </includes>
                    <filtering>true</filtering>
                </resource>
            </resources>
```

### 建立基本结构和配置框架

```java
com.lqx.pojo
com.lqx.dao
com.lqx.service
com.lqx.controller
mybatis-config.xml
```

## Mybatis层编写

```properties
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis?useSSL=false&useUnicode=true&characterEncoding=utf8
name=root
pwd=lqx2877354362
```

- IDEA关联数据库

### MyBatis的核心配置文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
<!--    <settings>-->
<!--        <setting name="logImpl" value="STDOUT_LOGGING"/>-->
<!--    </settings>-->
    <typeAliases>
        <package name="com.lqx.pojo"/>
    </typeAliases>
    <mappers>
        <mapper class="com.lqx.dao.BooksMapper"></mapper>
<!--        <mapper resource="com/lqx/dao/BooksMapper.xml"></mapper>-->
    </mappers>
</configuration>
```

- 数据库对应的实体类 com.lqx.pojo.Books

```java
package com.lqx.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Books {
    private int bookId;
    private String bookName;
    private int bookCounts;
    private String detail;
}
```

- Dao层的 Mapper接口

```java
package com.lqx.dao;
import com.lqx.pojo.Books;
import org.apache.ibatis.annotations.Param;
import java.util.List;
public interface BooksMapper {
    /**
     * 增加一个
     */
    int addBooks(Books book);
    /**
     * 删除一个
     */
    int deleteBookById(@Param("bookId") int id);
    /**
     *  修改一个
     */
    int updateBook(Books book);

    /**
     * 查询一个
     * @param id
     * @return
     */
    Books selectBooksById(@Param("bookId") int id);

    /**
     * 查询所有
     * @return
     */
    List<Books> selectAllBooks();

    List<Books> queryBooksByName(@Param("bookName") String bookName);
}

```

### 编写接口对应的 Mapper.xml 文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lqx.dao.BooksMapper">
    <insert id="addBooks" parameterType="Books">
        insert into ssmbuild.books(bookName, bookCounts, detail) values (#{bookName},#{bookCounts},#{detail})
    </insert>

    <delete id="deleteBookById" parameterType="_int">
        delete from ssmbuild.books where bookId=#{bookId}
    </delete>

    <update id="updateBook" parameterType="Books">
        update ssmbuild.books set bookName=#{bookName},bookCounts=#{bookCounts},detail=#{detail} where bookId=#{bookId};
    </update>

    <select id="selectBooksById" parameterType="_int" resultType="Books">
        select * from ssmbuild.books where bookId=#{bookId};
    </select>

    <select id="selectAllBooks" resultType="Books">
        select * from ssmbuild.books
    </select>

    <select id="queryBooksByName" resultType="Books">
        select * from ssmbuild.books where bookName like "%"#{bookName}"%"
    </select>
</mapper>
```

### Service层的接口和实现类

```java
package com.lqx.service;

import com.lqx.pojo.Books;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IBooksService {
    /**
     * 增加一个
     */
    int addBooks(Books book);
    /**
     * 删除一个
     */
    int deleteBookById(int id);
    /**
     *  修改一个
     */
    int updateBook(Books book);

    /**
     * 查询一个
     * @param id
     * @return
     */
    Books selectBooksById(int id);

    /**
     * 查询所有
     * @return
     */
    List<Books> selectAllBooks();

    List<Books> queryBooksByName(String bookName);
}
```

**接口**

```java
package com.lqx.service.impl;

import com.lqx.dao.BooksMapper;
import com.lqx.pojo.Books;
import com.lqx.service.IBooksService;

import java.util.List;

public class BooksServiceImpl implements IBooksService {
    private BooksMapper booksMapper;

    public void setBooksMapper(BooksMapper booksMapper) {
        this.booksMapper = booksMapper;
    }

    public int addBooks(Books book) {
        return booksMapper.addBooks(book);
    }

    public int deleteBookById(int id) {
        return booksMapper.deleteBookById(id);
    }

    public int updateBook(Books book) {
        return booksMapper.updateBook(book);
    }

    public Books selectBooksById(int id) {
        return booksMapper.selectBooksById(id);
    }
    public List<Books> selectAllBooks() {
        return booksMapper.selectAllBooks();
    }

    public List<Books> queryBooksByName(String bookName) {
        return booksMapper.queryBooksByName(bookName);
    }
}

```

底层需求操作完毕

## Spring层

**配置Spring整合MyBatis，数据源使用c3p0连接池；**

#### Spring整合Mybatis的相关的配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd">

    <context:property-placeholder location="classpath:db.properties"/>
<!--    数据库连接池-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${driver}"></property>
        <property name="jdbcUrl" value="${url}"></property>
        <property name="user" value="${name}"></property>
        <property name="password" value="${pwd}"></property>
        <!-- c3p0连接池的私有属性 -->
        <property name="maxPoolSize" value="30"/>
        <property name="minPoolSize" value="10"/>
        <!-- 关闭连接后不自动commit -->
        <property name="autoCommitOnClose" value="false"/>
        <!-- 获取连接超时时间 -->
        <property name="checkoutTimeout" value="10000"/>
        <!-- 当获取连接失败重试次数 -->
        <property name="acquireRetryAttempts" value="2"/>
    </bean>

<!--    配置SqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
<!--        注入数据库-->
        <property name="dataSource" ref="dataSource"></property>
<!--        配置mybatis-config.xml-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>
<!--    配置扫描Dao接口包，动态实现接口注入到spring容器中-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
<!--        给出需要扫描Dao接口包-->
        <property name="basePackage" value="com.lqx.dao"/>
    </bean>
</beans>
```

#### Spring整合service层

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans.xsd
   http://www.springframework.org/schema/context
   http://www.springframework.org/schema/context/spring-context.xsd
   http://www.springframework.org/schema/tx
   http://www.springframework.org/schema/tx/spring-tx.xsd
   http://www.springframework.org/schema/aop
   https://www.springframework.org/schema/aop/spring-aop.xsd">
    <context:component-scan base-package="com.lqx.service"></context:component-scan>

    <!--    BooksSeervice注入到IOC容器中-->
    <bean id="booksServiceImpl" class="com.lqx.service.impl.BooksServiceImpl">
        <property name="booksMapper" ref="booksMapper"></property>
    </bean>

    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"></property>
    </bean>
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="*" propagation="REQUIRED"/>
        </tx:attributes>
    </tx:advice>
    <aop:config>
        <aop:pointcut id="txPointCut" expression="execution(* com.lqx.dao.*.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"></aop:advisor>
    </aop:config>

</beans>
```

## SpringMVC层

#### web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <servlet>
        <servlet-name>DispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <!--一定要注意:我们这里加载的是总的配置文件，之前被这里坑了！-->
            <param-value>classpath:applicationContext.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>DispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!--encodingFilter-->
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>
            org.springframework.web.filter.CharacterEncodingFilter
        </filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!--Session过期时间-->
    <session-config>
        <session-timeout>15</session-timeout>
    </session-config>
</web-app>
```

#### spring-mvc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans.xsd
   http://www.springframework.org/schema/context
   http://www.springframework.org/schema/context/spring-context.xsd
   http://www.springframework.org/schema/mvc
   https://www.springframework.org/schema/mvc/spring-mvc.xsd">
    <!-- 配置SpringMVC -->
    <!-- 1.开启SpringMVC注解驱动 -->
    <mvc:annotation-driven />
    <!-- 2.静态资源默认servlet配置-->
    <mvc:default-servlet-handler/>

    <!-- 3.配置jsp 显示ViewResolver视图解析器 -->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="viewClass" value="org.springframework.web.servlet.view.JstlView"/>
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
<!--    扫描web相关的bean-->
<context:component-scan base-package="com.lqx.controller"></context:component-scan>
</beans>
```

#### Spring配置整合文件，applicationContext.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">
    <import resource="classpath:spring-dao.xml"></import>
<!--    <import resource="classpath:spring-service"></import>-->
    <import resource="classpath:spring_service.xml"></import>
    <import resource="classpath:spring-mvc.xml"></import>
</beans>
```

## Controller 和视图层

### BookController 类

```java
package com.lqx.controller;

import com.lqx.pojo.Books;
import com.lqx.service.IBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BooksController {
    @Autowired
    @Qualifier("booksServiceImpl")
    private IBooksService booksService;
    @RequestMapping("/allBook")
    public String allBook(Model model){
        List<Books> list = booksService.selectAllBooks();
        model.addAttribute("list",list);
        return "allBook";
    }
    @RequestMapping("/toAddBook")
    public String toAddBook(){
        return "addBook";
    }
    @RequestMapping("/addBook")
    public String addBook(Model model,Books books){
        if(booksService.queryBooksByName(books.getBookName()).isEmpty()){
        booksService.addBooks(books);
        }else{
            model.addAttribute("exitBookError","该书籍已经存在！！");
        }
//        转发
        return "forward:/book/allBook";
//        return "redirect:/book/allBook";
    }
    @RequestMapping("/toUpdate")
    public String toUpdate(Model model,int id){
        Books books = booksService.selectBooksById(id);
//        System.out.println("toUpdate"+books);
        model.addAttribute("book",books );
        return "updateBooks";
    }
    @RequestMapping("/updateBook")
    public String updateBook(Model model, Books book){
        if(booksService.queryBooksByName(book.getBookName()).isEmpty()){
            //判断修改后书籍是否有存在的
            booksService.updateBook(book);
            Books books = booksService.selectBooksById(book.getBookId());
            model.addAttribute("books", books);
        }else{
            model.addAttribute("exitBookError","该书籍已经存在！！");
        }
        return "forward:/book/allBook";
//        return "allBook";
    }
    @RequestMapping("/deleteBooks/{bookId}")
    public String deleteBooks(@PathVariable("bookId") int id){
        booksService.deleteBookById(id);
        return "redirect:/book/allBook";
    }
    @RequestMapping("/queryBooks")
    public String queryBooks(Model model,String bookName){
//        bookName这里的参数要和前端的标签name一致才能获取到
//        System.out.println("bookName================"+bookName);
    List<Books> books = booksService.queryBooksByName(bookName);
    if(books.isEmpty()){
        books = booksService.selectAllBooks();
        model.addAttribute("error","未找到！！");
    }
    model.addAttribute("list",books);
    return "allBook";
    }
    @RequestMapping("demo")
    public String demo(){
      return "demo";
    }

}

```

### index.jsp

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

  <head>
    <title>$Title$</title>
    <style type="text/css">
      a {
        text-decoration: none;
        color: black;
        font-size: 18px;
      }
      h3 {
        width: 180px;
        height: 38px;
        margin: 100px auto;
        text-align: center;
        line-height: 38px;
        background: deepskyblue;
        border-radius: 4px;
      }
    </style>
  </head>

  <body>

  <h3><a href="${pageContext.request.contentType}/book/allBook">进入书籍页面</a></h3>

  </body>
</html>

### 书籍列表页面 allbook.jsp

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>主页面</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>书籍列表 —— 显示所有书籍</small>
                </h1>
            </div>
        </div>
    </div>
<div class="row">
    <div class="col-md-4 column">
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/toAddBook">新增</a>
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/allBook">显示全部</a>
        <span style="color: red; font-weight: bold">${exitBookError}</span>
    </div>
    <div class="col-md-8 column">


        <form class="form-inline" action="${pageContext.request.contextPath}/book/queryBooks" method="post" style="float: right">
            <span style="color: red; font-weight: bold">${error}</span>
            <input type="text" class="form-control" name="bookName" placeholder="请输入你要查询的书籍名称"/>
            <input type="submit" value="查询" class="btn btn-primary"/>
        </form>
    
    </div>

</div>

<div class="row clearfix">
    <div class="col-md-12 column">
        <table class="table table-hover table-striped">
            <thead>
            <tr>
                <th>书籍编号</th>
                <th>书籍名字</th>
                <th>书籍数量</th>
                <th>书籍详情</th>
                <th>操作</th>
            </tr>
            </thead>


            <tbody>
            <c:forEach var="book" items="${list}">
                <tr>
                    <td>${book.bookId}</td>
                    <td>${book.bookName}</td>
                    <td>${book.bookCounts}</td>
                    <td>${book.detail}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/book/toUpdate?id=${book.bookId}">更改</a> |
                        <a href="${pageContext.request.contextPath}/book/deleteBooks/${book.bookId}">删除</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</div>

</div>
</body>
</html>

### 添加书籍页面

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>新增书籍</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>


<body>

<div class="row clearfix">
    <div class="col-md-12 column">
        <div class="page-header">
            <h1>
                <small>新增书籍</small>
            </h1>
        </div>
    </div>
</div>
<form action="${pageContext.request.contextPath}/book/addBook" method="post">
    书籍名称：<input type="text" name="bookName"><br><br><br>
    书籍数量：<input type="text" name="bookCounts"><br><br><br>
    书籍详情：<input type="text" name="detail"><br><br><br>
    <input type="submit" value="添加">
</form>


</div>

</body>
</html>

### BookController 类

```java
package com.lqx.controller;

import com.lqx.pojo.Books;
import com.lqx.service.IBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BooksController {
    @Autowired
    @Qualifier("booksServiceImpl")
    private IBooksService booksService;
    @RequestMapping("/allBook")
    public String allBook(Model model){
        List<Books> list = booksService.selectAllBooks();
        model.addAttribute("list",list);
        return "allBook";
    }
    @RequestMapping("/toAddBook")
    public String toAddBook(){
        return "addBook";
    }
    @RequestMapping("/addBook")
    public String addBook(Model model,Books books){
        if(booksService.queryBooksByName(books.getBookName()).isEmpty()){
        booksService.addBooks(books);
        }else{
            model.addAttribute("exitBookError","该书籍已经存在！！");
        }
//        转发
        return "forward:/book/allBook";
//        return "redirect:/book/allBook";
    }
    @RequestMapping("/toUpdate")
    public String toUpdate(Model model,int id){
        Books books = booksService.selectBooksById(id);
//        System.out.println("toUpdate"+books);
        model.addAttribute("book",books );
        return "updateBooks";
    }
    @RequestMapping("/updateBook")
    public String updateBook(Model model, Books book){
        if(booksService.queryBooksByName(book.getBookName()).isEmpty()){
            //判断修改后书籍是否有存在的
            booksService.updateBook(book);
            Books books = booksService.selectBooksById(book.getBookId());
            model.addAttribute("books", books);
        }else{
            model.addAttribute("exitBookError","该书籍已经存在！！");
        }
        return "forward:/book/allBook";
//        return "allBook";
    }
    @RequestMapping("/deleteBooks/{bookId}")
    public String deleteBooks(@PathVariable("bookId") int id){
        booksService.deleteBookById(id);
        return "redirect:/book/allBook";
    }
    @RequestMapping("/queryBooks")
    public String queryBooks(Model model,String bookName){
//        bookName这里的参数要和前端的标签name一致才能获取到
//        System.out.println("bookName================"+bookName);
    List<Books> books = booksService.queryBooksByName(bookName);
    if(books.isEmpty()){
        books = booksService.selectAllBooks();
        model.addAttribute("error","未找到！！");
    }
    model.addAttribute("list",books);
    return "allBook";
    }
    @RequestMapping("demo")
    public String demo(){
      return "demo";
    }

}

```

### 修改书籍页面  updateBook.jsp

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>更新页面</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>

<body>

<div class="row clearfix">


    <div class="col-md-12 column">
        <div class="page-header">
            <h1>
                <small>修改书籍</small>
            </h1>
        </div>
    </div>

</div>

<form action="${pageContext.request.contextPath}/book/updateBook" method="post">
    <input type="hidden" name="bookId" value="${book.bookId}"/>
    书籍名称：<input type="text" name="bookName" value="${book.bookName}"><br><br><br>
    书籍数量：<input type="text" name="bookCounts" value="${book.bookCounts}"><br><br><br>
    书籍详情：<input type="text" name="detail" value="${book.detail}"><br><br><br>
    <input type="submit" value="更改">
</form>


<div class="container">
</div>


</body>
</html>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>修改信息</small>
                </h1>
            </div>
        </div>
    </div>
    <form action="${pageContext.request.contextPath}/book/updateBook" method="post">
        <input type="hidden" name="bookID" value="${book.getBookID()}"/>
        书籍名称：<input type="text" name="bookName" value="${book.getBookName()}"/>
        书籍数量：<input type="text" name="bookCounts" value="${book.getBookCounts()}"/>
        书籍详情：<input type="text" name="detail" value="${book.getDetail() }"/>
        <input type="submit" value="提交"/>
    </form>
</div>
配置Tomcat，进行运行
