# 码上影院（Movie Ticketing System）

一套在线购票系统：SpringBoot 多模块后端 + 用户端前端 + 管理端前端，支持影院/影厅/场次/座位、订单、在线选座购票、后台多角色权限管理，并在 2026 年新增了一个基于 tool-calling 的购票智能体。

---

## 先说清楚这个项目是什么（署名与范围）

这点很重要，我不想让人误读：

- **2023–2024**：这是一个**高校课程小组作业**（3 人分工），管理端前端 fork 自开源模板 [`vue-manage-system`](https://github.com/lin-xin/vue-manage-system)（`小组代码/面向管理员前端页面/package.json` 里的 `name` 字段就是它）。**这部分不是我的原创**，代码版权归原小组。
- **2026**：我**独自接手**了这个已经跑不起来、零测试、带明文密钥的旧项目，做了可用性审计、安全修复，并新增了三个功能模块。

所以这个仓库值得看的不是"我写了一个购票系统"，而是这两件事：

1. **在一个不是我写的、没有测试兜底的遗留系统上，把功能加进去且不让原本能跑的东西退化。**
2. **我把它救活的过程留下了完整证据**（见 `启动说明/审计-2026-10-06.md`）。

接手时项目**不在版本控制里**，所以无法用 commit 历史区分"2023 原有"和"2026 新增"。我的补偿做法是把改动清单和每一项的验证方式写进审计文档，并在下面的"2026 新增"一节明确列出边界。

---

## 架构

```
小组代码/
├─ 后端java代码/            Maven 多模块（SpringBoot 2.7.3 / Java 8 / MyBatis-Plus）
│  ├─ commons/             实体、Mapper、Service、工具、全局异常、七牛配置   133 个 .java
│  ├─ ai-agent/     [2026] LLM 客户端 + tool-calling 循环 + 规则兜底          16 个 .java
│  ├─ app/                 面向用户端 API        :81  context-path /app      35 个 .java
│  └─ manager/             面向管理端 API        :80  context-path /         32 个 .java
├─ 面向用户/                Vue 3 + Element Plus 2.4  开发端口 8080           17 个 .vue
└─ 面向管理员前端页面/       Vue 3 + Element Plus 1.0  开发端口 8081           45 个 .vue
```

数据层：MySQL 库名 `theater`（27 张表）+ Redis（管理端会话存储、未支付订单的 key 过期监听）。

规模：Java 12,579 行（216 文件），Vue 16,877 行（62 文件）。

**认证是两套**，这是旧项目遗留的设计：管理端 = JWT 当 key 存 Redis、30 分钟滑动过期；用户端 = 纯 JWT 无状态校验。

---

## 2026 新增

### ① 购票智能体 `ai-agent`（16 类 / 1,689 行）

用户端右下角一个浮窗，自然语言 → 结构化下单。

- **不是 prompt 塞满上下文的做法**：模型只拿到 5 个工具的 JSON Schema，自己决定调用哪个 —— `list_movies` / `list_cinemas` / `find_showtimes` / `seat_summary` / `draft_order`。循环最多 5 轮（`max-tool-rounds`），每轮把工具结果回灌。
- **工具直接打自己的 Service，不走 HTTP**，避免"AI 通过公网接口绕权限"这类问题。
- **兜底不是装饰**：没配 API key、或者模型 429/超时，会自动降级到 `RuleAssistant`（关键词+槽位抽取），界面照常能用。这条路径我实测过，不是理论上可用。
- **模型无关**：`ai.base-url` / `ai.model` 是配置项，`ai.api-key` 支持环境变量注入；接口按 OpenAI 兼容的 `/v1/chat/completions` 实现，换模型只改两行配置。不绑死某个 provider。
- **下单只到"草稿"**：智能体永远不直接扣库存，产出一张草稿单，跳回选座页把座位勾好，由人点最后那一下。这是产品决策，不是技术限制。

### ② 经营看板 `/dashboard`

后台首页从静态占位改成真实聚合查询。口径上做了两件容易做错的事：用 `LEFT JOIN` 保证"零票场次"也出现在结果里（否则"售出 0"和"没数据"长得一样），并单独统计了异常场次体检数。

### ③ 影评 / 想看社区功能

表结构迁移 + app 侧接口 + 用户端 `movie.vue` 影区块 + 后台审核/删除。座位状态做了一次值域拆分：**"损坏"不再等于"已售"**，这是原来数据模型里混在一起的两件事。

---

## 2026 修复（安全与可用性）

完整证据在 `启动说明/审计-2026-10-06.md`，每条都标了是浏览器实测、curl 打真实服务、还是源码那一行。这里只列结论：

| 级别 | 问题 | 修法 |
|---|---|---|
| 严重 | `spring.redis.password` 写了实际不存在的口令 → 管理端**每一个**登录后的请求 500，用户端后端 `:81` 直接起不来 | 改 `${REDIS_PASSWORD:}`，拦截器读会话失败按"未登录"处理而不是抛穿 |
| 严重 | 后台登录密码校验被写成恒真 → 任意密码可进管理端 | 恢复 BCrypt 校验 |
| 严重 | 拦截器 `excludePathPatterns("/cinema/**")` 匿名放行整条业务线（含删除、改影院） | 收回到只有登录与验证码两个接口 |
| 严重 | 七牛 AK/SK 明文写在 5 个 Java 文件里 | 抽 `QiniuConfig` + 环境变量，未配置时上传接口返回 511 |
| 高 | 权限 SQL 字符串拼接（注入面）、`'/%'` 前缀匹配导致子路径越权 | 参数化 + 修正匹配逻辑，用拦截器同一条 SQL 验证 18/18 通过 |
| 高 | app 侧按 id 查他人订单（IDOR）、改密码接口"假成功"且把密码哈希返回给前端 | 归属校验 + 明确错误码 506/509 |
| 中 | 角色管理页的删除/批量删除是**前端假按钮**（后端根本没有接口） | 补 `DELETE /charact/del/{id}` + `POST /charact/delBatch`，并接上前端 |

审计文档里还有一节 **「我推翻掉的误报」** —— 有几个看起来像 bug 的东西查下来是正常的，我把它们单独列出来，免得下一个人去"修"没坏的地方。

---

## 已知限制（公开写出来，不装）

- **零测试**：后端没有任何单元测试，两个前端没有 test/lint 脚本。改完之后唯一的自动验证是"能编译"。这是我最想补但还没补的债。
  （`commons/src/test` 里原本只有一个 MyBatis-Plus 代码生成器，含原作者的本地路径和库口令，已从公开仓库移除。）
- `TokenUtil.SECRET` 仍是硬编码弱密钥；`CinemaUserController` 的重置密码仍是固定值 `111111`。都属于已知项，我刻意留着没动，因为改它们会牵动接口契约，而这套系统目前没有任何测试能告诉我改坏了什么。
- 座位矩阵数据里存在 `"p30"` 这类非标准单元格；订单接口信任前端传来的座位矩阵。都在审计第九节列着。
- 没有 commit 历史可看（接手时不在版本控制里），早期提交只有一个初始快照。

---

## 快速开始

### 环境

JDK 8、Maven 3.9、MySQL 5.7+、Redis、Node 16+。

### 1. 建库

```sql
CREATE DATABASE theater DEFAULT CHARSET utf8mb4;
```
导入 `小组代码/all.sql`（演示数据），再按需执行 `小组代码/` 下带日期后缀的迁移脚本。

> `all.sql` 里**不含任何账号数据**：接手时那份原始 dump 有 29 行用户/管理员账号和他们的密码哈希，我把这些行删掉了才提交。所以初始状态没有可登录账号，请这样获得一个：
> 1. 打开用户端注册一个普通账号；
> 2. 在库里给它管理员角色：`UPDATE cinema_user SET role='1002' WHERE account='你的账号';`
> 3. 管理端登录用这个账号。
> `1002` 是超级管理员角色，`1001` 是普通管理员，可见菜单差异见 `charact` / `role_menu` 表。

### 2. 后端

```bat
set JAVA_HOME=<你的 JDK8 路径>
cd 小组代码\后端java代码
mvn -B install -DskipTests
mvn -B -pl manager spring-boot:run -Dspring-boot.run.jvmArguments="-Dfile.encoding=UTF-8"
mvn -B -pl app     spring-boot:run -Dspring-boot.run.jvmArguments="-Dfile.encoding=UTF-8"
```

（首次构建要联网拉依赖；本机已有仓库缓存后可以加 `-o` 离线跑。）

`:80` = 管理端 API，`:81` = 用户端 API。
注意 `:81` **强依赖 Redis**：`RedisConfig` 注册了 key 过期监听器，Redis 连不上就直接启动失败、端口不会监听。

### 3. 前端

```bat
cd 小组代码\面向用户          & npm install & npm run serve    REM :8080
cd 小组代码\面向管理员前端页面  & npm install & npm run serve    REM :8081
```

两个前端**是 vue-cli 5 / webpack，不是 Vite**。改 `.vue` 之后别去 grep `js/app.js`，懒加载 chunk 名是 `js/src_views_Login_vue.js`（用户端）、`js/login.js`（管理端登录）、`js/icon.js`（管理端多个页面共享的 chunk）。

### 4. 环境变量（都有默认值，不配也能跑，功能自动降级）

| 变量 | 作用 | 不配会怎样 |
|---|---|---|
| `SENSENOVA_API_KEY` 及 `ai.*` | 智能体的大模型 | AI 浮窗走规则兜底，仍可用 |
| `QINIU_ACCESS_KEY` / `QINIU_SECRET_KEY` | 图片上传 | 上传接口返回 `511 未配置七牛云密钥` |
| `MYSQL_PASSWORD` | 数据库口令 | 用本地开发默认值 |
| `REDIS_PASSWORD` | Redis 口令 | 不发送 AUTH（本机 Redis 未设 requirepass） |
| `VUE_APP_API_BASE` | 前端 API 基址 | 用 `.env` 里的本地默认值 |

---

## 目录里那几个说明文件

- `启动说明/审计-2026-10-06.md` —— 全站可用性审计报告，含误报清单，本仓库最有信息量的一份。
- `启动说明/readme.md`、`readme-admin.md`、`readme-user.md` —— 2023 年原小组写的启动说明，保留原样。
- `本地不上传/` —— 被 `.gitignore` 排除，不在仓库里。
