package ru.rgs.botmon.controller;


import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Validated
@RequestMapping(path = "/web")
public class WebController {
}
