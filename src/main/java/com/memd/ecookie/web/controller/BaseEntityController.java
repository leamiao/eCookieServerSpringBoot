package com.memd.ecookie.web.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.memd.ecookie.common.Constants;
import com.memd.ecookie.common.JsonResponseModel;
import com.memd.ecookie.entity.BaseEntity;
import com.memd.ecookie.exception.ServiceException;
import com.memd.ecookie.web.util.I18nManager;
import com.memd.ecookie.web.util.OffsetBasedPageRequest;

public class BaseEntityController<E extends BaseEntity> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseEntityController.class);

    @Autowired
    protected I18nManager i18nManager;

    @GetMapping(produces = { "application/json;charset=UTF-8" })
    public ResponseEntity<JsonResponseModel<List<E>>> list(HttpServletRequest request) {
        JsonResponseModel<List<E>> model = new JsonResponseModel<>();
        String message = null;
        HttpStatus status = HttpStatus.OK;

        try {
            int startIndex = 0;
            int limit = -9999;
            int total = 0;

            List<E> list = new ArrayList<>();
            if (request.getParameter(Constants.START) != null) {
                startIndex = Integer.parseInt(request.getParameter(Constants.START));
            }
            if (request.getParameter(Constants.LIMIT) != null) {
                limit = Integer.parseInt(request.getParameter(Constants.LIMIT));
            }

            if (limit > 0) {
                Pageable pageRequest = null;
                if (request.getParameter(Constants.SORT) != null) {
                    pageRequest = new OffsetBasedPageRequest(startIndex, limit, Sort.unsorted());
                } else {
                    pageRequest = new OffsetBasedPageRequest(startIndex, limit);
                }

                Page<E> page = this.searchEntities(request, pageRequest);
                if (page != null) {
                    list = page.getContent();
                    total = (int) page.getTotalElements();
                }
            } else {
                list = this.searchEntities(request);
            }

            this.cleanUpEntities(list);

            model.setData(list);
            model.setTotal(total);
        } catch (ServiceException e) {
            String errorMessage = e.getMessage();
            message = this.i18nManager.getString(request, errorMessage, errorMessage, e.getParams());
            model.setSuccess(false);
            LOGGER.error(message, e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            message = e.getMessage();
            model.setSuccess(false);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        model.setMessage(message);

        return new ResponseEntity<>(model, status);
    }

    @PostMapping(value = "/create", produces = { "application/json;charset=UTF-8" })
    public ResponseEntity<JsonResponseModel<E>> create(@RequestBody E entity, HttpServletRequest request) {
        JsonResponseModel<E> model = new JsonResponseModel<>();
        String message = null;
        HttpStatus status = HttpStatus.OK;

        try {
            if (entity != null) {
                E createdEntity = this.createEntity(entity);
                this.cleanUpEntity(createdEntity);
                model.setData(createdEntity);
            }
        } catch (ServiceException e) {
            String errorMessage = e.getMessage();
            message = this.i18nManager.getString(request, errorMessage, errorMessage, e.getParams());
            model.setSuccess(false);
            LOGGER.error(message, e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            message = e.getMessage();
            model.setSuccess(false);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        model.setMessage(message);

        return new ResponseEntity<>(model, status);
    }

    @PostMapping(value = "/update", produces = { "application/json;charset=UTF-8" })
    public ResponseEntity<JsonResponseModel<E>> update(@RequestBody E entity, HttpServletRequest request) {
        JsonResponseModel<E> model = new JsonResponseModel<>();
        String message = null;
        HttpStatus status = HttpStatus.OK;

        try {
            if (entity != null) {
                E savedEntity = this.saveEntitiy(entity);
                this.cleanUpEntity(savedEntity);
                model.setData(savedEntity);
            }
        } catch (ServiceException e) {
            String errorMessage = e.getMessage();
            message = this.i18nManager.getString(request, errorMessage, errorMessage, e.getParams());
            model.setSuccess(false);
            LOGGER.error(message, e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            message = e.getMessage();
            model.setSuccess(false);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        model.setMessage(message);

        return new ResponseEntity<>(model, status);
    }

    @DeleteMapping(value = "/{entityId}", produces = { "application/json;charset=UTF-8" })
    public ResponseEntity<JsonResponseModel<String>> delete(@PathVariable(name = "entityId") Long entityId,
            HttpServletRequest request) {
        JsonResponseModel<String> model = new JsonResponseModel<>();
        String message = null;
        HttpStatus status = HttpStatus.OK;

        try {
            if (entityId != null) {
                this.deleteEntityById(entityId);
            }
        } catch (ServiceException e) {
            String errorMessage = e.getMessage();
            message = this.i18nManager.getString(request, errorMessage, errorMessage, e.getParams());
            model.setSuccess(false);
            LOGGER.error(message, e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            message = e.getMessage();
            model.setSuccess(false);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        model.setMessage(message);

        return new ResponseEntity<>(model, status);
    }

    @GetMapping(path = "/{entityId}")
    public ResponseEntity<JsonResponseModel<E>> get(@PathVariable(name = "entityId") Long entityId,
            HttpServletRequest request) {
        JsonResponseModel<E> model = new JsonResponseModel<>();
        String message = null;
        HttpStatus status = HttpStatus.OK;
        try {
            E entity = this.getEntityById(entityId);
            this.cleanUpEntity(entity);
            model.setData(entity);
        } catch (ServiceException e) {
            String errorMessage = e.getMessage();
            message = this.i18nManager.getString(request, errorMessage, errorMessage, e.getParams());
            model.setSuccess(false);
            LOGGER.error(message, e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            message = e.getMessage();
            model.setSuccess(false);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        model.setMessage(message);

        return new ResponseEntity<>(model, status);
    }

    @PatchMapping(path = "/{entityId}")
    public ResponseEntity<JsonResponseModel<E>> patch(@PathVariable(name = "entityId") Long entityId,
            @RequestBody E entity, HttpServletRequest request) {
        JsonResponseModel<E> model = new JsonResponseModel<>();
        String message = null;
        HttpStatus status = HttpStatus.OK;
        try {
            E saved = this.patchEntity(entityId, entity);
            this.cleanUpEntity(saved);
            model.setData(saved);
        } catch (ServiceException e) {
            String errorMessage = e.getMessage();
            message = this.i18nManager.getString(request, errorMessage, errorMessage, e.getParams());
            model.setSuccess(false);
            LOGGER.error(message, e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            message = e.getMessage();
            model.setSuccess(false);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        model.setMessage(message);

        return new ResponseEntity<>(model, status);
    }

    @PutMapping(path = "/{entityId}")
    public ResponseEntity<JsonResponseModel<E>> put(@PathVariable(name = "entityId") Long entityId,
            @RequestBody E entity, HttpServletRequest request) {
        JsonResponseModel<E> model = new JsonResponseModel<>();
        String message = null;
        HttpStatus status = HttpStatus.OK;

        try {
            E saved = this.putEntity(entityId, entity);
            this.cleanUpEntity(saved);
            model.setData(saved);
        } catch (ServiceException e) {
            String errorMessage = e.getMessage();
            message = this.i18nManager.getString(request, errorMessage, errorMessage, e.getParams());
            model.setSuccess(false);
            LOGGER.error(message, e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            message = e.getMessage();
            model.setSuccess(false);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        model.setMessage(message);

        return new ResponseEntity<>(model, status);
    }

    @PostMapping()
    public ResponseEntity<JsonResponseModel<List<E>>> saveList(@RequestBody List<E> entities,
            HttpServletRequest request) {
        JsonResponseModel<List<E>> model = new JsonResponseModel<>();
        String message = null;
        HttpStatus status = HttpStatus.OK;

        try {
            List<E> saved = this.saveEntities(entities);
            this.cleanUpEntities(saved);
            model.setData(saved);
        } catch (ServiceException e) {
            String errorMessage = e.getMessage();
            message = this.i18nManager.getString(request, errorMessage, errorMessage, e.getParams());
            model.setSuccess(false);
            LOGGER.error(message, e);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            message = e.getMessage();
            model.setSuccess(false);
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        model.setMessage(message);

        return new ResponseEntity<>(model, status);
    }

    protected void cleanUpEntities(List<E> list) {
        if (list != null) {
            for (E entity : list) {
                this.cleanUpEntity(entity);
            }
        }
    }

    protected void cleanUpEntity(E entity) {
    }

    protected List<E> searchEntities(HttpServletRequest request) {
        return null;
    }

    protected Page<E> searchEntities(HttpServletRequest request, Pageable pageRequest) {
        return null;
    }

    protected E createEntity(E entity) {
        return entity;
    }

    protected E saveEntitiy(E entity) {
        return entity;
    }

    protected void deleteEntityById(Long entityId) {
    }

    protected E getEntityById(Long entityId) {
        return null;
    }

    protected E patchEntity(Long entityId, E entity) {
        return null;
    }

    protected E putEntity(Long entityId, E entity) {
        return null;
    }

    protected List<E> saveEntities(List<E> entities) {
        return null;
    }

}
