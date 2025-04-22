# Diagramas del Proyecto BogoTrash

Este documento describe los principales diagramas del proyecto **BogoTrash**, explicando la estructura, componentes, base de datos y despliegue del sistema. Se han aplicado principios de diseño, patrones y buenas prácticas para representar gráficamente la arquitectura del sistema.

---

## 📚 Diagrama de Clases




### Uso del patrón MVVM en el proyecto BogoTrash

El patrón Model-View-ViewModel (MVVM) se utiliza en el proyecto BogoTrash para lograr una separación clara de responsabilidades, lo cual mejora la mantenibilidad, escalabilidad y testabilidad del código.

En el contexto de Android, MVVM es un patrón ampliamente recomendado, ya que permite que la interfaz de usuario (View) esté desacoplada de la lógica de negocio (ViewModel) y del acceso a datos (Model/Repository). Es parecido al MVC en este aspecto, adicional a esto nos permite actualizar la UI automáticamente cuando los datos cambian, gracias al uso de LiveData u observables.

### ¿Cómo se implementa en BogoTrash?

- **Model**: Representado por las clases de datos (`User`, `Recycler`, etc.) y la capa de repositorio (`UserRepository`, etc.), que contienen la lógica de acceso a la base de datos y a los datos del dominio.
- **ViewModel**: Intermediario entre la vista y los datos, se encarga de exponer la información a la vista y manejar eventos sin que esta sepa cómo se obtienen los datos. Por ejemplo, `MapViewModel`, `RewardViewModel` y otros, mantienen y gestionan el estado de la UI.
- **View (Activity/Fragment)**: Las actividades como `MainActivity`, `MapActivity` o `RegisterActivity` sólo se encargan de mostrar la información que reciben del ViewModel, y no contienen lógica de negocio.

### Beneficios obtenidos

- ✅ **Separación de responsabilidades**: Evita actividades monolíticas y difíciles de mantener.
- ✅ **Mejor testabilidad**: Las ViewModel pueden ser testeadas fácilmente sin necesidad de ejecutar la interfaz.
- ✅ **Escalabilidad**: Al agregar nuevas funcionalidades o vistas, se puede mantener el mismo esquema sin afectar otras capas.
- ✅ **Reactividad**: Se puede observar cambios en los datos usando LiveData, actualizando automáticamente la UI cuando sea necesario.

---

## 🗄️ Diagrama Entidad-Relación (ER)

![image](https://github.com/user-attachments/assets/c152599c-a2a3-4dd1-b51a-13bc0773db24)


El diagrama entidad-relación representa la estructura lógica de la base de datos de BogoTrash. Las principales entidades del sistema y sus relaciones son:

- **Users / Recyclers**: Tabla `Users` contiene la información base de todos los usuarios, mientras que `Recyclers` amplía esta información con datos de contacto y ubicación para quienes actúan como recicladores.
- **Campaigns / Participations**: Las campañas permiten incentivar la participación ciudadana. Un usuario puede participar en múltiples campañas.
- **Rewards / UserRewards**: Los usuarios pueden redimir recompensas acumulando puntos, y esta relación es gestionada mediante la tabla `UserRewards`.
- **RecyclingPoints / WasteTypes**: Existe una relación muchos a muchos entre puntos de reciclaje y tipos de residuos aceptados.

Este modelo está alineado con la lógica del dominio y respalda las operaciones CRUD implementadas en la app.

---

## 🧱 Diagrama de Componentes


Este diagrama muestra los componentes lógicos del sistema y sus interacciones, usando conectores UML `provided` y `required` para representar las dependencias explícitas entre módulos:

- **UI**: Contiene todas las vistas del sistema. Se comunica con `ViewModel` y utiliza `SessionManager` para gestionar el estado del usuario.
- **ViewModel**: Expone los datos a la UI y comunica los eventos del usuario hacia el `Repository`.
- **Repository**: Actúa como capa de acceso a datos. Comunica la app con la base de datos usando `DatabaseConnection`, y delega los datos al `Model`.
- **Model**: Define las clases del dominio (`User`, `Reward`, etc.).
- **Singletons**: `SessionManager` y `DatabaseConnection` son instancias únicas utilizadas globalmente.

Este diseño fomenta la modularidad y escalabilidad del sistema.

---

## 🚀 Diagrama de Despliegue


El diagrama de despliegue representa cómo se distribuyen los componentes del sistema a nivel físico:

### Entidades del despliegue:

- **Dispositivo Android**: Donde se ejecuta el artefacto `BogoTrash.apk`. Contiene todos los componentes lógicos de la aplicación.
- **Railway Cloud**: Plataforma externa que aloja la base de datos MySQL utilizada por el sistema.
- **Conexión JDBC (58943)**: Comunicación directa entre el componente `Repository` en la app y la base de datos en la nube usando el puerto proporcionado por Railway.

Esta arquitectura cliente-servidor permite mantener la lógica de negocio en la app y usar la nube únicamente para la persistencia.

---

> Estos diagramas han sido elaborados como parte del análisis y documentación técnica del sistema, cumpliendo con los estándares UML y buenas prácticas de ingeniería de software.
