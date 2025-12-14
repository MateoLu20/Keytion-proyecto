# Keytion

Keytion es una aplicación móvil nativa desarrollada para la plataforma Android utilizando Kotlin y Jetpack Compose. El proyecto está diseñado como una suite de herramientas esenciales para la higiene digital y la gestión de la seguridad de contraseñas. Su propósito principal es capacitar a los usuarios para que puedan crear, evaluar y gestionar claves de acceso de alta fortaleza, mitigando así el riesgo de ataques basados en credenciales débiles o comprometidas.
En la actualidad, la seguridad de las cuentas en línea depende fundamentalmente de la fortaleza de las contraseñas. El problema central es doble: Contraseñas débiles y la falta de conciencia. Keytion resuelve esto proporcionando una herramienta instantánea y visual que no solo evalúa el riesgo (Comprobador) sino que también ofrece una solución automática (Generador).

## Plataforma

- Sistema Operativo Objetivo: Android (versión mínima recomendada 7.0 / API 24+).

- Lenguaje de Programación: Kotlin.

- Framework UI: Jetpack Compose (Material 3).

- Arquitectura: Actividad Única (Single-Activity Architecture) con gestión de estado para la navegación entre pantallas.

## Interfaz de Usuario

La interfaz está diseñada para ser intuitiva, minimalista y visualmente atractiva, utilizando un esquema de color oscuro (Dark Theme) basado en la especificación Material 3 de Google.

- Navegación: Simple, basada en un menú principal con botones grandes y claros.

- Feedback Visual: Uso intensivo de indicadores de color (verde, amarillo, rojo) y barras de progreso para ofrecer retroalimentación inmediata sobre la fuerza de la contraseña.

- Usabilidad: Todos los elementos interactivos (botones, iconos) son grandes para facilitar la interacción táctil.

## Funcionalidad

La aplicación se centra en dos módulos principales:

### Módulo Comprobador de Contraseñas (Password Checker)

- Entrada: Campo de texto con ocultación de caracteres (PasswordVisualTransformation).

- Análisis en Tiempo Real: Evalúa la clave mientras el usuario escribe.

- Indicador de Fuerza: Muestra una barra de progreso que va de "Baja" (rojo) a "Excelente" (azul).

- Checklist: Detalla qué requisitos de seguridad se cumplen (ej: 8+ caracteres, Mayúsculas, Números, Símbolos).

### Módulo Generador de Contraseñas (Password Generator)

- Personalización: Controles deslizantes y casillas de verificación para definir:

- Longitud de la contraseña (ej: 8 a 30 caracteres).

- Inclusión de mayúsculas, minúsculas, números y símbolos.

- Generación: Un botón dispara la generación de una clave criptográficamente aleatoria basada en los parámetros seleccionados.

- Funcionalidad de Copia: Botón dedicado para copiar la contraseña generada al portapapeles.

## Diseño (Wireframes o Esquemas de Página)

El diseño sigue un patrón de cards y listas para organizar la información de forma jerárquica, optimizado para la lectura rápida en dispositivos móviles. El menu principal y el apartado de "Comprobador" van a tener un aspecto similar al siguiente:

<img width="328" height="808" alt="pantalla1" src="https://github.com/user-attachments/assets/ebe39840-34a7-408a-a287-917180a72e1c" />
<img width="328" height="808" alt="pantalla2" src="https://github.com/user-attachments/assets/a67f4fbb-40a4-43bd-8620-25a2d7d75b06" />

## Registro de Cambios (Changelog)

Esta sección documenta la evolución del proyecto Keytion a través de los módulos / semanas, cumpliendo con la necesidad de trazar los avances en el desarrollo.

### [0.1.0]

Añadido (Integración UI):

- Implementación de la Arquitectura de Actividad Única y la navegación basada en el estado de Compose.

- Implementación completa de la interfaz visual del Módulo Comprobador de Contraseñas (PasswordCheckerScreen).

- Inclusión de OutlinedTextField con PasswordVisualTransformation para la entrada de contraseña.

- Integración de componentes de Feedback Visual (PasswordStrengthBar y RequirementChecklist) con colores dinámicos.
  
- Diseño de Menús: Definición de la ubicación para las futuras opciones de Configuración/Ayuda usando el concepto de Menú de Opciones (Options Menu).

<img width="368" height="822" alt="image" src="https://github.com/user-attachments/assets/482c2cf3-ba10-4180-a917-e68d64def867" />

<img width="366" height="820" alt="image" src="https://github.com/user-attachments/assets/558f3fee-abb0-46cd-b12f-891f8c08187f" />


### [0.2.0]

Caracteristicas añadidas:

- Generador de Contraseñas: Implementación completa de la pantalla PasswordGeneratorScreen en Compose/Kotlin.

- Control de Parámetros: Se agregó un Slider para controlar la longitud de la contraseña (8 a 32 caracteres) y Checkboxes para seleccionar la inclusión de mayúsculas, números y símbolos.

- Lógica de Generación: Se implementó una lógica simple y eficiente para generar contraseñas aleatorias basada en el conjunto de caracteres seleccionado por el usuario.

- Componente Reutilizable: Se creó el componente OptionCheckbox para mejorar la reusabilidad en la interfaz.

![generadorpass](https://github.com/user-attachments/assets/6c33b964-1d00-458d-b28c-bdbc998cae5d)

