package com.libing.libingdemo.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.libing.libingdemo.listener.ExcelListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class EasyExcelUtils {
    /**
     * 导出数据为excel文件
     *
     * @param filename   文件名称
     * @param sheetName  页名称
     * @param dataResult 集合内的bean对象类型要与clazz参数一致
     * @param clazz      集合内的bean对象类型要与clazz参数一致
     * @param response   HttpServlet响应对象
     */
    public static void export(String filename, String sheetName, List<?> dataResult, Class<?> clazz, List<WriteHandler> handlers, HttpServletResponse response) {
        response.setStatus(200);
        OutputStream outputStream = null;
        ExcelWriter excelWriter = null;
        try {
            if (StringUtils.isBlank(filename)) {
                throw new RuntimeException("文件名不能为空");
            }
            String fileName = filename.concat(".xlsx");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            outputStream = response.getOutputStream();
            // 根据不同的策略生成不同的ExcelWriter对象
            if (dataResult == null) {
                excelWriter = getExportExcelWriter(outputStream, handlers);
            } else {
                excelWriter = getExportExcelWriter(outputStream);
            }

            WriteTable writeTable = EasyExcel.writerTable(0).head(clazz).needHead(true).build();
            if (StringUtils.isBlank(sheetName)) {
                sheetName = fileName;
            }
            WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).build();
            // 写出数据
            excelWriter.write(dataResult, writeSheet, writeTable);
        } catch (Exception e) {
            log.error("导出excel数据异常：", e);
            throw new RuntimeException(e);
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    log.error("导出数据关闭流异常", e);
                }
            }
        }
    }

    /**
     * 导出数据为excel文件
     *
     * @param filename   文件名称
     * @param dataResult 集合内的bean对象类型要与clazz参数一致
     * @param clazz      集合内的bean对象类型要与clazz参数一致
     * @param response   HttpServlet响应对象
     */
    public static void export(String filename, List<?> dataResult, Integer sheetSize, Class<?> clazz, List<WriteHandler> handlers, HttpServletResponse response) {
        response.setStatus(200);
        OutputStream outputStream = null;
        ExcelWriter excelWriter = null;
        try {
            if (StringUtils.isBlank(filename)) {
                throw new RuntimeException("文件名不能为空");
            }
            String fileName = filename.concat(".xlsx");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            outputStream = response.getOutputStream();
            // 根据不同的策略生成不同的ExcelWriter对象
            if (dataResult == null) {
                excelWriter = getExportExcelWriter(outputStream, handlers);
            } else {
                excelWriter = getExportExcelWriter(outputStream);
            }
            List<? extends List<?>> split = ListUtils.split(dataResult, sheetSize);
            WriteTable writeTable = EasyExcel.writerTable(0).head(clazz).needHead(true).build();
            for (int i = 0; i < split.size(); i++) {
                WriteSheet writeSheet = EasyExcel.writerSheet("第" + (i + 1) + "页").build();
                // 写出数据
                excelWriter.write(split.get(i), writeSheet, writeTable);
            }
        } catch (Exception e) {
            log.error("导出excel数据异常：", e);
            throw new RuntimeException(e);
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    log.error("导出数据关闭流异常", e);
                }
            }
        }
    }

    /**
     * 导出数据为excel文件
     *
     * @param filename   文件名称
     * @param sheetNames 页名称
     * @param dataResult 集合内的bean对象类型要与clazz参数一致
     * @param response   HttpServlet响应对象
     */
    public static void export(String filename, List<String> sheetNames, List<List<?>> dataResult, HttpServletResponse response) {
        response.setStatus(200);
        OutputStream outputStream = null;
        ExcelWriter excelWriter = null;
        try {
            if (StringUtils.isBlank(filename)) {
                throw new RuntimeException("文件名不能为空");
            }
            String fileName = filename.concat(".xlsx");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            outputStream = response.getOutputStream();
            // 根据不同的策略生成不同的ExcelWriter对象
            if (CollectionUtils.isEmpty(dataResult) || CollectionUtils.isEmpty(sheetNames) || dataResult.size() != sheetNames.size()) {
                throw new RuntimeException("导出数据错误");
            }
            excelWriter = getExportExcelWriter(outputStream);
            //导出多个sheet页
            AtomicInteger sheetNum = new AtomicInteger(1);
            for (int i = 0; i < sheetNames.size(); i++) {
                if (CollectionUtils.isEmpty(dataResult.get(i))) {
                    continue;
                }
                String sheetName = sheetNames.get(i);
                if (StringUtils.isBlank(sheetName)) {
                    sheetName = "sheet" + sheetNum.getAndAdd(1);
                }
                Class<?> clazz = Object.class;
                if (!CollectionUtils.isEmpty(dataResult.get(i))) {
                    clazz = dataResult.get(i).get(0).getClass();
                }
                WriteSheet writeSheet = EasyExcel.writerSheet(sheetName).head(clazz).build();
                excelWriter.write(dataResult.get(i), writeSheet);
            }
        } catch (Exception e) {
            log.error("导出excel数据异常：", e);
            throw new RuntimeException(e);
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    log.error("导出数据关闭流异常", e);
                }
            }
        }
    }

    /**
     * 根据不同策略生成不同的ExcelWriter对象， 可根据实际情况修改
     *
     * @param outputStream 数据输出流
     * @return 模板下载ExcelWriter对象
     */
    private static ExcelWriter getExportExcelWriter(OutputStream outputStream, List<WriteHandler> handlers) {
        ExcelWriterBuilder write = EasyExcel.write(outputStream);
        if (!CollectionUtils.isEmpty(handlers)) {
            for (WriteHandler handler : handlers) {
                write.registerWriteHandler(handler);
            }
        }
        return write.build();
    }

    /**
     * 根据不同策略生成不同的ExcelWriter对象， 可根据实际情况修改
     *
     * @param outputStream 数据输出流
     * @return 数据导出ExcelWriter对象
     */
    private static ExcelWriter getExportExcelWriter(OutputStream outputStream) {
        return EasyExcel.write(outputStream)
                .registerWriteHandler(getStyleStrategy())   //字体居中策略
                .build();
    }

    /**
     * 设置表格内容居中显示策略
     *
     * @return
     */
    public static HorizontalCellStyleStrategy getStyleStrategy() {
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置背景颜色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        //设置头字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short) 13);
        headWriteFont.setBold(true);
        headWriteCellStyle.setWriteFont(headWriteFont);
        //设置头居中
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);

        // 内容策略
        WriteCellStyle writeCellStyle = new WriteCellStyle();
        // 设置内容水平居中
        writeCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        return new HorizontalCellStyleStrategy(headWriteCellStyle, writeCellStyle);
    }

    /**
     * 根据Excel模板，批量导入数据
     *
     * @param file  导入的Excel
     * @param clazz 解析的类型
     * @return 解析完成的数据
     */
    public static List<?> importExcel(MultipartFile file, Class<?> clazz) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("没有文件或者文件内容为空！");
        }
        List<Object> dataList = null;
        BufferedInputStream ipt = null;
        try {
            InputStream is = file.getInputStream();
            // 用缓冲流对数据流进行包装
            ipt = new BufferedInputStream(is);
            // 数据解析监听器
            ExcelListener<Object> listener = new ExcelListener<>();
            // 读取数据
            EasyExcel.read(ipt, clazz, listener).sheet().doRead();
            // 获取去读完成之后的数据
            dataList = listener.getDataList();
        } catch (Exception e) {
            log.error(String.valueOf(e));
            throw new RuntimeException("数据导入失败！" + e);
        }
        return dataList;
    }

}