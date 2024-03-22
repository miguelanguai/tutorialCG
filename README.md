# Tutorial

## Miguel Ángel Guaita

Este es mi tutorial de Angular y Springboot

## Entorno de Desarrollo y Versiones

### Springboot

Generado por [Spring Initializr](https://start.spring.io/)

- Proyecto: Maven
- Versión Spring Boot: 3.2.3
- Group: com.ccsw
- ArtifactId: tutorial
- Versión JAVA: 21
- Dependencies. Web, DataJPA, H2Database

### Angular

- Versión Angular CLI: 16.2.12

## Para arrancar proyecto

### En Eclipse (Backend)

Desde el IDE Eclipse

- Importar el proyecto (File/Import/Existing Maven Projects)
- Arrancar la clase TutorialApplication.java, en paquete com.ccsw.tutorial, dentro de src/main/java

### En Angular  (Frontend)

Desde el IDE VSCode

- Dentro del directorio raíz del proyecto, ejecutar `npm install` la primera vez que se utilice en un PC
    > npm install

    la primera vez que se utilice en un PC
- Ejecutar
    > ng serve

    para arrancar el proyecto

- Acudir a <https://localhost:4200>  para verlo funcionando

## Notas y Observaciones

En los apartados realizados por mí, se puede crear clientes además de generar préstamos y filtrarlos en una lista paginada.

Cuando se genera un cliente, salta una excepción si se intenta generar uno con el mismo nombre, así como saltan excepciones cuando se intenta crear préstamos de juegos que están prestados en las mismas fechas y con clientes que ya tienen un juego prestado en las mismas fechas.

En el filtrado de préstamos, se puede ver que , filtrando por
páginas, al pasar de página no aparecen todo el resto de préstamos ni páginas vacías si se filtra desde las páginas del final (puesto que te manda a la primera página de nuevo)

Por otro lado, se arroja error si, al crear préstamos, se crea con un juego que ya está siendo prestado
a otro cliente por esas fechas o si se crea con un cliente que ya tiene un préstamo en esas fechas.
