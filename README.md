# **Moana**

## Demo 地址： ##

### http://dev5.sprintechco.com ###
注1：由于时间所限，目前项目的前端部分仅开发了移动版，建议使用手机或桌面浏览器开发模式

注2：线上版本还在继续开发中，预计1月10号完成，这之前功能可能不能完整体验，请见谅 :) 

注3：页面效果见附图

## 主要技术 ##

项目采用前后端分离设计，主要的技术框架为后端Spring boot与前端Angular 2.

（前端部分请见：https://github.com/Shawn280LiYang/moana-frontend ）

### 具体实现技术： ###
* 系统部署环境：CentOS 7
* 服务器：Nginx与Tomcat, Nginx伺服静态资源并转发动态请求到Tomcat
* Spring boot框架
* 数据持久层：dataNucleus提供的JPA组件
* 数据缓存：redis
* 数据库：mariaDB
* 前端：Angular2 与 Webpack
* 项目管理：Maven(后端), NPM(前端), git(版本管理)

## 功能介绍 ##
* 使用OAuth2.0支持微博与微信用户登陆
* 项目开发了自己的用户系统，支持用户注册、登陆、修改账户信息、绑定第三方账户
* 登陆过程使用MD5配合时间戳进行二次加密
* 用户主页展示已购电影票
* 首页每3秒刷新全部电影票余量
* 抢购成功发送邮件通知
* 每个用户每张电影票最多抢购数量限制为2张

## 附图 ##

### 首页 ###

![WechatIMG64.jpeg](https://github.com/Shawn280LiYang/moana-backend/raw/master/screenshots/WechatIMG64.jpeg)

### 用户个人主页 ###

![WechatIMG65.jpeg](https://github.com/Shawn280LiYang/moana-backend/raw/master/screenshots/WechatIMG65.jpeg)

### 登录页 ###

![WechatIMG66.jpeg](https://github.com/Shawn280LiYang/moana-backend/raw/master/screenshots/WechatIMG66.jpeg)

### 用户信息修改页 ###

![WechatIMG67.jpeg](https://github.com/Shawn280LiYang/moana-backend/raw/master/screenshots/WechatIMG67.jpeg)
