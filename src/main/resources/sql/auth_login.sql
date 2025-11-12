SELECT
  idUsuario,
  nombre,
  rol
FROM usuario
WHERE activo = TRUE
  AND usuario   = ?
  AND contrasena = ?
LIMIT 1;