package org.demo.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 可以根据表名创建实体
 * @ClassName: CreateEntity
 * @author WF
 * @date 2018年12月05日 上午9:27:46
 *
 */
public class CreateEntity{
	
	private static String author = "超享";
	private static String tableName = "T_DISH_FEATURES_OPTION";// 表名
	private static String javaType = "DishFeaturesOption";// 表名对应类名

	/** 工作空间路径. **/
//	private static String workspace = "D:/jee-oxygen/Workspace/eclipse-workspace/blasting-parent/blasting-common";
//	private static String controllerWorkspace = "D:/jee-oxygen/Workspace/eclipse-workspace/blasting-parent/blasting-api";
//	private static String mapperWorkspace = "D:/jee-oxygen/Workspace/eclipse-workspace/blasting-parent/blasting-common-resources";

	/** 工作空间路径.QZ.IDEA **/
	private static String workspace = "D:/IDEA/IdeaProjects/smartSG-parent/smartSG-common";
	private static String controllerWorkspace = "D:/IDEA/IdeaProjects/smartSG-parent/smartSG-bs";
	private static String mapperWorkspace = "D:/IDEA/IdeaProjects/smartSG-parent/smartSG-common-resources";
	
	/** 工作空间路径.YH.IDE **/
//	private static String workspace = "F:/workspace3/smartSG-parent/smartSG-common";
//	private static String controllerWorkspace = "F:/workspace3/smartSG-parent/smartSG-bs";
//	private static String mapperWorkspace = "F:/workspace3/smartSG-parent/smartSG-common-resources";

	/** 源文件路径. **/
	private static String resourcePath = "/src/main/java/";
	/** 包名. **/
//	private static String commonPackageName = "com.system";
//	private static String controllerBasePackageName = "com.system";

	private static String commonPackageName = "com.sg";
	private static String controllerBasePackageName = "com.sg";

	private static String modelPackageName = commonPackageName + ".model";
	//private static String controllerPackageName = controllerBasePackageName + ".restcontroller";//api
	private static String controllerPackageName = controllerBasePackageName + ".controller";//bs
	private static String managerPackageName = commonPackageName + ".service";
	private static String daoPackageName = commonPackageName + ".dao";

	//以上根据实际修改

	/** 生成的文件路径. **/
	private static String domainFilePath = "";
	private static String controllerFilePath = "";
	private static String managerFilePath = "";
	private static String daoFilePath = "";
	private static Date date = new Date();
	public static void main(String[] args) {
		javaType = tableName;
		if(javaType.startsWith("T_")) {
			javaType = javaType.substring(2);
		}
		javaType = getJavaName(javaType);
		javaType = firstUpper(javaType);
		System.out.println(javaType);
		domainFilePath = workspace + resourcePath + modelPackageName.replaceAll("\\.", "/") + "/";
		controllerFilePath = controllerWorkspace + resourcePath + controllerPackageName.replaceAll("\\.", "/") + "/";
		managerFilePath = workspace + resourcePath + managerPackageName.replaceAll("\\.", "/") + "/";
		daoFilePath = workspace + resourcePath + daoPackageName.replaceAll("\\.", "/") + "/";

		createEntity();
		createController();
		createManager();
		createDAO();
		createMapper();

	}

	public static void createMapper() {
		Connection conn = JdbcUtil.getConnection(JdbcUtil.MYSQL);
		String sql = "select * from " + tableName + " where 1=2";

		StringBuffer sb = new StringBuffer();
		String modelName = modelPackageName + "." + javaType;
		String namespace = javaType + "Dao";

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
		sb.append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \n");
		sb.append("\t\"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n");
		sb.append("<mapper namespace=\""+namespace+"\">\n");
		sb.append("\t<resultMap id=\"BaseResultMap\" type=\""+modelName+"\">\n");

		StringBuffer columnList = new StringBuffer();
		StringBuffer insertColumntList = new StringBuffer();
		StringBuffer insertFieldList = new StringBuffer();
		StringBuffer updateList = new StringBuffer();
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();//结果集列数
			int j = 0;
			int num = 6;
			for (int i = 0; i < columnCount; i++) {
				String name = metaData.getColumnLabel(i+1);//列名
				String javaName = getJavaName(name);
				if(name.equalsIgnoreCase("eid")) {
					sb.append("\t\t<id column=\"EID\" property=\"eid\" />\n");
				} else {
					sb.append("\t\t<result column=\""+name+"\" property=\""+javaName+"\" />\n");

					updateList.append("\t\t\t<if test=\""+javaName+" != null\">\n");
					updateList.append("\t\t\t\t"+name+" = #{"+javaName+"},\n");
					updateList.append("\t\t\t</if>\n");

				}
				if(j == 0) {
					insertColumntList.append("\t\t\t");
					insertFieldList.append("\t\t\t");
					columnList.append("\t\t");
				}
				if("CREATE_TIME".equals(name)) {
					columnList.append("DATE_FORMAT(CREATE_TIME,'%Y-%m-%d') CREATE_TIME");
				} else if("UPDATE_TIME".equals(name)) {
					columnList.append("DATE_FORMAT(UPDATE_TIME,'%Y-%m-%d %H:%i:%s') UPDATE_TIME");
				} else {
					columnList.append(name);
				}
				if(!name.equalsIgnoreCase("eid")) {
					insertColumntList.append(name);
					insertFieldList.append("#{" + javaName + "}");
					if(i < columnCount - 1) {
						insertColumntList.append(", ");
						insertFieldList.append(", ");
					}
				}
				if(i < columnCount - 1) {
					columnList.append(", ");
				}
				j++;
				if(j == num) {
					j = 0;
					columnList.append("\n");
					insertColumntList.append("\n");
					insertFieldList.append("\n");
				}
            }
		} catch (Exception e) {
		}
		sb.append("\t</resultMap>\n\n");
		sb.append("\t<sql id=\"Base_Column_List\">\n");
		sb.append(columnList.toString() + "\n");
		sb.append("\t</sql>\n\n");

		sb.append("\t<insert id=\"insert\" parameterType=\""+modelName+"\" useGeneratedKeys=\"true\" keyProperty=\"eid\">\n");
		sb.append("\t\tinsert into "+tableName+" (\n");
		sb.append(insertColumntList.toString() + "\n");
		sb.append("\t\t) values (\n");
		sb.append(insertFieldList.toString() + "\n");
		sb.append("\t\t)\n");
		sb.append("\t</insert>\n\n");

		sb.append("\t<select id=\"findById\" parameterType=\"java.lang.Long\" resultMap=\"BaseResultMap\">\n");
		sb.append("\t\tselect\n");
		sb.append("\t\t<include refid=\"Base_Column_List\" />\n");
		sb.append("\t\tfrom "+tableName+"\n");
		sb.append("\t\twhere EID = #{eid}\n");
		sb.append("\t</select>\n\n");

		sb.append("\t<update id=\"update\" parameterType=\""+modelName+"\">\n");
		sb.append("\t\tupdate "+tableName+"\n");
		sb.append("\t\t<set>\n");
		sb.append(updateList.toString());
		sb.append("\t\t</set>\n");
		sb.append("\t\twhere EID = #{eid}\n");
		sb.append("\t</update>\n\n");

		sb.append("</mapper>");

		String mapperFilePath = mapperWorkspace + "/src/main/resources/mapper/";
		String fileName = mapperFilePath + javaType + "Mapper.xml";
		writeToFile(fileName, sb);
	}


	public static void createController() {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + controllerPackageName + ";\n\n");
		sb.append("import javax.inject.Inject;\n\n");
		sb.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
		sb.append("import org.springframework.web.bind.annotation.RestController;\n\n");

		sb.append("import "+modelPackageName+"."+javaType+";\n");
		sb.append("import "+managerPackageName+"."+javaType+"Manager;\n\n");

		sb.append("import javacommon.base.BaseManager;\n");
		sb.append("import javacommon.base.BaseSpringController;\n\n");
		sb.append("/**\n");
		sb.append(" *\n");
//		sb.append(" * @ClassName: "+javaType+"Controller\n");
		sb.append(" * @author " + author + "\n");
		sb.append(" * @date "+format(date, "yyyy-MM-dd HH:mm:ss")+"\n");
		sb.append(" *\n");
		sb.append(" */\n");

		String path = firstLowe(javaType);
		sb.append("@RestController\n");
		sb.append("@RequestMapping(\"/"+path+"\")\n");

		sb.append("public class "+javaType+"Controller extends BaseBsController<"+javaType+"> {\n\n");

		sb.append("\t@Inject\n");
		sb.append("\tprivate "+javaType+"Manager "+path+"Manager;\n");
		sb.append("\t@Override\n");
		sb.append("\tprotected BaseManager<"+javaType+"> getManager() {\n");
		sb.append("\t\treturn "+path+"Manager;\n");
		sb.append("\t}\n\n");
		sb.append("}");
		String fileName = controllerFilePath + javaType + "Controller.java";
		writeToFile(fileName, sb);
	}
	public static void createManager() {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + managerPackageName + ";\n\n");
		sb.append("import javax.inject.Inject;\n\n");
		sb.append("import org.springframework.stereotype.Service;\n\n");

		sb.append("import "+daoPackageName+"."+javaType+"Dao;\n");
		sb.append("import "+modelPackageName+"."+javaType+";\n\n");

		sb.append("import javacommon.base.BaseManager;\n");
		sb.append("import javacommon.base.BaseMybatis3Dao;\n\n");

		sb.append("/**\n");
		sb.append(" *\n");
//		sb.append(" * @ClassName: "+javaType+"Manager\n");
		sb.append(" * @author " + author + "\n");
		sb.append(" * @date "+format(date, "yyyy-MM-dd HH:mm:ss")+"\n");
		sb.append(" *\n");
		sb.append(" */\n");

		String path = firstLowe(javaType);
		sb.append("@Service\n");
		sb.append("public class "+javaType+"Manager extends BaseManager<"+javaType+"> {\n\n");

		sb.append("\t@Inject\n");
		sb.append("\tprivate "+javaType+"Dao "+path+"Dao;\n");
		sb.append("\t@Override\n");
		sb.append("\tprotected BaseMybatis3Dao<"+javaType+"> getDao() {\n");
		sb.append("\t\treturn "+path+"Dao;\n");
		sb.append("\t}\n\n");
		sb.append("}");
		String fileName = managerFilePath + javaType + "Manager.java";
		writeToFile(fileName, sb);
	}

	public static void createDAO() {
		StringBuffer sb = new StringBuffer();
		sb.append("package " + daoPackageName + ";\n");
		sb.append("import org.springframework.stereotype.Repository;\n\n");

		sb.append("import "+modelPackageName+"."+javaType+";\n\n");

		sb.append("import javacommon.base.BaseMybatis3Dao;\n\n");

		sb.append("/**\n");
		sb.append(" *\n");
//		sb.append(" * @ClassName: "+javaType+"Manager\n");
		sb.append(" * @author " + author + "\n");
		sb.append(" * @date "+format(date, "yyyy-MM-dd HH:mm:ss")+"\n");
		sb.append(" *\n");
		sb.append(" */\n");

		sb.append("@Repository\n");
		sb.append("public class "+javaType+"Dao extends BaseMybatis3Dao<"+javaType+"> {\n\n");

		sb.append("}");
		String fileName = daoFilePath + javaType + "Dao.java";
		writeToFile(fileName, sb);
	}
	public static void createWeb() {
		Date date = new Date();
		StringBuffer sb = new StringBuffer();
		sb.append("package " + modelPackageName + ".web;\n");
		sb.append("import java.util.Date;\n\n");
		sb.append("import javax.servlet.http.HttpServletRequest;\n\n");
		sb.append("import org.springframework.beans.factory.annotation.Autowired;\n");
		sb.append("import org.springframework.stereotype.Controller;\n");
		sb.append("import org.springframework.ui.Model;\n");
		sb.append("import org.springframework.web.bind.annotation.RequestMapping;\n");
		sb.append("import org.springframework.web.bind.annotation.ResponseBody;\n\n");
		sb.append("import " + modelPackageName + ".domain."+javaType+";\n\n");
		sb.append("import cn.yesido.basic.com.domain.DataModul;\n");
		sb.append("import cn.yesido.basic.com.service.BasicService;\n");
		sb.append("import cn.yesido.utils.RandomUtils;\n");
		sb.append("import cn.yesido.json.JsonUtils;\n");
		sb.append("\n");

		sb.append("/**\n");
		sb.append(" *\n");
//		sb.append(" * @ClassName: "+javaType+"Controller\n");
		sb.append(" * @Description:TODO \n");
		sb.append(" * @author " + author + "\n");
		sb.append(" * @date "+format(date, "yyyy-MM-dd HH:mm:ss")+"\n");
		sb.append(" *\n");
		sb.append(" */\n");

		sb.append("@Controller\n");
		sb.append("@RequestMapping(\""+firstLowe(javaType)+"\")\n");
		sb.append("public class " + javaType + "Controller {\n");
		sb.append("\n");

		sb.append("\tprivate final static Class<"+javaType+"> clz = "+javaType+".class;\n");
		sb.append("\tprivate final static String ENTITY_KEY = \"entity\";\n");
		sb.append("\n");

		sb.append("\t@Autowired\n");
		sb.append("\tprivate BasicService basicService;\n");
		sb.append("\n");

		sb.append("\t@RequestMapping(value=\"/btajax\")\n");
		sb.append("\t@ResponseBody\n");
		sb.append("\tpublic String btajax(HttpServletRequest request, Model model) {\n");
		sb.append("\t\tDataModul<"+javaType+"> modul = basicService.getBtData(clz, request);\n");
		sb.append("\t\treturn JsonUtils.toJSON(modul);\n");
		sb.append("\t}\n");
		sb.append("\n");

		sb.append("\t@RequestMapping(value=\"/save\")\n");
		sb.append("\t@ResponseBody\n");
		sb.append("\tpublic String save("+javaType+" entity, Model model) {\n");
		sb.append("\t\tif(entity.getSysid() == null || entity.getSysid().equals(\"\")) {\n");
		sb.append("\t\t\tentity.setSysid(RandomUtils.getUUID());\n");
		sb.append("\t\t\tentity.setDeleteFlag(\"0\");\n");
		sb.append("\t\t\tentity.setCreateDate(new Date());\n");
		sb.append("\t\t\tbasicService.save(entity);\n");
		sb.append("\t\t} else {\n");
		sb.append("\t\t\tbasicService.update(entity);\n");
		sb.append("\t\t}\n");
		sb.append("\t\treturn \"success\";\n");
		sb.append("\t}\n");
		sb.append("\n");

		sb.append("\t@RequestMapping(value=\"/delete\")\n");
		sb.append("\t@ResponseBody\n");
		sb.append("\tpublic String delete(String sysid, Model model) {\n");
		sb.append("\t\tbasicService.delete(clz, sysid);\n");
		sb.append("\t\treturn \"success\";\n");
		sb.append("\t}\n");
		sb.append("\n");

		sb.append("\t@RequestMapping(value=\"/load\")\n");
		sb.append("\tpublic String load(String sysid, Model model) {\n");
		sb.append("\t\tif(sysid != null) {\n");
		sb.append("\t\t\tmodel.addAttribute(ENTITY_KEY, basicService.get(clz, sysid));\n");
		sb.append("\t\t}\n");
		sb.append("\t\treturn \"/"+firstLowe(javaType)+"/jsp/create_"+firstLowe(javaType)+".jsp\";\n");
		sb.append("\t}\n");
		sb.append("\n");

		/*sb.append("\t@RequestMapping(value=\"/getBtData\")\n");
		sb.append("\t@ResponseBody\n");
		sb.append("\tpublic String getBtData(HttpServletRequest request, Model model) {\n");
		sb.append("\t\tDataModul<"+javaType+"> modul = basicService.getBtData(clz, request);\n");
		sb.append("\t\treturn JsonUtils.toJSON(modul);\n");
		sb.append("\t}\n");*/

		sb.append("}\n");

		String fileName = controllerFilePath + javaType + "Controller.java";
		writeToFile(fileName, sb);
	}
	private static void writeToFile(String fileName, StringBuffer data) {
		System.out.println("生成文件：" + fileName);
		File file = new File(fileName);
		FileWriter fileWriter = null;
		BufferedWriter writer = null;
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			fileWriter = new FileWriter(file);
			writer = new BufferedWriter(fileWriter);
			writer.write(data.toString());
			writer.flush();
			writer.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public static Map<String,Map<String,String>> getComment(Connection conn,String tableName) {
		Map<String,Map<String,String>> result = new HashMap<String, Map<String,String>>();
		DatabaseMetaData meta;
		try {
			meta = conn.getMetaData();
			ResultSet resultSet = meta.getColumns(null, "%", tableName, "%");
			while (resultSet.next()) {
				String colName = resultSet.getString(4);
				String type = resultSet.getString(6);
				String length = resultSet.getString(7);
				String comment = resultSet.getString(12);
				String default_value = resultSet.getString(13);
				String isnull = resultSet.getString(18);
				Map<String,String> map = new HashMap<String, String>();
				map.put("type", type);
				map.put("length", length);
				map.put("comment", comment);
				map.put("default_value", default_value);
				map.put("isnull", isnull);
				result.put(colName, map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	public static void createEntity() {
		Connection conn = JdbcUtil.getConnection(JdbcUtil.MYSQL);
		String sql = "select * from " + tableName + " where 1=2";
		Date date = new Date();
		StringBuffer sb = new StringBuffer();
		StringBuffer getset = new StringBuffer();
		StringBuffer toString = new StringBuffer();
		Map<String, Map<String, String>> map = getComment(conn, tableName);
		sb.append("package " + modelPackageName + ";\n");
//		sb.append("import java.io.Serializable;\n");
//		sb.append("import java.util.Date;\n");
//		sb.append("import java.util.Map;\n");
//		sb.append("\n");
//		sb.append("import javax.persistence.Column;\n");
//		sb.append("import javax.persistence.Entity;\n");
//		sb.append("import javax.persistence.Table;\n");
		sb.append("\n");
		sb.append("import javacommon.base.BaseModel;\n");
		sb.append("\n");
		sb.append("/**\n");
		sb.append(" *\n");
//		sb.append(" * @ClassName: "+javaType+"\n");
//		sb.append(" * @Description:TODO \n");
		sb.append(" * @author "+author+"\n");
		sb.append(" * @date "+format(date, "yyyy-MM-dd HH:mm:ss")+"\n");
		sb.append(" *\n");
		sb.append(" */\n");
//		sb.append("@Entity\n");
//		sb.append("@Table(name = \"" + tableName + "\")\n");
		sb.append("public class " + javaType)
		.append(" extends BaseModel {").append("\n");
		sb.append("\n");
		sb.append("\t/** serialVersionUID */\n");
		sb.append("\tprivate static final long serialVersionUID = 1L;\n");
		sb.append("\n");
		try {
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();//结果集列数

			toString.append("\n\t@Override\n");
			toString.append("\tpublic String toString() {\n");
			toString.append("\t\treturn \"" + javaType + " [");
			int j = 1;
			for (int i = 0; i < columnCount; i++) {
				String name = metaData.getColumnLabel(i+1);//列名
				Map<String, String> comment = map.get(name);
				String javaType = getType(metaData.getColumnType(i + 1));
				String javaName = getJavaName(name);
				//System.out.println(metaData.getColumnClassName(i + 1));//获取Java类型
				if(!javaName.equalsIgnoreCase("eid")
						&& !javaName.equalsIgnoreCase("createTime")
						&& !javaName.equalsIgnoreCase("updateTime")) {
					//属性
//					String javaName = getJavaName(name);
					if(j == 2) {
						j = 1;
						toString.append(javaName + "=\" + " + javaName + "\n\t\t\t\t+ \", ");
					} else {
						j ++;
						toString.append(javaName + "=\" + " + javaName + " + \", ");
					}
					if(comment.get("comment") != null && !comment.get("comment").trim().equals("")) {
						sb.append("\t/** "+comment.get("comment")+". */\n");
					} else {
						sb.append("\t/** "+javaName+". */\n");
					}
//					sb.append("\t@Column(name = \"" + name + "\")\n");
					sb.append("\tprivate " + javaType + " " + javaName + ";" + "\n");
					sb.append("\n");

					getset.append("\tpublic " + javaType + " get" + firstUpper(javaName) + "() {\n");
					getset.append("\t\treturn " + javaName + ";\n");
					getset.append("\t}\n");

					getset.append("\tpublic void set" + firstUpper(javaName) + "(" + javaType + " " + javaName + ") {\n");
					getset.append("\t\tthis." + javaName + " = " + javaName + ";\n");
					getset.append("\t}\n");


				}
            }
			toString.append("eid=\" + getEid() + \"]\";\n");
			toString.append("\t}\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sb.append(getset);
		sb.append(toString);
		sb.append("}");

		String fileName = domainFilePath + javaType + ".java";
		writeToFile(fileName, sb);
	}
    public static String getType(int sqlType) {
    	String javaType = "";
    	if(sqlType == Types.VARCHAR || sqlType == Types.CHAR
    			|| sqlType == Types.LONGNVARCHAR || sqlType == Types.LONGVARCHAR) {
    		javaType = "String";
    	} else if(sqlType == Types.DATE || sqlType == Types.TIMESTAMP) {
//    		javaType = "Date";
    		javaType = "String";
    	} else if(sqlType == Types.BINARY || sqlType == Types.VARBINARY || sqlType == Types.LONGVARBINARY) {
    		javaType = "byte[]";
    	} else if(sqlType == Types.BIT) {
    		javaType = "Boolean";
    	} else if(sqlType == Types.TINYINT) {
    		javaType = "Integer";
    	} else if(sqlType == Types.SMALLINT) {
    		javaType = "Integer";
    	} else if(sqlType == Types.INTEGER) {
    		javaType = "Integer";
    	} else if(sqlType == Types.BIGINT) {
    		javaType = "Long";
    	} else if(sqlType == Types.DOUBLE || sqlType == Types.FLOAT) {
    		javaType = "Double";
    	} else if(sqlType == Types.DECIMAL) {
    		javaType = "BigDecimal";
    	} else {
    		javaType = "String";
    	}
    	return javaType;
    }
    public static String firstUpper(String s) {
    	return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    public static String firstLowe(String s) {
    	return s.substring(0, 1).toLowerCase() + s.substring(1);
    }
    public static String getJavaName(String db_name) {
    	String result = "";
    	String[] strings = db_name.split("_");
    	for (int i = 0; i < strings.length; i++) {
    		String tem = strings[i].toLowerCase();
			if(i > 0) {
				result += firstUpper(tem);
			} else {
				result += tem;
			}
		}
    	return result;
    }

    /**
     * 日期转成字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        String result = null;
        if (pattern == null) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            result = format.format(date);
        }
        return result;
    }
}
