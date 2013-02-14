* La carpeta "build-tools" contiene los scripts siguientes (para linux y windows):

-- set-env: Script para setear las variables de entorno necesarias para la generación de la aplicación. Ejecutar este script para compilar módulos sueltos.
-- build.xml: Archivo ANT para compilar todos los módulos y generar una carpeta build-tools/build donde se copiará los archivos de la aplicación.
-- generate: Script para setear el entorno y generar la aplicación. Lo hace llamando a los scripts anteriores.

Cómo generar la aplicación:
---------------------------

1. Situarse en el directorio "build-tools"
2. Ejecutar "ant"

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

1. Actualmente, el proyecto depende de librerías de JBoss para J2EE con lo que es necesario descargar JBoss AS 7.1.1 y descomprimir su contenido en la carpeta "PROJECT_DIR/thirdparty/jboss/jboss"