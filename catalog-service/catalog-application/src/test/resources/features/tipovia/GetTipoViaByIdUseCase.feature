# language: en
Feature: Obtener un tipo de vía por su ID
  Como usuario de la aplicación
  Quiero obtener un tipo de vía específico por su ID
  Para poder consultar información del tipo de vía

  Background:
    Given un repositorio de tipos de vía disponible

  Scenario: Obtener un tipo de vía existente
    Given un tipo de vía con ID "01" y nombre "Calle"
    When solicito el tipo de vía con ID "01"
    Then debería obtener el tipo de vía con nombre "Calle"

  Scenario: Lanzar excepción cuando el tipo de vía no existe
    Given que no existe un tipo de vía con ID "INVALIDO"
    When solicito el tipo de vía con ID "INVALIDO"
    Then debería lanzar una excepción de tipo NotFoundException para tipo de vía
    And el mensaje de error para tipo de vía debería contener "Tipo de vía no encontrado"
