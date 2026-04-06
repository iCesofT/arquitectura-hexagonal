# language: en
Feature: Obtener localidades paginadas
  Como usuario de la aplicación
  Quiero obtener un listado paginado de localidades
  Para poder consultar múltiples localidades de forma paginada

  Background:
    Given un repositorio de localidades disponible

  Scenario: Obtener localidades paginadas
    Given una lista de localidades disponible con 2 elementos
    And solicito la página de localidades 0 con tamaño 2
    When ejecuto la consulta de localidades paginadas
    Then debería obtener 2 localidades
