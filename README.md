<h1 >一个ssm整合图书后台管理系统</h1>
<h2 >环境要求</h2>
<blockquote><p>IDEA
MySQL 5.7
Tomcat 8.5
Maven 3.6</p>
</blockquote>
<h2 >创建数据库</h2>
<pre><code class='language-myssql' lang='myssql'>CREATE DATABASE `ssmbuild`;
USE `ssmbuild`;
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
  `bookID` INT(10) NOT NULL AUTO_INCREMENT COMMENT &#39;书id&#39;,
  `bookName` VARCHAR(100) NOT NULL COMMENT &#39;书名&#39;,
  `bookCounts` INT(11) NOT NULL COMMENT &#39;数量&#39;,
  `detail` VARCHAR(200) NOT NULL COMMENT &#39;描述&#39;,
  KEY `bookID` (`bookID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8
INSERT  INTO `books`(`bookID`,`bookName`,`bookCounts`,`detail`)VALUES 
(5,&#39;fofo&#39;,13,&#39;1232432&#39;),
(10,&#39;java也还好&#39;,122,&#39;别学了&#39;);
</code></pre>
<h2 >基本环境搭建</h2>
<ul>
<li>新建一Maven项目， 添加web的支持</li>

</ul>
<h3 >导入相关的pom依赖</h3>
<pre><code class='language-pom' lang='pom'>&lt;dependencies&gt;
        &lt;!--Junit--&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;junit&lt;/groupId&gt;
            &lt;artifactId&gt;junit&lt;/artifactId&gt;
            &lt;version&gt;4.12&lt;/version&gt;
        &lt;/dependency&gt;
        &lt;!--数据库驱动--&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;mysql&lt;/groupId&gt;
            &lt;artifactId&gt;mysql-connector-java&lt;/artifactId&gt;
            &lt;version&gt;5.1.47&lt;/version&gt;
        &lt;/dependency&gt;
        &lt;!-- 数据库连接池 --&gt;
        &lt;dependency&gt;
            &lt;groupId&gt;com.mchange&lt;/groupId&gt;
            &lt;artifactId&gt;c3p0&lt;/artifactId&gt;
            &lt;version&gt;0.9.5.2&lt;/version&gt;
        &lt;/dependency&gt;
         &lt;dependency&gt;
        &lt;groupId&gt;javax.servlet&lt;/groupId&gt;
        &lt;artifactId&gt;servlet-api&lt;/artifactId&gt;
        &lt;version&gt;2.5&lt;/version&gt;
    &lt;/dependency&gt;
    &lt;dependency&gt;
        &lt;groupId&gt;javax.servlet.jsp&lt;/groupId&gt;
        &lt;artifactId&gt;jsp-api&lt;/artifactId&gt;
        &lt;version&gt;2.2&lt;/version&gt;
    &lt;/dependency&gt;
    &lt;dependency&gt;
        &lt;groupId&gt;javax.servlet&lt;/groupId&gt;
        &lt;artifactId&gt;jstl&lt;/artifactId&gt;
        &lt;version&gt;1.2&lt;/version&gt;
    &lt;/dependency&gt;

    &lt;!--Mybatis--&gt;
    &lt;dependency&gt;
        &lt;groupId&gt;org.mybatis&lt;/groupId&gt;
        &lt;artifactId&gt;mybatis&lt;/artifactId&gt;
        &lt;version&gt;3.5.2&lt;/version&gt;
    &lt;/dependency&gt;
    &lt;dependency&gt;
        &lt;groupId&gt;org.mybatis&lt;/groupId&gt;
        &lt;artifactId&gt;mybatis-spring&lt;/artifactId&gt;
        &lt;version&gt;2.0.2&lt;/version&gt;
    &lt;/dependency&gt;

    &lt;!--Spring--&gt;
    &lt;dependency&gt;
        &lt;groupId&gt;org.springframework&lt;/groupId&gt;
        &lt;artifactId&gt;spring-webmvc&lt;/artifactId&gt;
        &lt;version&gt;5.1.9.RELEASE&lt;/version&gt;
    &lt;/dependency&gt;
    &lt;dependency&gt;
        &lt;groupId&gt;org.springframework&lt;/groupId&gt;
        &lt;artifactId&gt;spring-jdbc&lt;/artifactId&gt;
        &lt;version&gt;5.1.9.RELEASE&lt;/version&gt;
    &lt;/dependency&gt;
    &lt;dependency&gt;
        &lt;groupId&gt;org.projectlombok&lt;/groupId&gt;
        &lt;artifactId&gt;lombok&lt;/artifactId&gt;
        &lt;version&gt;1.18.8&lt;/version&gt;
    &lt;/dependency&gt;
    &lt;dependency&gt;
        &lt;groupId&gt;org.aspectj&lt;/groupId&gt;
        &lt;artifactId&gt;aspectjweaver&lt;/artifactId&gt;
        &lt;version&gt;1.8.13&lt;/version&gt;
    &lt;/dependency&gt;
&lt;/dependencies&gt;
</code></pre>
<ul>
<li>Maven资源过滤设置</li>

</ul>
<pre><code class='language-pom' lang='pom'> &lt;build&gt;
            &lt;resources&gt;
                &lt;resource&gt;
                    &lt;directory&gt;src/main/java&lt;/directory&gt;
                    &lt;includes&gt;
                        &lt;include&gt;**/*.properties&lt;/include&gt;
                        &lt;include&gt;**/*.xml&lt;/include&gt;
                    &lt;/includes&gt;
                    &lt;filtering&gt;true&lt;/filtering&gt;
                &lt;/resource&gt;
                &lt;resource&gt;
                    &lt;directory&gt;src/main/resources&lt;/directory&gt;
                    &lt;includes&gt;
                        &lt;include&gt;**/*.properties&lt;/include&gt;
                        &lt;include&gt;**/*.xml&lt;/include&gt;
                    &lt;/includes&gt;
                    &lt;filtering&gt;true&lt;/filtering&gt;
                &lt;/resource&gt;
            &lt;/resources&gt;
</code></pre>
<h3 >建立基本结构和配置框架</h3>
<pre><code class='language-java' lang='java'>com.lqx.pojo
com.lqx.dao
com.lqx.service
com.lqx.controller
mybatis-config.xml
</code></pre>
<h2 >Mybatis层编写</h2>
<pre><code class='language-properties' lang='properties'>driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis?useSSL=false&amp;useUnicode=true&amp;characterEncoding=utf8
name=root
pwd=
</code></pre>
<ul>
<li>IDEA关联数据库</li>

</ul>
<h3 >MyBatis的核心配置文件</h3>
<pre><code class='language-xml' lang='xml'>&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot; ?&gt;
&lt;!DOCTYPE configuration
        PUBLIC &quot;-//mybatis.org//DTD Config 3.0//EN&quot;
        &quot;http://mybatis.org/dtd/mybatis-3-config.dtd&quot;&gt;
&lt;configuration&gt;
&lt;!--    &lt;settings&gt;--&gt;
&lt;!--        &lt;setting name=&quot;logImpl&quot; value=&quot;STDOUT_LOGGING&quot;/&gt;--&gt;
&lt;!--    &lt;/settings&gt;--&gt;
    &lt;typeAliases&gt;
        &lt;package name=&quot;com.lqx.pojo&quot;/&gt;
    &lt;/typeAliases&gt;
    &lt;mappers&gt;
        &lt;mapper class=&quot;com.lqx.dao.BooksMapper&quot;&gt;&lt;/mapper&gt;
&lt;!--        &lt;mapper resource=&quot;com/lqx/dao/BooksMapper.xml&quot;&gt;&lt;/mapper&gt;--&gt;
    &lt;/mappers&gt;
&lt;/configuration&gt;
</code></pre>
<ul>
<li>数据库对应的实体类 com.lqx.pojo.Books</li>

</ul>
<pre><code class='language-java' lang='java'>package com.lqx.pojo;
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
</code></pre>
<ul>
<li>Dao层的 Mapper接口</li>

</ul>
<pre><code class='language-java' lang='java'>package com.lqx.dao;
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
    int deleteBookById(@Param(&quot;bookId&quot;) int id);
    /**
     *  修改一个
     */
    int updateBook(Books book);

    /**
     * 查询一个
     * @param id
     * @return
     */
    Books selectBooksById(@Param(&quot;bookId&quot;) int id);

    /**
     * 查询所有
     * @return
     */
    List&lt;Books&gt; selectAllBooks();

    List&lt;Books&gt; queryBooksByName(@Param(&quot;bookName&quot;) String bookName);
}

</code></pre>
<h3 >编写接口对应的 Mapper.xml 文件</h3>
<pre><code class='language-xml' lang='xml'>&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot; ?&gt;
&lt;!DOCTYPE mapper
        PUBLIC &quot;-//mybatis.org//DTD Config 3.0//EN&quot;
        &quot;http://mybatis.org/dtd/mybatis-3-mapper.dtd&quot;&gt;
&lt;mapper namespace=&quot;com.lqx.dao.BooksMapper&quot;&gt;
    &lt;insert id=&quot;addBooks&quot; parameterType=&quot;Books&quot;&gt;
        insert into ssmbuild.books(bookName, bookCounts, detail) values (#{bookName},#{bookCounts},#{detail})
    &lt;/insert&gt;

    &lt;delete id=&quot;deleteBookById&quot; parameterType=&quot;_int&quot;&gt;
        delete from ssmbuild.books where bookId=#{bookId}
    &lt;/delete&gt;

    &lt;update id=&quot;updateBook&quot; parameterType=&quot;Books&quot;&gt;
        update ssmbuild.books set bookName=#{bookName},bookCounts=#{bookCounts},detail=#{detail} where bookId=#{bookId};
    &lt;/update&gt;

    &lt;select id=&quot;selectBooksById&quot; parameterType=&quot;_int&quot; resultType=&quot;Books&quot;&gt;
        select * from ssmbuild.books where bookId=#{bookId};
    &lt;/select&gt;

    &lt;select id=&quot;selectAllBooks&quot; resultType=&quot;Books&quot;&gt;
        select * from ssmbuild.books
    &lt;/select&gt;

    &lt;select id=&quot;queryBooksByName&quot; resultType=&quot;Books&quot;&gt;
        select * from ssmbuild.books where bookName like &quot;%&quot;#{bookName}&quot;%&quot;
    &lt;/select&gt;
&lt;/mapper&gt;
</code></pre>
<h3 >Service层的接口和实现类</h3>
<pre><code class='language-java' lang='java'>package com.lqx.service;

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
    List&lt;Books&gt; selectAllBooks();

    List&lt;Books&gt; queryBooksByName(String bookName);
}
</code></pre>
<p><strong>接口</strong></p>
<pre><code class='language-java' lang='java'>package com.lqx.service.impl;

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
    public List&lt;Books&gt; selectAllBooks() {
        return booksMapper.selectAllBooks();
    }

    public List&lt;Books&gt; queryBooksByName(String bookName) {
        return booksMapper.queryBooksByName(bookName);
    }
}

</code></pre>
<p>底层需求操作完毕</p>
<h2 >Spring层</h2>
<p><strong>配置Spring整合MyBatis，数据源使用c3p0连接池；</strong></p>
<h4 >Spring整合Mybatis的相关的配置文件</h4>
<pre><code class='language-xml' lang='xml'>&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
&lt;beans xmlns=&quot;http://www.springframework.org/schema/beans&quot;
       xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
       xmlns:context=&quot;http://www.springframework.org/schema/context&quot;
       xsi:schemaLocation=&quot;http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd&quot;&gt;

    &lt;context:property-placeholder location=&quot;classpath:db.properties&quot;/&gt;
&lt;!--    数据库连接池--&gt;
    &lt;bean id=&quot;dataSource&quot; class=&quot;com.mchange.v2.c3p0.ComboPooledDataSource&quot;&gt;
        &lt;property name=&quot;driverClass&quot; value=&quot;${driver}&quot;&gt;&lt;/property&gt;
        &lt;property name=&quot;jdbcUrl&quot; value=&quot;${url}&quot;&gt;&lt;/property&gt;
        &lt;property name=&quot;user&quot; value=&quot;${name}&quot;&gt;&lt;/property&gt;
        &lt;property name=&quot;password&quot; value=&quot;${pwd}&quot;&gt;&lt;/property&gt;
        &lt;!-- c3p0连接池的私有属性 --&gt;
        &lt;property name=&quot;maxPoolSize&quot; value=&quot;30&quot;/&gt;
        &lt;property name=&quot;minPoolSize&quot; value=&quot;10&quot;/&gt;
        &lt;!-- 关闭连接后不自动commit --&gt;
        &lt;property name=&quot;autoCommitOnClose&quot; value=&quot;false&quot;/&gt;
        &lt;!-- 获取连接超时时间 --&gt;
        &lt;property name=&quot;checkoutTimeout&quot; value=&quot;10000&quot;/&gt;
        &lt;!-- 当获取连接失败重试次数 --&gt;
        &lt;property name=&quot;acquireRetryAttempts&quot; value=&quot;2&quot;/&gt;
    &lt;/bean&gt;

&lt;!--    配置SqlSessionFactory--&gt;
    &lt;bean id=&quot;sqlSessionFactory&quot; class=&quot;org.mybatis.spring.SqlSessionFactoryBean&quot;&gt;
&lt;!--        注入数据库--&gt;
        &lt;property name=&quot;dataSource&quot; ref=&quot;dataSource&quot;&gt;&lt;/property&gt;
&lt;!--        配置mybatis-config.xml--&gt;
        &lt;property name=&quot;configLocation&quot; value=&quot;classpath:mybatis-config.xml&quot;/&gt;
    &lt;/bean&gt;
&lt;!--    配置扫描Dao接口包，动态实现接口注入到spring容器中--&gt;
    &lt;bean class=&quot;org.mybatis.spring.mapper.MapperScannerConfigurer&quot;&gt;
        &lt;property name=&quot;sqlSessionFactoryBeanName&quot; value=&quot;sqlSessionFactory&quot;/&gt;
&lt;!--        给出需要扫描Dao接口包--&gt;
        &lt;property name=&quot;basePackage&quot; value=&quot;com.lqx.dao&quot;/&gt;
    &lt;/bean&gt;
&lt;/beans&gt;
</code></pre>
<h4 >Spring整合service层</h4>
<pre><code class='language-xml' lang='xml'>&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
&lt;beans xmlns=&quot;http://www.springframework.org/schema/beans&quot;
       xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
       xmlns:context=&quot;http://www.springframework.org/schema/context&quot;
       xmlns:tx=&quot;http://www.springframework.org/schema/tx&quot;
       xmlns:aop=&quot;http://www.springframework.org/schema/aop&quot;
       xsi:schemaLocation=&quot;http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans.xsd
   http://www.springframework.org/schema/context
   http://www.springframework.org/schema/context/spring-context.xsd
   http://www.springframework.org/schema/tx
   http://www.springframework.org/schema/tx/spring-tx.xsd
   http://www.springframework.org/schema/aop
   https://www.springframework.org/schema/aop/spring-aop.xsd&quot;&gt;
    &lt;context:component-scan base-package=&quot;com.lqx.service&quot;&gt;&lt;/context:component-scan&gt;

    &lt;!--    BooksSeervice注入到IOC容器中--&gt;
    &lt;bean id=&quot;booksServiceImpl&quot; class=&quot;com.lqx.service.impl.BooksServiceImpl&quot;&gt;
        &lt;property name=&quot;booksMapper&quot; ref=&quot;booksMapper&quot;&gt;&lt;/property&gt;
    &lt;/bean&gt;

    &lt;bean id=&quot;transactionManager&quot; class=&quot;org.springframework.jdbc.datasource.DataSourceTransactionManager&quot;&gt;
        &lt;property name=&quot;dataSource&quot; ref=&quot;dataSource&quot;&gt;&lt;/property&gt;
    &lt;/bean&gt;
    &lt;tx:advice id=&quot;txAdvice&quot; transaction-manager=&quot;transactionManager&quot;&gt;
        &lt;tx:attributes&gt;
            &lt;tx:method name=&quot;*&quot; propagation=&quot;REQUIRED&quot;/&gt;
        &lt;/tx:attributes&gt;
    &lt;/tx:advice&gt;
    &lt;aop:config&gt;
        &lt;aop:pointcut id=&quot;txPointCut&quot; expression=&quot;execution(* com.lqx.dao.*.*(..))&quot;/&gt;
        &lt;aop:advisor advice-ref=&quot;txAdvice&quot; pointcut-ref=&quot;txPointCut&quot;&gt;&lt;/aop:advisor&gt;
    &lt;/aop:config&gt;

&lt;/beans&gt;
</code></pre>
<h2 >SpringMVC层</h2>
<h4 >web.xml</h4>
<pre><code class='language-xml' lang='xml'>&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
&lt;web-app xmlns=&quot;http://xmlns.jcp.org/xml/ns/javaee&quot;
         xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
         xsi:schemaLocation=&quot;http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd&quot;
         version=&quot;4.0&quot;&gt;
    &lt;servlet&gt;
        &lt;servlet-name&gt;DispatcherServlet&lt;/servlet-name&gt;
        &lt;servlet-class&gt;org.springframework.web.servlet.DispatcherServlet&lt;/servlet-class&gt;
        &lt;init-param&gt;
            &lt;param-name&gt;contextConfigLocation&lt;/param-name&gt;
            &lt;!--一定要注意:我们这里加载的是总的配置文件，之前被这里坑了！--&gt;
            &lt;param-value&gt;classpath:applicationContext.xml&lt;/param-value&gt;
        &lt;/init-param&gt;
        &lt;load-on-startup&gt;1&lt;/load-on-startup&gt;
    &lt;/servlet&gt;
    &lt;servlet-mapping&gt;
        &lt;servlet-name&gt;DispatcherServlet&lt;/servlet-name&gt;
        &lt;url-pattern&gt;/&lt;/url-pattern&gt;
    &lt;/servlet-mapping&gt;

    &lt;!--encodingFilter--&gt;
    &lt;filter&gt;
        &lt;filter-name&gt;encodingFilter&lt;/filter-name&gt;
        &lt;filter-class&gt;
            org.springframework.web.filter.CharacterEncodingFilter
        &lt;/filter-class&gt;
        &lt;init-param&gt;
            &lt;param-name&gt;encoding&lt;/param-name&gt;
            &lt;param-value&gt;utf-8&lt;/param-value&gt;
        &lt;/init-param&gt;
    &lt;/filter&gt;
    &lt;filter-mapping&gt;
        &lt;filter-name&gt;encodingFilter&lt;/filter-name&gt;
        &lt;url-pattern&gt;/*&lt;/url-pattern&gt;
    &lt;/filter-mapping&gt;

    &lt;!--Session过期时间--&gt;
    &lt;session-config&gt;
        &lt;session-timeout&gt;15&lt;/session-timeout&gt;
    &lt;/session-config&gt;
&lt;/web-app&gt;
</code></pre>
<h4 >spring-mvc.xml</h4>
<pre><code class='language-xml' lang='xml'>&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
&lt;beans xmlns=&quot;http://www.springframework.org/schema/beans&quot;
       xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
       xmlns:context=&quot;http://www.springframework.org/schema/context&quot;
       xmlns:mvc=&quot;http://www.springframework.org/schema/mvc&quot;
       xsi:schemaLocation=&quot;http://www.springframework.org/schema/beans
   http://www.springframework.org/schema/beans/spring-beans.xsd
   http://www.springframework.org/schema/context
   http://www.springframework.org/schema/context/spring-context.xsd
   http://www.springframework.org/schema/mvc
   https://www.springframework.org/schema/mvc/spring-mvc.xsd&quot;&gt;
    &lt;!-- 配置SpringMVC --&gt;
    &lt;!-- 1.开启SpringMVC注解驱动 --&gt;
    &lt;mvc:annotation-driven /&gt;
    &lt;!-- 2.静态资源默认servlet配置--&gt;
    &lt;mvc:default-servlet-handler/&gt;

    &lt;!-- 3.配置jsp 显示ViewResolver视图解析器 --&gt;
    &lt;bean class=&quot;org.springframework.web.servlet.view.InternalResourceViewResolver&quot;&gt;
        &lt;property name=&quot;viewClass&quot; value=&quot;org.springframework.web.servlet.view.JstlView&quot;/&gt;
        &lt;property name=&quot;prefix&quot; value=&quot;/WEB-INF/jsp/&quot;/&gt;
        &lt;property name=&quot;suffix&quot; value=&quot;.jsp&quot;/&gt;
    &lt;/bean&gt;
&lt;!--    扫描web相关的bean--&gt;
&lt;context:component-scan base-package=&quot;com.lqx.controller&quot;&gt;&lt;/context:component-scan&gt;
&lt;/beans&gt;
</code></pre>
<h4 >Spring配置整合文件，applicationContext.xml</h4>
<pre><code class='language-xml' lang='xml'>&lt;?xml version=&quot;1.0&quot; encoding=&quot;UTF-8&quot;?&gt;
&lt;beans xmlns=&quot;http://www.springframework.org/schema/beans&quot;
       xmlns:xsi=&quot;http://www.w3.org/2001/XMLSchema-instance&quot;
       xsi:schemaLocation=&quot;http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd&quot;&gt;
    &lt;import resource=&quot;classpath:spring-dao.xml&quot;&gt;&lt;/import&gt;
&lt;!--    &lt;import resource=&quot;classpath:spring-service&quot;&gt;&lt;/import&gt;--&gt;
    &lt;import resource=&quot;classpath:spring_service.xml&quot;&gt;&lt;/import&gt;
    &lt;import resource=&quot;classpath:spring-mvc.xml&quot;&gt;&lt;/import&gt;
&lt;/beans&gt;
</code></pre>
<h2 >Controller 和视图层</h2>
<h3 >BookController 类</h3>
<pre><code class='language-java' lang='java'>package com.lqx.controller;

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
@RequestMapping(&quot;/book&quot;)
public class BooksController {
    @Autowired
    @Qualifier(&quot;booksServiceImpl&quot;)
    private IBooksService booksService;
    @RequestMapping(&quot;/allBook&quot;)
    public String allBook(Model model){
        List&lt;Books&gt; list = booksService.selectAllBooks();
        model.addAttribute(&quot;list&quot;,list);
        return &quot;allBook&quot;;
    }
    @RequestMapping(&quot;/toAddBook&quot;)
    public String toAddBook(){
        return &quot;addBook&quot;;
    }
    @RequestMapping(&quot;/addBook&quot;)
    public String addBook(Model model,Books books){
        if(booksService.queryBooksByName(books.getBookName()).isEmpty()){
        booksService.addBooks(books);
        }else{
            model.addAttribute(&quot;exitBookError&quot;,&quot;该书籍已经存在！！&quot;);
        }
//        转发
        return &quot;forward:/book/allBook&quot;;
//        return &quot;redirect:/book/allBook&quot;;
    }
    @RequestMapping(&quot;/toUpdate&quot;)
    public String toUpdate(Model model,int id){
        Books books = booksService.selectBooksById(id);
//        System.out.println(&quot;toUpdate&quot;+books);
        model.addAttribute(&quot;book&quot;,books );
        return &quot;updateBooks&quot;;
    }
    @RequestMapping(&quot;/updateBook&quot;)
    public String updateBook(Model model, Books book){
        if(booksService.queryBooksByName(book.getBookName()).isEmpty()){
            //判断修改后书籍是否有存在的
            booksService.updateBook(book);
            Books books = booksService.selectBooksById(book.getBookId());
            model.addAttribute(&quot;books&quot;, books);
        }else{
            model.addAttribute(&quot;exitBookError&quot;,&quot;该书籍已经存在！！&quot;);
        }
        return &quot;forward:/book/allBook&quot;;
//        return &quot;allBook&quot;;
    }
    @RequestMapping(&quot;/deleteBooks/{bookId}&quot;)
    public String deleteBooks(@PathVariable(&quot;bookId&quot;) int id){
        booksService.deleteBookById(id);
        return &quot;redirect:/book/allBook&quot;;
    }
    @RequestMapping(&quot;/queryBooks&quot;)
    public String queryBooks(Model model,String bookName){
//        bookName这里的参数要和前端的标签name一致才能获取到
//        System.out.println(&quot;bookName================&quot;+bookName);
    List&lt;Books&gt; books = booksService.queryBooksByName(bookName);
    if(books.isEmpty()){
        books = booksService.selectAllBooks();
        model.addAttribute(&quot;error&quot;,&quot;未找到！！&quot;);
    }
    model.addAttribute(&quot;list&quot;,books);
    return &quot;allBook&quot;;
    }
    @RequestMapping(&quot;demo&quot;)
    public String demo(){
      return &quot;demo&quot;;
    }

}

</code></pre>
<h3 >index.jsp</h3>
<p>&lt;%@ page contentType=&quot;text/html;charset=UTF-8&quot; language=&quot;java&quot; %&gt;
<html></p>
  
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
  
<p>  <body></p>
  <h3><a href="${pageContext.request.contentType}/book/allBook">进入书籍页面</a></h3>
<p>  </body>
</html></p>
<h3 >书籍列表页面 allbook.jsp</h3>
<p>&lt;%@ taglib prefix=&quot;c&quot; uri=&quot;<a href='http://java.sun.com/jsp/jstl/core' target='_blank' class='url'>http://java.sun.com/jsp/jstl/core</a>&quot; %&gt;
&lt;%@ page contentType=&quot;text/html;charset=UTF-8&quot; language=&quot;java&quot; %&gt;
<html></p>

    <title>主页面</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

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
    <div class="col-md-8 column"></div></div></div>
<pre><code>    &lt;form class=&quot;form-inline&quot; action=&quot;${pageContext.request.contextPath}/book/queryBooks&quot; method=&quot;post&quot; style=&quot;float: right&quot;&gt;
        &lt;span style=&quot;color: red; font-weight: bold&quot;&gt;${error}&lt;/span&gt;
        &lt;input type=&quot;text&quot; class=&quot;form-control&quot; name=&quot;bookName&quot; placeholder=&quot;请输入你要查询的书籍名称&quot;/&gt;
        &lt;input type=&quot;submit&quot; value=&quot;查询&quot; class=&quot;btn btn-primary&quot;/&gt;
    &lt;/form&gt;

&lt;/div&gt;
</code></pre>
<p></div></p>
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
            </thead></table></div></div>
<pre><code>        &lt;tbody&gt;
        &lt;c:forEach var=&quot;book&quot; items=&quot;${list}&quot;&gt;
            &lt;tr&gt;
                &lt;td&gt;${book.bookId}&lt;/td&gt;
                &lt;td&gt;${book.bookName}&lt;/td&gt;
                &lt;td&gt;${book.bookCounts}&lt;/td&gt;
                &lt;td&gt;${book.detail}&lt;/td&gt;
                &lt;td&gt;
                    &lt;a href=&quot;${pageContext.request.contextPath}/book/toUpdate?id=${book.bookId}&quot;&gt;更改&lt;/a&gt; |
                    &lt;a href=&quot;${pageContext.request.contextPath}/book/deleteBooks/${book.bookId}&quot;&gt;删除&lt;/a&gt;
                &lt;/td&gt;
            &lt;/tr&gt;
        &lt;/c:forEach&gt;
        &lt;/tbody&gt;
    &lt;/table&gt;
&lt;/div&gt;
</code></pre>
<p></div></p>
<p></div>
</body>
</html></p>
<h3 >添加书籍页面</h3>
<p>&lt;%@ page contentType=&quot;text/html;charset=UTF-8&quot; language=&quot;java&quot; %&gt;
<html></p>

    <title>新增书籍</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

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
<p></div></p>
</body>
</html>
<h3 >BookController 类</h3>
<pre><code class='language-java' lang='java'>package com.lqx.controller;

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
@RequestMapping(&quot;/book&quot;)
public class BooksController {
    @Autowired
    @Qualifier(&quot;booksServiceImpl&quot;)
    private IBooksService booksService;
    @RequestMapping(&quot;/allBook&quot;)
    public String allBook(Model model){
        List&lt;Books&gt; list = booksService.selectAllBooks();
        model.addAttribute(&quot;list&quot;,list);
        return &quot;allBook&quot;;
    }
    @RequestMapping(&quot;/toAddBook&quot;)
    public String toAddBook(){
        return &quot;addBook&quot;;
    }
    @RequestMapping(&quot;/addBook&quot;)
    public String addBook(Model model,Books books){
        if(booksService.queryBooksByName(books.getBookName()).isEmpty()){
        booksService.addBooks(books);
        }else{
            model.addAttribute(&quot;exitBookError&quot;,&quot;该书籍已经存在！！&quot;);
        }
//        转发
        return &quot;forward:/book/allBook&quot;;
//        return &quot;redirect:/book/allBook&quot;;
    }
    @RequestMapping(&quot;/toUpdate&quot;)
    public String toUpdate(Model model,int id){
        Books books = booksService.selectBooksById(id);
//        System.out.println(&quot;toUpdate&quot;+books);
        model.addAttribute(&quot;book&quot;,books );
        return &quot;updateBooks&quot;;
    }
    @RequestMapping(&quot;/updateBook&quot;)
    public String updateBook(Model model, Books book){
        if(booksService.queryBooksByName(book.getBookName()).isEmpty()){
            //判断修改后书籍是否有存在的
            booksService.updateBook(book);
            Books books = booksService.selectBooksById(book.getBookId());
            model.addAttribute(&quot;books&quot;, books);
        }else{
            model.addAttribute(&quot;exitBookError&quot;,&quot;该书籍已经存在！！&quot;);
        }
        return &quot;forward:/book/allBook&quot;;
//        return &quot;allBook&quot;;
    }
    @RequestMapping(&quot;/deleteBooks/{bookId}&quot;)
    public String deleteBooks(@PathVariable(&quot;bookId&quot;) int id){
        booksService.deleteBookById(id);
        return &quot;redirect:/book/allBook&quot;;
    }
    @RequestMapping(&quot;/queryBooks&quot;)
    public String queryBooks(Model model,String bookName){
//        bookName这里的参数要和前端的标签name一致才能获取到
//        System.out.println(&quot;bookName================&quot;+bookName);
    List&lt;Books&gt; books = booksService.queryBooksByName(bookName);
    if(books.isEmpty()){
        books = booksService.selectAllBooks();
        model.addAttribute(&quot;error&quot;,&quot;未找到！！&quot;);
    }
    model.addAttribute(&quot;list&quot;,books);
    return &quot;allBook&quot;;
    }
    @RequestMapping(&quot;demo&quot;)
    public String demo(){
      return &quot;demo&quot;;
    }

}

</code></pre>
<h3 >修改书籍页面  updateBook.jsp</h3>
<p>&lt;%@ page contentType=&quot;text/html;charset=UTF-8&quot; language=&quot;java&quot; %&gt;
<html></p>

    <title>更新页面</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

<body>
<div class="row clearfix"></div>
<pre><code>&lt;div class=&quot;col-md-12 column&quot;&gt;
    &lt;div class=&quot;page-header&quot;&gt;
        &lt;h1&gt;
            &lt;small&gt;修改书籍&lt;/small&gt;
        &lt;/h1&gt;
    &lt;/div&gt;
&lt;/div&gt;
</code></pre>
<p></div></p>
<form action="${pageContext.request.contextPath}/book/updateBook" method="post">
    <input type="hidden" name="bookId" value="${book.bookId}">
    书籍名称：<input type="text" name="bookName" value="${book.bookName}"><br><br><br>
    书籍数量：<input type="text" name="bookCounts" value="${book.bookCounts}"><br><br><br>
    书籍详情：<input type="text" name="detail" value="${book.detail}"><br><br><br>
    <input type="submit" value="更改">
</form>
<div class="container">
</div>
</body>
</html>
<p>&lt;%@ page contentType=&quot;text/html;charset=UTF-8&quot; language=&quot;java&quot; %&gt;
<html></p>

    <title>更新页面</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

<body>
<div class="container">
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
    <input type="hidden" name="bookId" value="${book.bookId}">
    书籍名称：<input type="text" name="bookName" value="${book.bookName}"><br><br><br>
    书籍数量：<input type="text" name="bookCounts" value="${book.bookCounts}"><br><br><br>
    书籍详情：<input type="text" name="detail" value="${book.detail}"><br><br><br>
    <input type="submit" value="更改">
</form></div>
<p></div></p>
</body>
</html>
<h3 >配置tomcat，运行</h3>
<p>&nbsp;</p>
