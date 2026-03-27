# Registro de préstamo de llaves de salón

Proyecto base de JavaFX para IntelliJ IDEA con Maven, Java 21 Temurin y FXML.

## Objetivo del ejercicio
Desarrollar un CRUD sencillo reutilizando los mismos controles de captura para **Agregar** y **Actualizar**.

Este proyecto **no viene completo**. Ya trae la estructura lista para que no se atoren importando el proyecto, pero ustedes deben completar parte de la lógica.

## Contexto
La aplicación registra préstamos de llaves de salón dentro de la escuela. Cada registro tiene:
- Nombre del solicitante
- Salón
- Turno

## Tecnologías
- Java 21 Temurin
- JavaFX 21
- Maven
- IntelliJ IDEA

## Estructura del proyecto
```text
src
└── main
    ├── java
    │   ├── com/example/prestamollaves
    │   │   ├── controller
    │   │   │   └── MainController.java
    │   │   ├── model
    │   │   │   └── PrestamoLlave.java
    │   │   ├── repository
    │   │   │   └── PrestamoLlaveRepository.java
    │   │   ├── service
    │   │   │   └── PrestamoLlaveService.java
    │   │   └── Main.java
    │   └── module-info.java
    └── resources
        └── com/example/prestamollaves
            └── main-view.fxml
```

## Qué ya viene listo
- Proyecto Maven importable en IntelliJ
- `pom.xml` configurado para Java 21 y JavaFX 21 explícito
- `module-info.java` en la ubicación correcta
- FXML creado y enlazado con el controller
- Paquetes `controller`, `service`, `repository` y `model`
- Método ejemplo para recargar el `ListView`
- Método ejemplo para cargar un elemento seleccionado desde el `ListView`
- Método ejemplo de búsqueda resuelto
- Comentarios dentro del código indicando qué falta hacer

## Qué deben completar
Deben completar la lógica de:
- `agregar()` en el controller
- `actualizar()` en el controller
- `eliminar()` en el controller
- `agregar(...)` en el service
- `actualizar(...)` en el service
- `eliminar(...)` en el service

## Regla importante del CRUD
Se deben **reutilizar los mismos controles** para agregar y actualizar.

Esto significa que:
- `txtNombreSolicitante`, `txtSalon` y `cbTurno` sirven para capturar datos nuevos
- esos mismos controles sirven para modificar un registro existente
- no se debe crear otra ventana ni otros controles para actualizar

## Cómo debe funcionar cada operación

### 1. Agregar
Al presionar **Agregar**:
1. Leer el contenido de `txtNombreSolicitante`, `txtSalon` y `cbTurno`
2. Validar que no estén vacíos
3. Validar que no exista otro registro con el mismo nombre del solicitante
4. Crear un objeto `PrestamoLlave`
5. Guardarlo en la lista
6. Refrescar el `ListView`
7. Limpiar los controles

### 2. Buscar
La búsqueda se hace por **nombre del solicitante**.

Al presionar **Buscar**:
1. Tomar el nombre escrito en `txtNombreSolicitante`
2. Buscar coincidencia en la lista
3. Si existe, cargar sus datos en los mismos controles
4. Guardar el nombre original en la variable `nombreOriginal`
5. Si no existe, mostrar mensaje

## Explicación detallada de UPDATE
**Actualizar no agrega un registro nuevo.** Primero debe localizarse el registro original.

El flujo correcto es este:
1. El usuario escribe un nombre en `txtNombreSolicitante` y presiona **Buscar**
   - o también puede seleccionar directamente un elemento del `ListView`
2. Cuando el registro es encontrado o seleccionado, sus datos se cargan en los mismos controles
3. En ese momento también se guarda internamente el valor original en la variable `nombreOriginal`
4. Después el usuario modifica los datos en los mismos controles
5. Al presionar **Actualizar**, el sistema debe buscar en la lista el registro que coincida con `nombreOriginal`
6. Si lo encuentra, modifica sus datos con los nuevos valores escritos en pantalla
7. Se refresca el `ListView`
8. Se limpian los controles

### Importante para UPDATE
- Si `nombreOriginal` es `null`, entonces no se ha buscado ni seleccionado ningún registro
- En ese caso no se debe actualizar nada
- Si el alumno cambió el nombre, también debe validarse que el nuevo nombre no esté repetido

### Ejemplo rápido de UPDATE
Supón que en la lista existe este registro:
```text
Carlos | A-12 | Matutino
```

Paso 1:
- El usuario busca `Carlos` o selecciona ese elemento del `ListView`
- Se cargan los datos en pantalla
- `nombreOriginal` guarda `Carlos`

Paso 2:
El usuario cambia los controles a:
```text
Nombre: Carlos Méndez
Salón: B-03
Turno: Vespertino
```

Paso 3:
Al presionar **Actualizar**:
- el sistema busca el registro cuyo nombre original era `Carlos`
- si lo encuentra, cambia sus datos

El resultado final será:
```text
Carlos Méndez | B-03 | Vespertino
```

## Explicación detallada de DELETE
**Eliminar sí debe borrar el objeto de la lista**, no solo limpiar la pantalla.

El flujo correcto es este:
1. El usuario escribe el nombre del solicitante en `txtNombreSolicitante`
   - o selecciona un elemento del `ListView` para que ese nombre se cargue en pantalla
2. Al presionar **Eliminar**, el controller debe mandar ese nombre al service
3. El service debe buscar si existe un registro con ese nombre
4. Si existe, el repository debe quitarlo de la lista
5. Luego se debe refrescar el `ListView`
6. Se deben limpiar los controles
7. Si no existe, se debe mostrar un mensaje

### Importante para DELETE
- Eliminar no debe crear listas nuevas
- Eliminar no debe dejar el elemento visible en pantalla
- Después de eliminar, sí o sí debe ejecutarse `actualizarLista()`

## Validaciones pedidas
Solo validaciones sencillas:
- El nombre del solicitante no debe estar vacío
- El salón no debe estar vacío
- El turno debe seleccionarse
- No se debe repetir el nombre del solicitante

## Cómo abrir el proyecto en IntelliJ
1. Descargar o clonar el repositorio
2. Abrir IntelliJ IDEA
3. Seleccionar **Open**
4. Elegir la carpeta raíz del proyecto
5. Esperar a que IntelliJ importe el `pom.xml`
6. Verificar que reconozca Maven correctamente

## Cómo ejecutar
Opción 1 desde IntelliJ:
- Abrir `Main.java`
- Ejecutar la clase

Opción 2 con Maven:
```bash
mvn clean javafx:run
```

## Rama que deben crear
Cada alumno debe trabajar en una rama con esta nomenclatura:
```text
nombre-apellido-u3-grado-grupo
```

Ejemplo para este grupo:
```text
eliel-rodriguez-u3-2D
```

## Entrega
1. Cuando terminen, avísenle al profesor su username de GitHub.
2. Una vez que el profesor los agregue al repositorio, tendrán **5 minutos** para subir su entrega.
3. Deben subir sus cambios únicamente a su propia rama.
4. No deben trabajar sobre `main`.

## Soluciones comunes si no compila o no corre

### Cambiar la versión de Java en el `pom.xml`
Si necesitan cambiar de versión, editen estas propiedades:
```xml
<maven.compiler.source>21</maven.compiler.source>
<maven.compiler.target>21</maven.compiler.target>
```

### Seleccionar el JDK correcto en IntelliJ
1. Ir a **File > Project Structure**
2. En **Project**, seleccionar **Project SDK: Temurin 21**
3. En **Project language level**, dejar una opción compatible con Java 21
4. En **Modules**, verificar que el módulo también use el mismo SDK

### Si Maven marca error por versión de Java
1. Confirmar que Java 21 esté instalado
2. Confirmar que IntelliJ esté apuntando a Temurin 21
3. Recargar el proyecto Maven
4. Volver a intentar la ejecución

### Si aparece error relacionado con JavaFX o módulos
1. Confirmar que se importó el proyecto como Maven y no solo como carpeta simple
2. Dar clic en **Reload All Maven Projects**
3. Verificar que existe `module-info.java` en:
```text
src/main/java/module-info.java
```
4. Confirmar que las dependencias de JavaFX en el `pom.xml` tienen la versión `21` escrita explícitamente

### Si no abre la vista
Verificar que el FXML esté en esta ruta:
```text
src/main/resources/com/example/prestamollaves/main-view.fxml
```

## Nota final
La intención es que se enfoquen en la lógica del CRUD y no en problemas de configuración.
Este proyecto base ya les evita crear toda la estructura desde cero.
