# language: en
Feature: Obtener países paginados
  Como usuario de la aplicación
  Quiero obtener una lista de países paginados
  Para poder consultar los países por páginas

  Background:
    Given un repositorio de países disponible

  Scenario: Obtener países paginados
    Given una lista de países disponible con 3 elementos
    And solicito la página de países 0 con tamaño 10
    When ejecuto la consulta de países paginados
    Then debería obtener 3 países
    And el número de página de países debería ser 0
    And el tamaño de página de países debería ser 10
    And el total de elementos debería ser 3

  Scenario: Obtener lista vacía de países
    Given una lista de países disponible con 0 elementos
    And solicito la página de países 0 con tamaño 10
    When ejecuto la consulta de países paginados
    Then debería obtener 0 países
    And la lista de países debería estar vacía
