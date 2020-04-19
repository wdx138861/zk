import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RunProvider {

    public static void main(String[] args) throws IOException {
        //创建spring容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-provider.xml");
        //启动spring容器
        ((ClassPathXmlApplicationContext)ac).start();
        //阻塞
        System.in.read();
    }
}
