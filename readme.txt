�������ƣ�	QQ״̬���
Ӣ������		qqstatus
�汾��		v1.0.0
���ܣ�		1������趨�õ�QQ����״̬�����趨���������䣬����ÿ��1�㷢���ʼ������״̬ͳ��ͼ
			2�������qq��109719189��QQ���������߱�������ѣ��ȸ�Q���߻�����ʱ�������ʼ���18957689879@189.cn
			3�����ѯ��δ�������趨ʱ�������Զ���ת������ҳ�棨/input.jsp����
��ڣ�		1��ͼƬͳ�Ʋ鿴��ַ��/showpic.action?qqId=109719189&date=2011-10-15
			2������ͳ�Ʋ鿴��ַ��/showtext.action?qqId=109719189&date=2011-10-15
			3�����ò�����ַ��/input.jsp
			
============================================================================
�ļ��ṹ˵����
	/qqstatus-core
		Ϊqqstatus-webģ���ṩjar�ļ�
	/qqstatus-web
		����ҳ��
	/qqstatus-web/src/main/resources
		hibernate/hibernate-main.cfg.xml	hibernate�����ļ�
		struts.xml							struts2�����ļ�
		struts2/**							struts2�����ļ�
		log4j.properties					log4j�����ļ�
		qqstatus.properties					��Ӧ�������ļ��������ü�⿪�ء������ʱ�䣬
			ͳ��ͼƬ����ʾ��С�����ĵ�ַ������ͼƬ���ļ���С��QQ���ٹ��ܿ��ء����ټ����
			����ͳ���ʼ��ļ���������ӳ�ʱ�䣨δʹ�ã��������ʼ����˺š����루δʹ�ã�
		
============================================================================
��Ʒ�����
	��Ѷ�ṩ��ʾ����״̬��ͼ�꣬��ȡ���ļ�����Ԥ����ȡ������״̬ͼ�����Ƚϣ��ļ���С��ȷ������״̬д��log.status
���ݿ����˵������ṹ�ο�/qqstatus-web/src/main/resources/cn/com/lazyhome/qqstatus/bean/Concern.hbm.xml��
	�˺ţ�rainbow
	���룺d~12345678ch
	
	��		concern_		�����QQ���б�
	�ֶΣ�	id				int			int��������
			qqId			string		�����QQ��
			delay			int			��ʱ��δʹ��
			mail			string		��������
			receivedTime	int			����ʱ�䣨δʹ�ã���ԭ�����趨��ʱ�����ʼ����̶ֹ�Ϊ�賿1��
			createTime		timestamp	����ʱ��
			createIp		string		�����˵�IP
			
	��		log_			����¼����¼ÿ��QQ��״̬��Ϣ
	�ֶΣ�	id				int			int��������
			qqId			string		�����QQ��
			status			int			����״̬��1�����ߣ�2�����߻�����
			file			blob		����״̬��ͼ��
			fileSize		int			ͼ���ļ���С
			time			timestamp	���ʱ��
			
============================================================================
ʱ�䣺		2011-11-15 22:15
����:		rainbow
end

