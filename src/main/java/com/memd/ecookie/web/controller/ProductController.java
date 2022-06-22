package com.memd.ecookie.web.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memd.ecookie.common.Constants;
import com.memd.ecookie.common.JsonHelper;
import com.memd.ecookie.common.SearchParam;
import com.memd.ecookie.common.SortParam;
import com.memd.ecookie.entity.ProductEntity;
import com.memd.ecookie.repository.ProductRepository;
import com.memd.ecookie.service.ProductService;

@RestController
@RequestMapping(path="/products") // This means URL's start with /demo (after Application path)
public class ProductController extends BaseEntityController<ProductEntity> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private ProductService productService;
	
	@Override
	protected void cleanUpEntity(ProductEntity entity) {
		if(entity != null && entity.getCategory() != null) {
			entity.getCategory().setProducts(null);
		}
	}

	@Override
	protected List<ProductEntity> searchEntities(HttpServletRequest request) {
		List<SearchParam> searchParams = JsonHelper.getInstance().convertFromJsonToList(request.getParameter(Constants.FILTER), SearchParam.class);

		return this.productService.search(
			request.getParameter(Constants.SEARCH_TERM), 
			searchParams,
			JsonHelper.getInstance().convertFromJsonToList(
				request.getParameter(Constants.SORT), SortParam.class)
		);
	}

	@Override
	protected Page<ProductEntity> searchEntities(HttpServletRequest request, Pageable pageRequest) {
		List<SearchParam> searchParams = JsonHelper.getInstance().convertFromJsonToList(request.getParameter(Constants.FILTER), SearchParam.class);

		return this.productService.search(
			request.getParameter(Constants.SEARCH_TERM), 
			searchParams,
			JsonHelper.getInstance().convertFromJsonToList(request.getParameter(Constants.SORT), SortParam.class),
			pageRequest
		);
	}
	
	@Override
	protected ProductEntity createEntity(ProductEntity entity) {
		return productService.saveProductEntity(entity, true);
	}

	@Override
	protected ProductEntity saveEntitiy(ProductEntity entity) {
		return productService.saveProductEntity(entity, true);
	}

	@Override
	protected void deleteEntityById(Long entityId) {
		this.productRepository.deleteById(entityId);
	}
	
	@Override
	protected ProductEntity getEntityById(Long entityId) {
		Optional<ProductEntity> optional = this.productRepository.findById(entityId);
		if (optional.isEmpty()) return null;
		return optional.get();
	}
    
	@Override
	protected ProductEntity patchEntity(Long entityId, ProductEntity entity) {
		if (entity == null) {
			return null;
		}
		entity.setId(entityId);
		return this.productService.patchProductEntity(entity);
	}
    
	@Override
	protected ProductEntity putEntity(Long entityId, ProductEntity entity) {
		if (entity == null) {
			return null;
		}
		entity.setId(entityId);

		return this.productService.saveProductEntity(entity, false);
	}

	@Override
	protected List<ProductEntity> saveEntities(List<ProductEntity> entities) {
		return productService.saveProductEntitys(entities);
	}
}
