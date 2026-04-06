# language: en
Feature: Obtener una provincia por su ID
  Como usuario de la aplicación
  Quiero obtener una provincia específica por su ID
  Para poder consultar información de la provincia

  Background:
    Given un repositorio de provincias disponible

  Scenario: Obtener una provincia existente
    Given una provincia con ID "28" y nombre "Madrid"
    When solicito la provincia con ID "28"
    Then debería obtener la provincia con nombre "Madrid"

  Scenario: Lanzar excepción cuando la provincia no existe
    Given que no existe una provincia con ID "INVALIDO"
    When solicito la provincia con ID "INVALIDO"
    Then debería lanzar una excepción de tipo NotFoundException para provincia
    And el mensaje de error para provincia debería contener "Provincia no encontrada"
