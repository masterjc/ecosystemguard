EcosystemGuard es un conjunto de m�dulos que conforman una aplicaci�n para la vigilancia remota de sistemas vivos como terrarios, acuarios, etc. 
Principalmente est� compuesto por el software que se debe instalar en el host local del ecosistema a vigilar (HostManager), el software central 
para la gesti�n de cuentas de EcosystemGuard y el software para los dispositivos m�viles desde los que se acceder� y consumir� la informaci�n 
recaptada en los diferentes ecosistemas.

* La carpeta "build-tools" contiene los scripts siguientes (para linux y windows):

-- set-env: Script para setear las variables de entorno necesarias para la generaci�n de la aplicaci�n. Ejecutar este script para compilar m�dulos sueltos.
-- build.xml: Archivo ANT principal
-- generate: Script para setear el entorno y generar la aplicaci�n. Copia en build-tools/build las aplicaciones que se deben desplegar.
-- deploy: Script para setear el entorno y generar la aplicaci�n. Adem�s, despliega la aplicaci�n en un entorno local de desarrollo. Ver set-env.

C�mo generar la aplicaci�n:
---------------------------

1. Situarse en el directorio "build-tools"
2. Ejecutar "generate"
3. En la carpeta "build" se pueden encontrar los diferentes componentes.

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

1. Actualmente, el proyecto depende de librer�as de terceros las cuales no se incluyen en este repositorio. Se deber�n descargar aparte 
y dejarlas disponibles en la carpeta "PROJECT_DIR/thirdparty". Para ver los requisitos externos ver "project-definitions.xml".
