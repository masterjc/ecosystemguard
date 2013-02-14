* La carpeta "build-tools" contiene los scripts siguientes (para linux y windows):

-- set-env: Script para setear las variables de entorno necesarias para la generaci�n de la aplicaci�n. Ejecutar este script para compilar m�dulos sueltos.
-- build.xml: Archivo ANT para compilar todos los m�dulos y generar una carpeta build-tools/build donde se copiar� los archivos de la aplicaci�n.
-- generate: Script para setear el entorno y generar la aplicaci�n. Lo hace llamando a los scripts anteriores.

C�mo generar la aplicaci�n:
---------------------------

1. Situarse en el directorio "build-tools"
2. Ejecutar "ant"

C�mo compilar m�dulos sueltos:
------------------------------

1. Situarse en el directorio "build-tools"
2. Ejecutar "set-env"
3. Compilar los m�dulos deseados

C�mo desarrollar con Eclipse
----------------------------

Eclipse utiliza un classpath definido en cada m�dulo en el archivo ".classpath" con lo que no utiliza la definici�n de classpath de ANT. As� pues, se ha utilizado un mecanismo de referencia a librer�as usando VARIABLES del workspace de ECLIPSE.

Generar la variable de Eclipse "PROJECT_DIR" apuntando a la carpeta ra�z del proyecto "ecosystemguard".

REQUISITOS EXTERNOS
-------------------

1. Actualmente, el proyecto depende de librer�as de JBoss para J2EE con lo que es necesario descargar JBoss AS 7.1.1 y descomprimir su contenido en la carpeta "PROJECT_DIR/thirdparty/jboss/jboss"