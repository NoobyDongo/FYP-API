/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.application.product;

import com.application.AbstractController;
import com.application.annotation.AllInOneGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Ison Ho
 */
@Data
@Entity
@AllInOneGenerator
public class Origin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
}

@Controller
@RequestMapping(path = "/origin")
class OriginController extends AbstractController<OriginRepository, Origin>{}

interface OriginRepository extends CrudRepository<Origin, Integer>{}
