<div align="center">

# Pryd
### Gestión de Tareas + Técnica Pomodoro

Aplicación Android de productividad personal que combina un tablero de tareas estilo Kanban con la técnica Pomodoro, diseñada para estudiantes y profesionales que buscan organizar su trabajo y mejorar su concentración.

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)
![Material Design 3](https://img.shields.io/badge/Material_Design_3-757575?style=for-the-badge&logo=material-design&logoColor=white)
![Room](https://img.shields.io/badge/Room-4285F4?style=for-the-badge&logo=android&logoColor=white)
![Hilt](https://img.shields.io/badge/Hilt-DD4B39?style=for-the-badge&logo=google&logoColor=white)
![Coroutines](https://img.shields.io/badge/Coroutines-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![Navigation](https://img.shields.io/badge/Navigation_Compose-4285F4?style=for-the-badge&logo=android&logoColor=white)

</div>

---

## Capturas de pantalla

### Tablero de tareas

<p align="center">
  <img src="https://github.com/user-attachments/assets/fc260d2f-9828-4835-94f6-39bc4eef496a" width="30%" />
  <img src="https://github.com/user-attachments/assets/1e5f5c36-40f6-4a43-bacf-a965510ec814" width="30%" />
  <img src="https://github.com/user-attachments/assets/8817b1a8-d98d-4964-9504-949762dac6b1" width="30%" />
</p>

### Crear actividad

<p align="center">
  <img src="https://github.com/user-attachments/assets/511bf0b8-5ec1-467e-ae54-a26291540b2c" width="30%" />
</p>

### Temporizador Pomodoro

<p align="center">
  <img src="https://github.com/user-attachments/assets/b4d688c6-1c8e-4075-ac1f-11589d72be97" width="30%" />
  <img src="https://github.com/user-attachments/assets/d02eb521-b6e3-411c-ab4a-7e520bf13805" width="30%" />
</p>

---

## Funcionalidades

### Gestión de tareas
- Crear actividades con título, descripción y prioridad (Alta / Media / Baja)
- Tablero Kanban con tres estados: **Pendientes**, **En Curso** y **Finalizadas**
- Mover actividades entre estados con un toque
- Eliminar actividades individuales o limpiar todas las finalizadas

### Temporizador Pomodoro
- Sesiones de enfoque y descanso automáticas
- Indicador circular animado con progreso en tiempo real
- Controles de inicio, pausa y salto de sesión
- Seguimiento de sesiones completadas (4 por ciclo)
- Soporte para modo oscuro

---

## Tecnologías

| Capa | Tecnología |
|------|-----------|
| Lenguaje | Kotlin |
| UI | Jetpack Compose + Material Design 3 |
| Arquitectura | Clean Architecture (Data / Domain / Presentation) |
| Inyección de dependencias | Hilt |
| Persistencia | Room Database |
| Estado | ViewModel + StateFlow |
| Navegación | Navigation Compose |

---

## Arquitectura

El proyecto sigue los principios de **Clean Architecture**, separando responsabilidades en tres capas independientes:

```
app/
├── data/
│   ├── local/          # Room Database, DAOs, entidades
│   └── repository/     # Implementaciones de repositorios
├── domain/
│   ├── model/          # Modelos de negocio
│   ├── repository/     # Interfaces de repositorios
│   └── usecase/        # Casos de uso
└── presentation/
    ├── board/          # Pantalla tablero de tareas
    ├── pomodoro/       # Pantalla temporizador
    ├── activity/       # Pantalla crear/editar actividad
    ├── navigation/     # NavHost y navegación
    └── theme/          # Colores, tipografía y tema
```

---

## Instalación

### Requisitos
- Android Studio Hedgehog o superior
- Android SDK 26+
- Kotlin 1.9+

### Pasos

```bash
# Clonar el repositorio
git clone https://github.com/Jmartinez25/pryd.git

# Abrir en Android Studio
# File > Open > seleccionar la carpeta del proyecto

# Ejecutar en emulador o dispositivo físico
./gradlew installDebug
```

---

<div align="center">

Desarrollado con ❤️ usando Jetpack Compose

</div>
