### oauth2-server

> 简介：最开始仅仅是为了熟悉Spring Security，配合oauth2.0进行权限验证等，后来就加入了mybatis-plus，redis缓存，rabbitmq消息发送，elasticSearch+canal数据搜索等......

> 基础架构：springboot+maven+mybatis-plus+mysql+redis+knife4j

> 相应功能：用户登录验证以及相关，权限管理，角色管理，文件上传，文件管理，邮件发送，消息推送，验证码生成，二维码生成，以及各种常用工具类......

> 运行环境：

* jdk：最低jdk8
* mysql：最低mysql8.0
* redis：依赖版本请查看 pom.xml中对应得版本
* mybatis-plus：依赖版本请查看 pom.xml中对应得版本
* knife4j2：依赖版本请查看pom.xml中对应得版本
* ElasticSearch：7.9.3（实际上7.6.2即可，我只是当时下载了最新版本而已，但是引入依赖的依旧是7.6.2）
* kibana：7.9.3（实际上7.6.2即可，我只是当时下载了最新版本而已，但是引入依赖的依旧是7.6.2）
* canal：1.5（因为只有1.5的版本支持es的7.x版本）
* rabbitMq：3.8.9（还有顺带的otp软件别忘了安装）
* MQTT:最新版本（具体的忘了......）


> 吐槽一下，印证了一句流传于程序员之间的话，看自己半年前的代码，我写过这个？这代码啥意思？这代码好丑......

