# language: en
Feature: Obtener tipos de vía paginados
  Como usuario de la aplicación
  Quiero obtener una lista de tipos de vía paginados
  Para poder consultar los tipos de vía por páginas

  Background:
    Given un repositorio de tipos de vía disponible

  Scenario: Obtener tipos de vía paginados
    Given una lista de tipos de vía disponible con 2 elementos
    And solicito la página de tipos de vía 0 con tamaño 10
    When ejecuto la consulta de tipos de vía paginados
    Then debería obtener 2 tipos de vía
    And el número de página de tipos de vía debería ser 0
    And el tamaño de página de tipos de vía debería ser 10
    And el total de elementos debería ser 2

  Scenario: Obtener lista vacía de tipos de vía
    Given una lista de tipos de vía disponible con 0 elementos
    And solicito la página de tipos de vía 0 con tamaño 10
    When ejecuto la consulta de tipos de vía paginados
    Then debería obtener 0 tipos de vía
    And la lista de tipos de vía debería estar vacía
