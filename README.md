# Jusic-serve

[![license](https://img.shields.io/github/license/hanhuoer/Jusic-serve?style=flat-square)](https://github.com/hanhuoer/Jusic-serve)
[![java](https://img.shields.io/badge/java-8-red?style=flat-square&logo=java)](https://github.com/hanhuoer/Jusic-serve)
[![java](https://img.shields.io/badge/springboot-2-green?style=flat-square&logo=spring)](https://github.com/hanhuoer/Jusic-serve)
[![stars](https://img.shields.io/github/stars/hanhuoer/Jusic-serve?style=flat-square)](https://github.com/hanhuoer/Jusic-serve)
[![issues](https://img.shields.io/github/issues/hanhuoer/Jusic-serve?style=flat-square)](https://github.com/hanhuoer/Jusic-serve)
[![forks](https://img.shields.io/github/forks/hanhuoer/Jusic-serve?style=flat-square)](https://github.com/hanhuoer/Jusic-serve)

```
                
             ,---._                                                                    
           .-- -.' \                                                                   
           |    |   :                            ,--,             .--,  .--,  .--,     
           :    ;   |         ,--,             ,--.'|             |\  \ |\  \ |\  \    
           :        |       ,'_ /|   .--.--.   |  |,              ` \  `` \  `` \  `   
           |    :   :  .--. |  | :  /  /    '  `--'_       ,---.   \ \  \\ \  \\ \  \  
           :         ,'_ /| :  . | |  :  /`./  ,' ,'|     /     \   , \  \, \  \, \  \ 
           |    ;   ||  ' | |  . . |  :  ;_    '  | |    /    / '   / /` // /` // /` / 
       ___ l         |  | ' |  | |  \  \    `. |  | :   .    ' /   ` /  /` /  /` /  /  
     /    /\    J   ::  | : ;  ; |   `----.   \'  : |__ '   ; :__ | .  /| .  /| .  /   
    /  ../  `..-    ,'  :  `--'   \ /  /`--'  /|  | '.'|'   | '.'|./__/ ./__/ ./__/    
    \    \         ; :  ,      .-./'--'.     / ;  :    ;|   :    :                     
     \    \      ,'   `--`----'      `--'---'  |  ,   /  \   \  /                      
      "---....--'                               ---`-'    `----'                       
                                                                                   
```


安装过程可以参考 [Wiki 页面](https://github.com/hanhuoer/Jusic-serve/wiki)，如果遇到问题可以在本项目提 issue


## 项目背景

逛 V 站的时候，偶然间看到一篇帖子介绍 [SyncMusic 点歌台](https://github.com/kasuganosoras/SyncMusic) 觉得挺有意思 ...

接下来 Jusic 就诞生了，嗯...这也是对所学 websocket 知识的一次实践。

后端: 本项目

前端: [Jusic-ui](https://github.com/hanhuoer/Jusic-ui)

## 安装

1. 克隆项目

   ```
   git clone https://github.com/hanhuoer/Jusic-serve.git
   ```

   

2. 安装 Redis

   [Redis](https://redis.io/)

3. 安装音乐基础服务

   你需要一个可以提供音乐的服务，本项目中使用的是另一个仓库的 [Musicoo](https://github.com/hanhuoer/Musicoo) 搭建的

4. 配置

   在 `src\main\resources\application.yml` 中配置好 redis 以及音乐服务

5. 打包项目

   ```
   # 项目是使用 maven 构建的，可以用下面的命令把项目打包成 jar 文件
   > mvn package
   # 如果觉得打包过程太久，那么可以选择下面这条命令跳过打包时的项目测试
   > mvn package -Dmaven.test.skip
   ```

   

6. 启动项目

   ```
   > java -jar jusic-serve.jar
   ```

   

7. 前端

   请前往 [Jusic-ui](https://github.com/hanhuoer/Jusic-ui) 项目



## 使用

1. 普通用户
   - 如果你想要点首歌可以在聊天窗口发送 `点歌 关键字`
   - 如果你不想听当前播放的音乐，那么你可以发起投票切换音乐 `投票切歌`
   - 如果你想要给自己修改一个昵称，那么你可以在聊天窗口发送 `设置昵称 名字`
2. 管理员
   - 如果你是点歌台的管理员，那么你可以这样获取网站的权限 `root password` 或者 `admin password`
   - 如果管理员觉得某一首音乐排序太靠后了，那么可以发送 `置顶音乐 音乐名`
   - 如果管理员觉得某一首音乐太难听，你可以移除播放列表 `删除音乐 音乐名`
   - 如果管理员发现有人在点歌台捣乱，那么你可以这样 `拉黑用户 用户id`
   - 如果管理员发现拉黑错了人，点歌台还提供另外一条指令 `漂白用户 用户id`
   - 如果管理员不想某一首音乐再次被点播，那么管理员可以这样 `拉黑音乐 音乐id`
   - 如果管理员想要从黑名单中移除某一首音乐，那么可以 `漂白音乐 音乐id`



## 在线预览

Jusic：[Jusic 点歌台](http://music.scoder.club/)

![preview](./doc/images/D453D047-42FA-4339-9E14-B23F354B3228.png)

![preview1](./doc/images/EFF30901-6EB1-466A-9BDF-77549039A150.png)



## 相关项目

kasuganosoras：[SyncMusic](https://github.com/kasuganosoras/SyncMusic)

ZeroDream：[Akkariin 点歌台](https://music.tql.ink)



## 更新日志

- 2020-02-01 可使用默认（热榜）或自定义歌单

- 2019-12-18 首次发布


## 开源协议

[GPL](https://github.com/hanhuoer/Musicoo/blob/master/LICENSE) © hanhuoer

