import com.wjs.entity.Stu;
import com.wjs.laterProjectApplication;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = laterProjectApplication.class)
@RunWith(SpringRunner.class)
public class TestPOI {










//    @Test
//    public void testpois() {
//
//        //创建一个Excel文档
//        Workbook workbook = new HSSFWorkbook();
//
//        //创建一个工作薄   参数：工作薄名字(sheet1,shet2....)
//        Sheet sheet = workbook.createSheet("用户信息表1");
//
//        //创建一行  参数：行下标(下标从0开始)
//        Row row = sheet.createRow(0);
//
//        //创建一个单元格  参数：单元格下标(下标从0开始)
//        Cell cell = row.createCell(0);
//
//        //给单元格设置内容
//        cell.setCellValue("这是第一行第一个单元格");
//
//        //导出单元格
//        try {
//            workbook.write(new FileOutputStream(new File("C:/Users/admin.DESKTOP-5CQ2AJF/Desktop/后期项目/POI练习/poi.xls")));
//
//            //释放资源
//            workbook.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testPoi(){
//        // 1. 创建表格对象
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        // 2. 创建页对象
//        HSSFSheet sheet = workbook.createSheet("学生信息");
//        // 3. 创建行对象
//        HSSFRow row = sheet.createRow(0);
//        // 4. 创建单元格对象
//        HSSFCell cell = row.createCell(0);
//
//        // 5. 将表格对象写入磁盘
//        try {
//            workbook.write(new File("C:/Users/admin.DESKTOP-5CQ2AJF/Desktop/后期项目/POI练习/poi.xls"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                workbook.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//    @Test
//    public void testPoiDemo(){
//        // 1. 创建表格对象
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        // 2. 创建页对象
//        HSSFSheet sheet = workbook.createSheet("学生信息");
//        //sheet.setColumnWidth(3,50*256);
//        //CellRangeAddress region=new CellRangeAddress(0, 0, 0, 10);
//        //施工
//        //sheet.addMergedRegion(region);
//        // 3. 创建行对象
//        HSSFRow row = sheet.createRow(0);
//        row.setHeight((short)500);
//        // 4. 创建表头
//        HSSFFont font = workbook.createFont();
//        // 加粗
//        font.setBold(true);
//        // 颜色
//        font.setColor(Font.COLOR_RED);
//        // 字体
//        font.setFontName("宋体");
//        // 斜体
//        font.setItalic(true);
//
//        HSSFCellStyle cellStyle1 = workbook.createCellStyle();
//        cellStyle1.setFont(font);
//
//        HSSFCell cell1 = row.createCell(5);
//        cell1.setCellStyle(cellStyle1);
//        cell1.setCellValue("脑壳疼");
//
//
//        String[] strs = {"ID","姓名","年龄","生日"};
//        for (int i = 0; i < strs.length; i++) {
//            String str = strs[i];
//            System.out.println(str);
//            row.createCell(i).setCellValue(str);
//        }
//        Stu rxx1 = new Stu("1", "Rxx", 18, new Date());
//        Stu rxx2 = new Stu("2", "Rxx", 18, new Date());
//        Stu rxx3 = new Stu("3", "Rxx", 18, new Date());
//        Stu rxx4 = new Stu("4", "Rxx", 18, new Date());
//        // 将集合中的数据写入至xls文件中
//        HSSFDataFormat dataFormat = workbook.createDataFormat();
//        short format = dataFormat.getFormat("yyyy-MM-dd");
//        HSSFCellStyle cellStyle = workbook.createCellStyle();
//        cellStyle.setDataFormat(format);
//        List<Stu> stus = Arrays.asList(rxx1, rxx2, rxx3, rxx4);
//        for (int i = 0; i < stus.size(); i++) {
//            HSSFRow row1 = sheet.createRow(i + 1);
//            row1.createCell(0).setCellValue(stus.get(i).getId());
//            row1.createCell(1).setCellValue(stus.get(i).getName());
//            row1.createCell(2).setCellValue(stus.get(i).getAge());
//            HSSFCell cell = row1.createCell(3);
//            cell.setCellStyle(cellStyle);
//            cell.setCellValue(stus.get(i).getBirth());
//        }
//        // 5. 将表格对象写入磁盘
//        try {
//            workbook.write(new File("C:/Users/admin.DESKTOP-5CQ2AJF/Desktop/后期项目/POI练习/poi.xls"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                workbook.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Test
//    public void testPoiImport() {
//
//        try {
//            //获取要导入的文件
//            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(
//                    new File("C:/Users/admin.DESKTOP-5CQ2AJF/Desktop/后期项目/POI练习/poi.xls")));
//
//            //获取工作薄
//            HSSFSheet sheet = workbook.getSheet("学生信息");
//
//            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//
//                Stu student = new Stu();
//
//                //获取行
//                HSSFRow row = sheet.getRow(i);
//
//                //获取Id
//                student.setId(row.getCell(0).getStringCellValue());
//                //获取name
//                student.setName(row.getCell(1).getStringCellValue());
//                //获取age
//                double ages = row.getCell(2).getNumericCellValue();
//                student.setAge((int) ages);
//                //获取生日
//                student.setBirth(row.getCell(3).getDateCellValue());
//
//                //调用一个插入数据库的方法
//                System.out.println(student);
//            }
//
//            //关闭资源
//            workbook.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

}
