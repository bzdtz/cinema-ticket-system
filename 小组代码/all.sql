/*
Navicat MySQL Data Transfer

Source Server         : xdb
Source Server Version : 80034
Source Host           : localhost:3306
Source Database       : theater

Target Server Type    : MYSQL
Target Server Version : 80034
File Encoding         : 65001

Date: 2024-01-07 00:34:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for actor
-- ----------------------------
DROP TABLE IF EXISTS `actor`;
CREATE TABLE `actor` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '演员id',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '演员的头像',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '演员真实的名字',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of actor
-- ----------------------------
INSERT INTO `actor` VALUES ('1', 'https://p0.pipi.cn/basicdata/25bfd62f537338f2aa50c8951f29fe1d7631e.jpg?imageView2/1/w/128/h/170', '许光汉');
INSERT INTO `actor` VALUES ('2', '	https://p0.pipi.cn/basicdata/25bfd6d77a38d3ddd20fafcb7a7be4080ea4e.jpg?imageView2/1/w/128/h/170', '章若楠');
INSERT INTO `actor` VALUES ('3', '	https://p0.pipi.cn/basicdata/25bfd6d7537e7a21f0d7c352df30c9b8be79f.jpg?imageView2/1/w/128/h/170', '丁冠森');
INSERT INTO `actor` VALUES ('4', 'https://p0.pipi.cn/basicdata/25bfd6d7537e7a11e506d60cca455ff8628ce.jpg?imageView2/1/w/128/h/170', '晏紫东');
INSERT INTO `actor` VALUES ('5', 'https://p0.pipi.cn/basicdata/25bfd6d77a3be13ba32c958cbd45abc628f93.jpg?imageView2/1/w/128/h/170', '郭丞');
INSERT INTO `actor` VALUES ('6', '	https://p0.pipi.cn/basicdata/25bfd6d753706d923506d6823487dcb50b549.jpg?imageView2/1/w/128/h/170', '王莎莎');
INSERT INTO `actor` VALUES ('7', 'https://p0.pipi.cn/basicdata/25bfd6d753706dddd257e2a36c57a09aa7f83.jpg?imageView2/1/w/128/h/170', '张家辉');
INSERT INTO `actor` VALUES ('8', 'https://p0.pipi.cn/basicdata/25bfd6d7537c69cbae2ff70a36e1ece177fb3.jpg?imageView2/1/w/128/h/170', '阮经天');
INSERT INTO `actor` VALUES ('9', 'https://p0.pipi.cn/basicdata/fb738633d7c92367cb8ea34cf17538253a97d.jpg?imageView2/1/w/128/h/170', '王大陆');
INSERT INTO `actor` VALUES ('10', 'https://p0.pipi.cn/basicdata/25bfd6d7537c69c9fd02ff4d4a2d266081c1d.jpg?imageView2/1/w/128/h/170', '秦沛');
INSERT INTO `actor` VALUES ('11', 'https://p0.pipi.cn/basicdata/25bfd6d7537e7acf3e57e20f7477edda371e5.jpg?imageView2/1/w/128/h/170', '马浴柯');
INSERT INTO `actor` VALUES ('12', 'https://p0.pipi.cn/basicdata/25bfd6d7537c69230f338f58d3a4208b9d56a.jpg?imageView2/1/w/128/h/170', '陈国坤');
INSERT INTO `actor` VALUES ('13', 'https://p0.pipi.cn/basicdata/25bfd6d7537e7acf3e281e319ed2c8de56589.jpg?imageView2/1/w/128/h/170', '连凯');
INSERT INTO `actor` VALUES ('14', 'https://p0.pipi.cn/basicdata/25bfd6d7537c6906d6ecd815c12196a392775.jpg?imageView2/1/w/128/h/170', '吴启华');
INSERT INTO `actor` VALUES ('15', 'https://p0.pipi.cn/basicdata/fb73869206d339be2acbaee410628873949d9.jpg?imageView2/1/w/128/h/170', '李相炫');
INSERT INTO `actor` VALUES ('16', 'https://p0.pipi.cn/friday/a344fe770e3c2fd0d192609b2e46ca4b.jpg?imageView2/1/w/128/h/170', '陈晓依');
INSERT INTO `actor` VALUES ('17', 'https://p0.pipi.cn/basicdata/25bfd6d7537e7af0ee02ffef263accdedf509.jpg?imageView2/1/w/128/h/170', '何昕霖');
INSERT INTO `actor` VALUES ('18', 'https://p0.pipi.cn/basicdata/25bfd6d753706d339e807762d4707d65e855e.jpg?imageView2/1/w/128/h/170', '姜皓文');
INSERT INTO `actor` VALUES ('19', 'https://p0.pipi.cn/basicdata/25bfd6d77a30e1d236ecd86b1c57029689c49.jpg?imageView2/1/w/128/h/170', '杨凝');
INSERT INTO `actor` VALUES ('20', 'https://p0.pipi.cn/basicdata/25bfd6d7537c69b535e19b71bbe6785345917.jpg?imageView2/1/w/128/h/170', '张璐');
INSERT INTO `actor` VALUES ('21', 'https://p0.pipi.cn/basicdata/fb73862f5bfdddaf3339ddb8b75e581466188.jpg?imageView2/1/w/128/h/170', '郑渊洁');
INSERT INTO `actor` VALUES ('22', 'https://p0.pipi.cn/basicdata/25bfd6d77a30e1b5358d33226e33969cb8074.jpg?imageView2/1/w/128/h/170', '王凯');
INSERT INTO `actor` VALUES ('23', 'https://p0.pipi.cn/basicdata/25bfd6d7537e7af0eee19b840ca6a93191e86.png?imageView2/1/w/128/h/170', '露西');
INSERT INTO `actor` VALUES ('24', 'https://p0.pipi.cn/basicdata/25bfd6d77a387ad7c3300b3da63f56dc0c48b.jpg?imageView2/1/w/128/h/170', '周侗');
INSERT INTO `actor` VALUES ('25', 'https://p0.pipi.cn/basicdata/25bfd6d7807338c696b12de58f921755d71eb.png?imageView2/1/w/128/h/170', '孙熹鹤');
INSERT INTO `actor` VALUES ('26', 'https://p0.pipi.cn/basicdata/25bfd6d7807338c696b12de58f921755d71eb.png?imageView2/1/w/128/h/170', '胡正健');
INSERT INTO `actor` VALUES ('27', 'https://p0.pipi.cn/basicdata/25bfd6d77a387ad7c3300be73d53f1115ade0.jpg?imageView2/1/w/128/h/170', '李翰林');
INSERT INTO `actor` VALUES ('28', 'https://p0.pipi.cn/basicdata/25bfd6d7537e7af0ee50c8ea0bcf0ccab3510.jpg?imageView2/1/w/128/h/170', '刘芊含');
INSERT INTO `actor` VALUES ('29', 'https://p0.pipi.cn/basicdata/25bfd6d7537e7af0ee9257750fef4f8c6b81a.png?imageView2/1/w/128/h/170', '刘晓倩');
INSERT INTO `actor` VALUES ('30', 'https://p0.pipi.cn/basicdata/25bfd6d7537e7a5015be1284392521beceeb9.jpg?imageView2/1/w/128/h/170', '吟良犬');
INSERT INTO `actor` VALUES ('31', 'https://p0.pipi.cn/basicdata/25bfd6d7807338c696b12de58f921755d71eb.png?imageView2/1/w/128/h/170', '三羊');

-- ----------------------------
-- Table structure for actor_movie
-- ----------------------------
DROP TABLE IF EXISTS `actor_movie`;
CREATE TABLE `actor_movie` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `act_id` int DEFAULT NULL COMMENT '演员id',
  `movie_id` int DEFAULT NULL COMMENT '电影id',
  `character` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '演员饰演的电影角色',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of actor_movie
-- ----------------------------
INSERT INTO `actor_movie` VALUES ('1', '1', '10', '周潇齐');
INSERT INTO `actor_movie` VALUES ('2', '2', '10', '章若楠');
INSERT INTO `actor_movie` VALUES ('3', '3', '10', '张放');
INSERT INTO `actor_movie` VALUES ('4', '4', '10', '常帆');
INSERT INTO `actor_movie` VALUES ('5', '5', '10', '陈琛');
INSERT INTO `actor_movie` VALUES ('6', '6', '10', '王昱可');
INSERT INTO `actor_movie` VALUES ('8', '7', '1', null);
INSERT INTO `actor_movie` VALUES ('9', '8', '1', null);
INSERT INTO `actor_movie` VALUES ('10', '9', '1', null);
INSERT INTO `actor_movie` VALUES ('11', '10', '1', null);
INSERT INTO `actor_movie` VALUES ('12', '11', '1', null);
INSERT INTO `actor_movie` VALUES ('13', '12', '1', null);
INSERT INTO `actor_movie` VALUES ('14', '13', '1', null);
INSERT INTO `actor_movie` VALUES ('15', '14', '1', null);
INSERT INTO `actor_movie` VALUES ('16', '15', '1', null);
INSERT INTO `actor_movie` VALUES ('17', '16', '1', null);
INSERT INTO `actor_movie` VALUES ('18', '17', '1', null);
INSERT INTO `actor_movie` VALUES ('19', '18', '1', null);
INSERT INTO `actor_movie` VALUES ('20', '19', '45', '舒克');
INSERT INTO `actor_movie` VALUES ('21', '20', '45', '贝塔');
INSERT INTO `actor_movie` VALUES ('22', '21', '45', '播报主持人');
INSERT INTO `actor_movie` VALUES ('23', '22', '45', '五角飞碟');
INSERT INTO `actor_movie` VALUES ('24', '23', '45', '利');
INSERT INTO `actor_movie` VALUES ('25', '24', '45', '小臭球');
INSERT INTO `actor_movie` VALUES ('26', '25', '45', '老臭球');
INSERT INTO `actor_movie` VALUES ('27', '26', '45', '糕鱼氏');
INSERT INTO `actor_movie` VALUES ('28', '27', '45', '黑风');
INSERT INTO `actor_movie` VALUES ('29', '28', '45', 'HR');
INSERT INTO `actor_movie` VALUES ('30', '29', '45', '舒克妈妈');
INSERT INTO `actor_movie` VALUES ('31', '30', '45', '咪丽');
INSERT INTO `actor_movie` VALUES ('32', '31', '45', '双胞胎');

-- ----------------------------
-- Table structure for charact
-- ----------------------------
DROP TABLE IF EXISTS `charact`;
CREATE TABLE `charact` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'role的Id 相当于老师的role表',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'role的名字',
  `sn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '别称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of charact
-- ----------------------------
INSERT INTO `charact` VALUES ('1', '超级管理员', '1001');
INSERT INTO `charact` VALUES ('2', '电影院管理员', '1002');
INSERT INTO `charact` VALUES ('14', '超级管理员', '1003');
INSERT INTO `charact` VALUES ('17', '人事', '1004');
INSERT INTO `charact` VALUES ('18', '研发部Leader', '1005');
INSERT INTO `charact` VALUES ('19', '产品部Leader', '1006');
INSERT INTO `charact` VALUES ('20', '影院部Leader', '1007');

-- ----------------------------
-- Table structure for cinema
-- ----------------------------
DROP TABLE IF EXISTS `cinema`;
CREATE TABLE `cinema` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '和cinemaUser的account进行联查',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '影院名称',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '电话',
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '省份',
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '城市',
  `country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '县',
  `specified_address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '详细地址',
  `tag` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '标签',
  `price` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '最低价格',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '' COMMENT '类型',
  `brand` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '品牌',
  `service` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '影院简介',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '头像图片',
  `pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '影院图片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10425 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of cinema
-- ----------------------------
INSERT INTO `cinema` VALUES ('1', '', '万达影城（新乡万达IMAX店）', '0373-5800011', '河南省', '新乡市', '万达', '牧野区荣校路街道宏力大道与学院路交叉口万达广场四楼', '改签|折扣卡|IMAX厅', '25', 'IMAX厅|4D厅', '万达影城', '改签：未取票用户放映前60分钟可改签|儿童优惠：1.3米以下儿童可免费无座观影（ VIP厅除外），一名成人限带一名儿童（仅限1.3米以下）|可停车：依据广场停车收费标准收费', 'https://pic.ntimg.cn/file/20200821/26753533_194402832850_2.jpg', null);
INSERT INTO `cinema` VALUES ('2', '', '万达影城（辉县）', '0373-6209111', '河南省', '新乡市', '辉县市', '辉县市九山路与水竹大道交汇处向东200米路北万成财富广场(居然之家)4楼', null, '12', '杜比全景声厅|DTS:X 临境音厅', '万达影城', '退：未取票用户放映前60分钟可退票|改签：未取票用户放映前60分钟可改签|3D眼镜免押金：免押金|儿童优惠：1.3米以下儿童免费，一名成人可携带一名儿童|WiFi：大堂休息区wifi覆盖|可停车：广场可免费停车', 'https://pic.ntimg.cn/file/20200821/26753533_194402832850_2.jpg', null);
INSERT INTO `cinema` VALUES ('10208', '', '长垣奥斯卡大视界国际影城', '0373-8827888', '河南省', '新乡市', '长垣县', '新乡市长垣县食博园南区26号', '改签|折扣卡|IMAX厅|4D厅', '25', 'IMAX厅|4D厅', '万达影城', '        奥斯卡大视界国际影城位于长垣县餐饮、文化、娱乐、休闲中心食博园内。影城面积两千平方米，拥有四个全数字化影厅，满足所有影片放映需求，500余个座位，以人体曲线设计的动感座椅，轻易的消除观众疲劳。        影城卖品部采用全套进口的美国GOLO METAL专业爆谷机、美国原装进口的专用玉米粒，配合了多种口味的健康糖料，为您爆出诱人的POPCORBN的美味；再现五星级影院的食品文化，让您充分享受奥斯卡大视界国际影城的卖品特色！      奥斯卡大视界国际影城倾全力为大众打造的星级国际影城，让您在这里体验真正专业的奥斯卡大片，尽享国际化视听盛宴。影城全体员工竭诚为您的观影做到尽善尽美的星级服务质量，衷心恭候您的大驾光临！', 'http://api.jisuapi.com/movie/upload/theater/1/6008.jpg', null);
INSERT INTO `cinema` VALUES ('10209', '', '奥斯卡幕唯国际影城', '', '河南省', '新乡市', '封丘县', '河南省封丘县北干道中段佳联购物广场5楼', '改签|折扣卡|IMAX厅', '25', 'IMAX厅|4D厅', '奥斯卡影城', '', 'http://api.jisuapi.com/movie/upload/theater/1/6009.jpg', null);
INSERT INTO `cinema` VALUES ('10210', '', '大地影院（新乡凤泉新玛特店）', '', '河南省', '新乡市', '凤泉区', '新乡市凤泉区区府路109号大商新玛特生活广场3楼', '改签|折扣卡|IMAX厅', '25', 'IMAX厅|4D厅', '奥斯卡影城', '        大地影院--凤泉新玛特影院设有4个影厅，共个329座位，影厅均采用杜比电影立体声技 术、德国ISC00PTIC放映镜头、专业扬声器JBL，专业电影放映机BARCO-2000，以及高超过6米，宽超过10米的超大银幕。影院全面实现数字化放映，能同步放映首轮大片，并实行合理大众化的票价，让普通的消费者毫无负担地走入电影院，率先体验先进技术带来的多元魅力！         2006年，藉由“让电影回到中国观众身边”的梦想，大地传播开始在中国建设连锁经营影院--大地影院。 秉承“让人人看得到电影、人人看得好电影”的理念，大地影院以合理化的票价、方便快捷的购票体验、酣畅舒适的观影感受，满足着中国观众对电影、对梦想的追求。 2013年全年，大地影院迎送观众超过5千万人次；2014年初，已有223家大地影院为生活在中国32个省份、超过120个城市里的观众提供电影消费服务，而这些数字仍在增长。 分享快乐，传递梦想，我们身边的大地影院，把电影梦带回我们身边。', 'http://api.jisuapi.com/movie/upload/theater/1/6010.jpg', null);
INSERT INTO `cinema` VALUES ('10211', '', '新乡橙天国际影城', '0373-5817222', '河南省', '新乡市', '高新区', '河南省新乡市高新区道清路55号红太阳百货四楼', '改签|折扣卡|IMAX厅', '25', 'IMAX厅|4D厅', '奥斯卡影城', '新乡橙天国际影城成立于2015年9月，影城座落与红太阳百货四楼，占地面积3000余平米，按照全国一线影城标准建设，拥有7个国际化标准3D放映厅，可容纳1000人左右同时观影，厅内配备了高端的7.1环绕声系统和顶级4K放映设备，观影厅采用高起坡，使银幕画面上下左右无遮拦地将观众包围，将带给观众别样的视听盛宴！', 'http://api.jisuapi.com/movie/upload/theater/1/6011.jpg', null);
INSERT INTO `cinema` VALUES ('10212', '', 'DMG国际影城新乡店', '0373-3551555', '河南省', '新乡市', '红旗区', '新乡市红旗区金穗大道与新二街交叉口宝龙城市广场三层', '改签|折扣卡|IMAX厅', '25', 'IMAX厅|4D厅', '奥斯卡影城', 'DMG传媒集团是整合营销的广告和传播机构。近20年来，我们融合了中西方文化。在上世纪九十年代，当中国的广告行业还处于起步阶段时，我们已进入这片新领域。从传统到数字化，从实践到经验，从品牌内容到体育营销，DMG在北京、上海、洛杉矶以及其他4个地区拥有近650名员工共同服务于客户。我们的影城一共有8个影厅，1200个座位，其中有一个是VIP厅，而1号厅和8号厅，银幕宽度有15米，而且其它的影厅银幕也都不小。座位数多的也是1号厅，有257个舒服的座椅可以供您选择，其它的影厅都在150座以上，VIP影厅则是有16个电动沙发，并且配有VIP休息室，所以我们的影城可以举行不同类型的活动，比如会议、培训、朋友聚会、生日派对甚至婚礼这些都可以来联系我们。', 'http://api.jisuapi.com/movie/upload/theater/1/6012.jpg', null);
INSERT INTO `cinema` VALUES ('10213', '100002', '新乡市奥克斯影院VIP', '0373-5856669', '河南省', '新乡市', '红旗区', '新乡市红旗区平原路与和平大道交叉口银马宝利4层', '改签|折扣卡|IMAX厅', '25', 'IMAX厅|4D厅', '奥斯卡影城', '    新乡奥斯卡银马影城隶属于河南奥斯卡电影院线，是按照五星级标准倾力打造的新乡市时尚3D影城。影城占地面积4000余平米，拥有10个豪华影厅（含8个3D影厅），可同时容纳1100人观影。    作为新乡市时尚3D影城，我们引进数字化放映设备，为影迷们奉上水晶般绚丽画质和身临其境般的3D观影体验。另外，影城还为观众倾心准备了美式风味爆米花，让您在享受美妙电影的同时大快朵颐。    人性化是奥斯卡银马影城一贯秉承的服务理念，10座影厅全天候不间断放映，解决观众排队购票、等候开映时间过长的问题，更有“新片秘荐”，影迷party等特色活动，为影迷们带来更多观影乐趣。     看3D电影，到奥斯卡银马影城！未来，奥斯卡银马影城将不仅是影迷们观影“头等舱”，更将成为大家娱乐放松的潮流新地标。', 'http://api.jisuapi.com/movie/upload/theater/1/6013.jpg', null);
INSERT INTO `cinema` VALUES ('10214', '', '新乡胖东来奥斯卡影城', '0373-2076892', '河南省', '新乡市', '其他', '河南省新乡市平原路139号胖东来百货4层', '改签|折扣卡|IMAX厅', '25', 'IMAX厅|4D厅', '奥斯卡影城', '　　　　　　　胖东来奥斯卡电影院简介胖东来百货奥斯卡电影院，选用数字设备和杜比环绕立体声音响，配以墙到墙超大屏幕，令画面细腻真实、色彩绚丽，音质完美无瑕、超级震撼！豪华舒适的座椅，轻易消除您的疲劳，温馨体贴的亲情服务，让您观影全程畅快淋漓，舒适惬意，是您休闲娱乐的最佳选择！     ', 'http://api.jisuapi.com/movie/upload/theater/1/6014.jpg', null);
INSERT INTO `cinema` VALUES ('10215', '', '大地影院（新乡新玛特店）', '0373-2076227', '河南省', '新乡市', '其他', '河南省新乡市解放路218号新玛特广场5楼', '改签|折扣卡|IMAX厅', '25', 'IMAX厅|4D厅', '国际影城', '大地数字影院—新乡影院位于新玛特广场5楼，拥有6个影厅，737个座位，影厅均采用杜比电影立体声技术、德国ISCOOPTIC镜头、专业扬声器JBL，专业放映机BARCO-90，以及高超过6米，宽超过10米的超大银幕。大地数字影院为了丰富当地居民的文化娱乐活动，致力打造大众消费，高档享受的纯数字影院。2006年，藉由“让电影回到中国观众身边”的梦想，大地传播开始在中国建设连锁经营影院--大地影院。 秉承“让人人看得到电影、人人看得好电影”的理念，大地影院以合理化的票价、方便快捷的购票体验、酣畅舒适的观影感受，满足着中国观众对电影、对梦想的追求。 2013年全年，大地影院迎送观众超过5千万人次；2014年初，已有223家大地影院为生活在中国32个省份、超过120个城市里的观众提供电影消费服务，而这些数字仍在增长。 分享快乐，传递梦想，我们身边的大地影院，把电影梦带回我们身边。', 'http://api.jisuapi.com/movie/upload/theater/1/6015.jpg', null);
INSERT INTO `cinema` VALUES ('10216', '', '辉县市大有国际影城', '', '河南省', '新乡市', '辉县市', '辉县市涌金大道开元百货六楼', '改签|折扣卡|IMAX厅', '25', 'IMAX厅|4D厅', '国际影城', '大有国际影城1号巨幕大厅隆重开幕！等你来体验！地址：开元百货六楼！', 'http://api.jisuapi.com/movie/upload/theater/1/6016.jpg', null);
INSERT INTO `cinema` VALUES ('10217', '', '华隆奥斯卡国际影城', '0373-6811266', '河南省', '新乡市', '辉县市', '辉县市华隆鞋行4楼', '改签|折扣卡|IMAX厅', '25', '', '国际影城', '', 'http://api.jisuapi.com/movie/upload/theater/1/6017.jpg', null);
INSERT INTO `cinema` VALUES ('10218', '', '辉县市华纳国际影城', '0373-6777888', '河南省', '新乡市', '辉县市 ', '辉县市 南关十字 涌金城市广场三楼', '改签|折扣卡|IMAX厅', '25', '', '国际影城', '华纳国际影城是辉县市一家星级影城，可同步放映国际国内2D、3D大片。影院采用世界高端2K放映设备和全球影院广泛采用的金属透声银幕，以及高端的航空座椅和情侣沙发。热情贴心的服务+一流的设备+温馨舒适的环境。华纳国际影城为您奉上的是一场超乎想象的视听盛宴，带给您的不仅是物超所值的惊喜，更让您收获的是一段快乐之旅！', 'http://api.jisuapi.com/movie/upload/theater/1/6018.jpg', null);
INSERT INTO `cinema` VALUES ('10219', '', '新乡scm星洲影城', '0373-5881622', '河南省', '新乡市', '其他', '新乡市东站春天里生活广场2楼', '改签|折扣卡|IMAX厅', '25', '', '国际影城', '', 'http://api.jisuapi.com/movie/upload/theater/1/6019.jpg', null);
INSERT INTO `cinema` VALUES ('10220', '', '获嘉县金泰奥斯卡电影城', '0373-4588585', '河南省', '新乡市', '其他', '河南省获嘉县城区行政路中段景文百货三楼', '改签|折扣卡|IMAX厅', '25', '', '奥斯卡影城', '金泰奥斯卡影城是我县一家现代化多厅豪华影院，投资近300万元，是一家拥有2D3D4D放映能力的全能星级影城，影城与国际接轨，投巨资引进美国科视，日本nec等高端进口设备，以独特的电影文化氛围、精湛的放映技术、精彩的视觉效果、先进的音响系统及超值的售卖票价、美味的影院小食、温馨的客户服务、幽雅的休闲布置缔造出真正专业化的电影文化空间。', 'http://api.jisuapi.com/movie/upload/theater/1/6020.jpg', null);
INSERT INTO `cinema` VALUES ('10221', '', '新星奥斯卡影城', '0373-2045966', '河南省', '新乡市', '卫滨区', '新乡市卫滨区平原路22号', '改签|折扣卡|IMAX厅', '25', '', '奥斯卡影城', '       新星奥斯卡影城由3个豪华放映厅组成，共计241个观众坐席。 美国JBL专业音箱，dolby 7.1声道数码环绕立体声系统，高亮度超大进口银幕，进口放映镜头，dolby先进的电影放映系统——在影城的每一个位置，观众都能充分感受到震撼的视听冲击力。 　　体贴舒适的人体工程座椅，洁净的洗手间，惬意的观众休息区，丰富便捷的小商品部，五星级酒店式的贴心服务，影城从每一个细节入手，为观众营造出一个富有人性化的电影文化氛围。 影城设有停车场可容纳50辆轿车停放，附近餐饮有肯德基、麦当劳、德克士、华莱士、李先生牛肉面、常来顺红焖等。影迷qq群:277234445', 'http://api.jisuapi.com/movie/upload/theater/1/6021.jpg', null);
INSERT INTO `cinema` VALUES ('10222', '', '新乡银河欢乐影城', '', '河南省', '新乡市', '其他', '新乡市卫滨区平原路尚潮去新乡店6F', '改签|折扣卡|IMAX厅', '25', 'IMAX厅|4D厅', '阳光影城', '新乡银河欢乐影城是由南京派唯投资管理有限公司斥资建设，专注打造电影行业新生品牌GALAPLEX的星级标准影城。影城面积3000㎡ ，投资约为一千五百万元。目前共八个超豪华影厅，半圆形分布。其中包括VIP影厅及儿童影厅。可容纳观众1200名左右。新乡银河欢乐影城是标准高，银幕大，面积大，环境好，观影条件好的多厅影院。', 'http://api.jisuapi.com/movie/upload/theater/1/6022.jpg', null);
INSERT INTO `cinema` VALUES ('10223', '', '新乡阳光影城', '0373-5859088', '河南省', '新乡市', '其他', '新乡市卫滨区东方步行街13号楼B座', '', '25', 'IMAX厅|4D厅', '阳光影城', '        新乡阳光影城由4个豪华放映厅组成，共计610个观众坐席。 美国JBL高保真音箱，SRD、DTS数码环绕立体声系统，高亮度超大进口银幕，德国产放映镜头，内地先进的电影放映系统——在影城的每一个位置，观众都能充分感受到震撼的视听冲击力。 　　体贴舒适的人体工程座椅，柔软高雅的地毯，洁净的洗手间，豪华惬意的观众休息区，丰富便捷的小商品部，五星级酒店式的贴心服务，影城从每一个细节入手，为观众营造出一个富有人性化的电影文化氛围。 ', 'http://api.jisuapi.com/movie/upload/theater/1/6023.jpg', null);
INSERT INTO `cinema` VALUES ('10224', '', '中影好莱坞国际影城', '0373-5898665-5898666', '河南省', '新乡市', '其他', '新乡市胜利路与健康路交汇新都汇购物中心5楼', '', '25', '', '阳光影城', '新乡中影好莱坞国际影城，坐落于新乡市新都汇购物中心五楼，影城占地面积2400余平米，全面按照五星级标准兴建，拥有6个国际化标准放映厅，1074座席。影城采用国际顶尖电影放映设备、超清晰画质金属银幕、航空级动感座椅以及由进口原料精心制作的香脆爆米花，将为您带来难以忘怀的电影盛宴。2016年6月18日，中影好莱坞国际影城率先完成了6个CINEAPPO激光厅的改造，填补了新乡电影市场无特效影厅的空白！其激光光源放映解决方案亮度高达10,000-30,000流明，播放3D电影的亮度可达普通3D电影的3倍，有效解决了3D画面偏暗，长时间观看眼部不适等困扰。CINEAPPO激光厅不仅仅是帮助我们提升了放映质量，同时也作为一种新的观影风暴而受到越来越多影迷的追捧，成为新的观影时尚。', 'http://api.jisuapi.com/movie/upload/theater/1/6024.jpg', null);
INSERT INTO `cinema` VALUES ('10225', '', '新乡恒大嘉凯影城雅苑', '0373-3088588', '河南省', '新乡市', '其他', '河南省新乡市卫滨区胜利路与南环交叉口恒大商业广场3楼', '', '25', 'IMAX厅|4D厅', '阳光影城', '拥有6个豪华电影厅及1个VIP厅，可容纳1200个人同时观影，并在休息区及VIP区域安放了座椅沙发。影城大堂顶部，邀请手绘大师精心绘制别具特色的银河系全景图。很多人首次来到恒大影城都会拿手机拍摄下来，赞不绝口！新乡恒大影城的每个影厅都设有宽大舒适的航空座椅，1.2米的排距让观者倍感舒适。其中最大的二号厅设有335个座椅，银幕宽达17米，更能使观众感受到不同寻常的视觉冲击和美轮美奂的光影享受。此外，影城还拥有独立的地下停车场，总计280个停车位。为您停车提供便捷的服务。', 'http://api.jisuapi.com/movie/upload/theater/1/6025.jpg', null);
INSERT INTO `cinema` VALUES ('10226', '', '京华奥斯卡国际影城', '0373-5736000', '河南省', '新乡市', '其他', '新乡市新乡县小吉镇冀源路', '改签|折扣卡', '25', '', '阳光影城', '      京华奥斯卡影城隶属于河南奥斯卡电影院线，是按照星级标准打造的新乡县时尚3D影城。影城占地面积3000余平米，拥有3个3D影厅。      作为新乡县时尚3D影城，我们引进数字化放映设备，为影迷们奉上水晶般绚丽画质和身临其境般的3D观影体验。另外，影城还为观众准备了美式风味爆米花，让您在享受电影的同时大快朵颐。      专业、人性化是京华奥斯卡影城一贯秉承的服务理念，有“新片秘荐”、“观影省钱攻略”等特色活动，为影迷们带来乐观影乐趣。      看3D电影，京华奥斯卡影城！未来，京华奥斯卡影城将是影迷们观影“头等舱”，将成为大家娱乐放松的潮流新地', 'http://api.jisuapi.com/movie/upload/theater/1/6026.jpg', null);
INSERT INTO `cinema` VALUES ('10227', '', '新乡市星洲影城', '0373-2869990', '河南省', '新乡市', '其他', '河南市新乡市新中大道801号易购商业广场4楼', '', '25', '', '阳光影城', ' 新乡市星洲MoviePlus影城坐落于大学城新中大道801号易购商业广场四楼，影城建筑面积三千平方米，并设有6个豪华数字电影厅。高水准的科技与人性化的服务融入了每一个细节，给您带来不仅仅是影片自身内容的观感，同时还是影院环境、电影文化等的超级享受。', 'http://api.jisuapi.com/movie/upload/theater/1/6027.jpg', null);
INSERT INTO `cinema` VALUES ('10228', '', '奥斯卡延津影城', '0373-7917777', '河南省', '新乡市', '其他', '延津县胜利路与健康路交叉口大润发商场4F', '改签|折扣卡', '25', '', '阳光影城', '奥斯卡延津影城是由奥斯卡院线与中凯文化传媒有限公司倾力打造的五星级标准化豪华Industrial wind影城，影城位置延津县胜利路与健康路交叉口楠江大润发4F，地处金融商业新区核心地带，总建筑面积1100多平方米，共有5个3D数字全景声放映厅——同时可容纳450余人观影，选用目前世界上亮度最高的科视激光放映机，国际最先进的杜比全景声音响设备，实现全数字化放映。', 'http://api.jisuapi.com/movie/upload/theater/1/6028.jpg', null);
INSERT INTO `cinema` VALUES ('10229', '', '悦时空影城（大商道店）', '0373-5930555', '河南省', '新乡市', '其他', '河南新乡原阳县新城区富康路与民主路交汇处购物中心3楼', '改签|折扣卡', '25', '', '阳光影城', '中广国际影城（原阳店） 一线大片，同步热映。影城按照国际标准打造的全4K多厅式影院，影城沿袭了具有中国特色的电影文化元素，以顶尖的影片放映技术高端的环绕系统及优质的客户服务，为观众提供良好的休闲娱乐环境。电影和音乐的完美结合，让您的心和电影一起跳舞！', 'http://api.jisuapi.com/movie/upload/theater/1/6029.jpg', null);
INSERT INTO `cinema` VALUES ('10230', '', '横店电影城（原阳店）', '0373-5911199', '河南省', '新乡市', '其他', '原阳县黄河大道北侧原新路西侧恒辉曼哈顿4楼横店电影城', '改签|折扣卡', '25', 'IMAX厅|4D厅', '阳光影城', '原阳横店电影城总经营面积2450平方米，严格按照星级影城标准设计和配置。影城秉承星级影城的时尚装修豪华，高雅大方，拥有5个视听效果一流、温馨舒适的观映厅，共设664个豪华舒适座椅。', 'http://api.jisuapi.com/movie/upload/theater/1/6030.jpg', null);
INSERT INTO `cinema` VALUES ('10231', '', '辉县市奥斯卡激光巨幕影城', '0373-6666234', '河南省', '新乡市', '其他', '辉县市涌金大道万和购物广场五楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', '阳光影城', '0', 'http://api.jisuapi.com/movie/upload/theater/1/7800.jpg', null);
INSERT INTO `cinema` VALUES ('10232', '', '辉县市奥斯卡激光影城', '0000-0000000', '河南省', '新乡市', '其他', '河南省新乡市辉县市涌金大道', '改签|折扣卡', '25', 'IMAX厅|4D厅', '奥斯卡影城', '', 'None', null);
INSERT INTO `cinema` VALUES ('10233', '', '卫辉市大卫奥斯卡影城', '0373-4491999', '河南省', '新乡市', '其他', '健康路比干大道交叉口豫商时代广场4楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', '奥斯卡影城', '', 'None', null);
INSERT INTO `cinema` VALUES ('10234', '', '长垣县宏力A生活旗舰店影城', '', '河南省', '新乡市', '其他', '长垣县长城大道与向阳路交叉口宏力A生活旗舰店三楼（老宏力广场）', '改签|折扣卡', '25', '', '奥斯卡影城', '', 'None', null);
INSERT INTO `cinema` VALUES ('10235', '', '新乡大商影城', '0000-0000000', '河南省', '新乡市', '其他', '平原路139号大商百货四楼东厅', '改签|折扣卡', '25', '', '奥斯卡影城', '', 'None', null);
INSERT INTO `cinema` VALUES ('10236', '', '新乡耀莱成龙影城（金穗大道店）', '0373-3088997', '河南省', '新乡市', '其他', '河南省新乡市红旗区新中大道与金穗大道西北角嘉亿东方明珠一层', '改签|折扣卡', '25', '', '奥斯卡影城', '耀莱成龙国际影城，是由成龙大哥与耀莱集团合作打造的五星级明星主题影城，影城拥有8个厅，采用全新3D NEC激光放映设备、超清晰画质金属银幕；总座位750个，座位全部采用最新型豪华航空座椅，恒温的中央空调系统，进口的放映和音响系统，超大无缝银幕给观众提供最舒适的国际一流的观看环境。    耀莱成龙国际影城拥有专业管理团队、终极式服务，将用全新的经营理念、现代化全方位的管理模式，使耀莱成龙国际影城成为全国影院中的核心，为广大新乡人民提供时尚而又高雅的休闲娱乐。', 'http://api.jisuapi.com/movie/upload/theater/1/9048.jpg', null);
INSERT INTO `cinema` VALUES ('10237', '', '新乡万达影城', '0373-5800011', '河南省', '新乡市', '其他', '河南省新乡市牧野区宏力大道与学院街交叉口万达广场四楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', '其他', ' ', 'http://api.jisuapi.com/movie/upload/theater/1/9049.jpg', null);
INSERT INTO `cinema` VALUES ('10238', '', '新乡市红壹影城', '0373-5803338', '河南省', '新乡市', '其他', '河南省新乡市卫滨区胜利路与人民路交叉口淘宝城三楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', '其他', ' ', 'None', null);
INSERT INTO `cinema` VALUES ('10239', '', '新乡奥斯卡新悦影城', '0373-5819188', '河南省', '新乡市', '其他', '河南省新乡市劳动南路', '', '25', 'IMAX厅|4D厅', '其他', ' ', 'http://api.jisuapi.com/movie/upload/theater/1/9051.jpg', null);
INSERT INTO `cinema` VALUES ('10240', '', '新乡恒大嘉凯影城金碧天下店', '0373-7535677', '河南省', '新乡市', '其他', '新乡市平原示范区太行大道与丽江路交叉口东南角恒大商业及剧场3层及3夹层', '', '25', 'IMAX厅|4D厅', '其他', 'None', 'None', null);
INSERT INTO `cinema` VALUES ('10241', '', '鹤壁万达影城', '', '河南省', '新乡市', '其他', '河南', '', '25', 'IMAX厅|4D厅', '其他', 'None', 'None', null);
INSERT INTO `cinema` VALUES ('10242', '', '长垣县奥斯卡新村影院', '0373-8888918', '河南省', '新乡市', '其他', '长垣县蒲西区卫华大道西段路北宏力新村一期会所二楼', '', '25', '', '其他', 'None', 'None', null);
INSERT INTO `cinema` VALUES ('10243', '', '风行国际影城', '0373-8287880', '河南省', '新乡市', '其他', '封丘县文化路与振兴路交叉口好又多商场4楼', '', '25', 'IMAX厅|4D厅', '其他', 'None', 'None', null);
INSERT INTO `cinema` VALUES ('10244', '', '弘哲奥斯卡影城', '0373-2068555', '河南省', '新乡市', '其他', '向阳路与东明大道交叉口向南100米诚诚常青藤广场30号楼', '', '25', '', '其他', 'None', 'None', null);
INSERT INTO `cinema` VALUES ('10245', '', '奥斯卡星辉国际影城（原阳店）', '0373-72222227', '河南省', '新乡市', '其他', '原阳县西街转盘香港城A座4楼', '', '25', '', '其他', 'None', 'http://api.jisuapi.com/movie/upload/theater/2/11544.jpg', null);
INSERT INTO `cinema` VALUES ('10246', '', '鑫泰奥斯卡激光影城', '0373-4799666', '河南省', '新乡市', '其他', ' 河南省新乡市获嘉县城关镇行政大街55号（八房井购物中心4楼）', '', '25', '', '其他', 'None', 'None', null);
INSERT INTO `cinema` VALUES ('10247', '', '中影星美国际影城', '', '河南省', '新乡市', '其他', '新乡平原路38号华彬大厦6层', '', '25', '', '其他', 'None', 'None', null);
INSERT INTO `cinema` VALUES ('10248', '', '辉县好莱坞影城', '', '河南省', '新乡市', '其他', '新乡 百泉路与政和大道交汇处西南角尚泉广场4层', '', '25', '', '其他', 'None', 'None', null);
INSERT INTO `cinema` VALUES ('10249', '', '辉县市莱卡时光影城', '', '河南省', '新乡市', '其他', '新乡 文昌大道中段金城百货3楼', '', '25', '', '其他', 'None', 'None', null);
INSERT INTO `cinema` VALUES ('10250', '', '长垣县艾影汇国际影城', '0373-7033619', '河南省', '新乡市', '其他', '匡城路南段银河国际二楼影院', '', '25', '', '其他', 'None', 'None', null);
INSERT INTO `cinema` VALUES ('10251', '', '巩义市奥斯卡影城', '0371-85021180', '河南省', '新乡市', '巩义市', '巩义市建设路与新兴路交叉口西南角德丰香榭里', '', '25', 'IMAX厅|4D厅', '其他', '', 'http://api.jisuapi.com/movie/upload/theater/1/1704.jpg', null);
INSERT INTO `cinema` VALUES ('10252', '', '横店电影城（巩义店）', '0371-60261117', '河南省', '新乡市', '巩义市', '巩义市东区盛威凯旋门三楼', '', '25', 'IMAX厅|4D厅', '其他', '', 'http://api.jisuapi.com/movie/upload/theater/1/1705.jpg', null);
INSERT INTO `cinema` VALUES ('10253', '', '星美国际影城（新密店）', '0371-69999976', '河南省', '新乡市', '新密市', '郑州市新密市西大街丹尼斯西观光电梯6楼', '', '25', 'IMAX厅|4D厅', '其他', '星美国际影城（新密店）隶属中影星美院线，位于新密市西大街丹尼斯西观光电梯6楼，一线大片，同步热映。影城按照国际标准打造的多厅式影院，影城沿袭了具有中国特色的电影文化元素，以顶尖的影片放映技术高端的环绕系统及优质的客户服务，为观众提供良好的休闲娱乐环境。电影和音乐的完美结合，让您的心和电影一起跳舞！', 'http://api.jisuapi.com/movie/upload/theater/1/6007.jpg', null);
INSERT INTO `cinema` VALUES ('10254', '', '登封奥斯卡小龙国际影城', '0371-62802222', '河南省', '新乡市', '登封市', '登封市大禹路西段156号（释小龙武院东院）', '', '25', 'IMAX厅|4D厅', '其他', '放映设备,使所有到影城的观众都能够得到超乎想象的视听冲击。票务热线：0371-62802222    业务咨询：0371-62802233', 'http://api.jisuapi.com/movie/upload/theater/1/6643.jpg', null);
INSERT INTO `cinema` VALUES ('10255', '', '河南奥斯卡汽车影院', '0371-65952827', '河南省', '新乡市', null, '郑州市三全路与金杯路向北省体育中心东门院内', '', '25', 'IMAX厅|4D厅', '其他', '', 'http://api.jisuapi.com/movie/upload/theater/1/6644.jpg', null);
INSERT INTO `cinema` VALUES ('10256', '', '横店电影城（郑州裕华店）', '0371-55025559', '河南省', '新乡市', '惠济区', '郑州市惠济区三全路与长兴路交叉口裕华广场5楼', '退|改签|折扣卡', '25', 'IMAX厅|4D厅', '其他', '郑州裕华横店电影城是横店院线的直营旗舰店，拥有6个3D厅，1个豪华双机巨幕厅，共1029个座位。影城装修豪华、典雅，休息区整洁明亮，双机巨幕影厅拥有超大巨幕及超震撼视听设备。观影顾客可凭影城当日有效电影票，免费停车3个小时。', 'http://api.jisuapi.com/movie/upload/theater/1/6645.jpg', null);
INSERT INTO `cinema` VALUES ('10257', '', '郑州惠济万达广场店', '0371-55399600', '河南省', '新乡市', '惠济区', '郑州市惠济区开元路68号万达广场4楼万达影城 ', '', '25', 'IMAX厅|4D厅', null, '郑州惠济万达影城作为万达院线在郑州市打造的首个旗舰级影城，影城建筑面积约8800平方米，共设置12个豪华放映厅，可容纳1800多名观众同时观影。影城所有的3D影厅均采用Real-D放映设备。影城配备四个特色影厅：1个舒适VIP厅、1个动感MX4D影厅、一个可爱儿童厅、1个IMAX影厅，其中IMAX影厅设有365个豪华头等舱芝华士座椅。', 'http://api.jisuapi.com/movie/upload/theater/1/6646.jpg', null);
INSERT INTO `cinema` VALUES ('10258', '', '星美国际影城（郑州上街店）', '', '河南省', '新乡市', '上街区', '河南省郑州市上街区济源路与金华路交叉口欧凯龙凯特城市广场4楼', '', '25', '', '星美国际影城', '', 'None', null);
INSERT INTO `cinema` VALUES ('10259', '', '星美国际影城上街店', '', '河南省', '新乡市', '上街区', '郑州市上街区济源路与金华路交叉口凯特城市广场4号楼4楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', '星美国际影城', '本影城于2015年2月份璀璨开幕，建筑面积4000多平方米，影城拥有五个放映厅，可同时容纳700多位观众同时观影。电影厅空间挑高达9米，高起坡、低视角、弧形宽银幕，完全依据人体工学设计的座椅。影厅全部采用放映设备索尼SRX-R510、SRX-R515,银幕及3D技术，加以专业的服务，给观众带来震撼般的体验。            人性化的整体贴心设计不仅体现在硬件设施方面，并考虑到软件服务的各个环节：环境优雅的休息区，让您在观影之余可以静静回味影片的每一个精彩瞬间；每个观众厅的超宽安全通道进出口设计，方便您的快捷出入。', 'http://api.jisuapi.com/movie/upload/theater/1/6648.jpg', null);
INSERT INTO `cinema` VALUES ('10260', '', '奥斯卡中铝国际影城', '0371-85765762', '河南省', '新乡市', '上街区', '河南省郑州市上街区汝南路（艺术宫院内）2-3楼', '改签|折扣卡', '25', '', '奥斯卡影城', '上街区奥斯卡中铝国际影城座落于汝南路与中心路交叉口向南100米路西艺术宫院内，依托原有艺术宫旧址，作为强有力的商家入驻，必将积极推进和大力加强本区电影文化产业的发展与建设。 影城共设两层（2-3楼）近3000平米。共设6个厅，可同时容纳九百多名观众同时观影，其中二楼中国巨幕厅设311座，给您“大不一样”的观影感受。 全进口巴可放映设备，高清视听，星级享受，“电”亮生活，给你好看。 硬件建设与装修方面投入大笔资金，影厅、售票大厅、卖品配套的多元一体化电影经营模式，也同时满足了不同受众的众多样化、多方面、多层次的精神文化需求。致力于做大做强本地区内电影文化产业。', 'http://api.jisuapi.com/movie/upload/theater/1/6649.jpg', null);
INSERT INTO `cinema` VALUES ('10261', '', '新密三合奥斯卡影城', '0371-69860918', '河南省', '新乡市', '新密市', '河南省新密市东大街与农业路交叉口西南角四楼', '改签|折扣卡', '25', '', '奥斯卡影城', '三合奥斯卡影城位于新密市中心城区,商业圈附近有新密市委市政府等行政单位十几家', 'http://api.jisuapi.com/movie/upload/theater/1/6650.jpg', null);
INSERT INTO `cinema` VALUES ('10262', '', '横店电影城（新密店）', '0371-55381888', '河南省', '新乡市', '新密市', '新密市嵩山大道6号万宝园白金广场6楼', '改签|折扣卡', '25', '', '奥斯卡影城', '新密横店电影城是横店影视股份有限公司投资兴建的数字影院，新密横店电影城位于新密市嵩山大道6号万宝园白金广场6楼，设有5个数字化影厅，共817座位，最大厅可容纳200多人。影城将以前卫的设计、标准化的服务为影迷提供优良的观影环境。  想像一下，在环绕立体声配合着惊心动魄的电影场面，加上舒适的航空座椅与巨大银幕，再配上我们精心现炸的进口爆米花......啧啧！（此处省略一万字）亲们！来吧！', 'http://api.jisuapi.com/movie/upload/theater/1/6651.jpg', null);
INSERT INTO `cinema` VALUES ('10263', '', '新郑奥斯卡影城', '0371-69953000', '河南省', '新乡市', '新郑市', '河南省新郑市溱水路中段，溱水路与金城路交叉口南250米路西', '改签|折扣卡', '25', '60帧厅', '奥斯卡影城', '新郑奥斯卡国际影城，位于新郑市溱水路与金城路交叉口向南250m路西。共有7个影厅，配备先进的放映设备，拥有舒适的观影环境。1个VIP厅，配有豪华电动座椅。一个196座巨幕厅。内设有儿童游乐场，电玩城，法国西餐厅，福宝贝手工坊，免费WIFI，100个停车位。', 'http://api.jisuapi.com/movie/upload/theater/1/6652.jpg', null);
INSERT INTO `cinema` VALUES ('10264', '', '奥斯卡金时代影城（龙湖店）', '0371-62617888', '河南省', '新乡市', null, '新郑龙湖镇泰山路与滨湖路交汇处（金时代商业广场2楼）', '改签|折扣卡', '25', '60帧厅', '奥斯卡影城', '', 'http://api.jisuapi.com/movie/upload/theater/1/6653.jpg', null);
INSERT INTO `cinema` VALUES ('10265', '', '新郑市星烨激光影城', '0371-55929688', '河南省', '新乡市', '新郑市', '新郑市人民路与玉前路交叉口庆都生活广场4楼', '改签|折扣卡', '25', '60帧厅', '奥斯卡影城', '五星级的配置,合理化的价格,首轮大片抢先观看。星烨潇湘国际影城,咱新郑人民自己的电影院！', 'http://api.jisuapi.com/movie/upload/theater/1/6654.jpg', null);
INSERT INTO `cinema` VALUES ('10266', '', '中牟奥斯卡国际影城', '0371-56700085', '河南省', '新乡市', null, '中牟县青年路与建设路交叉口向南100米世纪城时代广场4楼', '改签|折扣卡', '25', '60帧厅', '奥斯卡影城', '中牟奥斯卡国际影城是中牟县数字化影城，影城坐落于青年路与建设路交叉口路南世纪城时代广场四楼，影城装修大气、交通便利，同时具备标准的商业设施，如：世纪城时代广场（26000平方米）、丹尼斯购物中心、品牌餐饮、儿童乐园、动漫城、品牌男女装等，都在同一个综合体内，同时具备－1F、－2F两层停车场（近3000个停车位）。影城拥有7个数字放映厅，其中一个影厅采用激光放映，为观众打造无与伦比的观影体验；JBL音响、QSC功放和杜比配置解码器感受完美无瑕的逼真还音效果。水晶般清晰的画面、逼真的六声道加超低音音箱，令观影无比震撼。 影城地址：郑州市中牟县青年路与建设路交叉口路南世纪城时代广场四楼   票房热线：0371－56700085  ', 'http://api.jisuapi.com/movie/upload/theater/1/6655.jpg', null);
INSERT INTO `cinema` VALUES ('10267', '', '郑州欢乐国际影城', '0371-86506600', '河南省', '新乡市', '航空港区', '郑州市航空港区沃金商业广场三层', '改签|折扣卡', '25', '60帧厅', '国际影城', '郑州沃金欢乐世界是欢乐传媒集团与郑州沃金商业广场联手打造的欢乐传媒旗下欢乐世界。项目坐落于郑州市航空港区沃金商业广场，比邻富士康产业园区，总占地面积近8000平方米，投资2400万元，欢乐国际影城旗下包括：欢乐国际影院、欢乐电玩、欢乐之声KTV， 耗资千万！全新感受引爆星城，视觉盛宴即将开始！开业期间超值促销，火爆观影！', 'http://api.jisuapi.com/movie/upload/theater/1/6656.jpg', null);
INSERT INTO `cinema` VALUES ('10268', '', ' 郑州金汇VIP影城', '0371-66680855', '河南省', '新乡市', '二七区', '郑州市二七区政通路北大学路东红星古玩城四楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', '国际影城', '', 'http://api.jisuapi.com/movie/upload/theater/1/6657.jpg', null);
INSERT INTO `cinema` VALUES ('10269', '', '郑州比高电影城（橄榄城）', '0371-53307858', '河南省', '新乡市', '二七区', '郑州市二七区南三环与连云路交叉路口橄榄城都市广场5层', '改签|折扣卡', '25', 'IMAX厅|4D厅', '国际影城', '影城以优秀的试听设备、亲民的票价、贴心的服务提供给观众试听盛宴，至始至终以比高影城的服务宗旨与服务理念，真正做到“让电影回归大众”画面亮度比普通亮度提升一倍，让顾客享受影像魅力，感受真实的3D画面', 'http://api.jisuapi.com/movie/upload/theater/1/6658.jpg', null);
INSERT INTO `cinema` VALUES ('10270', '', '郑州二七万达广场店', '0371-86671189', '河南省', '新乡市', '二七区', '郑州市大学路航海路二七万达广场娱乐楼4楼万达影城', '改签|折扣卡', '25', 'IMAX厅|4D厅', '国际影城', '                 郑州万达影城二七店位于郑州市大学路与航海路交汇处2号门郑州二七万达广场娱乐楼4-5层，建筑面积达9000多平方米，共设立11个厅。           郑州万达影城二七店IMAX影厅共467个座位。同样拥有强大的音响系统传递激光校准的数字音效， IMAX专有高增益银幕，无论是IMAX或者是IMAX 3D技术，都会将观众的视野极大化，令您置身于电影之中。此外，影城还拥有2个索尼4K影厅，及RealD-3D共8个立体影厅。通过优良的设备呈现出更加亮丽的色彩画面，配备RealD-3D轻便舒适的3D眼镜，减轻老式眼镜的沉重及疲劳感，让整个观影过程更加的享受。自此带领观众进入新的观影时代。               在影厅建设上，全部影厅高度达9米以上，配备柔软舒适的阶梯式座椅。座椅排距达1.2米，使每一位观众观影无遮挡，倍感舒适。“wall to wall”整面墙式超视野巨幅银幕，让您有身临其境的观影效果。&', 'http://api.jisuapi.com/movie/upload/theater/1/6659.jpg', null);
INSERT INTO `cinema` VALUES ('10271', '', '郑州奥斯卡升龙国际影城', '0371-68865885', '河南省', '新乡市', '二七区', '郑州二七区大学路政通路交叉口向西100米路北升龙国际心A区2层2033', '改签|折扣卡', '25', 'IMAX厅|4D厅', '国际影城', '影务热线：68865885               团体票热线：68861338奥斯卡升龙国际影城，是投资三千余万元打造的郑州电影院。影院设计经典时尚前卫，结合升龙商业广场融合餐饮、娱乐、购物、文化等综合服务于一体的先天优势，必将成为郑州市民观影地。2016年全新升级空气清新影院，引入美国SMART-A离子净化技术，用心打造畅快呼吸的健康影城。1：全进口放映设备，视听震撼2：4500平米，8个影厅，6个独立3D立体影厅，1330座。3：1.2米排距，观影开阔，无遮挡，视野效果很好。1：浓郁的电影文化氛围，打造电影发烧友的理想殿堂。2：设计风格和主题清新时尚，体现时尚文化品位。3：U型影厅通道，流光溢彩，构成一条星光璀璨的“星光大道”。1：韩国引进的薯食主义，精选上等原料，带来炸鸡和薯条的超赞口感。2：纯天然鲜榨果汁VQ；以及风味独特、曼妙滋味的DF冰淇淋。3：原汁原味的美国原味爆米花，口感独特，畅享甜蜜。1：独特的大型衍生品区，全正品主题电影大片后产品供影迷收藏。2：独立便捷的网上购票及手机购票系统，尊享足不出户预定大片影票。', 'http://api.jisuapi.com/movie/upload/theater/1/6660.jpg', null);
INSERT INTO `cinema` VALUES ('10272', '', '奥斯卡德化电影城', '0371-63376898', '河南省', '新乡市', null, '德化街100号（友谊广场后面、天成珠宝西邻三楼）', '改签|折扣卡', '25', 'IMAX厅|4D厅', '国际影城', '奥斯卡德化电影城坐落于百年德化·风情购物公园 。时尚的弧型边廊上内嵌着四个放映厅，各个灯箱内张贴着精美的电影海报。环顾四周，影厅整洁高雅。热情的引导小姐引领着您踏着柔软的簇绒地毯来到影厅。人性化的坐椅设计，切合观众的生理曲线弧度，使您以舒适的角度来欣赏影片。数码立体声音响，立体声解码器采用的是DolbyCP650，声音设计逼近完美。银幕上的图象具有真切感和清晰度。', 'http://api.jisuapi.com/movie/upload/theater/1/6661.jpg', null);
INSERT INTO `cinema` VALUES ('10273', '', '横店电影城（郑州德化店）', '0371-55273338', '河南省', '新乡市', '二七区', '郑州市二七区德化街36号无限城6层', '改签|折扣卡', '25', 'IMAX厅|4D厅', '其他', '郑州德化横店电影城位于郑州市二七区德化街无限城6楼，整个影城大气，时尚典雅，融娱乐休闲、购物于一体，是河南郑州一家时尚的电影城，影城总建筑面积3800平方米，可容纳1400多人同时观影。影城拥有8个数字3D放映厅，影厅全部采用大整壁式金属弧形银幕，观影亮度更高，画面更清晰，视野更广阔，其中有2个特大双机厅，1个4K双机巨幕厅，3D影片立体感更强，效果更逼真，我们真诚的邀请您，期待您的光临！', 'http://api.jisuapi.com/movie/upload/theater/1/6662.jpg', null);
INSERT INTO `cinema` VALUES ('10274', '', '新百姓奥斯卡国际影城', '0371-53308880', '河南省', '新乡市', null, '嵩山南路与南三环百姓广场A馆3F', '', '25', '', '奥斯卡影城', '奥斯卡百姓国际影城是河南郑州新百姓电影院有限公司于2015年斥资。2500万打造的新一代纯索尼4K数字化3D国际影城，影城占地近5000平米，8个数字3D放映厅，共有1600余个座位，特色私人影院，为您提供全方位的观影体验。影城秉承着“平民票价、百姓影城“的文化理念，采用先进的索尼（4K）纯进口数字放映设备，先进的立体环绕声音响，设备同时采用TMS统一数字化管理系统。帅康至尊超舒适、超宽座距、错位的航空座椅，以及韩范优雅怀旧的时尚环境，贴心，全面的超值服务，让您体验非一般的震撼效果，为广大影迷带来视觉和听觉的全新享受。', 'http://api.jisuapi.com/movie/upload/theater/1/6663.jpg', null);
INSERT INTO `cinema` VALUES ('10275', '', '奥斯卡亚星小龙激光影城', '0371-55318177', '河南省', '新乡市', '二七区', '郑州市二七区嵩山路长江路交叉口亚星时代广场4楼', '', '25', '', '其他', '', 'http://api.jisuapi.com/movie/upload/theater/1/6664.jpg', null);
INSERT INTO `cinema` VALUES ('10276', '', '星美国际影商城郑州新象城店', '0371-61660109', '河南省', '新乡市', '二七区', '郑州市二七区大学中路9号负1层102   ', '', '25', '', '其他', '星美国际影商城（郑州大学路店）建筑面积约3000平方米，位于大学路政通路交叉口东南角新象城负一层，共有6个高规格放映厅，总计600余座位：全部支持2D、3D影片播放。银幕全部为金属银幕，放映机为美国进口的科视放映设备，能为观众呈现清晰、鲜艳、不失真的画面。立体环绕音系统，符合人体工学的沙发式座椅，配合科学合理的排距和仰角，保证舒适度，同时还采用45度角梯形设计，进一步提高了视觉效果，使影院更具人性化。多重硬件优势旨在为每位顾客呈现不折不扣的观影魅力，此外，影城休息区还设有饮水吧、美食区、按摩休息区。影院整体设计风格时尚、大气、宽敞、休闲，是顾客节假日及缓解生活压力的好去处。影院所在商城配备有运动城、健身房、台球厅、海底捞、绿茵阁和沃尔玛超市等休闲娱乐场所，更有500多个停车位，为顾客提供优美、舒适的观影环境。', 'http://api.jisuapi.com/movie/upload/theater/1/6665.jpg', null);
INSERT INTO `cinema` VALUES ('10277', '', '郑州中影二七国际影城', '0371-55985123', '河南省', '新乡市', '二七区', '河南省郑州市二七区民主路3号北京华联商厦五楼', '', '25', '', '国际影城', '影城位于郑州市二七区民主路3号北京华联商厦五楼，影城共有8个影城，共计1028个座位，支持2D、3D影片的播放。一号厅屏幕高10米宽20米，同时影城为了给您提供观影视觉效果，更是取消了前三排的座位安置，只为让您享受视听觉上的饕鬄盛宴。影院秉承“人无我有，人有我优持续改进，不断创新，客户第一”的经营理念，不断丰富观影内容，只为更好的为您服务。', 'http://api.jisuapi.com/movie/upload/theater/1/6666.jpg', null);
INSERT INTO `cinema` VALUES ('10278', '', '郑州大地恒世国际影城', '0371-56256788', '河南省', '新乡市', '二七区', '郑州市二七区嵩山南路与淮河路交汇处向南100米路西恒世国际影城', '', '25', '', '其他', '郑州大地恒世国际影城是河南省恒世文化传播有限公司于2015年兴资2000万元投资建设的纯数字化3D国际影城。本影城秉承着“新视界 欣期待心享受”的文化理念，采用NEC数字放映设备，美国杜比5.0环绕立体声音响设备，法国睿沃丰数字3D放映设备，采用TMS统一数字化管理系统，同时配置超舒适、超宽坐距的航空座椅及优雅、怀旧的时尚环境，贴心、超值服务，让您体验震撼效果。', 'http://api.jisuapi.com/movie/upload/theater/1/6667.jpg', null);
INSERT INTO `cinema` VALUES ('10279', '', '郑州星空影城', '0371-55321588-2002', '河南省', '新乡市', '二七区', '郑州市二七区陇海中路100号鑫都汇3层10A ', '', '25', '', '其他', '影城位于鑫苑鑫都汇3层。占地面积2000平米，共设近600个座位，7个影厅、情侣厅、无障碍通道等，并配备方便快捷的自助电脑售票系统、网络购票系统，同时卖品部将为观众提供各种饮料、美食及电影后产品。影城采用了TMS数字电影放映中央管理系统（TMS-Theatre Management System），集放映设备、影片存储、密钥等自动化管理于一体，实现了影城自动化设施的集中管理。全4K数字放映设备、超视野壁式清晰全金属银幕、梦幻雨花手扶太空座椅、GDC被动式3D系统（亮度更高、眼镜更轻、视效更真），这些高端科技的专业组合，毫无疑问将带给观众真正的前所未有的顶级的视听震撼！', 'http://api.jisuapi.com/movie/upload/theater/1/6668.jpg', null);
INSERT INTO `cinema` VALUES ('10280', '', 'CGV影城（郑州高新IMAX店）', '0371-55195959', '河南省', '新乡市', '高新区', '郑州市高新区科学大道金梭路交叉口正弘生活广场4F', '改签|折扣卡', '25', '', '其他', '', 'http://api.jisuapi.com/movie/upload/theater/1/6669.jpg', null);
INSERT INTO `cinema` VALUES ('10281', '', '郑州奥斯卡高新影城', '0371-56544444', '河南省', '新乡市', '高新区', '郑州市高新区科学大道53号10号楼4层', '改签|折扣卡', '25', '', '其他', '郑州奥斯卡高新影城位于郑州市高新区科学大道53号中原广告产业园4楼。影城于2014年3月营业，丰富了郑州人民的娱乐文化生活。一、环境介绍       郑州奥斯卡高新影城位于科学大道53号中原广告产业园，园内入驻有丹尼斯、电玩城、美食城等商家，充分满足观众一站式的消费需求。影城交通便利，驾车沿北环高速路或京沙高速路可快速到达影城，园内有宽敞的地下停车场供自驾车观众停放，观众还可乘坐B67、B35、45路可直达影城。二、硬件       郑州奥斯卡高新影城共有6个放映厅、1200多个观影座位。其中1个最有特色的巨幕影厅和4个3D影厅。巨幕影厅（2号厅）银幕宽度超过24M，配备了巨幕影音系统和4K放映设备，观众厅采用高起坡，使银幕画面上下左右无遮拦地将观众包围，同时采用两台巴可4万流明的数字放映机以及符合标准的数字放映服务器，将带给观众别样的视听盛宴！三、特色美食       影城内整齐洁净的卖品区为观众提供香气扑鼻、松脆可口的爆米花、美味小零食、汉堡薯条等……美食电影两不误！四、贴心服务    &nbs', 'http://api.jisuapi.com/movie/upload/theater/1/6670.jpg', null);
INSERT INTO `cinema` VALUES ('10282', '', '中影星影迷影城（光彩店）', '0371-65315001', '河南省', '新乡市', '管城区', '郑州市管城区人民路太康路交叉口光彩3楼（歌迷KTV楼下）', '改签|折扣卡', '25', '', '其他', '', 'http://api.jisuapi.com/movie/upload/theater/1/6671.jpg', null);
INSERT INTO `cinema` VALUES ('10283', '', '奥斯卡大上海国际影城', '0371-62001188', '河南省', '新乡市', null, '郑州东太康路24号大上海城六层（百货大楼东侧）', '改签|折扣卡', '25', '', '其他', '               奥斯卡国际影城是河南省电影公司在郑州市东太康路24号大上海城六层打造的影城。                影院共设有9个放映厅， 1713个座位，其中包括巨幕厅及64路环音的杜比全景声影厅。并拥有地下免费停车场，让您的爱车拥有自己的专席。               影院整体设计以经典的好莱坞式电影文化主题贯穿每个角落，1.1米的超宽排距、舒适的可调节座椅，给你提供极贵宾服务，开创娱乐视听新境界！               影院采用英国哈克里斯金属银幕和德国施奈德高保真镜头、美国JBL专业级音响，并采用杜比数码高保真还音系统和放映设备，使你享受到视觉感受，带给你视觉精彩刺激！               影城卖品部采用全套进口的美国GOLD  METAL专业爆谷机、美国', 'http://api.jisuapi.com/movie/upload/theater/1/6672.jpg', null);
INSERT INTO `cinema` VALUES ('10284', '', '横店电影城（郑州百盛店）', '0371-69373135', '河南省', '新乡市', null, '东太康路与人民路交叉口百盛购物广场6楼', '改签|折扣卡', '25', '', '其他', '', 'http://api.jisuapi.com/movie/upload/theater/1/6673.jpg', null);
INSERT INTO `cinema` VALUES ('10285', '', '郑州奥斯卡新天地影城', '0371-66388128', '河南省', '新乡市', null, '郑州市城东路与郑汴路交叉口向东300米康桥商务广场三层', '改签|折扣卡', '25', '', '奥斯卡影城', '奥斯卡新天地影城位于城东路郑卞路交叉口东300米，郑汴路39号康桥商务广场3楼，有多条公交线路，楼下负一层负二层有充足的停车位，为观影的观众提供了便利。影城内部环境优雅，一进门影城独具特色的环形回廊自然采光，宽敞明亮，郁郁葱葱的绿色植物令空气格外清新，高脚转椅为您驻足休憩提供了方便。舒适宽畅的休闲娱乐大厅采用了时尚的色调和风格进行装饰，迎送观众的自动扶梯、各个门厅廊边的一幅幅电影灯箱海报无处不渗透着浓郁的电影文化气息，让电影里流动的艺术凝固在瞬间，令人驻足流连。奥斯卡新天地影城按照星级影院标准打造，面积3000多平米，8个专业的放映厅错落有致的分布在影城的四周，环境温馨，格调高雅。和奥斯卡各大影院同步上映各类中外大片、经典影片。丰富的节目令人目不暇接，为您提供了更多的选择。卖品部内整套进口GOLD METAL专业爆谷机，采用美国原装进口的WEAVER玉米粒，配合了多种口味的健康糖料，爆出那诱人POPCORN的美味，无不令人怦然心动。影城使用美国先进的BARCO电脑程序控制放映机，单片机操作观影更流畅，数码放映设备让画面音效极具冲击力；音响系统全部采用国际上最先进的数码立体声设备及杜比公', 'http://api.jisuapi.com/movie/upload/theater/1/6674.jpg', null);
INSERT INTO `cinema` VALUES ('10297', '', '郑州万达影城保利光魔店', '0371-69528105', '河南省', '新乡市', null, '郑州市金水区农业路东47号卜蜂莲花3楼郑州保利影城', '改签|折扣卡', '25', '', null, '保利电影院线郑州店是由北京数字光魔管理公司投资2000万人民币兴建；影城总面积4000平方米，共建有六个不同大小的放映厅，小放映厅为设有67个座椅的VIP贵宾放映厅，大放映厅为设有410个豪华座椅的多功能放映厅。影城六个放映厅共设有1118个座椅，使您在观赏电影的时候，感到安逸、舒适。', 'http://api.jisuapi.com/movie/upload/theater/1/6686.jpg', null);
INSERT INTO `cinema` VALUES ('10298', '', '郑州奥斯卡新建文影城', '0371-63858209', '河南省', '新乡市', null, '黄河路经八路交叉口建文新世界商厦四层', '改签|折扣卡', '25', '', null, '奥斯卡新建文电影城，拥有10个2K以上全数字化影厅。影城共设1049个座位，影厅拥有座位数274个。震撼视听 · 全新感受· 非一般的观影体验  奥斯卡新建文影城舒适舒心！影厅选用数字放映机，施耐得镜头与英国哈克尼斯大银幕。成像细腻真实、层次丰富、色彩丽而不艳；JBL音箱、QSC功放和杜比最高配置解码器（CP650）感受逼真还音效果；水晶般清晰的画面、逼真的六声道加超低音音响。观影效果让专家振奋、令观众震撼！  影城地址：黄河路经八路交叉口建文·汇美尚城四层票房电话：0371-63858209团购电话：0371-63858026企业客服QQ：400-8999-527客服QQ：65550000会员俱乐部群：216930650 、216939440、 216940164、 216940609乘车路线：T5路、966路、10路、21路、22路、23路、27路、28路、63路、64路、71路、83路、86路、93路、95路、105路、211路、506路等。', 'http://api.jisuapi.com/movie/upload/theater/1/6687.jpg', null);
INSERT INTO `cinema` VALUES ('10307', '', '华士达影城（郑东店）', '0371-60227909', '河南省', '新乡市', null, '郑州市郑东新区普惠路77号绿地之窗尚峰座正大乐城3楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, '华士达影城总经营面积7915平方米，拥有8个观影厅，共设1352个舒适座椅。', 'http://api.jisuapi.com/movie/upload/theater/1/6696.jpg', null);
INSERT INTO `cinema` VALUES ('10308', '', '奥斯卡国际激光影城（中原新城店）', '0371-56809102', '河南省', '新乡市', null, '郑州市中原区陇海西路338号大商新玛特中原新城店四层', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, '影院目前拥有8个数字放映厅，内有1348个座位、其中情侣座136个，VIP豪华座46个。VIP豪华放映厅采用先进的激光放映设备，金属银幕，向观众呈现出超清晰真实的画质，并且拥有高坡度、大落差、宽排距的座椅设计，将给观众带来绝佳的观影效果。', 'http://api.jisuapi.com/movie/upload/theater/1/6697.jpg', null);
INSERT INTO `cinema` VALUES ('10309', '', '郑州耀莱成龙影城（锦艺二期店）', '0371-55357061', '河南省', '新乡市', null, '郑州市中原区秦岭路棉纺路交叉口锦艺城C区3F', '改签|折扣卡', '25', '', null, '郑州耀莱成龙影城位于郑州市棉纺路秦岭路交汇处锦艺城购物中心C区3楼，是耀莱集团成龙品牌，总面积2500平方米，拥有3个放映厅，一个巨幕影厅，一个VIP影厅，一个3D影厅，总座位数达614个温馨提示：每位成人仅限带一名1.3米以下儿童免费观看2D3D影片(4DX、巨幕厅、VIP厅除外)，无需购票，无座位，影城不提供儿童3D眼镜，为了保证您孩子的视力健康，可自行购买或自带与影城配套的3D眼镜，感谢您的配合！', 'http://api.jisuapi.com/movie/upload/theater/1/6698.jpg', null);
INSERT INTO `cinema` VALUES ('10310', '', '郑州优加国际影城（原奥斯卡乐丁影城）', '0371-86520682', '河南省', '新乡市', null, '郑州市中原区秦岭路与农业西路交叉口北300米路东乐丁广场2层', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, '奥斯卡乐丁国际影城位于河南省郑州市冉屯路与电厂路交汇处北200米路东乐丁广场2楼，影城面积3300平方米，设计打造7个现代时尚的标准影厅，900个座位。本影城秉承着“视听无限，乐享电影”的文化理念，提供轻松自然、休闲娱乐的时尚环境和贴心、全面的优质服务，全力为顾客打造完美、极致的观影体验。奥斯卡乐丁国际影城是一家纯数字化3D激光五星级影城，影院采用先进的NEC数字激光放映设备以及国际著名数字电影专业音响品牌环音设备，同时影院还配置了超宽坐距的舒适航空座椅，让顾客在观看好莱坞大片时可以感受到震撼的视听效果以及3D体验。', 'http://api.jisuapi.com/movie/upload/theater/1/6699.jpg', null);
INSERT INTO `cinema` VALUES ('10311', '', '郑州中原万达广场店', '0371-86671000', '河南省', '新乡市', null, '郑州市中原中路171号万达广场5层', '改签|折扣卡', '25', '', null, '郑州万达影城位于郑州市中原西路171号郑州中原万达广场娱乐楼5楼，建筑面积达7000多平方米，共设立11个厅：IMAX影厅，以及5个金属银幕3D厅，1个VIP影厅，共计座位约1600个。', 'http://api.jisuapi.com/movie/upload/theater/1/6700.jpg', null);
INSERT INTO `cinema` VALUES ('10321', '', '登封横店电影城', '0000-0000000', '河南省', '新乡市', null, '郑州高新技术产业开发区华强城市广场地上第四层F4-24号', '改签|折扣卡', '25', '', null, '', 'None', null);
INSERT INTO `cinema` VALUES ('10322', '', '登封中影数字国际影城', '0371-62811577', '河南省', '新乡市', null, '河南省登封市南环一路与嵩阳路交叉口', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, '', 'None', null);
INSERT INTO `cinema` VALUES ('10323', '', '巩义奥斯卡恒世', '0371-60268026', '河南省', '新乡市', null, '桐本路陇海路口朗曼假日广场3楼', '', '25', 'IMAX厅|4D厅', null, '', 'http://api.jisuapi.com/movie/upload/theater/1/9279.jpg', null);
INSERT INTO `cinema` VALUES ('10324', '', '郑州耀莱成龙影城（新田城店）', '0371-58507200', '河南省', '新乡市', null, '郑州市荥阳市贾峪镇新田城滨湖西路湖中岛B栋', '', '25', 'IMAX厅|4D厅', null, '荥阳为郑州内的一个城市，是河南省距省会最近的县级市。山川秀丽，古迹众多，人文积淀丰富！耀莱成龙影城坐落于荥阳开发地段新田城，整个区域已慢慢形成商业区！耀莱成龙国际影城由耀莱集团独家拥有成龙品牌。影城现已覆盖北京，广州，沈阳覆盖了北京、广州、江苏、沈阳、郑州、西安、烟台、云南、武汉等各大主要城市，2017年正式登陆于荥阳新田城，郑州耀莱成龙影城新田城店即将为您带来前所未有的观影体验和五星级的服务享受！！看电影就到成龙影城！！给您不一样的视觉体验······荥阳首家耀莱成龙国际影城，位于荥阳开发区新田城地段。JACKIE CHAN耀莱成龙国际影城是由国际巨星成龙先生和耀莱国际文化产业有限公司共同投资打造的超豪华现代影城。其旗下五棵松影城更是自2010年开业以来，连续五年蝉联全国影院票房总冠军。目前耀莱成龙国际已覆盖北京、上海、广州、南京、成都、沈阳、郑州、西安、烟台、昆明、武汉等城市。此次亮相的耀莱成龙国际影城新田城店，坐落于荥阳市贾峪镇新田城滨湖西路湖中B栋，地处新田城商业中心，拥有高清放映系统、专业环绕音响设备以及进口金属透声幕，高坡度无遮挡、低视点及宽排距座椅设计，将为观众提供最震撼', 'http://api.jisuapi.com/movie/upload/theater/1/9280.jpg', null);
INSERT INTO `cinema` VALUES ('10325', '', '荥阳鑫苑星空影城', '0371-66125268', '河南省', '新乡市', null, '荥阳市郑上路与广武路交叉口西南角鑫苑鑫都汇3层', '', '25', 'IMAX厅|4D厅', null, '影城位于荥阳市郑上路与广武路交叉口西南角鑫苑鑫都汇，是荥阳集NEC激光放映技术、GDC临境音及巨幕厅为一体的大型综合性影城。影城占地总面积近2911多平米，共设6个激光放映技术影厅，总计901个座位。其中包含1个可同时容纳333人的临境音巨幕厅，包含1个智能按摩椅厅，和全自动真皮座椅的VIP厅。影城采用了TMS数字电影放映中央管理系统（TMS），集放映设备、影片存储、密钥等自动化管理于一体，实现了影城自动化设施的集中管理。每个影厅均采用了超视野壁式清晰全金属银幕、豪华座椅、GDC临境音系统及NEC激光3D系统（亮度更高、眼镜更轻、视效更真）。影城内更配备豪华VIP休息区、儿童休闲区、专为残疾人设置的座位、无障碍通道、方便快捷的自助电脑售票系统、网络购票系统等。这些高端科技的专业组合，毫无疑问将树立荥阳市视听观影理念的新标准。', 'http://api.jisuapi.com/movie/upload/theater/1/9281.jpg', null);
INSERT INTO `cinema` VALUES ('10326', '', '美港激光MAX影城', '0371-58553888', '河南省', '新乡市', null, ' 河南省郑州市新郑龙湖镇文昌路与泰山路交叉口商业广场2期3楼', '', '25', 'IMAX厅|4D厅', null, '美港激光MAX影城龙湖店，加盟江苏幸福蓝海院线，位于美港俱乐部三层，五星级标准影厅，拥有9个标准化影厅1647座，影厅配备激光放映机，并引进杜比全景声，临境音、JBL音响，环形银幕等先进设施，意在为所有影迷提供龙湖地区高端舒适化观影，体验更有主题影厅，情侣座。', 'http://api.jisuapi.com/movie/upload/theater/1/9282.jpg', null);
INSERT INTO `cinema` VALUES ('10327', '', 'DY影城（华南城DMAX巨幕店）', '0371-55286968', '河南省', '新乡市', null, '新郑市龙湖镇华盛奥特莱斯三层', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, '', 'http://api.jisuapi.com/movie/upload/theater/1/9283.jpg', null);
INSERT INTO `cinema` VALUES ('10328', '', '郑州奥斯卡汇金国际影城', '0371-55959011', '河南省', '新乡市', null, '郑州市惠济区南阳路宋寨南街交叉口西北角升龙汇金广场A幢3层 ', '改签|折扣卡', '25', '', null, '郑州奥斯卡汇金影城面积约4000平方米，全境配备了“PM2.5空气净化系统”，通过“介质过滤”与“静电除尘”不断净化室内空气，为观众提供优质安心的的观影环境。影城内设7个多功能影厅：包含2个4K厅、4个激光厅、1个巨幕厅和1个全景声厅，共1194个座位。除此之外，在大厅和影厅通道内均设置了“手机充电站”，随时随地为“电力不足”的观众提供免费充电服务；不仅如此，如遇下雨天，影城还将为有需要的顾客提供雨伞。奥斯卡汇金国际影城不仅让观众全方位的体验更大、更亮、更清的观影视觉效果，也力求让每一位到访的顾客感受到舒适与温馨。', 'http://api.jisuapi.com/movie/upload/theater/1/9284.jpg', null);
INSERT INTO `cinema` VALUES ('10329', '', 'EVG奥斯卡激光影城（康桥华城店）', '0371-58636008', '河南省', '新乡市', null, '郑州市二七区大学路长城康桥华城9号楼2层', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, '', 'http://api.jisuapi.com/movie/upload/theater/1/9285.jpg', null);
INSERT INTO `cinema` VALUES ('10330', '', '溯时光奥斯卡影城', '0371-55010007', '河南省', '新乡市', null, '郑州市航海中路兴华南街交叉口西南角新龙汇时代广场2楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, '溯时光奥斯卡影城位于郑州市二七区，影城内集奥斯卡影院、豪华VIP点播厅、主题私人定制影院为一体，是您享受顶级观影效果的最佳场所！影城共占地2600平方，其中5个同步观影厅、2个豪华VIP点播厅、16个主题定制私人影厅，可同时容纳千人观影；主题定制影厅拥有海量片源、随心点播、独立空间；在这里，让观影无所不能！并按照全国一线影院标准建设，具有五大功能-全国一线影院，全球同步上映，国际先进设备，最佳观影效果，品牌管理模式。影院内部采用经典流行的色彩元素进行装修，只要踏进影院，时尚、浪漫的气息即刻映入你的眼帘。大厅内的售票处采用先进的LED电子显示屏，每日排片、票价有序排列，精准的电脑售票，观众可方便的选择自己想要的座位。大厅另侧设有小卖部，品类繁多的食品将满足您不同的需求。溯时光奥斯卡影城装修豪华大气且不失浓厚的电影气息，让观众置身于独特的电影文化氛围之中，获得身心的愉悦。影院同步上映中外各大影片，风格各异、内容丰富的电影将为您奉献一顿视觉盛宴。而影院一直秉承着“观众至上”的服务理念，严格内部管理，提升服务品质，以市场需求为导向，提供优质影片，满足当地人民的文化需求，引领带动文化消费的新潮流', 'http://api.jisuapi.com/movie/upload/theater/1/9286.jpg', null);
INSERT INTO `cinema` VALUES ('10331', '', '建业艾米1895电影街影城', '0371-56161895', '河南省', '新乡市', null, '河南省郑州市二七区华润万象城L536', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, '', 'http://api.jisuapi.com/movie/upload/theater/1/9287.jpg', null);
INSERT INTO `cinema` VALUES ('10332', '', '银兴国际影城二七广场店', '0371-53681001', '河南省', '新乡市', null, '河南省郑州市二七区二七路230号郑州华联商厦4楼5楼银兴国际影城', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, '', 'http://api.jisuapi.com/movie/upload/theater/1/9288.jpg', null);
INSERT INTO `cinema` VALUES ('10333', '', '郑州市茵卡汽车影院', '0371-55613320', '河南省', '新乡市', null, '郑州市二七区侯寨乡麦秸垛沟社区68号', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, '', 'None', null);
INSERT INTO `cinema` VALUES ('10338', '', '中影星影迷激光影城（陇海路大润发店）', '0371-63336001', '河南省', '新乡市', null, '郑州市管城区紫荆山路陇海路大润发4楼', '改签|折扣卡', '25', '', null, '', 'http://api.jisuapi.com/movie/upload/theater/1/9294.jpg', null);
INSERT INTO `cinema` VALUES ('10339', '', '悦嘉辰影院璞丽中心店影院', '0371-86669111', '河南省', '新乡市', null, '郑州市金水区黄河路1号院璞丽广场C座3层', '改签|折扣卡', '25', '', null, '', 'http://api.jisuapi.com/movie/upload/theater/1/9295.jpg', null);
INSERT INTO `cinema` VALUES ('10340', '', '中影星美国际影城中环店', '0371-63372666', '河南省', '新乡市', null, '河南省郑州市金水区花园路正道中环百货五楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, '', 'http://api.jisuapi.com/movie/upload/theater/1/9296.jpg', null);
INSERT INTO `cinema` VALUES ('10341', '', '奥斯卡一天地激光影城', '0371-86158788', '河南省', '新乡市', null, '郑州市丹尼斯一天地三楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, '', 'None', null);
INSERT INTO `cinema` VALUES ('10342', '', '河南嗨森未来主题影城', '0371-56975899', '河南省', '新乡市', null, '郑州市金水区文化路与北环交叉口家乐福二楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, '河南嗨森未来主题影城，是中原地区独创主题影城，占地面积1500平，拥有星际迷航、漫威英雄、轻奢主义、卡通游戏、城堡公主、机械朋克等七个不同风格的主题影厅。影厅的双调节式真皮沙发座椅宽大舒适，手机随时充电，全场的座椅实现180°躺着看电影，使观众在全场的任何座位都能欣赏到无遮挡、完美的视听效果。而且，每一个座椅都可以轻松调节头部和腿部角度，观影舒适感，座椅设有饮料和食品的托盘，为每位顾客提供舒适观影感受。', 'http://api.jisuapi.com/movie/upload/theater/1/9298.jpg', null);
INSERT INTO `cinema` VALUES ('10343', '', '郑州比高电影城（东站店）', '0371-58508660', '河南省', '新乡市', null, '郑州市郑东新区商鼎路与心怡路交叉口永和宇宙星广场第4、5层', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, '', 'http://api.jisuapi.com/movie/upload/theater/1/9299.jpg', null);
INSERT INTO `cinema` VALUES ('10344', '', '奥斯卡大郑东国际影城', '0371-55392196', '河南省', '新乡市', null, '郑州市郑东新区白沙镇敬业路白沙商贸城6号楼5层', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, '奥斯卡大郑东国际影城是河南奥斯卡院线旗下的位于郑东新区白沙镇新建的一座影城，其面积3600平方，总投资二千万元，由国内专业设计，装饰高端大气。有便利的交通，同许多其他商业配套设施，如大型商场、购物中心、餐饮娱乐场所等共同在一个商业中心。同时备有大型停车场，影城直达电梯也会将观众直接送到观影区域。奥斯卡大郑东国际影城沿袭了奥斯卡独特的电影文化，以影片放映技术、环绕声系统及客户服务，为观众提供了休闲娱乐环境。影城地址：郑州市郑东新区白沙镇恒通路白沙商贸城6号楼5层咨询电话：0371-55392196     ', 'http://api.jisuapi.com/movie/upload/theater/1/9300.jpg', null);
INSERT INTO `cinema` VALUES ('10345', '', '郑州市悦影绘影城', '0000-0000000', '河南省', '新乡市', null, '郑州高新技术产业开发区华强城市广场地上第四层F4-24号', '改签|折扣卡', '25', '', null, '', 'None', null);
INSERT INTO `cinema` VALUES ('10346', '', '郑州奥斯卡熙地港影城', '0371-61310766', '河南省', '新乡市', null, '河南省郑州市农业东路众意西路交叉口熙地港商场五楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, '', 'http://api.jisuapi.com/movie/upload/theater/1/9302.jpg', null);
INSERT INTO `cinema` VALUES ('10347', '', '奥斯卡悦汇影城', '0371-86598699', '河南省', '新乡市', null, '郑州航空港经济综合实验区郑港四街与郑港七路锦荣悦汇城5号楼四层', '改签|折扣卡', '25', '', null, '', 'http://api.jisuapi.com/movie/upload/theater/1/9303.jpg', null);
INSERT INTO `cinema` VALUES ('10348', '', '薛店奥斯卡影城', '0371-55929633', '河南省', '新乡市', null, '河南省郑州市新郑市薛店镇友谊路哈芙生活购物广场负一楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, '', 'None', null);
INSERT INTO `cinema` VALUES ('10349', '', '上街奥斯卡国际影城', '', '河南省', '新乡市', null, '河南省郑州市上街区济源路与汝南路交叉口东北角二楼', '改签|折扣卡', '25', '', null, null, 'http://api.jisuapi.com/movie/upload/theater/1/9381.jpg', null);
INSERT INTO `cinema` VALUES ('10350', '', '巩义中影星美国际影城', '0371-64066555', '河南省', '新乡市', null, '巩义市新兴路淘宝城2号楼4楼', '改签|折扣卡', '25', '', null, null, 'None', null);
INSERT INTO `cinema` VALUES ('10351', '', '奥斯卡新兄弟影城(荥阳)', '0371-64985333', '河南省', '新乡市', null, '荥阳市索河路和万山路交叉口东北角（近万山路）', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, 'None', null);
INSERT INTO `cinema` VALUES ('10352', '', '奥斯卡星影院（上悦城店）', '0371-89955517', '河南省', '新乡市', null, '郑州市金水区郑汴路英协路上悦城B馆3楼', '改签|折扣卡', '25', '', null, null, 'None', null);
INSERT INTO `cinema` VALUES ('10353', '', '华谊兄弟郑州影院', '0371-62008000', '河南省', '新乡市', null, '郑州市中州大道航海路万科龙堂广场4楼华谊兄弟影院', '改签|折扣卡', '25', '', null, null, 'None', null);
INSERT INTO `cinema` VALUES ('10354', '', '郑州耀莱成龙影城（麟起城店）', '0371-53312986', '河南省', '新乡市', null, '郑州市惠济区文化北路与英才街交叉口西南角美景万科广场6楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, 'http://api.jisuapi.com/movie/upload/theater/2/10017.jpg', null);
INSERT INTO `cinema` VALUES ('10355', '', '郑州奥斯卡又一城影城', '0371-56184444', '河南省', '新乡市', null, '郑州高新区科学大道89号升龙又一城商场A区3层', '改签|折扣卡', '25', '', null, null, 'http://api.jisuapi.com/movie/upload/theater/2/10018.jpg', null);
INSERT INTO `cinema` VALUES ('10356', '', '郑州中影泰得影院', '0371-55916980', '河南省', '新乡市', null, '郑州市二七区嵩山南路19号新生活广场地上3层3-17号商铺', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, 'http://api.jisuapi.com/movie/upload/theater/2/10019.jpg', null);
INSERT INTO `cinema` VALUES ('10357', '', '中影星美寰耀影城', '0371-88959006', '河南省', '新乡市', null, '郑州市金水区银河路南朝阳路东郑州1908商业区5号楼4层410号 ', '改签|折扣卡', '25', '', null, null, 'http://api.jisuapi.com/movie/upload/theater/2/10020.jpg', null);
INSERT INTO `cinema` VALUES ('10358', '', '泰禾影城', '0371-68131113', '河南省', '新乡市', null, '河南省郑州市上街区济源路89号和昌都汇广场景文百货4楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, 'http://api.jisuapi.com/movie/upload/theater/2/10021.jpg', null);
INSERT INTO `cinema` VALUES ('10359', '', '华曼乐影国际影城', '0371-56688878', '河南省', '新乡市', null, '河南省郑州市二七区百荣路南、大学路西鑫苑都汇广场2层209号 ', '', '25', '', null, null, 'None', null);
INSERT INTO `cinema` VALUES ('10360', '', '中影星美国际影城（郑州睿达店）', '0371-5308930', '河南省', '新乡市', null, '郑州市高新区瑞达路木兰里9号睿达广场3层', '', '25', 'IMAX厅|4D厅', null, null, 'None', null);
INSERT INTO `cinema` VALUES ('10361', '', '奥斯卡金成时代影城', '0371-55180366', '河南省', '新乡市', null, '郑州市金水区中州大道黄河路金成时代广场10号2－3层', '', '25', '', null, null, 'http://api.jisuapi.com/movie/upload/theater/2/10024.jpg', null);
INSERT INTO `cinema` VALUES ('10362', '', '新郑奥斯卡故里影城', '0371-69670999', '河南省', '新乡市', null, '新郑市新建路492号（新郑汽车站）', '', '25', 'IMAX厅|4D厅', null, null, 'http://api.jisuapi.com/movie/upload/theater/2/10025.jpg', null);
INSERT INTO `cinema` VALUES ('10363', '', '郑州万达公园茂店', '0371-89962350', '河南省', '新乡市', null, '郑州市高新区雪松路与翠竹街交叉口朗悦公园茂D栋4楼万达影城', '', '25', '', null, null, 'None', null);
INSERT INTO `cinema` VALUES ('10364', '', '悦时空全景声激光影城（帝湖店）', '0371-86616111', '河南省', '新乡市', null, '郑州航海西路桐柏路交叉口帝湖百货西停车场三楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, 'http://api.jisuapi.com/movie/upload/theater/2/10027.jpg', null);
INSERT INTO `cinema` VALUES ('10365', '', '中牟中影数字影城', '0371-62180070', '河南省', '新乡市', null, '郑州市中牟县学苑路与牟州街交叉口新生活广场4楼', '改签|折扣卡', '25', '', null, null, 'http://api.jisuapi.com/movie/upload/theater/2/10028.jpg', null);
INSERT INTO `cinema` VALUES ('10366', '', '香港嘉纳国际影城（亚星淘气堡店）', '0371-55023077', '河南省', '新乡市', null, '郑州市二七区嵩山南路与南彩路交叉口亚星锦绣山河星淘气包新天地A区3层', '改签|折扣卡', '25', '', null, null, 'http://api.jisuapi.com/movie/upload/theater/2/10029.jpg', null);
INSERT INTO `cinema` VALUES ('10367', '', '银兴国际影城（悠客店）', '0371-55381225', '河南省', '新乡市', null, '河南省郑州市管城区航海东路2号富田太阳城商业用房第四层', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, 'None', null);
INSERT INTO `cinema` VALUES ('10368', '', '新郑中影激光影城', '0371-55921333', '河南省', '新乡市', null, '河南省郑州市新郑市创业路与湖滨路华祥喜度大厦4楼', '改签|折扣卡', '25', '', null, null, 'None', null);
INSERT INTO `cinema` VALUES ('10369', '', '银兴国际影城(南阳路店)', '0371-55000840', '河南省', '新乡市', null, '郑州市金水区南阳路与黄河路交叉口西南角银兴悠客广场2楼', '改签|折扣卡', '25', '', null, null, 'None', null);
INSERT INTO `cinema` VALUES ('10370', '', '橙天巨幕影城郑州高新区店', '0371-55638777', '河南省', '新乡市', null, '河南省郑州市高新区科学大道红叶路高新万科广场4层', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, 'http://api.jisuapi.com/movie/upload/theater/2/10033.jpg', null);
INSERT INTO `cinema` VALUES ('10371', '', '奥斯卡幸福影城', '0371-63300006', '河南省', '新乡市', null, '河南省郑州市金水区青年路145号B区1-4号楼裙楼凤凰幸福城商场内商铺位号3001', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, null, null);
INSERT INTO `cinema` VALUES ('10372', '', '奥斯卡国际影城（星辰万科店）', '', '河南省', '新乡市', null, '郑州 航海西路21号星辰万科生活广场4楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, null, null);
INSERT INTO `cinema` VALUES ('10373', '', '奥斯卡影城（龙子湖博雅广场店）', '', '河南省', '新乡市', null, '郑州 龙子湖博雅广场5号楼三层', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, null, null);
INSERT INTO `cinema` VALUES ('10374', '', '河南奥斯卡硅谷影城', '0371-55621686', '河南省', '新乡市', null, '文化路82号硅谷广场六层', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, null, null);
INSERT INTO `cinema` VALUES ('10375', '', '奥斯卡VIP影城齐礼阎店', '', '河南省', '新乡市', null, '郑州嵩山路与航海路交叉口西北角4层', '改签|折扣卡', '25', '', null, null, null, null);
INSERT INTO `cinema` VALUES ('10376', '', '春天国际影城郑州锦艺城店', '0371-55885055', '河南省', '新乡市', null, '龙湖镇双湖大道郑新路交叉口东北角锦艺城购物中心三层3-30号', '', '25', 'IMAX厅|4D厅', null, null, null, null);
INSERT INTO `cinema` VALUES ('10377', '', '奥斯卡海亮影城', '0371-88971388', '河南省', '新乡市', null, '文化路与魏河北路交叉口向东海亮时代三层', '', '25', 'IMAX厅|4D厅', null, null, null, null);
INSERT INTO `cinema` VALUES ('10378', '', '郑州奥斯卡光合VIP影城', '0371-58618886', '河南省', '新乡市', null, '河南省郑州市中原区工人路与伊河路口光合大厦4层', '', '25', 'IMAX厅|4D厅', null, null, null, null);
INSERT INTO `cinema` VALUES ('10379', '', '万达影城（郑州高新万达广场店）', '0371-55686992', '河南省', '新乡市', null, '郑州高新技术产业开发区科学大道郁香路西北角万达广场3F、4F万达影城', '改签|折扣卡', '25', '', null, null, null, null);
INSERT INTO `cinema` VALUES ('10380', '', '荥阳横店电影城', '0371-60251866', '河南省', '新乡市', null, '河南省郑州市荥阳市汜河路与兴华路交叉口索河新天地5号楼4层', '改签|折扣卡', '25', '', null, null, null, null);
INSERT INTO `cinema` VALUES ('10381', '', '奥斯卡恒世VIP影城龙子湖店', '0371-88917108', '河南省', '新乡市', null, '河南省郑州市龙子湖平安大厦与博学路交汇处局外太阁茂购物中心4层02/03/08/09/10/1', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, null, null);
INSERT INTO `cinema` VALUES ('10382', '', '郑州东方嘉禾影城（花丹店）', '0371-86011188', '河南省', '新乡市', null, '花园路与农业路交叉口丹尼斯14楼', '改签|折扣卡', '25', '', null, null, null, null);
INSERT INTO `cinema` VALUES ('10383', '', '奥斯卡德化星影院', '0371-55055508', '河南省', '新乡市', null, '德化街100号D区3层', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, null, null);
INSERT INTO `cinema` VALUES ('10384', '', '郑州嘉辰影院（LUXE海尚店）', '', '河南省', '新乡市', null, '东风路31号瀚海美尚商业中心5层501号', '改签|折扣卡', '25', '', null, null, null, null);
INSERT INTO `cinema` VALUES ('10385', '', '好莱坞影城（盛华里店）', '0371-55059669', '河南省', '新乡市', null, '管城回族区航海东路1322号万锦城商场', '改签|折扣卡', '25', '', null, null, null, null);
INSERT INTO `cinema` VALUES ('10386', '', '好莱坞影城（天空之城店）', '', '河南省', '新乡市', null, '东风南路榆林北路交叉口绿地天空之城6楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, null, null);
INSERT INTO `cinema` VALUES ('10387', '', '新密好莱坞影城', '', '河南省', '新乡市', null, '西大街办事处香蜜花都金巴斗5楼', '改签|折扣卡', '25', '', null, null, null, null);
INSERT INTO `cinema` VALUES ('10388', '', '奥斯卡影城航海路丹尼斯店', '', '河南省', '新乡市', null, '航海东路第一大街交叉口西北角丹尼斯三层', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, null, null);
INSERT INTO `cinema` VALUES ('10389', '', '巩义中影国际电影城（东区店）', '0371-64288777', '河南省', '新乡市', null, '巩义市东区中原西路文化广场负一楼', '改签|折扣卡', '25', '', null, null, null, null);
INSERT INTO `cinema` VALUES ('10392', '', '麟洲奥斯卡国际影城', '0371-62139800', '河南省', '新乡市', null, '中牟县经开区前程大道99号美景美地麟洲2号楼3层', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, null, null);
INSERT INTO `cinema` VALUES ('10393', '', '巩义市米河逸嘉影城', '0371-64582777', '河南省', '新乡市', null, '巩义市益民路行政路瑞成时代广场（农业银行后面）', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, null, null);
INSERT INTO `cinema` VALUES ('10394', '', '幸福蓝海LUXE影城', '0371-55889300', '河南省', '新乡市', null, '新郑市双湖大道地铁站B2出口华祥国贸5楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, null, null);
INSERT INTO `cinema` VALUES ('10395', '', '华夏影都国际影城', '0371-55158678', '河南省', '新乡市', null, '登封书院河路与东关街交叉口万佳中心城东南广场电梯至四楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, null, null);
INSERT INTO `cinema` VALUES ('10396', '', '河南奥斯卡老龙窝汽车影院', '0371-66121728', '河南省', '新乡市', null, '荥阳市郑州市后殿村北老龙窝森林公园209号', '改签|折扣卡', '25', 'IMAX厅|4D厅', null, null, null, null);
INSERT INTO `cinema` VALUES ('10397', '', '好莱坞影城（新田360新都会店）', '0371-55180766', '河南省', '新乡市', null, '郑东新区金水东路与东风南路 交叉口东北角 新田360广场A馆5层', '', '25', 'IMAX厅|4D厅', null, null, null, null);
INSERT INTO `cinema` VALUES ('10399', '', 'CGV影城(正弘城IMAX店)', '0371-53361866', '河南省', '新乡市', null, '花园路126号正弘国际广场6-7层', '', '25', '', null, null, null, null);
INSERT INTO `cinema` VALUES ('10402', '', '金逸影城（高新大学城IMAX店）', '0371-55188669', '河南省', '新乡市', '新乡高新技术产业开发区', '高新区长椿路216号新悦荟商场4楼（河南工业大学西门对面）', '改签|折扣卡', '25', '60帧厅', null, null, 'http://s6gtr7r2k.hb-bkt.clouddn.com/af87ebe179aa406a9e0d5fb6ff6ba073002.png', null);
INSERT INTO `cinema` VALUES ('10407', '', '横店星光影城', '0371-63250910', '北京市', '市辖区', '东城区', '荥阳市国泰路与惠民路东南角豪布斯卡5楼', '改签|折扣卡', '25', '60帧厅', null, null, 'http://s6gtr7r2k.hb-bkt.clouddn.com/1e9ed56273d244859abbc17d3f0b1be3001.png', null);
INSERT INTO `cinema` VALUES ('10408', '', '万达影城（星海中心店）', '0373-3370888', '河南省', '新乡市', null, '红旗区人民东路705号星海中心4楼', '改签|折扣卡', '25', 'IMAX厅|4D厅', '万达影城', '退：未取票用户放映前60分钟可退票|改签：未取票用户放映前60分钟可改签|3D眼镜免押金：免押金|儿童优惠：1.3米以下儿童观影免票无座|可停车：免费停车', 'https://pic.ntimg.cn/file/20200821/26753533_194402832850_2.jpg', null);
INSERT INTO `cinema` VALUES ('10409', '', '万达影城（辉县居然之家店）', '0373-6209111', '河南省', '新乡市', '辉县市', '辉县市九山路与水竹大道交汇处向东200米路北万成财富广场(居然之家)4楼', '改签|折扣卡', '25', '杜比全景声厅|DTS:X 临境音厅', '万达影城', '退：未取票用户放映前60分钟可退票|改签：未取票用户放映前60分钟可改签|3D眼镜免押金：免押金|儿童优惠：1.3米以下儿童免费，一名成人可携带一名儿童|WiFi：大堂休息区wifi覆盖|可停车：广场可免费停车', 'https://pic.ntimg.cn/file/20200821/26753533_194402832850_2.jpg', null);
INSERT INTO `cinema` VALUES ('10410', '10087', '安阳万达', '123456789', '河南省', '新乡市', '平原湖', '123465', '改签|折扣卡', '25', '', '万达影城', null, 'http://s6gtr7r2k.hb-bkt.clouddn.com/3ee7fb59fc16437bb9cdebe4b87864fa001.png', null);
INSERT INTO `cinema` VALUES ('10420', '', '2131', '0220-1122333', '北京市', '市辖区', '东城区', '12312', '改签|折扣卡', '13.5', null, '1312312', '12312123', 'http://s6gtr7r2k.hb-bkt.clouddn.com/b4bd85517f284dd5925d6830d6d85edb002.png', null);
INSERT INTO `cinema` VALUES ('10421', '', 'test', '0020-1234567', '河南省', '洛阳市', '西工区', 'test', '改签|折扣卡', '13.5', '', 'any', 'test', 'http://s6gtr7r2k.hb-bkt.clouddn.com/401144d534a9408aaef0566803cf9384001.png', null);
INSERT INTO `cinema` VALUES ('10422', '', 'new ', '0012-11111111', '河南省', '新乡市', '红旗区', 'nnn', '改签|折扣卡', '12', '', 'NEW万达影城', 'asdfa', null, null);
INSERT INTO `cinema` VALUES ('10423', '', 'new1', '0111-4567894', '河南省', '新乡市', '红旗区', '河南师范大学', '改签|折扣卡', '35', '', 'NEW万达影城', '好好好', null, null);
INSERT INTO `cinema` VALUES ('10424', '917937', 'htu1', '0003-1234567', '河南省', '新乡市', '新乡县', '1111111', '', '165', '', '河师大', '他吞吞吐吐', null, null);

-- ----------------------------
-- Table structure for cinema_comment
-- ----------------------------
DROP TABLE IF EXISTS `cinema_comment`;
CREATE TABLE `cinema_comment` (
  `id` int NOT NULL COMMENT 'id',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  `cinema_id` int DEFAULT NULL COMMENT '影院id',
  `createtime` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '创建时间',
  `likes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '点赞',
  `comment_id` int DEFAULT NULL COMMENT '父评论id',
  `score` double(255,0) DEFAULT NULL COMMENT '得分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of cinema_comment
-- ----------------------------

-- ----------------------------
-- Table structure for cinema_user
-- ----------------------------
DROP TABLE IF EXISTS `cinema_user`;
CREATE TABLE `cinema_user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account` int DEFAULT NULL COMMENT '影院',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `age` int DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of cinema_user
-- ----------------------------

-- ----------------------------
-- Table structure for hall
-- ----------------------------
DROP TABLE IF EXISTS `hall`;
CREATE TABLE `hall` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cinema_id` int DEFAULT NULL COMMENT '影院Id',
  `hall_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '影院名字',
  `hall_size` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '影厅尺寸',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1784872978 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of hall
-- ----------------------------
INSERT INTO `hall` VALUES ('1', '1', '一号厅', '[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 1, -1]]');
INSERT INTO `hall` VALUES ('2', '1', 'IMAX厅', '[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 1, -1]]');
INSERT INTO `hall` VALUES ('3', '1', '二号厅', '[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 1, -1]]');
INSERT INTO `hall` VALUES ('4', null, '阿斯顿发射点发顺丰的发', '[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 1, -1]]');
INSERT INTO `hall` VALUES ('7', null, '阿斯顿发射点发顺丰的发', '[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `hall` VALUES ('8', null, 'string', '[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 1, -1]]');
INSERT INTO `hall` VALUES ('9', null, '阿斯蒂芬撒敌法', '[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `hall` VALUES ('10', null, '阿斯顿发射点发顺丰的发', '[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 1, -1]]');
INSERT INTO `hall` VALUES ('11', '100046', 'sssvip', '[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 1, -1]]');
INSERT INTO `hall` VALUES ('16', '100102', '二十发', '[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 1, -1]]');
INSERT INTO `hall` VALUES ('17', null, null, '[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 1, -1]]');
INSERT INTO `hall` VALUES ('18', '2', '二号厅', '[[1, 0, 1, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `hall` VALUES ('19', '2', '一号厅', '[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 1, -1]]');
INSERT INTO `hall` VALUES ('20', '2', 'IMAX厅', '[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 1, -1]]');
INSERT INTO `hall` VALUES ('1784872965', null, '一号厅', '[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `hall` VALUES ('1784872968', '100013', 's', '[[0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0],[0, -1, -1, 0, 0, 0, -1, -1, -1],[0, 0, 0, 0, -1, 0, 0, 0, 0],[0, 0, 0, 0, -1, 0, 0, 0, 0],[0, 0, 0, 0, -1, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `hall` VALUES ('1784872969', '706893', 'test', '[[0,0,0,0,0,0,0,0,0,-1],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0]]');
INSERT INTO `hall` VALUES ('1784872970', '706893', 'a', '[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `hall` VALUES ('1784872971', '10421', 'nnn', '[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `hall` VALUES ('1784872972', '10421', 'aaaaa', '[[0,0,0,0,0,0,0,0,0,1],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0]]');
INSERT INTO `hall` VALUES ('1784872973', '10422', 'nn', '[[0,0,0,0,0,-1,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0]]');
INSERT INTO `hall` VALUES ('1784872974', '10423', '向学楼', '[[0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0],[-1, -1, -1, -1, -1, -1, -1, -1, -1],[0, 0, 0, 0, -1, 0, 0, 0, 0],[0, 0, 0, 0, -1, 0, 0, 0, 0],[0, 0, 0, 0, -1, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `hall` VALUES ('1784872975', '10410', '20240106TEST', '[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `hall` VALUES ('1784872976', '10213', 'TEST20240106VIP', '[[0,0,0,0,0,0,0,0,0,1],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0]]');
INSERT INTO `hall` VALUES ('1784872977', '10424', '向学楼', '[[0,-1,0,-1,0,0,0,-1,0,-1,0],[0,-1,0,-1,0,0,0,-1,0,-1,0],[0,-1,0,-1,-1,0,-1,-1,0,-1,0],[0,0,0,-1,-1,0,-1,-1,0,-1,0],[0,0,0,-1,-1,0,-1,-1,0,-1,0],[0,-1,0,-1,-1,0,-1,-1,0,-1,0],[0,-1,0,-1,-1,0,-1,-1,0,0,0],[0,-1,0,-1,-1,0,-1,-1,0,0,0]]');

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '名称',
  `route` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '路由',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'component',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '图标',
  `level` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '层级',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '介绍',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '状态',
  `parent_id` int DEFAULT NULL COMMENT '父id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '用户管理', null, '用户', 'el-icon-lx-people', '1', 'test', '1', null);
INSERT INTO `menu` VALUES ('2', '电影管理', null, null, 'el-icon-lx-record', '1', null, '1', null);
INSERT INTO `menu` VALUES ('3', '影院管理', null, null, 'el-icon-lx-shop', '1', null, '1', null);
INSERT INTO `menu` VALUES ('4', '影厅管理', null, null, 'el-icon-lx-favor', '1', null, '1', null);
INSERT INTO `menu` VALUES ('5', '电影排片', 'test', null, 'el-icon-lx-sort', '1', null, '1', null);
INSERT INTO `menu` VALUES ('6', '评论管理', null, null, 'el-icon-lx-cascades', '1', null, '1', null);
INSERT INTO `menu` VALUES ('7', '订单管理', null, null, 'el-icon-lx-vipcard', '1', null, '1', null);
INSERT INTO `menu` VALUES ('8', '个人信息', 'userdata', null, 'el-icon-lx-cascades', '1', null, '1', '16');
INSERT INTO `menu` VALUES ('9', '修改密码', 'setPassword', null, 'el-icon-lx-cascades', '2', null, '1', '16');
INSERT INTO `menu` VALUES ('10', '用户列表', 'usertable', null, 'el-icon-lx-cascades', '2', null, '1', '1');
INSERT INTO `menu` VALUES ('11', '影院列表', 'cinematable', null, 'el-icon-lx-cascades', '2', null, '1', '3');
INSERT INTO `menu` VALUES ('14', '电影分类', 'movietype', null, 'el-icon-lx-cascades', '2', null, '1', '2');
INSERT INTO `menu` VALUES ('15', '电影列表', 'movietable', null, 'el-icon-lx-cascades', '2', null, '1', '2');
INSERT INTO `menu` VALUES ('16', '个人管理', null, null, 'el-icon-lx-sort', '1', null, '1', null);
INSERT INTO `menu` VALUES ('19', '系统管理', null, null, 'el-icon-lx-warn', '1', null, '1', null);
INSERT INTO `menu` VALUES ('20', '菜单管理', 'menutable', null, null, '2', null, '1', '19');
INSERT INTO `menu` VALUES ('21', '角色管理', 'roletable', null, null, '2', null, '1', '19');
INSERT INTO `menu` VALUES ('22', '权限管理', 'permission', null, null, '2', null, '1', '19');
INSERT INTO `menu` VALUES ('23', '影厅表', 'halltable', null, null, '2', null, '1', '4');
INSERT INTO `menu` VALUES ('24', '排片表', 'showtime', null, null, '2', null, '1', '5');
INSERT INTO `menu` VALUES ('25', '评论表', null, null, null, '2', null, '0', '6');
INSERT INTO `menu` VALUES ('26', '订单表', 'order', null, null, '2', null, '1', '7');
INSERT INTO `menu` VALUES ('27', '影院信息', null, null, null, '2', null, '0', '3');
INSERT INTO `menu` VALUES ('28', '管理员列表', 'cinemauser', null, null, '2', null, '1', '1');

-- ----------------------------
-- Table structure for movie
-- ----------------------------
DROP TABLE IF EXISTS `movie`;
CREATE TABLE `movie` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '名称',
  `banner` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '海报',
  `region` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '区域',
  `movie_length` int DEFAULT NULL COMMENT '电影播放时长',
  `score` double DEFAULT NULL COMMENT '评分',
  `box_office` int DEFAULT NULL COMMENT '票房',
  `synopsis` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '剧情简介',
  `langue` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '电影语言版本',
  `release_time` datetime DEFAULT NULL COMMENT '上映时间',
  `want_number` int DEFAULT NULL COMMENT '想看人数',
  `awards` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '奖项',
  `publisher` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '出品发行商',
  `price` double(10,2) DEFAULT NULL COMMENT '电影价格',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of movie
-- ----------------------------
INSERT INTO `movie` VALUES ('1', '怒潮', 'https://p0.pipi.cn/mmdb/fb73862f2ff51b339eddd204b77b9f4f4a215.jpg?imageView2/1/w/464/h/644', '中国大陆', '106', '9.3', '154000000', '年末解恨！除厄运！保平安！揭露海外犯罪产业黑幕：人口贩卖、器官交易…… 张家辉、阮经天、王大陆搏命黑吃黑！横跨黑白两道只手遮天的洪泰集团正值换选之际，一个神秘杀手陈安（张家辉 饰）却突然只身闯入这个是非混乱的旋涡，搅得洪泰集团大乱。身处警察阵营的麦朗汶（阮经天 饰）和黑帮阵营的马文康（王大陆 饰）也盯上了他……各方势力伺机而动，谁才是幕后的操控者？一场生猛混战一触即发。', '国语2D', '2024-01-05 09:00:00', '300000', '0', '厦门恒业牧马人影视文化传播有限公司', '38.50');
INSERT INTO `movie` VALUES ('2', '海王2:失落的王国', 'https://p0.pipi.cn/mmdb/fb73862f8d3ddd67cb537ca21505eb8a578f5.jpg?imageView2/1/w/464/h/644', '美国', '125', '9', '145000000', '惊涛再起，王者归来！《海王2：失落的王国》讲述了海王的全新传奇。在上一次试图击败海王未果后，黑蝠鲼依然不甘放弃为父报仇，誓要消灭海王。这一次，他找到了传说中的黑暗三叉戟，释放出古老的邪恶力量，比以往更来势汹汹。为了与之抗衡，海王向被囚禁狱中的弟弟奥姆（也是前亚特兰蒂斯国王）求助，组成了出乎意料的联盟。他俩必须抛弃前仇旧怨，携手并肩作战，才能从即将到来的灾难中保卫王国，拯救家人，拯救世界。', '原版3D', '2024-01-04 00:00:00', '4500', '0', '美国华纳兄弟影片公司', '36.50');
INSERT INTO `movie` VALUES ('3', '照明商店', '	https://p0.pipi.cn/mmdb/fb73862f8d3b12230fddd2dd65d6dfe01aa8b.jpg?imageView2/1/w/464/h/644', '中国大陆', '99', '9.1', '222000000', '深巷的尽头，一间“照明商店”长年亮着灯，神秘的老板（刘奕君 饰）细心擦拭着每一个灯泡，形形色色的客人络绎不绝。护士许念（章若楠 饰）和男友郑满（白宇帆 饰）新搬进了商店附近的公寓。她渐渐发现，这里的每一个人，心底都有一个不可言说的秘密。暴风雨之夜，她走进了巷尾的照明商店，答案渐渐被揭开……', '国语2D', '2024-01-04 18:00:00', '0', '0', '北京光线影业有限公司', '36.00');
INSERT INTO `movie` VALUES ('4', '\r\n年会不能停！', '	https://p0.pipi.cn/mmdb/fb73862f2ffc7eb860f2aab62cb4a844c40f0.jpg?imageView2/1/w/464/h/644', '中国大陆', '117', '9.6', '24080000', '高口碑喜剧片，看了都说好！年度超疯喜剧黑马！从头笑到尾，快乐不能停！钳工胡建林 (大鹏 饰)在集团裁员之际阴差阳错被调入总部，裹挟在“错调”事件中的人事经理马杰 (白客 饰) 为保饭碗被迫为其隐瞒四处周旋。从“工厂”到“大厂”，从“蓝领”变“金领”，胡建林因与大厂环境格格不入而笑料百出，也像一面“职场照妖镜”照出职场众生相......胡建林为何能在裁员之际一路升职加薪制霸大厂？马杰又能否在“错调”事件中全身而退？这场离谱的“错调”背后又隐藏着什么惊天大瓜……', '国语2D', '2024-01-03 18:00:00', '10', '0', '北京嘉映春天影业有限公司', '22.00');
INSERT INTO `movie` VALUES ('5', '三大队', 'https://p0.pipi.cn/mmdb/fb73862f06d923537c0e1350dd782507d890f.jpg?imageView2/1/w/464/h/644', '中国大陆', '132', '9.4', '369000000', '电影《三大队》根据真实事件改编，原载于“网易人间工作室”，原作名为《请转告局长，三大队任务完成了》（作者深蓝）。 刑侦大队队长程兵（张译 饰）带领的三大队在办理一起恶性案件的过程中导致嫌犯之一意外死亡，被判入狱。出狱后依然坚持以普通人身份追踪在逃嫌犯的故事。', '国语2D', '2024-01-05 09:00:00', '0', '0', '万达影业（海南）有限公司', '22.00');
INSERT INTO `movie` VALUES ('6', '死侍', 'https://img.zcool.cn/community/011a3e59633491a8012193a3bc10dc.jpg@1280w_1l_2o_100sh.jpg', '美国', '123', '9', '369000000', '哈哈哈哈', '原版3D', '2024-01-03 20:58:26', '15', '0', 'dd', '22.00');
INSERT INTO `movie` VALUES ('7', '盗墓笔记', 'https://img.zcool.cn/community/013d155c184b96a801209252b7c631.jpg@1280w_1l_2o_100sh.jpg', '中国大陆', '123', '9', '12123456', '多读多', '国语2D', '2024-01-03 20:59:17', '11', '0', '132', '22.00');
INSERT INTO `movie` VALUES ('8', '哈哈哈', 'https://img.zcool.cn/community/01d8305d59359aa8012187f483d3db.jpg@1280w_1l_2o_100sh.jpg', '中国大陆', '456', '9', '456789', '顶顶顶顶', '国语2D', '2024-01-03 21:00:06', '456', '0', 'ddddd', '22.00');
INSERT INTO `movie` VALUES ('10', '你的婚礼', 'https://p0.pipi.cn/mmdb/fb73869251b5bf51bae7aaaa5f1b43ad315b4.jpg?imageView2/1/w/464/h/644', '中国大陆', '115', '9.6', '789000000', '高中时，游泳特长生周潇齐 （许光汉饰）对转校生尤咏慈 （章若楠饰）一见钟情，年少懵懂的纯纯爱恋，男孩默默守护，但女孩却不告而别。此后的人生，15年的爱情长跑。你的婚礼，桨辩微备也是我的成人礼 。', '国语2D', '2024-01-04 10:00:00', '1450254', '1', '霍尔果斯青春光线影业有限公司', '22.00');
INSERT INTO `movie` VALUES ('44', '自定义', 'http://s6gtr7r2k.hb-bkt.clouddn.com/b6aa183018f845d992e76f09d3e1f412001.png', '中国大陆', '555', '0', '1666', '15646', '国语2D', '2024-02-01 11:45:00', '0', '', 'tttt', null);
INSERT INTO `movie` VALUES ('45', '\r\n舒克贝塔·五角飞碟', 'https://p0.pipi.cn/mmdb/54ecde8d9ab0fab5359235e894deb18f43d3d.jpg?imageView2/1/w/464/h/644', '中国大陆', '92', '9.1', '373200000', '大朋友小朋友喜大普奔！童话大王郑渊洁国民IP首登大银幕！开启新生代亲子观众逐梦之旅！ 舒克贝塔驾驶着全新装备五角飞碟向宇宙出发冒险，但是当他们回到地球时突发意外，穿越到十年后的魔方市。老鼠们的航空公司已经被捣毁，有一伙自称“极盗团”的老鼠们，正在霸占机场做尽坏事！为了重建自己的家园，舒克贝塔加入极盗团展开了一场卧底行动！这一次坚强勇敢的舒克贝塔该如何应对？请各位小伙伴们拭目以待！', '国语2D', '2023-12-30 09:00:00', '300000', null, '杭州童话大王影视有限公司', null);
INSERT INTO `movie` VALUES ('46', '金手指', 'https://p0.pipi.cn/mmdb/fb73862f5bf5bf02ff300b44817687f64dd8c.jpg?imageView2/1/w/464/h/644', '中国香港', '125', '9.2', '294000000', '用100元的投入换来百亿奢靡人生！看穷小子不择手段颠覆规则将财富和权势玩弄于股掌之中！梁朝伟刘德华二十年后再合体，极致演绎跨年档最受期待的 “暴富”大片！改自真实案件揭秘金融资本内幕！看《金手指》，一览上流社会令人瞠目结舌的狂妄生活！ 上市公司嘉文集团在短短几年间从默默无名到风生水起，再到没落清盘，市值蒸发超过一百亿。幕后老板程一言(梁朝伟饰)也从万众瞩目的股民偶像变成人人喊打的过街老鼠。高级调查主任刘启源(刘德华饰)长达十五年锲而不舍地搜证和跨境调查，消耗超过两亿诉讼费，竟发现局中有局案中有案，牵涉数条人命并波及香港整个上流社会，究竟谁在幕后?谁能逃脱?谁会出局?', '国语2D', '2023-12-30 09:00:00', '200000', null, '英皇（北京）影视文化传媒有限公司', null);
INSERT INTO `movie` VALUES ('47', '\r\n非诚勿扰3', 'https://p0.pipi.cn/mmdb/fb73862fc695bf925716bdced691878d22ee3.jpg?imageView2/1/w/464/h/644', '中国大陆', '119', '9', '71170000', '秦奋与梁笑笑，老狐狸与比目鱼，爱情故事万千，取其一对展开。两人结婚十年，梁笑笑找到心之所向，开始四海为家。 一别之后，两地相悬。以为是三四月，又谁知五六七八九十年。 好友老范，见其思念难解，忧其岁月蹉跎，故赠予一仿生智能人，模样若笑笑，伴其左右。 岁月时而静好，时而吵闹，时而苦中有笑，智能人也日渐有了曾经佳人的味道。本是良辰美景，故事突发变故，又一笑笑开锁入门，一笑笑莞尔一笑，一笑笑笑里藏刀，一切如梦如幻，如真似假。秦奋射出的箭，如今正中自己的靶心。谁去谁留？且在跨年揭晓。', '国语2D', '2023-12-30 10:00:00', '100000', null, '中影创意（北京）电影有限公司', null);
INSERT INTO `movie` VALUES ('48', '\r\n一个人的江湖', 'https://p0.pipi.cn/mmdb/54ecde8d8d3ddd50c8f0ee963d22eece610b8.jpg?imageView2/1/w/464/h/644', '中国大陆', '90', null, '70000', '拳村”少年张小明，是小拳种金刚拳的唯一传承人，他肩负着将“金刚门”发扬光大的重任，只身来到大城市，希冀用传统的武林方式实现光复门派的愿望，怎奈屡屡碰壁，遭人耻笑。正当他心灰意冷时，他却邂逅了一位叫李昂的神秘蹉跎青年……', '国语2D', '2024-01-09 05:34:00', '4516', null, '北京中视皮皮影业有限公司', null);
INSERT INTO `movie` VALUES ('49', '皮壳之下', 'https://p0.pipi.cn/mmdb/fb73862fb53339be2a230f6a3888b9bfa7423.jpg?imageView2/1/w/464/h/644', '中国大陆', '98', null, '10000', '北方某县城某棋牌室女店主陈翠香晚上回家路上被抢劫,陈翠香是玉石店老板陈生的姐姐。几天后,玉石店老板陈生夜里被钝器杀害。经警察查看路口监控录像,发现开出租的秦大志几次经过出事现场。玉石店老板陈生被害案件还在调查中,又出现了另一与秦大志有关的事件。一位因妻子出轨的公司老板张树业来该县城旅游,为解闷参与赌石,因赌石结识了秦大志和六仔。张树业对赌石越陷越深,且屡赌屡输,跳河自杀。当刑警再次查看玉石店老板陈生被害当晚秦大志行车记录仪,行车记录仪显示,在当天晚上一男子匆匆跑过,差点撞上秦大志的出车。秦大志却一直隐瞒了这个情况。二位警察再次询问秦大志,秦大志不得不承认当晚确实看见一男子,并表示不认识该男子。警察把秦大志和该男子(六仔)都列为调查对象......', '国语2D', '2024-01-09 10:36:00', '657', null, '北京虹合影业有限公司', null);
INSERT INTO `movie` VALUES ('50', '\r\n夏来冬往', 'https://p0.pipi.cn/mmdb/fb7386dd923dddd7c3be2ac9b23f5299b94e7.jpg?imageView2/1/w/464/h/644', '中国大陆', '97', null, '0', '在粤海市打拼多年的佳妮与男友志远，最近因为结婚买房的事产生分歧。一通电话让她重新联系到自己的亲生家庭并踏上寻亲之路，她发现原来自己还有3个亲姐弟。在生母家的这段日子里，三姐妹相互治愈疗愈，让佳妮想起了小时候与养父生活的点点滴滴，也让她开始重新审视自己对于家庭关系和感情的认知。回到粤海市后，看着一如既往在桥头迎接她回家的志远，她鼓起勇气做出改变。', '国语2D', '2024-01-10 05:37:00', '124', null, '深圳市微娱年代影视传媒有限公司', null);
INSERT INTO `movie` VALUES ('51', '大雨', 'https://p0.pipi.cn/mmdb/54ecde8d87af2a2ff739dd0e6eb9111084681.jpg?imageView2/1/w/464/h/644', '中国大陆', '101', null, '0', '重生大戏即将上演！沉没多年古船离奇现世，男孩馒头意外闯入，却发现船上聚集着诸多亡魂.... 一场大雨过后，“蝼蚁”重生，命运会有改变吗？', '国语2D', '2024-01-18 05:38:00', '38315', null, '上海今涂影业有限公司', null);
INSERT INTO `movie` VALUES ('52', '动物园里有什么？', 'https://p0.pipi.cn/mmdb/fb73862f7a3923395b67cb4ced5752d80d4b3.jpg?imageView2/1/w/464/h/644', '中国大陆', '96', null, '0', '一个打工社畜突然接手濒临倒闭的动物园，他试图与奇葩员工们完成一场令人匪夷所思的动物园营业计划。', '国语2D', '2024-01-17 05:39:00', '32765', null, '无锡观时文化传媒有限责任公司', null);
INSERT INTO `movie` VALUES ('53', '养蜂人', 'https://p0.pipi.cn/mmdb/54ecde8d87a51becd8b535e7398e69252338a.jpg?imageView2/1/w/464/h/644', '美国', '105', null, '0', '克莱（杰森·斯坦森 Jason Statham 饰）在乡下养蜂，平静度日。与其交好的房东奶奶帕克，被一场网络诈骗卷走了所有慈善筹款，崩溃自杀。克莱获悉真相后，孤身勇闯诈骗集团复仇。追查中，克莱挖出诈骗团伙背后盘根错节的利益集团，面临疯子特工、权势财团、FBI的接连围剿，游离法律之外的暗卫组织“养蜂人”也牵扯其中，克莱的真实身份也浮出水面。克莱能否凭一己之力对抗滔天罪恶？新的“疯暴”正在袭来……', '原版2D', '2024-01-16 05:40:00', '32504', null, '美国米拉麦克斯公司', null);
INSERT INTO `movie` VALUES ('54', '小行星猎人', 'https://p0.pipi.cn/mmdb/fb73869280751b0faf67cb082c20b3d9f4aaa.jpg?imageView2/1/w/464/h/644', '美国', '38', null, '0', 'IMAX原创电影《小行星猎人》（Asteroid Hunters）将带您深入太空，了解小行星这一天文奇观的迷人之处，探索其宇宙起源和对地球的潜在威胁。影片由黛西·雷德利（Daisy Ridley）担任英文版解说，大鹏担任中文版解说，介绍了小行星科学家并展示他们探测跟踪小行星的前沿工具及技术，以及有朝一日或将保护地球的先进科技。电影将让观众见证小行星防御领域的最新进展，了解人类如何凭借科学、创造力与决心，探索应对最可防御的自然灾难。', '原版2D', '2024-01-15 05:41:00', '8856', null, '美国爱麦克斯原创电影公司', null);
INSERT INTO `movie` VALUES ('55', '红毯先生', 'https://p0.pipi.cn/mmdb/fb73869233951bddd257e2d64480d4c83a577.jpg?imageView2/1/w/464/h/644', '中国大陆', '125', null, '0', '香港天王巨星刘伟驰（刘德华 饰）从影四十年，一直渴望得影帝。他决定与导演林浩（宁浩 饰）合作拍摄农村题材影片，从而在电影节赢得国际声誉。为此，刘伟驰深入农村体验生活、亲自拉投资、拒绝用替身，却因此引发了一系列令人哭笑不得的荒诞闹剧，也展现了娱乐圈的众生百态。', '国语2D', '2024-02-08 05:43:00', '97628', null, '上海欢十喜文化有限公司', null);
INSERT INTO `movie` VALUES ('56', 'testr', null, 'zh', '150', '0', '10086', '1111', 'zh', '2024-01-25 23:53:00', '0', '', '1111', null);

-- ----------------------------
-- Table structure for movie_cinema_mapping
-- ----------------------------
DROP TABLE IF EXISTS `movie_cinema_mapping`;
CREATE TABLE `movie_cinema_mapping` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `movie_id` int DEFAULT NULL COMMENT '电影id',
  `cinema_id` int DEFAULT NULL COMMENT 'cinemaUser的account',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of movie_cinema_mapping
-- ----------------------------
INSERT INTO `movie_cinema_mapping` VALUES ('1', '1', '100002');
INSERT INTO `movie_cinema_mapping` VALUES ('2', '2', '100002');
INSERT INTO `movie_cinema_mapping` VALUES ('3', '3', '100002');
INSERT INTO `movie_cinema_mapping` VALUES ('4', '4', '100002');
INSERT INTO `movie_cinema_mapping` VALUES ('5', '5', '100002');
INSERT INTO `movie_cinema_mapping` VALUES ('6', '6', '100002');
INSERT INTO `movie_cinema_mapping` VALUES ('7', '1', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('8', '2', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('39', '8', '100002');
INSERT INTO `movie_cinema_mapping` VALUES ('41', '44', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('42', '45', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('43', '46', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('44', '47', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('45', '18', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('46', '49', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('47', '50', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('48', '1', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('49', '2', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('50', '3', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('51', '4', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('52', '5', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('53', '6', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('54', '7', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('55', '8', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('56', '10', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('57', '44', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('58', '45', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('59', '46', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('60', '47', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('61', '48', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('62', '49', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('63', '50', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('64', '51', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('65', '52', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('66', '53', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('67', '54', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('68', '55', '10087');
INSERT INTO `movie_cinema_mapping` VALUES ('69', '1', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('70', '2', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('71', '3', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('72', '4', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('73', '5', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('74', '6', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('75', '7', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('76', '8', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('77', '10', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('78', '44', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('79', '45', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('80', '46', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('81', '47', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('82', '48', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('83', '49', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('84', '50', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('85', '51', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('86', '52', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('87', '53', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('88', '54', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('89', '55', '917937');
INSERT INTO `movie_cinema_mapping` VALUES ('90', '56', '917937');

-- ----------------------------
-- Table structure for movie_comment
-- ----------------------------
DROP TABLE IF EXISTS `movie_comment`;
CREATE TABLE `movie_comment` (
  `id` int NOT NULL COMMENT 'id',
  `user_id` int DEFAULT NULL COMMENT '用户id',
  `movie_id` int DEFAULT NULL COMMENT '电影id',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `likes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '喜欢',
  `comment_id` int DEFAULT NULL COMMENT '父影评',
  `score` double(10,0) DEFAULT NULL COMMENT '得分',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of movie_comment
-- ----------------------------

-- ----------------------------
-- Table structure for movie_img
-- ----------------------------
DROP TABLE IF EXISTS `movie_img`;
CREATE TABLE `movie_img` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `movie_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '电影id',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '电影相关图片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of movie_img
-- ----------------------------
INSERT INTO `movie_img` VALUES ('1', '10', 'https://p0.pipi.cn/basicdata/25bfd6d7537e7a57e2030c2c2063b27b083e1.jpg?imageView2/1/w/128/h/170');
INSERT INTO `movie_img` VALUES ('2', '10', 'https://p0.pipi.cn/basicdata/25bfd6d7537e7a57e2030c2c2063b27b083e1.jpg?imageView2/1/w/128/h/170');
INSERT INTO `movie_img` VALUES ('3', '10', 'https://p0.pipi.cn/basicdata/25bfd6d7537e7a57e2030c2c2063b27b083e1.jpg?imageView2/1/w/128/h/170');
INSERT INTO `movie_img` VALUES ('5', '45', 'https://p0.pipi.cn/friday/21396f86be6e2c9a2f233e255430921d.jpg?imageView2/1/w/106/h/106');
INSERT INTO `movie_img` VALUES ('6', '45', 'https://p0.pipi.cn/friday/7ea28ee08fc53f2cbe8d14b1c60a12f0.jpg?imageMogr2/thumbnail/2500x2500%3E');
INSERT INTO `movie_img` VALUES ('7', '45', 'https://p0.pipi.cn/friday/bbfe3e24239edf296a72858f64a8a8ab.jpg?imageMogr2/thumbnail/2500x2500%3E');
INSERT INTO `movie_img` VALUES ('8', '45', 'https://p0.pipi.cn/friday/c459da33b2b5907971889b9062d9a4f0.jpg?imageMogr2/thumbnail/2500x2500%3E');
INSERT INTO `movie_img` VALUES ('9', '45', 'https://p0.pipi.cn/friday/37ae7239c4b794b958ec597b582b8bc7.jpg?imageMogr2/thumbnail/2500x2500%3E');
INSERT INTO `movie_img` VALUES ('10', '45', 'https://p0.pipi.cn/friday/c8cd94979f63083657d91f705e7b8f50.jpg?imageMogr2/thumbnail/2500x2500%3E');
INSERT INTO `movie_img` VALUES ('11', '45', 'https://p0.pipi.cn/friday/e730a27a3794d42d2f95add1279525a4.jpg?imageMogr2/thumbnail/2500x2500%3E');
INSERT INTO `movie_img` VALUES ('12', '45', 'https://p0.pipi.cn/friday/19a4fcbf947679f3772cc42ee47cd7d4.jpg?imageMogr2/thumbnail/2500x2500%3E');
INSERT INTO `movie_img` VALUES ('13', '45', 'https://p0.pipi.cn/friday/bf0b2157aa353d1c123f62c69edb5dba.jpg?imageMogr2/thumbnail/2500x2500%3E');
INSERT INTO `movie_img` VALUES ('14', '45', 'https://p0.pipi.cn/friday/d9afeece821771d46c3754e2818a0e03.jpg?imageMogr2/thumbnail/2500x2500%3E');
INSERT INTO `movie_img` VALUES ('15', '45', 'https://p0.pipi.cn/friday/e75bec79eeb35274614ff2f13476a726.jpg?imageMogr2/thumbnail/2500x2500%3E');
INSERT INTO `movie_img` VALUES ('16', '45', 'https://p0.pipi.cn/mmdb/fb73868db12923ddd27a350bc851f3580c7e3.jpg?imageMogr2/thumbnail/2500x2500%3E');
INSERT INTO `movie_img` VALUES ('17', '45', 'https://p0.pipi.cn/friday/0751bb8bb52369ff3e5c401f49104af0.jpg?imageView2/1/w/106/h/106');
INSERT INTO `movie_img` VALUES ('18', '45', 'https://p0.pipi.cn/friday/065fa3604c82e8a4730f142e7a9f6dc1.jpg?imageView2/1/w/106/h/106');

-- ----------------------------
-- Table structure for movie_people
-- ----------------------------
DROP TABLE IF EXISTS `movie_people`;
CREATE TABLE `movie_people` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `movie_id` int DEFAULT NULL COMMENT '电影',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '人名',
  `img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'img',
  `job` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '在整部电影中的工作：编剧？出品人？制片人？演员？导演？',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of movie_people
-- ----------------------------
INSERT INTO `movie_people` VALUES ('1', '10', '韩天', '	https://p0.pipi.cn/basicdata/25bfd6d7537e7a57e2030c2c2063b27b083e1.jpg?imageView2/1/w/128/h/170', '导演');
INSERT INTO `movie_people` VALUES ('2', '10', '张影\r\n', '	https://p0.pipi.cn/basicdata/25bfd6d7537e7acf3e339ea76768369fa11e0.jpg?imageView2/1/w/128/h/170', '编剧');
INSERT INTO `movie_people` VALUES ('3', '10', '焦婷婷', '	https://p0.pipi.cn/basicdata/25bfd6d7537c69030c9ab43c6cf5f9fe94b30.jpg?imageView2/1/w/128/h/170', '编剧');
INSERT INTO `movie_people` VALUES ('4', '10', '韩天', '	https://p0.pipi.cn/basicdata/25bfd6d7537e7a57e2030c2c2063b27b083e1.jpg?imageView2/1/w/128/h/170', '编剧');
INSERT INTO `movie_people` VALUES ('5', '10', '王长田', 'https://p0.pipi.cn/basicdata/25bfd6d753706dd7c35bf123d0f300362252a.jpg?imageView2/1/w/128/h/170', '出品人');
INSERT INTO `movie_people` VALUES ('6', '10', '刘军', 'https://p0.pipi.cn/basicdata/25bfd6d7b53338f2aa92357913651883bc28a.jpg?imageView2/1/w/128/h/170', '出品人');
INSERT INTO `movie_people` VALUES ('7', '10', '曹晓北', 'https://p0.pipi.cn/basicdata/25bfd6d77a3be1b5353ba3a86a27db4665f5b.jpg?imageView2/1/w/128/h/170', '出品人');
INSERT INTO `movie_people` VALUES ('8', '10', '刘琼芳', 'https://p0.pipi.cn/basicdata/25bfd6d7807338c696b12de58f921755d71eb.png?imageView2/1/w/128/h/170', '出品人');
INSERT INTO `movie_people` VALUES ('9', '10', '曹晓北', 'https://p0.pipi.cn/basicdata/25bfd6d77a3be1b5353ba3a86a27db4665f5b.jpg?imageView2/1/w/128/h/170', '制片人 ');
INSERT INTO `movie_people` VALUES ('10', '10', '刘军', 'https://p0.pipi.cn/basicdata/25bfd6d7b53338f2aa92357913651883bc28a.jpg?imageView2/1/w/128/h/170', '制片人 ');
INSERT INTO `movie_people` VALUES ('11', '10', '刘琼芳', 'https://p0.pipi.cn/basicdata/25bfd6d7807338c696b12de58f921755d71eb.png?imageView2/1/w/128/h/170', '制片人 ');
INSERT INTO `movie_people` VALUES ('12', '10', '张弘毅', 'https://p0.pipi.cn/basicdata/25bfd6d77a30e111e58d337e947c78a5c5a0d.jpg?imageView2/1/w/128/h/170', '总策划');
INSERT INTO `movie_people` VALUES ('13', '10', '陈琳', '	https://p0.pipi.cn/basicdata/25bfd6d77a33395bf106d6ad55d287112ddee.jpg?imageView2/1/w/128/h/170', '摄影指导');
INSERT INTO `movie_people` VALUES ('14', '10', '宇明远', 'https://p0.pipi.cn/basicdata/25bfd6d7537e7acbaed23c7a9a5b4564ae904.jpg?imageView2/1/w/128/h/170', '摄影指导');
INSERT INTO `movie_people` VALUES ('15', '10', '何爽', 'https://p0.pipi.cn/basicdata/25bfd6d77a30e131392c955264e204fddfefd.jpg?imageView2/1/w/128/h/170', '美术指导');
INSERT INTO `movie_people` VALUES ('16', '10', '张金岩', 'https://p0.pipi.cn/basicdata/25bfd6dd3395bf3ba3395bd34517583e6f0dc.jpg?imageView2/1/w/128/h/170', '录音指导');
INSERT INTO `movie_people` VALUES ('17', '10', '龙筱竹', 'https://p0.pipi.cn/basicdata/25bfd6d7537c69c696b5355f4eeb9a9776ee4.jpg?imageView2/1/w/128/h/170', '录音指导');
INSERT INTO `movie_people` VALUES ('18', '10', '朱琳', '	https://p0.pipi.cn/basicdata/25bfd6d7537c695015cbae443e669094c844f.jpg?imageView2/1/w/128/h/170', '剪辑指导 ');
INSERT INTO `movie_people` VALUES ('19', '10', '陈建骐\r\n', '	https://p0.pipi.cn/basicdata/25bfd6d77a30e10fafbe2a83470fabb326788.jpg?imageView2/1/w/128/h/170', '作曲 ');
INSERT INTO `movie_people` VALUES ('20', '10', '赵珂', '	https://p0.pipi.cn/basicdata/25bfd6d77a3339e7aa8ea3ea02fdb565ddc5b.jpg?imageView2/1/w/128/h/170', '作曲 ');
INSERT INTO `movie_people` VALUES ('21', '10', '罗恩妮', 'https://p0.pipi.cn/basicdata/25bfd6d7807338c696b12de58f921755d71eb.png?imageView2/1/w/128/h/170', '作曲 ');
INSERT INTO `movie_people` VALUES ('22', '10', '张洪伟', '	https://p0.pipi.cn/basicdata/fb73869a0fa923925767cbd6ecc970a76d171.jpg?imageView2/1/w/128/h/170', '执行导演');
INSERT INTO `movie_people` VALUES ('23', '10', '徐吉晴', 'https://p0.pipi.cn/basicdata/25bfd6d7807338c696b12de58f921755d71eb.png?imageView2/1/w/128/h/170', '执行制片人 ');
INSERT INTO `movie_people` VALUES ('24', '10', '许力文', '	https://p0.pipi.cn/basicdata/25bfd6d7537e7a7df5be1212d5d57f287c398.jpg?imageView2/1/w/128/h/170', '造型指导');
INSERT INTO `movie_people` VALUES ('25', '10', '郭栋楠', 'https://p0.pipi.cn/basicdata/25bfd6d77a30e1ddd2338f762b6aea1a7a55c.jpg?imageView2/1/w/128/h/170', '宣传总监');
INSERT INTO `movie_people` VALUES ('26', '45', '郑亚旗', 'https://p0.pipi.cn/basicdata/fb7386bef2a5bfbe2a281e3aaf1426c1606db.png?imageView2/1/w/128/h/170', '导演');
INSERT INTO `movie_people` VALUES ('27', '45', '郑亚旗', 'https://p0.pipi.cn/basicdata/fb7386bef2a5bfbe2a281e3aaf1426c1606db.png?imageView2/1/w/128/h/170', '编剧');
INSERT INTO `movie_people` VALUES ('28', '45', '薛宏达', 'https://p0.pipi.cn/basicdata/25bfd6d7807338c696b12de58f921755d71eb.png?imageView2/1/w/128/h/170', '编剧');
INSERT INTO `movie_people` VALUES ('29', '45', '刘晓峥', 'https://p0.pipi.cn/basicdata/25bfd6d7807338c696b12de58f921755d71eb.png?imageView2/1/w/128/h/170', '编剧');
INSERT INTO `movie_people` VALUES ('30', '45', '郑亚旗', 'https://p0.pipi.cn/basicdata/fb7386bef2a5bfbe2a281e3aaf1426c1606db.png?imageView2/1/w/128/h/170', '出品人');
INSERT INTO `movie_people` VALUES ('31', '45', '郑志昊', 'https://p0.pipi.cn/basicdata/25bfd6d7537e7a50c811e50894e359d0398eb.jpg?imageView2/1/w/128/h/170', '出品人');
INSERT INTO `movie_people` VALUES ('32', '45', '张博', 'https://p0.pipi.cn/basicdata/25bfd69251b92371f77df508887644710dcb3.jpg?imageView2/1/w/128/h/170', '出品人');
INSERT INTO `movie_people` VALUES ('33', '45', '蔡元', 'https://p0.pipi.cn/basicdata/25bfd6518075bf8d33cf3e102dea0b84f34cd.jpg?imageView2/1/w/128/h/170', '出品人');
INSERT INTO `movie_people` VALUES ('34', '45', '徐天福', 'https://p0.pipi.cn/basicdata/25bfd6d7537e7a1789d7c3e09e6de1aad7cf1.jpg?imageView2/1/w/128/h/170', '出品人');
INSERT INTO `movie_people` VALUES ('35', '45', '雷芳昌', 'https://p0.pipi.cn/basicdata/25bfd6d77a33397e12be126a121a6bd219341.jpg?imageView2/1/w/128/h/170', '联合出品人');
INSERT INTO `movie_people` VALUES ('36', '45', '黄皞', 'https://p0.pipi.cn/basicdata/25bfd6d7807338c696b12de58f921755d71eb.png?imageView2/1/w/128/h/170', '联合出品人');
INSERT INTO `movie_people` VALUES ('37', '45', '曾茂军', 'https://p0.pipi.cn/basicdata/25bfd6d7537e7ad236339e69d7878bd65efde.jpg?imageView2/1/w/128/h/170', '联合出品人');
INSERT INTO `movie_people` VALUES ('38', '45', '王垂林', 'https://p0.pipi.cn/basicdata/25bfd6d77a3be12c95ddd286c823b0c0f42ca.jpg?imageView2/1/w/128/h/170', '联合出品人');
INSERT INTO `movie_people` VALUES ('39', '45', '蔡志军', 'https://p0.pipi.cn/basicdata/25bfd6d7537e7a2c95e5bc4d4873b87183a83.jpg?imageView2/1/w/128/h/170', '联合出品人');
INSERT INTO `movie_people` VALUES ('40', '45', '王柏权', 'https://p0.pipi.cn/basicdata/25bfd6d7807338c696b12de58f921755d71eb.png?imageView2/1/w/128/h/170', '监制 ');
INSERT INTO `movie_people` VALUES ('41', '45', '方凌', 'https://p0.pipi.cn/basicdata/25bfd6d77a38d38d33230fb2847d20e7a39fd.jpg?imageView2/1/w/128/h/170', '监制 ');
INSERT INTO `movie_people` VALUES ('42', '45', '李艳', 'https://p0.pipi.cn/basicdata/25bfd6d7807338c696b12de58f921755d71eb.png?imageView2/1/w/128/h/170', '制片人');
INSERT INTO `movie_people` VALUES ('43', '45', '孙凌宇', 'https://p0.pipi.cn/basicdata/25bfd6d7807338c696b12de58f921755d71eb.png?imageView2/1/w/128/h/170', '制片人');
INSERT INTO `movie_people` VALUES ('44', '45', '郑诚', 'https://p0.pipi.cn/basicdata/fb7386ddd7c338d23cc9fdf20878669e4deaa.jpg?imageView2/1/w/128/h/170', '联合制片人');
INSERT INTO `movie_people` VALUES ('45', '45', '王頔', 'https://p0.pipi.cn/basicdata/25bfd6d7807338c696b12de58f921755d71eb.png?imageView2/1/w/128/h/170', '联合制片人');
INSERT INTO `movie_people` VALUES ('46', '45', '黄琼逸', 'https://p0.pipi.cn/basicdata/25bfd69287af2a02ff338f0b29ccddf20f83c.png?imageView2/1/w/128/h/170', '剪辑指导');

-- ----------------------------
-- Table structure for movie_type_mapping
-- ----------------------------
DROP TABLE IF EXISTS `movie_type_mapping`;
CREATE TABLE `movie_type_mapping` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `type_id` int DEFAULT NULL COMMENT '电影id',
  `movie_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '类型id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of movie_type_mapping
-- ----------------------------
INSERT INTO `movie_type_mapping` VALUES ('2', '10', '1');
INSERT INTO `movie_type_mapping` VALUES ('4', '11', '2');
INSERT INTO `movie_type_mapping` VALUES ('5', '13', '2');
INSERT INTO `movie_type_mapping` VALUES ('6', '2', '3');
INSERT INTO `movie_type_mapping` VALUES ('7', '2', '4');
INSERT INTO `movie_type_mapping` VALUES ('8', '4', '4');
INSERT INTO `movie_type_mapping` VALUES ('14', '5', '7');
INSERT INTO `movie_type_mapping` VALUES ('15', '16', '7');
INSERT INTO `movie_type_mapping` VALUES ('16', '1', '8');
INSERT INTO `movie_type_mapping` VALUES ('17', '1', '1');
INSERT INTO `movie_type_mapping` VALUES ('18', '1', '2');
INSERT INTO `movie_type_mapping` VALUES ('19', '1', '3');
INSERT INTO `movie_type_mapping` VALUES ('20', '1', '4');
INSERT INTO `movie_type_mapping` VALUES ('22', '1', '6');
INSERT INTO `movie_type_mapping` VALUES ('23', '1', '7');
INSERT INTO `movie_type_mapping` VALUES ('24', '1', '8');
INSERT INTO `movie_type_mapping` VALUES ('25', '1', '10');
INSERT INTO `movie_type_mapping` VALUES ('26', '31', '10');
INSERT INTO `movie_type_mapping` VALUES ('125', '4', '5');
INSERT INTO `movie_type_mapping` VALUES ('126', '3', '5');
INSERT INTO `movie_type_mapping` VALUES ('127', '2', '5');
INSERT INTO `movie_type_mapping` VALUES ('128', '1', '5');
INSERT INTO `movie_type_mapping` VALUES ('133', '3', '44');
INSERT INTO `movie_type_mapping` VALUES ('134', '1', '44');
INSERT INTO `movie_type_mapping` VALUES ('135', '3', '45');
INSERT INTO `movie_type_mapping` VALUES ('136', '2', '45');
INSERT INTO `movie_type_mapping` VALUES ('138', '7', '56');
INSERT INTO `movie_type_mapping` VALUES ('139', '1', '56');

-- ----------------------------
-- Table structure for movietype
-- ----------------------------
DROP TABLE IF EXISTS `movietype`;
CREATE TABLE `movietype` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `typename` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '类型名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of movietype
-- ----------------------------
INSERT INTO `movietype` VALUES ('1', '全部');
INSERT INTO `movietype` VALUES ('2', '喜剧');
INSERT INTO `movietype` VALUES ('3', '动画');
INSERT INTO `movietype` VALUES ('4', '剧情');
INSERT INTO `movietype` VALUES ('5', '恐怖');
INSERT INTO `movietype` VALUES ('6', '惊悚');
INSERT INTO `movietype` VALUES ('7', '科幻1');
INSERT INTO `movietype` VALUES ('8', '动作');
INSERT INTO `movietype` VALUES ('9', '悬疑');
INSERT INTO `movietype` VALUES ('10', '犯罪');
INSERT INTO `movietype` VALUES ('11', '冒险');
INSERT INTO `movietype` VALUES ('12', '战争');
INSERT INTO `movietype` VALUES ('13', '奇幻');
INSERT INTO `movietype` VALUES ('14', '运动');
INSERT INTO `movietype` VALUES ('15', '家庭');
INSERT INTO `movietype` VALUES ('16', '古装');
INSERT INTO `movietype` VALUES ('17', '武侠');
INSERT INTO `movietype` VALUES ('18', '西部');
INSERT INTO `movietype` VALUES ('19', '历史');
INSERT INTO `movietype` VALUES ('20', '传记');
INSERT INTO `movietype` VALUES ('21', '歌舞');
INSERT INTO `movietype` VALUES ('22', '黑色电影');
INSERT INTO `movietype` VALUES ('23', '短片');
INSERT INTO `movietype` VALUES ('24', '纪录片');
INSERT INTO `movietype` VALUES ('25', '戏曲');
INSERT INTO `movietype` VALUES ('26', '音乐');
INSERT INTO `movietype` VALUES ('27', '灾难');
INSERT INTO `movietype` VALUES ('28', '青春');
INSERT INTO `movietype` VALUES ('29', '儿童');
INSERT INTO `movietype` VALUES ('30', '其他');
INSERT INTO `movietype` VALUES ('31', '爱情');

-- ----------------------------
-- Table structure for order
-- ----------------------------
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `summary` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '摘要',
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '状态',
  `total_price` decimal(10,0) DEFAULT NULL COMMENT '总价',
  `order_id` int DEFAULT NULL COMMENT '订单id',
  `pay_id` int DEFAULT NULL COMMENT '支付id',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `last_confirm_time` datetime DEFAULT NULL COMMENT '最后确认时间',
  `user_id` int DEFAULT NULL,
  `showtimes_id` int DEFAULT NULL COMMENT '排片id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1962598424 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of order
-- ----------------------------
INSERT INTO `order` VALUES ('1', 'aaa', '已支付', '1000', '1', '1', '2024-01-02 09:00:31', '2024-01-02 12:00:36', '2', '1');
INSERT INTO `order` VALUES ('2', '222', '已支付', '23', '2', '2', '2024-01-02 09:10:00', '2024-01-02 12:10:02', '2', '2');
INSERT INTO `order` VALUES ('3', '222', '未支付', '50', '3', '3', '2024-01-02 12:15:40', null, '2', '3');
INSERT INTO `order` VALUES ('4', '333', '未支付', '233', '4', '4', '2024-01-02 17:30:42', null, '11', '8');
INSERT INTO `order` VALUES ('138526721', null, '未支付', '39', '282959525', null, null, null, '111111', '10');
INSERT INTO `order` VALUES ('234995713', null, '未支付', '26', '342962082', null, null, null, '111111', '10');
INSERT INTO `order` VALUES ('314687490', null, '未支付', '26', '879896555', null, null, null, '111111', '10');
INSERT INTO `order` VALUES ('344047618', null, '未支付', '26', '703345033', null, null, null, '111111', '10');
INSERT INTO `order` VALUES ('377602049', null, '未支付', '26', '653674732', null, null, null, '111111', '10');
INSERT INTO `order` VALUES ('423739393', null, '未支付', '26', '844861153', null, null, null, '111111', '10');
INSERT INTO `order` VALUES ('725729281', null, '未支付', '26', '480106735', null, null, null, '111111', '10');
INSERT INTO `order` VALUES ('822198273', null, '未支付', '26', '161601915', null, null, null, '111111', '10');
INSERT INTO `order` VALUES ('830586882', null, '未支付', '26', '425566382', null, null, null, '111111', '10');
INSERT INTO `order` VALUES ('1178714113', null, '未支付', '26', '896098782', null, null, null, '111111', '10');
INSERT INTO `order` VALUES ('1262600193', null, '未支付', '26', '836277474', null, null, null, '111111', '10');
INSERT INTO `order` VALUES ('1270988801', null, '未支付', '26', '643649602', null, null, null, '111111', '10');
INSERT INTO `order` VALUES ('1459732481', null, '未支付', '26', '430018298', null, null, null, '111111', '10');
INSERT INTO `order` VALUES ('1686224897', null, '未支付', '26', '231821463', null, null, null, '111111', '10');
INSERT INTO `order` VALUES ('1929494530', null, '未支付', '26', '604431667', null, null, null, '111111', '10');
INSERT INTO `order` VALUES ('1946271746', null, '未支付', '26', '836059438', null, null, null, '111111', '10');
INSERT INTO `order` VALUES ('1962598401', null, '未支付', '39', '892715178', null, null, null, '2', '10');
INSERT INTO `order` VALUES ('1962598402', null, '已支付', '26', '959452425', '171872', null, null, '111111', '10');
INSERT INTO `order` VALUES ('1962598403', null, '已支付', '26', '880924957', '503272', null, null, '111111', '10');
INSERT INTO `order` VALUES ('1962598404', null, '已支付', '26', '836070853', '125746', '2024-01-05 00:16:46', '2024-01-05 00:16:46', '111111', '10');
INSERT INTO `order` VALUES ('1962598405', null, '已支付', '26', '355552269', '430719', '2024-01-05 10:48:50', '2024-01-05 10:48:50', '555555', '10');
INSERT INTO `order` VALUES ('1962598406', null, '已支付', '13', '825391393', '347107', '2024-01-05 10:49:55', '2024-01-05 10:49:55', '555555', '10');
INSERT INTO `order` VALUES ('1962598407', null, '未支付', '26', '918228242', null, null, null, '555555', '10');
INSERT INTO `order` VALUES ('1962598408', null, '未支付', '26', '876224318', null, null, '2024-01-05 10:57:00', '555555', '10');
INSERT INTO `order` VALUES ('1962598409', null, '未支付', '13', '873141807', null, null, '2024-01-05 11:50:49', '333333', '10');
INSERT INTO `order` VALUES ('1962598410', null, '已支付', '13', '204185695', '734555', '2024-01-05 12:17:29', '2024-01-05 12:17:26', '123456', '10');
INSERT INTO `order` VALUES ('1962598411', null, '未支付', '13', '939956188', null, null, '2024-01-05 12:17:41', '123456', '10');
INSERT INTO `order` VALUES ('1962598412', null, '已支付', '13', '128904261', '296262', '2024-01-05 12:25:48', '2024-01-05 12:25:44', '123456', '10');
INSERT INTO `order` VALUES ('1962598413', null, '已支付', '13', '871978679', '769050', '2024-01-05 12:55:05', '2024-01-05 12:54:55', '1234567', '10');
INSERT INTO `order` VALUES ('1962598414', null, '已支付', '14', '550443390', '160521', '2024-01-06 08:56:40', '2024-01-06 08:56:38', '333333', '12');
INSERT INTO `order` VALUES ('1962598415', null, '已支付', '14', '738532487', '5264', '2024-01-06 11:25:03', '2024-01-06 11:24:57', '991866', '12');
INSERT INTO `order` VALUES ('1962598416', null, '已支付', '123', '227075353', '913958', '2024-01-06 11:25:40', '2024-01-06 11:25:39', '991866', '23');
INSERT INTO `order` VALUES ('1962598417', null, '未支付', '38', '62022861', null, null, '2024-01-06 11:36:21', '991866', '25');
INSERT INTO `order` VALUES ('1962598418', null, '已支付', '76', '45136365', '819459', '2024-01-06 18:01:25', '2024-01-06 18:01:18', '20240106', '25');
INSERT INTO `order` VALUES ('1962598419', null, '未支付', '47', '661342300', null, null, '2024-01-06 18:17:57', '20240106', '26');
INSERT INTO `order` VALUES ('1962598420', null, '已支付', '47', '899032043', '46202', '2024-01-06 23:26:42', '2024-01-06 23:25:50', '777777', '26');
INSERT INTO `order` VALUES ('1962598421', null, '未支付', '246', '665423048', null, null, '2024-01-06 23:28:32', '777777', '23');
INSERT INTO `order` VALUES ('1962598422', null, '已支付', '156', '244510297', '397772', '2024-01-07 00:08:30', '2024-01-07 00:08:27', '777777', '27');
INSERT INTO `order` VALUES ('1962598423', null, '已支付', '156', '842908718', '983137', '2024-01-07 00:14:43', '2024-01-07 00:14:38', '777777', '27');

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `seat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '座位信息',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价钱',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES ('1', '1', '[1,5]', '50.00');
INSERT INTO `order_detail` VALUES ('2', '1', '[1,6]', '60.00');
INSERT INTO `order_detail` VALUES ('3', '4', '[1,1]', '12.00');
INSERT INTO `order_detail` VALUES ('4', '4', '[1,2]', '16.00');
INSERT INTO `order_detail` VALUES ('5', '4', '[2,2]', '18.00');
INSERT INTO `order_detail` VALUES ('6', '60222409', '[11,5]', '38.00');
INSERT INTO `order_detail` VALUES ('7', '689627619', '[5,3]', '38.00');
INSERT INTO `order_detail` VALUES ('8', '436145374', '[10,8]', '38.00');
INSERT INTO `order_detail` VALUES ('9', '560484298', '[0,0]', '23.00');
INSERT INTO `order_detail` VALUES ('10', '892715178', '[4,7]', '13.00');
INSERT INTO `order_detail` VALUES ('11', '892715178', '[5,7]', '13.00');
INSERT INTO `order_detail` VALUES ('12', '892715178', '[6,7]', '13.00');
INSERT INTO `order_detail` VALUES ('13', '518797462', '[9,9]', '13.00');
INSERT INTO `order_detail` VALUES ('14', '518797462', '[9,8]', '13.00');
INSERT INTO `order_detail` VALUES ('15', '518797462', '[9,7]', '13.00');
INSERT INTO `order_detail` VALUES ('16', '518797462', '[9,6]', '13.00');
INSERT INTO `order_detail` VALUES ('17', '518797462', '[9,5]', '13.00');
INSERT INTO `order_detail` VALUES ('18', '722738021', '[2,6]', '13.00');
INSERT INTO `order_detail` VALUES ('19', '722738021', '[2,7]', '13.00');
INSERT INTO `order_detail` VALUES ('20', '92517466', '[3,7]', '13.00');
INSERT INTO `order_detail` VALUES ('21', '92517466', '[3,6]', '13.00');
INSERT INTO `order_detail` VALUES ('22', '92517466', '[3,5]', '13.00');
INSERT INTO `order_detail` VALUES ('23', '91012203', '[0,9]', '13.00');
INSERT INTO `order_detail` VALUES ('24', '91012203', '[1,9]', '13.00');
INSERT INTO `order_detail` VALUES ('25', '548702203', '[5,8]', '13.00');
INSERT INTO `order_detail` VALUES ('26', '548702203', '[4,8]', '13.00');
INSERT INTO `order_detail` VALUES ('27', '480977279', '[6,6]', '13.00');
INSERT INTO `order_detail` VALUES ('28', '480977279', '[6,7]', '13.00');
INSERT INTO `order_detail` VALUES ('29', '684755858', '[7,6]', '13.00');
INSERT INTO `order_detail` VALUES ('30', '684755858', '[7,7]', '13.00');
INSERT INTO `order_detail` VALUES ('31', '425566382', '[8,6]', '13.00');
INSERT INTO `order_detail` VALUES ('32', '425566382', '[8,7]', '13.00');
INSERT INTO `order_detail` VALUES ('33', '844861153', '[0,4]', '13.00');
INSERT INTO `order_detail` VALUES ('34', '844861153', '[0,5]', '13.00');
INSERT INTO `order_detail` VALUES ('35', '114974008', '[4,4]', '13.00');
INSERT INTO `order_detail` VALUES ('36', '114974008', '[5,4]', '13.00');
INSERT INTO `order_detail` VALUES ('37', '836277474', '[8,4]', '13.00');
INSERT INTO `order_detail` VALUES ('38', '836277474', '[7,4]', '13.00');
INSERT INTO `order_detail` VALUES ('39', '697894644', '[3,4]', '13.00');
INSERT INTO `order_detail` VALUES ('40', '697894644', '[2,4]', '13.00');
INSERT INTO `order_detail` VALUES ('41', '896098782', '[5,9]', '13.00');
INSERT INTO `order_detail` VALUES ('42', '896098782', '[6,9]', '13.00');
INSERT INTO `order_detail` VALUES ('43', '643649602', '[2,9]', '13.00');
INSERT INTO `order_detail` VALUES ('44', '643649602', '[3,9]', '13.00');
INSERT INTO `order_detail` VALUES ('45', '381612638', '[2,2]', '13.00');
INSERT INTO `order_detail` VALUES ('46', '381612638', '[3,2]', '13.00');
INSERT INTO `order_detail` VALUES ('47', '231821463', '[7,9]', '13.00');
INSERT INTO `order_detail` VALUES ('48', '231821463', '[8,9]', '13.00');
INSERT INTO `order_detail` VALUES ('49', '105571671', '[4,9]', '13.00');
INSERT INTO `order_detail` VALUES ('50', '105571671', '[6,8]', '13.00');
INSERT INTO `order_detail` VALUES ('51', '909775350', '[6,3]', '13.00');
INSERT INTO `order_detail` VALUES ('52', '909775350', '[7,3]', '13.00');
INSERT INTO `order_detail` VALUES ('53', '836059438', '[4,2]', '13.00');
INSERT INTO `order_detail` VALUES ('54', '836059438', '[5,2]', '13.00');
INSERT INTO `order_detail` VALUES ('55', '653674732', '[2,8]', '13.00');
INSERT INTO `order_detail` VALUES ('56', '653674732', '[3,8]', '13.00');
INSERT INTO `order_detail` VALUES ('57', '703345033', '[1,4]', '13.00');
INSERT INTO `order_detail` VALUES ('58', '703345033', '[1,5]', '13.00');
INSERT INTO `order_detail` VALUES ('59', '480106735', '[3,3]', '13.00');
INSERT INTO `order_detail` VALUES ('60', '480106735', '[4,3]', '13.00');
INSERT INTO `order_detail` VALUES ('61', '616402199', '[0,6]', '13.00');
INSERT INTO `order_detail` VALUES ('62', '616402199', '[1,6]', '13.00');
INSERT INTO `order_detail` VALUES ('63', '604431667', '[0,1]', '13.00');
INSERT INTO `order_detail` VALUES ('64', '604431667', '[1,1]', '13.00');
INSERT INTO `order_detail` VALUES ('65', '107250462', '[3,1]', '13.00');
INSERT INTO `order_detail` VALUES ('66', '107250462', '[4,1]', '13.00');
INSERT INTO `order_detail` VALUES ('67', '50379798', '[2,5]', '13.00');
INSERT INTO `order_detail` VALUES ('68', '50379798', '[1,7]', '13.00');
INSERT INTO `order_detail` VALUES ('69', '161601915', '[4,5]', '13.00');
INSERT INTO `order_detail` VALUES ('70', '161601915', '[5,5]', '13.00');
INSERT INTO `order_detail` VALUES ('71', '879896555', '[7,8]', '13.00');
INSERT INTO `order_detail` VALUES ('72', '879896555', '[8,8]', '13.00');
INSERT INTO `order_detail` VALUES ('73', '430018298', '[5,7]', '13.00');
INSERT INTO `order_detail` VALUES ('74', '430018298', '[4,7]', '13.00');
INSERT INTO `order_detail` VALUES ('75', '741355604', '[5,6]', '13.00');
INSERT INTO `order_detail` VALUES ('76', '741355604', '[4,6]', '13.00');
INSERT INTO `order_detail` VALUES ('77', '342962082', '[6,2]', '13.00');
INSERT INTO `order_detail` VALUES ('78', '342962082', '[7,2]', '13.00');
INSERT INTO `order_detail` VALUES ('79', '496535359', '[1,3]', '13.00');
INSERT INTO `order_detail` VALUES ('80', '496535359', '[2,3]', '13.00');
INSERT INTO `order_detail` VALUES ('81', '282959525', '[6,5]', '13.00');
INSERT INTO `order_detail` VALUES ('82', '282959525', '[7,5]', '13.00');
INSERT INTO `order_detail` VALUES ('83', '282959525', '[8,5]', '13.00');
INSERT INTO `order_detail` VALUES ('84', '303316500', '[9,4]', '13.00');
INSERT INTO `order_detail` VALUES ('85', '303316500', '[9,3]', '13.00');
INSERT INTO `order_detail` VALUES ('86', '649166984', '[8,3]', '13.00');
INSERT INTO `order_detail` VALUES ('87', '649166984', '[5,3]', '13.00');
INSERT INTO `order_detail` VALUES ('88', '900012500', '[0,8]', '13.00');
INSERT INTO `order_detail` VALUES ('89', '900012500', '[1,8]', '13.00');
INSERT INTO `order_detail` VALUES ('90', '773015009', '[6,4]', '13.00');
INSERT INTO `order_detail` VALUES ('91', '773015009', '[8,2]', '13.00');
INSERT INTO `order_detail` VALUES ('92', '865888905', '[0,7]', '13.00');
INSERT INTO `order_detail` VALUES ('93', '865888905', '[9,2]', '13.00');
INSERT INTO `order_detail` VALUES ('94', '959452425', '[7,1]', '13.00');
INSERT INTO `order_detail` VALUES ('95', '959452425', '[6,1]', '13.00');
INSERT INTO `order_detail` VALUES ('96', '880924957', '[9,1]', '13.00');
INSERT INTO `order_detail` VALUES ('97', '880924957', '[8,1]', '13.00');
INSERT INTO `order_detail` VALUES ('98', '836070853', '[6,0]', '13.00');
INSERT INTO `order_detail` VALUES ('99', '836070853', '[7,0]', '13.00');
INSERT INTO `order_detail` VALUES ('100', '355552269', '[1,2]', '13.00');
INSERT INTO `order_detail` VALUES ('101', '355552269', '[2,1]', '13.00');
INSERT INTO `order_detail` VALUES ('102', '825391393', '[5,1]', '13.00');
INSERT INTO `order_detail` VALUES ('103', '918228242', '[8,0]', '13.00');
INSERT INTO `order_detail` VALUES ('104', '918228242', '[9,0]', '13.00');
INSERT INTO `order_detail` VALUES ('105', '123935506', '[6,9]', '12.30');
INSERT INTO `order_detail` VALUES ('106', '123935506', '[7,9]', '12.30');
INSERT INTO `order_detail` VALUES ('107', '876224318', '[4,0]', '13.00');
INSERT INTO `order_detail` VALUES ('108', '876224318', '[5,0]', '13.00');
INSERT INTO `order_detail` VALUES ('109', '873141807', '[3,0]', '13.00');
INSERT INTO `order_detail` VALUES ('110', '204185695', '[2,0]', '13.00');
INSERT INTO `order_detail` VALUES ('111', '939956188', '[1,0]', '13.00');
INSERT INTO `order_detail` VALUES ('112', '128904261', '[7,4]', '13.00');
INSERT INTO `order_detail` VALUES ('113', '871978679', '[7,1]', '13.00');
INSERT INTO `order_detail` VALUES ('114', '550443390', '[9,9]', '13.50');
INSERT INTO `order_detail` VALUES ('115', '738532487', '[0,8]', '13.50');
INSERT INTO `order_detail` VALUES ('116', '227075353', '[11,9]', '123.00');
INSERT INTO `order_detail` VALUES ('117', '62022861', '[7,4]', '38.00');
INSERT INTO `order_detail` VALUES ('118', '45136365', '[0,8]', '38.00');
INSERT INTO `order_detail` VALUES ('119', '45136365', '[1,8]', '38.00');
INSERT INTO `order_detail` VALUES ('120', '661342300', '[4,9]', '23.60');
INSERT INTO `order_detail` VALUES ('121', '661342300', '[5,9]', '23.60');
INSERT INTO `order_detail` VALUES ('122', '899032043', '[0,9]', '23.60');
INSERT INTO `order_detail` VALUES ('123', '899032043', '[1,9]', '23.60');
INSERT INTO `order_detail` VALUES ('124', '665423048', '[8,9]', '123.00');
INSERT INTO `order_detail` VALUES ('125', '665423048', '[7,9]', '123.00');
INSERT INTO `order_detail` VALUES ('126', '244510297', '[7,5]', '156.00');
INSERT INTO `order_detail` VALUES ('127', '842908718', '[0,10]', '156.00');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '名字',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT 'url',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '介绍',
  `sn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '是否禁止使用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2128764936 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES ('-2031984639', '管理员用户', '/cinema-user', '管理员或者影厅管理', '1');
INSERT INTO `permission` VALUES ('-1436393470', '排片管理', '/showtimes', null, '1');
INSERT INTO `permission` VALUES ('-1272815615', '电影类型管理', '/movietype', null, '1');
INSERT INTO `permission` VALUES ('-1201512446', '电影管理', '/movie', null, '1');
INSERT INTO `permission` VALUES ('4', '菜单', '/menu', null, '1');
INSERT INTO `permission` VALUES ('5', '用户管理', '/user', null, '1');
INSERT INTO `permission` VALUES ('456454646', '角色管理', '/charact', null, '1');
INSERT INTO `permission` VALUES ('492986369', '订单权限', '/order', null, '1');
INSERT INTO `permission` VALUES ('517840894', '影院管理', '/cinema', '操作影院', '1');
INSERT INTO `permission` VALUES ('994148354', '删除影院', '/cinema/del', 'test', '1');
INSERT INTO `permission` VALUES ('1474457602', '影厅权限', '/hall', null, '1');
INSERT INTO `permission` VALUES ('1721917442', '权限管理', '/permission', null, '1');
INSERT INTO `permission` VALUES ('1814192130', '用户管理', '/user', null, '1');
INSERT INTO `permission` VALUES ('2128764931', '影厅管理', '/hall', null, '1');
INSERT INTO `permission` VALUES ('2128764932', '映射管理', '/movie-type-mapping', null, '1');
INSERT INTO `permission` VALUES ('2128764933', '详细订单管理', '/order-detail', null, '1');
INSERT INTO `permission` VALUES ('2128764934', '映射管理', '/role-menu', null, '1');
INSERT INTO `permission` VALUES ('2128764935', '权限映射管理', '/role-permission', null, '1');

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL,
  `menu_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1287299093 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of role_menu
-- ----------------------------
INSERT INTO `role_menu` VALUES ('-1711648767', '1001', '1');
INSERT INTO `role_menu` VALUES ('-1711648766', '1001', '2');
INSERT INTO `role_menu` VALUES ('-1711648765', '1001', '3');
INSERT INTO `role_menu` VALUES ('-1711648764', '1001', '4');
INSERT INTO `role_menu` VALUES ('-1711648763', '1001', '5');
INSERT INTO `role_menu` VALUES ('-1711648762', '1001', '6');
INSERT INTO `role_menu` VALUES ('-1711648761', '1001', '7');
INSERT INTO `role_menu` VALUES ('-1711648760', '1001', '8');
INSERT INTO `role_menu` VALUES ('-1711648759', '1001', '16');
INSERT INTO `role_menu` VALUES ('-1711648758', '1001', '19');
INSERT INTO `role_menu` VALUES ('-1711648757', '1001', '28');
INSERT INTO `role_menu` VALUES ('-1711648756', '1001', '14');
INSERT INTO `role_menu` VALUES ('-1711648755', '1001', '15');
INSERT INTO `role_menu` VALUES ('-1711648754', '1001', '11');
INSERT INTO `role_menu` VALUES ('-1711648753', '1001', '23');
INSERT INTO `role_menu` VALUES ('-1711648752', '1001', '24');
INSERT INTO `role_menu` VALUES ('-1711648751', '1001', '26');
INSERT INTO `role_menu` VALUES ('-1711648750', '1001', '8');
INSERT INTO `role_menu` VALUES ('-1711648749', '1001', '20');
INSERT INTO `role_menu` VALUES ('-1711648748', '1001', '21');
INSERT INTO `role_menu` VALUES ('-1711648747', '1001', '22');
INSERT INTO `role_menu` VALUES ('-1711648746', '1001', '10');
INSERT INTO `role_menu` VALUES ('-1086308350', '1002', '15');
INSERT INTO `role_menu` VALUES ('-1086308349', '1002', '23');
INSERT INTO `role_menu` VALUES ('-1086308348', '1002', '24');
INSERT INTO `role_menu` VALUES ('-1086308347', '1002', '26');
INSERT INTO `role_menu` VALUES ('-1086308346', '1002', '8');
INSERT INTO `role_menu` VALUES ('-1086308345', '1002', '9');
INSERT INTO `role_menu` VALUES ('-1086308344', '1002', '1');
INSERT INTO `role_menu` VALUES ('-1086308343', '1002', '2');
INSERT INTO `role_menu` VALUES ('-1086308342', '1002', '3');
INSERT INTO `role_menu` VALUES ('-1086308341', '1002', '4');
INSERT INTO `role_menu` VALUES ('-1086308340', '1002', '5');
INSERT INTO `role_menu` VALUES ('-1086308339', '1002', '6');
INSERT INTO `role_menu` VALUES ('-1086308338', '1002', '7');
INSERT INTO `role_menu` VALUES ('-1086308337', '1002', '8');
INSERT INTO `role_menu` VALUES ('-1086308336', '1002', '16');
INSERT INTO `role_menu` VALUES ('-1086308335', '1002', '19');
INSERT INTO `role_menu` VALUES ('-1086308334', '1002', '27');

-- ----------------------------
-- Table structure for role_menu_copy
-- ----------------------------
DROP TABLE IF EXISTS `role_menu_copy`;
CREATE TABLE `role_menu_copy` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int NOT NULL,
  `menu_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of role_menu_copy
-- ----------------------------
INSERT INTO `role_menu_copy` VALUES ('1', '1001', '8');
INSERT INTO `role_menu_copy` VALUES ('2', '1001', '9');
INSERT INTO `role_menu_copy` VALUES ('3', '1001', '16');
INSERT INTO `role_menu_copy` VALUES ('4', '1002', '8');
INSERT INTO `role_menu_copy` VALUES ('5', '1002', '16');
INSERT INTO `role_menu_copy` VALUES ('6', '1001', '19');
INSERT INTO `role_menu_copy` VALUES ('7', '1001', '20');
INSERT INTO `role_menu_copy` VALUES ('8', '1001', '21');
INSERT INTO `role_menu_copy` VALUES ('9', '1001', '22');
INSERT INTO `role_menu_copy` VALUES ('51', '1001', '2');
INSERT INTO `role_menu_copy` VALUES ('52', '1001', '14');
INSERT INTO `role_menu_copy` VALUES ('53', '1001', '15');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_id` int DEFAULT NULL,
  `permission_id` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2038079509 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES ('-1822203902', '1002', '-2031984639');
INSERT INTO `role_permission` VALUES ('-1822203901', '1002', '-1436393470');
INSERT INTO `role_permission` VALUES ('-1822203900', '1002', '-1272815615');
INSERT INTO `role_permission` VALUES ('-1822203899', '1002', '-1201512446');
INSERT INTO `role_permission` VALUES ('-1822203898', '1002', '4');
INSERT INTO `role_permission` VALUES ('-1822203897', '1002', '5');
INSERT INTO `role_permission` VALUES ('-1822203896', '1002', '456454646');
INSERT INTO `role_permission` VALUES ('-1822203895', '1002', '492986369');
INSERT INTO `role_permission` VALUES ('-1813815294', '1002', '517840894');
INSERT INTO `role_permission` VALUES ('-1813815293', '1002', '1474457602');
INSERT INTO `role_permission` VALUES ('-1813815292', '1002', '1721917442');
INSERT INTO `role_permission` VALUES ('-1813815291', '1002', '1814192130');
INSERT INTO `role_permission` VALUES ('-1813815290', '1002', '2128764931');
INSERT INTO `role_permission` VALUES ('-1813815289', '1002', '2128764932');
INSERT INTO `role_permission` VALUES ('-1813815288', '1002', '2128764933');
INSERT INTO `role_permission` VALUES ('-1813815287', '1002', '2128764934');
INSERT INTO `role_permission` VALUES ('-1813815286', '1002', '2128764935');
INSERT INTO `role_permission` VALUES ('372920322', '1001', '4');
INSERT INTO `role_permission` VALUES ('372920323', '1001', '2');
INSERT INTO `role_permission` VALUES ('372920324', '1001', '3');
INSERT INTO `role_permission` VALUES ('372920325', '1001', '-2031984639');
INSERT INTO `role_permission` VALUES ('372920326', '1001', '4');
INSERT INTO `role_permission` VALUES ('372920327', '1001', '492986369');
INSERT INTO `role_permission` VALUES ('372920328', '1001', '1721917442');
INSERT INTO `role_permission` VALUES ('372920329', '1001', '1814192130');
INSERT INTO `role_permission` VALUES ('372920330', '1001', '456454646');
INSERT INTO `role_permission` VALUES ('372920331', '1001', '2128764932');
INSERT INTO `role_permission` VALUES ('372920332', '1001', '2128764933');
INSERT INTO `role_permission` VALUES ('372920333', '1001', '2128764934');
INSERT INTO `role_permission` VALUES ('372920334', '1001', '2128764935');
INSERT INTO `role_permission` VALUES ('372920335', '1001', '5');
INSERT INTO `role_permission` VALUES ('372920336', '1001', '-1272815615');
INSERT INTO `role_permission` VALUES ('372920337', '1001', '517840894');
INSERT INTO `role_permission` VALUES ('372920338', '1001', '1474457602');
INSERT INTO `role_permission` VALUES ('372920339', '1001', '2128764931');
INSERT INTO `role_permission` VALUES ('372920340', '1001', '-1201512446');
INSERT INTO `role_permission` VALUES ('372920341', '1001', '-1436393470');

-- ----------------------------
-- Table structure for showtimes
-- ----------------------------
DROP TABLE IF EXISTS `showtimes`;
CREATE TABLE `showtimes` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `movie_id` int DEFAULT NULL COMMENT '电影id',
  `cinema_id` int DEFAULT NULL COMMENT '影院id',
  `hall_id` int DEFAULT NULL COMMENT '影厅id',
  `showtime` time DEFAULT NULL COMMENT '放映时间',
  `showdate` date DEFAULT NULL COMMENT '放映日期',
  `sale` decimal(10,2) DEFAULT NULL COMMENT '售价',
  `seat` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '座位',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of showtimes
-- ----------------------------
INSERT INTO `showtimes` VALUES ('1', '1', '1', '2', '08:23:40', '2024-01-03', '12.00', '[[1, 0, 1, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `showtimes` VALUES ('2', '1', '1', '2', '18:29:09', '2024-01-02', '13.00', '[[1, 0, 1, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `showtimes` VALUES ('3', '1', '1', '3', '10:50:47', '2024-01-01', '15.00', '[[1, 0, 1, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `showtimes` VALUES ('4', '1', '1', '1', '11:15:24', '2024-01-01', '16.00', '[[1, 0, 1, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `showtimes` VALUES ('5', '2', '1', '3', '18:38:03', '2024-01-03', '23.00', '[[1, 0, 1, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `showtimes` VALUES ('6', '1', '1', '3', '23:59:53', '2023-12-31', '30.00', '[[1, 0, 1, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `showtimes` VALUES ('7', '1', '1', '3', '23:00:21', '2023-12-31', '30.00', '[[1, 0, 1, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `showtimes` VALUES ('8', '1', '2', '20', '16:50:38', '2024-01-02', '12.00', '[[1, 0, 1, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `showtimes` VALUES ('9', '1', '2', '19', '16:50:43', '2024-01-03', '12.00', '[[1, 0, 1, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `showtimes` VALUES ('10', '3', '1', '2', '10:04:00', '2024-01-06', '13.00', '[[1,1,1,1,1,1,1,1,1,1],[1,1,1,1,1,1,1,1,1,1],[1,1,1,1,1,1,1,1,1,1],[1,1,1,1,1,1,1,1,1,1],[1,1,1,1,1,1,1,1,1,1],[1,1,1,1,1,1,1,1,1,1],[1,1,1,1,1,1,1,1,1,1],[1,1,1,1,1,1,1,1,1,1],[1,1,0,1,1,1,1,1,1,1],[1,1,1,1,1,1,0,1,1,1]]');
INSERT INTO `showtimes` VALUES ('11', '2', '1', '1', '10:07:00', '2024-01-03', '12.30', '[[1, 0, 1, 1, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]');
INSERT INTO `showtimes` VALUES ('12', '2', '1', '1', '08:02:00', '2024-01-11', '13.50', '[[0,0,0,0,0,0,0,0,1,-1],[0,0,0,0,0,0,0,0,0,-1],[0,0,0,0,0,0,0,0,0,-1],[0,0,0,0,0,0,0,0,0,-1],[0,0,0,0,0,0,0,0,0,-1],[0,0,0,0,0,0,0,0,0,-1],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,1],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[1,0,-1,\"p30\",0,0,0,0,0,0]]');
INSERT INTO `showtimes` VALUES ('13', null, '1', null, '17:10:15', '2024-01-04', null, null);
INSERT INTO `showtimes` VALUES ('14', null, null, null, '17:19:53', '2024-01-04', null, null);
INSERT INTO `showtimes` VALUES ('15', '2', '1', null, '17:22:45', '2024-01-06', null, null);
INSERT INTO `showtimes` VALUES ('16', null, '1', null, '17:23:07', '2024-01-04', null, null);
INSERT INTO `showtimes` VALUES ('17', '1', '1', '2', '06:00:00', '2024-01-05', '12.30', null);
INSERT INTO `showtimes` VALUES ('18', '2', '1', '1', '05:01:00', '2024-01-06', '12.30', null);
INSERT INTO `showtimes` VALUES ('19', '3', '1', '1', '00:00:00', '2024-01-03', '12.00', null);
INSERT INTO `showtimes` VALUES ('20', '3', '1', '1', '14:00:00', '2024-01-04', '12.00', null);
INSERT INTO `showtimes` VALUES ('21', '1', '1', '1', '14:00:00', '2024-01-03', '15.00', null);
INSERT INTO `showtimes` VALUES ('22', '1', '10422', '1784872973', '00:00:00', '2024-01-07', '15.00', null);
INSERT INTO `showtimes` VALUES ('23', '1', '1', '1', '00:00:00', '2024-01-17', '123.00', '[[0,0,0,0,0,0,0,0,0,-1],[0,0,0,0,0,0,0,0,0,-1],[0,0,0,0,0,0,0,0,0,-1],[0,0,0,0,0,0,0,0,0,-1],[0,0,0,0,0,0,0,0,0,-1],[0,0,0,0,0,0,0,0,0,-1],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,1],[0,0,0,0,0,0,0,0,0,1],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,1],[1,0,-1,\"p30\",0,0,0,0,0,0]]');
INSERT INTO `showtimes` VALUES ('24', '1', '10422', '1784872973', '01:00:00', '2024-01-10', '152.00', '[[0,0,0,0,0,-1,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0]]');
INSERT INTO `showtimes` VALUES ('25', '1', '10423', '1784872974', '16:00:00', '2024-01-11', '38.00', '[[0,0,0,0,0,0,0,0,1],[0,0,0,0,0,0,0,0,1],[0,0,0,0,0,0,0,0,0],[-1,-1,-1,-1,-1,-1,-1,-1,-1],[0,0,0,0,-1,0,0,0,0],[0,0,0,0,-1,0,0,0,0],[0,0,0,0,-1,0,0,0,0],[0,0,0,0,1,0,0,0,0]]');
INSERT INTO `showtimes` VALUES ('26', '46', '10410', '1784872975', '01:00:00', '2024-01-12', '23.60', '[[0,0,0,0,0,0,0,0,0,1],[0,0,0,0,0,0,0,0,0,1],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,1],[0,0,0,0,0,0,0,0,0,1],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0]]');
INSERT INTO `showtimes` VALUES ('27', '51', '10424', '1784872977', '00:00:00', '2024-01-17', '156.00', '[[0,-1,0,-1,0,0,0,-1,0,-1,1],[0,-1,0,-1,0,0,0,-1,0,-1,0],[0,-1,0,-1,-1,0,-1,-1,0,-1,0],[0,0,0,-1,-1,0,-1,-1,0,-1,0],[0,0,0,-1,-1,0,-1,-1,0,-1,0],[0,-1,0,-1,-1,0,-1,-1,0,-1,0],[0,-1,0,-1,-1,0,-1,-1,0,0,0],[0,-1,0,-1,-1,1,-1,-1,0,0,0]]');
INSERT INTO `showtimes` VALUES ('28', '45', '10410', '1784872975', '07:06:00', '2024-01-13', '12.30', '[[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0],[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]]');

-- ----------------------------
-- Table structure for showtimesdetail
-- ----------------------------
DROP TABLE IF EXISTS `showtimesdetail`;
CREATE TABLE `showtimesdetail` (
  `id` int NOT NULL COMMENT 'id',
  `showtimes_id` int DEFAULT NULL COMMENT '排片id',
  `intro` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '简介',
  `notes` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '下单须知',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of showtimesdetail
-- ----------------------------

-- ----------------------------
-- Table structure for tag_movie
-- ----------------------------
DROP TABLE IF EXISTS `tag_movie`;
CREATE TABLE `tag_movie` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tag` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci COMMENT '主要是用于显示是tab图标eg3DMAX?2DMAX',
  `movieId` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_520_ci;

-- ----------------------------
-- Records of tag_movie
-- ----------------------------
INSERT INTO `tag_movie` VALUES ('1', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEUAAAAZCAMAAABQHxOXAAAANlBMVEUAAAAAAAAAhP////9Epf93vf+73v8RjP/d7//u9/8ilP8znf/M5v9mtf+Zzv+q1v+Ixv9Vrf8Ox5ywAAAAAnRSTlMzAIL4qAgAAAEtSURBVDjL7ZPNjsMwCIRbsI3/4iTv/7I7g9NDrb1ttOqhSEUMxR84JA+5wZ63UB5fyv9QQqh/psSiqkMkKawMQwZBpO4yBcsOL5Kmuotl7Qsl5SOrNsmaCUp+MJ/QPG4FAlVVJ1Q6sruWus4icqpWYX/LjIwhNNlxgmVomTNtGEb1/OXpJhQG1QAkfZgOtnEUIgM8oSJXZqV4H29qTqkewuWu8WDjzftUBrDdr7ZSrBMih1+/0x/TpaEZJzlCwDXiHAEaTVZKy9rtupWMV2O6GLk8OMJpTLeiab2RZ0uMyOJf7mp4t1ShN0xQbGhHBiJgZt8xfmWhRHWTqrQMnK+4Qjebr82OTJql1q+9be+UGtzE6JunXpqRSQv1erkrpNcY9Gd/jV/KG+X5uMF+APHWE1liaIDvAAAAAElFTkSuQmCC', '2');
INSERT INTO `tag_movie` VALUES ('2', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEUAAAAZCAMAAABQHxOXAAAANlBMVEUAAAAAAAAAhP////9Epf93vf8RjP+73v/d7//u9/9mtf8ilP/M5v8znf+q1v+Zzv+Ixv9Vrf8pmp8MAAAAAnRSTlMzAIL4qAgAAAExSURBVDjL7VPLcsMgDEwQCMTD4P//2e6K9FD72Eynh2jGghVotZLtR3iDPd/C8viw/AmLxqi/ZkkCqyFkrjZ1RxJxfx3z2hKZWIbIEbRIv7AcOZlYCEUKibInlhOY6WoAuNVEdmZH9BBr97lk5rK+IrUFxRYYNsjIwzDFtqYKMSLntaOYJqNRJEIGfdwOVimFlBGepCG8IleWhejyfHXffAtXuqTFwtXVNm5gCPT7dMfpCpe33+nXdnlKQSYlRLSRtgRgr3ebC6vs2czvwnSJM5lwJKcxPEzyvSO1dHa2gdNVkOXVMnGFAtMpHRGA2MX8HeOxC4sP0Sr00AqLFAEr8ND92RyI5LAn11/vrV46iilF5QcMGzsCI+ZOw4gNa2PjgH5Hgf/13/hh+cnyfLzBvgAoTBNzukOVAAAAAABJRU5ErkJggg==', '4');
INSERT INTO `tag_movie` VALUES ('3', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEUAAAAZCAMAAABQHxOXAAAANlBMVEUAAAAAAAAAhP////9Epf93vf+73v8RjP/d7//u9/8ilP8znf/M5v9mtf+Zzv+q1v+Ixv9Vrf8Ox5ywAAAAAnRSTlMzAIL4qAgAAAEtSURBVDjL7ZPNjsMwCIRbsI3/4iTv/7I7g9NDrb1ttOqhSEUMxR84JA+5wZ63UB5fyv9QQqh/psSiqkMkKawMQwZBpO4yBcsOL5Kmuotl7Qsl5SOrNsmaCUp+MJ/QPG4FAlVVJ1Q6sruWus4icqpWYX/LjIwhNNlxgmVomTNtGEb1/OXpJhQG1QAkfZgOtnEUIgM8oSJXZqV4H29qTqkewuWu8WDjzftUBrDdr7ZSrBMih1+/0x/TpaEZJzlCwDXiHAEaTVZKy9rtupWMV2O6GLk8OMJpTLeiab2RZ0uMyOJf7mp4t1ShN0xQbGhHBiJgZt8xfmWhRHWTqrQMnK+4Qjebr82OTJql1q+9be+UGtzE6JunXpqRSQv1erkrpNcY9Gd/jV/KG+X5uMF+APHWE1liaIDvAAAAAElFTkSuQmCC', '5');
INSERT INTO `tag_movie` VALUES ('4', '', '1');
INSERT INTO `tag_movie` VALUES ('5', '', '3');
INSERT INTO `tag_movie` VALUES ('6', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEUAAAAZCAMAAABQHxOXAAAANlBMVEUAAAAAAAAAhP////9Epf93vf8RjP+73v/d7//u9/9mtf8ilP/M5v8znf+q1v+Zzv+Ixv9Vrf8pmp8MAAAAAnRSTlMzAIL4qAgAAAExSURBVDjL7VPLcsMgDEwQCMTD4P//2e6K9FD72Eynh2jGghVotZLtR3iDPd/C8viw/AmLxqi/ZkkCqyFkrjZ1RxJxfx3z2hKZWIbIEbRIv7AcOZlYCEUKibInlhOY6WoAuNVEdmZH9BBr97lk5rK+IrUFxRYYNsjIwzDFtqYKMSLntaOYJqNRJEIGfdwOVimFlBGepCG8IleWhejyfHXffAtXuqTFwtXVNm5gCPT7dMfpCpe33+nXdnlKQSYlRLSRtgRgr3ebC6vs2czvwnSJM5lwJKcxPEzyvSO1dHa2gdNVkOXVMnGFAtMpHRGA2MX8HeOxC4sP0Sr00AqLFAEr8ND92RyI5LAn11/vrV46iilF5QcMGzsCI+ZOw4gNa2PjgH5Hgf/13/hh+cnyfLzBvgAoTBNzukOVAAAAAABJRU5ErkJggg==', '6');
INSERT INTO `tag_movie` VALUES ('7', ' ', '7');
INSERT INTO `tag_movie` VALUES ('8', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEUAAAAZCAMAAABQHxOXAAAANlBMVEUAAAAAAAAAhP////9Epf93vf+73v8RjP/d7//u9/8ilP8znf/M5v9mtf+Zzv+q1v+Ixv9Vrf8Ox5ywAAAAAnRSTlMzAIL4qAgAAAEtSURBVDjL7ZPNjsMwCIRbsI3/4iTv/7I7g9NDrb1ttOqhSEUMxR84JA+5wZ63UB5fyv9QQqh/psSiqkMkKawMQwZBpO4yBcsOL5Kmuotl7Qsl5SOrNsmaCUp+MJ/QPG4FAlVVJ1Q6sruWus4icqpWYX/LjIwhNNlxgmVomTNtGEb1/OXpJhQG1QAkfZgOtnEUIgM8oSJXZqV4H29qTqkewuWu8WDjzftUBrDdr7ZSrBMih1+/0x/TpaEZJzlCwDXiHAEaTVZKy9rtupWMV2O6GLk8OMJpTLeiab2RZ0uMyOJf7mp4t1ShN0xQbGhHBiJgZt8xfmWhRHWTqrQMnK+4Qjebr82OTJql1q+9be+UGtzE6JunXpqRSQv1erkrpNcY9Gd/jV/KG+X5uMF+APHWE1liaIDvAAAAAElFTkSuQmCC', '8');
INSERT INTO `tag_movie` VALUES ('9', ' ', '10');
INSERT INTO `tag_movie` VALUES ('10', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEUAAAAZCAMAAABQHxOXAAAANlBMVEUAAAAAAAAAhP////9Epf93vf8RjP+73v/d7//u9/9mtf8ilP/M5v8znf+q1v+Zzv+Ixv9Vrf8pmp8MAAAAAnRSTlMzAIL4qAgAAAExSURBVDjL7VPLcsMgDEwQCMTD4P//2e6K9FD72Eynh2jGghVotZLtR3iDPd/C8viw/AmLxqi/ZkkCqyFkrjZ1RxJxfx3z2hKZWIbIEbRIv7AcOZlYCEUKibInlhOY6WoAuNVEdmZH9BBr97lk5rK+IrUFxRYYNsjIwzDFtqYKMSLntaOYJqNRJEIGfdwOVimFlBGepCG8IleWhejyfHXffAtXuqTFwtXVNm5gCPT7dMfpCpe33+nXdnlKQSYlRLSRtgRgr3ebC6vs2czvwnSJM5lwJKcxPEzyvSO1dHa2gdNVkOXVMnGFAtMpHRGA2MX8HeOxC4sP0Sr00AqLFAEr8ND92RyI5LAn11/vrV46iilF5QcMGzsCI+ZOw4gNa2PjgH5Hgf/13/hh+cnyfLzBvgAoTBNzukOVAAAAAABJRU5ErkJggg==', '53');
INSERT INTO `tag_movie` VALUES ('11', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAEUAAAAZCAMAAABQHxOXAAAANlBMVEUAAAAAAAAAhP////9Epf93vf+73v8RjP/d7//u9/8ilP8znf/M5v9mtf+Zzv+q1v+Ixv9Vrf8Ox5ywAAAAAnRSTlMzAIL4qAgAAAEtSURBVDjL7ZPNjsMwCIRbsI3/4iTv/7I7g9NDrb1ttOqhSEUMxR84JA+5wZ63UB5fyv9QQqh/psSiqkMkKawMQwZBpO4yBcsOL5Kmuotl7Qsl5SOrNsmaCUp+MJ/QPG4FAlVVJ1Q6sruWus4icqpWYX/LjIwhNNlxgmVomTNtGEb1/OXpJhQG1QAkfZgOtnEUIgM8oSJXZqV4H29qTqkewuWu8WDjzftUBrDdr7ZSrBMih1+/0x/TpaEZJzlCwDXiHAEaTVZKy9rtupWMV2O6GLk8OMJpTLeiab2RZ0uMyOJf7mp4t1ShN0xQbGhHBiJgZt8xfmWhRHWTqrQMnK+4Qjebr82OTJql1q+9be+UGtzE6JunXpqRSQv1erkrpNcY9Gd/jV/KG+X5uMF+APHWE1liaIDvAAAAAElFTkSuQmCC', '54');

-- ----------------------------
-- Table structure for ticket
-- ----------------------------
DROP TABLE IF EXISTS `ticket`;
CREATE TABLE `ticket` (
  `id` int NOT NULL,
  `ticket_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '取票码',
  `order_id` int DEFAULT NULL COMMENT '订单id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of ticket
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int NOT NULL COMMENT 'id',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '用户名',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `salt` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `age` int DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `head_img` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_see_record
-- ----------------------------
DROP TABLE IF EXISTS `user_see_record`;
CREATE TABLE `user_see_record` (
  `id` int NOT NULL COMMENT 'id',
  `user_id` int DEFAULT NULL,
  `movie_id` int DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC;

-- ----------------------------
-- Records of user_see_record
-- ----------------------------
