package egovframework.com.cmm.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.ibm.icu.math.BigDecimal;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.let.rsv.service.ReservationService;
import egovframework.let.rsv.service.ReservationVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class ExcelDownloadView extends AbstractExcelView {
  

	@SuppressWarnings({"unchecked","deprecation"})
	protected void buildExcelDocument(Map model, HSSFWorkbook wb, HttpServletRequest req, HttpServletResponse resp)throws Exception{
		Map<String, Object> dataMap = (Map<String, Object>)model.get("dataMap");
		
		String title = (String)dataMap.get("title");
		List<String> columMap = (List<String>)dataMap.get("columMap");
		List<Object> valueMap = (List<Object>)dataMap.get("valueMap");
		
		HSSFCell cell = null;
		//셀 스타일 설정 값
		HSSFCellStyle style = this.getHSSFCeLLStyleByHeader(wb);
		
		HSSFSheet sheet = wb.createSheet(title);
		
		if(columMap != null && columMap.size() > 0) {
			//header정보 등록
			for(int i =0; i < columMap.size(); i++) {
				sheet.setColumnWidth(i, 500* 8);
				
				cell = getCell(sheet, 0, i);
				cell.setCellStyle(style);
				setText(cell, columMap.get(i));
			}
	
		 //body정보 등록
	      Object obj = null;
	      for(int i =0; i < valueMap.size(); i++) {
	    	  Map<String, Object> data = (Map<String, Object>) valueMap.get(i);
	    	  for(int id = 0; id < columMap.size(); id++) {
	    		  obj = data.get(columMap.get(id));
		          cell = getCell(sheet, 1 + i, id);
		          if(obj instanceof String) {
		        	 setText(cell, (String)obj);
		          }else if(obj instanceof  Integer || obj instanceof Long || obj instanceof Double || obj instanceof BigDecimal || obj instanceof Float) {
		        	  cell.setCellValue(Double.valueOf(String.valueOf(obj)));
		          }
	    	  }
	      }
			
		}
	}
	
	private HSSFCellStyle getHSSFCeLLStyleByHeader(HSSFWorkbook wb) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setTopBorderColor(HSSFColor.BLACK.index);
        
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBottomBorderColor(HSSFColor.BLACK.index);

	    style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	    style.setRightBorderColor(HSSFColor.BLACK.index);
	    
	    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	    style.setLeftBorderColor(HSSFColor.BLACK.index);
	    return style;
	}


}












































