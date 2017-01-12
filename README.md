# **Moana**

## Demo 地址： ##

### http://dev5.sprintechco.com ###
注1: 时间所限，目前项目的前端部分仅开发了移动版，建议使用手机或桌面浏览器开发者模式选择移动设备

注2: 第三方登陆提供微信和微博登陆, 其中微信登陆只能在手机微信应用自带浏览器中使用,微博登陆各终端均可使用

更新状态: 所有基本功能均打通, 前端用户交互已优化,后期将继续添加:

1. 后端增加退票接口并继续优化性能 

2. 目前支持用户修改昵称与邮箱,后期将支持所有用户修改头像

3. '热门'目前无展示,后续将提供给用户售票统计信息,展示热门影片

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

## 首页 ##

![WechatIMG64.jpeg](https://github.com/Shawn280LiYang/moana-backend/raw/master/screenshots/WechatIMG64.jpeg)

## 登陆页 ##

![WechatIMG65.jpeg](https://github.com/Shawn280LiYang/moana-backend/raw/master/screenshots/WechatIMG65.jpeg)

## 注册页 ##

![WechatIMG66.jpeg](https://github.com/Shawn280LiYang/moana-backend/raw/master/screenshots/WechatIMG66.jpeg)

## 用户主页 ##

![WechatIMG67.jpeg](https://github.com/Shawn280LiYang/moana-backend/raw/master/screenshots/WechatIMG67.jpeg)

## 用户信息修改页 ##

![WechatIMG68.jpeg](https://github.com/Shawn280LiYang/moana-backend/raw/master/screenshots/WechatIMG68.jpeg)

## 通知组件 ##

![WechatIMG69.jpeg](https://github.com/Shawn280LiYang/moana-backend/raw/master/screenshots/WechatIMG69.jpeg)

![WechatIMG69.jpeg](https://github.com/Shawn280LiYang/moana-backend/raw/master/screenshots/WechatIMG70.jpeg)




