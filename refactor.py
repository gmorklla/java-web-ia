import os
import re

package_map = {
    # model
    "Categoria": "mx.com.qtx.core.model.Categoria",
    "Cliente": "mx.com.qtx.core.model.Cliente",
    "DetallePedido": "mx.com.qtx.core.model.DetallePedido",
    "DetalleVenta": "mx.com.qtx.core.model.DetalleVenta",
    "Empleado": "mx.com.qtx.core.model.Empleado",
    "InventarioProducto": "mx.com.qtx.core.model.InventarioProducto",
    "Pedido": "mx.com.qtx.core.model.Pedido",
    "Producto": "mx.com.qtx.core.model.Producto",
    "Proveedor": "mx.com.qtx.core.model.Proveedor",
    "ProveedorPrimarioProd": "mx.com.qtx.core.model.ProveedorPrimarioProd",
    "ProveedorPrimarioProdId": "mx.com.qtx.core.model.ProveedorPrimarioProdId",
    "Rol": "mx.com.qtx.core.model.Rol",
    "Tienda": "mx.com.qtx.core.model.Tienda",
    "TipoEmpleado": "mx.com.qtx.core.model.TipoEmpleado",
    "Venta": "mx.com.qtx.core.model.Venta",
    "ItemCarrito": "mx.com.qtx.core.model.ItemCarrito",

    # service
    "CarritoCompraService": "mx.com.qtx.core.service.CarritoCompraService",
    "IGestorDatosAltoNivel": "mx.com.qtx.core.service.IGestorDatosAltoNivel",
    "CatalogoService": "mx.com.qtx.core.service.CatalogoService",

    # controller
    "CatalogoController": "mx.com.qtx.api.controller.CatalogoController",
    "CarritoController": "mx.com.qtx.api.controller.CarritoController",

    # dto
    "CategoriaDTO": "mx.com.qtx.api.dto.CategoriaDTO",
    "CategoriaProductoDTO": "mx.com.qtx.api.dto.CategoriaProductoDTO",

    # persistence
    "CategoriaRepository": "mx.com.qtx.infrastructure.persistence.CategoriaRepository",
    "ProductoRepository": "mx.com.qtx.infrastructure.persistence.ProductoRepository",
    "GestorVentasDAO": "mx.com.qtx.infrastructure.persistence.GestorVentasDAO",
    "GestorVentasJPAmanual": "mx.com.qtx.infrastructure.persistence.GestorVentasJPAmanual",
    "IGestorDatos": "mx.com.qtx.infrastructure.persistence.IGestorDatos",

    # monitoring
    "ConcurrencyTrackingFilter": "mx.com.qtx.infrastructure.monitoring.ConcurrencyTrackingFilter",
    "SessionMonitoringListener": "mx.com.qtx.infrastructure.monitoring.SessionMonitoringListener",

    # config
    "DipArq07m03explorCapaPer1Application": "mx.com.qtx.infrastructure.config.DipArq07m03explorCapaPer1Application",

    # tests
    "ProbadorCapaDatos": "mx.com.qtx.test.servicios.ProbadorCapaDatos",
    "ProbadorCapaDatosJPA": "mx.com.qtx.test.servicios.ProbadorCapaDatosJPA"
}

def get_expected_package(filepath):
    filepath = filepath.replace('\\', '/')
    if "src/main/java/" in filepath:
        rel_path = filepath.split("src/main/java/")[1]
    elif "src/test/java/" in filepath:
        rel_path = filepath.split("src/test/java/")[1]
    else:
        return None
    pkg_path = os.path.dirname(rel_path)
    return pkg_path.replace('/', '.')

def process_file(filepath):
    expected_package = get_expected_package(filepath)
    if not expected_package: return

    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    # Replace package
    content = re.sub(r'^package\s+([a-zA-Z0-9_\.]+);', f'package {expected_package};', content, flags=re.MULTILINE)

    # Replace specific old imports that might be in the code with new ones
    for class_name, new_full_class in package_map.items():
        pattern = r'^import\s+mx\.com\.qtx\.dipArq07m03explorCapaPer\.[a-zA-Z0-9_\.]+\.' + class_name + r';'
        content = re.sub(pattern, f'import {new_full_class};', content, flags=re.MULTILINE)
        
        # Also replace imports in old formats like `import mx.com.qtx.dipArq07m03explorCapaPer.negocio.ItemCarrito;` etc.
        # The regex above handles it because `mx.com.qtx.dipArq07m03explorCapaPer.[a-zA-Z0-9_\.]+` matches the subpackage.

        # What if it's imported as `import mx.com.qtx.dipArq07m03explorCapaPer.DipArq07m03explorCapaPer1Application;`?
        if class_name == "DipArq07m03explorCapaPer1Application":
            pattern = r'^import\s+mx\.com\.qtx\.dipArq07m03explorCapaPer\.' + class_name + r';'
            content = re.sub(pattern, f'import {new_full_class};', content, flags=re.MULTILINE)
            
    # Remove old wildcard imports just in case
    content = re.sub(r'^import\s+mx\.com\.qtx\.dipArq07m03explorCapaPer\.[a-zA-Z0-9_\.]+\.\*;', '', content, flags=re.MULTILINE)

    # Clean up imports of same package
    lines = content.split('\n')
    new_lines = []
    for line in lines:
        if line.startswith('import '):
            imported_class_full = line.replace('import ', '').replace(';', '').strip()
            imported_pkg = '.'.join(imported_class_full.split('.')[:-1])
            if imported_pkg == expected_package:
                continue
        new_lines.append(line)

    content = '\n'.join(new_lines)


    with open(filepath, 'w', encoding='utf-8') as f:
        f.write(content)

for root, dirs, files in os.walk('src'):
    for file in files:
        if file.endswith('.java'):
            process_file(os.path.join(root, file))

print("Refactoring complete.")
