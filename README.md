# Gualapop
| Nombre y apellidos | Correo | Cuenta github |
| --- | --- | --- |
| Jose Luis Salvador Martin | jl.salvador.2023@alumnos.urjc.es | n3kr00Gl00m |
| Fernando Prieto Olías | f.prieto.2019@alumnos.urjc.es | Fervo8564 |
| Cassiel Seth Mayorca Heirisman | cs.mayorca.2018@alumnos.urjc.es | cassiel_smh |
| Rodrigo Montilla Fernández | r.montilla.2019@alumnos.urjc.es | romofe01 |
| Pablo Villamayor Iglesias | p.villamayor@alumnos.urjc.es | PabloVILL |

## Trello
https://trello.com/b/oU51T77H/fase-2

# Fase 0
## **1. Entidades:**
  - Usuario
  - Producto
  - Pedidos
  - Crítica o reseña

## **2. Usuarios:**
  * **_Anónimo_**: no introduce ninguna credencial en la aplicación, se le muestran anuncios aleatorios
  * **_Registrado_**: se registra en la aplicación (nombre, apodo, fecha de nacimiento, foto y contraseña).
  * **_Administrador_**: se registra en la app (nombre, apodo, fecha nacimiento, foto, contraseña).

## **3. Permisos de los usuarios:**
  * **_Anónimo_**:  podrán ver productos, descripciones y valoraciones, no puede subir reseñas ni productos
  * **_Registrado_**: podrán hacer todo lo que hace un usuario no registrado y además comprar, publicar un anuncio, valorar un producto y/o usuario.
  * **_Administrador_**:consultar gráficas, eliminar anuncios, usuarios y mismos derechos que los no registrados, pero no podrán comprar y vender

## **4. Imágenes:**
  * Foto de perfil: Habría que añadir una sección de perfil con edición de foto, descripción, etc.
  * Todo producto tiene que tener imágen.

## **5. Gráficos:**
  * En el perfil del usuario un grafico mostrando el balance de beneficios en base a sus ventas y sus compras
  
## **6. Tecnología complementaria:**
  *  Generación pdf de factura a la hora de realizar una compra

## **7. Algoritmo avanzado:**
  * Actualizacion automatica de valoracion de usuario al recibir una nueva entrada 


# Fase 1

### Capturas de pantalla

Perfil- En esta pantalla el usuario tendrá acceso a sus propios productos y podrá eliminarlos, acceso a la gráfica de balance de beneficios y configuración de la información de su perfil así como poder hacer logout.

![image](https://github.com/CodeURJC-DAW-2023-24/webapp13/assets/80538164/62ee9d3d-5095-47b6-ad1c-86fa47c72638)

![image](https://github.com/CodeURJC-DAW-2023-24/webapp13/assets/80538164/fd7a7ff8-2f53-4247-a314-9bb8616493cc)

![image](https://github.com/CodeURJC-DAW-2023-24/webapp13/assets/80538164/3bd1ebf0-c4e7-4e9a-8de3-c2d0422b1f65)

![imagen](https://github.com/CodeURJC-DAW-2023-24/webapp13/assets/130240595/77b8afdd-5aee-4f35-ab6a-109e469351be)

![imagen](https://github.com/CodeURJC-DAW-2023-24/webapp13/assets/130240595/de8658fb-1703-4785-9473-f1a1a40d0c6d)

![image](https://github.com/CodeURJC-DAW-2023-24/webapp13/assets/130240595/55580893-9194-4fa4-96cf-edfccd1eb371)

Administrador- Pantalla la cual se basa en la interfaz que tendria el administrador para poder gestionar los reportes
pertinentes recibidos de los usuarios
![image](https://github.com/CodeURJC-DAW-2023-24/webapp13/assets/45970422/2ad6094f-85c8-4281-8096-b6563cc7593d)
![image](https://github.com/CodeURJC-DAW-2023-24/webapp13/assets/45970422/167fb329-1c09-4410-ba0a-19477d9a6521)



# Fase 2
## Instrucciones de ejecución
Para instalar y ejecutar la aplicación del grupo 13 se deben seguir los siguientes pasos:
> 1.	Usamos el siguiente hipervínculo al repositorio de Github del grupo 8: https://github.com/CodeURJC-DAW-2023-24/webapp13 (Asegurarse que nos encontramos en la rama main).
> 2.	Nos dirigimos a MySQL y en nuestro servidor local creamos una base de datos llamada gualapop.
> 3.	Una vez descargada la aplicación, en un entorno de desarrollo con la extensión de spring preparada de antemano, abrimos el archivo application.properties y ponemos la contraseña y usuario que tengamos en nuestra base de datos de MySQL y ejecutamos el programa.
> 4.	Sin parar la ejecución del programa, introduce la siguiente Url en un navegador: https://localhost:8443/
> 5.	Una vez que nos cargue la pantalla de la tienda, la aplicación estará lista para usarse.

Requisitos para la ejecución del programa:
>-	MySQL: 8.0
>-	Pack de Spring Boot para el entorno de desarrollo que se esté trabajando: (versión 2.4.2)
>-	Versión de java: 17
>-	BootStrap: 3.3.7
>-	JQuery: 3.1.1-1

## Diagrama de navegación
![Diag_navegacion](https://github.com/CodeURJC-DAW-2023-24/webapp13/assets/80538164/64afdc0d-f56d-4011-bbb6-0110f75c8cf3)

## Diagrama con las entidades de la base de datos:
![Diag_clases](https://github.com/CodeURJC-DAW-2023-24/webapp13/assets/80538164/4a1f873f-4eae-4ccf-bf25-8b25240b9b4c)

## Diagrama de clases y templates
Leyenda del diagrama:
- Morado: Vistas
- Verde: Controladores
- Azul: Repositorios
- Negro: Modelos
- Rojo: Servicios
- Azul claro: Seguridad
Introducir aquí imágen diagrama de clases

## Participación de miembros (Fase 2)

### Rodrigo Montilla Fernández | r.montilla.2019@alumnos.urjc.es

> Descripción de la participación

| Número	| Descripción	| Commit | Archivos |
   | :---: | :---: | :---: | :---: |
   | 1º | Nombre commit	| Link al commit | Link al Archivo |
   | 2º	| Nombre commit	| Link al commit | Link al Archivo |
   | 3º	| Nombre commit	| Link al commit | Link al Archivo |
   | 4º	| Nombre commit	| Link al commit | Link al Archivo |
   | 5º	| Nombre commit	| Link al commit | Link al Archivo |

### Fernando Prieto Olías	| f.prieto.2019@alumnos.urjc.es

> Este miembro del equipo se encargó del registro del usuario, configuración de su perfil como los settings y el gráfico de balance entre ingresos y gastos, generador de PDFs para las facturas después de cada compra y creación de los diagramas de clases y diagrama de navegación. Ha realizado también diversos arreglos en el codigo a algunas funcionalidades que generaba algún tipo de error (Problemas con la sincronización de la cuenta de github y los commits realizados)

| Número	| Descripción	| Commit | Archivos |
   | :---: | :---: | :---: | :---: |
   | 1º | Generador PDF	| [Generate PDFs](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/9dd31d5476c9ace59818ab86830d72d196c7d844) | [PDFService](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/java/es/gualapop/backend/service/PDFService.java) |
   | 2º	| User Registro	| [User register](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/72f6845460446dabbbe7300c27024e97ee5489d6) | [UserService](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/java/es/gualapop/backend/service/UserService.java) |
   | 3º	| Gráfica Beneficios  | [Gráfico estático falta datos reales (En otro commit subo la gráfica funcional)](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/0b8e0cd34ab682c6c169640c7331d740553f370a) | [Profile.html](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/resources/templates/profile.html) |
   | 4º	| User Perfil y Settings	| [User profile mustache and settings for updateUser](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/3de422b938d9d78080449f778639568a11000d8a) | [UserController](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/java/es/gualapop/backend/controller/UserController.java) |
   | 5º	| Documentación	| [Diagrama de clases y diagrama de navegación](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/050fb9323693714b8034a4a3c3d3b46790f49cfb) | [ReadMe](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/README.md) |


### Jose Luis Salvador Martín	| jl.salvador.2023@alumnos.urjc.es

> Descripción de la participación

| Número	| Descripción	| Commit | Archivos |
   | :---: | :---: | :---: | :---: |
   | 1º | Nombre commit	| Link al commit | Link al Archivo |
   | 2º	| Nombre commit	| Link al commit | Link al Archivo |
   | 3º	| Nombre commit	| Link al commit | Link al Archivo |
   | 4º	| Nombre commit	| Link al commit | Link al Archivo |
   | 5º	| Nombre commit	| Link al commit | Link al Archivo |


### Cassiel Seth Mayorca Heirisman	| cs.mayorca.2018@alumnos.urjc.es

> Descripción de la participación

| Número	| Descripción	| Commit | Archivos |
   | :---: | :---: | :---: | :---: |
   | 1º | Nombre commit	| Link al commit | Link al Archivo |
   | 2º	| Nombre commit	| Link al commit | Link al Archivo |
   | 3º	| Nombre commit	| Link al commit | Link al Archivo |
   | 4º	| Nombre commit	| Link al commit | Link al Archivo |
   | 5º	| Nombre commit	| Link al commit | Link al Archivo |

### Pablo Villamayor Iglesias	| p.villamayor@alumnos.urjc.es

> Descripción de la participación

| Número	| Descripción	| Commit | Archivos |
   | :---: | :---: | :---: | :---: |
   | 1º | Nombre commit	| Link al commit | Link al Archivo |
   | 2º	| Nombre commit	| Link al commit | Link al Archivo |
   | 3º	| Nombre commit	| Link al commit | Link al Archivo |
   | 4º	| Nombre commit	| Link al commit | Link al Archivo |
   | 5º	| Nombre commit	| Link al commit | Link al Archivo |
