package com.memd.ecookie.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.memd.ecookie.common.Constants;
import com.memd.ecookie.common.SearchParam;
import com.memd.ecookie.common.SortParam;
import com.memd.ecookie.entity.CategoryEntity;
import com.memd.ecookie.exception.ServiceException;
import com.memd.ecookie.repository.CategoryRepository;
import com.memd.ecookie.specification.DefaultSpecification;

@Service
@Transactional
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<CategoryEntity> saveCategoryEntitys(List<CategoryEntity> list) {
        if (list == null) {
            return null;
        }

        List<CategoryEntity> results = new ArrayList<CategoryEntity>(list.size());
        for (CategoryEntity entity : list) {
            CategoryEntity saved = saveCategoryEntity(entity, false);
            if (saved != null) {
                results.add(saved);
            }
        }

        return results;
    }

    public CategoryEntity saveCategoryEntity(CategoryEntity entity, boolean isPatch) {
        if (entity == null) {
            return null;
        }

        CategoryEntity existed = null;
        Long id = entity.getId();
        if (id != null && id > 0) {
            Optional<CategoryEntity> optional = this.categoryRepository.findById(id);
            if (optional.isPresent()) {
                existed = optional.get();
                if (!existed.getName().equals(entity.getName())) {
                    CategoryEntity existed2 = this.categoryRepository.findByName(entity.getName());
                    if (existed2 != null) {
                        throw new ServiceException(Constants.DUPLICATE_ENTITY_NAME,
                                new String[] { Constants.PRODUCT_ENTITY, entity.getName() });
                    }
                }
            }
        }

        if (existed == null) {
            existed = this.categoryRepository.findByName(entity.getName());
        }

        if (existed != null) {
            // update
            if (!isPatch || entity.isModified("name")) {
                existed.setName(entity.getName());
            }
            return existed;
        }

        // create
        entity.setId(null);

        return this.categoryRepository.save(entity);
    }

    public CategoryEntity patchCategoryEntity(CategoryEntity entity) {
        if (entity == null) {
            return null;
        }

        CategoryEntity existed = null;
        Long id = entity.getId();
        if (id != null && id > 0) {
            Optional<CategoryEntity> optional = this.categoryRepository.findById(id);
            if (optional.isPresent()) {
                existed = optional.get();
                if (!existed.getName().equals(entity.getName())) {
                    CategoryEntity existed2 = this.categoryRepository.findByName(entity.getName());
                    if (existed2 != null) {
                        throw new ServiceException(Constants.DUPLICATE_ENTITY_NAME,
                                new String[] { Constants.PRODUCT_ENTITY, entity.getName() });
                    }
                }
            }
        }

        if (existed == null) {
            throw new ServiceException(Constants.ENTITY_NOT_EXIST_FOR_PATCH,
                    new Object[] { Constants.PRODUCT_ENTITY, entity.getId() });
        }

        // update
        if (entity.isModified("name")) {
            existed.setName(entity.getName());
        }

        return existed;
    }

    public List<CategoryEntity> search(String searchTerm, List<SearchParam> searchParams, List<SortParam> sortParams) {
        List<CategoryEntity> list = this.categoryRepository
                .findAll(createSearchSpecification(searchTerm, searchParams, sortParams));

        return list;
    }

    public Page<CategoryEntity> search(String searchTerm, List<SearchParam> searchParams, List<SortParam> sortParams,
            Pageable pageRequest) {
        Page<CategoryEntity> page = this.categoryRepository
                .findAll(createSearchSpecification(searchTerm, searchParams, sortParams), pageRequest);
        if (page != null) {
            List<CategoryEntity> list = page.getContent();
        }

        return page;
    }

    private Specification<CategoryEntity> createSearchSpecification(String searchTerm, List<SearchParam> searchParams,
            List<SortParam> sortParams) {
        DefaultSpecification<CategoryEntity> spec = new DefaultSpecification<>("searchValue", searchTerm, sortParams,
                searchParams);
        return spec;
    }

    public CategoryEntity fetchCategory(Long categoryId, CategoryEntity category) {
        Long id = categoryId;
        if (id == null) {
            if (category != null) {
                id = category.getId();
            }
        }

        if (id != null) {
            Optional<CategoryEntity> optional = this.categoryRepository.findById(id);
            if (!optional.isEmpty()) {
                return optional.get();
            }
        }

        if (category != null && category.getName() != null) {
            return this.categoryRepository.findByName(category.getName());
        }

        return null;
    }
}
