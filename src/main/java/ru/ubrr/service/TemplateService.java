package ru.ubrr.service;

import ru.ubrr.domain.Template;

import java.util.List;

public interface TemplateService {

    Template findById(Long id);
    List<Template> findAllTemplates();

}
