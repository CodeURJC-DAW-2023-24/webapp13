# Gualapop
| Nombre y apellidos | Correo | Cuenta github |
| --- | --- | --- |
| Jose Luis Salvador Martin | jl.salvador.2023@alumnos.urjc.es | n3kr00Gl00m |
| Fernando Prieto Olías | f.prieto.2019@alumnos.urjc.es | Fervo8564 |
| Cassiel Seth Mayorca Heirisman | cs.mayorca.2018@alumnos.urjc.es | cassiel_smh |
| Rodrigo Montilla Fernández | r.montilla.2019@alumnos.urjc.es | romofe01 |

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

> Este miembro del equipo se ha encargado de la lógica de AJAX, la gestión y diseño de la base de datos, traducir todo el código que había en español a inglés, navegación entre pantallas, añadir producto, gestión de imágenes, funcionalidad de valoraciones, comprar producto, gestión de la pasarela de pago y gestión de productos en el perfil propio del usuario.

| Número	| Descripción	| Commit | Archivos |
   | :---: | :---: | :---: | :---: |
   | 1º | Profile Products & Delete Products	| [Commit Link](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/56af87e5b4256d7bbc636e7ba31ba457503357f1) | [User Controller](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/java/es/gualapop/backend/controller/UserController.java) |
   | 2º	| New Review	| [Commit Link](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/75ec7ced5c21b5adbf409a0ddd8a3800d6f0a7d6) | [Product Controller](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/java/es/gualapop/backend/controller/ProductController.java) |
   | 3º	| Purchase Product	| [Commit Link](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/6cfe68028d9d41a1fa8e7ce5b5e0fb5d76ca6ee6) | [Product Controller](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/java/es/gualapop/backend/controller/ProductController.java) |
   | 4º	| English translation except templates	| [Commit Link](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/151c934ff6d309540639212bb7c271b60ee4ff71) | [User](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/java/es/gualapop/backend/model/User.java) |
   | 5º	| DB additions + AJAX	| [Commit Link](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/6c36a80eb33684afc4c64a21e5bb9a1d4ba66ce7) | [Script](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/resources/static/js/script.js) |

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

> Este miembro del equipo del equipo se encargo de crear la base del proyecto, creando los models, repositories, y aportando en la edición y creación de los controllers. Creador de la lógica de la barra de búsqueda y categorias además del mustache de los archivos html como index, producto individual etc. Ha realizado diversas ediciones como en el AJAX del proyecto. Ha tenido problemas con la sincronizacion de la cuenta de github, por lo que no se ven reflejados todos los commits a mi cuenta.

| Número	|         Descripción	         |                                                               Commit                                                                |                                                                      Archivos                                                                      |
   | :---: |:----------------------------:|:-----------------------------------------------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------:|
   | 1º |           Models	            |         [Models creation](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/5fd142c4b0214ca0861df9eef825b992d6046fb3)         |         [Product](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/java/es/gualapop/backend/model/Product.java)         |
   | 2º	|     Errores y services 	     | [Errors corrections and services](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/e89ecb5dc2f22c222ddc3bfa129e53b3ce610833) | [ProductService](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/java/es/gualapop/backend/service/ProductService.java) |
   | 3º	|            AJAX	             |              [AJAX](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/9c62fbd3bd4ecdc5728a8775d039c1e7e470eb6b)               |                 [AJAX](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/resources/static/js/script.js)                  |
   | 4º	| Página de perfil de usuario	 |    [User Profile & functions](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/da46eb276e2a01ca48531ccafe70d2da0e700079)     | [Profile](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/java/es/gualapop/backend/controller/ProductController.java)  |
   | 5º	|          Buscador	           |            [Searcher](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/543cc2c2eb9f5c0cb4a4e54475668ebb5c31891b)             | [Search Service](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/java/es/gualapop/backend/service/SearchService.java)  |


### Cassiel Seth Mayorca Heirisman	| cs.mayorca.2018@alumnos.urjc.es

> Descripción de la participación

| Número	| Descripción	| Commit | Archivos |
   | :---: | :---: | :---: | :---: |
   | 1º | Gestion de reportes	| [magement](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/bc72e38f2e79c8e162dac9f9143365f558ce490e) | [ReportController](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/bc72e38f2e79c8e162dac9f9143365f558ce490e#diff-655a30a315195d210002dd5b387cd9af0eca3eeff9e2ab50285cfda16a335ab4) |
   | 2º	| Añadir nuevos reportes	| [add new report](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/4117720fef26018a39791e99da95f28101663a1a) | [Report](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/4117720fef26018a39791e99da95f28101663a1a#diff-37b4d5720e174c4c0ff69411cff16bd368439171d610cfccf8808f026ef7f703) |
   | 3º	| Añadir nuevo producto	| [add new product](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/935cee2e4c488d2dd199a1ed1b00ebc7c58bcf99) | [ProductController](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/935cee2e4c488d2dd199a1ed1b00ebc7c58bcf99#diff-b0a1d03d1823606ca115167779bfac33b1f8fad632cbfc8c04260837b6f6f370) |
   | 4º	| Inicializacion base de datos	| [Start Database](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/95075ad128433c542b290256624c588fbfe0ab14) | [DatabaseInizialiter](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/95075ad128433c542b290256624c588fbfe0ab14#diff-0f7118cb988f48134ea964e8f95943f43ea363ebe613e9efced6f9e4a051d76e) |
   | 5º	| Inserccion imagenes a html	| [add images](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/536b6eab0b7f0be137a184289eea6ba9604bf63a)https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/536b6eab0b7f0be137a184289eea6ba9604bf63a | [ProductController](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/536b6eab0b7f0be137a184289eea6ba9604bf63a#diff-b0a1d03d1823606ca115167779bfac33b1f8fad632cbfc8c04260837b6f6f370)https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/536b6eab0b7f0be137a184289eea6ba9604bf63a#diff-b0a1d03d1823606ca115167779bfac33b1f8fad632cbfc8c04260837b6f6f370 |


