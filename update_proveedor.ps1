$filePath = "C:\Users\Usuario\Desktop\agenda-medica-comunitaria\src\main\java\com\usco\agendamedicacomunitaria\service\ProveedorServiceImpl.java"
$content = Get-Content -Path $filePath -Raw
$newContent = $content -replace 'return proveedorRepository.findAll\(\);', 'return proveedorRepository.findAllActivos();'
Set-Content -Path $filePath -Value $newContent
