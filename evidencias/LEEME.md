# Evidencias del Taller #2

Guarde en esta carpeta las capturas de las **ocho pruebas obligatorias**, nombradas así:

```
prueba-1-get-listar-200.png
prueba-2-get-por-id-200.png
prueba-3-post-registrar-201.png
prueba-4-put-actualizar-200.png
prueba-5-delete-eliminar-200.png
prueba-6-post-invalido-400.png
prueba-7-put-invalido-400.png
prueba-8-get-inexistente-404.png
```

## Cómo tomar cada captura en IntelliJ IDEA

1. Ejecute `ApiTaller2Application` y espere el mensaje `Started ApiTaller2Application`.
2. Abra `pruebas/empleados.http`.
3. Ejecute las peticiones **en orden** con el botón verde ▶ de cada bloque.
4. En cada respuesta debe verse: el **método**, la **URL**, el **código HTTP** y el **JSON**.
5. Capture la pantalla (`Win + Shift + S`) incluyendo el panel de la petición y el de la respuesta,
   y guarde el archivo en esta carpeta con el nombre indicado arriba.

> Importante: ejecute las pruebas en orden. La prueba 5 elimina el empleado con id 3 y
> la prueba 4 modifica el empleado con id 2; si repite pruebas, reinicie la aplicación
> para volver a los datos iniciales.

## Códigos esperados

| Prueba | Código |
|---|---|
| 1, 2, 4, 5 | 200 OK |
| 3 | 201 Created |
| 6, 7 | 400 Bad Request |
| 8 | 404 Not Found |
