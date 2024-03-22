package vn.fs.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import vn.fs.entities.InvoiceDetail;

@Service
public class Dataaaaaaa {
	public Context setData(List<InvoiceDetail> inList) {
		Context context = new Context();
		Map<String, Object> map = new HashMap<>();
		map.put("lsDetailInvoice", inList);
		context.setVariables(map);
		return context;
	}
}
