### solr
开源企业搜索平台

特点：

- 全文检索
- 数据库集成
- 动态聚类
- 富文本处理

#### 环境搭建
1.准备工作

- solr 6.0: 6.0 及以上使用java8编写
- jdk8
- tomcat8
- nginx

2.配置工作

- 配置使用tomcat启动，默认启动容器为jetty
- 配置mysql，使得可以从mysql中将数据导入solr中
- 配置中文分词工具IK Analyzer，导入搜狗词库，分词更准确
- 配置自动读取数据库新增数据

1.solr 读取数据库数据配置

solrconfig.xml

```xml
<requestHandler name="/dataimport" class="org.apache.solr.handler.dataimport.DataImportHandler">  
　  <lst name="defaults">  
　     <str name="config">data-config.xml</str>  
　  </lst>  
</requestHandler>  
```

data-config.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<dataConfig>
    <dataSource name="source1" type="JdbcDataSource" driver="com.mysql.jdbc.Driver" url="jdbc:mysql://dbm.office.domob-inc.cn:3306/tsia" user="domob" password="domob" batchSize="-1" />
　　<document>
        <entity name="key_words" pk="id"  dataSource="source1" 
                query="select * from key_words"
                deltaImportQuery="select * from key_words where id='${dih.delta.id}'"
                deltaQuery="select id from key_words where FROM_UNIXTIME(`create_time`, '%Y-%m-%d %H:%i:%S') > '${dataimporter.last_index_time}'">
　　　      <field column="id" name="id"/>
　　　      <field column="key" name="key"/>
           <field column="uid" name="uid"/>
           <field column="create_time" name="create_time"/>
　　　  </entity>
　　</document>
</dataConfig>
```
`deltaQuery`增量索引，原理是从数据库中根据deltaQuery指定的SQL语句查询出所有需要增量导入的数据

managed-schema

```xml
<field name="id" type="int" indexed="true" stored="true" required="true" multiValued="false" />  
<field name="key" type="string" indexed="true" stored="false"/>  
<field name="uid" type="int" indexed="true" stored="false"/>  
<field name="create_time" type="int" indexed="true" stored="true" />
```
2.solr 配置中文分词工具

```
ik-analyzer-solr5-5.x.jar
IKAnalyzer.cfg.xml
sougou_dict.dic
stopword.dic
```

solrconfig.xml

```xml
<fieldType name="text_ik" class="solr.TextField">
	<analyzer class="org.wltea.analyzer.lucene.IKAnalyzer"/>
</fieldType>
<field name="text_ik" type="text_ik" indexed="true" stored="true" multiValued="false" />
```


