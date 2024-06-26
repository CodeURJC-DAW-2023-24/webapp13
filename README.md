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
![Diag_ER](https://github.com/CodeURJC-DAW-2023-24/webapp13/assets/80538164/98be0455-7d07-4d8e-9e6f-201f7c69711a)


## Diagrama de clases y templates
Leyenda del diagrama:
- Morado: Vistas
- Verde: Controladores
- Azul: Repositorios
- Gris: Modelos
- Rojo: Servicios
- Azul claro: Seguridad
![Diag_clases](https://github.com/CodeURJC-DAW-2023-24/webapp13/assets/80538164/4a1f873f-4eae-4ccf-bf25-8b25240b9b4c)

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

>  Este miembro se encargo de la creacion inicial del proyecto SpringBoot asi como enlazar el proyecto a la base de datos, ademas ha aportdo en la creacion de controller y models participando ademas en la gestion de formulacion y la gestion de objetos con la base de datos

| Número	| Descripción	| Commit | Archivos |
   | :---: | :---: | :---: | :---: |
   | 1º | Gestion de reportes	| [magement](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/bc72e38f2e79c8e162dac9f9143365f558ce490e) | [ReportController](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/bc72e38f2e79c8e162dac9f9143365f558ce490e#diff-655a30a315195d210002dd5b387cd9af0eca3eeff9e2ab50285cfda16a335ab4) |
   | 2º	| Añadir nuevos reportes	| [add new report](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/4117720fef26018a39791e99da95f28101663a1a) | [Report](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/4117720fef26018a39791e99da95f28101663a1a#diff-37b4d5720e174c4c0ff69411cff16bd368439171d610cfccf8808f026ef7f703) |
   | 3º	| Añadir nuevo producto	| [add new product](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/935cee2e4c488d2dd199a1ed1b00ebc7c58bcf99) | [ProductController](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/935cee2e4c488d2dd199a1ed1b00ebc7c58bcf99#diff-b0a1d03d1823606ca115167779bfac33b1f8fad632cbfc8c04260837b6f6f370) |
   | 4º	| Inicializacion base de datos	| [Start Database](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/95075ad128433c542b290256624c588fbfe0ab14) | [DatabaseInizialiter](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/95075ad128433c542b290256624c588fbfe0ab14#diff-0f7118cb988f48134ea964e8f95943f43ea363ebe613e9efced6f9e4a051d76e) |
   | 5º	| Inserccion imagenes a html	| [add images](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/536b6eab0b7f0be137a184289eea6ba9604bf63a) | [ProductController](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/536b6eab0b7f0be137a184289eea6ba9604bf63a#diff-b0a1d03d1823606ca115167779bfac33b1f8fad632cbfc8c04260837b6f6f370) |



# Fase 3

## API y Dockerización

### Documentación API Rest

*Archivo yaml: [https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/api-docs/api-docs.yaml](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/api-docs/api-docs.yaml)

*Documentación API REST: [https://rawcdn.githack.com/CodeURJC-DAW-2023-24/webapp13/983b98b4620655c4f82d5afaceb74a064a1c5352/backend/api-docs/api-docs.html](https://rawcdn.githack.com/CodeURJC-DAW-2023-24/webapp13/983b98b4620655c4f82d5afaceb74a064a1c5352/backend/api-docs/api-docs.html)

### Instrucciones de ejecución de la aplicación dockerizada:

1. Es necesaria la instalacion de docker y docker compose. 

2. Abrir la terminal navegar hasta la carpeta Docker. 

3.1. Windows: Escribir el comando: "docker-compose up --build". 

3.2. Linux: Escribir el comando: "sudo docker-compose up --build".

4. Una vez finalizado el proceso de construcción de docker abrir el navegador y escribir:"https://localhost:8443/". 

5. Una vez escrita te redirigida a la pantalla de inicio de la aplicacion: "https://localhost:8443/index" una vez se cargue estará lista para ser utilizada.

### Documentación para construcción de la imagen docker:
LINUX:

1. Clonar repositorio mediante git clone https://github.com/CodeURJC-DAW-2023-24/webapp13.git

2. Descargar Docker 

3. Descargar Docker Compose

4. Navegar hasta la carpeta Docker

5. Ejecutar el comando: "chmod 777 create_image.sh"

6. Ejecutar el comando: "./create_image.sh"

## Diagrama de Clases actualizado
Leyenda del diagrama:
- Morado: Vistas
- Verde: Controladores
- Azul: Repositorios
- Gris: Modelos
- Rojo: Servicios
- Azul claro: Seguridad
![Diag_clases](https://github.com/CodeURJC-DAW-2023-24/webapp13/assets/80538164/b0b895f7-c499-4783-a46a-09e42d6b976b)

## Participación de miembros (Fase 3)

### Rodrigo Montilla Fernández | r.montilla.2019@alumnos.urjc.es


| Número	| Descripción	| Commit | Archivos |
   | :---: | :---: | :---: | :---: |
   | 1º | Header Location	| [POST header Locations](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/93fad995b6e3d13eefe4dfbf34db45d0d9a37e0d) | [ReviewRestController](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/93fad995b6e3d13eefe4dfbf34db45d0d9a37e0d#diff-c4cf910c1ce36d784c1e5c7f81d5cd901eba349d5c8e345b5f229e2fc1cac7f8) |
   | 2º	| API Documentation	| [API DOC](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/a47047d1fc15324636b66131bf91d45eb3ae9aa6) | [api-docs](https://github.com/CodeURJC-DAW-2023-24/webapp13/tree/main/backend/api-docs) |
   | 3º	| Query GET Similar Products	| [GET Similar Products](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/735cbaf6d379469217225e752c5e3db0ef4b3bff) | [ProductsRestController](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/735cbaf6d379469217225e752c5e3db0ef4b3bff#diff-1990fffdec44e0d5df9b9956793243fa3463fe766309d34dff858b5777878d9e) |
   | 4º	| Query GET Products by ProductType	| [GET Products by ProductType](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/aae9cae4aad9417f9cd582e11a0d58f2d0851efd) | [ProductsRestController](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/aae9cae4aad9417f9cd582e11a0d58f2d0851efd#diff-1990fffdec44e0d5df9b9956793243fa3463fe766309d34dff858b5777878d9e) |
   | 5º	| Query POST Product Image	| [POST Product Image](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/285f1708c0f76c6811ee4c2db7e9cddd7a6c71a7) | [ProductsRestController](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/aae9cae4aad9417f9cd582e11a0d58f2d0851efd#diff-1990fffdec44e0d5df9b9956793243fa3463fe766309d34dff858b5777878d9e) |

### Fernando Prieto Olías	| f.prieto.2019@alumnos.urjc.es


| Número	| Descripción	| Commit | Archivos |
   | :---: | :---: | :---: | :---: |
   | 1º | Query GET para reviews	| [API GET Reviews](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/7c9bbda780d6990635df6207a7e1c292e151655d) | [ReviewRestController](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/java/es/gualapop/backend/controller/api/ReviewRestController.java) |
   | 2º	| Query POST review to an user	| [API POST Review](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/f812a14da416320b9d5988bffd71380ff23ba932) | [ReviewRestController](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/java/es/gualapop/backend/controller/api/ReviewRestController.java) |
   | 3º	| Query DELETE reviews by ID	| [API DELETE Reviews](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/b3bc5e1a1c30117f7c78c968767c29abd4d3f540) | [ReviewRestController](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/java/es/gualapop/backend/controller/api/ReviewRestController.java) |
   | 4º	| Create Dockerfile	| [ADD Dockerfile](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/6acea49dc88af55a3dd38bf21815c5b0d8332a57) | [Dockerfile](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/Dockerfile) |
   | 5º	| Create script for upload docker image	| [ADD Create_image.sh](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/8f828e184b4c8737def8859f533c7fe8adadf014) | [Create_image.sh](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/create_image.sh) |


### Jose Luis Salvador Martín	| jl.salvador.2023@alumnos.urjc.es


| Número	 | Descripción	|                                                           Commit                                                           |                                                                                                               Archivos                                                                                                                |
   |:-------:| :---: |:--------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
   |  1º     | Creation of ProductRestController & modification of some classes	| [ProductRestController](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/0f0ea6412f44ed21fa7e01f7c37914e9e2ad3d60)  | [ProductRestController](https://github.com/CodeURJC-DAW-2023-24/webapp13/tree/0f0ea6412f44ed21fa7e01f7c37914e9e2ad3d60/backendhttps://github.com/CodeURJC-DAW-2023-24/webapp13/tree/0f0ea6412f44ed21fa7e01f7c37914e9e2ad3d60/backend) |
   |   2º	   |  Creation of ReportRestController 	|  [ReportRestController](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/e0ecaa4d4fbb9318435e5870a101c686b384662d)  |                                                                                                           [ReportRestController](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/e0ecaa4d4fbb9318435e5870a101c686b384662d)                                                                                                            |
   |   3º	   |  Fix some "UserRestController" methods 	| [ Fixed UserController ](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/fbfc86a3618bee4f07eab7cad78dc8ab1db4580c) |                                                                                                           [ Fixed UserController ](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/fbfc86a3618bee4f07eab7cad78dc8ab1db4580c)                                                                                                            |
   |   4º	   |  Some docuAPI from ReportRestController 	|        [DocuAPI](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/e5bf1479730c6b5385351f4d8e06dd285d846e18)         |                                                                                                           [DocuAPI](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/e5bf1479730c6b5385351f4d8e06dd285d846e18)                                                                                                            |
   |   5º	   |  Login method created, fixed some methods of ReportRestController 	|                                                  [Login and Report](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/d2d9ad2ec5871332d39c1ef5aea8d137e65d5a0a)                                                  |                                                                                                           [Login and Report](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/d2d9ad2ec5871332d39c1ef5aea8d137e65d5a0a)                                                                                                               |


### Cassiel Seth Mayorca Heirisman	| cs.mayorca.2018@alumnos.urjc.es


| Número	| Descripción	| Commit | Archivos |
   | :---: | :---: | :---: | :---: |
   | 1º | create postman document	| [Postman doc](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/2c2fc8b814c709462ef57af629164d5c5b4897e6) | [api.postman](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/api.postman_collection.json) |
   | 2º	| create and build docker-compose| [docker-compose](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/8fb040519f78c3d22a9733b7053584468f7c462a) | [docker-compose](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/docker-compose.yml) |
   | 3º	| fix bad practices	| [fix api review](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/0d4f6e28011315647e88347e895868a28214d33a) | [reviewRestController](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/java/es/gualapop/backend/controller/api/ReviewRestController.java) |
   | 4º	| add new methods to reviewRestController	| [method api review](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/b0c86a8fe0ba4dfdf614dd7df2ef7157e83e768b) | [reviewRestController](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/java/es/gualapop/backend/controller/api/ReviewRestController.java) |
   | 5º	| add new methods to userRestController	| [method api user](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/048f380aea7b87a60122316be1462dcf228ff34a) | [userRestController](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/backend/src/main/java/es/gualapop/backend/controller/api/UserRestController.java) |


# Fase 4

## Configuración del Entorno de Desarrollo

Para configurar el entorno de desarrollo de la aplicación SPA utilizando Angular, sigue estos pasos:

### Prerrequisitos

1. **Node.js**: Asegúrate de que Node.js esté instalado en tu sistema. Puedes descargarlo desde [el sitio web oficial de Node.js](https://nodejs.org/es).
   
2. **Angular CLI**: Instala Angular CLI globalmente usando npm:

   ```bash
   npm install -g @angular/cli

### Iniciar Angular

1. **Clonar el proyecto**
 ```bash
   https://github.com/CodeURJC-DAW-2023-24/webapp13.git
   ```

2. **Asegurarse de que el backend esté en funcionamiento**

3. **Redireccionar al directorio ProjectFrontend**
   ```bash
   cd webapp13/frontend
   ```
   
4. **Instalar todos los módulos necesarios**
   ```bash
   npm install
   ```
   
5. **Ejecutar la SPA**
   ```bash
   npm start
   ```

La SPA estará lista en http://localhost:4200/.

## SPA con Docker y Máquina Virtual
Para acceder a la SPA con Docker o con la máquina virtual, debes seguir las instrucciones en la fase 3 de como crear contenedores y hacerlo o en local o en la maquina virtual. la ruta local sería https://localhost:8443/new respectivamente y en la maquina virtual sería https://10.100.139.229:8443/new

## Diagrama de Clases Angular
Leyenda del diagrama:
- Verde: Componentes
- Rojo: Servicios
- Azul: Templates

![diag_angular](https://github.com/CodeURJC-DAW-2023-24/webapp13/assets/80538164/079d5305-20ba-488e-9363-ee8b52aca196)


## Participación de miembros (Fase 4)


### Rodrigo Montilla Fernández | r.montilla.2019@alumnos.urjc.es

| Número	| Descripción	| Commit | Archivos |
   | :---: | :---: | :---: | :---: |
   | 1º | Products & Categories	| [Fix Categories + Products](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/d458ddbd1e4d049ead60e9930ee8413d810d744e) | [Index Component](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/frontend/fase4/src/app/index/index.component.ts) |
   | 2º	| Individual Product	| [Individual Product Without User Img](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/f46b4b348e2f37e517145216fc9ad8d71dc9ec27) | [Individual Product](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/frontend/fase4/src/app/individual-product/individual-product.component.ts) |
   | 3º	| Purchase Product	| [Purchase Product](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/75a89dcd97736e5e30ccfbf54b519cb4c2841a67) | [Product Component](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/frontend/fase4/src/app/product/product.component.ts) |
   | 4º	| Consult Profile & Report | [Reports & More](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/c0a347718f9ef9620e03504cc34957c8c565aefb) | [Report-Form Component](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/frontend/fase4/src/app/report-form/report-form.component.ts) |
   | 5º	| Manage Reports	| [Manage Reports](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/243aeaecc949d8492f05e594b3038c568c246aae) | [Report Component](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/frontend/fase4/src/app/report/report.component.ts) |


### Fernando Prieto Olías	| f.prieto.2019@alumnos.urjc.es

| Número	| Descripción	| Commit | Archivos |
   | :---: | :---: | :---: | :---: |
   | 1º | Anguar Login	| [Angular login](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/e5d4803e1a491366a5c3621e8430e5de88744431) | [auth.service.ts](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/frontend/fase4/src/app/Services/auth.service.ts) |
   | 2º	| Benefict chart on user profile and logout	| [Benefict chart and logout fix](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/65463ffea538cfe618c71d4cf9cc48d45fa68978) | [profile.component.ts](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/frontend/fase4/src/app/profile/profile.component.ts) |
   | 3º	| apiPDF function in backend and new product	| [api PDF y newProduct en angular](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/8ef0380fdc522395eae1119f1d86c813dea3bd9e) | [new-product.component.ts](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/frontend/fase4/src/app/new-product/new-product.component.ts) |
   | 4º	| Include angular in docker	| [Docker angular](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/aec4710851b6852774b8b9a63c439b51d0bc365f) | [Dockerfile](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/Dockerfile) |
   | 5º	| Add user profile settings in angular	| [settings user profile](https://github.com/CodeURJC-DAW-2023-24/webapp13/commit/8f828e184b4c8737def8859f533c7fe8adadf014) | [user.service.ts](https://github.com/CodeURJC-DAW-2023-24/webapp13/blob/main/frontend/fase4/src/app/Services/user.service.ts) |

## Enlace al vídeo de YouTube

[Video Demostración](https://youtu.be/eIF4X-uYLE4)
