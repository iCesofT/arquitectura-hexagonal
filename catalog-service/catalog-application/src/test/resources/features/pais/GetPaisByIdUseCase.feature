# language: en
Feature: Obtener un país por su ID
  Como usuario de la aplicación
  Quiero obtener un país específico por su ID
  Para poder consultar información del país

  Background:
    Given un repositorio de países disponible

  Scenario: Obtener un país existente
    Given un país con ID "724" y nombre "España"
    When solicito el país con ID "724"
    Then debería obtener el país con nombre "España"

  Scenario: Lanzar excepción cuando el país no existe
    Given que no existe un país con ID "INVALIDO"
    When solicito el país con ID "INVALIDO"
    Then debería lanzar una excepción de tipo NotFoundException
    And el mensaje de error debería contener "País no encontrado"

  Scenario: Verificar llamadas al repositorio
    Given un país con ID "724" y nombre "España"
    When solicito el país con ID "724" dos veces
    Then el repositorio debería haber sido llamado dos veces
