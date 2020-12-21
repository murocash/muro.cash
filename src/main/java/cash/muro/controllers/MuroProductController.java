package cash.muro.controllers;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import cash.muro.bch.model.BchException;
import cash.muro.entities.MuroProduct;
import cash.muro.services.MuroProductService;
import cash.muro.springsecurity.authorization.MuroRole;

@Controller
public class MuroProductController {
	
	private static final String PRODUCTS = "muro-shop";
	private static final String PRODUCT_FORM = "muro-product-form";

	@Autowired
	MuroProductService productService;
	
	@GetMapping(PRODUCTS)
	public String listProducts(Model model) {
		model.addAttribute(productService.findAll());
		return PRODUCTS;
	}	
	
	@PostMapping(PRODUCT_FORM)
	@RolesAllowed(MuroRole.ADMIN)
	public String saveProduct(@ModelAttribute MuroProduct product) throws BchException {
		productService.save(product);
		return "redirect:/" + PRODUCTS;
	}

	@GetMapping(PRODUCT_FORM)
	public String newProduct(Model model) {
		model.addAttribute(new MuroProduct());
		return PRODUCT_FORM;
	}
	
	@GetMapping("delete-muro-product/{productCode}")
	@RolesAllowed(MuroRole.ADMIN)
	public String deleteProduct(@PathVariable String productCode) {
		productService.delete(productCode);
		return "redirect:/" + PRODUCTS;
	}

}
