# language: en
Feature: Obtener provincias paginadas
  Como usuario de la aplicación
  Quiero obtener una lista de provincias paginadas
  Para poder consultar las provincias por páginas

  Background:
    Given un repositorio de provincias disponible

  Scenario: Obtener provincias paginadas
    Given una lista de provincias disponible con 2 elementos
    And solicito la página de provincias 0 con tamaño 10
    When ejecuto la consulta de provincias paginadas
    Then debería obtener 2 provincias
    And el número de página de provincias debería ser 0
    And el tamaño de página de provincias debería ser 10
    And el total de elementos debería ser 2

  Scenario: Obtener lista vacía de provincias
    Given una lista de provincias disponible con 0 elementos
    And solicito la página de provincias 0 con tamaño 10
    When ejecuto la consulta de provincias paginadas
    Then debería obtener 0 provincias
    And la lista de provincias debería estar vacía
