# language: en
Feature: Obtener una comunidad autónoma por su ID
  Como usuario de la aplicación
  Quiero obtener una comunidad autónoma específica por su ID
  Para poder consultar información de la comunidad autónoma

  Background:
    Given un repositorio de comunidades autónomas disponible

  Scenario: Obtener una comunidad autónoma existente
    Given una comunidad autónoma con ID "01" y nombre "Andalucía"
    When solicito la comunidad autónoma con ID "01"
    Then debería obtener la comunidad autónoma con nombre "Andalucía"

  Scenario: Lanzar excepción cuando la comunidad autónoma no existe
    Given que no existe una comunidad autónoma con ID "INVALIDO"
    When solicito la comunidad autónoma con ID "INVALIDO"
    Then debería lanzar una excepción de tipo NotFoundException para comunidad autónoma
    And el mensaje de error para comunidad autónoma debería contener "Comunidad Autónoma no encontrada"
