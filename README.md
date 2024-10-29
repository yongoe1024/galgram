# 文档
若依vue版本的升级版  
优势：仅增强了`生成器`与`mybatisplus`的相关功能，而且修改代码集中在新模块，不影响若依本身的代码，与若依本身无缝对接。

## 技术
前端技术栈 ES6、vue、vuex、vue-router、vue-cli、axios、element-ui  
后端技术栈 SpringBoot、MyBatis、Spring Security、Jwt  

## 功能升级
1. 代码放到新模块`serve`，不掺和到系统模块中
2. 生成的代码采用mybatis-plus
3. 生成的代码采用lombok
4. 生成的代码采用LocalDateTime、LocalDate等，不再使用Date
5. 生成器会自动生成到指定位置，不用移动，仅需执行sql就可以
6. 生成的前端api.js 与 index.vue放到一起，方便查看
7. 生成器可以自动添加表字段 remark、create_time、update_time、create_by、update_by(强制)
8. 生成器自动判断LocalDateTime、LocalDate与其对应的element-ui组件(若依都是日期选择器)
9. 数据库 int、bigint对应java的Integer、Long(若依都是Long)
10. @TableId实现功能：数据库自增id自增不传值、非自增Long采用雪花算法，非自增String采用uuid，其余类型不处理
11. 可直接将vue打包到admin模块的resource下的static目录下，访问http://localhost:8080/index.html(jar部署偷懒方法)
12. 生成树表，新增加的树形选择器组件更好看(若依的组件只支持一个顶级节点，本组件不限制)
13. 自动添加create_time、update_time、create_by、update_by的值(mybatis-plus自带的功能)
14. 前端带导入导出(默认不带导入)

## 以下与若依相同

## 环境
JDK >= 11 (推荐11版本)  
Mysql >= 5.7 (推荐8.0版本)  
Redis >= 3.0 (推荐7.0版本)  
Maven >= 3.0 (推荐3.9.1版本)  
Node >= 12 (推荐v16.16.0版本)  

## 前端运行
npm install --registry=https://registry.npmmirror.com  
npm i  
npm run serve  
或者安装pnpm   
pnpm config set registry https://registry.npmmirror.com  
pnpm i  
pnpm serve  

## 后端运行
1. 导入数据脚本`mysql8-galgram.sql`
2. 打开IDEA运行`GalgramApplication.java`。

## nginx
```
server {
    listen       80;
    server_name  localhost;
    charset utf-8;

    location / {
        root   /home/ruoyi/projects/ruoyi-ui;
        try_files $uri $uri/ /index.html;
        index  index.html index.htm;
    }
    
    location /prod-api/ {
        proxy_set_header Host $http_host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header REMOTE-HOST $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_pass http://localhost:8080/;
    }

    error_page   500 502 503 504  /50x.html;
    location = /50x.html {
        root   html;
    }
}
```

## 项目结构
```
com.galgram     
├── common            // 工具类
│       └── annotation                    // 自定义注解
│       └── config                        // 全局配置
│       └── constant                      // 通用常量
│       └── core                          // 核心控制
│       └── enums                         // 通用枚举
│       └── exception                     // 通用异常
│       └── filter                        // 过滤器处理
│       └── utils                         // 通用类处理
├── framework         // 框架核心
│       └── aspectj                       // 注解实现
│       └── config                        // 系统配置
│       └── datasource                    // 数据权限
│       └── interceptor                   // 拦截器
│       └── manager                       // 异步处理
│       └── security                      // 权限控制
│       └── web                           // 前端控制
├── ruoyi-generator   // 代码生成（可移除）
├── ruoyi-quartz      // 定时任务（可移除）
├── ruoyi-system      // 系统代码
├── ruoyi-admin       // 后台服务
├── ruoyi-serve      // 其他模块
```
```
├── build                      // 构建相关  
├── bin                        // 执行脚本
├── public                     // 公共文件
│   ├── favicon.ico            // favicon图标
│   └── index.html             // html模板
│   └── robots.txt             // 反爬虫
├── src                        // 源代码
│   ├── api                    // 所有请求
│   ├── assets                 // 主题 字体等静态资源
│   ├── components             // 全局公用组件
│   ├── directive              // 全局指令
│   ├── layout                 // 布局
│   ├── plugins                // 通用方法
│   ├── router                 // 路由
│   ├── store                  // 全局 store管理
│   ├── utils                  // 全局公用方法
│   ├── views                  // view
│   ├── App.vue                // 入口页面
│   ├── main.js                // 入口 加载组件 初始化等
│   ├── permission.js          // 权限管理
│   └── settings.js            // 系统配置
├── .editorconfig              // 编码格式
├── .env.development           // 开发环境配置
├── .env.production            // 生产环境配置
├── .env.staging               // 测试环境配置
├── .eslintignore              // 忽略语法检查
├── .eslintrc.js               // eslint 配置项
├── .gitignore                 // git 忽略项
├── babel.config.js            // babel.config.js
├── package.json               // package.json
└── vue.config.js              // vue.config.js
```