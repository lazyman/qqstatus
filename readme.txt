程序名称：	QQ状态检测
英文名：		qqstatus
版本：		v1.0.0
功能：		1、监测设定好的QQ在线状态，若设定了提醒邮箱，将于每天1点发送邮件昨天的状态统计图
			2、并针对qq：109719189的QQ，做上下线变更的提醒，既该Q上线或下线时都发送邮件到18957689879@189.cn
			3、需查询的未做配置设定时，将会自动跳转到配置页面（/input.jsp）。
入口：		1、图片统计查看地址：/showpic.action?qqId=109719189&date=2011-10-15
			2、文字统计查看地址：/showtext.action?qqId=109719189&date=2011-10-15
			3、配置参数地址：/input.jsp
			
============================================================================
文件结构说明：
	/qqstatus-core
		为qqstatus-web模块提供jar文件
	/qqstatus-web
		访问页面
	/qqstatus-web/src/main/resources
		hibernate/hibernate-main.cfg.xml	hibernate配置文件
		struts.xml							struts2配置文件
		struts2/**							struts2配置文件
		log4j.properties					log4j配置文件
		qqstatus.properties					本应用配置文件，可配置监测开关、监测间隔时间，
			统计图片的显示大小、监测的地址、在线图片的文件大小，QQ跟踪功能开关、跟踪间隔，
			发送统计邮件的间隔、发送延迟时间（未使用），发送邮件的账号、密码（未使用）
		
============================================================================
设计分析：
	腾讯提供显示在线状态的图标，提取该文件，与预先提取的在线状态图标做比较（文件大小）确定在线状态写入log.status
数据库设计说明，表结构参看/qqstatus-web/src/main/resources/cn/com/lazyhome/qqstatus/bean/Concern.hbm.xml：
	账号：rainbow
	密码：d~12345678ch
	
	表：		concern_		需监测的QQ号列表
	字段：	id				int			int自增主键
			qqId			string		需监测的QQ号
			delay			int			延时，未使用
			mail			string		接收邮箱
			receivedTime	int			接收时间（未使用），原设想设定几时发送邮件，现固定为凌晨1点
			createTime		timestamp	创建时间
			createIp		string		创建人的IP
			
	表：		log_			监测记录，记录每个QQ的状态信息
	字段：	id				int			int自增主键
			qqId			string		需监测的QQ号
			status			int			在线状态：1，在线；2，离线或隐身
			file			blob		在线状态的图标
			fileSize		int			图标文件大小
			time			timestamp	监测时间
			
============================================================================
时间：		2011-11-15 22:15
作者:		rainbow
end

