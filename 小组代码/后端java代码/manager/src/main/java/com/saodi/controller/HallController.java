package com.saodi.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodi.po.Hall;
import com.saodi.query.HallQuery;
import com.saodi.service.IHallService;
import com.saodi.util.HallUtil;
import com.saodi.vo.ResponseObj;
import io.swagger.annotations.ApiParam;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author saodi
 * @since 2023-12-19
 */
@RestController
@RequestMapping("/hall")
public class HallController {

    @Autowired
    IHallService service;

    @PostMapping("/updateinit/{id}")
    public ResponseObj updateInit(@PathVariable("id") Integer id) {

        boolean init = service.init(id);
        return ResponseObj.SUCCESS();
    }

    @PostMapping("/getbyid/{id}")
    public ResponseObj getbyid(@PathVariable("id") Integer id) {

        Hall byId = service.getById(id);
        return ResponseObj.SUCCESS(byId);
    }

    @PostMapping("/page")
    public ResponseObj page(@RequestBody HallQuery query) {

        Page byPage = service.getByPage(query);
        return ResponseObj.SUCCESS(byPage);
    }

    @PostMapping("/saveOrupdate")
    public  ResponseObj update(@RequestBody Hall hall){
        boolean b = service.saveOrUpdate(hall);
        System.out.println(hall);
        return b?ResponseObj.SUCCESS(hall):ResponseObj.ERROR();
    }

    @PostMapping("/del")
    public ResponseObj del(@RequestBody Hall hall){
        boolean b = service.removeById(hall);
        return  b?ResponseObj.SUCCESS():ResponseObj.ERROR();
    }

    @PostMapping("/getBycinema_account/{account}")
    public ResponseObj getByCinemaAccount(@PathVariable("account") Integer account){
        QueryWrapper<Hall> query = new QueryWrapper<Hall>(new Hall()).eq("cinema_id", account);
        List<Hall> list = service.list(query);
        return ResponseObj.SUCCESS(list);
    }

    @PostMapping("/updatehall/{id}")
    public ResponseObj multipartFileTest(@ApiParam(value = "multipartFile") @RequestParam MultipartFile multipartFile, @PathVariable("id") Integer id) throws Exception {
//        File file = new File("C:\\Users\\SAODI\\Desktop\\1219\\html\\1.png");
//        System.out.println(multipartFile.getName());
//        System.out.println(multipartFile.getOriginalFilename());
//        System.out.println(multipartFile.getContentType());
//
//        multipartFile.transferTo(file);
//        System.out.println(file.getAbsolutePath());
//        return file.getAbsolutePath();
        InputStream inputStream = multipartFile.getInputStream();
        Workbook workbook = new HSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        String sheetName = sheet.getSheetName();
        System.out.println(sheetName);
        Row row = sheet.getRow(0);
        Cell cell1 = row.getCell(0);
        Cell cell2 = row.getCell(1);
        Integer x = (int) cell1.getNumericCellValue();
        Integer y = (int) cell2.getNumericCellValue();
        int[][] array = new int[x][y];
        int firstRowNum = sheet.getFirstRowNum();
        System.out.println(firstRowNum);

        Integer i = firstRowNum + 2;

        int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();

        for (int rowNum = i; rowNum < physicalNumberOfRows; rowNum++) {
            Row r = sheet.getRow(rowNum);
            if (null == r) {
                continue;
            }
            for (int cellNum = r.getFirstCellNum(); cellNum < r.getPhysicalNumberOfCells(); cellNum++) {
                Cell cell = r.getCell(cellNum);
                if (cell != null)
                    array[rowNum-2][cellNum]=(int) cell.getNumericCellValue();
            }
        }
        String s=HallUtil.twoDimensionalArrayToString(array);
        System.out.println(s);
        UpdateWrapper uw = new UpdateWrapper();
        uw.set("hall_size", s);
        uw.eq(id != null ? true : false, "id", id);
        boolean update = service.update(uw);
        return  update?ResponseObj.SUCCESS():ResponseObj.ERROR();
    }

    @PostMapping("/delBatch")
    public ResponseObj delBatch(@RequestBody Collection<Integer> hallId){
        boolean b = service.removeBatchByIds(hallId);
        return  b?ResponseObj.SUCCESS():ResponseObj.ERROR();
    }

}
