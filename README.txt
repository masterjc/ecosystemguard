EcosystemGuard es un conjunto de módulos que conforman una aplicación para la vigilancia remota de sistemas vivos como terrarios, acuarios, etc. 
Principalmente está compuesto por el software que se debe instalar en el host local del ecosistema a vigilar (HostManager), el software central 
para la gestión de cuentas de EcosystemGuard y el software para los dispositivos móviles desde los que se accederá y consumirá la información 
recaptada en los diferentes ecosistemas.

* La carpeta "build-tools" contiene los scripts siguientes (para linux y windows):

-- set-env: Script para setear las variables de entorno necesarias para la generación de la aplicación. Ejecutar este script para compilar módulos sueltos.
-- build.xml: Archivo ANT principal
-- generate: Script para setear el entorno y generar la aplicación. Copia en build-tools/build las aplicaciones que se deben desplegar.
-- deploy: Script para setear el entorno y generar la aplicación. Además, despliega la aplicación en un entorno local de desarrollo. Ver set-env.

Cómo generar la aplicación:
---------------------------

1. Situarse en el directorio "build-tools"
2. Ejecutar "generate"
3. En la carpeta "build" se pueden encontrar los diferentes componentes.

Cómo compilar módulos sueltos:
------------------------------

1. Situarse en el directorio "build-tools"
2. Ejecutar "set-env"
3. Compilar los módulos deseados

Cómo desarrollar con Eclipse
----------------------------

Eclipse utiliza un classpath definido en cada módulo en el archivo ".classpath" con lo que no utiliza la definición de classpath de ANT. Así pues, se ha utilizado un mecanismo de referencia a librerías usando VARIABLES del workspace de ECLIPSE.

Generar la variable de Eclipse "PROJECT_DIR" apuntando a la carpeta raíz del proyecto "ecosystemguard".

REQUISITOS EXTERNOS
-------------------

1. Actualmente, el proyecto depende de librerías de terceros las cuales no se incluyen en este repositorio. Se deberán descargar aparte 
y dejarlas disponibles en la carpeta "PROJECT_DIR/thirdparty". Para ver los requisitos externos ver "project-definitions.xml".
