package org.example.controller;

import org.springframework.data.convert.ReadingConverter;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

@ReadingConverter
@Validated
@RequestMapping("/task")
public class TaskController {
}
