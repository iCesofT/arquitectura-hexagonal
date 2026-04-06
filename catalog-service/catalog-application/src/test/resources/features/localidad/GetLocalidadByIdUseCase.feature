# language: en
Feature: Obtener una localidad por su ID
  Como usuario de la aplicación
  Quiero obtener una localidad específica por su ID
  Para poder consultar información de la localidad

  Background:
    Given un repositorio de localidades disponible

  Scenario: Obtener una localidad existente
    Given una localidad con ID "28079" y nombre "Madrid"
    When solicito la localidad con ID "28079"
    Then debería obtener la localidad con nombre "Madrid"

  Scenario: Lanzar excepción cuando la localidad no existe
    Given que no existe una localidad con ID "INVALIDO"
    When solicito la localidad con ID "INVALIDO"
    Then debería lanzar una excepción de tipo NotFoundException para localidad
