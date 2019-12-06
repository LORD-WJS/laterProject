package EasyExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.util.FileUtils;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.wjs.laterProjectApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by HIAPAD on 2019/12/2.
 */
@SpringBootTest(classes = laterProjectApplication.class)
@RunWith(SpringRunner.class)
public class TestEasyExcel {
    String fileName = "C:/Users/admin.DESKTOP-5CQ2AJF/Desktop/后期项目/POI练习/"+new Date().getTime()+"DemoData.xlsx";
    @Test
    public void test01(){
        // 写法1 . 链式调用
        // EasyExcel.write(fileName,DemoData.class).sheet().doWrite(data());
        // 写法2 .
        ExcelWriter build = EasyExcel.write(fileName, DemoData.class).build();
        // String : 页名称  Int : 第几页    可以同时指定
        WriteSheet sheet = EasyExcel.writerSheet("测试用").build();
        build.write(data(),sheet);//data()是一个实体类集合，通过最下方方法获取
        build.finish();
        System.out.println("完成");
    }
    @Test
    public void test02(){
        //链式调用
//         new DemoDataListener(); //每次都需要创建新的DemoDataListener 在Spring框架中使用时需要注意
//         EasyExcel.read("C:/Users/admin.DESKTOP-5CQ2AJF/Desktop/后期项目/POI练习/1575268838705DemoData.xlsx",DemoData.class,new DemoDataListener()).sheet().doRead();

        ExcelReader excelReader = EasyExcel.read("C:/Users/admin.DESKTOP-5CQ2AJF/Desktop/后期项目/POI练习/1575268838705DemoData.xlsx", DemoData.class, new DemoDataListener()).build();
        ReadSheet readSheet = EasyExcel.readSheet("测试用").build();//此处可写sheet名或下标
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }

    @Test
    public void test03(){
        // 1. 创建一个需要忽略|包含的字段集合
        Set set = new HashSet();
        // 2. 放入需要忽略|包含的属性名
        set.add("date");
        //3. excludeColumnFiledNames 排除      includeColumnFiledNames 只包含
        EasyExcel.write(fileName,DemoData.class).excludeColumnFiledNames(set).sheet().doWrite(data());
    }
    @Test
    public void test04(){
        // EasyExcel 通过创建空对象集合的形式 生成只有表头的文件 可以作为网络使用的模板Excle
        ComplexHeadData complexHeadData = new ComplexHeadData();
        List<ComplexHeadData> complexHeadData1 = Arrays.asList(complexHeadData);
        // 注意 null 不要写
        EasyExcel.write(fileName,ComplexHeadData.class).sheet().doWrite(complexHeadData1 );
    }
    @Test
    public void test05(){
        ConverterData rxx = new ConverterData("Rxx1", new Date(), 11.0);
        ConverterData rxx1 = new ConverterData("Rxx2", new Date(), 11.0);
        ConverterData rxx2 = new ConverterData("Rxx3", new Date(), 11.0);
        EasyExcel.write(fileName,ConverterData.class).sheet().doWrite(Arrays.asList(rxx,rxx1,rxx2));
    }
    @Test
    public void test06() throws IOException {
        ImageData imageData = new ImageData();
        imageData.setFile(new File("C:/Users/admin.DESKTOP-5CQ2AJF/Desktop/后期项目/壁纸/3.jpg"));
        imageData.setByteArray(FileUtils.readFileToByteArray(new File("C:/Users/admin.DESKTOP-5CQ2AJF/Desktop/后期项目/壁纸/4.jpg")));
        imageData.setInputStream(new FileInputStream(new File("C:/Users/admin.DESKTOP-5CQ2AJF/Desktop/后期项目/壁纸/3.jpg")));
        imageData.setString("C:/Users/admin.DESKTOP-5CQ2AJF/Desktop/后期项目/壁纸/3.jpg");
        //imageData.setUrl(new URL(
        //        "https://raw.githubusercontent.com/alibaba/easyexcel/master/src/test/resources/converter/img.jpg"));
        List<ImageData> imageData1 = Arrays.asList(imageData);
        EasyExcel.write(fileName,ImageData.class).sheet().doWrite(imageData1);
    }
    private List<DemoData> data(){
        DemoData demoData1 = new DemoData("Rxx", new Date(), 1.0, "Rxx");
        DemoData demoData2 = new DemoData("Rxx", new Date(), 1.0, "Rxx");
        DemoData demoData3 = new DemoData("Rxx", new Date(), 1.0, "Rxx");
        DemoData demoData4 = new DemoData("Rxx", new Date(), 1.0, "Rxx");
        List<DemoData> demoData = Arrays.asList(demoData1, demoData2, demoData3, demoData4);
        return demoData;
    }
}
