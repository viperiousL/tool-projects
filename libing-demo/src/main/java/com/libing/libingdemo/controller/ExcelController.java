package com.libing.libingdemo.controller;

import com.alibaba.excel.write.handler.WriteHandler;
import com.libing.libingdemo.handler.CommentWriteHandler;
import com.libing.libingdemo.handler.CustomSheetWriteHandler;
import com.libing.libingdemo.utils.EasyExcelUtils;
import com.libing.libingdemo.vo.ExportExcelBookVo;
import com.libing.libingdemo.vo.ExportExcelUserVo;
import com.libing.libingdemo.vo.ExportExcelVo;
import com.libing.libingdemo.vo.ImportExcelVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 事件控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/excel")
public class ExcelController {

    /**
     * excel模板下载
     */
    @RequestMapping(value = "/template", method = RequestMethod.GET)
    public String template(HttpServletResponse response) {
        String fileName = "导入模板下载" + System.currentTimeMillis();
        try {
            List<WriteHandler> handlers = new ArrayList<>();
            handlers.add(new CommentWriteHandler());
            handlers.add(new CustomSheetWriteHandler());
            EasyExcelUtils.export(fileName, "", null, ImportExcelVo.class, handlers, response);
        } catch (Exception e) {
            return "模板下载失败" + e.getMessage();
        }
        return "模板下载成功！";
    }

    /**
     * Excel批量导入数据
     *
     * @param file 导入文件
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public String importEvents(MultipartFile file) {
        try {
            List<?> list = EasyExcelUtils.importExcel(file, ImportExcelVo.class);
            for (Object o : list) {
                System.out.println(o);
            }
            return "数据导入完成";
        } catch (Exception e) {
            return "数据导入失败！" + e.getMessage();
        }
    }


    /**
     * excel数据导出
     *
     * @param size 导出条数， 也可以是用户需要导出数据的条件
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public String export(Long size, HttpServletResponse response) {
        // 模拟根据条件在数据库查询数据
        ArrayList<ExportExcelVo> excelVos = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            ExportExcelVo excelVo = new ExportExcelVo();
            excelVo.setContact(String.valueOf(10000000000L + i));
            excelVo.setName("公司名称" + i);
            excelVo.setCreditCode("社会性用代码" + i);
            excelVo.setProvince("地区" + i);
            excelVo.setLegalPerson("法人" + i);
            excelVo.setFormerName("曾用名" + i);
            excelVo.setStockholder("投资人" + i);
            excelVo.setCreateTime(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date()));
            excelVos.add(excelVo);
        }
        String fileName = "数据导出" + System.currentTimeMillis();
        try {
            EasyExcelUtils.export(fileName, "第一页", excelVos, ExportExcelVo.class, null, response);
        } catch (Exception e) {
            return "数据导出成功" + e.getMessage();
        }
        return "数据导出失败！";
    }

    /**
     * excel数据导出
     *
     * @param size 导出条数， 也可以是用户需要导出数据的条件
     * @return
     */
    @RequestMapping(value = "/exportss", method = RequestMethod.GET)
    public String exportss(Long size, HttpServletResponse response) {
        // 模拟根据条件在数据库查询数据
        ArrayList<ExportExcelVo> excelVos = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            ExportExcelVo excelVo = new ExportExcelVo();
            excelVo.setContact(String.valueOf(10000000000L + i));
            excelVo.setName("公司名称" + i);
            excelVo.setCreditCode("社会性用代码" + i);
            excelVo.setProvince("地区" + i);
            excelVo.setLegalPerson("法人" + i);
            excelVo.setFormerName("曾用名" + i);
            excelVo.setStockholder("投资人" + i);
            excelVo.setCreateTime(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date()));
            excelVos.add(excelVo);
        }
        String fileName = "数据导出" + System.currentTimeMillis();
        try {
            EasyExcelUtils.export(fileName, excelVos, 10, ExportExcelVo.class, null, response);
        } catch (Exception e) {
            return "数据导出成功" + e.getMessage();
        }
        return "数据导出失败！";
    }

    /**
     * excel数据导出
     *
     * @param size 导出条数， 也可以是用户需要导出数据的条件
     * @return
     */
    @RequestMapping(value = "/exports", method = RequestMethod.GET)
    public String exports(Long size, HttpServletResponse response) {
        // 模拟根据条件在数据库查询数据
        List<List<?>> listList = new ArrayList<>();
        List<String> sheetNames = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            sheetNames.add("第" + i + "页");
        }
        List<ExportExcelVo> excelVos = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            ExportExcelVo excelVo = new ExportExcelVo();
            excelVo.setContact(String.valueOf(10000000000L + i));
            excelVo.setName("公司名称" + i);
            excelVo.setCreditCode("社会性用代码" + i);
            excelVo.setProvince("地区" + i);
            excelVo.setLegalPerson("法人" + i);
            excelVo.setFormerName("曾用名" + i);
            excelVo.setStockholder("投资人" + i);
            excelVo.setCreateTime(new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(new Date()));
            excelVos.add(excelVo);
        }
        listList.add(excelVos);
        listList.add(new ArrayList<>());
        List<ExportExcelUserVo> exportExcelUserVos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ExportExcelUserVo exportExcelUserVo = new ExportExcelUserVo();
            exportExcelUserVo.setName("name" + i);
            exportExcelUserVo.setAge(18 + i);
            exportExcelUserVo.setCode(100 + i);
            exportExcelUserVos.add(exportExcelUserVo);
        }
        listList.add(exportExcelUserVos);
        listList.add(new ArrayList<>());
        List<ExportExcelBookVo> exportExcelBookVos = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ExportExcelBookVo exportExcelBookVo = new ExportExcelBookVo();
            exportExcelBookVo.setName("name" + i);
            exportExcelBookVo.setPrice(18 + i);
            exportExcelBookVo.setReaderNum(100 + i);
            exportExcelBookVos.add(exportExcelBookVo);
        }
        listList.add(exportExcelBookVos);
        String fileName = "数据导出" + System.currentTimeMillis();
        try {
            EasyExcelUtils.export(fileName, sheetNames, listList, response);
        } catch (Exception e) {
            return "数据导出成功" + e.getMessage();
        }
        return "数据导出失败！";
    }


}