package vn.fs.controller.admin;

import java.security.Principal;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.fs.entities.OrderDetail;
import vn.fs.entities.User;
import vn.fs.repository.OrderDetailRepository;
import vn.fs.repository.UserRepository;


@Controller
public class ReportController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	OrderDetailRepository orderDetailRepository;

	// Statistics by product sold
	@GetMapping(value = "/admin/reports")
	public String report(Model model, Principal principal) throws SQLException {
		User user = userRepository.findByEmail(principal.getName());
		model.addAttribute("user", user);
		OrderDetail orderDetail = new OrderDetail();
		model.addAttribute("orderDetail", orderDetail);
		List<Object[]> listReportCommon = orderDetailRepository.repo();
		float total = 0.0f;

		for (Object[] array : listReportCommon) {
		    if (array.length >= 2 && array[2] instanceof Number) {
		        total += ((Number) array[2]).doubleValue();
		    }
		}
		DecimalFormat decimalFormat = new DecimalFormat("#.##");

		model.addAttribute("totalPrice",decimalFormat.format(total));
		model.addAttribute("listReportCommon", listReportCommon);

		return "admin/statistical";
	}

	// Statistics by category sold
	@RequestMapping(value = "/admin/reportCategory")
	public String reportcategory(Model model, Principal principal) throws SQLException {
		User user = userRepository.findByEmail(principal.getName());
		model.addAttribute("user", user);

		OrderDetail orderDetail = new OrderDetail();
		model.addAttribute("orderDetail", orderDetail);
		List<Object[]> listReportCommon = orderDetailRepository.repoWhereCategory();
		model.addAttribute("listReportCommon", listReportCommon);
		float total = 0.0f;

		for (Object[] array : listReportCommon) {
		    if (array.length >= 2 && array[2] instanceof Number) {
		        total += ((Number) array[2]).doubleValue();
		    }
		}
		DecimalFormat decimalFormat = new DecimalFormat("#.##");

		model.addAttribute("totalPrice",decimalFormat.format(total));
		return "admin/statiscalCate";
	}

	// Statistics of products sold by year
	@RequestMapping(value = "/admin/reportYear")
	public String reportyear(Model model, Principal principal) throws SQLException {
		User user = userRepository.findByEmail(principal.getName());
		model.addAttribute("user", user);

		OrderDetail orderDetail = new OrderDetail();
		model.addAttribute("orderDetail", orderDetail);
		List<Object[]> listReportYearOn = orderDetailRepository.repoOnWhereYear();
		List<Object[]> listReportYearOff = orderDetailRepository.repoOffWhereYear();

		model.addAttribute("listReportYearOn", listReportYearOn);
		model.addAttribute("listReportYearOff", listReportYearOff);

		float totalOn = 0.0f;
		float totalOff = 0.0f;

		for (Object[] array : listReportYearOn) {
		    if (array.length >= 2 && array[2] instanceof Number) {
		    	totalOn += ((Number) array[2]).doubleValue();
		    }
		}
		for (Object[] array : listReportYearOff) {
		    if (array.length >= 2 && array[2] instanceof Number) {
		    	totalOff += ((Number) array[2]).doubleValue();
		    }
		}
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		model.addAttribute("totalPrice",decimalFormat.format(totalOff+totalOn));
		return "admin/statiscalYear";
	}

	// Statistics of products sold by month
	@RequestMapping(value = "/admin/reportMonth")
	public String reportmonth(Model model, Principal principal) throws SQLException {
		User user = userRepository.findByEmail(principal.getName());
		model.addAttribute("user", user);

		OrderDetail orderDetail = new OrderDetail();
		model.addAttribute("orderDetail", orderDetail);
		List<Object[]> listReportMonthOn = orderDetailRepository.repoWhereMonthOn();
		List<Object[]> listReportMonthOff = orderDetailRepository.repoWhereMonthOff();

		model.addAttribute("listReportMonthOn", listReportMonthOn);
		model.addAttribute("listReportMonthOff", listReportMonthOff);


		return "admin/statiscalMonth";
	}

	// Statistics of products sold by quarter
	@RequestMapping(value = "/admin/reportQuarter")
	public String reportquarter(Model model, Principal principal) throws SQLException {
		User user = userRepository.findByEmail(principal.getName());
		model.addAttribute("user", user);

		OrderDetail orderDetail = new OrderDetail();
		model.addAttribute("orderDetail", orderDetail);
		List<Object[]> listReportQUARTEROn = orderDetailRepository.repoWhereQUARTEROn();
		List<Object[]> listReportQUARTEROff = orderDetailRepository.repoWhereQUARTEROff();

		model.addAttribute("listReportQUARTEROn", listReportQUARTEROn);
		model.addAttribute("listReportQUARTEROff", listReportQUARTEROff);


		return "admin/statiscalQuy";
	}

	// Statistics by user
	@RequestMapping(value = "/admin/reportOrderCustomer")
	public String reportordercustomer(Model model, Principal principal) throws SQLException {
		User user = userRepository.findByEmail(principal.getName());
		model.addAttribute("user", user);

		OrderDetail orderDetail = new OrderDetail();
		model.addAttribute("orderDetail", orderDetail);
		List<Object[]> listReportCommon = orderDetailRepository.reportCustommer();
		model.addAttribute("listReportCommon", listReportCommon);

		return "admin/statiscalUser";
	}
	

}
