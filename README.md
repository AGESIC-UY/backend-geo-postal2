# geo-postal2

"geoPostal" es el visualizador de mapas implementado por la Administración Nacional de Correos (ANC) para manipular y presentar la información geográfica de relevancia para la empresa.

"GeoPostal" es el visualizador de mapas implementado por la Administración Nacional de Correos (ANC) para manipular y presentar la información geográfica de relevancia para la empresa.

Dada la estrecha relación entre las actividades llevadas a cabo en la Administración Nacional de Correos y la información geográfica, surgió la iniciativa de implementar un visualizador geográfico que permita, además de presentar las herramientas básicas de un mapa (navegación, búsqueda, etc.), hacer pública la información relevante a la ciudadanía.

"geoPostal" incluye un sistema de administración que permite configurar las capas de información, búsquedas permitidas, mapas base y plugins del visualizador en perfiles diferenciados permitiendo poseer “más de un visualizador”. Es una aplicación orientada a plugins y por ello brinda una interfaz amigable para adicionarlos y enriquecer así sus funcionalidades.

La solución se encuentra desarrollado en GeoExt, una librería JavaScript que se integra fácilmente con OpenLayers. El sistema de administración "geoPostal" se encuentra desarrollado en Java utilizando Java IceFaces 3 y ambas aplicaciones, visualizador y administrador, corren en un servidor Tomcat 6.  Las capas de información de publican en GeoServer 2.2.2 y se almacena la información de las mismas en una base de datos Postgis 2.0.
