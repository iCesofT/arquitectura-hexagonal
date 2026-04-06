# language: en
Feature: Obtener comunidades autónomas paginadas
  Como usuario de la aplicación
  Quiero obtener una lista de comunidades autónomas paginadas
  Para poder consultar las comunidades autónomas por páginas

  Background:
    Given un repositorio de comunidades autónomas disponible

  Scenario: Obtener comunidades autónomas paginadas
    Given una lista de comunidades autónomas disponible con 2 elementos
    And solicito la página de comunidades autónomas 0 con tamaño 10
    When ejecuto la consulta de comunidades autónomas paginadas
    Then debería obtener 2 comunidades autónomas
    And el número de página de comunidades autónomas debería ser 0
    And el tamaño de página de comunidades autónomas debería ser 10
    And el total de elementos debería ser 2

  Scenario: Obtener lista vacía de comunidades autónomas
    Given una lista de comunidades autónomas disponible con 0 elementos
    And solicito la página de comunidades autónomas 0 con tamaño 10
    When ejecuto la consulta de comunidades autónomas paginadas
    Then debería obtener 0 comunidades autónomas
    And la lista de comunidades autónomas debería estar vacía
