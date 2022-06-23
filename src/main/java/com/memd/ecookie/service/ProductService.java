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
import com.memd.ecookie.entity.ProductEntity;
import com.memd.ecookie.exception.ServiceException;
import com.memd.ecookie.repository.ProductRepository;
import com.memd.ecookie.specification.DefaultSpecification;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    public List<ProductEntity> saveProductEntitys(List<ProductEntity> list) {
        if (list == null) {
            return null;
        }

        List<ProductEntity> results = new ArrayList<ProductEntity>(list.size());
        for (ProductEntity entity : list) {
            ProductEntity saved = saveProductEntity(entity, false);
            if (saved != null) {
                results.add(saved);
            }
        }

        return results;
    }

    public ProductEntity saveProductEntity(ProductEntity entity, boolean isPatch) {
        if (entity == null) {
            return null;
        }

        Double price = entity.getPrice();
        if (price != null && price < 0) {
            throw new ServiceException(Constants.PRODUCT_PRICE_SHOULD_NOT_BE_NEGATIVE,
                    new Object[] { entity.getName(), price });
        }

        ProductEntity existed = null;
        Long id = entity.getId();
        if (id != null && id > 0) {
            Optional<ProductEntity> optional = this.productRepository.findById(id);
            if (optional.isPresent()) {
                existed = optional.get();
                if (!existed.getName().equals(entity.getName())) {
                    ProductEntity existed2 = this.productRepository.findByName(entity.getName());
                    if (existed2 != null) {
                        throw new ServiceException(Constants.DUPLICATE_ENTITY_NAME,
                                new String[] { Constants.PRODUCT_ENTITY, entity.getName() });
                    }
                }
            }
        }

        if (existed == null) {
            existed = this.productRepository.findByName(entity.getName());
        }

        Long categoryId = entity.getCategoryId();
        CategoryEntity category = this.categoryService.fetchCategory(categoryId, entity.getCategory());

        Date currentDate = new Date();

        if (existed != null) {
            // update
            if (category != null && !category.getId().equals(existed.getCategory().getId())) {
                existed.setCategory(category);
            }
            if (!isPatch || entity.isModified("name")) {
                existed.setName(entity.getName());
            }
            existed.setUpdatedBy(entity.getUpdatedBy());
            existed.setUpdatedDate(currentDate);
            if (!isPatch || entity.isModified("imageUrl")) {
                existed.setImageUrl(entity.getImageUrl());
            }
            if (!isPatch || entity.isModified("price")) {
                existed.setPrice(entity.getPrice());
            }
            if (!isPatch || entity.isModified("description")) {
                existed.setDescription(entity.getDescription());
            }

            return existed;
        }

        // create
        if (category == null) {
            throw new ServiceException(Constants.CATEGORY_NOT_EXIST,
                    new Object[] { Constants.PRODUCT_ENTITY, categoryId });
        }
        entity.setId(null);
        entity.setCategory(category);
        entity.setCreatedDate(currentDate);
        entity.setUpdatedDate(currentDate);

        entity.setCreatedBy(entity.getUpdatedBy());
        entity.setCreatedDate(currentDate);

        if (entity.getImageUrl() == null) {
            entity.setImageUrl(Constants.DEFAULT_IMG_URL);
        }
        return this.productRepository.save(entity);
    }

    public ProductEntity patchProductEntity(ProductEntity entity) {
        if (entity == null) {
            return null;
        }

        Double price = entity.getPrice();
        if (price != null && price < 0) {
            throw new ServiceException(Constants.PRODUCT_PRICE_SHOULD_NOT_BE_NEGATIVE,
                    new Object[] { entity.getName(), price });
        }

        ProductEntity existed = null;
        Long id = entity.getId();
        if (id != null && id > 0) {
            Optional<ProductEntity> optional = this.productRepository.findById(id);
            if (optional.isPresent()) {
                existed = optional.get();
                if (!existed.getName().equals(entity.getName())) {
                    ProductEntity existed2 = this.productRepository.findByName(entity.getName());
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

        Date currentDate = new Date();

        // update
        if (entity.isModified("name")) {
            existed.setName(entity.getName());
        }
        existed.setUpdatedBy(entity.getUpdatedBy());
        existed.setUpdatedDate(currentDate);
        if (entity.isModified("imageUrl")) {
            existed.setImageUrl(entity.getImageUrl());
        }
        if (entity.isModified("price")) {
            existed.setPrice(entity.getPrice());
        }
        if (entity.isModified("description")) {
            existed.setDescription(entity.getDescription());
        }

        return existed;
    }

    public List<ProductEntity> search(String searchTerm, List<SearchParam> searchParams, List<SortParam> sortParams) {
        List<ProductEntity> list = this.productRepository
                .findAll(createSearchSpecification(searchTerm, searchParams, sortParams));

        return list;
    }

    public Page<ProductEntity> search(String searchTerm, List<SearchParam> searchParams, List<SortParam> sortParams,
            Pageable pageRequest) {
        Page<ProductEntity> page = this.productRepository
                .findAll(createSearchSpecification(searchTerm, searchParams, sortParams), pageRequest);
        if (page != null) {
            List<ProductEntity> list = page.getContent();
        }

        return page;
    }

    private Specification<ProductEntity> createSearchSpecification(String searchTerm, List<SearchParam> searchParams,
            List<SortParam> sortParams) {
        DefaultSpecification<ProductEntity> spec = new DefaultSpecification<>("searchValue", searchTerm, sortParams,
                searchParams);
        return spec;
    }
}
