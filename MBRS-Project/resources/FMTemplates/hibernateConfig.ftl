<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
                                         "http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
	<session-factory >
		<property name="hibernate.connection.driver_class">com.mysql.jdbc.Driver</property>
		<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/mbrs</property>
		<property name="hibernate.connection.username">root</property>
		<property name="hibernate.connection.password">nikola</property>
  
  
		<property name="hibernate.dialect">org.hibernate.dialect.MySQL5Dialect</property>	
		<property name="hibernate.current_session_context_class">thread</property>
		<property name="hibernate.connection.CharSet">utf8</property>
		<property name="hibernate.connection.characterEncoding">utf8</property>
		<property name="hibernate.connection.useUnicode">true</property>
		<property name="hibernate.show_sql">true</property>
		<property name="hibernate.hbm2ddl.auto">create</property> 
 		
 <#list classes as class>
 		<mapping class="${class.typePackage}.${class.name}"/>	  
 </#list>
 	</session-factory>
</hibernate-configuration>