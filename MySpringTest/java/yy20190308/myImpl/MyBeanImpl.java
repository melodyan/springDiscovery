package yy20190308.myImpl;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import yy20190307.MyTestBean;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Iterator;

/**
 * 自己实现简单的Spring bean生成
 */
public class MyBeanImpl {

	public static Object getBeanByName(String beanName) throws Exception{
		//1.读取配置文件
		SAXReader reader = new SAXReader();
		File file=new File("MySpringTest/target/classes/beanFactoryTest.xml");
		Document doc = reader.read(file);
		Element rootElement = doc.getRootElement();
		//迭代根目录下标签为<bean>的子元素
		Iterator<Element> iterator = rootElement.elementIterator("bean");
		Object bean=null;
		while(iterator.hasNext()){
			Element ele = iterator.next();
			String id = ele.attribute("id").getValue();
			if(beanName.equals(id)){
				System.out.println("id:"+id);
				String clazzFullName = ele.attribute("class").getValue();
				System.out.println("clazzFullName:"+clazzFullName);
				//2.通过反射生成需要的Bean
				System.out.println("正在通过反射生成该bean。。。。。。。。。。。。");
				Constructor<?> constructor = Class.forName(clazzFullName).getConstructor();
				bean = constructor.newInstance();
				System.out.println(clazzFullName+":bean生成完毕。。。。。。。。。。。。");
			}
		}
		return bean;
	}


	public static void main(String[] args) throws Exception {

		MyTestBean myTestBean = (MyTestBean) getBeanByName("myTestBean");
		System.out.println(myTestBean.toString());
	}
}
