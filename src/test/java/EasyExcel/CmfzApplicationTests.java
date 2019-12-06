package EasyExcel;

import com.wjs.entity.Stu;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmfzApplicationTests {


    @Test
    public void testPoi(){
        // 1. 创建表格对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 2. 创建页对象
        HSSFSheet sheet = workbook.createSheet("学生信息");
        // 3. 创建行对象
        HSSFRow row = sheet.createRow(0);
        // 4. 创建单元格对象
        HSSFCell cell = row.createCell(0);

        // 5. 将表格对象写入磁盘
        try {
            workbook.write(new File("D:\\桌面杂项\\stu.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void testPoiDemo(){
        // 1. 创建表格对象
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 2. 创建页对象
        HSSFSheet sheet = workbook.createSheet("学生信息");
        sheet.setColumnWidth(3,50*256);
        CellRangeAddress region=new CellRangeAddress(0, 0, 0, 10);
        //施工
        sheet.addMergedRegion(region);
        // 3. 创建行对象
        HSSFRow row = sheet.createRow(0);
        row.setHeight((short)500);
        // 4. 创建表头
        HSSFFont font = workbook.createFont();
        // 加粗
        font.setBold(true);
        // 颜色
        font.setColor(Font.COLOR_RED);
        // 字体
        font.setFontName("宋体");
        // 斜体
        font.setItalic(true);

        HSSFCellStyle cellStyle1 = workbook.createCellStyle();
        cellStyle1.setFont(font);

        HSSFCell cell1 = row.createCell(5);
        cell1.setCellStyle(cellStyle1);
        cell1.setCellValue("脑壳疼");


        String[] strs = {"ID","姓名","年龄","生日"};
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i];
            row.createCell(i).setCellValue(str);
        }
        Stu rxx1 = new Stu("1", "Rxx", 18, new Date());
        Stu rxx2 = new Stu("1", "Rxx", 18, new Date());
        Stu rxx3 = new Stu("1", "Rxx", 18, new Date());
        Stu rxx4 = new Stu("1", "Rxx", 18, new Date());
        // 将集合中的数据写入至xls文件中
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd");
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(format);
        List<Stu> stus = Arrays.asList(rxx1, rxx2, rxx3, rxx4);
        for (int i = 0; i < strs.length; i++) {
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(stus.get(i).getId());
            row1.createCell(1).setCellValue(stus.get(i).getName());
            row1.createCell(2).setCellValue(stus.get(i).getAge());
            HSSFCell cell = row1.createCell(3);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(stus.get(i).getBirth());
        }
        // 5. 将表格对象写入磁盘
        try {
            workbook.write(new File("D:\\桌面杂项\\stu.xls"));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /*
        导入Poi数据
     */
    @Test
    public void testPoiImport() throws IOException {
        // 读入一个xls文件
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(new File("D:\\桌面杂项\\stu.xls")));
        // 读入页
        HSSFSheet sheet = workbook.getSheet("学生信息");
        // 读入行的数据
        for(int i=1;i<=sheet.getLastRowNum();i++){
            HSSFRow row = sheet.getRow(i);
            String stringCellValue = row.getCell(1).getStringCellValue();
            System.out.println(stringCellValue);
            double d = row.getCell(2).getNumericCellValue();
            System.out.println((int)d);
            Date dateCellValue = row.getCell(3).getDateCellValue();
            System.out.println(dateCellValue);
        }
    }
}
