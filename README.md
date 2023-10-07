### github加速（基于改hosts文件ip映射实现）
#### 1.原理
+ （1）爬虫查询出github相关域名的ip地址
+ （2）将ip 域名的映射写入hosts文件中
+ （3）刷新windows dns缓存

#### 2.使用方法
+ 修改'./conf/defalut.conf'文件中的配置，里面有默认配置，可以直接拿来使用，若有其余域名需求，可以在里添加
+ 运行Run.class文件，按照控制台提示输入即可

#### 3.文件说明
```
|- conf：配置文件，按需修改里面内容，一般默认即可
|- doc：问题文档，收纳常见问题
|- cmd：windows启动文件，将java打包成jar后，可以通过双击此bat来一步启动，无需敲命令
|- src：源码
```