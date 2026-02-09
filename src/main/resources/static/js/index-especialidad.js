$(document).ready(function () {
    // Inicializa el DataTable sobre la tabla con ID 'tablaEspecialidad'
    const tabla = $('#tablaEspecialidad').DataTable({
        pageLength: 6, // Muestra 6 registros por página
        lengthChange: false, // Oculta el selector de cantidad de registros por página
		language: {
		    lengthMenu: "Mostrar _MENU_ registros por página",
		    zeroRecords: "No se encontraron resultados",
		    info: "Mostrando _START_ a _END_ de _TOTAL_ registros",
		    infoEmpty: "Mostrando 0 a 0 de 0 registros",
		    infoFiltered: "(filtrado de _MAX_ registros totales)",
		    search: "Buscar:",
		    paginate: {
		        first: "Primero",
		        last: "Último",
		        next: "Siguiente",
		        previous: "Anterior"
				}
        },

        // Esta función se ejecuta cuando DataTables termina de inicializar
        initComplete: function () {
            // Recorre cada fila del cuerpo de la tabla
            $('#tablaEspecialidad tbody tr').each(function () {
                // Busca la celda con clase "monto"
                const td = $(this).find('td.monto');

                // Convierte el texto a número decimal
                const valor = parseFloat(td.text());

                // Si el valor es numérico, lo formatea como moneda: S/ 00.00
                if (!isNaN(valor)) {
                    td.text('S/ ' + valor.toFixed(2));
                    // Alternativa con miles: td.text(new Intl.NumberFormat('es-PE', { style: 'currency', currency: 'PEN' }).format(valor));
                }
            });
        }
    });

    // Guarda el input de búsqueda por texto
    const input = $('#buscarInput');

    // Guarda el selector del tipo de filtro (por código o por especialidad)
    const filtro = $('#filtroTipo');

    // Agrega una función personalizada de filtro a DataTables
    $.fn.dataTable.ext.search.push(function (settings, data, dataIndex) {
        const tipo = filtro.val();              // Obtiene si está filtrando por "codigo" o "especialidad"
        const valor = input.val().toLowerCase(); // Obtiene el texto ingresado por el usuario

        const codigo = data[0].toLowerCase();        // Primera columna (ID)
        const especialidad = data[1].toLowerCase();  // Segunda columna (nombre)

        // Filtra según lo seleccionado
        if (tipo === "codigo" && codigo.includes(valor)) return true;
        if (tipo === "especialidad" && especialidad.includes(valor)) return true;

        // Si el campo está vacío, muestra todo
        return valor === "";
    });

    // Cada vez que se escribe en el input, redibuja la tabla aplicando filtros
    input.on("input", function () {
        tabla.draw();
    });

    // Cada vez que se cambia el tipo de filtro (código o especialidad), redibuja la tabla
    filtro.on("change", function () {
        tabla.draw();
    });
});
