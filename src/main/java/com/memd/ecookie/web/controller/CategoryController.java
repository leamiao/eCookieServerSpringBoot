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
import com.memd.ecookie.entity.CategoryEntity;
import com.memd.ecookie.repository.CategoryRepository;
import com.memd.ecookie.service.CategoryService;

@RestController
@RequestMapping(path="/categories") // This means URL's start with /demo (after Application path)
public class CategoryController extends BaseEntityController<CategoryEntity> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private CategoryService categoryService;
	
	@Override
	protected void cleanUpEntity(CategoryEntity entity) {
		if(entity != null) {
			entity.setProducts(null);
		}
	}

	@Override
	protected List<CategoryEntity> searchEntities(HttpServletRequest request) {
		List<SearchParam> searchParams = JsonHelper.getInstance().convertFromJsonToList(request.getParameter(Constants.FILTER), SearchParam.class);

		return this.categoryService.search(
			request.getParameter(Constants.SEARCH_TERM), 
			searchParams,
			JsonHelper.getInstance().convertFromJsonToList(
				request.getParameter(Constants.SORT), SortParam.class)
		);
	}

	@Override
	protected Page<CategoryEntity> searchEntities(HttpServletRequest request, Pageable pageRequest) {
		List<SearchParam> searchParams = JsonHelper.getInstance().convertFromJsonToList(request.getParameter(Constants.FILTER), SearchParam.class);

		return this.categoryService.search(
			request.getParameter(Constants.SEARCH_TERM), 
			searchParams,
			JsonHelper.getInstance().convertFromJsonToList(request.getParameter(Constants.SORT), SortParam.class),
			pageRequest
		);
	}
	
	@Override
	protected CategoryEntity createEntity(CategoryEntity entity) {
		return categoryService.saveCategoryEntity(entity, true);
	}

	@Override
	protected CategoryEntity saveEntitiy(CategoryEntity entity) {
		return categoryService.saveCategoryEntity(entity, true);
	}

	@Override
	protected void deleteEntityById(Long entityId) {
		this.categoryRepository.deleteById(entityId);
	}
	
	@Override
	protected CategoryEntity getEntityById(Long entityId) {
		Optional<CategoryEntity> optional = this.categoryRepository.findById(entityId);
		if (optional.isEmpty()) return null;
		return optional.get();
	}
    
	@Override
	protected CategoryEntity patchEntity(Long entityId, CategoryEntity entity) {
		if (entity == null) {
			return null;
		}
		entity.setId(entityId);
		return this.categoryService.patchCategoryEntity(entity);
	}
    
	@Override
	protected CategoryEntity putEntity(Long entityId, CategoryEntity entity) {
		if (entity == null) {
			return null;
		}
		entity.setId(entityId);

		return this.categoryService.saveCategoryEntity(entity, false);
	}

	@Override
	protected List<CategoryEntity> saveEntities(List<CategoryEntity> entities) {
		return categoryService.saveCategoryEntitys(entities);
	}
}
